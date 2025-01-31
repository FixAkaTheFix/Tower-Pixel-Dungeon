package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class LicteriaSprite extends CharSprite{


    {
        //perspectiveRaise = 10 / 16f; //2 pixels
    }
    public LicteriaSprite() {
        super();

        texture( Assets.Sprites.LICTERIA );

        TextureFilm frames = new TextureFilm( texture, 25, 17 );

        idle = new MovieClip.Animation( 2, true );
        idle.frames( frames, 0, 1, 2 );

        run = idle.clone();

        attack = new MovieClip.Animation( 6, false );
        attack.frames( frames, 0, 1, 2 );

        die = new MovieClip.Animation( 4, false );
        die.frames( frames, 3, 4, 5, 6 );

        play( idle );
    }
    @Override
    public void onComplete(Animation anim) {
        super.onComplete(anim);
        if (anim == attack){
            play(idle);
        }
    }
    @Override
    public int blood() {
        return 0xFF88CC44;
    }
}
