package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerCrossbowGatlingSprite extends MobSprite {

    private Animation cast;

    public TowerCrossbowGatlingSprite() {
        super();


        texture( Assets.Sprites.TOWERCROSSBOW );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 6, true );
        idle.frames( frames, 64,65,66,67);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, true );
        attack.frames( frames, 68,69,70,71);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 64);

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent(cell, ch.pos)) {

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new Dart(), new Callback() {
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