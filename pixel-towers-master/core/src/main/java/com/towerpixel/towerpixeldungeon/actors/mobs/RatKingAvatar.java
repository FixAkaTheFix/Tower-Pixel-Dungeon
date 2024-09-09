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
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.GnollSprite;
import com.towerpixel.towerpixeldungeon.sprites.RatKingAvatarSprite;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class RatKingAvatar extends Rat {
	
	{
		spriteClass = RatKingAvatarSprite.class;
		
		HP = HT = 400;
		defenseSkill = 5;

		viewDistance = 4;
		alignment=Alignment.ALLY;
		
		EXP = 0;
		maxLvl = 999;
	}

	@Override
	protected boolean act() {
		beckon(Dungeon.hero.pos);
		return super.act();
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 4, 8 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 15;
	}
	
	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(10, 20);
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				GameScene.show(new WndDialogueWithPic(new RatKingAvatarSprite(), Messages.get(RatKing.class, "projectionname"),
						new String[]{
								Messages.get(RatKing.class, "projectiondeath1"),
								Messages.get(RatKing.class, "projectiondeath2"),
								Messages.get(RatKing.class, "projectiondeath3"),

						},
						new byte[]{
								WndDialogueWithPic.IDLE
						}
				));
			}
		});
	}
}
