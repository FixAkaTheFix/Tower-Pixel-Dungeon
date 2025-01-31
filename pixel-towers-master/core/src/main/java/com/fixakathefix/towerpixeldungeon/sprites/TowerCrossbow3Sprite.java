package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerCrossbow3Sprite extends MobSprite {

    private Animation cast;

    public TowerCrossbow3Sprite() {
        super();

        texture( Assets.Sprites.TOWERCROSSBOW );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 32,33,34,35);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 36,37,38,39);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 32 );

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent(cell, ch.pos)) {

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new ParalyticDart(), new Callback() {
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