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

package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.StenchGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CampRatFlagSprite;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class CampRatLeader extends CampRat {

	{
		spriteClass = CampRatFlagSprite.class;
		
		HP = HT = 20;
		defenseSkill = 5;

		damageMin = 10;
		damageMax = 12;
		defMin = 2;
		defMax = 3;

		cost = 400;

		immunities.add(Paralysis.class);
		immunities.add(StenchGas.class);

		viewDistance = 15;
	}

	@Override
	protected boolean act() {
		GameScene.updateFog(pos, 5);
		return super.act();
	}

	@Override
	public int attackSkill( Char target ) {
		return 20;
	}

	@Override
	public void damage(int dmg, Object src) { //damage is divided between all camprats near
		ArrayList<CampRat> ratsaround = new ArrayList<>();
		if (HP < dmg) dmg = HP + (dmg - HP)/3;
		for (int i : PathFinder.NEIGHBOURS25){
			if (Char.findChar(pos+i) instanceof CampRat && Char.findChar(pos+i).alignment == this.alignment){
				ratsaround.add((CampRat) Char.findChar(pos+i));
			}
		}
		if (!ratsaround.isEmpty()){
			dmg /= ratsaround.size();
			for (CampRat raticate : ratsaround ) if (!(raticate instanceof CampRatLeader)) raticate.damage(dmg, src);
		}
		super.damage(dmg,src);
	}
}
