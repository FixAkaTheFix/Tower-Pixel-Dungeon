package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public class SluggerSprite extends MobSprite {

    public SluggerSprite() {
        super();

        texture( Assets.Sprites.SLUGGER );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 1 );

        run = new Animation( 12, true );
        run.frames( frames, Random.IntRange(2,7), Random.IntRange(2,7), Random.IntRange(2,7), Random.IntRange(2,7), Random.IntRange(2,7));

        attack = new Animation( 12, false );
        attack.frames( frames, 13,14,15 );

        die = new Animation( 12, false );
        die.frames( frames, 8, 9, 10 ,11,12 );

        play( idle );
    }
}
