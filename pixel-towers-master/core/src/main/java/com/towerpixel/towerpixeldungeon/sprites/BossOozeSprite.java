/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.sprites;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.items.quest.GooBlob;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.Emitter.Factory;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossOozeSprite extends MobSprite {

    private Animation jumpAttack;

    private Animation cast;

    private Animation jump;

    private Emitter spray;

    public BossOozeSprite() {
        super();

        texture( Assets.Sprites.BOSSOOZE );

        TextureFilm frames = new TextureFilm( texture, 60, 44 );

        idle = new Animation( 15, true );
        idle.frames( frames, 4, 3,2,1, 0, 0, 1,2,3 );

        run = new Animation( 24, true );
        run.frames( frames, 5,4,3,2,3,4 );


        jump = new Animation ( 25, false );
        jump.frames( frames, 4, 5,6,7,8);

        attack = new Animation( 25, false );
        attack.frames( frames, 15,16,17,18,19,20,21,22,22,22);

        die = new Animation( 10, false );
        die.frames( frames, 9,10,11,12,13,14);

        play(idle);

        spray = centerEmitter();
        spray.autoKill = false;
        spray.pour( OozeParticle.FACTORY, 0.04f );
        spray.on = false;
    }

    @Override
    public void link(Char ch) {
        super.link(ch);
        if (ch.HP*2 <= ch.HT)
            spray(true);
    }

    @Override
    public int blood() {
        return 0xFF000000;
    }

    public void spray(boolean on){
        spray.on = on;
    }

    @Override
    public void update() {
        super.update();
        spray.pos(center());
        spray.visible = visible;
    }

    public static class OozeParticle extends PixelParticle.Shrinking {

        public static final Emitter.Factory FACTORY = new Factory() {
            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                ((OozeParticle)emitter.recycle( OozeParticle.class )).reset( x, y );
            }
        };

        public OozeParticle() {
            super();

            color( 0x111111 );
            lifespan = 0.4f;

            acc.set( 0, +50 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan;

            size = 4;
            speed.polar( -Random.Float( PointF.PI ), Random.Float( 32, 48 ) );
        }

        @Override
        public void update() {
            super.update();
            float p = left / lifespan;
            am = p > 0.5f ? (1 - p) * 2f : 1;
        }
    }

    @Override
    public void jump(int from, int to, Callback callback) {
        play(jump);
        super.jump(from, to, callback);
    }

    @Override
    public void attack( int cell ) {
        ArrayList<Integer> candidates = new ArrayList<>();
        //all cells around the goo
        candidates.add(ch.pos+2);
        candidates.add(ch.pos+2-Dungeon.level.width());
        candidates.add(ch.pos-2);
        candidates.add(ch.pos-2-Dungeon.level.width());
        candidates.add(ch.pos-Dungeon.level.width());
        candidates.add(ch.pos+1+Dungeon.level.width());
        candidates.add(ch.pos-1+Dungeon.level.width());
        candidates.add(ch.pos+2+Dungeon.level.width());
        candidates.add(ch.pos-2+Dungeon.level.width());
        candidates.add(ch.pos+2*Dungeon.level.width());

        candidates.add(ch.pos-2*Dungeon.level.width());
        candidates.add(ch.pos+1-2*Dungeon.level.width());
        candidates.add(ch.pos-1-2*Dungeon.level.width());
        candidates.add(ch.pos+2-2*Dungeon.level.width());
        candidates.add(ch.pos-2-2*Dungeon.level.width());
        if (candidates.contains(cell))  {
            super.attack( cell );
        } else {

            play( attack );
            ((MissileSprite)parent.recycle( MissileSprite.class )).
                    reset( this, cell, new GooBlob(), new Callback() {
                        @Override
                        public void call() {
                            play(idle);
                            ch.onAttackComplete();
                        }
                    } );
            turnTo( ch.pos , cell );


        }
    }

    @Override
    public void showStatus(int color, String text, Object... args) {
        if (text == null) {
            super.showStatus(color, " ", args);
        } else super.showStatus(color, text, args);
    }
}
