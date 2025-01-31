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

package com.fixakathefix.towerpixeldungeon.items.scrolls;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCShooting;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfAnimation extends Scroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_ANIMATION;
		image = ItemSpriteSheet.SCROLL_ANIMATION;
	}

	@Override
	public void doRead() {
		if (!anonymous) curItem.collect(); //we detach it later
		GameScene.selectCell(targeter);
	}

	private CellSelector.Listener targeter = new CellSelector.Listener() {

		@Override
		public void onSelect(Integer cell) {
			if (cell == null){
				return;
			}

			Mob target = null;
			if (cell != null){
				Char ch = Actor.findChar(cell);
				if (ch != null && ch.alignment == Char.Alignment.ALLY && ch instanceof TowerCShooting && ch.buff(Animated.class)==null){
					target = (Mob)ch;
				}
			}

			if (target == null  && !anonymous){
				return;
			} else {

				detach(curUser.belongings.backpack);

				Sample.INSTANCE.play( Assets.Sounds.READ, 1f, 1.4f );


				if (target != null){
					if (!target.isImmune(Animated.class)){
						Buff.affect(target, Animated.class);
						Buff.affect(target, Healing.class).setHeal(target.HT, 0.1f, 5);
						((Tower)target).sellable = false;
					}
					target.sprite.centerEmitter().burst( Speck.factory( Speck.HEART ), 10 );
				} else {
					GLog.w(Messages.get(ScrollOfAnimation.class, "notatower"));
				}
				readAnimation();

			}
		}

		@Override
		public String prompt() {
			return Messages.get(ScrollOfSirensSong.class, "prompt");
		}

	};
}
