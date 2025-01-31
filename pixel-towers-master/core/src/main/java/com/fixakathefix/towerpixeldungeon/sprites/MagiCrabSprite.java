package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class MagiCrabSprite extends MobSprite {

    public MagiCrabSprite() {
        super();

        texture( Assets.Sprites.CRAB );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 5, true );
        idle.frames( frames, 64,65,64,66 );

        run = new Animation( 15, true );
        run.frames( frames, 67,68,69,70 );

        attack = new Animation( 12, false );
        attack.frames( frames, 71,72,73);

        die = new Animation( 12, false );
        die.frames( frames, 74,75,76,77 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0x8888FF80;
    }
}