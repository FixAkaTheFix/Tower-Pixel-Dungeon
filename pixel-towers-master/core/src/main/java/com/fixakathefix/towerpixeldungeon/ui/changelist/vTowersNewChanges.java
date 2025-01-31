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

package com.fixakathefix.towerpixeldungeon.ui.changelist;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.fixakathefix.towerpixeldungeon.sprites.BeeSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CausticSlimeSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ElementalSprite;
import com.fixakathefix.towerpixeldungeon.sprites.GorematiaSpiritSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.PortalUnstableSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ShopkeeperSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerRatCampSprite;
import com.fixakathefix.towerpixeldungeon.sprites.YogSprite;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class vTowersNewChanges {

	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_Coming_Soon(changeInfos);
		add_v010_Changes(changeInfos);
		add_v020_Changes(changeInfos);
		add_v030_Changes(changeInfos);
		add_v031_Changes(changeInfos);
		add_v032_Changes(changeInfos);
		add_v050_Changes(changeInfos);
	}

	public static void add_Coming_Soon( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("Coming Soon", true, "");
		changes.hardlight(0xCCCCCC);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.CHALLENGE_ON), "General",
				"Well, the new update is out and it is of great quality and took great effort. Not like I am begging for donations, as I didn't even add an option to do them :)\n\n" +
				"Next update will depend on reception from the Community, may appear, or may not be released at all. Soooo, please tell me about all the bugs, your ideas on #5PD discord#5, this helps very much!"));

		changes.addButton( new ChangeButton(Icons.get(Icons.TOWER), "new Towers?",
				"I generally don't know what towers too add. These ideas, mentioned in the previous version that I think is still installed (as the package name is different): the hive, the beebot and Ice rod are easy to add and are generally bad. They do not provide additional gameplay time which I crave. But, here is another bunch of suggested tower ideas\n\n",
				"- The Stomper: a melee tower that pushes enemies from it.\n",
				"- The Healing station - will heal allies in an area\n",
				"- A dummy - a moving tower that easily evades attacks and distracts enemies\n"
				));

		changes.addButton( new ChangeButton(Icons.get(Icons.DUELIST), "new Heroes",
				"Well, next are Cleric (not the one Evan added, he is op and I generally dislike the direction of his... balancing. Too op even for pixel towers) and the Engineer."));

		changes.addButton( new ChangeButton(Icons.get(Icons.AMULET), "new Modes",
				"Idk, endless mode is done. Suggest me modes in #2reddit#2 and #5discord#5"));

		changes.addButton( new ChangeButton(new YogSprite(), "Yog-Dzewa sealing ritual",
				"The ritual already has a full concept of #0f#0#9o#9#5u#5#7r#7 phases. You can guess what that means phphph. Thanks for reading this... It means you are with me on that journey, and that means a lot"));

	}

	public static void add_v010_Changes( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v0.1.0", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), "General",
				 "So, currently the game has:\n" +
						 "- an original wave mechanic\n" +
						 "- 20 levels, each is unique and has 3 modes to play\n" +
						 "- over 12 towers, some of which are level-specific\n" +
						 "- 3 abilities for each hero\n" +
						 "- a ton of menu styles to choose from\n" +
						 "- a ton of new enemies and many original bosses"));

		changes.addButton(new ChangeButton(Icons.get(Icons.SLOT1), "Last update",
				"The new 0.1.0 version added:\n" +
						"- 5 new levels\n" +
						"- 7 new towers\n" +
						"- 2 new modes for each level\n" +
						"- new menus, slot and tower equipment systems\n" +
						"- hero abilities\n" +
						"- Lore"));
		changes = new ChangeInfo("0.1.1", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugfixes and additions",
				"The new 0.1.1 version has a ton of bugs fixed:\n" +
						"- skills cost the intended amount of coins now\n" +
						"- minibosses are no longer corruptable\n" +
						"- many minor spelling mistakes were fixed (thx Ifrit)\n" +
						"- several minor ui glitches fixed\n" +
						"- level 3 difficulty lowered\n" +
						"- Styles have more beatiful elements now"));

		changes.addButton(new ChangeButton(new GorematiaSpiritSprite(), "#1???#1",
				Random.oneOf("#1HEY, WHERE IS THE RAT KING?????#1",
						"#1I WON'T BE DELETED THAT EASILY!!!1#",
						"#1YOU WON'T DARE TO...#1")));


	}
	public static void add_v020_Changes( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v0.2.0", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new ItemSprite(new WandOfMagicMissile()), "Additions",
				"All new content added in ver-0.2.0 includes:\n" +
						"- Almost all wands reworked. Also most of them have a new sprite\n" +
						"- Skills rebalanced. Some got new icons too and reworked in mechanic, like Rogue's Rush skill, or blessing\n" +
						"- Added some visuals to effects, such as blessing, and reworked berserk totem, which grants it's buff for longer durations at the cost of the effect's power"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugs and balancing",
				"Bugs removed:\n" +
						"- pushing-yourself-into-the-void bug. Now you can't cheese the tengu level, sorry((((\n" +
						"- levels 4, 10, 11, 12 were rebalanced\n" +
						"- The visual bug with the walls in stage 2 not disappearing after burning"));
		/*changes = new ChangeInfo("0.1.1", false, null);
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugfixes and additions",
				"The new 0.1.1 version has a ton of bugs fixed:\n" +
						"- skills cost the intended amount of coins now\n" +
						"- minibosses are no longer corruptable\n" +
						"- many minor spelling mistakes were fixed (thx Ifrit)\n" +
						"- several minor ui glitches fixed\n" +
						"- level 3 difficulty lowered\n" +
						"- Styles have more beatiful elements now"));

		changes.addButton(new ChangeButton(new GorematiaSpiritSprite(), "#???#",
				Random.oneOf("#HEY, WHERE IS THE RAT KING?????#",
						"#I WON'T BE DELETED THAT EASILY!!!#",
						"#YOU WON'T DARE TO...#")));*/


	}
	public static void add_v030_Changes( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v0.3.0", false, null);
		changes.hardlight(CharSprite.MYSTERIOUS);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new PortalUnstableSprite(), "Additions",
				"\n\n" +
						"All new content added in ver-0.3.0 includes:\n" +
						"- Switched the Amulet tower to portals, which have less HP, but the enemy disappears upon entering them, allowing for early dif buffer \n" +
						"- AI of some monsters had some changes, for example: snakes ignore towers and run directly to the amulet\n" +
						"- Portals, bosses dying or being hurt have cool animations\n" +
						"- Magicrabs are immune to lightning now\n" +
						"- Poison effect has a visual of green bubbles emitting"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugs and balancing",
				"Bugs removed:\n" +
						"- Fixed bosses crashing on summoning minions\n" +
						"- Fixed a lot of ui bugs for Tower Selection scene, including button and picture alignment\n" +
						"- Fixed mage's necromancy skeleton placement\n" +
						"- Fixed tower ai, including their targeting, especially dartguns targeting, which got reworked a bit to actually work as intended\n" +
						"- Some bosses don't end the game directly. That is useful for putting several of them into the level, and for future development"));

	}
	public static void add_v031_Changes( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v0.3.1", false, null);
		changes.hardlight(CharSprite.MYSTERIOUS);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new CausticSlimeSprite(), "Additions",
				"-Released 17.10.2024-\n" +
						"All new content added in ver-0.3.1 includes:\n" +
						"- Ooze does not damage the portal with it's jumps anymore"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugs and balancing",
				"Bugs removed:\n" +
						"- Ooze collision, effect display and sprite showing partially fixed\n" +
						"- Attack range display values in tower selection scene fixed"));

	}
	public static void add_v032_Changes( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("v0.3.2", false, null);
		changes.hardlight(CharSprite.MYSTERIOUS);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new RatSprite(), "Additions",
				"-Released 18.10.2024-\n" +
						"- No additions overall. A small bugfix only"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugs and balancing",
				"Bugs removed:\n" +
						"- Changes scene crash removed"));

	}
	public static void add_v050_Changes( ArrayList<ChangeInfo> changeInfos) {

		ChangeInfo changes = new ChangeInfo("v0.5.0", true, null);
		changes.hardlight(CharSprite.NEGATIVE);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.COPY), "General",
				"General update info:\n" +
						"- Released 31.01.25\n" +
						"- Implemented complete, huge overhauls of heroes, most items, shops, damage dealing and scaling, badges, journal\n" +
						"- rebalanced most towers and half of the stages, some enemies\n" +
						"- Fixed over 609 bugs"));

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.TANK, 0, 90, 12, 15), "New Heroes 1",
				"#1First new hero: the Tank#1:\n" +
						"- The Tank is a #1defense class#1, capable of surviving many hits, easily defend himself and allies\n" +
						"- His main feature is his helmet, which blocks 15% of physical damage, and he starts with some defensive equipment, like the Shield potion\n" +
						"- He can #1Raise shields#1 to give a weak shield to all nearby allies, perform a #1Wall stance#1 to increase defense even more at a cost of mobility, and #1Taunt#1 enemies to distract them from important Towers or points"));
		changes.addButton(new ChangeButton(new Image(Assets.Sprites.NECROHERO, 0, 90, 12, 15), "New Heroes 2",
				"#9Second new hero: the Necromancer#9:\n" +
						"- The Necromancer is a #9summoner class#9, and relies on undead minions and obelisks to defeat foes\n" +
						"- His body is frail: the necromancer has only 11 STR and no good starter equipment, but is excellent at magic\n" +
						"- He can #9Summon skeletons#9 as cannon fodder, #9Evade death#9 by healing from seemingly lethal hits and #9Conjure obelisks#9 - stone monoliths from the beyond which will help in battle"));
		changes.addButton(new ChangeButton(new Image(Assets.Sprites.WARRIOR, 0, 90, 12, 15), "Hero rework",
				"_Heroes_ have been heavily reworked:\n" +
						"- Heroes' _HP and regeneration_ has been heavily adjusted\n" +
						"- Strength mechanic got a minor rework\n" +
						"- _Heroes cannot die anymore_: instead they _Faint_ - lose consiousness for some turns before resurrecting near the portal. The more the hero dies, the longer is the cooldown\n" +
						"- Added a ton of new spells the heroes can utilise (see spells)"));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.HEROSPELL_SWIFT), "Spells addition and rework",
				"Spells have been reworked and a lot of new spells have been added:\n" +
						"- #1Taunt#1: force enemies to attack you\n" +
						"- #0Target#0: force towers to attack a specific enemy\n" +
						"- #2Treat wounds#2: heal a small portion of your HP for free\n" +
						"- #9Obelisk#9: create different obelisks to assist you in battle\n" +
						"- #4Vine lasher#4: create lashers that knockback foes\n" +
						"- #5Ice wall#5: conjure ice walls to stall foes\n" +
						"- #7Banner#7: Create banners that increase mobs' attack speed\n" +
						"- And many more... A list is a long one, no one would read it if it was. Just play around with heroes, each one of them has unique spells now"));
		changes.addButton(new ChangeButton(Icons.get(Icons.STAIRS), "Stages rework",
				"A lot of stages have been changed:\n" +
						"- _First stages_ became easier\n" +
						"- _Gnoll village_ generation was reworked\n" +
						"- Stage _9_ was changed into a puzzle level\n" +
						"- Stages _5_ and _10_ got heavily rebalanced. stage 5 got a rebuild.\n" +
						"- Some item drop procedures were changed. Not all of them, so there might be bugs connected to that"));

		changes.addButton(new ChangeButton(Icons.get(Icons.BADGES), "Badges, Journal and many other reworks",
				"- _Badges got a major overhaul_: All of them got completely changed, new tiers of badges have been added, a TON of new badges have been created\n" +
						"- _Rankings_ now work properly, with new tower stats tracked and attacks counted\n" +
						"- Ported a heavily edited version of the new Shattered Pixel Dungeon journal, which tracks enemy encounters and tower usage"));

		changes.addButton(new ChangeButton(Icons.get(Icons.BLOODAMULET), "#1Hard mode changes#1",
				"Made several changes to how hardmode works:\n" +
						"- Champions amount depends on enemy quantity instead of there being one champion each wave\n" +
						"- Timer which gives only a few seconds to do a turn was removed"));
		changes.addButton(new ChangeButton(Icons.get(Icons.ETHERIALAMULET), "#5Challenge mode changes#5",
				"Made several changes to how some challenges work:\n" +
						"- Invisibility for mobs changed: invisible mobs can barely be seen (instead of being completely transparent), but can't be hit directly\n" +
						"- #5IT'S A SPY#5 changed for all spies to be invisible, while still having a random texture. Also you can't place towers near each other.\n" +
						"- for stage 9 challenge changed to #5WALLED#5 - all lightning wands were changed into tier 3 walls"));


		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ARTIFACT_ROSESEED), "Artifact rework",
				"Artifact got a rework:\n" +
						"- The _Armband_ stealing can now be heavily punished\n" +
						"- _Sandals of nature_ grow grass while walking. Higher tiers grow more grass \n" +
						"- _Chalice_ got a heavy rework with direct damage being replaced by heavy Soul bleeding in change for ultra high regeneration\n" +
						"- Readded _cape of thorns_\n" +
						"- Dried rose has been changed into an immortal _Rose bush_ tower\n" +
						"- _Parchment of foresight_ now creates Observers on use\n" +
						"- _Horn of plenty_ acts as an infinite weak heal\n" +
						"- _Hourglass_ now needs to be fixed, and requires your time to be upgraded\n" +
						"- _Cloak of shadows_ and chains remained the same, #0Alchemists toolkit got removed#0\n" +
						"- _Unstable spellbook_ core mechanics remained the same, except for heavy charge, scroll adding and spellcasting mechanics overhaul"));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.BLANDFRUIT), "Blandfruit rework",
				"Blandfruits were reimplemented and got a rework:\n" +
						"- Blandfruit bushes can be found among usual plants, or you can plant them using blandfruit seeds, which can also be bought in a shop\n" +
						"- Each blandfruit has a random potion effect assigned to them, without the need to cook them!"));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.WAR_HAMMER), "Weaponry rework",
				"Most weapons got reworked, some changed to other and some added:\n" +
						"- Weapon tiers coorelate with strength requirement: heavier weapons have higher tier\n" +
						"- The better is the weapon's _tier_, the cooler battle effects it has: _t1_ dirk has minor boost to stealth attacks, _t4_ berserker axe hits all enemies around, _t5_ war hammer creates a blast wave on hit \n" +
						"- Some weapons get _special effects_ only if upgraded\n" +
						"- Damage of all melee weapons and wands _scales with depth_\n" +
						"- All weaponry is seemingly identified, so you #0cannot determine whether the item is cursed or not without equipping it#0\n" +
						"- Upgrading a weapon makes its damage rise by a _percentage_ instead of a constant amount for better scaling\n" +
						"- Balanced weapons for strength: the warrior has the most strength to focus on melee, while such classes like summoner-Necromancer and ranged-Huntress have only 11 STR, the lowest possible. Also Strength is displayed near the character now"));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.EXOTIC_UNSPEAKABLE), "The scrolls and the unspeakable",
				"Inserted a lot of powers from the Beyond:\n" +
						"- #1Nightmare rifts#1 can appear around the dungeon. Those summon more enemies and need to be destroyed, unless you wish to be overwhelmed\n" +
						"- Added some artifacts and mechanics requiring your very Soul to function, most notable being the #1Scroll of Unspeakable Horrors#1 and the reworked #0Chalice of blood#0 \n" +
						"Added and reworked most scrolls:\n" +
						"- _Animation scroll_ can make your summoningand shooting towers alive. A tricky thing to use, but can help in destroying rifts or better tower control\n" +
						"- _Scroll of golems_ can create living statues out of items\n" +
						"- _Holy nova scroll_ banishes all evil magic in your proximity\n" +
						"- _Rat legion scroll_ summons a horde of rats to help you\n" +
						"- _Scroll of discoveries_ reveals all items on the floor\n" +
						"- _Scroll of rage_ now increases strength of your allies\n" +
						"- _Scrolls of Skulls and Demonic skull_ summon devastating explosive projectiles\n" +
						"- _Scroll of metamorphosis_ turns an enemy into an ally. A random ally, like, a TOTALLY random one, but an ally nonetheless\n" +
						"- Scrolls of identity, portal and remove curse are no more. All curses are removed via Holy Nova scrolls\n" +
						"- Scrolls of mirror and prismatic image, upgrade and enchantment, recharging and mystical energy, challenge, transmutation, affection and magic mapping remained the same"));

		changes.addButton(new ChangeButton(new TowerCrossbow1Sprite(), "Tower changes",
				"Made some changes to how towers work:\n" +
						"- All non-wand T4 shooting towers got their #9health#9 buffed\n" +
						"- _Guards_ are now sentient thus can be given more commands, are immune to being chained (they catch chains and disorient enemy guards, still can be knocked back), got more expensive and have better stats\n" +
						"- _Disintegration rod tower_ now does not damage all allies, only those which are not 'alive' towers (tower-guards and moving summons are damaged; crossbows, cannons and etc. are not). Due to this it was nerfed in attack speed.\n" +
						"- _Dartgun_ balancing values readjusted\n" +
						"- _Missile launcher_ is now cheaper yet weaker, with shorter range\n" +
						"- _Elite grave_ greatly buffed with the max number of minions doubled\n" +
						"- _Arcane nuke_ has been buffed with its range, damage and attack speed increased.\n" +
						"- _ROGUE LOG TNT BUFFED, THAT IS THE BUFF WE WERE WAITING FOR_ - explosion is now a 5x5 square, the damage is heavily increased\n" +
						"- readjusted damage reduction for many towers\n" +
						"- fixed some logic considering towers counting as sentient and/or alive beings\n" +
						"- all kinds of _wands_ now have accuracy close to infinite"));
		changes.addButton(new ChangeButton(new BeeSprite(), "Bee changes",
				"Made some changes to bees:\n" +
						"- Increased HP and damage of bees, both of which scale with depth\n" +
						"- all bees are now free and friendly, meaning they become an ally without a honey elixir and no more bound to any spots/honeypots\n" +
						"- bees poison enemies with their sting, the poison duration depends on damage"));
		changes.addButton(new ChangeButton(new ShopkeeperSprite(), "Shop changes",
				"Made some changes to how shopkeepers place their items:\n" +
						"- You can by as much instances of a shop item you want; that is true for both Tower and Item shops, for all items except equipable ones\n" +
						"- Tower shop keeper sells only 4 towers you chose due to that (before he additionally sold a single random tower spawner)\n" +
						"- All shop items are divided into #2elite#2 and _normal_ ones. #2Elite#2 are powerful items (ench scrolls, ankhs, artifacts, healing potions etc.) or items that may be necessary for progression, such as strength potions and upgrade scrolls. Normal items are generic consumables or equipment.\n" +
						"- Each shop places 1 random elite item and 4 random normal items on sale\n" +
						"- After depth 15 item variety is increased, adding totems, exotic potions, and increasing possible sold equipment level\n" +
						"- Most shopkeeper prices are now scaling with depth"));
		changes.addButton(new ChangeButton(new TowerRatCampSprite(), "New Tower",
				"Added a new Tower: the Rat camp tent!\n" +
						"- Rat camp is a versatile summoning tower, which creates mobs once per wave, when foes get close\n" +
						"- You can choose which rats to summon: knife infantry, leaderrats, ratchers, legionairats or magic rodents!\n" +
						"- The tower cannot be sold or upgraded directly, but you can train up to 5 soldiers at once in one tent"));
		changes.addButton(new ChangeButton(new ElementalSprite.Chaos(), "Text #1C#1#0o#0#2l#2#3o#3#4r#4#5i#5#6n#6#7g#7",
				"Added some code that allows #1for#1 #0text#0 #2coloring#2!\n" +
						"- Each class has their representative colors: mage's color is #5light-blue#5, necromancer's - #9neon-green#9 etc.\n" +
						"- Some items have their name and description colors changed too"));
		changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_OFF), "New Mode!",
						"After beating the Ooze you can play _Endless mode_ - a new gamemode with infinite waves, huge-scale maps and random loot spawns (lags with more than 1000 necromancers on the screen are included). These stages are quite different, but they do not need to be balanced at least, because in the end, you will still lose(. However these provide a completely different experience, as there is no limit to your base size a crap ton of items to use."));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.WAND_REGROWTH), "Regrowth wand is back!",
				"- Wand of regrowth now can appear in item loot polls\n" +
				"- Some stats of wand of regrowth were changed"));
		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.HOLY_DART), "Darts overhaul",
				"- Tipped darts now have at least 5 usages and do not drop upon tip effect loss (they break similarly to as usual)\n" +
						"- Random types of darts can be sold at a shop\n" +
						"- Some dart effects were amplified, healing dart's effects got nerfed"));


		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), "Bugs",
				"Bugs removed:\n" +
						"- Ooze bad bugs, IBNLM: bad collision, softlocking, infinite jumping, double acting, crashing the game\n" +
						"- Towers immune to barkskin\n" +
						"- Stone of prediction working as garbage, predicting a single wave. Now it not only works, but adds an entirely new mechanic which allows you to predict several next waves\n" +
						"- Dwarf King insta-killing the amulet, teleporting chaotically\n" +
						"- Tower false targeting\n" +
						"- Hardmode bugs: double bosses false behaviour and crashes\n" +
						"- Spells double cost bug (or any cost - the spells only have a general cooldown)\n" +
						"- Several item drop bugs\n" +
						"- Menu tab wrong displaying and displacement bugs\n" +
						"- Shopkeepers now spawn their items on initialization to prevent spontaneous empty shop duplications\n" +
						"- some lacking mob immunities\n" +
						"- rockets not flying. Not like instantly falling, but counting as flying mobs, that is.\n" +
						"- non-damage wands' stats containing a statement about damage scaling with depth\n" +
						"- DWM boss bugs, including infinite acting\n" +
						"- Shock elementals melting through the portal\n" +
						"- Distant attack false counting\n" +
						"- infinite accuracy numbers displaying as actual (quadrillions) numbers\n" +
						"- Lightning rods dealing almost double damage to the first target affected by the lightning\n" +
						"- Missile launcher rockets dealing 1.5x damage to the direct target\n" +
						"- Duelist ability not giving an enchant to the newly summoned weaponry\n" +
						"- etc. etc. etc... There was so many bugs I accidentally fell asleep twice while fixing them"));

	}


}
