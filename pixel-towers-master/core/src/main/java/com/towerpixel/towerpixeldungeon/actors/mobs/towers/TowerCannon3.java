package com.towerpixel.towerpixeldungeon.actors.mobs.towers;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannon3Sprite;

public class TowerCannon3 extends TowerCannon2{

    {
        HP = HT = 90;
        spriteClass = TowerCannon3Sprite.class;

        attackRange = 7;//DPT =32.5*0.66 = 21.45;DPT/C=21,45:1150=0.0187
        baseAttackDelay = 1.5f;

        upgCount = 2;

        upgradeLevel = 11;

        cost = 1300;
        upgrade1Cost = 1500;
        upgrade2Cost = 1500;
        damageMin = 6;
        damageMax = 25;
        damageExplosionMult = 0.5f;
    }

}
