package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.sprites.GoblinNinjaSprite;

public class GoblinNinja extends Goblin{
    {
        spriteClass = GoblinNinjaSprite.class;

        HP = HT = 30;
        defenseSkill = 10;

        viewDistance = 20;
        baseSpeed = 1.4f;

        EXP = 10;
        maxLvl = 15;

        targetingPreference = TargetingPreference.NOT_AMULET;
    }
}
