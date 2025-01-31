package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class TowerCSpawning extends TowerNotliving{

    {
        properties.add(Property.INORGANIC);

        //the tower spawns mobs, stores the mobs it has in a HashSet, tracks the amount of them and their id's, lets you manage them
    }
    public int maxMinions;
    public int minionCooldown; public int minionCooldownLeft = 0;
    public int minionHP;
    public int minionDamageMax;
    public int minionDamageMin;
    public int minionAttackSkill;
    public int minionDefenseSkill;
    public int minionDR;

    //minionClass is is defined in spawnMinion(pos)!!!

    public boolean spawnsExcessMinions;//if they are spawned, they are affected by affectExcessMinion(). Otherwise they do not spawn at all.

    public ArrayList<TowerCSpawningMinion> minions = new ArrayList<>();


    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT , maxMinions, minionCooldown, minionHP, minionDamageMin, minionDamageMax, minionAttackSkill*10, minionDefenseSkill*20, minionDR, minions.size(), maxMinions, minionCooldownLeft));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

    public String infoWithoutCurrent() {
        StringBuilder info = new StringBuilder();
        info.append(info());
        info.delete(info.indexOf("Current number of"), info.indexOf("Turns to next spawn attempt:") + "Turns to next spawn attempt:".length() + Integer.toString(minionCooldownLeft).length()+4);
        return info.toString();
    }

    public void addMinion(TowerCSpawningMinion minion) {
        minions.add(minion);
        if (minions.size()>maxMinions){
            TowerCSpawningMinion theoldestminion = minions.get(0);
            affectExcessMinion(theoldestminion);
            removeMinion(theoldestminion);
        }
    }

    public void removeMinion(TowerCSpawningMinion minion) {
        minions.remove(minion);
        minion.momTower=null;
        minion.momID=-1;
    }

    public void affectExcessMinion(TowerCSpawningMinion minion){
        //defined in inheritors
    }

    @Override
    protected boolean getCloser(int target) {
        if (buff(Animated.class) !=null) {
            beckon(hero.pos);
            return super.getCloser( hero.pos );
        } else return true;
    }

    @Override
    protected boolean getFurther(int target) {
        if (buff(Animated.class) !=null) return super.getFurther(target); else return true;
    }
    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    public static final String MINIONCOOLDOWNLEFT = "minioncooldownleft";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MINIONCOOLDOWNLEFT, minionCooldownLeft);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        minionCooldownLeft = bundle.getInt(MINIONCOOLDOWNLEFT);
    }
    @Override
    public void die(Object cause) {
        ArrayList<TowerCSpawningMinion> concur = new ArrayList<>(minions);
        for (TowerCSpawningMinion m : concur){
            m.onMomtowerDeath();
        }
        super.die(cause);
    }
    public void spawnMinion(int pos) {
        //does something unique for each minion spawned, check inheritors.
    }

    @Override
    protected boolean act() {
        minionCooldownLeft--;
        if (minionCooldownLeft<=0) {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS8) {
                if (Char.findChar(pos + i) == null && level.passable[pos + i])
                    candidates.add(pos + i);
            }
            if (!candidates.isEmpty() && (minions.size()<maxMinions || spawnsExcessMinions)){
                spawnMinion(Random.element(candidates));
            }
            minionCooldownLeft = minionCooldown;
        }
        return super.act();
    }

    public abstract static class TowerCSpawningMinion extends Mob{

        public int momID;

        public TowerCSpawning momTower;

        boolean staysNearMomTower = false;

        boolean diesWithoutMomtower = false;

        public int minionHP;
        public int minionDamageMax;
        public int minionDamageMin;
        public int minionAttackSkill;
        public int minionDefenseSkill;
        public int minionDR;


        public TowerCSpawningMinion  (){
            super();
            viewDistance = 7;
        }


        @Override
        protected boolean act() {
            boolean delayedAct = super.act();

            momTower = (TowerCSpawning) Char.findById(momID);
            if (momTower!=null && !momTower.minions.contains(this)){
                momTower.addMinion(this);
            }


            if (momTower!=null && momTower.isAlive() && staysNearMomTower && Math.random()>0.4)this.beckon(momTower.pos);
            return delayedAct;
        }


        @Override
        public int attackSkill(Char target) {
            return minionAttackSkill;
        }

        @Override
        public int drRoll() {
            return super.drRoll() + Random.IntRange(0, minionDR);
        }

        @Override
        public int damageRoll() {
            return Random.IntRange(minionDamageMin, minionDamageMax);
        }

        @Override
        public void die(Object cause) {
            if (momTower!=null) {
                momTower.removeMinion(this);
            }
            super.die(cause);
        }

        public void onMomtowerDeath(){
            if (diesWithoutMomtower){
                die(this);
            }
        }
        public static final String MOMID = "momid";
        public static final String MINION_HT = "minionht";
        public static final String MINION_DAMAGEMAX = "miniondamagemax";
        public static final String MINION_DAMAGEMIN= "miniondamagemin";
        public static final String MINION_DR = "miniondr";
        public static final String MINION_ATTACKSKILL = "minionattackskill";
        public static final String MINION_DEFENSESKILL = "miniondefenseskill";
        public static final String STAYSNEARMOMTOWER = "staysnearmomtower";
        public static final String DIESWITHOUTMOMTOWER = "dieswithoutmomtower";



        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(MOMID, momID);
            bundle.put(MINION_HT, HT);
            bundle.put(MINION_ATTACKSKILL, minionAttackSkill);
            bundle.put(MINION_DEFENSESKILL, minionDefenseSkill);
            bundle.put(MINION_DAMAGEMIN, minionDamageMin);
            bundle.put(MINION_DAMAGEMAX, minionDamageMax);
            bundle.put(MINION_DR, minionDR);
            bundle.put(STAYSNEARMOMTOWER, staysNearMomTower);
            bundle.put(DIESWITHOUTMOMTOWER,diesWithoutMomtower);

        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            momID = bundle.getInt(MOMID);
            momTower = (TowerCSpawning) Char.findById(momID);
            if (momTower!=null) momTower.addMinion(this);

            HT = bundle.getInt(MINION_HT);
            minionAttackSkill = bundle.getInt(MINION_ATTACKSKILL);
            minionDefenseSkill = bundle.getInt(MINION_DEFENSESKILL);
            minionDamageMin = bundle.getInt(MINION_DAMAGEMIN);
            minionDamageMax = bundle.getInt(MINION_DAMAGEMAX);
            minionDR = bundle.getInt(MINION_DR);
            staysNearMomTower = bundle.getBoolean(STAYSNEARMOMTOWER);
            diesWithoutMomtower = bundle.getBoolean(DIESWITHOUTMOMTOWER);
        }
    }
}
