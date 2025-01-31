package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.blobs.StenchGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.watabou.utils.Random;

public abstract class CampRat extends Rat {
    {
        alignment = Alignment.ALLY;


        immunities.add(Paralysis.class);
        immunities.add(StenchGas.class);

        state = HUNTING;

        viewDistance = 11;

    }

    public int damageMin;
    public int damageMax;
    public int defMin;
    public int defMax;

    public int cost;
    @Override
    protected boolean act() {
        int posofleader = -1;

       for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof CampRatLeader &&
                    mob !=this &&
                    (Dungeon.level.trueDistance(mob.pos, this.pos) <= Dungeon.level.trueDistance(posofleader, this.pos) || posofleader==-1)){
                posofleader = mob.pos;
            }
        }

       if (posofleader!=-1 && !(this instanceof CampRatLeader)) beckon(posofleader);
        return super.act();
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(defMin, defMax);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(damageMin, damageMax);
    }
}
