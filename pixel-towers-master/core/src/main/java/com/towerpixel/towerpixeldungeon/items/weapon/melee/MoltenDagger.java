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
import com.towerpixel.towerpixeldungeon.actors.blobs.Blob;
import com.towerpixel.towerpixeldungeon.actors.blobs.Fire;
import com.towerpixel.towerpixeldungeon.actors.blobs.StenchGas;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class MoltenDagger extends MeleeWeapon {

	{
		image = ItemSpriteSheet.WORN_SHORTSWORD;
		hitSound = Assets.Sounds.HIT_SLASH;
		hitSoundPitch = 1.1f;
		rarity = 1;
		tier = 5;
	}

	@Override
	public float abilityChargeUse( Hero hero ) {
		if (hero.buff(Sword.CleaveTracker.class) != null){
			return 0;
		} else {
			return super.abilityChargeUse( hero );
		}
	}

	@Override
	public int min(int lvl) {
		return  Math.round(7*(damageModifier()+1) +
				2*lvl*(damageModifier()+1));
	}

	@Override
	public int max(int lvl) {
		return  Math.round(10*(damageModifier()+1) +
				3*lvl*(damageModifier()+1));
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {
		ArrayList<Integer> respawnPoints = new ArrayList<>();
		for (int iz = 0; iz < PathFinder.NEIGHBOURS25.length; iz++) {
			int p = defender.pos + PathFinder.NEIGHBOURS8[iz];
			if (Actor.findChar(p) == null && Dungeon.level.passable[p]) {
				respawnPoints.add(p);
			}
		}
		if (!respawnPoints.isEmpty())GameScene.add(Blob.seed(Random.element(respawnPoints), 3, Fire.class));
		if (level()>0) GameScene.add(Blob.seed(attacker.pos, 3, Fire.class));
		return super.proc(attacker, defender, damage);
	}

	@Override
	public String targetingPrompt() {
		return Messages.get(this, "prompt");
	}

	@Override
	protected void duelistAbility(Hero hero, Integer target) {
		Sword.cleaveAbility(hero, target, 1.30f, this);
	}

}
