package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.PylonTowerBrokenSprite;

public class TowerPylonBroken extends TowerCShooting {

    {
        HP = HT = 80;
        spriteClass = PylonTowerBrokenSprite.class;

        attackRange = 0;
        upgCount = 1;

        upgrade1Cost = 1000;

        baseAttackDelay = 1.7f;
        damageMin = 0;
        damageMax = 1;
        alignment = Alignment.NEUTRAL;
        properties.add(Property.IMMOVABLE);

    }

    @Override
    public int attackSkill(Char target) {
        return 0;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return false;
    }

    @Override
    protected boolean doAttack( Char enemy ) {
        return true;
    }

}