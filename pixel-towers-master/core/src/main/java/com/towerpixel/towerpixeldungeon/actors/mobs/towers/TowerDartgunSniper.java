package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgun3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgunSniperSprite;

public class TowerDartgunSniper extends TowerDartgun2 {
    {
        HP = HT = 30;
        spriteClass = TowerDartgunSniperSprite.class;

        viewDistance = 20;
        cost = 1610;
        upgCount = 0;
        baseAttackDelay = 3f;
        damageMin = 31;
        damageMax = 33;
        upgradeLevel = 8;
        poisonPower = 16;
    }

    @Override
    public int attackSkill(Char target) {
        return 20;
    }
}
