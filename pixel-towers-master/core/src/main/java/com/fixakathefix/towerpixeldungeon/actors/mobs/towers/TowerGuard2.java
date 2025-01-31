package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard2Sprite;

public class TowerGuard2 extends TowerGuard1{

    {
        HP = HT = 150;
        spriteClass = TowerGuard2Sprite.class;

        cost = 450;
        damageMin = 5;
        damageMax = 8;
        upgradeLevel = 9;
        defMin = 0;
        defMax = 7;
        upgrade1Cost = 600;
        regenNum = 10;
    }

    @Override
    public int attackSkill(Char target) {
        return 11;
    }
}
