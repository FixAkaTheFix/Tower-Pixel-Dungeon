package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TowerWand2Sprite extends MobSprite {

    private Animation cast;

    public TowerWand2Sprite() {
        super();

        texture( Assets.Sprites.TOWERWAND );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 16,17,18,19);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 20,21,22,23);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 16 );

        play( idle );
    }

    @Override
    public void attack( int cell ) {
        super.zap( cell );

        MagicMissile.boltFromChar( parent,
                MagicMissile.MAGIC_MISSILE,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {ch.onAttackComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.ZAP );
    }
}