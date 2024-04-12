package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GoblinGiantSprite extends MobSprite {

	public GoblinGiantSprite() {
		super();
		
		texture( Assets.Sprites.GOBLINGIANT );
		
		TextureFilm frames = new TextureFilm( texture, 18, 19 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );
		
		run = new Animation( 17, true );
		run.frames( frames, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
		
		attack = new Animation( 8, false );
		attack.frames( frames, 18, 19, 18 );
		
		die = new Animation( 12, false );
		die.frames( frames, 12, 13, 14, 15, 16, 17 );
		
		play( idle );
	}
}
