package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class HermitCrabNoShellSprite extends MobSprite {

    public HermitCrabNoShellSprite() {
        super();

        texture( Assets.Sprites.CRAB );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 5, true );
        idle.frames( frames, 48,49,48,50 );

        run = new Animation( 15, true );
        run.frames( frames, 51, 52, 53, 54 );

        attack = new Animation( 12, false );
        attack.frames( frames, 55,56,57);

        die = new Animation( 12, false );
        die.frames( frames, 58,59,60,61 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}
