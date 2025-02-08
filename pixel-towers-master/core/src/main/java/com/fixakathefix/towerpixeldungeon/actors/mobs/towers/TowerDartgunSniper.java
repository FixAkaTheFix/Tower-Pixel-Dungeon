package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerDartgunSniperSprite;

public class TowerDartgunSniper extends TowerDartgun3 {
    {
        HP = HT = 100;
        spriteClass = TowerDartgunSniperSprite.class;//dpt/c = 0.00568 + p

        attackRange = 20;
        cost = 1610;
        upgCount = 0;
        baseAttackDelay = 3.5f;
        damageMin = 31;
        damageMax = 33;
        upgradeLevel = 8;
        poisonPower = 19;
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }
}
