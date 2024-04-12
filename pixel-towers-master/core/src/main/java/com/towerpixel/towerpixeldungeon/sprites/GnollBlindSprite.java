package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GnollBlindSprite extends MobSprite {

    private Animation cast;

    public GnollBlindSprite() {
        super();

        texture( Assets.Sprites.GNOLL );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 2, true );
        idle.frames( frames, 42,42,42,43,42,42,43,43);

        run = new Animation( 12, true );
        run.frames( frames, 46,47,48,49);

        attack = new Animation( 12, false );
        attack.frames( frames, 44,45,42);

        die = new Animation( 12, false );
        die.frames( frames, 50,51,52);

        play( idle );
    }
}