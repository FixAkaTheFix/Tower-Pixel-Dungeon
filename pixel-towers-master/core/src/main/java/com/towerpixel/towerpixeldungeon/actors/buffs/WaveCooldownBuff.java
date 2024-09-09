package com.towerpixel.towerpixeldungeon.actors.buffs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;

public class WaveCooldownBuff extends FlavourBuff {

    public static final float DURATION	= 30f;

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public String desc() {
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