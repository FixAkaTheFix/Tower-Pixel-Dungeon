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

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Wraith;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DarksteelSaber extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SCIMITAR;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.2f;
		rarity = 1;
		tier = 5;
		DLY = 0.8f; //1.25x speed
	}


	@Override
	public int min(int lvl) {
		return  Math.round(6*(damageModifier()+1) +
				2*lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return Math.round(7 * (damageModifier() + 1) +    //16 base, down from 20
				3*lvl * (damageModifier() + 1));   //scaling unchanged
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		ArrayList<Integer> respawnPoints = new ArrayList<>();
		for (int iz = 0; iz < PathFinder.NEIGHBOURS8.length; iz++) {
			int p = defender.pos + PathFinder.NEIGHBOURS8[iz];
			if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
				respawnPoints.add(p);
			}
		}
		if (!respawnPoints.isEmpty()&& Math.random()*level()>0.6){
			Wraith wraith = new Wraith();
			wraith.pos = Random.element(respawnPoints);
			wraith.alignment = attacker.alignment;
			wraith.state = wraith.HUNTING;
			GameScene.add(wraith);
		}

		return super.proc(attacker, defender, damage);
	}


	@Override
	public float abilityChargeUse(Hero hero) {
		return 2*super.abilityChargeUse(hero);
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		beforeAbilityUsed(hero);
		Buff.prolong(hero, SwordDance.class, 5f); //5 turns as using the ability is instant
		hero.sprite.operate(hero.pos);
		hero.next();
		afterAbilityUsed(hero);
	}

	public static class SwordDance extends FlavourBuff {

		{
			announced = true;
			type = buffType.POSITIVE;
		}

		@Override
		public int icon() {
			return BuffIndicator.DUEL_DANCE;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (6 - visualcooldown()) / 6);
		}
	}

}
