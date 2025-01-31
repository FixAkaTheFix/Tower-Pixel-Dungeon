package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;

public class SkeletonWarriorShieldedSprite extends MobSprite {

    public SkeletonWarriorShieldedSprite() {
        super();

        texture( Assets.Sprites.SKELETON );

        TextureFilm frames = new TextureFilm( texture, 12, 15 );

        idle = new Animation( 12, true );
        idle.frames( frames, 34,34,34,34,36,36,36,34);

        run = new Animation( 15, true );
        run.frames( frames, 38,39,40,41,42,43);

        attack = new Animation( 15, false );
        attack.frames( frames, 48,49,50);

        die = new Animation( 12, false );
        die.frames( frames, 44,45,46,47);

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
