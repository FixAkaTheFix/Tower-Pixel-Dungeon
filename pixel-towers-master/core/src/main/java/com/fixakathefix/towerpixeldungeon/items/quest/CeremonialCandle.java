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
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.levels.RegularLevel;
import com.fixakathefix.towerpixeldungeon.levels.rooms.standard.RitualSiteRoom;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;


public class CeremonialCandle extends Item {

	//generated with the wandmaker quest
	public static int ritualPos;

	{
		image = ItemSpriteSheet.CANDLE;

		defaultAction = AC_THROW;

		unique = true;
		stackable = true;
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
	public void doDrop(Hero hero) {
		super.doDrop(hero);
		aflame = false;
		checkCandles();
	}

	@Override
	protected void onThrow(int cell) {
		super.onThrow(cell);
		aflame = false;
		checkCandles();
	}

	@Override
	public boolean doPickUp(Hero hero, int pos) {
		Badges.validateCandle();
		if (super.doPickUp(hero, pos)){
			aflame = false;
			return true;
		}
		return false;
	}

	public boolean aflame = false;

	public static String AFLAME = "aflame";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(AFLAME, aflame);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		aflame = bundle.getBoolean(AFLAME);
	}

	@Override
	public Emitter emitter() {
		if (aflame) {
			Emitter emitter = new Emitter();
			emitter.pos(6, 0);
			emitter.fillTarget = false;
			emitter.pour(ElmoParticle.FACTORY, 0.25f);
			return emitter;
		}
		return super.emitter();
	}

	private static void checkCandles(){
		if (!(Dungeon.level instanceof RegularLevel)){
			return;
		}

		if (!(((RegularLevel) Dungeon.level).room(ritualPos) instanceof RitualSiteRoom)){
			return;
		}

		Heap[] candleHeaps = new Heap[4];

		candleHeaps[0] = Dungeon.level.heaps.get(ritualPos - Dungeon.level.width());
		candleHeaps[1] = Dungeon.level.heaps.get(ritualPos + 1);
		candleHeaps[2] = Dungeon.level.heaps.get(ritualPos + Dungeon.level.width());
		candleHeaps[3] = Dungeon.level.heaps.get(ritualPos - 1);

		boolean allCandles = true;
		for (Heap h : candleHeaps){
			if (h != null && h.type == Heap.Type.HEAP){
				boolean foundCandle = false;
				for (Item i : h.items){
					if (i instanceof CeremonialCandle){
						if (!((CeremonialCandle) i).aflame) {
							((CeremonialCandle) i).aflame = true;
							h.sprite.view(h).place(h.pos);
						}
						foundCandle = true;
					}
				}
				if (!foundCandle){
					allCandles = false;
				}
			} else {
				allCandles = false;
			}
		}

		if (allCandles){

			for (Heap h : candleHeaps) {
				for (Item i : h.items.toArray(new Item[0])){
					if (i instanceof CeremonialCandle){
						h.remove(i);
					}
				}
			}
				
			Elemental.NewbornFireElemental elemental = new Elemental.NewbornFireElemental();
			Char ch = Actor.findChar( ritualPos );
			if (ch != null) {
				ArrayList<Integer> candidates = new ArrayList<>();
				for (int n : PathFinder.NEIGHBOURS8) {
					int cell = ritualPos + n;
					if ((Dungeon.level.passable[cell] || Dungeon.level.avoid[cell]) && Actor.findChar( cell ) == null) {
						candidates.add( cell );
					}
				}
				if (candidates.size() > 0) {
					elemental.pos = Random.element( candidates );
				} else {
					elemental.pos = ritualPos;
				}
			} else {
				elemental.pos = ritualPos;
			}
			elemental.state = elemental.HUNTING;
			GameScene.add(elemental, 1);

			for (int i : PathFinder.NEIGHBOURS9){
				CellEmitter.get(ritualPos+i).burst(ElmoParticle.FACTORY, 10);
			}
			Sample.INSTANCE.play(Assets.Sounds.BURNING);
		}

	}

	@Override
	public int value() {
		return 30;
	}
}
