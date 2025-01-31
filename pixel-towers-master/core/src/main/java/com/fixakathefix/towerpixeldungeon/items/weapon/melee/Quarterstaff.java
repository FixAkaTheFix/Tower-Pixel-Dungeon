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

package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Quarterstaff extends MeleeWeapon {

	{
		image = ItemSpriteSheet.QUARTERSTAFF;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;
		rarity = 2;
		tier = 2;
	}


	@Override
	public int min(int lvl) {
		return  Math.round(7*damageModifier() +    //12 base, down from 15
				2*lvl*damageModifier());   //scaling unchanged
	}

	@Override
	public int max(int lvl) {
		return  Math.round(14*damageModifier() +    //12 base, down from 15
				5*lvl*damageModifier());   //scaling unchanged
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 2 + Dungeon.depth/4;	//2 extra defence
	}

	@Override
	public float abilityChargeUse(Hero hero) {
		return 2*super.abilityChargeUse(hero);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		beforeAbilityUsed(hero);
		Buff.prolong(hero, DefensiveStance.class, 5f); //4 turns as using the ability is instant
		hero.sprite.operate(hero.pos);
		hero.next();
		afterAbilityUsed(hero);
	}

	public static class DefensiveStance extends FlavourBuff {

		{
			announced = true;
			type = buffType.POSITIVE;
		}

		@Override
		public int icon() {
			return BuffIndicator.DUEL_EVASIVE;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (6 - visualcooldown()) / 6);
		}
	}

}
