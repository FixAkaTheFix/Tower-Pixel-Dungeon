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
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class CampRatMageSprite extends MobSprite {

	private Animation cast;

	public CampRatMageSprite() {
		super();
		
		texture( Assets.Sprites.TOWERCAMPRATMAGE );
		
		TextureFilm frames = new TextureFilm( texture, 16, 19 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 1 );

		attack = new Animation( 15, false );
		attack.frames( frames, 2, 3, 4, 5, 0 );

		cast = attack.clone();

		run = new Animation( 10, true );
		run.frames( frames, 6, 7, 8, 9, 10 );

		die = new Animation( 10, false );
		die.frames( frames, 11, 12, 13, 14 );
		
		play( idle );
	}
	@Override
	public void attack( int cell ) {
		super.zap( cell );
		play(cast);

		MagicMissile.boltFromChar( parent,
				MagicMissile.FROST,
				this,
				cell,
				new Callback() {
					@Override
					public void call() {ch.onAttackComplete();
					}
				} );
		Sample.INSTANCE.play( Assets.Sounds.RAY );
	}
}
