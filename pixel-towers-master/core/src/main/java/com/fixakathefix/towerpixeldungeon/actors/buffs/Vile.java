package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Vile extends Buff {

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add( CharSprite.State.DARKENED );
        else if (target.invisible == 0) target.sprite.remove( CharSprite.State.DARKENED );
    }

    @Override
    public int icon() {
        return BuffIndicator.VILE;
    }
}