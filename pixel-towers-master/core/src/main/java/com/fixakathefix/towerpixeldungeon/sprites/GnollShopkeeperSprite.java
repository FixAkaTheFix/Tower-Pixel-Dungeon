package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GnollShopkeeperSprite extends MobSprite {

	public GnollShopkeeperSprite() {
		super();
		
		texture( Assets.Sprites.GNOLLSHOPKEEPER );
		
		TextureFilm frames = new TextureFilm( texture, 12, 15 );
		
		idle = new Animation( 2, true );
		idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );
		
		run = idle.clone();
		
		attack = idle.clone();
		
		die = idle.clone();
		
		play( idle );
	}
}
