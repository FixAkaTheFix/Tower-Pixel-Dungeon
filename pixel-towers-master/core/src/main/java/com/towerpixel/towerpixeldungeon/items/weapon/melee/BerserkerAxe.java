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
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class BerserkerAxe extends MeleeWeapon {

	{
		image = ItemSpriteSheet.BATTLE_AXE;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 0.9f;
		rarity = 1;

		tier = 4;
	}


	@Override
	public int min(int lvl) {
		return  Math.round(7*(damageModifier()+1) +
				2*lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return  Math.round(10*(damageModifier()+1) +    //20 base, down from 25
				3*lvl*(damageModifier()+1));   //scaling unchanged
	}



	@Override
	public int proc(Char attacker, Char defender, int damage) {
		for (int i : PathFinder.NEIGHBOURS8){
			int cell = attacker.pos + i;
			Char ch = Char.findChar(cell);
			if (ch!=null) ch.damage(damage - ch.drRoll(), this);
		}
		return super.proc(attacker, defender, damage);
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Mace.heavyBlowAbility(hero, target, 1.55f, this);
	}

}
