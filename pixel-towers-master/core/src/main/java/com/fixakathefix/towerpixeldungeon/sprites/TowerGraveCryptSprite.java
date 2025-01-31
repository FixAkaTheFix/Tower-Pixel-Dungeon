package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class TowerGraveCryptSprite extends MobSprite {

    private Animation cast;

    public TowerGraveCryptSprite() {
        super();

        texture( Assets.Sprites.TOWERGRAVE);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 48,49,50,51);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 52,53,54,55);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 48 );

        play( idle );
    }

}