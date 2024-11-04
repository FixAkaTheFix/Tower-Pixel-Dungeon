package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Pike;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class TowerCrossbowBallistaSprite extends MobSprite {

    private Animation cast;

    public TowerCrossbowBallistaSprite() {
        super();

        texture( Assets.Sprites.TOWERCROSSBOW );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 48,49,50,51);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 52,53,54,55);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 48 );

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent(cell, ch.pos)) {

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new Pike(), new Callback() {
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