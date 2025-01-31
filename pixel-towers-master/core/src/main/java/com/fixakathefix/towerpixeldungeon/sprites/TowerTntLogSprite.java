package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.PointF;

public class TowerTntLogSprite extends MobSprite {

	public TowerTntLogSprite() {
		super();
		
		texture( Assets.Sprites.TOWERTNTLOG );
		
		TextureFilm frames = new TextureFilm( texture, 11, 12 );
		
		idle = new Animation( 1, false );
		idle.frames( frames, 0);
		
		run = new Animation( 1, false);
		run.frames( frames, 0 );
		
		attack = new Animation( 1, false );
		attack.frames( frames, 0 );
		
		die = new Animation( 6, false );
		die.frames( frames, 1, 2, 3 );
		
		play( idle );
	}

	@Override
	public void bloodBurstA(PointF from, int damage) {
		//its a log. no blood of any kind, i guess
	}
}
