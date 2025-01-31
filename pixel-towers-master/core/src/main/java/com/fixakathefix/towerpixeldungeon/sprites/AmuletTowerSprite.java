package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public class AmuletTowerSprite  extends MobSprite{
    public AmuletTowerSprite() {
        super();

        texture( Assets.Sprites.SPINNINGAMULET );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new Animation( 10, true );
        idle.frames( frames, 0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);

        run = idle.clone();

        attack = idle.clone();

        die = new Animation( 1, false );
        die.frames( frames, 1 );

        play( idle );
    }
}
