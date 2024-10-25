/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.towerpixel.towerpixeldungeon.journal;

import com.towerpixel.towerpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.towerpixel.towerpixeldungeon.actors.mobs.Acidic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Albino;
import com.towerpixel.towerpixeldungeon.actors.mobs.ArmoredBrute;
import com.towerpixel.towerpixeldungeon.actors.mobs.ArmoredStatue;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bandit;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bee;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossOoze;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossRatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTengu;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTroll;
import com.towerpixel.towerpixeldungeon.actors.mobs.Brute;
import com.towerpixel.towerpixeldungeon.actors.mobs.CausticSlime;
import com.towerpixel.towerpixeldungeon.actors.mobs.ChiefRat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Crab;
import com.towerpixel.towerpixeldungeon.actors.mobs.CrystalMimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM100;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM200;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM201;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMW;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWBody;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWHead;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWMinion;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWWheels;
import com.towerpixel.towerpixeldungeon.actors.mobs.DemonSpawner;
import com.towerpixel.towerpixeldungeon.actors.mobs.DwarfKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.Elemental;
import com.towerpixel.towerpixeldungeon.actors.mobs.Eye;
import com.towerpixel.towerpixeldungeon.actors.mobs.FetidRat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Ghoul;
import com.towerpixel.towerpixeldungeon.actors.mobs.Gnoll;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollBlind;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollThrower;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goblin;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinFat;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinGiant;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinNinja;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinSand;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoldenMimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Golem;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goo;
import com.towerpixel.towerpixeldungeon.actors.mobs.GreatCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.Guard;
import com.towerpixel.towerpixeldungeon.actors.mobs.HermitCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.HermitCrabNoShell;
import com.towerpixel.towerpixeldungeon.actors.mobs.MagiCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Monk;
import com.towerpixel.towerpixeldungeon.actors.mobs.Necromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.Piranha;
import com.towerpixel.towerpixeldungeon.actors.mobs.Pylon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.actors.mobs.RatKingAvatar;
import com.towerpixel.towerpixeldungeon.actors.mobs.RipperDemon;
import com.towerpixel.towerpixeldungeon.actors.mobs.RotHeart;
import com.towerpixel.towerpixeldungeon.actors.mobs.RotLasher;
import com.towerpixel.towerpixeldungeon.actors.mobs.Scorpio;
import com.towerpixel.towerpixeldungeon.actors.mobs.Senior;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shaman;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shinobi;
import com.towerpixel.towerpixeldungeon.actors.mobs.Skeleton;
import com.towerpixel.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.towerpixel.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.towerpixel.towerpixeldungeon.actors.mobs.Slime;
import com.towerpixel.towerpixeldungeon.actors.mobs.Slugger;
import com.towerpixel.towerpixeldungeon.actors.mobs.Snake;
import com.towerpixel.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.Spinner;
import com.towerpixel.towerpixeldungeon.actors.mobs.Statue;
import com.towerpixel.towerpixeldungeon.actors.mobs.Succubus;
import com.towerpixel.towerpixeldungeon.actors.mobs.Swarm;
import com.towerpixel.towerpixeldungeon.actors.mobs.Thief;
import com.towerpixel.towerpixeldungeon.actors.mobs.Warlock;
import com.towerpixel.towerpixeldungeon.actors.mobs.Wraith;
import com.towerpixel.towerpixeldungeon.actors.mobs.YogDzewa;
import com.towerpixel.towerpixeldungeon.actors.mobs.YogFist;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Blacksmith;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Ghost;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Imp;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Sheep;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.items.artifacts.DriedRose;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfRegrowth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.levels.rooms.special.SentryRoom;
import com.towerpixel.towerpixeldungeon.levels.traps.AlarmTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.BlazingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.BurningTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ChillingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ConfusionTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.CorrosionTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.CursingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.DisarmingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.DisintegrationTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.DistortionTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ExplosiveTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.FlashingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.FlockTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.FrostTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GatewayTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GeyserTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GrimTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GrippingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GuardianTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.OozeTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.PitfallTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.PoisonDartTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.RockfallTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ShockingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.StormTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.SummoningTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.TeleportationTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.TenguDartTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ToxicTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.WarpingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.WeakeningTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.WornDartTrap;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.BlandfruitBush;
import com.towerpixel.towerpixeldungeon.plants.Blindweed;
import com.towerpixel.towerpixeldungeon.plants.Earthroot;
import com.towerpixel.towerpixeldungeon.plants.Fadeleaf;
import com.towerpixel.towerpixeldungeon.plants.Firebloom;
import com.towerpixel.towerpixeldungeon.plants.Icecap;
import com.towerpixel.towerpixeldungeon.plants.Mageroyal;
import com.towerpixel.towerpixeldungeon.plants.Rotberry;
import com.towerpixel.towerpixeldungeon.plants.Sorrowmoss;
import com.towerpixel.towerpixeldungeon.plants.Starflower;
import com.towerpixel.towerpixeldungeon.plants.Stormvine;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

//contains all the game's various entities, mostly enemies, NPCS, and allies, but also traps and plants
public enum Bestiary {

	NORMAL,
	ELITE,
	BOSSES,
	UNIVERSAL,
	RARE,
	NEUTRAL,
	ALLY,
	TRAP,
	PLANT;

	//tracks whether an entity has been encountered
	private final LinkedHashMap<Class<?>, Boolean> seen = new LinkedHashMap<>();
	//tracks enemy kills, trap activations, plant tramples, or just sets to 1 for seen on allies
	private final LinkedHashMap<Class<?>, Integer> encounterCount = new LinkedHashMap<>();

	//should only be used when initializing
	private void addEntities(Class<?>... classes ){
		for (Class<?> cls : classes){
			seen.put(cls, false);
			encounterCount.put(cls, 0);
		}
	}

	public Collection<Class<?>> entities(){
		return seen.keySet();
	}

	public String title(){
		return Messages.get(this, name() + ".title");
	}

	public int totalEntities(){
		return seen.size();
	}

	public int totalSeen(){
		int seenTotal = 0;
		for (boolean entitySeen : seen.values()){
			if (entitySeen) seenTotal++;
		}
		return seenTotal;
	}

	static {

		NORMAL.addEntities(Rat.class, Snake.class, GnollBlind.class, GnollThrower.class, Gnoll.class, Swarm.class, Crab.class, Slime.class,
				HermitCrab.class, HermitCrabNoShell.class,
				Skeleton.class, Thief.class, DM100.class, Guard.class, Necromancer.class, Shinobi.class,
				Bat.class, Brute.class, Shaman.RedShaman.class, Shaman.BlueShaman.class, Shaman.PurpleShaman.class, Spinner.class, DM200.class,
				Goblin.class, GoblinFat.class, GoblinNinja.class, GoblinSand.class,
				GoblinShaman.ShamanStrength.class, GoblinShaman.ShamanRegen.class, GoblinShaman.ShamanShield.class, GoblinShaman.ShamanFake.class,
				Slugger.class,
				Ghoul.class, Elemental.FireElemental.class, Elemental.FrostElemental.class, Elemental.ShockElemental.class, Warlock.class, Monk.class, Golem.class,
				RipperDemon.class, DemonSpawner.class, Succubus.class, Eye.class, Scorpio.class);
		ELITE.addEntities(FetidRat.class, ChiefRat.class, GnollTrickster.class, Goo.class, GreatCrab.class, MagiCrab.class, GoblinGiant.class,
				SkeletonArmored.class, SkeletonArmoredShielded.class
		);

		BOSSES.addEntities( BossRatKing.class, BossOoze.class,
				BossTengu.class, BossTroll.class,
				DMW.class, DMWHead.class, DMWBody.class, DMWWheels.class, DMWMinion.class,
				BossDwarfKing.class,
				YogDzewa.Larva.class, YogFist.BurningFist.class, YogFist.SoiledFist.class, YogFist.RottingFist.class, YogFist.RustedFist.class,YogFist.BrightFist.class, YogFist.DarkFist.class, YogDzewa.class);

		UNIVERSAL.addEntities(Wraith.class, Piranha.class, Mimic.class, GoldenMimic.class, Statue.class, GuardianTrap.Guardian.class, SentryRoom.Sentry.class);

		RARE.addEntities(Albino.class, CausticSlime.class,
				Bandit.class, SpectralNecromancer.class,
				ArmoredBrute.class, DM201.class,
				Elemental.ChaosElemental.class, Senior.class,
				Acidic.class,
				CrystalMimic.class, ArmoredStatue.class);

		NEUTRAL.addEntities(Ghost.class, RatKing.class, Shopkeeper.class, Wandmaker.class, Blacksmith.class, Imp.class, Sheep.class, Bee.class);

		ALLY.addEntities(MirrorImage.class, PrismaticImage.class, RatKingAvatar.class,
				DriedRose.GhostHero.class,
				TowerCrossbow1.class, TowerWand1.class, TowerWall1.class, TowerCannon1.class, TowerGuard1.class, TowerLightning1.class, TowerGrave1.class, TowerDartgun1.class, TowerDisintegration1.class, TowerTntLog.class,
				WandOfWarding.Ward.class, WandOfLivingEarth.EarthGuardian.class,
				ShadowClone.ShadowAlly.class, SmokeBomb.NinjaLog.class, SpiritHawk.HawkAlly.class);

		TRAP.addEntities(WornDartTrap.class, PoisonDartTrap.class, DisintegrationTrap.class, GatewayTrap.class,
				ChillingTrap.class, BurningTrap.class, ShockingTrap.class, AlarmTrap.class, GrippingTrap.class, TeleportationTrap.class, OozeTrap.class,
				FrostTrap.class, BlazingTrap.class, StormTrap.class, GuardianTrap.class, FlashingTrap.class, WarpingTrap.class,
				ConfusionTrap.class, ToxicTrap.class, CorrosionTrap.class,
				FlockTrap.class, SummoningTrap.class, WeakeningTrap.class, CursingTrap.class,
				GeyserTrap.class, ExplosiveTrap.class, RockfallTrap.class, PitfallTrap.class,
				DistortionTrap.class, DisarmingTrap.class, GrimTrap.class);

		PLANT.addEntities(Rotberry.class, Sungrass.class, Fadeleaf.class, Icecap.class,
				Firebloom.class, Sorrowmoss.class, Swiftthistle.class, Blindweed.class,
				Stormvine.class, Earthroot.class, Mageroyal.class, Starflower.class,
				BlandfruitBush.class,
				WandOfRegrowth.Dewcatcher.class, WandOfRegrowth.Seedpod.class, WandOfRegrowth.Lotus.class);

	}

	//some mobs and traps have different internal classes in some cases, so need to convert here
	private static final HashMap<Class<?>, Class<?>> classConversions = new HashMap<>();
	static {
		classConversions.put(Necromancer.NecroSkeleton.class,  Skeleton.class);

		classConversions.put(TenguDartTrap.class,              PoisonDartTrap.class);

		classConversions.put(DwarfKing.DKGhoul.class,          Ghoul.class);
		classConversions.put(DwarfKing.DKWarlock.class,        Warlock.class);
		classConversions.put(DwarfKing.DKMonk.class,           Monk.class);
		classConversions.put(DwarfKing.DKGolem.class,          Golem.class);

		classConversions.put(YogDzewa.YogRipper.class,         RipperDemon.class);
		classConversions.put(YogDzewa.YogEye.class,            Eye.class);
		classConversions.put(YogDzewa.YogScorpio.class,        Scorpio.class);
	}

	public static boolean isSeen(Class<?> cls){
		for (Bestiary cat : values()) {
			if (cat.seen.containsKey(cls)) {
				return cat.seen.get(cls);
			}
		}
		return false;
	}

	public static void setSeen(Class<?> cls){
		if (classConversions.containsKey(cls)){
			cls = classConversions.get(cls);
		}
		for (Bestiary cat : values()) {
			if (cat.seen.containsKey(cls) && !cat.seen.get(cls)) {
				cat.seen.put(cls, true);
				Journal.saveNeeded = true;
			}
		}
	}

	public static int encounterCount(Class<?> cls) {
		for (Bestiary cat : values()) {
			if (cat.encounterCount.containsKey(cls)) {
				return cat.encounterCount.get(cls);
			}
		}
		return 0;
	}

	//used primarily when bosses are killed and need to clean up their minions
	public static boolean skipCountingEncounters = false;

	public static void countEncounter(Class<?> cls){
		countEncounters(cls, 1);
	}

	public static void countEncounters(Class<?> cls, int encounters){
		if (skipCountingEncounters){
			return;
		}
		if (classConversions.containsKey(cls)){
			cls = classConversions.get(cls);
		}
		for (Bestiary cat : values()) {
			if (cat.encounterCount.containsKey(cls) && cat.encounterCount.get(cls) != Integer.MAX_VALUE){
				cat.encounterCount.put(cls, cat.encounterCount.get(cls)+encounters);
				if (cat.encounterCount.get(cls) < -1_000_000_000){ //to catch cases of overflow
					cat.encounterCount.put(cls, Integer.MAX_VALUE);
				}
				Journal.saveNeeded = true;
			}
		}
	}

	private static final String BESTIARY_CLASSES    = "bestiary_classes";
	private static final String BESTIARY_SEEN       = "bestiary_seen";
	private static final String BESTIARY_ENCOUNTERS = "bestiary_encounters";

	public static void store( Bundle bundle ){

		ArrayList<Class<?>> classes = new ArrayList<>();
		ArrayList<Boolean> seen = new ArrayList<>();
		ArrayList<Integer> encounters = new ArrayList<>();

		for (Bestiary cat : values()) {
			for (Class<?> entity : cat.entities()) {
				if (cat.seen.get(entity) || cat.encounterCount.get(entity) > 0){
					classes.add(entity);
					seen.add(cat.seen.get(entity));
					encounters.add(cat.encounterCount.get(entity));
				}
			}
		}

		Class<?>[] storeCls = new Class[classes.size()];
		boolean[] storeSeen = new boolean[seen.size()];
		int[] storeEncounters = new int[encounters.size()];

		for (int i = 0; i < storeCls.length; i++){
			storeCls[i] = classes.get(i);
			storeSeen[i] = seen.get(i);
			storeEncounters[i] = encounters.get(i);
		}

		bundle.put( BESTIARY_CLASSES, storeCls );
		bundle.put( BESTIARY_SEEN, storeSeen );
		bundle.put( BESTIARY_ENCOUNTERS, storeEncounters );

	}

	public static void restore( Bundle bundle ){

		if (bundle.contains(BESTIARY_CLASSES)
				&& bundle.contains(BESTIARY_SEEN)
				&& bundle.contains(BESTIARY_ENCOUNTERS)){
			Class<?>[] classes = bundle.getClassArray(BESTIARY_CLASSES);
			boolean[] seen = bundle.getBooleanArray(BESTIARY_SEEN);
			int[] encounters = bundle.getIntArray(BESTIARY_ENCOUNTERS);

			for (int i = 0; i < classes.length; i++){
				for (Bestiary cat : values()){
					if (cat.seen.containsKey(classes[i])){
						cat.seen.put(classes[i], seen[i]);
						cat.encounterCount.put(classes[i], encounters[i]);
					}
				}
			}
		}

	}

}
