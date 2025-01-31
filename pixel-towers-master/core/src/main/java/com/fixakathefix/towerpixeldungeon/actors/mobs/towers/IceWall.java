package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.IceWallSprite;
import com.fixakathefix.towerpixeldungeon.sprites.walls.TowerWall1Sprite;

public class IceWall extends TowerCWall {
    {
        spriteClass = IceWallSprite.class;
        HP = HT = 50;
        cost = 100;
        upgrade1Cost = 150;
        damageMin = 0;
        damageMax = 1;
        defMin = 0;
        defMax = 1;
        upgradeLevel = 3;
        state = PASSIVE;
        upgCount = 0;
        sellable = false;
        properties.add(Property.ICY);
        properties.add(Property.INORGANIC);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (distance(enemy) <= 1) Buff.prolong(enemy, Chill.class,2);
        return super.defenseProc(enemy, damage);
    }
}
