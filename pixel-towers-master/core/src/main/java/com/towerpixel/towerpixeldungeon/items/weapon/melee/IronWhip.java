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
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Terror;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class IronWhip extends MeleeWeapon {

	{
		image = ItemSpriteSheet.WHIP;
		hitSound = Assets.Sounds.HIT;
		hitSoundPitch = 1.1f;
		rarity = 2;

		tier = 3;
		RCH = 2;
	}

	@Override
	public int max(int lvl) {
		return  Math.round(10*(damageModifier()+1) +    //12 base, down from 20
				3*lvl*(damageModifier()));     //+3 per level, down from +4
	}

	@Override
	public int min(int lvl) {
		return  Math.round(8*(damageModifier()+1) +    //12 base, down from 20
				3*lvl*(damageModifier()));     //+3 per level, down from +4
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (level()>0 && defender.HP < damage*4) Buff.affect(defender, Terror.class, 3);
		return super.proc(attacker, defender, damage);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {

		ArrayList<Char> targets = new ArrayList<>();

		hero.belongings.abilityWeapon = this;
		for (Char ch : Actor.chars()){
			if (ch.alignment == Char.Alignment.ENEMY
					&& !hero.isCharmedBy(ch)
					&& Dungeon.level.heroFOV[ch.pos]
					&& hero.canAttack(ch)){
				targets.add(ch);
			}
		}
		hero.belongings.abilityWeapon = null;

		if (targets.isEmpty()) {
			GLog.w(Messages.get(this, "ability_no_target"));
			return;
		}

		throwSound();
		hero.sprite.attack(hero.pos, new Callback() {
			@Override
			public void call() {
				beforeAbilityUsed(hero);
				for (Char ch : targets) {
					hero.attack(ch);
					if (!ch.isAlive()){
						onAbilityKill(hero);
					}
				}
				Invisibility.dispel();
				hero.spendAndNext(hero.attackDelay());
				afterAbilityUsed(hero);
			}
		});
	}

}
