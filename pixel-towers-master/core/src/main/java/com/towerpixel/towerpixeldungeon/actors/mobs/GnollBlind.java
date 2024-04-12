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

package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.sprites.GnollBlindSprite;
import com.watabou.utils.Random;

public class GnollBlind extends Mob {

    {
        spriteClass = GnollBlindSprite.class;
        viewDistance = 2;//blind
        HP = HT = 14;
        defenseSkill = 5;

        EXP = 2;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, 11 );//those fight fiercely, but never actually see where they attack
    }

    @Override
    public int attackSkill( Char target ) {
        return 5;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 2);
    }

}