package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class BossRatKingSprite extends MobSprite {

    private Animation speak;

    public BossRatKingSprite() {
        super();

        texture( Assets.Sprites.BOSSRATKING );

        TextureFilm frames = new TextureFilm( texture, 16, 18 );

        idle = new Animation( 2, true );
        idle.frames( frames, 0, 0, 0, 1 );

        run = new Animation( 10, true );
        run.frames( frames, 6, 7, 8, 9, 10 );

        attack = new Animation( 15, false );
        attack.frames( frames, 2, 3, 4, 5, 0 );

        die = run.clone();

        play( idle );
    }
}