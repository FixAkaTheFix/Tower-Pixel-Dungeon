package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DMWBodySprite extends MobSprite {

    public DMWBodySprite() {
        super();

        texture( Assets.Sprites.DM300 );

        TextureFilm frames = new TextureFilm( texture, 25, 22);

        idle = new Animation( 12, true );
        idle.frames( frames, 32,33,34 );

        run = new Animation( 12, true );
        run.frames( frames, 32,33,34 );

        attack = new Animation( 12, false );
        attack.frames( frames, 32,33,34 );

        die = new Animation( 12, false );
        die.frames( frames, 32,33,34, 35 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}