package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class FlagFriendSprite extends MobSprite {
    public FlagFriendSprite() {
        super();

        texture( Assets.Sprites.FLAGFRIEND );

        TextureFilm frames = new TextureFilm( texture, 16, 22 );

        idle = new Animation( 1, true );
        idle.frames( frames, 0);

        run = new Animation( 1, true );
        run.frames( frames, 0 );

        attack = new Animation( 1, false );
        attack.frames( frames, 0 );

        die = new Animation( 1, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
