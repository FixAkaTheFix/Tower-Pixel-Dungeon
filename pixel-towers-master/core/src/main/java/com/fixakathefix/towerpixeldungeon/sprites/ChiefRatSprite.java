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

public class ChiefRatSprite extends MobSprite {

    public ChiefRatSprite() {
        super();

        texture(Assets.Sprites.RAT);

        TextureFilm frames = new TextureFilm(texture, 16, 15);

        idle = new Animation(2, true);
        idle.frames(frames, 64, 64, 64, 65);

        run = new Animation(10, true);
        run.frames(frames, 70, 71, 72, 73, 74);

        attack = new Animation(15, false);
        attack.frames(frames, 66, 67, 68, 69, 64);

        die = new Animation(10, false);
        die.frames(frames, 75, 76, 77, 78);

        play(idle);
    }
}