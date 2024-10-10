package com.towerpixel.towerpixeldungeon.actors.mobs.towers;


import com.towerpixel.towerpixeldungeon.sprites.TowerCannon2Sprite;


public class TowerCannon2 extends TowerCannon1{

    {
        HP = HT = 60;
        spriteClass = TowerCannon2Sprite.class;

        attackRange = 7;//DPT =15*0.66 = 10;DPT/C=10:450=0.02222
        baseAttackDelay = 1.5f;

        upgradeLevel = 8;

        cost = 600;
        upgrade1Cost = 700;
        damageMin = 4;
        damageMax = 12;
        damageExplosionMult = 0.5f;
    }
}
