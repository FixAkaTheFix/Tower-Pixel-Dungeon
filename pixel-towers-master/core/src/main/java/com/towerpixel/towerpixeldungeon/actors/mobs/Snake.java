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

package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.journal.Guidebook;
import com.towerpixel.towerpixeldungeon.journal.Document;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.SnakeSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class Snake extends Mob {
	
	{
		spriteClass = SnakeSprite.class;
		
		HP = HT = 4;
		defenseSkill = 50;
		
		EXP = 2;

		maxLvl = 4;
		
		loot = Generator.Category.SEED;
		lootChance = 0.25f;

		targetingPreference = TargetingPreference.ONLY_AMULET;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 4, 7 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}

	private static int dodges = 0;

	@Override
	public boolean canGetSurpriseAttacked() {
		return false;
	}

	@Override
	public String defenseVerb() {
		dodges++;
		if (dodges >= 2 && !Document.ADVENTURERS_GUIDE.isPageRead(Document.GUIDE_SURPRISE_ATKS)){
			GLog.p(Messages.get(Guidebook.class, "hint"));
			GameScene.flashForDocument(Document.ADVENTURERS_GUIDE, Document.GUIDE_SURPRISE_ATKS);
			dodges = 0;
		}
		return super.defenseVerb();
	}
}
