package com.towerpixel.towerpixeldungeon.actors.buffs;

import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;
import com.towerpixel.towerpixeldungeon.ui.Icons;

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
        if (on) target.sprite.add(CharSprite.State.LEVITATING);
        else target.sprite.remove(CharSprite.State.LEVITATING);
    }
}