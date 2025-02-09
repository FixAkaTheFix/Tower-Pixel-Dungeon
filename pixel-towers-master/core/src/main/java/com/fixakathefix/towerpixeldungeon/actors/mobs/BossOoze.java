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
import com.fixakathefix.towerpixeldungeon.actors.blobs.CorrosiveGas;
import com.fixakathefix.towerpixeldungeon.actors.blobs.StenchGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Ooze;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.SubAmuletTower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Arena5;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.scenes.RankingsScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossOozeSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BossOoze extends Mob {

    {
        spriteClass = BossOozeSprite.class;
        viewDistance = 10;


        HP = HT = 3333;
        defenseSkill = 5;

        EXP = 100;

        properties.add(Property.BOSS);
        properties.add(Property.DEMONIC);
        properties.add(Property.ACIDIC);
        properties.add(Property.IMMOVABLE);
        immunities.add(Paralysis.class);
        immunities.add(StenchGas.class);

    }

    int phase = 1;
    float attackDelayMult = 3f;
    int minDamage = 8;
    int maxDamage = 12;
    int attackRange = 6;

    BossImage[] images = new BossImage[]{
            new BossImage(),new BossImage(),new BossImage(),new BossImage(),new BossImage()
    };


    public void setStats() {
        if (phase == 1) {
            attackRange = 6;
            attackDelayMult = 1f;
            minDamage = 5;
            maxDamage = 10;
        }
        if (phase == 2) {
            attackRange = 5;
            attackDelayMult = 1f;
            minDamage = 7;
            maxDamage = 13;
        }
        if (phase == 3) {
            attackRange = 4;
            attackDelayMult = 0.5f;
            minDamage = 6;
            maxDamage = 12;
        }
        if (phase == 4) {
            baseSpeed = 4f;
            attackRange = 2;
            attackDelayMult = 0.5f;
            minDamage = 50;
            maxDamage = 70;
            viewDistance = 4;
        }
        if (level!=null&&level.mode == WndModes.Modes.HARDMODE){
            minDamage*=2;
            maxDamage*=3;
        }
    }
    public void setPhase(int phas) {
        phase = phas;
        //1 = simple ranged attacks, includes ABthrowslime
        //2 = spawns slimes, jumps, which move all the towers and friendly mobs, damage EVERYTHING on the field
        //3 = small caustic spits, only 100 HP wide phase
        //4 = blind melee rage
        setStats();

        if (phase == 2) {
            abSummon();
        }
        if (phase == 4) {
            speak("ENRAGED", CharSprite.WARNING);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.3f, 0.5f);
        }

        CellEmitter.center(pos).burst(BossOozeSprite.OozeParticle.FACTORY, phase * 20);
    }

    int jumpCooldown = 2;
    boolean imagesAdded = false;


    public void addImages(){
        deleteImages();//to add images we must delete any existing ones even if there is none
        images = new BossImage[]{
                new BossImage(), new BossImage(), new BossImage(),new BossImage(), new BossImage()
        };
        for (BossImage image : images) {
            image.assignedMob = this;
            GameScene.add(image);
        }
        moveImages();
    }

    @Override
    public void notice() {
        Dungeon.level.seal();
        super.notice();
    }

    public void moveImages(){
        int i = 0;
        for (BossImage image : images) {
            try{
                image.sprite.move(image.pos, pos+PathFinder.NEIGHBOURS8[i] );
            } catch (Exception ignored){} // in some cases, in the beginning for example,
            image.pos=pos+PathFinder.NEIGHBOURS8[i];

            if (level!=null)level.occupyCell(image);
            i++;
        }
    }


    public void deleteImages(){
        HashSet<BossImage> allimages = new HashSet<>(Arrays.asList(images));
        for (BossImage image : allimages) {
            image.die(image);//temporarily ports all images to pos 1. Does not affect the gameplay anyhow, because after the goo ends its move, they come back.
        }
    }

    public void collisionDamageCheck(){
        ArrayList<Integer> candidates = new ArrayList<>();
        //all cells around the goo
        candidates.add(pos+1);
        candidates.add(pos);
        candidates.add(pos-1);
        candidates.add(pos+1-Dungeon.level.width());
        candidates.add(pos-Dungeon.level.width());
        candidates.add(pos-1-Dungeon.level.width());


        for (int i : candidates){
            if (Actor.findChar(i)!=null&&!(Actor.findChar(i) instanceof BossImage)&&!(Actor.findChar(i) instanceof BossOoze )){
                Actor.findChar(i).damage(Random.Int(20,40),this);
                if (Actor.findChar(i) instanceof Tower) {
                    CellEmitter.center(Actor.findChar(i).pos).burst(BlastParticle.FACTORY, 10);
                    Actor.findChar(i).die(this);
                }
            }
            if (level.map[i] == Terrain.STATUE) Level.set(i, Terrain.EMBERS);
            GameScene.updateMap(i);
        }
    }

    private final int callForHelpTime=8;
    private int callForHelpTimer=0;

    private final int jumpTime=7;
    private int jumpTimer=0;

    @Override
    protected boolean act() {
        if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "act() start");
        moveImages();
        if (!imagesAdded) {
            addImages();//adds images to the game
            imagesAdded = true;
        }
        deleteImages();//make some free space for the ooze to move
        GameScene.updateFog(pos, 3);
        jumpTimer++;
        callForHelpTimer++;
        setStats();
        if (jumpTimer >= jumpTime && phase!=4 && !level.cellAdjacentToBorderCells(pos)) {
            if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "jumpTimer ability start");
            jumpTimer = 0;
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS25) {
                int cell = pos + n;
                if (Dungeon.level.passable[cell] &&
                        Dungeon.level.passable[cell - level.width()] &&
                        Dungeon.level.passable[cell - level.width()] &&
                        Actor.findChar(cell) == null ) {
                    candidates.add(cell);
                }
            }
            if (level instanceof Arena5) ((Arena)level).deploymobs(8055, Arena.Direction.TOORIGHT, 1);
            if (!candidates.isEmpty()) {
                int newpos = Random.element(candidates);
                sprite.visible = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[newpos];
                sprite.jump(pos, newpos, 20, 0.4f, new Callback() {
                    @Override
                    public void call() {
                        pos = newpos;
                        Dungeon.level.occupyCell(BossOoze.this);
                        addImages();
                        spend(1.01f);
                        next();
                        if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "jumpTimer ability call ended");
                    }
                });
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "jumpTimer ability ended");
                addImages();
                return false;
            } else {
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "reached general act due to no candidates for the small jump");
                boolean x = super.act();//we need to do the usual acting without returning anything;
                addImages();
                collisionDamageCheck();
                return x;
            }
        } else if (phase==1 && Math.random()>0.75 && enemy!=null) {
            if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 1 action started");

            ArrayList<Integer> candidates = new ArrayList<>();
            //all cells around the enemy
            for (int i : PathFinder.NEIGHBOURS25){
                if (Dungeon.level.passable[enemy.pos+i]) candidates.add(enemy.pos+i);
            }
            if (level instanceof Arena5) ((Arena)level).deploymobs(8055, Arena.Direction.TOORIGHT, 1);
            if (!candidates.isEmpty()) {
                int slimepos = Random.element(candidates);
                CausticSlime slime = new CausticSlime();
                //slime.spend(1f);
                slime.state = slime.WANDERING;
                slime.pos = pos;
                GameScene.add(slime);
                slime.spend(1.011f);
                spend(1.01f);
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 1 slimeShot jump started");
                slime.sprite.jump(pos, slimepos, Random.Int(30, 70), 0.6f, new Callback() {
                    @Override
                    public void call() {
                        Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.5f, 1.5f);
                        Camera.main.shake(5, 0.3f);
                        sprite.idle();
                        Sample.INSTANCE.play(Assets.Sounds.BLAST);
                        slime.pos = slimepos;
                        Dungeon.level.occupyCell(slime);
                        addImages();
                        next();
                        if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 1 slimeShot jump ended");
                    }
                });
                return false;
            } else {
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 1 slimeShot no candidates");
                spend(1.01f);
                addImages();
                return true;
            }

        } else if (phase == 2 && Math.random()>0.6) {
            if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 2 action started");
            jumpCooldown--;

            if (jumpCooldown <= 0) {

                jumpCooldown = 2;
                spend(1.01f);
                sprite.jump(pos, pos, 100, 0.9f, new Callback() {
                    @Override
                    public void call() {
                        if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 2 call started");
                        Sample.INSTANCE.play(Assets.Sounds.HIT_CRUSH);
                        Camera.main.shake(40, 0.5f);
                        sprite.idle();
                        Sample.INSTANCE.play(Assets.Sounds.BLAST);
                        for (int i : PathFinder.NEIGHBOURS25) if (Dungeon.level.passable[pos + i])
                            CellEmitter.center(pos).burst(BlastParticle.FACTORY, 5);

                        for (int i = 0; i<Dungeon.level.width()*Dungeon.level.height();i++) {
                                Char ch = Actor.findChar(i);
                                if (ch != null && !ch.properties().contains(Property.IMMOVABLE)) {

                                    if (ch.alignment != Char.Alignment.ALLY)
                                        ch.damage(damageRoll(), this);
                                    else {
                                        ch.damage(Random.Int(1, 3 ), this);
                                    }
                                    Ballistica trajectory = new Ballistica(pos, ch.pos, Ballistica.STOP_SOLID);
                                    int strength = 8;
                                    throwChar(ch, trajectory, strength, false, true, getClass());

                                }
                            }
                        addImages();
                        collisionDamageCheck();
                        next();
                        if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 2 call ended");
                    }
                });
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 2 action ended");
                return false;
            } else {
                if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "reached general act due to cooldown");
                boolean x = super.act();//we need to do the usual acting without returning anything;
                addImages();
                collisionDamageCheck();
                return x;
            }
        } else if (Math.random()>0.2 && phase == 3) {
            if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "phase 3 action started");
            abChargeShot();
            spend(1.01f);
            addImages();
            collisionDamageCheck();
            next();
            return true;
        } else { if (DeviceCompat.isDebug()) DeviceCompat.log("OOZE", "reached general act last");
        boolean x = super.act();//we need to do the usual acting without returning anything;
            addImages();
        collisionDamageCheck();
           return x;
        }
    }

    public void abSummon() {
        ArrayList<Integer> candidates = new ArrayList<>();
        //all cells around the goo, yup, crappy, but that is a one-time thing.
        candidates.add(pos+2);
        candidates.add(pos+2-Dungeon.level.width());
        candidates.add(pos-2);
        candidates.add(pos-2-Dungeon.level.width());
        candidates.add(pos-Dungeon.level.width());
        candidates.add(pos+1+Dungeon.level.width());
        candidates.add(pos-1+Dungeon.level.width());
        candidates.add(pos+2+Dungeon.level.width());
        candidates.add(pos-2+Dungeon.level.width());
        candidates.add(pos+2*Dungeon.level.width());

        candidates.add(pos-2*Dungeon.level.width());
        candidates.add(pos+1-2*Dungeon.level.width());
        candidates.add(pos-1-2*Dungeon.level.width());
        candidates.add(pos+2-2*Dungeon.level.width());
        candidates.add(pos-2-2*Dungeon.level.width());
        for (int i : PathFinder.NEIGHBOURS25)
            try {
                if (candidates.contains(pos + i) && Dungeon.level.passable[pos + i] && Actor.findChar(pos + i) == null) {
                    CausticSlime slime = new CausticSlime();
                    slime.pos = pos + i;
                    slime.state = slime.HUNTING;
                    CellEmitter.floor(pos+i).start(ElmoParticle.FACTORY, 0.1f, 10);
                    GameScene.add(slime);
                    slime.spend(1.011f);
                }
            } catch (Exception ignored) {}
    }



    public void abChargeShot() {
        if (enemy!=null){
            Ballistica trajectory = new Ballistica(pos, enemy.pos, Ballistica.STOP_TARGET);
            if (level instanceof Arena5) ((Arena)level).deployMobs(8055);
            for (int i : trajectory.subPath(0, trajectory.dist)){
                CellEmitter.floor(i).start(BossOozeSprite.OozeParticle.FACTORY,0.5f, 15);
                GameScene.add(Blob.seed(i, 20, StenchGas.class));
                GameScene.add(Blob.seed(i, 5, CorrosiveGas.class));
                if (Actor.findChar(i)!=null) Buff.affect(Actor.findChar(i), Ooze.class).set(100);
            }
        }
    }


    @Override
    public float attackDelay() {
        return super.attackDelay() * attackDelayMult;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Ooze.class).set(15);
        return super.attackProc(enemy, damage);
    }

    @Override
    public int damageRoll() {return Random.NormalIntRange(minDamage, maxDamage);}

    @Override
    public int attackSkill(Char target) {
        return 15;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        ArrayList<Integer> candidates = new ArrayList<>();
        //all cells around the goo
        candidates.add(pos+2);
        candidates.add(pos+2-Dungeon.level.width());
        candidates.add(pos-2);
        candidates.add(pos-2-Dungeon.level.width());
        candidates.add(pos-Dungeon.level.width());
        candidates.add(pos+1+Dungeon.level.width());
        candidates.add(pos-1+Dungeon.level.width());
        candidates.add(pos+2+Dungeon.level.width());
        candidates.add(pos-2+Dungeon.level.width());
        candidates.add(pos+2*Dungeon.level.width());

        candidates.add(pos-2*Dungeon.level.width());
        candidates.add(pos+1-2*Dungeon.level.width());
        candidates.add(pos-1-2*Dungeon.level.width());
        candidates.add(pos+2-2*Dungeon.level.width());
        candidates.add(pos-2-2*Dungeon.level.width());

        if (attackRange>2) return (Dungeon.level.distance(this.pos, enemy.pos) <= attackRange);
        else return(candidates.contains(enemy.pos));
    }
    @Override
    public void damage(int dmg, Object src) {
        if (!BossHealthBar.isAssigned()){
            BossHealthBar.assignBoss( this );
        }

        if ((HP*3 <= HT)){
            BossHealthBar.bleed(true);
        }
        for (BossImage imag : images){
            imag.assignedMob = this;
        }
        if (dmg >= 10) {
            //does not take a lot more than 9 damage
            dmg = 9 + (int) (Math.sqrt(dmg - 9));
        }
        int x = 1;
        if (HP - dmg <= 2600) x = 2;
        if (HP - dmg <= 1300) x = 3;
        if (HP - dmg <= 750) x = 4;
        if (phase != x) setPhase(x);
        if (level.mode == WndModes.Modes.HARDMODE){
            CausticSlime slime = new CausticSlime();
            slime.pos = this.pos;
            slime.state = slime.HUNTING;
            GameScene.add(slime);
            slime.spend(1.011f);
            WandOfBlastWave.throwChar(slime, new Ballistica(
                    pos, Random.Int(0, level.width()*level.height()),
                    Ballistica.PROJECTILE),

                    5, false,
                    true,
                    src.getClass());
        }
        super.damage(dmg, src);
    }

    @Override
    public void die(Object cause) {
        Badges.validateEnemy(this);
        boolean lastOozeOnLevelfive = true;
        for (Mob mob : level.mobs){
            if (mob instanceof BossOoze && mob != this) {
                lastOozeOnLevelfive = false;
                break;
            }
        }
        for (BossImage image : images) {
            image.die(this);
        }
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE, 2, 0.2f);


        if (!lastOozeOnLevelfive || !(level instanceof Arena5) || cause == Arena.AmuletTower.class){
            super.die(cause);
        } else {
            hero.buffs().clear();
            hero.next();

            Camera.main.panFollow(BossOoze.this.sprite,2f);

            BossOoze.this.sprite.die();
            hero.die(Arena.AmuletTower.class);
            hero.sprite.play(hero.sprite.idle, true);

            boolean finalLastOozeOnLevelfive = lastOozeOnLevelfive;
            this.sprite.jump(pos, pos, 0, 3, new Callback() {
                @Override
                public void call() {

                    BossOoze.this.die(cause);


                    if (finalLastOozeOnLevelfive && level instanceof Arena5){
                        Dungeon.win(Amulet.class);
                        Dungeon.deleteGame( GamesInProgress.curSlot, true );
                        Game.switchScene( RankingsScene.class );
                    }

                }
            });
        }
    }
    private final String PHASE = "phase";
    private final String CALLFORHELPTIMER = "callforhelptimer";

    private final String JUMPTIMER = "jumptimer";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(PHASE, phase);
        bundle.put(CALLFORHELPTIMER,callForHelpTimer);
        bundle.put(JUMPTIMER, jumpTimer);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        addImages();//adds images to the game. Necessary for the Boss to restore its hitbox
        phase = bundle.getInt(PHASE);
        callForHelpTimer = bundle.getInt(CALLFORHELPTIMER);
        jumpTimer = bundle.getInt(JUMPTIMER);
        if (state != SLEEPING) BossHealthBar.assignBoss(this);
        if ((HP*2 <= HT)) BossHealthBar.bleed(true);
        setStats();

    }
}