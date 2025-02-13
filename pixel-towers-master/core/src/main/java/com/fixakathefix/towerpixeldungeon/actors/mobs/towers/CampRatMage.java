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

package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.CampRatMageSprite;

public class CampRatMage extends CampRat {

	{
		spriteClass = CampRatMageSprite.class;
		
		HP = HT = 8;
		defenseSkill = 1;//dpt/c = 0.01025

		damageMin = 10;
		damageMax = 31;
		defMin = 0;
		defMax = 1;


		cost = 800;
	}

	@Override
	public float attackDelay() {
		return super.attackDelay()*2.5f;
	}

	@Override
	public int attackSkill( Char target ) {
		return 100000;
	}
	@Override
	protected boolean canAttack( Char enemy ) {
		return super.canAttack(enemy)
				|| distance(enemy)<=5;
	}
	

}
