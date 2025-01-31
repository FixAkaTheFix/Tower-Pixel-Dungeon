package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerCannon1Sprite extends MobSprite {

    private Animation cast;

    public TowerCannon1Sprite() {
        super();

        texture( Assets.Sprites.TOWERCANNON);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 0,1,2,3);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 4,5,6,7,8,9,10);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 0 );

        play( idle );
    }

    @Override
    public void die() {
        emitter().burst( Speck.factory( Speck.SMOKE ), 3 );
        super.die();
    }

    @Override
    public void attack( int cell ) {


        if (Dungeon.level.heroFOV[ch.pos] || Dungeon.level.heroFOV[cell]) {

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new Bomb(), new Callback() {
                        @Override
                        public void call() {
                            ch.onAttackComplete();
                        }
                    } );

            play( cast );
            turnTo( ch.pos , cell );

        } else {

            super.attack( cell );

        }
    }
}