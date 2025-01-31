package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMW;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class DMWSprite extends MobSprite {
    private Animation stab;
    private Animation prep;
    private Animation leap;


        public DMWSprite () {
            super();

            texture( Assets.Sprites.DM300 );

            TextureFilm frames = new TextureFilm( texture, 25, 22 );

            idle = new Animation( 8, true );
            idle.frames( frames, 16, 17 );

            run = new Animation( 8, true );
            run.frames( frames, 16, 17 );

            attack = new Animation( 12, false );
            attack.frames( frames,  19, 20, 21,22,23 );

            zap = new Animation( 8, false );
            zap.frames( frames, 19, 18, 19 );

            die = new Animation( 8, false );
            die.frames( frames, 18, 20, 21,20,17,20,21,18 );

            stab = new Animation( 12, false );
            stab.frames( frames,  19, 20, 21,22,23 );

            prep = new Animation( 16, true );
            prep.frames( frames, 16, 17 );

            leap = new Animation( 1, true );
            leap.frames( frames, 21 );

            play( idle );
        }

        public void zap( int cell ) {

            super.zap( cell );

            MagicMissile.boltFromChar( parent,
                    MagicMissile.FORCE,
                    this,
                    cell,
                    new Callback() {
                        @Override
                        public void call() {
                            ((DMW)ch).onZapComplete();
                        }
                    } );
            Sample.INSTANCE.play( Assets.Sounds.GAS );
        }

        @Override
        public void place(int cell) {
            if (parent != null) parent.bringToFront(this);
            super.place(cell);
        }

        @Override
        public void die() {
            emitter().burst( Speck.factory( Speck.WOOL ), 8 );
            super.die();
        }

        @Override
        public void onComplete( Animation anim ) {
            if (anim == zap) {
                idle();
            }
            super.onComplete( anim );
        }

        @Override
        public int blood() {
            return 0xFFFFFF88;
        }

    public void chargePrep( int cell ){
        turnTo( ch.pos, cell );
        play( prep );
    }

    @Override
    public void jump( int from, int to, float height, float duration,  Callback callback ) {
        super.jump( from, to, height, duration, callback );
        play( leap );
    }

    }
