package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Random;

public class IceWallSprite extends MobSprite{
    private Emitter particles;

    public IceWallSprite() {
        super();

        texture( Assets.Sprites.ICE_WALL );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );
        int i = Random.NormalIntRange(0,3);

        idle = new MovieClip.Animation( 1, true );
        idle.frames( frames, i);

        run = idle.clone();
        attack = idle.clone();
        die = idle.clone();

        play( idle );
    }
    @Override
    public void kill() {
        super.kill();
        if (particles != null){
            particles.killAndErase();
        }
    }
    protected Emitter createEmitter() {
        Emitter emitter = emitter();
        emitter.pour( MagicMissile.MagicParticle.FACTORY, 0.06f );
        return emitter;
    }

    @Override
    public void link( Char ch ) {
        super.link( ch );

        if (particles == null) {
            particles = createEmitter();
        }
    }

    @Override
    public void update() {
        super.update();

        if (particles != null){
            particles.visible = visible;
        }
    }

    @Override
    public void die() {
        super.die();
        if (particles != null){
            particles.on = false;
        }
    }

    @Override
    public int blood() {
        return 0xFF8EE3FF;
    }
}
