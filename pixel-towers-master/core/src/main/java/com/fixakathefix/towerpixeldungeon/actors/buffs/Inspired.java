package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Inspired extends FlavourBuff{
    public static final float DURATION = 10f;

    {
        type = Buff.buffType.POSITIVE;
        announced = false;
    }

    @Override
    public int icon() {
        return BuffIndicator.INSPIRED;
    }


    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
    }


}
