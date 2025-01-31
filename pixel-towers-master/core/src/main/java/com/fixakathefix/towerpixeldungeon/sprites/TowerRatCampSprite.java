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
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRatCamp;
import com.watabou.noosa.TextureFilm;

public class TowerRatCampSprite extends MobSprite {

	public Animation lvl1;
	public Animation lvl2;
	public Animation lvl3;
	public Animation lvl4;
	public Animation lvl5;

	public TowerRatCampSprite() {
		super();
		
		texture( Assets.Sprites.TOWERRATCAMP );
		
		TextureFilm frames = new TextureFilm( texture, 17, 17 );

		lvl1 = new Animation(1, true);
		lvl1.frames(frames, 0);
		lvl2 = new Animation(1, true);
		lvl2.frames(frames, 1);
		lvl3 = new Animation(1, true);
		lvl3.frames(frames, 2);
		lvl4 = new Animation(1, true);
		lvl4.frames(frames, 3);
		lvl5 = new Animation(1, true);
		lvl5.frames(frames, 4);

		idle = new Animation( 1, true );
		idle.frames( frames, 0);
		run = idle.clone();
		attack = idle.clone();
		die = idle.clone();
		
		play( idle );
	}

	@Override
	public void idle() {
		if (ch==null) play (idle); else linkVisuals(ch);
	}

	@Override
	public void linkVisuals(Char ch) {
		super.linkVisuals(ch);
		switch (((TowerRatCamp)ch).levelCurrent){
			case 1: default: play(lvl1); break;
			case 2: play(lvl2); break;
			case 3: play(lvl3); break;
			case 4: play(lvl4); break;
			case 5: play(lvl5); break;
		}


	}

	@Override
	public void move(int from, int to) {
		linkVisuals(ch);
		super.move(from, to);
		linkVisuals(ch);
	}

}
