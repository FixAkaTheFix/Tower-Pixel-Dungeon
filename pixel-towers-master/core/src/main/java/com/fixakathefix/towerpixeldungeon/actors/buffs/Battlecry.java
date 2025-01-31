package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

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
    public void fx(boolean on) {
        if (on) target.sprite.aura( 0xDD0000,  10, 15, 180 );
        else target.sprite.clearAura();
    }


    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }
}
