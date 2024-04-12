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

package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Terror;
import com.towerpixel.towerpixeldungeon.sprites.GoblinSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Goblin extends Mob {
	
	{
		spriteClass = GoblinSprite.class;
		
		HP = HT = 40;
		defenseSkill = 6;

		viewDistance = 10;
		baseSpeed = 1.1f;
		
		EXP = 10;
		maxLvl = 15;
	}

	protected int wasScared=0;
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 8, 13 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 15;
	}

	@Override
	protected boolean act() {
		if (HP<HT) HP+=5;
		if (HP<HT*3/5&&wasScared<3&&buff(Terror.class)==null) {
			wasScared++;
			Buff.append(this, Terror.class, 7);
			Sample.INSTANCE.play(Assets.Sounds.FALLING, 1, (float) Math.random()*0.5f + 1f);
		}
		return super.act();
	}

	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(0,1);
	}

	private static final String WASSCARED = "wasscared";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(WASSCARED,wasScared);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		wasScared = bundle.getInt(WASSCARED);
	}
}
