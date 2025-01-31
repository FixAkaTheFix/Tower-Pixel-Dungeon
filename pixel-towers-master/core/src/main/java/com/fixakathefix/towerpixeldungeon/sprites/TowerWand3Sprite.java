package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class TowerWand3Sprite extends MobSprite {

    private Animation cast;

    public TowerWand3Sprite() {
        super();

        texture( Assets.Sprites.TOWERWAND );

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