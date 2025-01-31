package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class PortalSprite  extends MobSprite{

    public Animation collapse;
    public PortalSprite() {
        super();

        texture( Assets.Sprites.PORTAL );

        TextureFilm frames = new TextureFilm( texture, 18, 22 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0,1,2,3,4,5,6,7);

        run = idle.clone();

        attack = idle.clone();
        collapse = new Animation(2, true);
        collapse.frames(frames,8,9,10,11,12,12,11,10,9,8);

        die = new Animation( 8, false );
        die.frames( frames, 13,14,15 );

        play( idle );
    }
}