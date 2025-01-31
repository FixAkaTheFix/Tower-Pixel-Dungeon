package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GoblinSprite extends MobSprite {

	public GoblinSprite() {
		super();
		
		texture( Assets.Sprites.GOBLIN );
		
		TextureFilm frames = new TextureFilm( texture, 12, 16 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );
		
		run = new Animation( 12, true );
		run.frames( frames, 2, 3, 4, 5, 6, 7, 8 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 9, 10, 11, 12, 13 );
		
		die = new Animation( 12, false );
		die.frames( frames, 14, 15, 16, 17 );
		
		play( idle );
	}
}
