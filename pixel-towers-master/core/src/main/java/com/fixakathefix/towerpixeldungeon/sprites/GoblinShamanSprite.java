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
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public abstract class GoblinShamanSprite extends MobSprite {

    protected Class particleType;

    protected abstract int texOffset();

    public GoblinShamanSprite() {
        super();

        int c = texOffset();

        texture( Assets.Sprites.GOBLINSHAMAN );

        TextureFilm frames = new TextureFilm( texture, 12, 16 );

        idle = new Animation( 2, true );
        idle.frames( frames, c+0, c+0, c+0, c+1, c+0, c+0, c+1, c+1 );

        run = new Animation( 12, true );
        run.frames( frames, c+2, c+3, c+4, c+5, c+6, c+7, c+8 );

        attack = new Animation( 12, false );
        attack.frames( frames, Random.Int(c+9,c+13),Random.Int(c+9,c+13),Random.Int(c+9,c+13),Random.Int(c+9,c+13),Random.Int(c+9,c+13),Random.Int(c+9,c+13));

        die = new Animation( 12, false );
        die.frames( frames, c+14, c+15, c+16, c+17 );

        play( idle );
    }

    public static class ShamanStrength extends GoblinShamanSprite {
        @Override
        protected int texOffset() {
            return 0;
        }
    }

    public static class ShamanShield extends GoblinShamanSprite {
        @Override
        protected int texOffset() {
            return 18;
        }
    }

    public static class ShamanRegen extends GoblinShamanSprite {
        @Override
        protected int texOffset() {
            return 36;
        }
    }
    public static class ShamanFake extends GoblinShamanSprite {
        @Override
        protected int texOffset() {
            return 54;
        }
    }
}