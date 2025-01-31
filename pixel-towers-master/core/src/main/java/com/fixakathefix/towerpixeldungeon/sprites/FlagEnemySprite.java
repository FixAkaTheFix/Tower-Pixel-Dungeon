package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class FlagEnemySprite  extends MobSprite {
    public FlagEnemySprite() {
        super();

        texture( Assets.Sprites.FLAGENEMY );

        TextureFilm frames = new TextureFilm( texture, 18, 22 );

        idle = new MovieClip.Animation( 1, true );
        idle.frames( frames, 0,1,2,3);

        run = new MovieClip.Animation( 1, true );
        run.frames( frames, 0,1,2,3 );

        attack = new MovieClip.Animation( 1, false );
        attack.frames( frames, 0,1,2,3 );

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 0,1,2,3 );

        play( idle );
    }
}
