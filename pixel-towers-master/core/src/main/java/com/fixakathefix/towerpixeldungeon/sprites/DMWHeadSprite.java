package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWHead;
import com.fixakathefix.towerpixeldungeon.effects.Lightning;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;

public class DMWHeadSprite extends MobSprite {

    public DMWHeadSprite() {
        super();

        texture( Assets.Sprites.DM300 );

        TextureFilm frames = new TextureFilm( texture, 25, 22 );

        idle = new Animation( 2, true );
        idle.frames( frames, 24, 25);

        run = new Animation( 4, true );
        run.frames( frames, 24, 25);

        attack = new Animation( 10, false );
        attack.frames( frames, 27, 28, 29, 30);

        die = new Animation( 12, false );
        die.frames( frames, 27,29,30,27,25,24,26,24,26,24,26,24,26,24,26,24,26,24,26,24,26,24,26 );

        zap = new Animation( 7, false );
        zap.frames( frames, 27, 28, 29, 30 );

        play( idle );
    }
    public void zap( int pos ) {

        Char enemy = Actor.findChar(pos);

        //shoot lightning from eye, not sprite center.
        PointF origin = center();
        if (flipHorizontal){
            origin.y -= 12*scale.y;
            origin.x -= 1*scale.x;
        } else {
            origin.y -= 12*scale.y;
            origin.x += 1*scale.x;
        }
        if (enemy != null) {
            parent.add(new Lightning(origin, enemy.sprite.destinationCenter(), (DMWHead) ch));
        } else {
            parent.add(new Lightning(origin, pos, (DMWHead) ch));
        }
        Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );

        super.zap( ch.pos );
        flash();
    }
    @Override
    public void die() {
        emitter().burst( Speck.factory( Speck.WOOL ), 10 );
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
        return 0xFFFFEA80;
    }
}