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

public class TowerLightning3Sprite extends MobSprite {

    private Animation cast;

    public TowerLightning3Sprite() {
        super();

        texture( Assets.Sprites.TOWERLIGHTNING);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 4, true );
        idle.frames( frames, 32,33,34,35);

        run = idle.clone();

        attack = new Animation( 12, false );
        attack.frames( frames, 36,37,38,39);

        cast = attack.clone();

        die = new Animation( 1, false );
        die.frames( frames, 32 );

        play( idle );
    }
}