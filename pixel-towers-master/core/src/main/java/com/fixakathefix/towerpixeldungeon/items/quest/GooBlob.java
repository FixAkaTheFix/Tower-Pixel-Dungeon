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

package com.fixakathefix.towerpixeldungeon.items.quest;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.GooSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class GooBlob extends Item {
	
	{
		image = ItemSpriteSheet.BLOB;
		stackable = true;
	}

	@Override
	protected void onThrow(int cell) {
		if (CeremonialCandle.checkCellForSurroundingLitCandles(cell)){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : PathFinder.NEIGHBOURS25){
				int cel = cell + i;
				if (Char.findChar(cel)==null && Dungeon.level.passable[cel]) candidates.add(cel);
			}
			int power = 40;
			while (power > 0 && !candidates.isEmpty()){
				power -= 40;
				CausticSlime slime = new CausticSlime();
				slime.pos = Random.element(candidates);
				candidates.remove((Integer)slime.pos);
				slime.alignment = Char.Alignment.ALLY;
				slime.state = slime.HUNTING;
				CellEmitter.get(slime.pos).burst(GooSprite.GooParticle.FACTORY, 7);
				GameScene.add(slime);
			}
			Sample.INSTANCE.play(Assets.Sounds.WATER);
			CellEmitter.get(cell).start(GooSprite.GooParticle.FACTORY, 0.02f,50);
			return;
		}
		super.onThrow(cell);
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int value() {
		return quantity * 30;
	}

	@Override
	public int energyVal() {
		return quantity * 3;
	}
}
