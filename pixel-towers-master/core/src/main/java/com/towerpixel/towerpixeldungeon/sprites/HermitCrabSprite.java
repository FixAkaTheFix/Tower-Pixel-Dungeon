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

package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class HermitCrabSprite extends MobSprite {

    public HermitCrabSprite() {
        super();

        texture( Assets.Sprites.CRAB );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 5, true );
        idle.frames( frames, 32, 33, 32, 34 );

        run = new Animation( 15, true );
        run.frames( frames, 35, 36, 37, 38 );

        attack = new Animation( 12, false );
        attack.frames( frames, 39, 40, 41 );

        die = new Animation( 12, false );
        die.frames( frames, 42, 43, 44, 45 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}
