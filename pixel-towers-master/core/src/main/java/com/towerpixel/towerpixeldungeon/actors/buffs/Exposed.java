package com.towerpixel.towerpixeldungeon.actors.buffs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;

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
