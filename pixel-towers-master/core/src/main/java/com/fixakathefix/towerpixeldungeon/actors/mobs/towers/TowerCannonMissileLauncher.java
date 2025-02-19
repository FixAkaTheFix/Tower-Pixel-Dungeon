package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.RocketSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCannonMissileLauncherSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TowerCannonMissileLauncher extends TowerCSpawning {

    public static int minionHP;
    public static int minionDamageMax;
    public static int minionDamageMin;
    public static int minionAttackSkill;
    public static int minionDefenseSkill;
    public static int minionDR;

    {
        HP = HT = 250;

        spriteClass = TowerCannonMissileLauncherSprite.class;

        viewDistance = 7;

        maxMinions = 5000000;
        minionCooldown = 1000000;
        minionCooldownLeft = 1000000;

        minionHP = 1;
        minionDamageMin = 20;
        minionDamageMax = 40;

        minionAttackSkill = 1000000;
        minionDefenseSkill = 6;
        minionDR = 0;

        cost = 2800;

        upgCount = 0;

        upgradeLevel = 15;

    }
    public static float damageExplosionMult = 0.5f;

    @Override
    protected boolean act() {
        if (enemy!=null) minionCooldownLeft = 0;
        return super.act();
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT, minionDamageMin, minionDamageMax, viewDistance));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }
    @Override
    public void spawnMinion(int pos) {
        RocketMinion minion = new RocketMinion();
        minion.alignment = this.alignment;
        minion.pos = pos;
        GameScene.add(minion);
        Dungeon.level.occupyCell(minion);
        minion.state = minion.WANDERING;
        CellEmitter.get(this.pos).burst(SmokeParticle.SPEW, 5);
        CellEmitter.get(minion.pos).burst(SmokeParticle.FACTORY, 10);
        spend(1f);
    }

    public static class RocketMinion extends Mob{

        {
            spriteClass = RocketSprite.class;
            HP = HT = minionHP;
            defenseSkill = minionDefenseSkill;
            viewDistance = 10;
            baseSpeed = 5f;
            flying = true;
        }
        private int countdown = 20;

        @Override
        protected boolean act() {
            countdown--;
            if (countdown<=0) {
                die(Hero.class);
                this.spend(1);
                return true;
            }
            return super.act();
        }

        @Override
        public int attackSkill(Char target) {
            return minionAttackSkill;
        }

        @Override
        public int damageRoll() {
            return Random.IntRange(minionDamageMin, minionDamageMax);
        }

        @Override
        public int attackProc( Char enemy, int damage ) {

            CellEmitter.center(enemy.pos).burst(BlastParticle.FACTORY, 30);
            CellEmitter.get(enemy.pos).burst(SmokeParticle.FACTORY, 4);
            Sample.INSTANCE.play( Assets.Sounds.BLAST);
            int cell;
            for (int i : PathFinder.NEIGHBOURS4){
                cell = enemy.pos + i;
                Char ch = Char.findChar(cell);
                if (ch!=null){
                    if (ch.alignment == Alignment.ALLY){
                        //friends receive 0 damage
                    } else if (ch != enemy){
                        ch.damage (Math.round(damageRoll()*damageExplosionMult) - ch.drRoll(), getClass());
                    };//damages foes nearby, with lowered damage
                }
                if (level.heroFOV[enemy.pos+i]) {
                    CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);

                }
            }
            super.die(enemy);
            return damage;
        }

        @Override
        public void die(Object cause) {
            CellEmitter.center(pos).burst(BlastParticle.FACTORY, 30);
            CellEmitter.get(pos).burst(SmokeParticle.FACTORY, 4);
            Sample.INSTANCE.play( Assets.Sounds.BLAST);
            int cell;
            for (int i : PathFinder.NEIGHBOURS4){
                cell = pos + i;
                Char ch = Char.findChar(cell);
                if (ch!=null){
                    if (ch.alignment == Alignment.ALLY){
                        //friends receive 0 damage
                    } else {
                        ch.damage (Math.round(damageRoll()*damageExplosionMult) - ch.drRoll(),getClass());
                    };//damages foes nearby, with lowered damage
                }
                if (level.heroFOV[pos+i]) {
                    CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
                }
            }
            super.die(cause);
        }
        public static final String COUNTDOWN = "countdown";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(COUNTDOWN, countdown);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            countdown = bundle.getInt(COUNTDOWN);
        }
    }
}

