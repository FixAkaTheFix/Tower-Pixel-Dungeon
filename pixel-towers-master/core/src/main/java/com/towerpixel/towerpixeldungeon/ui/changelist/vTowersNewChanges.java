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

package com.towerpixel.towerpixeldungeon.ui.changelist;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.hero.HeroClass;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.ChangesScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.GorematiaSpiritSprite;
import com.towerpixel.towerpixeldungeon.sprites.HeroSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.sprites.RatKingAvatarSprite;
import com.towerpixel.towerpixeldungeon.sprites.YogSprite;
import com.towerpixel.towerpixeldungeon.ui.Icons;
import com.towerpixel.towerpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class vTowersNewChanges {

	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		add_Coming_Soon(changeInfos);
		add_v010_Changes(changeInfos);
		add_v020_Changes(changeInfos);
	}

	public static void add_Coming_Soon( ArrayList<ChangeInfo> changeInfos ) {

		ChangeInfo changes = new ChangeInfo("Coming Soon", true, "");
		changes.hardlight(0xCCCCCC);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.CROSS), "Overview",
				"Next updates may... not appear soon. Custom levels, interesting designs take a lot of time. And adding one item per 2 weeks is not possible for this one, as it is not that replayable.\n\n" +
				"I don't have a specific date for this, but those are the concepts I had in mind for the 1.0.0 version"));

		changes.addButton( new ChangeButton(Icons.get(Icons.TOWER), "new Towers",
				"Several towers that have concepts yet are not in the game:\n\n",
				"- Beehive: a wall which created bees on being hit\n\n",
				"- BeeBot: a friendly unit which would slowly heal towers and collect loot\n\n",
				"- Ice Rod: a slowing wand with moderate damage\n\n",
				"- Prismatic Rod: a support wand which blinds targets. Was considered too specific and removed from the game.\n\n"
				));

		changes.addButton( new ChangeButton(Icons.get(Icons.DUELIST), "new Heroes",
				"There would be another two heroes, the necromancer, which would create separate bone magic towers, and the engineer, who would be able to fix them."));

		changes.addButton( new ChangeButton(Icons.get(Icons.AMULET), "new Modes",
				"I had two more modes in mind: the Endless mode, and a vs Rat King mode, in which you would try to survive more than a bot does."));

		changes.addButton( new ChangeButton(new YogSprite(), "Yog-Dzewa sealing ritual",
				"Something I wouldn't spoil at any cost, yet that one would be great."));

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

		changes.addButton(new ChangeButton(new GorematiaSpiritSprite(), "#???#",
				Random.oneOf("#HEY, WHERE IS THE RAT KING?????#",
						"#I WON'T BE DELETED THAT EASILY!!!#",
						"#YOU WON'T DARE TO...#")));


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

}
