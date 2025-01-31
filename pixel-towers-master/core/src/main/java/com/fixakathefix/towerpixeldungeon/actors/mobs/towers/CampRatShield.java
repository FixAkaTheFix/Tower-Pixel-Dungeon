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
import com.fixakathefix.towerpixeldungeon.actors.DamageSource;
import com.fixakathefix.towerpixeldungeon.sprites.CampRatShieldSprite;

public class CampRatShield extends CampRat {

	{
		spriteClass = CampRatShieldSprite.class;

		HP = HT = 50;
		defenseSkill = 1;

		damageMin = 9;
		damageMax = 13;
		defMin = 1;
		defMax = 2;

		cost = 650;
	}
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}

	@Override
	public float attackDelay() {
		return super.attackDelay() * 1.33f;
	}

	@Override
	public void damage(int dmg, Object src) {
		if (!(DamageSource.MAGICAL.contains(src.getClass())))dmg*=0.85f; //15% damage reduction
		super.damage(dmg, src);
	}
	@Override
	protected boolean canAttack(Char enemy) {
		return super.canAttack(enemy) || (distance(enemy)<=2);
	}
}
