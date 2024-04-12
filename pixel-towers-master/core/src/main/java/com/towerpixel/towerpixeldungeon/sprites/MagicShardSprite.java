package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class MagicShardSprite extends MobSprite{

    public MagicShardSprite() {
        super();

        texture( Assets.Sprites.SHARD );

        TextureFilm frames = new TextureFilm( texture, 10, 10 );

        idle = new Animation( 15, true );
        idle.frames(frames, 2, 3, 6, 5, 12);

        run = new Animation( 15, true );
        run.frames( frames, 2, 3, 6, 5, 12);

        attack = new Animation( 12, false );
        attack.frames( frames, 8, 9, 10, 11 );

        die = new Animation( 12, false );
        die.frames( frames, 5, 8,9,10,11, 7);

        play( idle );
    }
}
