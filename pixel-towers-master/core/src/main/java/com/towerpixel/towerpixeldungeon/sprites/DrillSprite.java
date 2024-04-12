package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class DrillSprite extends MobSprite {

    public DrillSprite() {
        super();
        perspectiveRaise = -2.9f;

        texture( Assets.Sprites.DRILL );

        TextureFilm frames = new TextureFilm( texture, 196, 110 );

        idle = new Animation( 30, true );
        idle.frames( frames, 0, 1);

        run = attack  = idle.clone();

        die = new Animation(15,true);
        die.frames(frames, 0,1,0,1,0,0,0,1,0,0,0,1,0,0);


        play( idle );
    }
}