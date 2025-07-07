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
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class HolyTome extends MeleeWeapon {

	//See towerpixeldungeon.items.herospells.HeroSpell.cooldown() for method insertion

	{
		image = ItemSpriteSheet.HOLY_TOME;
		hitSound = Assets.Sounds.HIT_CRUSH;
		hitSoundPitch = 1.5f;

		tier = 1;

		rarity = 6;
		unique = true;
		bones = false;

		spellCooldownModifier = 0.8f;
	}



	@Override
	public int min(int lvl) {
		return  Math.round(2*damageModifier() +
				1*lvl*damageModifier());
	}
	@Override
	public int max(int lvl) {
		return  Math.round(3*damageModifier() +
				2*lvl*damageModifier());
	}

	@Override
	public String info() {
		return super.info();
	}

	public String statsInfo(){
		String mes = Messages.get(this, "stats_desc");
		if (Dungeon.hero.heroClass == HeroClass.PRIEST) mes+="\n" + Messages.get(this, "stats_desc_cleric");
		return mes;
	}

	@Override
	public int damageRoll(Char owner) {
		return super.damageRoll(owner);
	}
}
