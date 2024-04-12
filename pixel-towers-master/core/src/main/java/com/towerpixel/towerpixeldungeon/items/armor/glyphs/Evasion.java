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

package com.towerpixel.towerpixeldungeon.items.armor.glyphs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.armor.Armor;
import com.towerpixel.towerpixeldungeon.items.armor.Armor.Glyph;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Evasion extends Glyph {

    private static ItemSprite.Glowing VOID = new ItemSprite.Glowing( 0x330033, 0.4f );

    @Override
    public int proc( Armor armor, Char attacker, Char defender, int damage) {

        int level = Math.max( 0, armor.buffedLvl() );
        // lvl 0 - 10%
        // lvl 1 - 12%
        // lvl 2 - 14% max 30%
        float procChance = (level+5f)/(level+50f) * procChanceMultiplier(defender);
        if (procChance > 0.3f) procChance = 0.3f;//needed to not become deathless using this
        if (Random.Float() < procChance && defender instanceof Hero) {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[hero.pos+n] && Actor.findChar( hero.pos+n ) == null) {
                    candidates.add( hero.pos+n );
                }
            }

            if (!candidates.isEmpty()) {
                int pos;
                pos = Random.element( candidates );

                hero.sprite.jump(hero.pos, pos, new Callback() {
                    @Override
                    public void call() {
                        hero.pos = pos;
                        hero.speak("dodged", 0xFFFF00);
                    }
                });
                Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
                CellEmitter.get(hero.pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
                return 0;
            }
        }
        return damage;
    }

    @Override
    public Glowing glowing() {
        return VOID;
    }
}