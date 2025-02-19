package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerLightning3Sprite;

public class TowerLightning3 extends TowerLightning2 {
    {
        HP = HT = 180;
        spriteClass = TowerLightning3Sprite.class;

        attackRange = 3;
        baseAttackDelay = 5f;

        cost = 1300;
        upgCount = 0;
        damageMin = 35;
        damageMax = 95;
        upgradeLevel = 12;
        maxChainLength = 4;
    }
}
