package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class PortalSprite  extends MobSprite{
    public PortalSprite() {
        super();

        texture( Assets.Sprites.PORTAL );

        TextureFilm frames = new TextureFilm( texture, 18, 22 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0,1,2,3,4,5,6,7);

        run = idle.clone();

        attack = idle.clone();

        die = new Animation( 1, false );
        die.frames( frames, 1 );

        play( idle );
    }
}