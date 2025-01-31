package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class GorematiaSpiritSprite extends MobSprite{

        public GorematiaSpiritSprite() {
            super();

            texture( Assets.Sprites.IT );

            TextureFilm frames = new TextureFilm( texture, 16, 16 );

            idle = new Animation( 4, true );
            idle.frames( frames, 22,22,22,22,22,22,22,24,25,22,22,22,22,22,27);

            run = new Animation( 6, true );
            run.frames( frames, 0,1,2,3,4,5,6,7,8,9,10,11);

            attack = new Animation( 30, false );
            attack.frames( frames, 12,13,14,15,16,17,18,19,20,21,22);

            die = new Animation( 12, false );
            die.frames( frames, 28,28,28,28,28,29,29,29,29,30,30,30,30,31,30,31,30,31,30,31,27,26,25,24,23,24,26,27,24);

            play( idle );
        }
}
