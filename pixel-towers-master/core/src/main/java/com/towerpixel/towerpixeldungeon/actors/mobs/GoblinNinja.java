package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.sprites.GoblinNinjaSprite;
import com.towerpixel.towerpixeldungeon.windows.WndModes;

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
        if(Dungeon.level.mode == WndModes.Modes.CHALLENGE){
            Buff.affect(this, Invisibility.class,50);
        }
    }
}
