package com.towerpixel.towerpixeldungeon.actors.mobs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.GamesInProgress;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.Blob;
import com.towerpixel.towerpixeldungeon.actors.blobs.CorrosiveGas;
import com.towerpixel.towerpixeldungeon.actors.blobs.StenchGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Ooze;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vertigo;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.items.Amulet;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.scenes.RankingsScene;
import com.towerpixel.towerpixeldungeon.sprites.BossOozeSprite;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossOoze extends Mob {

    {
        spriteClass = BossOozeSprite.class;
        viewDistance = 10;


        HP = HT = 4000;
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
            minDamage = 8;
            maxDamage = 12;
        }
        if (phase == 2) {
            attackRange = 5;
            attackDelayMult = 1f;
            minDamage = 9;
            maxDamage = 15;
        }
        if (phase == 3) {
            attackRange = 4;
            attackDelayMult = 0.5f;
            minDamage = 7;
            maxDamage = 13;
        }
        if (phase == 4) {
            baseSpeed = 4f;
            attackRange = 2;
            attackDelayMult = 0.5f;
            minDamage = 50;
            maxDamage = 70;
            viewDistance = 4;
        }
    }
    public void setPhase(int phas) {
        phase = phas;
        //1 = simple ranged attacks, includes ABthrowslime
        //2 = spawns slimes, jumps, which move all the towers and friendly mobs, damage EVERYTHING on the field
        //3 = small caustic spits, only 100 HP wide phase
        //4 = blind melee rage
        if (phase == 1) {
            attackRange = 6;
            attackDelayMult = 1f;
            minDamage = 8;
            maxDamage = 12;
        }
        if (phase == 2) {
            attackRange = 5;
            attackDelayMult = 1f;
            minDamage = 9;
            maxDamage = 15;
            abSummon();
        }
        if (phase == 3) {
            attackRange = 4;
            attackDelayMult = 0.5f;
            minDamage = 7;
            maxDamage = 13;
        }
        if (phase == 4) {
            baseSpeed = 4f;
            attackRange = 2;
            attackDelayMult = 0.5f;
            minDamage = 50;
            maxDamage = 70;
            viewDistance = 4;
            speak("ENRAGED", CharSprite.WARNING);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.3f, 0.5f);
        }

        CellEmitter.center(pos).burst(BossOozeSprite.OozeParticle.FACTORY, phase * 20);
    }

    int jumpCooldown = 2;
    boolean imagesAdded = false;


    public void addImages(){
        for (BossImage image : images) {
            image.assignedMob = this;
            GameScene.add(image);
        }
    }

    @Override
    public void notice() {
        Dungeon.level.seal();
        super.notice();
    }

    public void moveImages(){
        int i = 0;
        for (BossImage image : images) {
            image.pos=pos+PathFinder.NEIGHBOURS8[i];
            i++;
        }
    }
    public void positionImages(){
        for (BossImage image : images) {
            Dungeon.level.occupyCell(image);//changes the images position to its "pos"
            GameScene.add(image); //this moves both the image and its sprite
        }
    }

    public void deleteImages(){
        for (BossImage image : images) {
            image.pos = 1;//temporarily ports all images to pos 1. Does not affect the gameplay anyhow, because after the goo ends its move, they come back.
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

        spend(0.5f);
        GameScene.updateFog(pos, 3);
        jumpTimer++;
        callForHelpTimer++;
        setStats();
        if (jumpTimer >= jumpTime && phase!=4) try {
            jumpTimer=0;
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS25) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null && Dungeon.level.passable[pos + n - level.width()]) {
                    candidates.add(pos + n);
                }
            }
            int newpos = Random.element(candidates);
            sprite.visible = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[newpos];
            sprite.jump(pos, newpos, 20, 0.3f, new Callback() {
                @Override
                public void call() {
                    Dungeon.level.occupyCell(BossOoze.this);
                    pos = newpos;
                    next();
                }
            });
            return false;

        } catch (Exception ignored) {}


        if (!imagesAdded) {
            moveImages();//sets images' pos'
            addImages();//adds images to the game
            positionImages();//puts images to positions
            imagesAdded = true;
        }
        deleteImages();//make some free space for the ooze to move, temporarily

        if (phase==1 && Math.random()>0.5 && enemy!=null) {
            abThrowSlime(new Callback() {
                @Override
                public void call() {
                    ArrayList<Integer> candidates = new ArrayList<>();
                    //all cells around the hero
                    for (int i : PathFinder.NEIGHBOURS8){
                        if (Dungeon.level.passable[pos+i]) candidates.add(hero.pos+i);
                    }
                    if (!candidates.isEmpty()) {
                        int slimepos = Random.element(candidates);
                        CausticSlime slime = new CausticSlime();
                        slime.pos = pos;
                        GameScene.add(slime);
                        slime.sprite.jump(pos, slimepos, 30, 0.9f, new Callback() {
                            @Override
                            public void call() {

                                Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG, 0.5f, 1.5f);
                                Camera.main.shake(5, 0.3f);
                                sprite.idle();
                                Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                slime.pos = slimepos;
                                Dungeon.level.occupyCell(slime);
                                next();
                            }
                        });
                    }
                }
            });
            next();
        }

        if (phase == 2&& Math.random()>0.6) {
            jumpCooldown--;

            if (jumpCooldown <= 0) {

                jumpCooldown = 2;

                sprite.jump(pos, pos, 100, 0.5f, new Callback() {
                    @Override
                    public void call() {
                        Sample.INSTANCE.play(Assets.Sounds.HIT_CRUSH);
                        Camera.main.shake(40, 0.5f);
                        sprite.idle();
                        Sample.INSTANCE.play(Assets.Sounds.BLAST);
                        for (int i : PathFinder.NEIGHBOURS25) if (Dungeon.level.passable[pos + i])
                            CellEmitter.center(pos).burst(BlastParticle.FACTORY, 5);

                        for (int i = 0; i<Dungeon.level.width()*Dungeon.level.height();i++)
                            try {
                                Char ch = Actor.findChar(i);
                                if (ch != null && ch!=BossOoze.this&& !(ch instanceof BossImage)) {

                                    if (ch.alignment != Char.Alignment.ALLY)
                                        ch.damage(damageRoll(), this);
                                    else {
                                        Buff.affect(ch, Vertigo.class, 2);
                                        ch.damage(Random.Int(1, 3 ), this);
                                    }
                                    Ballistica trajectory = new Ballistica(pos, ch.pos, Ballistica.STOP_SOLID);
                                    int strength = 8;
                                    throwChar(ch, trajectory, strength, false, true, getClass());

                                }

                            } catch (Exception ignored) {
                            }
                        hero.spendAndNextConstant(1f);
                    }
                });
                moveImages();
                positionImages();
                collisionDamageCheck();
                return true;
            }
        } else if (Math.random()>0.2 && phase == 3) {
            abChargeShot();
            spend(1);
            next();
        }
        super.act();//we need to do the usual acting without returning anything;
        moveImages();
        positionImages();
        collisionDamageCheck();
        return true;
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
                    CellEmitter.floor(pos+i).start(ElmoParticle.FACTORY, 0.1f, 10);
                    GameScene.add(slime);
                }
            } catch (Exception ignored) {}
    }

    public void abThrowSlime(Callback callback) {

    }

    public void abChargeShot() {
        Ballistica trajectory = new Ballistica(pos, enemy.pos, Ballistica.STOP_TARGET);

        for (int i : trajectory.subPath(0, trajectory.dist)){
            CellEmitter.floor(i).start(BossOozeSprite.OozeParticle.FACTORY,0.5f, 15);
            GameScene.add(Blob.seed(i, 20, StenchGas.class));
            GameScene.add(Blob.seed(i, 5, CorrosiveGas.class));
            Dungeon.level.map[i] = Terrain.WATER;
            if (Actor.findChar(i)!=null) Buff.affect(Actor.findChar(i), Ooze.class).set(100);
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
    public int damageRoll() {
        return Random.NormalIntRange(minDamage, maxDamage);
    }

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
        if (HP - dmg <= 3000) x = 2;
        if (HP - dmg <= 2000) x = 3;
        if (HP - dmg <= 1000) x = 4;
        if (phase != x) setPhase(x);
        super.damage(dmg, src);
    }

    @Override
    public void die(Object cause) {
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


        if (!lastOozeOnLevelfive || Dungeon.depth!=5 || cause == Arena.AmuletTower.class){
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


                    if (finalLastOozeOnLevelfive && Dungeon.depth==5){
                        Dungeon.win(Amulet.class);
                        Dungeon.deleteGame( GamesInProgress.curSlot, true );
                        Game.switchScene( RankingsScene.class );
                        Badges.validateBossSlain();
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
        images = new BossImage[]{
                new BossImage(),new BossImage(),new BossImage(),new BossImage(),new BossImage()
        };
        moveImages();//sets images' pos'
        addImages();//adds images to the game. Necessary for the Boss to restore its hitbox
        phase = bundle.getInt(PHASE);
        callForHelpTimer = bundle.getInt(CALLFORHELPTIMER);
        jumpTimer = bundle.getInt(JUMPTIMER);
        if (state != SLEEPING) BossHealthBar.assignBoss(this);
        if ((HP*2 <= HT)) BossHealthBar.bleed(true);

    }
}