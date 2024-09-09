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

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.hero.HeroSubClass;
import com.towerpixel.towerpixeldungeon.actors.hero.Talent;
import com.towerpixel.towerpixeldungeon.items.artifacts.CloakOfShadows;
import com.towerpixel.towerpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.towerpixel.towerpixeldungeon.plants.Swiftthistle;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;

public class Small extends Buff {

    {
        type = buffType.NEUTRAL;
        announced = false;
    }

    @Override
    public int icon() {
        return 51;
    }

    @Override
    public void fx(boolean on) {
        if (on) target.sprite.scale.set(0.5f, 0.5f);
        else target.sprite.scale.set(1f, 1f);
        target.sprite.update();
    }
}