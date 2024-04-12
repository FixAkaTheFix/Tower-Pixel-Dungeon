package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.TextureFilm;

public abstract class TowerTotemSprite extends CharSprite{

    protected abstract int texOffset();

    public TowerTotemSprite() {
        super();

        int c = texOffset();

        texture( Assets.Sprites.TOWERTOTEM );

        TextureFilm frames = new TextureFilm( texture, 20, 20 );

        idle = new Animation( 5, true );
        idle.frames( frames, c+0, c+1, c+2, c+3, c+4, c+3, c+2, c+1 );

        attack = new Animation( 12, false );
        attack.frames( frames, c+3, c+4, c+3 );

        zap = attack.clone();

        die = new Animation( 12, false );
        die.frames( frames, c+5, c+6, c+7 );

        play( idle );
    }
    public static class THealing extends TowerTotemSprite {
        @Override
        protected int texOffset() {
            return 0;
        }
    }
    public static class TNecrotic extends TowerTotemSprite {
        @Override
        protected int texOffset() {
            return 20;
        }
    }
    public static class TAttack extends TowerTotemSprite {
        @Override
        protected int texOffset() {
            return 40;
        }
    }
    public static class TShield extends TowerTotemSprite {
        @Override
        protected int texOffset() {
            return 60;
        }
    }
}
