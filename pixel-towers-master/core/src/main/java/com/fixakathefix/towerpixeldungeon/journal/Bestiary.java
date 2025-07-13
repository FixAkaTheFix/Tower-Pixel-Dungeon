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

package com.fixakathefix.towerpixeldungeon.journal;

import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.huntress.SpiritHawk;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.SmokeBomb;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Acidic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ArmoredBrute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ArmoredStatue;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bee;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTenguClone;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTroll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CrystalMimic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM200;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM201;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMW;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWBody;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWHead;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWMinion;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWWheels;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DemonSpawner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DrillBig;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Eye;
import com.fixakathefix.towerpixeldungeon.actors.mobs.FetidRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollBlind;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goblin;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinFat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinGiant;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinNinja;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinSand;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoldenMimic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GreatCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrabNoShell;
import com.fixakathefix.towerpixeldungeon.actors.mobs.LicteriaLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mimic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Necromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Piranha;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RatKingAvatar;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Scorpio;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shinobi;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slugger;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Spinner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Statue;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Swarm;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogDzewa;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogFist;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.Sheep;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Banner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatArcher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatKnife;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatLeader;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatMage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatShield;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.IceWall;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskBloodstone;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskNecrotic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskPermafrost;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.SubAmuletTower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbowBallista;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbowGatling;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgunSniper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgunSpitter;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGraveCrypt;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGraveElite;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuardPaladin;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuardSpearman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerMiner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerPylon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerPylonBroken;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRatCamp;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRoseBush;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWallRunic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWallSpiked;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand3;
import com.fixakathefix.towerpixeldungeon.items.artifacts.DriedRose;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfRegrowth;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfWarding;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.rooms.special.SentryRoom;
import com.fixakathefix.towerpixeldungeon.levels.traps.AlarmTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.BlazingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.BurningTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.ChillingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.ConfusionTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.CorrosionTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.CursingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DisarmingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DisintegrationTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DistortionTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.ExplosiveTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FlashingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FlockTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FrostTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GatewayTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GeyserTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GrimTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GrippingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GuardianTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.OozeTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.PitfallTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.PoisonDartTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.RockfallTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.ShockingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.StormTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.SummoningTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.TeleportationTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.TenguDartTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.ToxicTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.WarpingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.WeakeningTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.WornDartTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.BlandfruitBush;
import com.fixakathefix.towerpixeldungeon.plants.Blindweed;
import com.fixakathefix.towerpixeldungeon.plants.Earthroot;
import com.fixakathefix.towerpixeldungeon.plants.Fadeleaf;
import com.fixakathefix.towerpixeldungeon.plants.Firebloom;
import com.fixakathefix.towerpixeldungeon.plants.Icecap;
import com.fixakathefix.towerpixeldungeon.plants.Mageroyal;
import com.fixakathefix.towerpixeldungeon.plants.Rotberry;
import com.fixakathefix.towerpixeldungeon.plants.Sorrowmoss;
import com.fixakathefix.towerpixeldungeon.plants.Starflower;
import com.fixakathefix.towerpixeldungeon.plants.Stormvine;
import com.fixakathefix.towerpixeldungeon.plants.Sungrass;
import com.fixakathefix.towerpixeldungeon.plants.Swiftthistle;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

//contains all the game's various entities, mostly enemies, NPCS, and allies, but also traps and plants
public enum Bestiary
		{

	NORMAL,
	ELITE,
	BOSSES,
	UNIVERSAL,
	ALLY,
	TOWERS,
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

		NORMAL.addEntities(Rat.class, Albino.class, Snake.class, GnollBlind.class, GnollThrower.class, Gnoll.class, Swarm.class, Crab.class, Slime.class,
				HermitCrab.class, HermitCrabNoShell.class,CausticSlime.class,
				Skeleton.class, Thief.class, Bandit.class, DM100.class, Guard.class, Necromancer.class, SpectralNecromancer.class, Shinobi.class,
				Bat.class, Brute.class, ArmoredBrute.class, Shaman.RedShaman.class, Shaman.BlueShaman.class, Shaman.PurpleShaman.class, Spinner.class, DM200.class, DM201.class,
				Goblin.class, GoblinFat.class, GoblinNinja.class, GoblinSand.class,
				GoblinShaman.ShamanStrength.class, GoblinShaman.ShamanRegen.class, GoblinShaman.ShamanShield.class, GoblinShaman.ShamanFake.class,
				Slugger.class,
				Ghoul.class, Elemental.FireElemental.class, Elemental.FrostElemental.class, Elemental.ShockElemental.class, Elemental.ChaosElemental.class,
				Warlock.class, Monk.class, Senior.class, Golem.class,
				RipperDemon.class, DemonSpawner.class, Succubus.class, Eye.class, Scorpio.class, Acidic.class);
		ELITE.addEntities(FetidRat.class, ChiefRat.class, GnollTrickster.class, Goo.class, GreatCrab.class, MagiCrab.class, GoblinGiant.class,
				SkeletonArmored.class, SkeletonArmoredShielded.class
		);

		BOSSES.addEntities( BossRatKing.class, BossOoze.class,
				BossNecromancer.class,
				BossTengu.class, BossTenguClone.class, BossTroll.class,
				DMW.class, DMWHead.class, DMWBody.class, DMWWheels.class, DMWMinion.class,
				BossDwarfKing.class
				//,YogDzewa.Larva.class, YogFist.BurningFist.class, YogFist.SoiledFist.class, YogFist.RottingFist.class, YogFist.RustedFist.class,YogFist.BrightFist.class, YogFist.DarkFist.class, YogDzewa.class
				);

		UNIVERSAL.addEntities(EnemyPortal.class, Wraith.class,Sheep.class, DrillBig.class, Piranha.class, Mimic.class, GoldenMimic.class, CrystalMimic.class,  Statue.class, ArmoredStatue.class);

		ALLY.addEntities(
				Bee.class,
				Arena.AmuletTower.class, SubAmuletTower.class,
				MirrorImage.class, PrismaticImage.class, RatKingAvatar.class,
				TowerRoseBush.GhostHero.class,

				WandOfWarding.Ward.class, WandOfLivingEarth.EarthGuardian.class,
				ShadowClone.ShadowAlly.class, Banner.class, IceWall.class,
				CampRatKnife.class, CampRatLeader.class, CampRatShield.class, CampRatArcher.class, CampRatMage.class,
				LicteriaLasher.class, ObeliskBloodstone.class, ObeliskNecrotic.class, ObeliskPermafrost.class,
				NormalShopKeeper.class, TowerShopKeeper.class
				);
		TOWERS.addEntities(
				TowerCrossbow1.class, TowerCrossbow2.class, TowerCrossbow3.class, TowerCrossbowBallista.class, TowerCrossbowGatling.class,
				TowerWand1.class, TowerWand2.class, TowerWand3.class,
				TowerWall1.class, TowerWall2.class, TowerWall3.class, TowerWallRunic.class, TowerWallSpiked.class,
				TowerCannon1.class, TowerCannon2.class, TowerCannon3.class, TowerCannonMissileLauncher.class, TowerCannonNuke.class,
				TowerGuard1.class, TowerGuard2.class, TowerGuard3.class, TowerGuardPaladin.class, TowerGuardSpearman.class,
				TowerLightning1.class, TowerLightning2.class, TowerLightning3.class,
				TowerGrave1.class, TowerGrave2.class, TowerGrave3.class, TowerGraveCrypt.class, TowerGraveElite.class,
				TowerDartgun1.class, TowerDartgun2.class, TowerDartgun3.class, TowerDartgunSpitter.class, TowerDartgunSniper.class,
				TowerDisintegration1.class, TowerDisintegration2.class, TowerDisintegration3.class,
				TowerTntLog.class,
				TowerTotem.TotemHealing.class, TowerTotem.TotemAttack.class, TowerTotem.TotemNecrotic.class, TowerTotem.TotemShield.class,
				TowerMiner.class,
				TowerPylonBroken.class, TowerPylon.class, TowerRatCamp.class, TowerRoseBush.class
				);

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
