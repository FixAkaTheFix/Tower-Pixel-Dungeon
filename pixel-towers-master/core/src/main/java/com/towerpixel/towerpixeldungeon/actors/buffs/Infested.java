/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.actors.buffs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.PoisonParticle;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Infested extends Buff implements Hero.Doom {

    {
        announced = true;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
    }

    @Override
    public int icon() {
        return BuffIndicator.ANIMATED;
    }

    @Override
    public boolean attachTo(Char target) {
        if (super.attachTo(target) && target.sprite != null){
            CellEmitter.center(target.pos).burst( PoisonParticle.SPLASH, 5 );
            return true;
        } else
            return false;
    }

    @Override
    public boolean act() {
        if (target.isAlive()) {
            int i = Random.Int( 25 - hero.lvl);
            if (i == 1) {target.damage( 1, this );}
        } else {
            detach();
        }
        return true;
    }

    @Override
    public void onDeath() {
        Badges.validateDeathFromPoison();//FIXME NOT POISON BUT INFESTATION
        Dungeon.fail( getClass() );
        GLog.n( Messages.get(this, "ondeath") );
    }
}