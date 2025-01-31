package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.Shuriken;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ShinobiSprite extends MobSprite {

	public ShinobiSprite() {
		super();
		
		texture(Random.oneOf(Assets.Sprites.SHINOBI,Assets.Sprites.SHINOBIFACELESS,Assets.Sprites.SHINOBISMILING,Assets.Sprites.SHINOBINORMAL,Assets.Sprites.SHINOBIKNIGHT) );
		
		TextureFilm frames = new TextureFilm( texture, 14, 16 );
		
		idle = new Animation( 10, true );
		idle.frames( frames, 0, 0, 0, 1 );
		
		run = new Animation( 30, false );
		run.frames( frames, 2, 3, 4, 5, 0 );
		
		attack = new Animation( 30, false );
		attack.frames( frames, 6, 7, 7, 0 );
		
		zap = attack.clone();
		
		die = new Animation( 12, false );
		die.frames( frames, 8, 9, 10, 10, 10, 10, 10, 10 );
		
		play( run.clone() );
	}
	
	@Override
	public void idle() {
		isMoving = false;
		super.idle();
	}
	
	@Override
	public void move( int from, int to ) {
		
		place( to );
		
		play( run );
		turnTo( from , to );

		isMoving = true;

		if (Dungeon.level.water[to]) {
			GameScene.ripple( to );
		}

	}
	
	@Override
	public void attack( int cell ) {
		if (!Dungeon.level.adjacent( cell, ch.pos )) {

			((MissileSprite)parent.recycle( MissileSprite.class )).
				reset( this, cell, new Shuriken(), new Callback() {
					@Override
					public void call() {
						ch.onAttackComplete();
					}
				} );
			
			play( zap );
			turnTo( ch.pos , cell );
			
		} else {
			
			super.attack( cell );
			
		}
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == run) {
			synchronized (this){
				isMoving = false;
				idle();

				notifyAll();
			}
		} else {
			super.onComplete( anim );
		}
	}
}
