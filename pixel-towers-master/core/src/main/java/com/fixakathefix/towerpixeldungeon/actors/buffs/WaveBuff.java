package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class WaveBuff extends FlavourBuff {

    {
        type = buffType.NEUTRAL;
        announced = true;
    }



    @Override
    public String desc() {
        if (((Arena)Dungeon.level).maxWaves >= 1000) return Messages.get(this, "descnomax", Dungeon.level.wave, dispTurns());
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