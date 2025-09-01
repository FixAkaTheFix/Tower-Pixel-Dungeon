package com.fixakathefix.towerpixeldungeon.actors.mobs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.Dungeon.updateLevelExplored;
import static com.fixakathefix.towerpixeldungeon.Dungeon.win;

import com.badlogic.gdx.utils.Timer;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Frost;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Pushing;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Arena25;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.AmuletScene;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingAvatarSprite;
import com.fixakathefix.towerpixeldungeon.sprites.YogSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.AttackIndicator;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossYog extends Mob{

    {
        spriteClass = YogSprite.class;

        HP = HT = 20000;

        defenseSkill = 0;

        viewDistance = 100;

        baseSpeed=1f;

        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);
        immunities.add(Paralysis.class);
        immunities.add(Chill.class);
        immunities.add(Frost.class);

        state = PASSIVE;

        alignment = Alignment.ENEMY;
    }

    public boolean shielded = false;
    public int phase = 0;

    private boolean reallydead = false;

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }
    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (shielded){
            return 0;
        } else {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                    candidates.add(pos + n);
                }
            }

            if (!candidates.isEmpty()) {
                YogDzewa.Larva spawn = new YogDzewa.Larva();
                spawn.alignment = this.alignment;

                spawn.pos = Random.element(candidates);
                spawn.state = spawn.HUNTING;

                GameScene.add(spawn, 0);
                Dungeon.level.occupyCell(spawn);

                if (sprite.visible) {
                    Actor.addDelayed(new Pushing(spawn, pos, spawn.pos), -1);
                }
            }
        }
        return super.defenseProc(enemy, damage);
    }

    /**
     * initially the boss is at phase 0 and will wait until it is damaged. After it is damaged, he will "shield" and start wave 1 phase 1.
     This boss has 5 phases, which are similar.
     Each fifth wave it is weakened, allowing it to be hit, the Portal is meanwhile stalled and will just spam demons and succubi minions on the sides.
     After the boss HP is reduced by 1000 (per damage cycle/5 waves), the boss pushes all nearby chars outwards and start the wave cooldown.
     Then, 5 waves.

     */
    public void weaken(){
        shielded = false;
        updateSpriteState();
        Buff.detach(Dungeon.hero, WaveCooldownBuff.class);
        ((Arena)level).amuletTower.stalled = true;
    }
    public void shield(){
        ((Arena)level).amuletTower.stalled = false;
        shielded = true;
        updateSpriteState();
        phase++;
        LargeBlastWave.blast(pos);
        for (Mob mob : Level.mobs){
            if (distance(mob) < 6){
                Ballistica ballistica = new Ballistica(pos, mob.pos, Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(mob, ballistica, 20, false, false, getClass());
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        Sample.INSTANCE.play(Assets.Sounds.DEATH, 1f, 0.8f);
        Buff.affect(Dungeon.hero, WaveCooldownBuff.class, 100);
    }

    @Override
    public void updateSpriteState() {
        super.updateSpriteState();
        if (shielded) sprite.add(CharSprite.State.SHIELDED);
        else sprite.remove(CharSprite.State.SHIELDED);
    }

    private void firstShield(){
        level.buildFlagMaps();
        Buff.affect(Dungeon.hero, WaveCooldownBuff.class, 100);
        ((Arena)level).amuletTower.stalled = false;
        shielded = true;
        updateSpriteState();
        viewDistance = 100;
        Dungeon.hero.viewDistance = 100;
        updateLevelExplored();
        GameScene.updateFog();
        Dungeon.observe();
        ((Arena)level).updateFieldOfView(Dungeon.hero, Dungeon.hero.fieldOfView);
        LargeBlastWave.blast(pos);
        for (Mob mob : Level.mobs){
            if (distance(mob) < 10){
                Ballistica ballistica = new Ballistica(pos, mob.pos, Ballistica.PROJECTILE);
                WandOfBlastWave.throwChar(mob, ballistica, 20, false, false, getClass());
            }
        }
        if (distance(Dungeon.hero)< 8){
            Ballistica heroballistica = new Ballistica(pos, ((Arena)level).amuletCell, Ballistica.STOP_TARGET);
            WandOfBlastWave.throwChar(Dungeon.hero, heroballistica, 20, false, false, getClass());
        }

        Sample.INSTANCE.play(Assets.Sounds.BLAST, 1f, 0.8f);
        updateSpriteState();
        GameScene.scene.menu.active =
                GameScene.scene.menu.visible = false;
        GameScene.scene.status.active =
                GameScene.scene.status.visible = false;
        Dungeon.hero.busy();
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 24 + ((Arena)level).width() * 24;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 2);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 30);
            }
        }, 1f);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 14 + ((Arena)level).width() * 35;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 7);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 25);
            }
        }, 2f);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 15 + ((Arena)level).width() * 55;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 12);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 20);
            }
        }, 3f);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 46 + ((Arena)level).width() * 24;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 2);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 30);
            }
        }, 4f);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 56 + ((Arena)level).width() * 35;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 7);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 25);
            }
        }, 5f);
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = 55 + ((Arena)level).width() * 55;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 5f);
                EnemyPortal.createEnemyPortal(pos, 12);
                CellEmitter.floor(pos).start(ShadowParticle.UP, 0.1f, 20);
            }
        }, 6f);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                int pos = Dungeon.hero.pos;
                Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 0.5f);
            }
        }, 7f);

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Dungeon.hero.ready = true;
                AttackIndicator.updateState();
                GameScene.scene.menu.active =
                        GameScene.scene.menu.visible =
                                GameScene.scene.status.active =
                                        GameScene.scene.status.visible = true;
                Buff.affect(Dungeon.hero, WaveCooldownBuff.class, 50);
                ((Arena)level).amuletTower.stalled = false;
            }
        }, 10f);
    }
    public void startBattle(){
        phase++;
        Dungeon.level.playLevelMusic();
        firstShield();
    }

    @Override
    public void damage(int dmg, Object src) {
        if (dmg > 10000) {
            reallydead = true;
            die(Dungeon.hero);
            return;
        }
        if (shielded){
            sprite.showStatus(CharSprite.MYSTERIOUS, Messages.get(DwarfKing.class, "invulnerable"));
        } else {
            super.damage(dmg*10, src);
            if (phase == 0){
                startBattle();
            } if (HP < 16001 && phase == 1){
                HP = 16000;
                shield();
            } else if (HP < 12001 && phase == 2){
                HP = 12000;
                shield();
            } else if (HP < 8001 && phase == 3){
                HP = 8000;
                shield();
            } else if (HP < 4001 && phase == 4){
                HP = 4000;
                shield();
            } else if (phase == 5){
                if (HP < 1) {
                    reallydead = true;
                    die(Dungeon.hero);
                }
            }
        };
    }

    private int countToDemonsLeft = 0;
    private final int countToDemons = 20;
    @Override
    protected boolean act() {
        if (!shielded && phase!=0){
            countToDemonsLeft--;
            if (countToDemonsLeft<=0){
                ((Arena)level).deploymobs(8055, Arena.Direction.UP, 2);
                countToDemonsLeft = countToDemons;
            }

        }
        return super.act();
    }



    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    @Override
    public void die(Object cause) {
        if (reallydead){
            Badges.validateEnemy(this);
            win( Amulet.class );
            Dungeon.deleteGame( GamesInProgress.curSlot, true );
            GameScene.scene.menu.active =
                    GameScene.scene.menu.visible = false;
            GameScene.scene.status.active =
                    GameScene.scene.status.visible = false;
            Timer timer = new Timer();
            hero.busy();
            Camera.main.panTo(DungeonTilemap.tileCenterToWorld(pos), 1f);
            Camera.main.shake(3, 1);
            WndDialogueWithPic.dialogue(new YogSprite(), Messages.get(BossYog.class, "name"),
                    new String[]{
                            Messages.get(BossYog.class, "realend1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    }, WndDialogueWithPic.WndType.YOGFINAL);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                    Camera.main.shake(3, 0.5f);
                }
            }, 1f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                    Camera.main.shake(7, 0.5f);
                }
            }, 2f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                    Camera.main.shake(9, 0.5f);
                }
            }, 3f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                    Camera.main.shake(20, 0.5f);
                }
            }, 4f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.ROCKS);
                    Camera.main.shake(30, 2f);
                }
            }, 5f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    GameScene.flash(0xFFFFFF);
                }
            }, 6.8f);
            timer.scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    ShatteredPixelDungeon.switchScene(AmuletScene.class);
                }
            }, 7f);

        }
    }

    @Override
    public boolean interact(Char c) {
        if (shielded) {
            return true;
        }
        return super.interact(c);
    }

    private static final String SHIELDED = "shielded";
    private static final String PHASE = "phase";
    private static final String COUNTTODEMONS = "counttodemons";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SHIELDED, shielded);
        bundle.put(PHASE, phase);
        bundle.put(COUNTTODEMONS, countToDemonsLeft);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        shielded = bundle.getBoolean(SHIELDED);
        phase = bundle.getInt(PHASE);
        countToDemonsLeft = bundle.getInt(COUNTTODEMONS);
    }
    public class LargeBlastWave extends WandOfBlastWave.BlastWave {
        @Override
        public void update() {
            super.update();

            if ((time -= Game.elapsed) <= 0) {
                kill();
            } else {
                float p = time / TIME_TO_FADE;
                alpha(p);
                scale.y = scale.x = (1-p)*8;
            }
        }
    }
}