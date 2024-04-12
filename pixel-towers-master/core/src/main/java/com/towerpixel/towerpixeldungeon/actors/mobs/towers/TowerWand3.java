package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerWand3Sprite;

public class TowerWand3 extends TowerWand1 {
    {
        HP = HT = 60;
        spriteClass = TowerWand3Sprite.class;

        viewDistance = 6;//DPT =29*0.6 = 17.4 DPT/C = 17.4/1350 =0.0129
        upgCount = 3;

        upgradeLevel = 11;

        cost = 1000;
        upgrade1Cost = 800;
        upgrade2Cost = 1000;
        upgrade3Cost = 1200;
        damageMin = 23;
        damageMax = 24;
    }
}
