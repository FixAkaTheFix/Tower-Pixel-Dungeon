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

import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.sprites.BruteSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerShopKeeper extends NewShopKeeper {

    {
        spriteClass = BruteSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public ArrayList<Item> generateItems() {
        ArrayList<Item> itemsToSpawn = new ArrayList<>();
        if (Dungeon.isChallenged(Challenges.BOMBARDA_MAXIMA)) {
            if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
                if (Dungeon.depth < 5) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                } else if (Dungeon.depth < 9) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                } else {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                }
            } else {
                if (Dungeon.depth < 5) {
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerWall());
                } else if (Dungeon.depth < 9) {
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerWall());
                    itemsToSpawn.add(new SpawnerCannon());
                } else {
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerWall());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerGrave());
                }
            }
        } else {
            if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
            if (Dungeon.depth < 5) {
                itemsToSpawn.add(Random.oneOf(new ScrollOfUpgrade(), new PotionOfStrength()));
                itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));

            } else if (Dungeon.depth < 9) {
                itemsToSpawn.add(Random.oneOf(new ScrollOfUpgrade(), new PotionOfStrength()));
                itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));

            } else {
                itemsToSpawn.add(Random.oneOf(new ScrollOfUpgrade(), new PotionOfStrength()));
                itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));
                itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.POTION),Generator.random(Generator.Category.SCROLL),Generator.random(Generator.Category.RING),Generator.random(Generator.Category.ARMOR),Generator.random(Generator.Category.WAND),Generator.random(Generator.Category.WEAPON)));

            }
        } else {
                if (Dungeon.depth < 5) {
                    itemsToSpawn.add(new SpawnerCrossbow());
                    itemsToSpawn.add(new SpawnerWand());
                    itemsToSpawn.add(new SpawnerWall());
                } else if (Dungeon.depth < 9) {
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
            }
        }
        return itemsToSpawn;
    }
}
