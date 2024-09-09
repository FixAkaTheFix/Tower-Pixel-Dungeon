package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuardSpearmanSprite;

public class TowerGuardSpearman extends TowerGuard3{

    {
        HP = HT = 200;
        spriteClass = TowerGuardSpearmanSprite.class;

        cost = 1800;
        damageMin = 30;
        damageMax = 40;
        upgradeLevel = 8;
        defMin = 1;
        defMax = 7;
        upgCount = 0;
        regenNum = 20;
        viewDistance = 3;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return super.canAttack(enemy) || (distance(enemy)<=2);
    }
}
