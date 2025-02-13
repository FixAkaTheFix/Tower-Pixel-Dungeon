package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCannon3Sprite;

public class TowerCannon3 extends TowerCannon2{

    {
        HP = HT = 150;
        spriteClass = TowerCannon3Sprite.class;


        upgCount = 2;

        upgradeLevel = 11;

        cost = 1300;
        upgrade1Cost = 1500;
        upgrade2Cost = 1500;
        damageMin = 5;
        damageMax = 23;
        damageExplosionMult = 0.5f;
    }

}
