package com.towerpixel.towerpixeldungeon.actors.buffs;

import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;

public class Battlecry extends FlavourBuff {

    public static final float DURATION = 5f;

    {
        type = Buff.buffType.POSITIVE;
        announced = true;
    }

    @Override
    public int icon() {
        return BuffIndicator.RAGE;
    }


    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }
}
