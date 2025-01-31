package com.fixakathefix.towerpixeldungeon.actors.buffs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

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
