package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class TargetSprite extends MobSprite {

	public TargetSprite() {
		super();
		
		texture( Assets.Sprites.TARGET);
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
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
