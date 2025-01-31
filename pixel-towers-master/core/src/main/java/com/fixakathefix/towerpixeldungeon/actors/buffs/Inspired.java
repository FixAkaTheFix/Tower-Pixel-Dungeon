package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.utils.Bundle;

public class Inspired extends Buff{
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
    public boolean act() {
        if (target.isAlive()) {

            spend( TICK );

            if ((left -= TICK) <= 0) {
                detach();
            }

        } else {

            detach();

        }

        return true;
    }


    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }


    protected float left;

    private static final String LEFT	= "left";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( LEFT, left );

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        left = bundle.getFloat( LEFT );
    }

    public void set( float duration ) {
        this.left = Math.max(duration, left);
    }

    public void extend( float duration ) {
        this.left += duration;
    }


    public String iconTextDisplay(){
        return Integer.toString((int) left);
    }

    @Override
    public String desc() {
        return Messages.get(this, "desc");
    }

}
