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

package com.fixakathefix.towerpixeldungeon;

import com.fixakathefix.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTroll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMW;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogDzewa;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.artifacts.Artifact;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Badges {
	
	public enum Badge {

		//statistical
		MONSTERS_SLAIN_1( 0 ), MONSTERS_SLAIN_2( 1 ), MONSTERS_SLAIN_3( 2 ), MONSTERS_SLAIN_4( 3 ),MONSTERS_SLAIN_5( 4 ), MONSTERS_SLAIN_6( 5 ),
		FOOD_EATEN_1( 16 ),FOOD_EATEN_2( 17 ), FOOD_EATEN_3( 18 ), FOOD_EATEN_4( 19 ), FOOD_EATEN_5 ( 20 ), FOOD_EATEN_6 ( 21 ),
		STRENGTH_ATTAINED_1( 32 ), STRENGTH_ATTAINED_2( 33), STRENGTH_ATTAINED_3( 34 ),STRENGTH_ATTAINED_4( 35 ),STRENGTH_ATTAINED_5( 36 ), STRENGTH_ATTAINED_6( 37 ),
		ITEM_LEVEL_1( 48 ), ITEM_LEVEL_2( 49 ), ITEM_LEVEL_3( 50 ),ITEM_LEVEL_4( 51 ),ITEM_LEVEL_5( 52 ), ITEM_LEVEL_6( 53 ),
		GAMES_PLAYED_1 ( 64, true ),GAMES_PLAYED_2( 65, true ),GAMES_PLAYED_3( 66, true ),GAMES_PLAYED_4( 67, true ),GAMES_PLAYED_5( 68, true ),
		TOWERS_BUILT_1 ( 80),TOWERS_BUILT_2 ( 81),TOWERS_BUILT_3 ( 82),TOWERS_BUILT_4 ( 83),TOWERS_BUILT_5 ( 84),


		//copper
		CHIEF(96),FAKESHAMAN(97),TROLL(98), RATKING(99), REMAC(100),DEATH(101, true),
		//wood
		DUST(112), CANDLE(113), SNAKED(114), GETOVERHERE(115),
		//silver
		HOLY_NOVA(128), NUKED(129), EYE_SLAYER(130),DEATH_FROM_FRIENDLY_MAGIC( 131 ),POTION_ADDICT(132),BOOKWORM(133), UNDEAD(134),
		//gold
		DRINK_TOXIC_GAS(144),GRIM_WEAPON( 145 ),NEO_DODGE( 146 ), ALMOST_LOST_1(147), BLOOD_CULTIST_1(148),DEATH_2(149),
		//diamond
		ALMOST_LOST_2(160),TOWER_TYPES(161),CAPTAIN(162),AVATAR(163), PYLONS(164), NOPYLONS(165),
		//ruby
		DUELIST_SAC(176), ALMOST_LOST_3(177),BLOOD_CULTIST_2(178),
		//special
		REDEMPTION(192),

//192

		//bosses
		OOZE(208), TENGU(209), DWM(210),KING(211),YOG(212), VICTORY( 213 , true);


		public boolean meta;

		public int image;
		
		Badge( int image ) {
			this( image, false );
		}
		
		Badge( int image, boolean meta ) {
			this.image = image;
			this.meta = meta;
		}

		public String title(){
			return Messages.get(this, name()+".title");
		}

		public String desc(){
			return Messages.get(this, name()+".desc");
		}
		
		Badge() {
			this( -1 );
		}
	}
	
	private static HashSet<Badge> global;
	private static HashSet<Badge> local = new HashSet<>();
	
	private static boolean saveNeeded = false;

	public static void reset() {
		local.clear();
		loadGlobal();
	}
	
	public static final String BADGES_FILE	= "badges.dat";
	private static final String BADGES		= "badges";
	
	private static final HashSet<String> removedBadges = new HashSet<>();
	static{
		//v1.3.0 (These were removed and re-added internally as new unlock reqs were added)
		removedBadges.add("YASD");
		removedBadges.add("DEATH_FROM_GLYPH");
	}

	private static final HashMap<String, String> renamedBadges = new HashMap<>();
	static{
		//no renamed badges currently
	}

	public static HashSet<Badge> restore( Bundle bundle ) {
		HashSet<Badge> badges = new HashSet<>();
		if (bundle == null) return badges;
		
		String[] names = bundle.getStringArray( BADGES );
		if (names == null) return badges;

		for (int i=0; i < names.length; i++) {
			try {
				if (renamedBadges.containsKey(names[i])){
					names[i] = renamedBadges.get(names[i]);
				}
				if (!removedBadges.contains(names[i])){
					badges.add( Badge.valueOf( names[i] ) );
				}
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException(e);
			}
		}

		addReplacedBadges(badges);
	
		return badges;
	}
	
	public static void store( Bundle bundle, HashSet<Badge> badges ) {
		addReplacedBadges(badges);

		int count = 0;
		String names[] = new String[badges.size()];
		
		for (Badge badge:badges) {
			names[count++] = badge.name();
		}
		bundle.put( BADGES, names );
	}
	
	public static void loadLocal( Bundle bundle ) {
		local = restore( bundle );
	}
	
	public static void saveLocal( Bundle bundle ) {
		store( bundle, local );
	}
	
	public static void loadGlobal() {
		if (global == null) {
			try {
				Bundle bundle = FileUtils.bundleFromFile( BADGES_FILE );
				global = restore( bundle );

			} catch (IOException e) {
				global = new HashSet<>();
			}
		}
	}

	public static void saveGlobal() {
		if (saveNeeded) {
			
			Bundle bundle = new Bundle();
			store( bundle, global );
			
			try {
				FileUtils.bundleToFile(BADGES_FILE, bundle);
				saveNeeded = false;
			} catch (IOException e) {
				ShatteredPixelDungeon.reportException(e);
			}
		}
	}

	public static int totalUnlocked(boolean global){
		if (global) return Badges.global.size();
		else        return Badges.local.size();
	}

	public static void validateMonstersSlain() {
		Badge badge = null;
		
		if (!local.contains( Badge.MONSTERS_SLAIN_1 ) && Statistics.enemiesSlain >= 100) {
			badge = Badge.MONSTERS_SLAIN_1;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_2 ) && Statistics.enemiesSlain >= 200) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_2;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_3 ) && Statistics.enemiesSlain >= 500) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_3;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_4 ) && Statistics.enemiesSlain >= 1000) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_4;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_5 ) && Statistics.enemiesSlain >= 3000) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_5;
			local.add( badge );
		}
		if (!local.contains( Badge.MONSTERS_SLAIN_6 ) && Statistics.enemiesSlain >= 10000) {
			if (badge != null) unlock(badge);
			badge = Badge.MONSTERS_SLAIN_6;
			local.add( badge );
		}
		
		displayBadge( badge );
	}

	public static void validateStrengthAttained() {
		Badge badge = null;
		
		if (!local.contains( Badge.STRENGTH_ATTAINED_1 ) && Dungeon.hero.STR >= 15) {
			badge = Badge.STRENGTH_ATTAINED_1;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_2 ) && Dungeon.hero.STR >= 17) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_2;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_3 ) && Dungeon.hero.STR >= 20) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_3;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_4 ) && Dungeon.hero.STR >= 25) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_4;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_5 ) && Dungeon.hero.STR >= 30) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_5;
			local.add( badge );
		}
		if (!local.contains( Badge.STRENGTH_ATTAINED_6 ) && Dungeon.hero.STR >= 40) {
			if (badge != null) unlock(badge);
			badge = Badge.STRENGTH_ATTAINED_6;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	
	public static void validateFoodEaten() {
		Badge badge = null;
		
		if (!local.contains( Badge.FOOD_EATEN_1 ) && Statistics.foodEaten >= 1) {
			badge = Badge.FOOD_EATEN_1;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_2 ) && Statistics.foodEaten >= 3) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_2;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_3 ) && Statistics.foodEaten >= 6) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_3;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_4 ) && Statistics.foodEaten >= 10) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_4;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_5 ) && Statistics.foodEaten >= 20) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_5;
			local.add( badge );
		}
		if (!local.contains( Badge.FOOD_EATEN_6 ) && Statistics.foodEaten >= 50) {
			if (badge != null) unlock(badge);
			badge = Badge.FOOD_EATEN_6;
			local.add( badge );
		}
		
		displayBadge( badge );
	}


	
	public static void validateItemLevelAquired( Item item ) {
		
		// This method should be called:
		// 1) When an item is obtained (Item.collect)
		// 2) When an item is upgraded (ScrollOfUpgrade, ScrollOfWeaponUpgrade, ShortSword, WandOfMagicMissile)
		// 3) When an item is identified

		// Note that artifacts should never trigger this badge as they are alternatively upgraded
		if (!item.levelKnown || item instanceof Artifact) {
			return;
		}
		Badge badge = null;
		if (!local.contains( Badge.ITEM_LEVEL_1 ) && item.level() >= 1) {
			badge = Badge.ITEM_LEVEL_1;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_2 ) && item.level() >= 3) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_2;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_3 ) && item.level() >= 6) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_3;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_4 ) && item.level() >= 10) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_4;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_5 ) && item.level() >= 14) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_5;
			local.add( badge );
		}
		if (!local.contains( Badge.ITEM_LEVEL_5 ) && item.level() >= 29) {
			if (badge != null) unlock(badge);
			badge = Badge.ITEM_LEVEL_5;
			local.add( badge );
		}
		
		displayBadge( badge );
	}
	public static void validateGamesPlayed() {
		Badge badge = null;
		if (Rankings.INSTANCE.totalNumber >= 5 ) {
			badge = Badge.GAMES_PLAYED_1;
		}
		if (Rankings.INSTANCE.totalNumber >= 20 ) {
			badge = Badge.GAMES_PLAYED_2;
		}
		if (Rankings.INSTANCE.totalNumber >= 50 ) {
			badge = Badge.GAMES_PLAYED_3;
		}
		if (Rankings.INSTANCE.totalNumber >= 200 ) {
			badge = Badge.GAMES_PLAYED_4;
		}
		if (Rankings.INSTANCE.totalNumber >= 500) {
			badge = Badge.GAMES_PLAYED_5;
		}

		displayBadge( badge );
	}
	public static void validateTowersBuilt(int towers) {
		Badge badge = null;
		if (towers >= 20 ) {
			badge = Badge.TOWERS_BUILT_1;
		}
		if (towers >= 30 ) {
			badge = Badge.TOWERS_BUILT_2;
		}
		if (towers >= 50 ) {
			badge = Badge.TOWERS_BUILT_3;
		}
		if (towers >= 70 ) {
			badge = Badge.TOWERS_BUILT_4;
		}
		if (towers >= 100) {
			badge = Badge.TOWERS_BUILT_5;
		}

		displayBadge( badge );
	}

	public static void validateEnemy(Mob mob) {
		Badge badge = null;
		if (mob instanceof ChiefRat) badge = Badge.CHIEF;
		else if (mob instanceof BossTroll) badge = Badge.TROLL;
		else if (mob instanceof GoblinShaman.ShamanFake) badge = Badge.FAKESHAMAN;
		else if (mob instanceof BossRatKing) badge = Badge.RATKING;
		else if (mob instanceof BossNecromancer) badge = Badge.REMAC;
		else if (mob instanceof BossOoze) badge = Badge.OOZE;
		else if (mob instanceof BossTengu) badge = Badge.TENGU;
		else if (mob instanceof DMW) badge = Badge.DWM;
		else if (mob instanceof BossDwarfKing) badge = Badge.KING;
		else if (mob instanceof YogDzewa) badge = Badge.YOG;
		if (badge != null){
			local.add( badge );
			displayBadge( badge );
		};
	}

	public static void validateAlmostLost1() {
		Badge badge = Badge.ALMOST_LOST_1;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateEyeSlayer() {
		Badge badge = Badge.EYE_SLAYER;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateAlmostLost2() {
		Badge badge = Badge.ALMOST_LOST_2;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateAlmostLost3() {
		Badge badge = Badge.ALMOST_LOST_3;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateTowerTypes() {
		Badge badge = Badge.TOWER_TYPES;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDust() {
		Badge badge = Badge.DUST;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateCandle() {
		Badge badge = Badge.CANDLE;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateSnaked() {
		Badge badge = Badge.SNAKED;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateGetoverhere() {
		Badge badge = Badge.GETOVERHERE;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validatePotionAddict() {
		Badge badge = Badge.POTION_ADDICT;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateBookworm() {
		Badge badge = Badge.BOOKWORM;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateUndead() {
		Badge badge = Badge.UNDEAD;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDeath() {
		Badge badge = Badge.DEATH;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDeath2() {
		Badge badge = Badge.DEATH_2;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateBloodCultist(int index) {
		Badge badge;
		switch (index){
			case 1: default: {
				badge = Badge.BLOOD_CULTIST_1;
				break;
			}
			case 2: {
				badge = Badge.BLOOD_CULTIST_2;
				break;
			}
		}

		local.add( badge );
		displayBadge( badge );
	}

	public static void validatePylons() {
		Badge badge = Badge.PYLONS;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateVictory() {
		Badge badge = Badge.VICTORY;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateNoPylons() {
		Badge badge = Badge.NOPYLONS;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDeathFromFriendlyMagic() {
		Badge badge = Badge.DEATH_FROM_FRIENDLY_MAGIC;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDuelistSac() {
		Badge badge = Badge.DUELIST_SAC;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateRedemption() {
		Badge badge = Badge.REDEMPTION;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateDrinkToxicGas() {
		Badge badge = Badge.DRINK_TOXIC_GAS;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateNeoDodge() {
		Badge badge = Badge.NEO_DODGE;
		local.add( badge );
		displayBadge( badge );
	}

	public static void validateHolyNova() {
		Badge badge = Badge.HOLY_NOVA;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateNuked() {
		Badge badge = Badge.NUKED;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateAvatar() {
		Badge badge = Badge.AVATAR;
		local.add( badge );
		displayBadge( badge );
	}
	public static void validateCaptain() {
		Badge badge = Badge.CAPTAIN;
		local.add( badge );
		displayBadge( badge );
	}



	public static void validateGrimWeapon() {
		if (!local.contains( Badge.GRIM_WEAPON )) {
			Badge badge = Badge.GRIM_WEAPON;
			local.add( badge );
			displayBadge( badge );
		}
	}

	
	private static void displayBadge( Badge badge ) {
		
		if (badge == null || !Dungeon.customSeedText.isEmpty()) {
			return;
		}
		
		if (isUnlocked( badge )) {
			//REMOVED! badges display only when gained for the first time
			/*if (!badge.meta) {
				GLog.h( Messages.get(Badges.class, "endorsed", badge.title()) );
				GLog.newLine();
			}*/
			
		} else {
			
			unlock(badge);
			
			GLog.h( Messages.get(Badges.class, "new", badge.title() + " (" + badge.desc() + ")") );
			GLog.newLine();
			PixelScene.showBadge( badge );
		}
	}
	
	public static boolean isUnlocked( Badge badge ) {
		return global.contains( badge );
	}
	
	public static HashSet<Badge> allUnlocked(){
		loadGlobal();
		return new HashSet<>(global);
	}
	
	public static void disown( Badge badge ) {
		loadGlobal();
		global.remove( badge );
		saveNeeded = true;
	}
	
	public static void unlock( Badge badge ){
		if (!isUnlocked(badge) && Dungeon.customSeedText.isEmpty()){
			if (badge == Badge.VICTORY) SPDSettings.introsOff(true);//a sneaky thing for intros to stop showing after the first gaining of the badge
			global.add( badge );
			saveNeeded = true;
		}
	}

	public static List<Badge> filterReplacedBadges( boolean global ) {

		ArrayList<Badge> badges = new ArrayList<>(global ? Badges.global : Badges.local);

		Iterator<Badge> iterator = badges.iterator();
		while (iterator.hasNext()) {
			Badge badge = iterator.next();
			if ((!global && badge.meta) || badge.image == -1) {
				iterator.remove();
			}
		}

		Collections.sort(badges);

		return filterReplacedBadges(badges);

	}

	//only show the highest unlocked and the lowest locked
	private static final Badge[][] tierBadgeReplacements = new Badge[][]{

			//{Badge.GAMES_PLAYED_1, Badge.GAMES_PLAYED_2, Badge.GAMES_PLAYED_3, Badge.GAMES_PLAYED_4, Badge.GAMES_PLAYED_5},
	};

	//don't show the later badge if the earlier one isn't unlocked
	private static final Badge[][] prerequisiteBadges = new Badge[][]{
			{Badge.OOZE ,Badge.OOZE  },
			{Badge.FOOD_EATEN_1, Badge.FOOD_EATEN_2, Badge.FOOD_EATEN_3, Badge.FOOD_EATEN_4, Badge.FOOD_EATEN_5 },
			{Badge.GAMES_PLAYED_1, Badge.GAMES_PLAYED_2, Badge.GAMES_PLAYED_3,Badge.GAMES_PLAYED_4,Badge.GAMES_PLAYED_5},
			{Badge.BLOOD_CULTIST_1, Badge.BLOOD_CULTIST_2},
			{Badge.MONSTERS_SLAIN_1,Badge.MONSTERS_SLAIN_2,Badge.MONSTERS_SLAIN_3, Badge.MONSTERS_SLAIN_4, Badge.MONSTERS_SLAIN_5},
			{Badge.TOWERS_BUILT_1, Badge.TOWERS_BUILT_2, Badge.TOWERS_BUILT_3, Badge.TOWERS_BUILT_4, Badge.TOWERS_BUILT_5},
			{Badge.ITEM_LEVEL_1, Badge.ITEM_LEVEL_2, Badge.ITEM_LEVEL_3, Badge.ITEM_LEVEL_4, Badge.ITEM_LEVEL_5},
			{Badge.STRENGTH_ATTAINED_1, Badge.STRENGTH_ATTAINED_2, Badge.STRENGTH_ATTAINED_3, Badge.STRENGTH_ATTAINED_4, Badge.STRENGTH_ATTAINED_5},
			{Badge.TENGU,Badge.TENGU },
			{Badge.DWM  ,Badge.DWM   },
			{Badge.KING ,Badge.KING  },
			{Badge.YOG  ,Badge.YOG   },
			{Badge.RATKING ,Badge.RATKING   },
			{Badge.AVATAR ,Badge.AVATAR },
			{Badge.VICTORY ,Badge.VICTORY   },
			{Badge.CHIEF ,Badge.CHIEF   },
			{Badge.REMAC,Badge.REMAC},
			{Badge.GETOVERHERE ,Badge.GETOVERHERE   },
			{Badge.CAPTAIN ,Badge.CAPTAIN   },
			{Badge.DEATH_2,Badge.DEATH_2   },
	};

	//If the summary badge is unlocked, don't show the component badges
	private static final Badge[][] summaryBadgeReplacements = new Badge[][]{

			/*{Badge.DEATH_FROM_FRIENDLY_MAGIC, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_SACRIFICE, Badge.DEATH_FROM_ALL},
			{Badge.DEATH_FROM_GRIM_TRAP, Badge.DEATH_FROM_ALL},*/

	};
	
	public static List<Badge> filterReplacedBadges( List<Badge> badges ) {

		for (Badge[] tierReplace : tierBadgeReplacements){
			leaveBest( badges, tierReplace );
		}

		for (Badge[] metaReplace : summaryBadgeReplacements){
			leaveBest( badges, metaReplace );
		}
		
		return badges;
	}
	
	private static void leaveBest( Collection<Badge> list, Badge...badges ) {
		for (int i=badges.length-1; i > 0; i--) {
			if (list.contains( badges[i])) {
				for (int j=0; j < i; j++) {
					list.remove( badges[j] );
				}
				break;
			}
		}
	}

	public static List<Badge> filterBadgesWithoutPrerequisites(List<Badges.Badge> badges ) {

		for (Badge[] prereqReplace : prerequisiteBadges){
			leaveWorst( badges, prereqReplace );
		}

		for (Badge[] tierReplace : tierBadgeReplacements){
			leaveWorst( badges, tierReplace );
		}

		Collections.sort( badges );

		return badges;
	}

	private static void leaveWorst( Collection<Badge> list, Badge...badges ) {
		for (int i=0; i < badges.length; i++) {
			if (list.contains( badges[i])) {
				for (int j=i+1; j < badges.length; j++) {
					list.remove( badges[j] );
				}
				break;
			}
		}
	}

	public static Collection<Badge> addReplacedBadges(Collection<Badges.Badge> badges ) {

		for (Badge[] tierReplace : tierBadgeReplacements){
			addLower( badges, tierReplace );
		}

		for (Badge[] metaReplace : summaryBadgeReplacements){
			addLower( badges, metaReplace );
		}

		return badges;
	}

	private static void addLower( Collection<Badge> list, Badge...badges ) {
		for (int i=badges.length-1; i > 0; i--) {
			if (list.contains( badges[i])) {
				for (int j=0; j < i; j++) {
					list.add( badges[j] );
				}
				break;
			}
		}
	}

	//used for badges with completion progress that would otherwise be hard to track
	public static String showCompletionProgress( Badge badge ){
		if (isUnlocked(badge)) return null;

		String result = "\n";

		/*if (badge == Badge.OOZE_SLAIN_ALL_CLASSES){
			for (HeroClass cls : HeroClass.values()){
				result += "\n";
				if (isUnlocked(firstBossClassBadges.get(cls)))  result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                            result += Messages.titleCase(cls.title());
			}

			return result;

		} else if (badge == Badge.VICTORY_ALL_CLASSES) {

			for (HeroClass cls : HeroClass.values()){
				result += "\n";
				if (isUnlocked(victoryClassBadges.get(cls)))    result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                            result += Messages.titleCase(cls.title());
			}

			return result;

		} else if (badge == Badge.BOSS_SLAIN_3_ALL_SUBCLASSES){

			for (HeroSubClass cls : HeroSubClass.values()){
				if (cls == HeroSubClass.NONE) continue;
				result += "\n";
				if (isUnlocked(thirdBossSubclassBadges.get(cls)))   result += "_" + Messages.titleCase(cls.title()) + "_";
				else                                                result += Messages.titleCase(cls.title()) ;
			}

			return result;
		}*/

		return null;
	}
}
