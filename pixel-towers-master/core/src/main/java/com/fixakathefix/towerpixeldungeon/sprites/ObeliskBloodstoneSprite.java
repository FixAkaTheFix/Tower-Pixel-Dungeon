package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class ObeliskBloodstoneSprite extends MobSprite {

    public ObeliskBloodstoneSprite() {
        super();

        texture( Assets.Sprites.OBELISK_BLOODSTONE );

        TextureFilm frames = new TextureFilm( texture, 12, 14 );

        idle = new Animation( 3, true );
        idle.frames( frames, 0, 1, 2, 3 );

        run = new Animation( 4, true );
        run.frames( frames, 0, 1, 2, 3);

        attack = new Animation( 5, false );
        attack.frames( frames, 4, 5, 4, 5);

        die = new Animation( 7, false );
        die.frames( frames, 5, 6, 7, 8, 9);

        play( idle );
    }
    @Override
    public int blood() {
        return 0xAA0000;
    }
}