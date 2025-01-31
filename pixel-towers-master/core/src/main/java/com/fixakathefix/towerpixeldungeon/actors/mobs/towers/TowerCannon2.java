package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;


import com.fixakathefix.towerpixeldungeon.sprites.TowerCannon2Sprite;


public class TowerCannon2 extends TowerCannon1{

    {
        HP = HT = 80;
        spriteClass = TowerCannon2Sprite.class;


        baseAttackDelay = 1.5f;

        upgradeLevel = 8;

        cost = 600;
        upgrade1Cost = 700;
        damageMin = 4;
        damageMax = 12;
        damageExplosionMult = 0.5f;
    }
}
