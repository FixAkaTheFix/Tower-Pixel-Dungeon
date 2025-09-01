package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard3Sprite;

public class TowerGuard3 extends TowerGuard2{

    {
        HP = HT = 400;
        spriteClass = TowerGuard3Sprite.class;//dpt/c = 0.01333

        cost = 1050;
        damageMin = 12;
        damageMax = 16;
        upgradeLevel = 16;
        defMin = 2;
        defMax = 6;
        upgCount = 2;
        upgrade1Cost = 1500;
        upgrade2Cost = 800;
        regenNum = 10;
    }

    @Override
    public int attackSkill(Char target) {
        return 13;
    }
}
