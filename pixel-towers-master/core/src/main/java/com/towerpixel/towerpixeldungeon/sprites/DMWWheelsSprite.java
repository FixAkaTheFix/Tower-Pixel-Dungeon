package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DMWWheelsSprite extends MobSprite {

    public DMWWheelsSprite() {
        super();

        texture( Assets.Sprites.DM300 );

        TextureFilm frames = new TextureFilm( texture, 25, 22 );

        idle = new Animation( 5, true );
        idle.frames( frames, 40, 41, 42, 41 );

        run = new Animation( 15, true );
        run.frames( frames, 40, 41, 40, 42 );

        attack = new Animation( 12, false );
        attack.frames( frames, 43, 44, 45, 46, 47 );

        die = new Animation( 20, true );
        die.frames( frames, 46, 45, 43, 42, 45, 41 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}