package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard3Sprite;

public class TowerGuard3 extends TowerGuard2{

    {
        HP = HT = 300;
        spriteClass = TowerGuard3Sprite.class;

        cost = 1050;
        damageMin = 8;
        damageMax = 12;
        upgradeLevel = 16;
        defMin = 1;
        defMax = 9;
        upgCount = 2;
        upgrade1Cost = 1500;
        upgrade2Cost = 800;
        regenNum = 20;
    }

    @Override
    public int attackSkill(Char target) {
        return 12;
    }
}
