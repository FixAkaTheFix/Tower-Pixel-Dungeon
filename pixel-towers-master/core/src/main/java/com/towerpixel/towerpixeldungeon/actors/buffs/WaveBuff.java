package com.towerpixel.towerpixeldungeon.actors.buffs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class WaveBuff extends FlavourBuff {

    {
        type = buffType.NEUTRAL;
        announced = true;
    }



    @Override
    public String desc() {
        if (Dungeon.level.wave == ((Arena)Dungeon.level).maxWaves) return Messages.get(this, "descend", Dungeon.level.wave, ((Arena)Dungeon.level).maxWaves);
        return Messages.get(this, "desc", Dungeon.level.wave, ((Arena)Dungeon.level).maxWaves, dispTurns());
    }


    @Override
    public String heroMessage() {
        return "";
    }

    @Override
    public int icon() {
        return BuffIndicator.WAVE;
    }

    @Override
    public String iconTextDisplay() {
        return "";
    }
}