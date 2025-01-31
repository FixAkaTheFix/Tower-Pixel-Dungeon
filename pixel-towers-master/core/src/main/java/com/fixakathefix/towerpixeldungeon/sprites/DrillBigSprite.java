package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DrillBigSprite extends MobSprite {

    public DrillBigSprite() {
        super();
        perspectiveRaise = -4.9f;

        texture( Assets.Sprites.DRILLBIG );

        TextureFilm frames = new TextureFilm( texture, 260, 174 );

        idle = new Animation( 30, true );
        idle.frames( frames, 0, 1);

        run = attack  = idle.clone();

        die = new Animation(15,true);
        die.frames(frames, 0,1,0,1,0,0,0,1,0,0,0,1,0,0);


        play( idle );
    }
}