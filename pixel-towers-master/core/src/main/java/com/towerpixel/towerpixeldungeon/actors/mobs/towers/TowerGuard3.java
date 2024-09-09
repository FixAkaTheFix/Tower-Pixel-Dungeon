package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard3Sprite;

public class TowerGuard3 extends TowerGuard2{

    {
        HP = HT = 200;
        spriteClass = TowerGuard3Sprite.class;

        cost = 700;
        damageMin = 7;
        damageMax = 10;
        upgradeLevel = 16;
        defMin = 1;
        defMax = 9;
        upgCount = 2;
        upgrade1Cost = 1100;
        upgrade2Cost = 1100;
        regenNum = 15;
    }
}
