package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.messages.Messages;

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
    protected boolean act() {
        sprite.linkVisuals(this);
        if (buff(Animated.class) !=null) beckon(hero.pos);
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

    @Override
    protected boolean getCloser(int target) {
        if (buff(Animated.class) !=null) {
            if (Dungeon.level.distance(pos, hero.pos)>2) return super.getCloser(hero.pos);
            else {
                return super.getCloser( target );
            }
        } else return true;
    }

    @Override
    protected boolean getFurther(int target) {
        if (buff(Animated.class) !=null) return super.getFurther(target); else return true;
    }


}
