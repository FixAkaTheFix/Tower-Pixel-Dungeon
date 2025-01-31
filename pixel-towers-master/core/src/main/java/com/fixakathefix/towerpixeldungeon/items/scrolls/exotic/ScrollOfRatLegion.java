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

package com.fixakathefix.towerpixeldungeon.items.scrolls.exotic;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfRatLegion extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_RATKING;
		image = ItemSpriteSheet.EXOTIC_RATLEGION;
	}
	
	@Override
	public void doRead() {

		ArrayList<Integer> candidates = new ArrayList<>();
		for (int i : PathFinder.NEIGHBOURS25){
			int cell = Dungeon.hero.pos + i;
			if (Char.findChar(cell)==null && Dungeon.level.passable[cell]) candidates.add(cell);
		}
		int power = Dungeon.depth*15 + ((Arena)Dungeon.level).wave*3;
		if (!candidates.isEmpty()) while (power >=0){
			if (power > 100) {
				power-=55;
				ChiefRat chief = new ChiefRat();
				chief.pos = Random.element(candidates);
				chief.alignment = Char.Alignment.ALLY;
				chief.state = chief.HUNTING;
				CellEmitter.get(chief.pos).burst(ElmoParticle.FACTORY, 12);
				GameScene.add(chief);
			} else {
				power -=8;
				Rat remi = new Rat();
				remi.pos = Random.element(candidates);
				remi.alignment = Char.Alignment.ALLY;
				remi.state = remi.HUNTING;
				CellEmitter.get(remi.pos).burst(FlameParticle.FACTORY, 7);
				GameScene.add(remi);
			}
		}
		Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1f,2f);
		Sample.INSTANCE.play(Assets.Sounds.FALLING,1f,1.2f);
		readAnimation();
	}
}
