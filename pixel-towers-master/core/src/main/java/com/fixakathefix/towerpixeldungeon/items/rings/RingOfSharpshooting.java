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

package com.fixakathefix.towerpixeldungeon.items.rings;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class RingOfSharpshooting extends Ring {

	{
		image = ItemSpriteSheet.RING_SHARPSHOOTING;
		icon = ItemSpriteSheet.Icons.RING_SHARPSHOOT;
	}

	public String statsInfo() {
		if (isIdentified()){
			return Messages.get(this, "stats", soloBuffedBonus(), Messages.decimalFormat("#.##", 100f * (Math.pow(1.2, soloBonus()) - 1f)));
		} else {
			return Messages.get(this, "typical_stats", 1, Messages.decimalFormat("#.##", 20f));
		}
	}
	
	@Override
	protected RingBuff buff( ) {
		return new Aim();
	}
	
	public static int levelDamageBonus( Char target ){
		return getBuffedBonus(target, RingOfSharpshooting.Aim.class);
	}
	
	public static float durabilityMultiplier( Char target ){
		return (float)(Math.pow(1.2, getBonus(target, Aim.class)));
	}

	public class Aim extends RingBuff {
	}
}
