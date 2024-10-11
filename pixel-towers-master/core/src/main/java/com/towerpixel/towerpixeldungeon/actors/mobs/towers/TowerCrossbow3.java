package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow3Sprite;

public class TowerCrossbow3 extends TowerCrossbow2{

    {
        HP = HT = 80;
        spriteClass = TowerCrossbow3Sprite.class;

        baseAttackDelay = 0.5f;
        cost = 1000;
        upgCount=2;

        upgradeLevel = 11;

        upgrade1Cost = 1000;
        upgrade2Cost = 1000;
        damageMin = 5;
        damageMax = 11;
    }
}
