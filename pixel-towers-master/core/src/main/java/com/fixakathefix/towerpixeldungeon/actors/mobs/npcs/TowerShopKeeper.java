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

package com.fixakathefix.towerpixeldungeon.actors.mobs.npcs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.sprites.BruteSprite;
import com.fixakathefix.towerpixeldungeon.sprites.GnollShopkeeperSprite;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerShopKeeper extends NewShopKeeper {

    {
        spriteClass = GnollShopkeeperSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public ArrayList<Item> generateItems() {
        ArrayList<Item> itemsToSpawn = new ArrayList<>();

        if(Dungeon.depth==7 && Dungeon.level.mode == WndModes.Modes.CHALLENGE){
            itemsToSpawn.add(Random.oneOf(
                    TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot4)
            ));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
        } else {
            itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot1));
            itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot2));
            itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot3));
            itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot4));
        }


        return itemsToSpawn;
    }
}
