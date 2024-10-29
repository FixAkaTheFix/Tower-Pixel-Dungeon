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

package com.towerpixel.towerpixeldungeon.items;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.items.armor.Armor;
import com.towerpixel.towerpixeldungeon.items.armor.ClothArmor;
import com.towerpixel.towerpixeldungeon.items.armor.DuelistArmor;
import com.towerpixel.towerpixeldungeon.items.armor.HuntressArmor;
import com.towerpixel.towerpixeldungeon.items.armor.LeatherArmor;
import com.towerpixel.towerpixeldungeon.items.armor.MageArmor;
import com.towerpixel.towerpixeldungeon.items.armor.MailArmor;
import com.towerpixel.towerpixeldungeon.items.armor.PlateArmor;
import com.towerpixel.towerpixeldungeon.items.armor.RogueArmor;
import com.towerpixel.towerpixeldungeon.items.armor.ScaleArmor;
import com.towerpixel.towerpixeldungeon.items.armor.WarriorArmor;
import com.towerpixel.towerpixeldungeon.items.artifacts.Artifact;
import com.towerpixel.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.towerpixel.towerpixeldungeon.items.artifacts.CloakOfShadows;
import com.towerpixel.towerpixeldungeon.items.artifacts.DriedRose;
import com.towerpixel.towerpixeldungeon.items.artifacts.EtherealChains;
import com.towerpixel.towerpixeldungeon.items.artifacts.HornOfPlenty;
import com.towerpixel.towerpixeldungeon.items.artifacts.MasterThievesArmband;
import com.towerpixel.towerpixeldungeon.items.artifacts.SandalsOfNature;
import com.towerpixel.towerpixeldungeon.items.artifacts.TalismanOfForesight;
import com.towerpixel.towerpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.towerpixel.towerpixeldungeon.items.artifacts.UnstableSpellbook;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Firebomb;
import com.towerpixel.towerpixeldungeon.items.bombs.FrostBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.HolyBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.RegrowthBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.ShockBomb;
import com.towerpixel.towerpixeldungeon.items.food.Food;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.food.Pasty;
import com.towerpixel.towerpixeldungeon.items.potions.Potion;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfExperience;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfFrost;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHaste;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfInvisibility;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfParalyticGas;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfPurity;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.rings.Ring;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfAccuracy;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfArcana;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfElements;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfEnergy;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfEvasion;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfForce;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfFuror;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfHaste;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfLethality;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfMight;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfSharpshooting;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfTenacity;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfWealth;
import com.towerpixel.towerpixeldungeon.items.scrolls.Scroll;

import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTerror;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.stones.Runestone;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfFear;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfAggression;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfAugmentation;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfBlast;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfBlink;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfClairvoyance;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfDeepSleep;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfDisarming;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfEnchantment;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfFlock;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfShock;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.towerspawners.TowerSpawner;
import com.towerpixel.towerpixeldungeon.items.wands.Wand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfCorrosion;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfCorruption;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfDisintegration;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfFireblast;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfFrost;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLightning;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfPrismaticLight;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfRegrowth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfTransfusion;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.*;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Bolas;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.FishingSpear;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ForceCube;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Javelin;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Kunai;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Shuriken;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingClub;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingSpear;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Tomahawk;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.Trident;
import com.towerpixel.towerpixeldungeon.plants.Blindweed;
import com.towerpixel.towerpixeldungeon.plants.Mageroyal;
import com.towerpixel.towerpixeldungeon.plants.Earthroot;
import com.towerpixel.towerpixeldungeon.plants.Fadeleaf;
import com.towerpixel.towerpixeldungeon.plants.Firebloom;
import com.towerpixel.towerpixeldungeon.plants.Icecap;
import com.towerpixel.towerpixeldungeon.plants.Plant;
import com.towerpixel.towerpixeldungeon.plants.Rotberry;
import com.towerpixel.towerpixeldungeon.plants.Sorrowmoss;
import com.towerpixel.towerpixeldungeon.plants.Starflower;
import com.towerpixel.towerpixeldungeon.plants.Stormvine;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;
import com.watabou.utils.GameMath;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Generator {

	public enum Category {

		ITEM    ( 0, 0, Item.class       ),
		WEAPON	( 2, 2, MeleeWeapon.class),
		WEP_T1	( 0, 0, MeleeWeapon.class),
		WEP_T2	( 0, 0, MeleeWeapon.class),
		WEP_T3	( 0, 0, MeleeWeapon.class),
		WEP_T4	( 0, 0, MeleeWeapon.class),
		WEP_T5	( 0, 0, MeleeWeapon.class),
		WEP_T6	( 0, 0, MeleeWeapon.class),
		WEP_T7	( 0, 0, MeleeWeapon.class),
		WEP_T8	( 0, 0, MeleeWeapon.class),
		WEP_T9	( 0, 0, MeleeWeapon.class),
		WEP_T10	( 0, 0, MeleeWeapon.class),
		WEP_T11	( 0, 0, MeleeWeapon.class),
		WEP_T12	( 0, 0, MeleeWeapon.class),
		WEP_T13	( 0, 0, MeleeWeapon.class),
		WEP_T14	( 0, 0, MeleeWeapon.class),
		WEP_T15	( 0, 0, MeleeWeapon.class),
		
		ARMOR	( 2, 1, Armor.class ),
		
		MISSILE ( 1, 2, MissileWeapon.class ),
		MIS_T1  ( 0, 0, MissileWeapon.class ),
		MIS_T2  ( 0, 0, MissileWeapon.class ),
		MIS_T3  ( 0, 0, MissileWeapon.class ),
		MIS_T4  ( 0, 0, MissileWeapon.class ),
		MIS_T5  ( 0, 0, MissileWeapon.class ),
		MIS_T6  ( 0, 0, MissileWeapon.class ),
		MIS_T7  ( 0, 0, MissileWeapon.class ),
		MIS_T8  ( 0, 0, MissileWeapon.class ),
		MIS_T9  ( 0, 0, MissileWeapon.class ),
		MIS_T10  ( 0, 0, MissileWeapon.class ),
		MIS_T11  ( 0, 0, MissileWeapon.class ),
		MIS_T12 ( 0, 0, MissileWeapon.class ),
		MIS_T13 ( 0, 0, MissileWeapon.class ),
		MIS_T14  ( 0, 0, MissileWeapon.class ),
		MIS_T15  ( 0, 0, MissileWeapon.class ),


		BOMB( 1, 1, Bomb.class),
		
		WAND	( 1, 1, Wand.class ),
		RING	( 1, 0, Ring.class ),
		ARTIFACT( 0, 1, Artifact.class),
		ARTIFACTNOCHAINS( 0, 0, Artifact.class),
		
		FOOD	( 0, 0, Food.class ),
		
		POTION	( 8, 8, Potion.class ),
		SEED	( 1, 1, Plant.Seed.class ),
		
		SCROLL	( 8, 8, Scroll.class ),
		STONE   ( 1, 1, Runestone.class),
		
		GOLD	( 10, 10,   Gold.class ),

		TOWER   (0,0, TowerSpawner.class),

		TOWERP2   (0,0, TowerSpawner.class),

		TOWERP3   (0,0, TowerSpawner.class);
		
		public Class<?>[] classes;

		//some item types use a deck-based system, where the probs decrement as items are picked
		// until they are all 0, and then they reset. Those generator classes should define
		// defaultProbs. If defaultProbs is null then a deck system isn't used.
		//Artifacts in particular don't reset, no duplicates!
		public float[] probs;
		public float[] defaultProbs = null;
		//These variables are used as a part of the deck system, to ensure that drops are consistent
		// regardless of when they occur (either as part of seeded levelgen, or random item drops)
		public Long seed = null;
		public int dropped = 0;

		//game has two decks of 35 items for overall category probs
		//one deck has a ring and extra armor, the other has an artifact and extra thrown weapon
		//Note that pure random drops only happen as part of levelgen atm, so no seed is needed here
		public float firstProb;
		public float secondProb;
		public Class<? extends Item> superClass;
		
		private Category( float firstProb, float secondProb, Class<? extends Item> superClass ) {
			this.firstProb = firstProb;
			this.secondProb = secondProb;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}

			//items without a category-defined order are sorted based on the spritesheet
			return Short.MAX_VALUE+item.image();
		}

		static {
			GOLD.classes = new Class<?>[]{
					Gold.class };
			GOLD.probs = new float[]{ 1 };
			
			POTION.classes = new Class<?>[]{
					PotionOfStrength.class, //2 drop every chapter, see Dungeon.posNeeded()
					PotionOfHealing.class,
					PotionOfMindVision.class,
					PotionOfFrost.class,
					PotionOfLiquidFlame.class,
					PotionOfToxicGas.class,
					PotionOfHaste.class,
					PotionOfInvisibility.class,
					PotionOfLevitation.class,
					PotionOfParalyticGas.class,
					PotionOfPurity.class,
					PotionOfExperience.class};
			POTION.defaultProbs = new float[]{ 1, 10, 10, 5, 4, 3, 7, 7, 2, 2, 5, 1 };
			POTION.probs = POTION.defaultProbs.clone();

			TOWER.classes = new Class<?>[]{
					SpawnerCrossbow.class,
					SpawnerWand.class,
					SpawnerWall.class,
					};
			TOWER.defaultProbs = new float[]{ 2 ,2 ,3};
			TOWER.probs = TOWER.defaultProbs.clone();

			TOWERP2.classes = new Class<?>[]{
					SpawnerCrossbow.class,
					SpawnerWand.class,
					SpawnerWall.class,
					SpawnerCannon.class};
			TOWERP2.defaultProbs = new float[]{ 2 ,2 ,3 ,2};
			TOWERP2.probs = TOWERP2.defaultProbs.clone();

			TOWERP3.classes = new Class<?>[]{
					SpawnerCrossbow.class,
					SpawnerWand.class,
					SpawnerWall.class,
					SpawnerCannon.class,
					SpawnerGrave.class};
			TOWERP3.defaultProbs = new float[]{ 2 ,2 ,3 ,2, 2};
			TOWERP3.probs = TOWER.defaultProbs.clone();


			
			SEED.classes = new Class<?>[]{
					Rotberry.Seed.class, //quest item
					Sungrass.Seed.class,
					Fadeleaf.Seed.class,
					Icecap.Seed.class,
					Firebloom.Seed.class,
					Sorrowmoss.Seed.class,
					Swiftthistle.Seed.class,
					Blindweed.Seed.class,
					Stormvine.Seed.class,
					Earthroot.Seed.class,
					Mageroyal.Seed.class,
					Starflower.Seed.class};
			SEED.defaultProbs = new float[]{ 0, 5, 5, 5, 2, 5, 5, 5, 5, 5, 5, 2 };
			SEED.probs = SEED.defaultProbs.clone();
			
			SCROLL.classes = new Class<?>[]{
					ScrollOfUpgrade.class, //1 drop every chapter, see Dungeon.souNeeded()
					ScrollOfAnimation.class,
					ScrollOfRemoveCurse.class,
					ScrollOfMirrorImage.class,
					ScrollOfRecharging.class,
					ScrollOfTeleportation.class,
					ScrollOfLullaby.class,
					ScrollOfMagicMapping.class,
					ScrollOfRage.class,
					ScrollOfSkulls.class,
					ScrollOfTerror.class,
					ScrollOfTransmutation.class
			};
			SCROLL.defaultProbs = new float[]{ 0, 6, 4, 3, 3, 3, 2, 2, 2, 2, 2, 1 };
			SCROLL.probs = SCROLL.defaultProbs.clone();
			
			STONE.classes = new Class<?>[]{
					StoneOfEnchantment.class,   //1 is guaranteed to drop on floors 6-19
					StoneOfIntuition.class,     //1 additional stone is also dropped on floors 1-3
					StoneOfDisarming.class,
					StoneOfFlock.class,
					StoneOfShock.class,
					StoneOfBlink.class,
					StoneOfDeepSleep.class,
					StoneOfClairvoyance.class,
					StoneOfAggression.class,
					StoneOfBlast.class,
					StoneOfFear.class,
					StoneOfAugmentation.class  //1 is sold in each shop
			};
			STONE.defaultProbs = new float[]{ 2, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2 };
			STONE.probs = STONE.defaultProbs.clone();

			BOMB.classes = new Class<?>[]{
					Bomb.class,
					Firebomb.class,
					RegrowthBomb.class,
					Bomb.DoubleBomb.class,
					HolyBomb.class,
					FrostBomb.class,
					ShockBomb.class
			};
			BOMB.probs = new float[]{5,4,4,4,4,3,4};

			WAND.classes = new Class<?>[]{
					WandOfMagicMissile.class,
					WandOfLightning.class,
					WandOfDisintegration.class,
					WandOfFireblast.class,
					WandOfCorrosion.class,
					WandOfBlastWave.class,
					WandOfLivingEarth.class,
					WandOfFrost.class,
					WandOfPrismaticLight.class,
					WandOfWarding.class,
					WandOfTransfusion.class,
					WandOfCorruption.class,
					WandOfRegrowth.class};
			WAND.probs = new float[]{ 4, 4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3 };
			
			//see generator.randomWeapon
			WEAPON.classes = new Class<?>[]{};
			WEAPON.probs = new float[]{};
			
			WEP_T1.classes = new Class<?>[]{
					WornShortsword.class,
					MagesStaff.class,
					Dagger.class,
					Gloves.class,
					Rapier.class
			};
			WEP_T1.probs = new float[]{ 1, 0, 1, 1, 1 };
			
			WEP_T2.classes = new Class<?>[]{
					Shortsword.class,
					HandAxe.class,
					Spear.class,
					Quarterstaff.class,
					Dirk.class,
					StoneHammer.class,
					DualHatchet.class,
					RedKnife.class,
					BlueKnife.class,
					CurvedKnife.class,
			};
			WEP_T2.probs = new float[]{ 6, 5, 5, 4, 4, 4, 4, 3, 3, 3};
			
			WEP_T3.classes = new Class<?>[]{
					Sword.class,
					Mace.class,
					Scimitar.class,
					RoundShield.class,
					Sai.class,
					Whip.class,
					Trisai.class,
					BoneMachete.class,
					GoldenSword.class
			};
			WEP_T3.probs = new float[]{ 6, 5, 5, 4, 4, 4, 4, 4, 1};
			
			WEP_T4.classes = new Class<?>[]{
					Longsword.class,
					BattleAxe.class,
					Flail.class,
					RunicBlade.class,
					AssassinsBlade.class,
					Crossbow.class
			};
			WEP_T4.probs = new float[]{ 6, 5, 5, 4, 4, 4 };
			
			WEP_T5.classes = new Class<?>[]{
					Greatsword.class,
					WarHammer.class,
					Glaive.class,
					Greataxe.class,
					Greatshield.class,
					Gauntlet.class
			};
			WEP_T5.probs = new float[]{ 6, 4, 5, 4, 4, 4 };
			WEP_T8.classes = new Class<?>[]{
					Banhammer.class,
					Infipike.class,
			};
			WEP_T8.probs = new float[]{ 5, 5 };
			
			//see Generator.randomArmor
			ARMOR.classes = new Class<?>[]{
					ClothArmor.class,
					LeatherArmor.class,
					MailArmor.class,
					ScaleArmor.class,
					PlateArmor.class,
					WarriorArmor.class,
					MageArmor.class,
					RogueArmor.class,
					HuntressArmor.class,
					DuelistArmor.class
			};
			ARMOR.probs = new float[]{ 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 };
			
			//see Generator.randomMissile
			MISSILE.classes = new Class<?>[]{};
			MISSILE.probs = new float[]{};
			
			MIS_T1.classes = new Class<?>[]{
					ThrowingStone.class,
					ThrowingKnife.class,
					ThrowingSpike.class
			};
			MIS_T1.probs = new float[]{ 6, 5 };
			
			MIS_T2.classes = new Class<?>[]{
					FishingSpear.class,
					ThrowingClub.class,
					Shuriken.class
			};
			MIS_T2.probs = new float[]{ 6, 5, 4 };
			
			MIS_T3.classes = new Class<?>[]{
					ThrowingSpear.class,
					Kunai.class,
					Bolas.class
			};
			MIS_T3.probs = new float[]{ 6, 5, 4 };
			
			MIS_T4.classes = new Class<?>[]{
					Javelin.class,
					Tomahawk.class,
					HeavyBoomerang.class
			};
			MIS_T4.probs = new float[]{ 6, 5, 4 };
			
			MIS_T5.classes = new Class<?>[]{
					Trident.class,
					ThrowingHammer.class,
					ForceCube.class
			};
			MIS_T5.probs = new float[]{ 6, 5, 4 };
			
			FOOD.classes = new Class<?>[]{
					Food.class,
					Pasty.class,
					MysteryMeat.class };
			FOOD.probs = new float[]{ 4, 1, 0 };
			
			RING.classes = new Class<?>[]{
					RingOfAccuracy.class,
					RingOfArcana.class,
					RingOfElements.class,
					RingOfEnergy.class,
					RingOfEvasion.class,
					RingOfForce.class,
					RingOfFuror.class,
					RingOfHaste.class,
					RingOfLethality.class,
					RingOfMight.class,
					RingOfSharpshooting.class,
					RingOfTenacity.class,
					RingOfWealth.class};
			RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
			ARTIFACT.classes = new Class<?>[]{
					//AlchemistsToolkit.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					DriedRose.class,
					EtherealChains.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class
			};
			ARTIFACT.defaultProbs = new float[]{ 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 };
			ARTIFACT.probs = ARTIFACT.defaultProbs.clone();

			ARTIFACTNOCHAINS.classes = new Class<?>[]{
					//AlchemistsToolkit.class,
					ChaliceOfBlood.class,
					CloakOfShadows.class,
					DriedRose.class,
					EtherealChains.class,
					HornOfPlenty.class,
					MasterThievesArmband.class,
					SandalsOfNature.class,
					TalismanOfForesight.class,
					TimekeepersHourglass.class,
					UnstableSpellbook.class
			};
			ARTIFACTNOCHAINS.defaultProbs = new float[]{ 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 };
			ARTIFACTNOCHAINS.probs = ARTIFACTNOCHAINS.defaultProbs.clone();
		}
	}

	private static final float[][] floorSetTierProbs = new float[][] {
			{25, 25, 25, 20,  5},
			{25, 25, 25, 20,  5},
			{25, 25, 25, 20,  5},
			{25, 25, 25, 20,  5},
			{25, 25, 25, 20,  5}
	};

	private static boolean usingFirstDeck = false;
	private static HashMap<Category,Float> defaultCatProbs = new LinkedHashMap<>();
	private static HashMap<Category,Float> categoryProbs = new LinkedHashMap<>();

	public static void fullReset() {
		usingFirstDeck = Random.Int(2) == 0;
		generalReset();
		for (Category cat : Category.values()) {
			reset(cat);
			if (cat.defaultProbs != null) {
				cat.seed = Random.Long();
				cat.dropped = 0;
			}
		}
	}

	public static void generalReset(){
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, usingFirstDeck ? cat.firstProb : cat.secondProb );
			defaultCatProbs.put( cat, cat.firstProb + cat.secondProb );
		}
	}

	public static void reset(Category cat){
		if (cat.defaultProbs != null) cat.probs = cat.defaultProbs.clone();
	}
	
	public static Item random() {
		Category cat = Random.chances( categoryProbs );
		if (cat == null){
			usingFirstDeck = !usingFirstDeck;
			generalReset();
			cat = Random.chances( categoryProbs );
		}
		categoryProbs.put( cat, categoryProbs.get( cat ) - 1);

		if (cat == Category.SEED) {
			//We specifically use defaults for seeds here because, unlike other item categories
			// their predominant source of drops is grass, not levelgen. This way the majority
			// of seed drops still use a deck, but the few that are spawned by levelgen are consistent
			return randomUsingDefaults(cat);
		} else {
			return random(cat);
		}
	}

	public static Item randomUsingDefaults(){
		return randomUsingDefaults(Random.chances( defaultCatProbs ));
	}
	
	public static Item random( Category cat ) {
		switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case MISSILE:
				return randomMissile();
			case ARTIFACT:
				Item item = randomArtifact();
				//if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				if (cat.defaultProbs != null && cat.seed != null){
					Random.pushGenerator(cat.seed);
					for (int i = 0; i < cat.dropped; i++) Random.Long();
				}

				int i = Random.chances(cat.probs);
				if (i == -1) {
					reset(cat);
					i = Random.chances(cat.probs);
				}
				if (cat.defaultProbs != null) cat.probs[i]--;

				if (cat.defaultProbs != null && cat.seed != null){
					Random.popGenerator();
					cat.dropped++;
				}

				return ((Item) Reflection.newInstance(cat.classes[i])).random();
		}
	}

	//overrides any deck systems and always uses default probs
	// except for artifacts, which must always use a deck
	public static Item randomUsingDefaults( Category cat ){
		if (cat.defaultProbs == null || cat == Category.ARTIFACT) {
			return random(cat);
		} else {
			return ((Item) Reflection.newInstance(cat.classes[Random.chances(cat.defaultProbs)])).random();
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		return Reflection.newInstance(cl).random();
	}

	public static Armor randomArmor(){
		return randomArmor(Dungeon.depth / 5);
	}
	
	public static Armor randomArmor(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Armor a = (Armor)Reflection.newInstance(Category.ARMOR.classes[Random.chances(floorSetTierProbs[floorSet])]);
		a.random();
		return a;
	}

	public static final Category[] wepTiers = new Category[]{
			Category.WEP_T1,
			Category.WEP_T2,
			Category.WEP_T3,
			Category.WEP_T4,
			Category.WEP_T5,
			Category.WEP_T6,
			Category.WEP_T7,
			Category.WEP_T8,
			Category.WEP_T9,
			Category.WEP_T10,
			Category.WEP_T11,
			Category.WEP_T12,
			Category.WEP_T13,
			Category.WEP_T14,
			Category.WEP_T15
	};

	public static MeleeWeapon randomWeapon(){
		return randomWeapon(Dungeon.depth / 5);
	}
	
	public static MeleeWeapon randomWeapon(int floorSet) {

		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Category c = wepTiers[Random.chances(floorSetTierProbs[floorSet])];
		MeleeWeapon w = (MeleeWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}
	
	public static final Category[] misTiers = new Category[]{
			Category.MIS_T1,
			Category.MIS_T2,
			Category.MIS_T3,
			Category.MIS_T4,
			Category.MIS_T5,

	};
	
	public static MissileWeapon randomMissile(){
		return randomMissile(Dungeon.depth / 5);
	}
	
	public static MissileWeapon randomMissile(int floorSet) {
		
		floorSet = (int)GameMath.gate(0, floorSet, floorSetTierProbs.length-1);
		
		Category c = misTiers[Random.chances(floorSetTierProbs[floorSet])];
		MissileWeapon w = (MissileWeapon)Reflection.newInstance(c.classes[Random.chances(c.probs)]);
		w.random();
		return w;
	}

	//enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		Category cat = Category.ARTIFACT;

		if (cat.defaultProbs != null && cat.seed != null){
			Random.pushGenerator(cat.seed);
			for (int i = 0; i < cat.dropped; i++) Random.Long();
		}

		int i = Random.chances( cat.probs );

		if (cat.defaultProbs != null && cat.seed != null){
			Random.popGenerator();
			cat.dropped++;
		}

		//if no artifacts are left, return null
		if (i == -1){
			return null;
		}

		cat.probs[i]--;
		return (Artifact) Reflection.newInstance((Class<? extends Artifact>) cat.classes[i]).random();

	}

	public static boolean removeArtifact(Class<?extends Artifact> artifact) {
		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++){
			if (cat.classes[i].equals(artifact) && cat.probs[i] > 0) {
				cat.probs[i] = 0;
				return true;
			}
		}
		return false;
	}

	private static final String FIRST_DECK = "first_deck";
	private static final String GENERAL_PROBS = "general_probs";
	private static final String CATEGORY_PROBS = "_probs";
	private static final String CATEGORY_SEED = "_seed";
	private static final String CATEGORY_DROPPED = "_dropped";

	public static void storeInBundle(Bundle bundle) {
		bundle.put(FIRST_DECK, usingFirstDeck);

		Float[] genProbs = categoryProbs.values().toArray(new Float[0]);
		float[] storeProbs = new float[genProbs.length];
		for (int i = 0; i < storeProbs.length; i++){
			storeProbs[i] = genProbs[i];
		}
		bundle.put( GENERAL_PROBS, storeProbs);

		for (Category cat : Category.values()){
			if (cat.defaultProbs == null) continue;

			bundle.put(cat.name().toLowerCase() + CATEGORY_PROBS,   cat.probs);
			if (cat.seed != null) {
				bundle.put(cat.name().toLowerCase() + CATEGORY_SEED, cat.seed);
				bundle.put(cat.name().toLowerCase() + CATEGORY_DROPPED, cat.dropped);
			}
		}
	}

	public static void restoreFromBundle(Bundle bundle) {
		fullReset();

		usingFirstDeck = bundle.getBoolean(FIRST_DECK);

		if (bundle.contains(GENERAL_PROBS)){
			float[] probs = bundle.getFloatArray(GENERAL_PROBS);
			for (int i = 0; i < probs.length; i++){
				categoryProbs.put(Category.values()[i], probs[i]);
			}
		}

		for (Category cat : Category.values()){
			if (bundle.contains(cat.name().toLowerCase() + CATEGORY_PROBS)){
				float[] probs = bundle.getFloatArray(cat.name().toLowerCase() + CATEGORY_PROBS);
				if (cat.defaultProbs != null && probs.length == cat.defaultProbs.length){
					cat.probs = probs;
				}
				if (bundle.contains(cat.name().toLowerCase() + CATEGORY_SEED)){
					cat.seed = bundle.getLong(cat.name().toLowerCase() + CATEGORY_SEED);
					cat.dropped = bundle.getInt(cat.name().toLowerCase() + CATEGORY_DROPPED);
				}
			}
		}
		
	}
}
