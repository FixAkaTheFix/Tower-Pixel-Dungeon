package com.towerpixel.towerpixeldungeon.actors.buffs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;

public class RunningToReport extends Dread {
    {
        announced = false;
    }

    @Override
    public boolean act() {

        if (!Dungeon.level.heroFOV[target.pos]
                && Dungeon.level.distance(target.pos, hero.pos) >= 6) {
            if (target instanceof Mob){
                ((Mob) target).EXP /= 2;
            }
            target.destroy();
            target.sprite.killAndErase();
            target.die(RunningToReport.class);
        }

        spend(TICK);
        return true;
    }

    @Override
    public void recover() {
    }

    @Override
    public int icon() {
        return BuffIndicator.RUNNINGTOREPORT;
    }
}
