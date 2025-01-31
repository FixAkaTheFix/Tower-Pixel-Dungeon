package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Exposed extends Buff {//Makes all enemies be drawn to your position wherever you are

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.EXPOSED;
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {
            spend(TICK);
            if (!Dungeon.level.mobs.isEmpty()) for(Mob mob : Dungeon.level.mobs){
                mob.beckon(target.pos);
            }
        } else {
            detach();
        }
        return true;
    }
}
