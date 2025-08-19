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

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ElementalSprite;
import com.fixakathefix.towerpixeldungeon.sprites.WraithSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Wisp extends Mob {



    {
        spriteClass = ElementalSprite.Wisp.class;

        HP = HT = 1 + Dungeon.depth;

        defenseSkill = 20;

        flying = true;
    }


    @Override
    public int damageRoll() {
        return Random.NormalIntRange(1, Dungeon.depth);
    }

    @Override
    public int attackSkill( Char target ) {
        return 15;
    }


    public static void spawnAround( int pos ) {
        for (int n : PathFinder.NEIGHBOURS4) {
            spawnAt( pos + n );
        }
    }

    public static Wraith spawnAt( int pos ) {
        if ((!Dungeon.level.solid[pos] || Dungeon.level.passable[pos]) && Actor.findChar( pos ) == null) {

            Wraith w = new Wraith();
            w.adjustStats( Dungeon.scalingDepth() );
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w );
            Dungeon.level.occupyCell(w);

            w.sprite.alpha( 0 );
            w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

            w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

            return w;
        } else {
            return null;
        }
    }

    @Override
    public void die(Object cause) {
        if (alignment == Alignment.ENEMY){
            Dungeon.gold++;
            Item.updateQuickslot();
        }

        super.die(cause);
    }
}
