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
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TowerWand1Sprite extends MobSprite {

    private Animation cast;

    public TowerWand1Sprite() {
        super();

        texture( Assets.Sprites.TOWERWAND);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 0,1,2,3);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 4,5,6,7);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        super.zap( cell );

        MagicMissile.boltFromChar( parent,
                MagicMissile.MAGIC_MISSILE,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {ch.onAttackComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }
}