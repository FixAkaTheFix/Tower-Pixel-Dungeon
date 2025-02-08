package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerDartgun2Sprite;

public class TowerDartgun2 extends TowerDartgun1 {
    {
        HP = HT = 35;
        spriteClass = TowerDartgun2Sprite.class;//dpt/c = 0.0105 + p

        attackRange = 13;
        cost = 430;
        upgrade1Cost = 480;
        damageMin = 7;
        damageMax = 11;
        upgradeLevel = 8;
        poisonPower = 5;
    }

    @Override
    public int attackSkill(Char target) {
        return 22;
    }
}
