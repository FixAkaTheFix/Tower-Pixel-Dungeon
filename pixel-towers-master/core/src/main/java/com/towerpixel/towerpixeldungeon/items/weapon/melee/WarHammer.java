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

package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class WarHammer extends MeleeWeapon {

	{
		image = ItemSpriteSheet.WAR_HAMMER;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1f;
		rarity = 2;
		tier = 4;
		ACC = 1.20f; //20% boost to accuracy
	}


	@Override
	public int min(int lvl) {
		return  Math.round(9*(damageModifier()+1) +
				3*lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return  Math.round(22*(damageModifier()+1) +    //24 base, down from 30
				8*lvl*(damageModifier()+1));   //scaling unchanged
	}


	@Override
	public int proc(Char attacker, Char defender, int damage) {
		(new WandOfBlastWave()).onZap(new Ballistica(attacker.pos, defender.pos, Ballistica.STOP_TARGET));
		return super.proc(attacker, defender, damage);
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Mace.heavyBlowAbility(hero, target, 1.50f, this);
	}

}
