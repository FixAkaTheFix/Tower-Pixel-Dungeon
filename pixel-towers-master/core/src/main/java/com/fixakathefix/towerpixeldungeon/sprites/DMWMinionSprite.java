package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DMWMinionSprite extends MobSprite {

    public DMWMinionSprite() {
        super();

        texture( Assets.Sprites.DM300 );

        TextureFilm frames = new TextureFilm( texture, 25, 22 );

        idle = new Animation( 5, true );
        idle.frames( frames, 48, 49 );

        run = new Animation( 10, true );
        run.frames( frames, 48, 49 );

        attack = new Animation( 30, false );
        attack.frames( frames, 48, 49, 48, 49, 48, 49, 48, 49, 48, 49, 48, 49 );

        die = new Animation( 10, false );
        die.frames( frames, 50, 51, 50, 51, 52 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}