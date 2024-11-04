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
import com.towerpixel.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.ui.AttackIndicator;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.HashSet;

public class Sai extends MeleeWeapon {

	{
		image = ItemSpriteSheet.SAI;
		hitSound = Assets.Sounds.HIT_STAB;
		hitSoundPitch = 1.3f;
		rarity = 2;

		tier = 1;
		DLY = 0.45f; //2x speed
	}


	@Override
	public int min(int lvl) {
		return  Math.round(4*(damageModifier()+1) +
				lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return  Math.round(5.5f*(damageModifier()+1)) +
				2*lvl*Math.round(2*(damageModifier()+1));
	}


	@Override
	public float delayFactor(Char owner) {
		return super.delayFactor(owner)/Math.max(1, level()+1);
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Sai.comboStrikeAbility(hero, target, 0.35f, this);
	}

	public static void comboStrikeAbility(Hero hero, Integer target, float boostPerHit, MeleeWeapon wep){
		if (target == null) {
			return;
		}

		Char enemy = Actor.findChar(target);
		if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
			GLog.w(Messages.get(wep, "ability_no_target"));
			return;
		}

		hero.belongings.abilityWeapon = wep;
		if (!hero.canAttack(enemy)){
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

				HashSet<ComboStrikeTracker> buffs = hero.buffs(ComboStrikeTracker.class);
				int recentHits = buffs.size();
				for (Buff b : buffs){
					b.detach();
				}

				boolean hit = hero.attack(enemy, 1f + boostPerHit*recentHits, 0, Char.INFINITE_ACCURACY);
				if (hit && !enemy.isAlive()){
					wep.onAbilityKill(hero);
				}

				Invisibility.dispel();
				hero.spendAndNext(hero.attackDelay());
				if (recentHits >= 2 && hit){
					Sample.INSTANCE.play(Assets.Sounds.HIT_STRONG);
				}

				wep.afterAbilityUsed(hero);
			}
		});
	}

	public static class ComboStrikeTracker extends FlavourBuff{

		public static float DURATION = 5f;

	}

}
