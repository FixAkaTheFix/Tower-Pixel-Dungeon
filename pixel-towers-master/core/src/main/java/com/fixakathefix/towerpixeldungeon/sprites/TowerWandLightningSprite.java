package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class TowerWandLightningSprite extends MobSprite {

    private Animation cast;

    public TowerWandLightningSprite() {
        super();

        texture( Assets.Sprites.TOWERWAND );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 64,65,66,67);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 68,69,70,71);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 64 );

        play( idle );
    }
}