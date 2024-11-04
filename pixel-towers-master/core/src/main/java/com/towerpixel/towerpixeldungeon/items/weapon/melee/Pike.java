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
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.ui.AttackIndicator;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Pike extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SPEAR;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 0.9f;
		rarity = 1;

		tier = 3;
		DLY = 1.5f; //0.67x speed
		RCH = 4;    //extra reach
	}

	@Override
	public int min(int lvl) {
		return  Math.round(5*(damageModifier()+1) +
				2*lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return  Math.round(20f*(damageModifier()+1)) +    //20 base, up from 15
				lvl*Math.round(6f*(damageModifier()+1)); //+4 per level, up from +3
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Pike.spikeAbility(hero, target, 1.40f, this);
	}

	public static void spikeAbility(Hero hero, Integer target, float dmgMulti, MeleeWeapon wep){
		if (target == null) {
			return;
		}

		Char enemy = Actor.findChar(target);
		if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
			GLog.w(Messages.get(wep, "ability_no_target"));
			return;
		}

		hero.belongings.abilityWeapon = wep;
		if (!hero.canAttack(enemy) || Dungeon.level.adjacent(hero.pos, enemy.pos)){
			GLog.w(Messages.get(wep, "ability_bad_position"));
			hero.belongings.abilityWeapon = null;
			return;
		}
		hero.belongings.abilityWeapon = null;

		hero.sprite.attack(enemy.pos, new Callback() {
			@Override
			public void call() {
				wep.beforeAbilityUsed(hero);
				AttackIndicator.target(enemy);
				if (hero.attack(enemy, dmgMulti, 0, Char.INFINITE_ACCURACY)) {
					if (enemy.isAlive()){
						//trace a ballistica to our target (which will also extend past them
						Ballistica trajectory = new Ballistica(hero.pos, enemy.pos, Ballistica.STOP_TARGET);
						//trim it to just be the part that goes past them
						trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size() - 1), Ballistica.PROJECTILE);
						//knock them back along that ballistica
						WandOfBlastWave.throwChar(enemy, trajectory, 1, true, false, hero.getClass());
					} else {
						wep.onAbilityKill(hero);
					}
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
				}
				Invisibility.dispel();
				hero.spendAndNext(hero.attackDelay());
				wep.afterAbilityUsed(hero);
			}
		});
	}

}
