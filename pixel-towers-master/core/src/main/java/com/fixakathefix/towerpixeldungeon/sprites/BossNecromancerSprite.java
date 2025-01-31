/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
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

package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class BossNecromancerSprite extends MobSprite {

    private Animation abTpSkelly;
    private Animation abHeal;
    private Animation abSummonMinion;
    private Animation abTpSkellyCont;
    private Animation abHealCont;
    private Animation abSummonMinionCont;


    public BossNecromancerSprite(){
        super();

        texture( Assets.Sprites.BOSSNECRO );
        TextureFilm film = new TextureFilm( texture, 16, 22 );

        idle = new Animation( 1, true );
        idle.frames( film, 0, 0, 0, 1, 0, 0, 0, 0, 1 );

        run = new Animation( 8, true );
        run.frames( film, 0, 0, 0, 2, 3, 4 );

        abSummonMinion = new Animation( 4, false );
        abSummonMinion.frames( film, 5, 6, 7, 8 );

        die = new Animation( 10, false );
        die.frames( film, 9, 10, 11, 12 );

        attack = new Animation( 10, false );
        attack.frames( film, 13, 14, 15, 16, 17 );

        abTpSkelly = attack.clone();

        abTpSkellyCont = new Animation( 10, true );
        abTpSkellyCont.frames( film,  16, 17 );

        abHeal = new Animation(6,false);
        abHeal.frames(film, 18,19,20,21,20,21);

        abHealCont = new Animation(6,true);
        abHealCont.frames(film, 20,21);

        abSummonMinionCont = new Animation( 6, true );
        abSummonMinionCont.frames( film, 7, 8 );

        idle();
    }

    @Override
    public void attack( int cell ) {
        super.attack(cell);

        MagicMissile.boltFromChar( parent,
                MagicMissile.FIRE,
                this,
                cell,
                new Callback() {
                    @Override
                    public void call() {ch.onAttackComplete();
                    }
                } );
        Sample.INSTANCE.play( Assets.Sounds.BURNING );
    }

    public void abSummonMinion(Callback callback){
        animCallback = callback;

        play(abSummonMinion);

        for (int i: PathFinder.NEIGHBOURS8){
            CellEmitter.center(ch.pos+i).burst(Speck.factory(Speck.BONE), 2);
        }
    }
    public void abTpSkelly(){
        play(abTpSkelly);
    }
    public void abHeal() {
        play(abHeal);
        ch.sprite.emitter().start(ElmoParticle.FACTORY, 0.05f, 3);
    }
    @Override
    public void onComplete(Animation anim) {
        super.onComplete(anim);
        if (anim == abHeal||anim == abHealCont){
            play(abHealCont);
        } else if (anim == abSummonMinion || anim == abSummonMinionCont){
            play(abSummonMinionCont);
        } else if (anim == abTpSkelly){
            play(abTpSkellyCont);
        } else {
                idle();
        }
    }
}

