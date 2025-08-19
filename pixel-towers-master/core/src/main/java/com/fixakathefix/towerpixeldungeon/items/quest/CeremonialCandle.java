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

import com.badlogic.gdx.utils.IntMap;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.levels.Arena5;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;

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
	//needed for making a candle not exist before pickup
	transient boolean valid = true;

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
		valid = true;
		checkAndUpdateCandles();
	}

	@Override
	protected void onThrow(int cell) {
		super.onThrow(cell);
		aflame = false;
		valid = true;
		checkAndUpdateCandles();
	}

	@Override
	public boolean doPickUp(Hero hero, int pos) {
		if (super.doPickUp(hero, pos)){
			this.valid = false;
			Badges.validateCandle();
			aflame = false;
			checkAndUpdateCandles();
			this.valid = true;
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
	public String info() {
		if (aflame) return super.info() + "\n\n" + Messages.get(CeremonialCandle.class, "lit");
		return super.info();
	}

	public static boolean checkCellForSurroundingCandles(int cell){
		for (int i : PathFinder.NEIGHBOURS4){
			int cellcheck = cell + i;
			Heap heap = Dungeon.level.heaps.get(cellcheck);
			if (heap == null) return false;
			else {
				Item item = heap.items.getFirst();
				if (!(item instanceof CeremonialCandle) || !((CeremonialCandle)item).valid ){
					return false;
				}
			}
		}
		return true;
	}
	public static boolean checkCellForSurroundingLitCandles(int cell){
		for (int i : PathFinder.NEIGHBOURS4){
			int cellcheck = cell + i;
			Heap heap = Dungeon.level.heaps.get(cellcheck);
			if (heap == null) return false;
			else {
				Item item = heap.items.getFirst();
				if (!(item instanceof CeremonialCandle) || !((CeremonialCandle)item).aflame || !((CeremonialCandle)item).valid){
					return false;
				}
			}
		}
		return true;
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

	private static void checkAndUpdateCandles(){
		ArrayList<Heap> heapstoupdate = new ArrayList<>();
		ArrayList<CeremonialCandle> candlestolit = new ArrayList<>();
		for (IntMap.Entry<Heap> heap : Dungeon.level.heaps) {
			if (heap.value.items.getFirst() instanceof CeremonialCandle) {
				((CeremonialCandle)heap.value.items.getFirst()).aflame=false;
				heapstoupdate.add(heap.value);
				if (checkCellForSurroundingCandles(heap.key+1)){
					for (int i : PathFinder.NEIGHBOURS4){
						candlestolit.add((CeremonialCandle)Dungeon.level.heaps.get(heap.key + 1 + i).items.getFirst());
					}
					if (!SPDSettings.ritualWasMade()){
						WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
								new String[]{
										Messages.get(RatKing.class, "l5ritual1"),
										Messages.get(RatKing.class, "l5ritual2"),
										Messages.get(RatKing.class, "l5ritual3"),
										Messages.get(RatKing.class, "l5ritual4"),
										Messages.get(RatKing.class, "l5ritual5"),
								},
								new byte[]{
										WndDialogueWithPic.RUN,
										WndDialogueWithPic.IDLE,
										WndDialogueWithPic.RUN,
										WndDialogueWithPic.IDLE,
										WndDialogueWithPic.IDLE

								});
						SPDSettings.ritualWasMade(true);
					}
				}
			}
		}
		for (CeremonialCandle candle : candlestolit){
			candle.aflame = true;
		}
		for (Heap heap : heapstoupdate){
			heap.sprite.view(heap).place(heap.pos);
		}
	}

	@Override
	public int value() {
		return 30;
	}
}
