package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;

public class NoTowerWithering extends Buff{
    //is only applied to dying units


    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public boolean attachTo(Char target) {
        return super.attachTo(target);
    }

    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }


    @Override
    public boolean act() {
        if (target.isAlive()) {
            target.damage(target.HT/30 + 1, NoTowerWithering.class);
                spend( TICK );
        } else {
            detach();
        }
        return true;
    }

    @Override
    public void tintIcon(Image icon) {
        icon.hardlight(0.4f, 0.4f, 0.4f);
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.add( CharSprite.State.DARKENED );
        else if (target.invisible == 0) target.sprite.remove( CharSprite.State.DARKENED );
    }
}
