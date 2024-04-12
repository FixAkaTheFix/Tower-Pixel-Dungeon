package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.RainbowParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Random;

public class TowerWandPrismaticSprite extends MobSprite {

    private Animation cast;

    public TowerWandPrismaticSprite() {
        super();

        texture( Assets.Sprites.TOWERWAND );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 4, true );
        idle.frames( frames, 80,81,82,83);

        run = idle.clone();

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 84,85,86,87);

        cast = attack.clone();

        die = new MovieClip.Animation( 1, false );
        die.frames( frames, 80 );

        play( idle );
    }
    private void affectMap(Ballistica beam){
        for (int c : beam.subPath(0, beam.dist)){
            if (!Dungeon.level.insideMap(c)){
                continue;
            }

            CellEmitter.center(c).burst( RainbowParticle.BURST, Random.IntRange( 1, 2 ) );
        }
        GameScene.updateFog();
    }

    public void onZap(Ballistica beam) {
        affectMap(beam);
        Char ch = Actor.findChar(beam.collisionPos);

    }


}