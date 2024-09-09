package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class SignSprite extends MobSprite {

	public SignSprite() {
		super();
		
		texture( Assets.Sprites.SIGN);
		
		TextureFilm frames = new TextureFilm( texture, 13, 13 );
		
		idle = new Animation( 1, false );
		idle.frames( frames, 0);
		
		run = new Animation( 1, false );
		run.frames( frames, 0);
		
		attack = new Animation( 1, false );
		attack.frames( frames, 0);
		
		die = new Animation( 1, false );
		die.frames( frames, 0);
		
		play( idle );
	}
}
