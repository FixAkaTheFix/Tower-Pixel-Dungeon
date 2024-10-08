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
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerDartgunSpitterSprite extends MobSprite {

    private Animation cast;

    public TowerDartgunSpitterSprite() {
        super();

        texture( Assets.Sprites.TOWERDARTGUN);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 4, false );
        idle.frames( frames, 24);

        run = idle.clone();

        attack = new Animation( 12, false );
        attack.frames( frames, 24);

        cast = attack.clone();

        die = new Animation( 1, false );
        die.frames( frames, 24 );

        play( idle );
    }
}