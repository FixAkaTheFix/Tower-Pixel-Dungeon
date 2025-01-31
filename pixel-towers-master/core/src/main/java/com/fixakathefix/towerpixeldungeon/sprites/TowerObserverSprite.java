package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class TowerObserverSprite extends MobSprite {

	public TowerObserverSprite() {
		super();
		
		texture( Assets.Sprites.OBSERVER );
		
		TextureFilm frames = new TextureFilm( texture, 15, 15 );
		
		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1, 2, 3, 4, 5);
		
		run = new Animation( 8, true );
		run.frames( frames, 0, 1, 2, 3, 4, 5 );
		
		attack = new Animation( 16, false );
		attack.frames( frames, 0, 1, 2, 3, 4, 5 );
		
		die = new Animation( 3, false );
		die.frames( frames, 6,7,8,9,10);
		
		play( idle );
	}
}
