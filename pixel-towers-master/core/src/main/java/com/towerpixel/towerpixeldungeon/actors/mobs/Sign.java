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

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.SignSprite;
import com.towerpixel.towerpixeldungeon.sprites.TargetSprite;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

import java.util.ArrayList;
import java.util.Arrays;

public class Sign extends Mob {
	
	{
		spriteClass = SignSprite.class;
		
		HP = HT = 1000000;
		defenseSkill = 0;

		viewDistance = 10;
		
		EXP = 2;
		maxLvl = 10;
		alignment = Alignment.NEUTRAL;

		state = PASSIVE;
	}

	public ArrayList<String> signWords = new ArrayList<>();

	public Sign(String...strings){
		super();
		for (String s : strings){
			signWords.add(s);
		}
	}
	public Sign(){
		super();
	}

	@Override
	public boolean interact(Char c) {
		if (c == Dungeon.hero) {
			Game.runOnRenderThread(new Callback() {
				@Override
				public void call() {
					GameScene.show(new WndDialogueWithPic(sprite(), Messages.get(Sign.class, "name"),
							signWords.toArray(new String[0])
					));
				}
			});
		}
		return true;
	}


	public static final String SIGNWORDS = "signwords";
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(SIGNWORDS, signWords.toArray(new String[0]));
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		signWords = new ArrayList<String>(Arrays.asList(bundle.getStringArray(SIGNWORDS)));
	}

	@Override
	protected boolean getCloser(int target) {
		return true;
	}

	@Override
	protected boolean getFurther(int target) {
		return true;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return false;
	}
}
