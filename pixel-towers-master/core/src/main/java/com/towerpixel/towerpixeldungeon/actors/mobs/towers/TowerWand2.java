package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerWand2Sprite;

public class TowerWand2 extends TowerWand1 {
    {
        HP = HT = 50;
        spriteClass = TowerWand2Sprite.class;

        viewDistance = 6;//DPT =13*0.6 = 7.8 DPT/C = 7.8/350 = 0,0223

        cost = 450;
        upgrade1Cost = 550;
        damageMin = 10;
        damageMax = 11;
        upgradeLevel = 10;
    }
}
