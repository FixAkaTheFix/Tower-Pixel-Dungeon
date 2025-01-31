package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class LicteriaLasherSprite extends MobSprite {

    private Animation appear;

    public LicteriaLasherSprite() {
        super();

        texture( Assets.Sprites.LICTERIALASHER );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 4, true );
        idle.frames( frames, 0,1,2);

        run = idle.clone();

        attack = new Animation( 10, false );
        attack.frames( frames, 3,4,5 );

        die = new Animation( 10, false );
        die.frames( frames, 6,7,8,9 );

        appear = new Animation(8, false);
        appear.frames( frames, 10,11,12,13 );

        play( appear );
    }

    @Override
    public void onComplete(Animation anim) {
        super.onComplete(anim);
        if (anim == appear){
            play(idle);
        }
    }


    @Override
    public int blood() {
        return 0xFF88CC44;
    }
}
