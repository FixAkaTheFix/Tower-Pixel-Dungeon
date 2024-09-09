package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.messages.Messages;

public class TowerCWall extends TowerNotliving{

    {
    HP = HT = 50;
    cost = 50;
    upgrade1Cost = 100;
    damageMin = 0;
    damageMax = 1;
    defMin = 1;
    defMax = 2;
    properties.add(Property.IMMOVABLE);
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }

    @Override
    protected boolean act() {
        sprite.linkVisuals(this);
        super.act();
        sprite.linkVisuals(this);
        return true;
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT , defMin, defMax));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

    @Override
    protected boolean canAttack(Char enemy) {return false;
    }

    @Override
    public void damage(int dmg, Object src) {
        sprite.linkVisuals(this);//check sprite.
        super.damage(dmg, src);
        sprite.linkVisuals(this);//check sprite.
    }


}
