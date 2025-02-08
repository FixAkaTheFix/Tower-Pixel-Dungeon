package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerWand3Sprite;

public class TowerWand3 extends TowerWand2 {
    {
        HP = HT = 120;
        spriteClass = TowerWand3Sprite.class;

        upgCount = 0;

        upgradeLevel = 11;

        cost = 1000;
        damageMin = 21;
        damageMax = 23;
    }
}
