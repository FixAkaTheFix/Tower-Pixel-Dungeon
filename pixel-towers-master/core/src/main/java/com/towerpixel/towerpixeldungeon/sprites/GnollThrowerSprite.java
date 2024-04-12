package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;

public class GnollThrowerSprite extends MobSprite {
    private Animation cast;
    public GnollThrowerSprite() {
        super();

        texture( Assets.Sprites.GNOLL );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0, 1, 0, 0, 1, 1 );

        run = new Animation( 12, true );
        run.frames( frames, 4, 5, 6, 7 );

        attack = new Animation( 12, false );
        attack.frames( frames, 2, 3, 0 );

        die = new Animation( 12, false );
        die.frames( frames, 8, 9, 10 );

        play( idle );
    }
    @Override
    public void attack( int cell ) {
        if (!Dungeon.level.adjacent(cell, ch.pos)) {

            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new ThrowingStone(), new Callback() {
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