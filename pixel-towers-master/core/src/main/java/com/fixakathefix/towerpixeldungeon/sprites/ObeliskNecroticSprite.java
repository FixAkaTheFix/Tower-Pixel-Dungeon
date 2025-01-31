package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class ObeliskNecroticSprite extends MobSprite {

    public ObeliskNecroticSprite() {
        super();

        texture( Assets.Sprites.OBELISK_NECROTIC );

        TextureFilm frames = new TextureFilm( texture, 12, 14 );

        idle = new Animation( 6, true );
        idle.frames( frames, 0, 1, 2, 3, 4, 5 );

        run = new Animation( 9, true );
        run.frames( frames, 0, 1, 2, 3, 4, 5  );

        attack = new Animation( 5, false );
        attack.frames( frames, 5, 6, 7, 6, 5);

        die = new Animation( 7, false );
        die.frames( frames, 5, 6, 7, 8, 9);

        play( idle );
    }

    @Override
    public int blood() {
        return 0x33ff33;
    }
}