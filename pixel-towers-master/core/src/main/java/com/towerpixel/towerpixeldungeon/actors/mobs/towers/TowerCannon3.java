package com.towerpixel.towerpixeldungeon.actors.mobs.towers;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannon3Sprite;

public class TowerCannon3 extends TowerCannon2{

    {
        HP = HT = 90;
        spriteClass = TowerCannon3Sprite.class;


        baseAttackDelay = 1.5f;

        upgCount = 2;

        upgradeLevel = 11;

        cost = 1300;
        upgrade1Cost = 1500;
        upgrade2Cost = 1000;
        damageMin = 6;
        damageMax = 25;
        damageExplosionMult = 0.5f;
    }

}
