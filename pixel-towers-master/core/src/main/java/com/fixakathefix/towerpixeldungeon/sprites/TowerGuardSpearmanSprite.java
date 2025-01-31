/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
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

package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.PointF;

public class TowerGuardSpearmanSprite extends MobSprite {

    public TowerGuardSpearmanSprite() {
        super();

        texture( Assets.Sprites.TOWERGUARDSPEARMAN );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );

        run = new MovieClip.Animation( 15, true );
        run.frames( frames, 2, 3, 4, 5, 6, 7 );

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 8, 9, 10 );

        die = new MovieClip.Animation( 8, false );
        die.frames( frames, 11, 12, 13, 14 );

        play( idle );
    }
    public void bloodBurstA(PointF from, int damage) {
        /*
         * I guess this is considered a human. No blood for you.
         */
    }
}