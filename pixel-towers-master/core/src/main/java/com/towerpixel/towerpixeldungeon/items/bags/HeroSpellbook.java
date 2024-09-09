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

package com.towerpixel.towerpixeldungeon.items.bags;

import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.herospells.HeroSpell;
import com.towerpixel.towerpixeldungeon.items.quest.GooBlob;
import com.towerpixel.towerpixeldungeon.items.quest.MetalShard;
import com.towerpixel.towerpixeldungeon.items.stones.Runestone;
import com.towerpixel.towerpixeldungeon.items.wands.Wand;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.towerpixel.towerpixeldungeon.plants.Plant;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

import java.util.ArrayList;

public class HeroSpellbook extends Bag {

    {
        image = ItemSpriteSheet.MASTERY;

        unique = true;
        keptThoughLostInvent = true;

    }

    @Override
    public boolean canHold( Item item ) {
        if (item instanceof HeroSpell){
            return super.canHold(item);
        } else {
            return false;
        }
    }
    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        return actions;
    }

    public int capacity(){
        return 19;
    }

    @Override
    public int value() {
        return 30;
    }
}