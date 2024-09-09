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

import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.GnollSprite;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Gnoll extends Mob {
	
	{
		spriteClass = GnollSprite.class;
		
		HP = HT = 18;
		defenseSkill = 5;

		viewDistance = 10;
		
		EXP = 2;
		maxLvl = 10;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 4, 8 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}
	
	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(0, 2);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if (Dungeon.depth==3 && Dungeon.level.mode== WndModes.Modes.CHALLENGE){
			int cell;
			Sample.INSTANCE.play(Assets.Sounds.BLAST);
			for (int i : PathFinder.NEIGHBOURS8){
				cell = pos + i;
				Char ch = Char.findChar(cell);
				if (ch!=null){
					if (ch.alignment == this.alignment){
						//friends receive 0 damage
					} else {
						ch.damage (Random.Int(15, 25),this);
						Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
						throwChar(ch, trajectory, 2, false, true, getClass());
						if (ch instanceof Arena.AmuletTower) ch.die(this);
					};//damages foes nearby and throws them away
				}
				if (level.heroFOV[cell]) {
					CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
				}
			}
		}
	}
}
