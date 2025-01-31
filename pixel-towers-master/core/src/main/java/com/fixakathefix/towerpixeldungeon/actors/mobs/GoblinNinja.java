package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.levels.Arena14;
import com.fixakathefix.towerpixeldungeon.sprites.GoblinNinjaSprite;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;

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
        if(Dungeon.level instanceof Arena14 && Dungeon.level.mode == WndModes.Modes.CHALLENGE){
            Buff.affect(this, Invisibility.class,50);
        }
    }
}
