package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class TowerGrave3Sprite extends MobSprite {

    private Animation cast;

    public TowerGrave3Sprite() {
        super();

        texture( Assets.Sprites.TOWERGRAVE);

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 32,33,34,35);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 36,37,38,39);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 32 );

        play( idle );
    }

}