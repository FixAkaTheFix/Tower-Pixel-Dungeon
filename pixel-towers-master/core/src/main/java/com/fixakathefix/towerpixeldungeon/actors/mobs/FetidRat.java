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

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.StenchGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Ooze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.Ghost;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.FetidRatSprite;
import com.watabou.utils.Random;

public class FetidRat extends Rat {

	{
		spriteClass = FetidRatSprite.class;

		HP = HT = 30;
		defenseSkill = 5;

		EXP = 4;

		maxLvl = 10;

		state = WANDERING;

		properties.add(Property.MINIBOSS);
		properties.add(Property.DEMONIC);

		targetingPreference = TargetingPreference.NOT_WALLS;
	}

	@Override
	public int attackSkill( Char target ) {
		return 12;
	}

	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(1, 2);
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Ooze.class).set( Ooze.DURATION );
		}

		return damage;
	}

	@Override
	public int defenseProc( Char enemy, int damage ) {

		GameScene.add(Blob.seed(pos, 20, StenchGas.class));

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void die( Object cause ) {
		super.die( cause );
		Ghost.Quest.process();
	}
	
	{
		immunities.add( StenchGas.class );
	}
}