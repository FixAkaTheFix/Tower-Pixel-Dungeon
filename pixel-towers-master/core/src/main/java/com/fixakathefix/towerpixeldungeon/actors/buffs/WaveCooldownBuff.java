package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class WaveCooldownBuff extends FlavourBuff {

    public static final float DURATION	= 30f;

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public String desc() {
        if (((Arena)Dungeon.level).maxWaves > 1000) return Messages.get(this, "descnomax", Dungeon.level.wave + 1, this.dispTurns());
        return Messages.get(this, "desc", Dungeon.level.wave + 1, ((Arena)Dungeon.level).maxWaves, this.dispTurns());
    }
    @Override
    public String heroMessage() {
        return "";
    }

    @Override
    public int icon() {
        return BuffIndicator.WAVECOOLDOWN;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }

    @Override
    public void detach() {
        super.detach();
    }
}