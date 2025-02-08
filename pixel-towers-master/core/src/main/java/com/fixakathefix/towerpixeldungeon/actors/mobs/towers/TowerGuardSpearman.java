package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuardSpearmanSprite;

public class TowerGuardSpearman extends TowerGuard3{

    {
        HP = HT = 350;
        spriteClass = TowerGuardSpearmanSprite.class;

        cost = 1950;
        damageMin = 32;
        damageMax = 42;//dpt/c = 0.01897
        upgradeLevel = 8;
        defMin = 2;
        defMax = 5;
        upgCount = 0;
        regenNum = 20;
        viewDistance = 3;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return super.canAttack(enemy) || (distance(enemy)<=2);
    }
}
