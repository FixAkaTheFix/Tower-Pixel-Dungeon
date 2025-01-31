package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import sun.jvm.hotspot.memory.UniverseExt;

public class ObeliskPermafrostSprite extends MobSprite {

    public ObeliskPermafrostSprite() {
        super();

        texture( Assets.Sprites.OBELISK_PERMAFROST );

        TextureFilm frames = new TextureFilm( texture, 14, 14 );

        idle = new Animation( 4, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7 );

        run = new Animation( 6, true );
        run.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7  );

        attack = new Animation( 12, false );
        attack.frames( frames, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7 );

        die = new Animation( 7, false );
        die.frames( frames, 8, 9, 10, 11);

        zap = attack.clone();

        play( idle );
    }

    @Override
    public synchronized void attack(int cell, Callback callback) {
        MagicMissile.boltFromChar( parent,
                MagicMissile.FROST,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {
                        ch.onAttackComplete();
                        ch.next();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
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
        return 0xAABBFFFF;
    }
}