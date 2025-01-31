package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class BossTrollSprite extends MobSprite {

    public BossTrollSprite() {
        super();

        texture( Assets.Sprites.BOSSTROLL );

        TextureFilm frames = new TextureFilm( texture, 25, 23 );

        idle = new Animation( 2, true );
        idle.frames( frames, 20, 20, 20, 21, 20, 20, 21, 21 );

        run = new Animation( 5, true );
        run.frames( frames, 22,23,24,25 );

        attack = new Animation( 12, false );
        attack.frames( frames, 26,27,28,29,30 );

        die = new Animation( 12, false );
        die.frames( frames, 31,32,33,34,35 );

        play( idle );
    }
}