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
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.Gnoll;
import com.towerpixel.towerpixeldungeon.items.ArcaneResin;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.Stylus;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.food.Berry;
import com.towerpixel.towerpixeldungeon.items.food.MeatPie;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.ExoticPotion;
import com.towerpixel.towerpixeldungeon.items.quest.CeremonialCandle;
import com.towerpixel.towerpixeldungeon.items.quest.CorpseDust;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfBlast;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.RatKingSprite;
import com.towerpixel.towerpixeldungeon.sprites.ShopkeeperSprite;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class NormalShopKeeper extends NewShopKeeper {

    {
        spriteClass = ShopkeeperSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    @Override
    public ArrayList<Item> generateItems() {
        int type = Random.Int(3);
        ArrayList<Item> itemsToSpawn = new ArrayList<>();
        if(Dungeon.depth==7 && Dungeon.level.mode == WndModes.Modes.CHALLENGE){
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
            itemsToSpawn.add(Random.oneOf(Generator.random(Generator.Category.SCROLL)));
        } else
        if (Dungeon.depth < 16) {
            switch (type) {
                case 0: {
                    itemsToSpawn.add(new PotionOfHealing());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(Random.oneOf(new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new Stylus(), new CorpseDust(), new ArcaneResin(), new CeremonialCandle()));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.RING).upgrade(Random.IntRange(0, 1)).identify());
                    break;
                }
                case 1: {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.POTION));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WEAPON).upgrade(Random.IntRange(0, 2)).identify());
                    break;
                }
                case 2: {
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SCROLL));
                    itemsToSpawn.add(new Berry());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.BOMB));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.ARMOR).upgrade(Random.IntRange(0, 2)).identify());
                    break;
                }
                case 3: {
                    itemsToSpawn.add(new ElixirOfHoneyedHealing());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.MISSILE));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WAND).upgrade(Random.IntRange(0, 2)).identify());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SEED));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.POTION));
                    break;
                }
            }
        } else {
            switch (type) {
                case 0: {
                    itemsToSpawn.add(new PotionOfHealing());
                    itemsToSpawn.add(new MeatPie());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(new Stylus());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.RING).upgrade(Random.IntRange(1, 3)).identify());
                    break;
                }
                case 1: {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.POTION));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WEAPON).upgrade(Random.IntRange(1, 3)).identify());
                    break;
                }
                case 2: {
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SCROLL));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.BOMB));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.ARMOR).upgrade(Random.IntRange(1, 3)).identify());
                    break;
                }
                case 3: {
                    itemsToSpawn.add(new Sungrass.Seed());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.MISSILE).upgrade(Random.IntRange(0, 4)));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WAND).upgrade(Random.IntRange(0, 4)).identify());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SEED));
                    itemsToSpawn.add(Reflection.newInstance(ExoticPotion.regToExo.get(Generator.randomUsingDefaults(Generator.Category.POTION).getClass())));
                    break;
                }
            }
        }
        return itemsToSpawn;
    }

    @Override
    public boolean interact(Char c) {
        if (c == Dungeon.hero) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndDialogueWithPic(sprite(), "Shopkeeper",
                            new String[]{
                                    Messages.get(NormalShopKeeper.class, "line1"),
                                    Messages.get(NormalShopKeeper.class, "line2"),
                                    Messages.get(NormalShopKeeper.class, "line3"),
                            }
                    ));
                }
            });
        }
        return true;
    }

}