package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard2Sprite;

public class TowerGuard2 extends TowerGuard1{

    {
        HP = HT = 200;
        spriteClass = TowerGuard2Sprite.class;//dpt/c = 0.01667

        cost = 450;
        damageMin = 7;
        damageMax = 13;
        upgradeLevel = 9;
        defMin = 1;
        defMax = 5;
        upgrade1Cost = 600;
        regenNum = 7;
    }

    @Override
    public int attackSkill(Char target) {
        return 12;
    }
}
