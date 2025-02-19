package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;

public class Vampirism extends Buff implements Hero.Doom{
    private static final String COUNT = "count";

    protected int count = 0;

    {
        type = buffType.NEUTRAL;
        announced = true;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(COUNT, count);

    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        count = bundle.getInt(COUNT);
    }
    @Override
    public void fx(boolean on) {
        if (on) target.sprite.ra = 0.8f;
        else if (target.invisible == 0) target.sprite.ra = 1f;
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {
            count++;
            if (count % 3 == 1) target.damage( 1, this );
        } else {
            detach();
        }
        return true;
    }

    @Override
    public void onDeath() {

        Dungeon.fail( getClass() );
        GLog.n( Messages.get(this, "ondeath") );

    }
}
