package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Minion extends Buff{
    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    @Override
    public int icon() {
        return BuffIndicator.MINION;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add( CharSprite.State.DEMONIC );
        else if (target.invisible == 0) target.sprite.remove( CharSprite.State.DEMONIC);
    }
}
