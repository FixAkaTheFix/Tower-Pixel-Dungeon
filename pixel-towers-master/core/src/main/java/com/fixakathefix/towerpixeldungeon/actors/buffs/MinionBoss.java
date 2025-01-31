package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class MinionBoss extends Minion{

    @Override
    public int icon() {
        return BuffIndicator.MINIONBOSS;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add( CharSprite.State.PURPLEDEMONIC );
        else if (target.invisible == 0) target.sprite.remove( CharSprite.State.PURPLEDEMONIC );
    }
}
