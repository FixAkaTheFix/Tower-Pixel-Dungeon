package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerCannon2Sprite extends MobSprite {

    private Animation cast;

    public TowerCannon2Sprite() {
        super();

        texture( Assets.Sprites.TOWERCANNON);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 16,17,18,19);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 20,21,22,23,24,25,26);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 16 );

        play( idle );
    }

    @Override
    public void die() {
        emitter().burst( Speck.factory( Speck.SMOKE ), 3 );
        super.die();
    }

    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent(cell, ch.pos)) {

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