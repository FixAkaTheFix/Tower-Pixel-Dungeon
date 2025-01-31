package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class SkeletonWarriorSprite extends MobSprite {

    public SkeletonWarriorSprite() {
        super();

        texture( Assets.Sprites.SKELETON );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 12, true );
        idle.frames( frames, 17,17,17,17,19,19,19,17);

        run = new Animation( 15, true );
        run.frames( frames, 21,22,23,24,25,26);

        attack = new Animation( 15, false );
        attack.frames( frames, 31,32,33);

        die = new Animation( 12, false );
        die.frames( frames, 27,28,29,30);

        play( idle );
    }

    @Override
    public void die() {
        super.die();
        if (Dungeon.level.heroFOV[ch.pos]) {
            emitter().burst( Speck.factory( Speck.BONE ), 6 );
        }
    }

    @Override
    public int blood() {
        return 0xFFcccccc;
    }
}
