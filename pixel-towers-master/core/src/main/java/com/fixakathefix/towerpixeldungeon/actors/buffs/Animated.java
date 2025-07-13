package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Animated extends Buff {

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    @Override
    public int icon() {
        return BuffIndicator.ANIMATED;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add(CharSprite.State.LOW_HEARTS);
        else target.sprite.remove(CharSprite.State.LOW_HEARTS);
    }
}