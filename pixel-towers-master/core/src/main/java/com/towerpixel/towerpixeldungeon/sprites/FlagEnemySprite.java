package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class FlagEnemySprite  extends MobSprite {
    public FlagEnemySprite() {
        super();

        texture( Assets.Sprites.FLAGENEMY );

        TextureFilm frames = new TextureFilm( texture, 18, 22 );

        idle = new MovieClip.Animation( 1, true );
        idle.frames( frames, 0);

        run = new MovieClip.Animation( 1, true );
        run.frames( frames, 0 );

        attack = new MovieClip.Animation( 1, false );
        attack.frames( frames, 0 );

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 0 );

        play( idle );
    }
}
