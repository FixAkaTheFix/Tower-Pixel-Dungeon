package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class SkullDemonicSprite extends MobSprite {

    public SkullDemonicSprite() {
        super();

        texture( Assets.Sprites.SKULLS);

        TextureFilm frames = new TextureFilm( texture, 10, 10 );

        idle = new Animation( 6, true );
        idle.frames( frames, 16, 20, 21, 20, 16, 20, 21, 20,16, 20, 21, 20,16, 20, 21, 20,17,18,19,17,18,19 );

        run = idle.clone();

        attack = new Animation( 8, false );
        attack.frames( frames, 22,23);

        die = new Animation( 12, false );
        die.frames( frames, 22,23,24,25 );

        play( idle );
    }
}
