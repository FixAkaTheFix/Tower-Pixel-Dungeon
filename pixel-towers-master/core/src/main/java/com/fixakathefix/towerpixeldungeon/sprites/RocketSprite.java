package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class RocketSprite extends MobSprite {

    public RocketSprite() {
        super();

        texture( Assets.Sprites.ROCKET);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 5, true );
        idle.frames( frames, 0, 1,2,3,4 );

        run = new Animation( 10, true );
        run.frames( frames, 0,1,2,3);

        attack = new Animation( 12, false );
        attack.frames( frames, 6,7,8,9,10,11,12,13,14,15,16 );

        die = new Animation( 12, false );
        die.frames( frames, 7,13,14,15,16 );

        play( idle );
    }
}
