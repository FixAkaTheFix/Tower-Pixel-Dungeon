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
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.features.Door;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.AttackIndicator;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class Rapier extends MeleeWeapon {

	{
		image = ItemSpriteSheet.RAPIER;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.3f;
		rarity = 1;
		tier = 2;
		critMult = 1.5f;

		bones = false;
	}


	@Override
	public int min(int lvl) {
		return  Math.round(6*damageModifier() +
				2*lvl*damageModifier());
	}

	@Override
	public int max(int lvl) {
		return  Math.round(12*damageModifier() +    //8 base, down from 10
				4*lvl*damageModifier());   //scaling unchanged
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		if (level()>0) Buff.affect(defender, Bleeding.class).set(Dungeon.depth/2 * level() + 5);
		return super.proc(attacker, defender, damage);
	}

	@Override
	public int defenseFactor( Char owner ) {
		return 1;	//1 extra defence
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		if (target == null){
			return;
		}

		Char enemy = Actor.findChar(target);
		//duelist can lunge out of her FOV, but this wastes the ability instead of cancelling if there is no target
		if (Dungeon.level.heroFOV[target]) {
			if (enemy == null || enemy == hero || hero.isCharmedBy(enemy)) {
				GLog.w(Messages.get(this, "ability_no_target"));
				return;
			}
		}

		if (hero.rooted || Dungeon.level.distance(hero.pos, target) < 2
				|| Dungeon.level.distance(hero.pos, target)-1 > reachFactor(hero)){
			GLog.w(Messages.get(this, "ability_bad_position"));
			return;
		}

		int lungeCell = -1;
		for (int i : PathFinder.NEIGHBOURS8){
			if (Dungeon.level.distance(hero.pos+i, target) <= reachFactor(hero)
					&& Actor.findChar(hero.pos+i) == null
					&& (Dungeon.level.passable[hero.pos+i] || (Dungeon.level.avoid[hero.pos+i] && hero.flying))){
				if (lungeCell == -1 || Dungeon.level.trueDistance(hero.pos + i, target) < Dungeon.level.trueDistance(lungeCell, target)){
					lungeCell = hero.pos + i;
				}
			}
		}

		if (lungeCell == -1){
			GLog.w(Messages.get(this, "ability_bad_position"));
			return;
		}

		final int dest = lungeCell;
		hero.busy();
		Sample.INSTANCE.play(Assets.Sounds.MISS);
		hero.sprite.jump(hero.pos, dest, 0, 0.1f, new Callback() {
			@Override
			public void call() {
				if (Dungeon.level.map[hero.pos] == Terrain.OPEN_DOOR) {
					Door.leave( hero.pos );
				}
				hero.pos = dest;
				Dungeon.level.occupyCell(hero);

				if (enemy != null && hero.canAttack(enemy)) {
					hero.sprite.attack(enemy.pos, new Callback() {
						@Override
						public void call() {
							//+3+lvl damage, equivalent to +67% damage, but more consistent
							beforeAbilityUsed(hero);
							AttackIndicator.target(enemy);
							if (hero.attack(enemy, 1f, augment.damageFactor(3 + level()), Char.INFINITE_ACCURACY)) {
								Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
								if (!enemy.isAlive()) {
									onAbilityKill(hero);
								}
							}
							Invisibility.dispel();
							hero.spendAndNext(hero.attackDelay());
							afterAbilityUsed(hero);
						}
					});
				} else {
					beforeAbilityUsed(hero);
					GLog.w(Messages.get(Rapier.class, "ability_no_target"));
					hero.spendAndNext(hero.speed());
					afterAbilityUsed(hero);
				}
			}
		});
	}
}
