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
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.items.Ankh;
import com.fixakathefix.towerpixeldungeon.items.ArcaneResin;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.LiquidMetal;
import com.fixakathefix.towerpixeldungeon.items.Stylus;
import com.fixakathefix.towerpixeldungeon.items.Torch;
import com.fixakathefix.towerpixeldungeon.items.food.Berry;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.potions.AlchemicalCatalyst;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.ExoticPotion;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.fixakathefix.towerpixeldungeon.items.quest.CeremonialCandle;
import com.fixakathefix.towerpixeldungeon.items.quest.CorpseDust;
import com.fixakathefix.towerpixeldungeon.items.quest.CorruptedOoze;
import com.fixakathefix.towerpixeldungeon.items.quest.GooBlob;
import com.fixakathefix.towerpixeldungeon.items.quest.MetalShard;
import com.fixakathefix.towerpixeldungeon.items.quest.Vilebloom;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.fixakathefix.towerpixeldungeon.items.spells.AquaBlast;
import com.fixakathefix.towerpixeldungeon.items.spells.ArcaneCatalyst;
import com.fixakathefix.towerpixeldungeon.items.spells.CurseInfusion;
import com.fixakathefix.towerpixeldungeon.items.spells.Recycle;
import com.fixakathefix.towerpixeldungeon.items.spells.SummonElemental;
import com.fixakathefix.towerpixeldungeon.items.spells.TelekineticGrab;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemAttack;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemHealing;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.fixakathefix.towerpixeldungeon.levels.Arena7;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Sungrass;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ShopkeeperSprite;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
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

        ArrayList<Item> itemsToSpawn = new ArrayList<>();
        if(Dungeon.level instanceof Arena7 && Dungeon.level.mode == WndModes.Modes.CHALLENGE){
            itemsToSpawn.add(Random.oneOf(Generator.randomUsingDefaults(Generator.Category.SCROLL2)));
            itemsToSpawn.add(Random.oneOf(Generator.randomUsingDefaults(Generator.Category.SCROLL2)));
            itemsToSpawn.add(Random.oneOf(Generator.randomUsingDefaults(Generator.Category.SCROLL2)));
            itemsToSpawn.add(Random.oneOf(Generator.randomUsingDefaults(Generator.Category.SCROLL2)));
            itemsToSpawn.add(Random.oneOf(Generator.randomUsingDefaults(Generator.Category.SCROLL2)));
        } else if (Dungeon.depth < 16) {
            itemsToSpawn.add(eliteShopItem());
            itemsToSpawn.add(normalShopItem());
            itemsToSpawn.add(normalShopItem());
            itemsToSpawn.add(normalShopItem());
            itemsToSpawn.add(normalShopItem());
        } else {
            itemsToSpawn.add(eliteShopItem());
            itemsToSpawn.add(normalSecondTierShopItem());
            itemsToSpawn.add(normalSecondTierShopItem());
            itemsToSpawn.add(normalSecondTierShopItem());
            itemsToSpawn.add(normalSecondTierShopItem());
        }
        return itemsToSpawn;
    }

    private Item normalShopItem(){
        return Random.oneOf(
                Generator.randomUsingDefaults(Generator.Category.POTION),
                Generator.randomUsingDefaults(Generator.Category.SEED),
                Generator.randomUsingDefaults(Generator.Category.FOOD),
                Generator.randomUsingDefaults(Generator.Category.SCROLL2),
                Generator.randomUsingDefaults(Generator.Category.SCROLL2),
                Generator.randomUsingDefaults(Generator.Category.RING),
                Generator.randomUsingDefaults(Generator.Category.STONE),
                Generator.randomUsingDefaults(Generator.Category.WAND),
                Generator.randomUsingDefaults(Generator.Category.DART),
                Generator.randomUsingDefaults(Generator.Category.MISSILE),
                Generator.randomUsingDefaults(Generator.Category.BOMB),
                Random.oneOf(
                        //random garbage
                        new MetalShard(), new GooBlob(),//these 2 are useless
                        new CorpseDust(), new CeremonialCandle(),//these 2 give achievements on being picked up
                        new Vilebloom(), new CorruptedOoze(),//these 2 may give a glass cannon effect


                        //non-garbage random item
                        new Stylus(), new ArcaneResin(),
                        new AlchemicalCatalyst(), new ArcaneCatalyst(), new Honeypot(),
                        new LiquidMetal(), new LiquidMetal(), new LiquidMetal(), new LiquidMetal(), new LiquidMetal()),//higher (30%) chance for tower-healing liquid metal
                Generator.randomUsingDefaults(Generator.Category.ARMOR).upgrade(Random.NormalIntRange(0,2)).identify(),
                Generator.randomUsingDefaults(Generator.Category.WEAPON).upgrade(Random.NormalIntRange(0,1)).identify()
        );
    }
    public static Item normalSecondTierShopItem(){
        return Random.oneOf(
                Generator.randomUsingDefaults(Generator.Category.POTION),
                Generator.randomUsingDefaults(Generator.Category.SEED),
                Generator.randomUsingDefaults(Generator.Category.FOOD),
                Generator.randomUsingDefaults(Generator.Category.SCROLL2),
                Generator.randomUsingDefaults(Generator.Category.SCROLL2),
                Generator.randomUsingDefaults(Generator.Category.RING).upgrade(Random.NormalIntRange(0,1)).identify(),
                Reflection.newInstance(ExoticPotion.regToExo.get(Generator.randomUsingDefaults(Generator.Category.POTION).getClass())),
                Generator.randomUsingDefaults(Generator.Category.STONE),
                Generator.randomUsingDefaults(Generator.Category.WAND),
                Generator.randomUsingDefaults(Generator.Category.DART),
                Generator.randomUsingDefaults(Generator.Category.MISSILE),
                Generator.randomUsingDefaults(Generator.Category.BOMB),
                new LiquidMetal(),
                Random.oneOf(
                        //random garbage
                        new Dart(), new Torch(),//these 2 are changed from blob & shard to be more.. functional
                        new CorpseDust(), new CeremonialCandle(),
                        new Vilebloom(), new CorruptedOoze(),

                        //non-garbage random item
                        new Stylus(), new ArcaneResin(),
                        new AlchemicalCatalyst(), new ArcaneCatalyst(), new Honeypot(),
                        new LiquidMetal(), new LiquidMetal(), new LiquidMetal(), new LiquidMetal(), new LiquidMetal()),//higher (30%) chance for tower-healing liquid metal

                Random.oneOf(new TelekineticGrab(), new SummonElemental(), new AquaBlast(), new CurseInfusion(), new Recycle(),
                        new SpawnerTotemAttack(), new SpawnerTotemNecrotic(), new SpawnerTotemHealing(), new SpawnerTotemShield()),
                Generator.randomUsingDefaults(Generator.Category.ARMOR).upgrade(Random.NormalIntRange(0,4)).identify(),
                Generator.randomUsingDefaults(Generator.Category.WEAPON).upgrade(Random.NormalIntRange(0,3)).identify()
        );
    }
    public static Item eliteShopItem(){
        return Random.oneOf(
                new ElixirOfHoneyedHealing(),
                new PotionOfShielding(),
                new PotionOfStrength(),
                new MeatPie(),
                new ScrollOfUpgrade(),
                new ScrollOfEnchantment(),
                new Ankh(),
                new ElixirOfHoneyedHealing(),
                new PotionOfShielding(),
                new PotionOfStrength(),
                new MeatPie(),
                new ScrollOfUpgrade(),
                new ScrollOfEnchantment(),
                new Ankh(),
                TowerInfo.getHeroAbility(Dungeon.level.slot1),
                TowerInfo.getHeroAbility(Dungeon.level.slot2),
                TowerInfo.getHeroAbility(Dungeon.level.slot3),
                TowerInfo.getHeroAbility(Dungeon.level.slot4),
                Generator.randomUsingDefaults(Generator.Category.SCROLL2),
                Generator.randomUsingDefaults(Generator.Category.ARTIFACT)

        );
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