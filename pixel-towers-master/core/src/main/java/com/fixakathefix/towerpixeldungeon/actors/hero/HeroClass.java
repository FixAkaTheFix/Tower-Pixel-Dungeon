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

package com.fixakathefix.towerpixeldungeon.actors.hero;

import static com.fixakathefix.towerpixeldungeon.Dungeon.gold;
import static com.fixakathefix.towerpixeldungeon.items.Item.updateQuickslot;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Challenges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.QuickSlot;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.Ankh;
import com.fixakathefix.towerpixeldungeon.items.BrokenSeal;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.Waterskin;
import com.fixakathefix.towerpixeldungeon.items.armor.ClassArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.ClothArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.DuelistArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.HuntressArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.LeatherArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.MageArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.MailArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.PlateArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.RogueArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.WarriorArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.curses.AntiEntropy;
import com.fixakathefix.towerpixeldungeon.items.armor.curses.Unreliable;
import com.fixakathefix.towerpixeldungeon.items.artifacts.BrokenHourglass;
import com.fixakathefix.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.fixakathefix.towerpixeldungeon.items.artifacts.CloakOfShadows;
import com.fixakathefix.towerpixeldungeon.items.artifacts.HornOfPlenty;
import com.fixakathefix.towerpixeldungeon.items.artifacts.RoseSeed;
import com.fixakathefix.towerpixeldungeon.items.artifacts.SandalsOfNature;
import com.fixakathefix.towerpixeldungeon.items.artifacts.TalismanOfForesight;
import com.fixakathefix.towerpixeldungeon.items.artifacts.UnstableSpellbook;
import com.fixakathefix.towerpixeldungeon.items.bags.VelvetPouch;
import com.fixakathefix.towerpixeldungeon.items.debuggers.StableTeleportScroll;
import com.fixakathefix.towerpixeldungeon.items.debuggers.StaffOfBeasts;
import com.fixakathefix.towerpixeldungeon.items.food.Food;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbArcanesword;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbBanner;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbBerserk;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbEgoist;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbIceWall;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbLasher;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbOakskin;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbObelisk;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbRegeneration;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTarget;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTaunt;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTeleport;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrAngerTheDead;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrArrowVolley;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrBombVolley;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrDartgunAlly;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrGreatWall;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrHyperats;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrLightningStrike;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrNullify;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrOrder;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrOvercharge;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrPlanB;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTreatwounds;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbUndying;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbVision;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbBlessing;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbGibberish;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbNecromancy;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbDisengage;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbShadowclone;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbSwift;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbGlowup;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbGoldarmor;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbShield;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbWallstance;
import com.fixakathefix.towerpixeldungeon.items.herospells.HeroSpell;
import com.fixakathefix.towerpixeldungeon.items.herospells.HeroSpellTargeted;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfPurity;
import com.fixakathefix.towerpixeldungeon.items.potions.brews.CausticBrew;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.fixakathefix.towerpixeldungeon.items.scrolls.Scroll;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfRage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.debuggers.WandOfDebug;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfGolems;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfHolyNova;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfRatLegion;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfUnspeakableHorrors;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAugmentation;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfBlast;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfDeepSleep;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfDisarming;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfFear;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfFlock;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfShock;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCamp;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerDartgun;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerGuard;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerLightning;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.items.wands.Wand;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfRegrowth;
import com.fixakathefix.towerpixeldungeon.items.weapon.SpiritBow;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Banhammer;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Dagger;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Dirk;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MagesStaff;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Quarterstaff;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Rapier;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.RoundShield;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.ShortSword;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Sword;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.BlindingDart;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.HealingDart;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.PoisonDart;
import com.fixakathefix.towerpixeldungeon.levels.traps.Trap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.BlandfruitBush;
import com.fixakathefix.towerpixeldungeon.plants.Sorrowmoss;
import com.fixakathefix.towerpixeldungeon.plants.Starflower;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Reflection;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	NECROHERO(HeroSubClass.BETA, HeroSubClass.ALPHA),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK ),
	TANK(HeroSubClass.BETA, HeroSubClass.ALPHA),
	PRIEST(HeroSubClass.BETA, HeroSubClass.ALPHA),
	FIX( HeroSubClass.BETA, HeroSubClass.ALPHA);

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();
		Waterskin waterskin = new Waterskin();
		waterskin.collect();


		new Amulet().collect();

		switch (this) {
			case FIX:
				initFix( hero );
				break;

			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;
			case TANK:
				initTank(hero);
				break;
			case NECROHERO:
				initNecrohero(hero);
				break;
			case PRIEST:
				initPriest(hero);
				break;

		}


		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public int getBrightColor(){
		switch (this) {
			case WARRIOR: default:
				return 0xFFFF9966;
			case MAGE:
				return 0xFF00FFFF;
			case ROGUE:
				return 0xFFDDDDDD;
			case HUNTRESS:
				return 0xFF11FF11;
			case DUELIST:
				return 0xFFFF00FF;
			case NECROHERO:
				return 0xFF55FF55;
			case TANK:
				return 0xFFFF0000;
			case PRIEST:
				return 0xFFFFFFAA;
			case FIX:
				return 0xFF00FF00;
		}
	}
	public int getDarkColor(){
		switch (this) {
			case WARRIOR: default:
				return 0xFFAA5500;
			case MAGE:
				return 0xFF009999;
			case ROGUE:
				return 0xFF999999;
			case HUNTRESS:
				return 0xFF00AA00;
			case DUELIST:
				return 0xFFBB00BB;
			case NECROHERO:
				return 0xFF44CC44;
			case TANK:
				return 0xFFAA0000;
			case PRIEST:
				return 0xFFAAAA00;
			case FIX:
				return 0xFF000000;
		}
	}

	private static void initNecrohero( Hero hero ) {;
		hero.STR = 11;

		Item i = new ClothArmor().identify();
		hero.belongings.armor = (ClothArmor)i;
		(hero.belongings.weapon = new Dagger()).identify();

		HeroSpell a1 = new AbNecromancy();
		a1.collect();
		HeroSpell a2 = new AbUndying();
		a2.collect();
		HeroSpell a3 = new AbObelisk();
		a3.collect();
		Dungeon.quickslot.setSlot(0, a1);
		Dungeon.quickslot.setSlot(1, a2);
		Dungeon.quickslot.setSlot(2, a3);

		new ElixirOfToxicEssence().collect();
		new StoneOfFear().collect();
		new Sorrowmoss.Seed().collect();
	}
	private static void initTank( Hero hero ) {

		hero.STR = 13;
		hero.defenseSkill = 4;

		Item i = new ClothArmor().identify();
		hero.belongings.armor = (ClothArmor)i;

		(hero.belongings.weapon = new ShortSword()).identify();

		HeroSpell a1 = new AbShield();
		a1.collect();
		HeroSpell a2 = new AbWallstance();
		a2.collect();
		HeroSpell a3 = new AbTaunt();
		a3.collect();
		Dungeon.quickslot.setSlot(0, a1);
		Dungeon.quickslot.setSlot(1, a2);
		Dungeon.quickslot.setSlot(2, a3);

		new PotionOfShielding().collect();
		new StoneOfDeepSleep().collect();
		new StoneOfDeepSleep().collect();
		new StoneOfDeepSleep().collect();
		new Starflower.Seed().collect();
	}

	private static void initWarrior( Hero hero ) {
		hero.critMult = 2f;
		hero.STR = 14;
		hero.attackSkill = 9;


		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;


		(hero.belongings.weapon = new Sword()).identify();

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		HeroSpell a1 = new AbTreatwounds();
		a1.collect();
		HeroSpell a2 = new AbGlowup();
		a2.collect();
		HeroSpell a3 = new AbGoldarmor();
		a3.collect();
		Dungeon.quickslot.setSlot(0, a1);
		Dungeon.quickslot.setSlot(1, a2);
		Dungeon.quickslot.setSlot(2, a3);

		new ScrollOfRage().collect();
		new PotionOfPurity().collect();
		new PotionOfHealing().collect();
	}

	private static void initMage( Hero hero ) {
		hero.STR = 12;

		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;

		MeleeWeapon staff = (MeleeWeapon) (new MagesStaff(new WandOfMagicMissile()));
		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);
		HeroSpell a1 = new AbGibberish();
		a1.collect();
		HeroSpell a2 = new AbIceWall();
		a2.collect();
		HeroSpell a3 = new AbTeleport();
		a3.collect();
		Dungeon.quickslot.setSlot(1, a1);
		Dungeon.quickslot.setSlot(2, a2);
		Dungeon.quickslot.setSlot(3, a3);

		new StoneOfShock().collect();
		new StoneOfBlast().collect();
		new ScrollOfAnimation().collect();
		new ScrollOfAnimation().collect();
	}

	private static void initRogue( Hero hero ) {


		hero.defenseSkill = 6;
		hero.STR = 13;
		gold = 100;
		updateQuickslot();

		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;


		(hero.belongings.weapon = new Dirk()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.quantity(3).collect();

		Dungeon.quickslot.setSlot(0, cloak);

		HeroSpell a1 = new AbDisengage();
		a1.collect();
		HeroSpell a2 = new AbShadowclone();
		a2.collect();
		HeroSpell a3 = new AbSwift();
		a3.collect();
		Dungeon.quickslot.setSlot(1, a1);
		Dungeon.quickslot.setSlot(2, a2);
		Dungeon.quickslot.setSlot(3, a3);

		new PotionOfShroudingFog().collect();
		new ScrollOfTeleportation().collect();
	}

	private static void initHuntress( Hero hero ) {

		hero.STR = 11;
		hero.attackSkill = 11;

		Item x = new LeatherArmor().identify();
		x.upgrade(1);
		hero.belongings.armor = (LeatherArmor)x;


		(hero.belongings.weapon = new ShortSword()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		HeroSpell a1 = new AbOakskin();
		a1.collect();
		HeroSpell a2 = new AbLasher();
		a2.collect();
		HeroSpell a3 = new AbVision();
		a3.collect();
		Dungeon.quickslot.setSlot(1, a1);
		Dungeon.quickslot.setSlot(2, a2);
		Dungeon.quickslot.setSlot(3, a3);

		new HealingDart().collect();
		new PoisonDart().collect();
		new PoisonDart().collect();
		new BlindingDart().collect();
		new BlindingDart().collect();
		new BlindingDart().collect();

		new ElixirOfHoneyedHealing().collect();


	}

	private static void initDuelist( Hero hero ) {
		hero.STR = 13;
		hero.critChance = 0.15f;

		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(3).collect();

		new Dirk().identify().collect();
		new Quarterstaff().identify().collect();
		new StoneOfAugmentation().collect();
		new StoneOfAugmentation().collect();
		new StoneOfAugmentation().collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);

		HeroSpell a1 = new AbTarget();
		a1.collect();
		HeroSpell a2 = new AbArcanesword();
		a2.collect();
		HeroSpell a3 = new AbBanner();
		a3.collect();
		Dungeon.quickslot.setSlot(1, a1);
		Dungeon.quickslot.setSlot(2, a2);
		Dungeon.quickslot.setSlot(3, a3);

		new ScrollOfMirrorImage().collect();

	}
	private static void initPriest( Hero hero ) {
		hero.STR = 10;

		Item i = new ClothArmor().identify();
		hero.belongings.armor = (ClothArmor)i;

		new ScrollOfHolyNova().collect();
		new StoneOfFlock().collect();
	}
	private static void initFix(Hero hero){

		Banhammer banhammer = new Banhammer();
		banhammer.upgrade(10000);

		(hero.belongings.weapon = banhammer).identify();

		gold = 100000;
		updateQuickslot();

		hero.STR = 14;

		PotionOfMindVision p = new PotionOfMindVision();
		p.identify().collect();
		PlateArmor armor = new PlateArmor();
		armor.identify().collect();
		armor.upgrade(150);
		armor.cursed = true;
		armor.inscribe(new AntiEntropy());

		new ScrollOfHolyNova().collect();
		new RoseSeed().collect();

		for (int i = 0; i < 9; i++) {
			new ScrollOfGolems().collect();
			MeleeWeapon wep = Generator.randomWeapon();
			if (Math.random()>0.5) wep.cursed = true;
			wep.collect();
		}

		WandOfDebug buggo = new WandOfDebug();
		buggo.identify().collect();
		new Ankh().collect();

		StaffOfBeasts staffo = new StaffOfBeasts();
		staffo.identify().collect();

		WandOfRegrowth wandOfRegrowth = new WandOfRegrowth();
		wandOfRegrowth.upgrade(5).collect();

		StableTeleportScroll stableTeleportScroll = new StableTeleportScroll();
		stableTeleportScroll.identify().collect();
		new ScrollOfTeleportation().collect();

		//new AbTrArrowVolley().collect();
		//new AbTrBombVolley().collect();
		//new AbTrOvercharge().collect();
		//new AbTrHyperats().collect();
		//new AbTrAngerTheDead().collect();
		//new AbTrDartgunAlly().collect();
		//new AbTrGreatWall().collect();
		//new AbTrLightningStrike().collect();
		new AbTrNullify().collect();
		new AbTrOrder().collect();
		new AbTrPlanB().collect();


		new PotionOfLevitation().collect();
		new BrokenHourglass().collect();
		new UnstableSpellbook().collect();
		new WandOfBlastWave().collect();
		/*for (Class cas : Generator.Category.SCROLL.classes){
			Scroll s = (Scroll)Reflection.newInstance(cas);
			s.collect();
		}*/


	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}
	public String heroSpells(){
		return Messages.get(HeroClass.class, name()+"_herospells");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
			case NECROHERO:
			case PRIEST:
			case TANK:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
			case FIX:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
			case FIX:
				return Assets.Sprites.FIX;
			case TANK:
				return Assets.Sprites.TANK;
			case PRIEST:
				return Assets.Sprites.PRIEST;
			case NECROHERO:
				return Assets.Sprites.NECROHERO;
		}
	}

	public String splashArt(){
		switch (this) {
			default:
			case WARRIOR:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
			case FIX: case NECROHERO: case TANK: case PRIEST:
				return Assets.Splashes.FIX;
		}
	}
	
	public boolean isUnlocked(){
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR:
				return true;
			case FIX:
				return DeviceCompat.isDebug();
			case MAGE:
				return true;
			case ROGUE:
				return true;
			case HUNTRESS:
				return SPDSettings.maxlevelunlocked()>=5;
			case NECROHERO:
				return SPDSettings.maxlevelunlocked()>=9;
			case DUELIST:
				return SPDSettings.maxlevelunlocked()>=11;
			case TANK:
				return SPDSettings.maxlevelunlocked()>=14;
			case PRIEST:
				return Badges.isUnlocked(Badges.Badge.REDEMPTION);
		}
		return false;
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
