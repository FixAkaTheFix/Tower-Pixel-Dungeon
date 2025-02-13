package com.fixakathefix.towerpixeldungeon.actors.mobs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Fire;
import com.fixakathefix.towerpixeldungeon.actors.blobs.SmokeScreen;
import com.fixakathefix.towerpixeldungeon.actors.blobs.ToxicGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Charm;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Cripple;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Frost;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Weakness;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Arena10;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.scenes.RankingsScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TenguSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossTengu extends Mob {

    {
        spriteClass = TenguSprite.class;

        HP = HT = 3500;
        defenseSkill = 8;
        viewDistance = 4;

        properties.add(Property.BOSS);

        immunities.add( Paralysis.class );
        immunities.add( ToxicGas.class );
        immunities.add( Charm.class );
        immunities.add( Blindness.class );
        immunities.add( Burning.class) ;
        targetingPreference = TargetingPreference.NOT_AMULET;
        ranged = true;
    }

    public int phase = 1;
    public void setPhase(int phas) {

        //1 = simple ranged attacks dealing random debuffs
        //2 = After being attacked summons a shinobi
        //3 = ranged again, now throws bombs
        //4 = blind rage after a smokebang, all towers paralyzed forever and blinded, he is one-hit

        if (phase == 1) {
        }
        if (phase == 1 && phas == 2) {
            speak("Suffer...", CharSprite.WARNING);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.3f, 1f);
            phase = phas;
        }
        if (phase == 2 && phas == 3) {
            speak("Weak", CharSprite.WARNING);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.3f, 1f);
            phase = phas;
        }
        if (phase == 3 && phas == 4) {
            phase = phas;

            ArrayList<Runnable> r2 = new ArrayList<>();
            for (int i = 0; i < 1;i++)r2.add(null);
            if (level instanceof Arena10)((Arena)Dungeon.level).deploymobs(8056, Arena.Direction.RIGHT, 1);
            r2.add(1, new Runnable() {
                @Override
                public void run() {
                    Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
                    for (int i : PathFinder.NEIGHBOURS25){
                        int cell = pos + i;

                        if (level.passable[cell] && Char.findChar(cell)==null) {
                            BossTenguClone clone = new BossTenguClone();
                            clone.targetingPreference = TargetingPreference.NOT_AMULET;
                            clone.pos = cell;
                            GameScene.add(clone);
                        }
                    }
                }
            });
            Buff.affect(BossTengu.this, AnkhInvulnerability.class, 10);

            WndDialogueWithPic.dialogue(new TenguSprite(), Messages.get(BossTengu.class, "name"),
                    new String[]{
                            Messages.get(BossTengu.class, "lastbreath1"),
                            Messages.get(BossTengu.class, "lastbreath2", hero.className())
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    }, WndDialogueWithPic.WndType.NORMAL, r2);
            Sample.INSTANCE.play(Assets.Sounds.GAS,1.3f, 0.5f);
            Buff.affect(Dungeon.hero, Blindness.class, 2);
            for (int i : PathFinder.NEIGHBOURS25)
                try {
                    if (Dungeon.level.passable[pos + i] && hero.fieldOfView[pos + i]) {
                        CellEmitter.floor(pos+i).start(SmokeParticle.FACTORY, 0.1f, 10);
                    }
                } catch (Exception ignored) {}
        }




    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 10, 20 );
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
        return attack.collisionPos == enemy.pos;
    }

    private int callForHelpTime=20;
    private int callForHelpTimer=0;

    private int warpTime = 10;
    private int warpTimer=0;

    private int cloneTime = 9;
    private int cloneTimer =0;

    private int gasTime = 16 ;
    private int gasTimer =0;

    @Override
    protected boolean act() {
        int x = 1;
        if (HP<= 2500) x = 2;
        if (HP<= 1500) x = 3;
        if (HP <= 500) x = 4;
        if (phase != x) setPhase(x);
        callForHelpTimer++;
        warpTimer++;
        cloneTimer++;
        gasTimer++;
        if( Dungeon.depth==10 && callForHelpTimer>=callForHelpTime ){
            ((Arena)Dungeon.level).deploymobs(8055, Arena.Direction.RIGHT, 1);
            callForHelpTimer=0;
        }
        if( warpTimer>=warpTime){
            if (enemy == null) enemy = hero;
            warpTimer = 0;
            ArrayList<Integer> cellsToWarp = new ArrayList<>();
            final int targetCell = enemy.pos;
            Item pot = new PotionOfShroudingFog();
            pot.icon = ItemSpriteSheet.POTION_PARALYTICGAS;
            ((MissileSprite) sprite.parent.recycle(MissileSprite.class)).
                    reset(pos, targetCell, pot, new Callback() {
                        @Override
                        public void call() {
                            GameScene.add( Blob.seed( targetCell, 50, SmokeScreen.class ) );
                            Sample.INSTANCE.play( Assets.Sounds.SHATTER );
                            next();
                        }
                    });
            enemy = null;
            Sample.INSTANCE.play(Assets.Sounds.GAS);
            for (int seen = 1; seen < level.width()*level.height()-5; seen++) if (Dungeon.level.distance(((Arena)level).amuletCell, seen) <= 8 && Char.findChar(seen)==null && level.passable[seen]) cellsToWarp.add(seen);
            if (!cellsToWarp.isEmpty()){
                ScrollOfTeleportation.appear( this, Random.element(cellsToWarp) );
                Dungeon.level.occupyCell( this );
            }
        }
        if( gasTimer >= gasTime){
            if (enemy == null) enemy = hero;
            gasTimer = 0;
            if (enemy!=null) {
            ((MissileSprite) sprite.parent.recycle(MissileSprite.class)).
                    reset(pos, enemy.pos, new PotionOfLiquidFlame(), new Callback() {
                        @Override
                        public void call() {
                            if (enemy!=null) GameScene.add( Blob.seed( enemy.pos, 2, Fire.class ) );
                            Sample.INSTANCE.play( Assets.Sounds.SHATTER );
                            next();
                        }
                    });
            }
            if (enemy!=null) {
                ((MissileSprite) sprite.parent.recycle(MissileSprite.class)).
                        reset(pos, hero.pos, new PotionOfToxicGas() , new Callback() {
                            @Override
                            public void call() {
                                if (enemy!=null) GameScene.add( Blob.seed( hero.pos, 100, ToxicGas.class ) );
                                Sample.INSTANCE.play( Assets.Sounds.SHATTER );
                                next();
                            }
                        });
            }
        }
        if( cloneTimer >= cloneTime){
            BossTenguClone clone = new BossTenguClone();
            clone.pos = pos;
            if (enemy == null) enemy = hero;
            clone.alignment = Alignment.ENEMY;
            GameScene.add(clone);
            cloneTimer = 0;
            ArrayList<Integer> cellsToWarp = new ArrayList<>();
            for (int seen = 1; seen < level.width()*level.height()-5; seen++) if (Dungeon.level.distance(((Arena)level).amuletCell, seen) <= 8 && Char.findChar(seen)==null && level.passable[seen]) cellsToWarp.add(seen);
            if (!cellsToWarp.isEmpty()){
                ScrollOfTeleportation.appear( this, Random.element(cellsToWarp) );
                Dungeon.level.occupyCell( this );
            }
        }
        return super.act();
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        int cell;


        if (phase == 3) for (int i : PathFinder.NEIGHBOURS9){
            cell = enemy.pos + i;
            if (Actor.findChar(cell)!=null){
                if (Actor.findChar(enemy.pos).alignment == Alignment.ENEMY){
                    Actor.findChar(cell).damage (Math.round(damageRoll()*0.05f) - enemy.drRoll(), Bomb.class);//his minions receive 5% damage only
                } else if (Actor.findChar(enemy.pos)==Actor.findChar(cell)) {
                } else Actor.findChar(cell).damage (Math.round(damageRoll()*0.3f) - enemy.drRoll(),Bomb.class);//damages foes nearby, with lowered damage
            }
            if (Dungeon.level.heroFOV[enemy.pos+i]) {
                CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
            }
            if (Dungeon.level.heroFOV[enemy.pos]) {
                CellEmitter.center(enemy.pos).start(SmokeParticle.FACTORY, 0.3f, 8);
                CellEmitter.center(enemy.pos).start(SmokeParticle.FACTORY, 1f, 8);
            }
            if (Dungeon.level.flamable[cell]) {//affects terrain
                Dungeon.level.destroy(cell);
                GameScene.updateMap(cell);
            }
            Heap heap = Dungeon.level.heaps.get(cell);//explodes bombs and affects heaps nearby
            if (heap != null) heap.explode();
            Ballistica trajectory = new Ballistica(pos, enemy.pos, Ballistica.STOP_SOLID);
            int strength = 3;
            throwChar(enemy, trajectory, strength, false, true, getClass());
        }

        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        if (!(phase==3)){
        if (Random.Int( 4 ) == 0) {
            Buff.affect( enemy, Cripple.class, 2);
        }
        if (Random.Int( 5 ) == 0) {
            Buff.affect( enemy, Weakness.class, 3);
        }
        if (Random.Int( 10 ) == 0) {
            Buff.affect(enemy, Frost.class);
        }
        if (Random.Int( 10 ) == 0) {
            Buff.affect( enemy, Burning.class);
        }
        }

        return damage;
    }

    private int damageReceived = 0;

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (phase == 2) {
            damageReceived += damage;
            if (damageReceived>=150) {
                damageReceived = 0;
            ArrayList<Integer> candidates2 = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                    candidates2.add(pos + n);
                }
            }
            for (Mob mob : Dungeon.level.mobs) if (mob.alignment!=this.alignment){
                mob.enemy = null;
            }

            if (!candidates2.isEmpty()) {
                int posi;
                Shinobi shinobi = new Shinobi();
                shinobi.pos = pos;
                GameScene.add(shinobi);
                Dungeon.level.occupyCell(shinobi);
                posi = Random.element(candidates2);
                this.sprite.jump(pos, posi, new Callback() {
                    @Override
                    public void call() {
                        pos = posi;
                        speak("dodged", 0xFFFF00);
                    }
                });
                Sample.INSTANCE.play(Assets.Sounds.MISS);
                return 0;
            }
        }
        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {

        int hpBracket = HT / 20;

        int beforeHitHP = HP;

        int x = 1;
        if (HP - dmg <= 2000) x = 2;
        if (HP - dmg <= 1000) x = 3;
        if (HP - dmg <= 200) x = 4;
        if (phase != x) setPhase(x);
        super.damage(dmg, src);

        if (!BossHealthBar.isAssigned()){
            BossHealthBar.assignBoss( this );
        }

        if ((HP*3 <= HT)){
            BossHealthBar.bleed(true);
        }

        //tengu cannot be hit through multiple brackets at a time
        if ((beforeHitHP/hpBracket - HP/hpBracket) >= 2){
            HP = hpBracket * ((beforeHitHP/hpBracket)-1) + 1;
        }
    }

    @Override
    protected boolean getCloser( int target ) {
        teleport( );
        spend( 1);
        return true;
    }

    @Override
    public float attackDelay() {
        return super.attackDelay() * 0.9f;
    }

    private void teleport() {

        Ballistica route = new Ballistica( pos, target, Ballistica.PROJECTILE);
        if (route.dist == 0){
            return;
        }
        int cell = route.path.get(2);
        if (Dungeon.level.avoid[ cell ]){
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS25) {
                cell = route.collisionPos + n;
                if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
                    candidates.add( cell );
                }
            }
            if (candidates.size() != 0) cell = Random.element(candidates);
            else {
                return;
            }
        }
        ScrollOfTeleportation.appear( this, cell );
    }

    @Override
    public int attackSkill( Char target ) {
        return 20;
    }

    @Override
    public void die(Object cause) {
        Badges.validateEnemy(this);
        boolean lastTenguOnLevel10 = true;
        for (Mob mob : level.mobs){
            if (mob instanceof BossTengu && mob != this) {
                lastTenguOnLevel10 = false;
                break;
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE, 2, 0.8f);


        if (!lastTenguOnLevel10 || Dungeon.depth!=10 || cause == Arena.AmuletTower.class){
            super.die(cause);
        } else {
            hero.buffs().clear();
            hero.next();

            Camera.main.panFollow(BossTengu.this.sprite,2f);

            BossTengu.this.sprite.play(BossTengu.this.sprite.die, true);
            hero.die(Arena.AmuletTower.class);
            hero.sprite.play(hero.sprite.idle, true);

            boolean finalLastTenguOnLevel10 = lastTenguOnLevel10;
            this.sprite.jump(pos, pos, 0, 3, new Callback() {
                @Override
                public void call() {

                    BossTengu.this.die(Arena.AmuletTower.class);
                    if (finalLastTenguOnLevel10 && Dungeon.depth==10){
                        Dungeon.win(Amulet.class);
                        Dungeon.deleteGame( GamesInProgress.curSlot, true );
                        Game.switchScene( RankingsScene.class );
                    }

                }
            });
        }
    }


    private static final String PHASE = "phase";

    public static final String DAMAGE_RECEIVED = "damage_received";
    public static final String LOGTIMER = "logtimer";
    public static final String WARPTIMER = "warptimer";
    public static final String CALLFORHELPTIMER = "callforhelptimer";

    public static final String LOGTIME = "logtime";
    public static final String WARPTIME = "warptime";
    public static final String CALLFORHELPTIME = "callforhelptime";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(PHASE, phase);
        bundle.put(DAMAGE_RECEIVED, damageReceived);
        bundle.put(LOGTIMER, cloneTimer);
        bundle.put(WARPTIMER, warpTimer);
        bundle.put(CALLFORHELPTIMER, callForHelpTimer);
        bundle.put(LOGTIME, cloneTime);
        bundle.put(WARPTIME, warpTime);
        bundle.put(CALLFORHELPTIME, callForHelpTime);
    }
    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        phase = bundle.getInt(PHASE);
        damageReceived = bundle.getInt(DAMAGE_RECEIVED);
        cloneTimer = bundle.getInt(LOGTIMER);
        warpTimer = bundle.getInt(WARPTIMER);
        callForHelpTimer = bundle.getInt(CALLFORHELPTIMER);
        cloneTime = bundle.getInt(LOGTIME);
        warpTime = bundle.getInt(WARPTIME);
        callForHelpTime = bundle.getInt(CALLFORHELPTIME);

        if (state != SLEEPING) BossHealthBar.assignBoss(this);
        if ((HP*2 <= HT)) BossHealthBar.bleed(true);

    }
}
