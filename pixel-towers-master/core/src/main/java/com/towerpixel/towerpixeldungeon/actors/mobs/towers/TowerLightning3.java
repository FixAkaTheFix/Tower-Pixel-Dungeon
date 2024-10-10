package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerLightning3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerWand1Sprite;

public class TowerLightning3 extends TowerLightning2 {
    {
        HP = HT = 100;
        spriteClass = TowerLightning3Sprite.class;

        attackRange = 3;
        baseAttackDelay = 4f;

        cost = 1300;
        upgCount = 0;
        damageMin = 45;
        damageMax = 110;
        upgradeLevel = 3;
        maxChainLength = 8;
    }
}
