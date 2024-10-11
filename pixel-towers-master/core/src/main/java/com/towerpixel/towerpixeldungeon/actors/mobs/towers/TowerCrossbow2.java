package com.towerpixel.towerpixeldungeon.actors.mobs.towers;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow2Sprite;

public class TowerCrossbow2 extends TowerCrossbow1{
    {
        HP = HT = 55;
        spriteClass = TowerCrossbow2Sprite.class;


        baseAttackDelay = 0.65f;

        upgradeLevel = 8;

        cost = 450;
        upgrade1Cost = 550;
        damageMin = 3;
        damageMax = 7;
    }
}
