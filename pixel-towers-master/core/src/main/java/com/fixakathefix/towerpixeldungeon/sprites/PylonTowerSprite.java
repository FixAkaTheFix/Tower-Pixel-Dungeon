package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerPylon;
import com.fixakathefix.towerpixeldungeon.effects.Lightning;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;

public class PylonTowerSprite extends MobSprite {

    public PylonTowerSprite() {
        super();

        texture(Assets.Sprites.PYLON);

        TextureFilm frames = new TextureFilm(texture, 10, 20);

        idle = new MovieClip.Animation(1, true);
        idle.frames(frames, 0);

        attack = new MovieClip.Animation(1, true);
        attack.frames(frames, 1);

        zap = new Animation( 8, false );
        zap.frames( frames, 1 );

        run = idle.clone();

        die = idle.clone();

        play(idle);
    }

    public void zap( int pos ) {

        Char enemy = Actor.findChar(pos);

        PointF origin = center();
        if (flipHorizontal){
            origin.y -= 6*scale.y;
            origin.x -= 1*scale.x;
        } else {
            origin.y -= 8*scale.y;
            origin.x += 1*scale.x;
        }
        if (enemy != null) {
            parent.add(new Lightning(origin, enemy.sprite.destinationCenter(), (TowerPylon) ch));
        } else {
            parent.add(new Lightning(origin, pos, (TowerPylon) ch));
        }
        Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );

        super.zap( ch.pos );
        flash();
    }

    @Override
    public void die() {
        emitter().burst( Speck.factory( Speck.WOOL ), 5 );
        super.die();
    }

    @Override
    public void onComplete( Animation anim ) {
        if (anim == zap) {
            idle();
        }
        super.onComplete( anim );
    }

    @Override
    public int blood() {
        return 0xFFFFFF88;
    }
}