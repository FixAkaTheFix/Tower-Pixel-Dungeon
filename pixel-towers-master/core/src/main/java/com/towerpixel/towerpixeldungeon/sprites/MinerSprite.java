package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class MinerSprite extends MobSprite {

	public MinerSprite() {
		super();
		
		texture( Assets.Sprites.MINER);
		
		TextureFilm frames = new TextureFilm( texture, 16, 16 );
		
		idle = new Animation( 5, true );
		idle.frames( frames, 0, 1);
		
		run = idle.clone();
		
		attack = idle.clone();
		
		die = new Animation( 1, false );
		die.frames( frames, 2 );
		
		play( idle );
	}
}
