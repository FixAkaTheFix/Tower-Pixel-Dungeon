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

package com.towerpixel.towerpixeldungeon.actors.hero;

import static com.towerpixel.towerpixeldungeon.Dungeon.gold;
import static com.towerpixel.towerpixeldungeon.items.Item.updateQuickslot;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.QuickSlot;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.ArmorAbility;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.duelist.Feint;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.mage.WildMagic;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.warrior.HeroicLeap;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.warrior.Shockwave;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.towerpixel.towerpixeldungeon.items.Amulet;
import com.towerpixel.towerpixeldungeon.items.BrokenSeal;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.Waterskin;
import com.towerpixel.towerpixeldungeon.items.armor.ClothArmor;
import com.towerpixel.towerpixeldungeon.items.armor.LeatherArmor;
import com.towerpixel.towerpixeldungeon.items.armor.MailArmor;
import com.towerpixel.towerpixeldungeon.items.armor.PlateArmor;
import com.towerpixel.towerpixeldungeon.items.artifacts.AlchemistsToolkit;
import com.towerpixel.towerpixeldungeon.items.artifacts.CloakOfShadows;
import com.towerpixel.towerpixeldungeon.items.artifacts.MasterThievesArmband;
import com.towerpixel.towerpixeldungeon.items.artifacts.SandalsOfNature;
import com.towerpixel.towerpixeldungeon.items.bombs.Flashbang;
import com.towerpixel.towerpixeldungeon.items.debuggers.StableTeleportScroll;
import com.towerpixel.towerpixeldungeon.items.debuggers.StaffOfBeasts;
import com.towerpixel.towerpixeldungeon.items.food.Food;
import com.towerpixel.towerpixeldungeon.items.potions.Potion;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHaste;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfInvisibility;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfPurity;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfMagicalSight;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfSnapFreeze;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfAntiMagic;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.towerpixel.towerpixeldungeon.items.spells.CurseInfusion;
import com.towerpixel.towerpixeldungeon.items.debuggers.WandOfDebug;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfAggression;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfBlast;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfBlink;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfClairvoyance;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfDisarming;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfFlock;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfShock;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfRegrowth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfSnakes;
import com.towerpixel.towerpixeldungeon.items.weapon.DebugBow;
import com.towerpixel.towerpixeldungeon.items.weapon.SpiritBow;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Banhammer;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Dirk;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Gloves;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MagesStaff;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Quarterstaff;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Rapier;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Shortsword;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Sword;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.HeavyBoomerang;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingSpike;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.AdrenalineDart;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.BlindingDart;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.HealingDart;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Blindweed;
import com.towerpixel.towerpixeldungeon.plants.Earthroot;
import com.towerpixel.towerpixeldungeon.plants.Stormvine;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.plants.Swiftthistle;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK ),
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

		new ScrollOfIdentify().identify();

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

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		hero.critMult = 1.3f;
		hero.STR = 14;
		hero.attackSkill = 9;

		Item i = new MailArmor().identify();
		hero.belongings.armor = (MailArmor)i;

		(hero.belongings.weapon = new Sword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.quantity(5).collect();
		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
		}

		new ElixirOfHoneyedHealing().collect();
		new PotionOfPurity().collect();
		new PotionOfShielding().collect();
		new ScrollOfRage().identify().collect();
	}

	private static void initMage( Hero hero ) {
		hero.STR = 12;

		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;

		MeleeWeapon staff = (MeleeWeapon) (new MagesStaff(new WandOfMagicMissile()).upgrade(1));
		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new StoneOfShock().collect();
		new StoneOfAggression().collect();
		new StoneOfBlast().collect();
		new StoneOfClairvoyance().collect();
		new StoneOfDisarming().collect();
		new StoneOfFlock().collect();
		new StoneOfIntuition().collect();
		new StoneOfIntuition().collect();
		new StoneOfIntuition().collect();
		new StoneOfIntuition().collect();
		new StoneOfIntuition().collect();
		new ScrollOfAntiMagic().collect();
		new ScrollOfRecharging().collect();

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
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify().collect();
		new PotionOfInvisibility().identify().collect();
		new PotionOfHaste().collect();
		new StoneOfBlink().collect();
		new Flashbang().collect();
		new PotionOfSnapFreeze().collect();
	}

	private static void initHuntress( Hero hero ) {
		hero.STR = 11;
		hero.attackSkill = 11;

		Item i = new ClothArmor().identify();
		hero.belongings.armor = (ClothArmor)i;

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		for (int x = 0; x < 3; x++){
			new Dart().collect();
		}
		new Blindweed.Seed().collect();
		new Stormvine.Seed().collect();
		new Sungrass.Seed().collect();
		new Swiftthistle.Seed().collect();
		new Earthroot.Seed().collect();
	}

	private static void initDuelist( Hero hero ) {
		hero.STR = 13;
		hero.critChance = 0.15f;

		Item i = new LeatherArmor().identify();
		hero.belongings.armor = (LeatherArmor)i;

		(hero.belongings.weapon = new Rapier()).identify().upgrade(1);
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).collect();

		new Dirk().identify().collect();
		new Quarterstaff().identify().collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new ScrollOfMirrorImage().identify().collect();
		new ScrollOfPrismaticImage().collect();
		new ScrollOfSirensSong().collect();

	}
	private static void initFix(Hero hero){

		Banhammer banhammer = new Banhammer();
		banhammer.upgrade(100);

		Shortsword shortsword = new Shortsword();
		shortsword.identify().collect();

		PlateArmor arr = new PlateArmor();
		arr.identify().collect();

		(hero.belongings.weapon = banhammer).identify();

		gold = 10000;
		updateQuickslot();

		hero.STR = 12;

		PotionOfMindVision p = new PotionOfMindVision();
		p.identify().collect();
		PlateArmor armor = new PlateArmor();
		armor.identify().collect();
		armor.upgrade(150);

		new Amulet().collect();

		WandOfDebug buggo = new WandOfDebug();
		buggo.identify().collect();

		StaffOfBeasts staffo = new StaffOfBeasts();
		staffo.identify().collect();

		WandOfSnakes r22 = new WandOfSnakes();
		r22.identify().collect();

		new WandOfRegrowth().collect();
		new SandalsOfNature().upgrade(10000).collect();

		StableTeleportScroll stableTeleportScroll=new StableTeleportScroll();
		stableTeleportScroll.identify().collect();

		MasterThievesArmband armband = new MasterThievesArmband();
		armband.identify().collect();

		for (int i = 0; i<=20; i++) {
			new PotionOfMagicalSight().collect();
			new ScrollOfTeleportation().collect();
			new SpawnerCrossbow().identify().collect();
			new SpawnerWand().identify().collect();
			new SpawnerGrave().identify().collect();
			new SpawnerWall().identify().collect();
			new SpawnerCannon().identify().collect();
			new ScrollOfMagicMapping().identify().collect();
			new PotionOfToxicGas().identify().collect();

		}


		DebugBow bow = new DebugBow();
		bow.identify().collect();

		AlchemistsToolkit alchemistsToolkit = new AlchemistsToolkit();
		alchemistsToolkit.identify().collect();





		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfHealing().identify();
		new PotionOfStrength().identify();
		new ScrollOfUpgrade().identify();

	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
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
			case FIX:
				return Assets.Splashes.FIX;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		//if (DeviceCompat.isDebug()) return true;
		return true;

		/*switch (this){
			case WARRIOR: default:
				return true;
			case FIX:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
			case DUELIST:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
		}*/
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
