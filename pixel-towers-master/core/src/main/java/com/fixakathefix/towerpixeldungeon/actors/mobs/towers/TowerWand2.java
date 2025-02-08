package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerWand2Sprite;

public class TowerWand2 extends TowerWand1 {
    {
        HP = HT = 70;
        spriteClass = TowerWand2Sprite.class;

        cost = 450;
        upgrade1Cost = 550;//dpt/c = 0.00703
        damageMin = 9;
        damageMax = 10;
        upgradeLevel = 10;
    }
}
