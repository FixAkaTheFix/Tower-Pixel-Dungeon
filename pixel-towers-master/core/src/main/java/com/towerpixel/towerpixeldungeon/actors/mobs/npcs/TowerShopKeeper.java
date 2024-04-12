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

package com.towerpixel.towerpixeldungeon.actors.mobs.npcs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.sprites.BruteSprite;

import java.util.ArrayList;

public class TowerShopKeeper extends NewShopKeeper {

    {
        spriteClass = BruteSprite.class;

        properties.add(Property.IMMOVABLE);
    }

@Override
    public  ArrayList<Item> generateItems() {
        ArrayList<Item> itemsToSpawn = new ArrayList<>();
        if (Dungeon.depth<5) {

            itemsToSpawn.add(new SpawnerCrossbow());
            itemsToSpawn.add(new SpawnerWand());
            itemsToSpawn.add(new SpawnerWall());
        } else if (Dungeon.depth<9) {
            itemsToSpawn.add(new SpawnerCrossbow());
            itemsToSpawn.add(new SpawnerWand());
            itemsToSpawn.add(new SpawnerWall());
            itemsToSpawn.add(new SpawnerCannon());
        } else {
            itemsToSpawn.add(new SpawnerCrossbow());
            itemsToSpawn.add(new SpawnerWand());
            itemsToSpawn.add(new SpawnerWall());
            itemsToSpawn.add(new SpawnerCannon());
            itemsToSpawn.add(new SpawnerGrave());
        }
        return itemsToSpawn;
    }
}
