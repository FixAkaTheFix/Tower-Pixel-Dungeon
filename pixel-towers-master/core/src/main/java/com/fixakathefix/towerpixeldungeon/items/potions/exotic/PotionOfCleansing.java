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

package com.fixakathefix.towerpixeldungeon.items.potions.exotic;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AllyBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hunger;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Infested;
import com.fixakathefix.towerpixeldungeon.actors.buffs.LostInventory;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.Flare;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfCleansing extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_CLEANSE;
		image = ItemSpriteSheet.EXOTIC_CLEANSING;
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		Buff.detach( hero, Infested.class );
		cleanse( hero );
		new Flare( 6, 32 ).color(0xFF4CD2, true).show( curUser.sprite, 2f );
	}
	
	@Override
	public void shatter(int cell) {
		for (int i : PathFinder.NEIGHBOURS25){
			int cell2 = cell + i;

				if (Dungeon.level.heroFOV[cell2]) {
					Sample.INSTANCE.play(Assets.Sounds.SHATTER);
					splash(cell2);
					identify();
				}

				if (Actor.findChar(cell2) != null){
					cleanse(Actor.findChar(cell2));
				}

		}

	}

	public static void cleanse(Char ch){
		cleanse(ch, Cleanse.DURATION);
	}

	public static void cleanse(Char ch, float duration){
		for (Buff b : ch.buffs()){
			if (b.type == Buff.buffType.NEGATIVE
					&& !(b instanceof AllyBuff)
					&& !(b instanceof LostInventory)){
				b.detach();
			}
			if (b instanceof Hunger){
				((Hunger) b).satisfy(Hunger.STARVING);
			}
		}
		Buff.affect(ch, Cleanse.class, duration);
	}

	public static class Cleanse extends FlavourBuff {

		{
			type = buffType.POSITIVE;
		}

		public static final float DURATION = 20f;

		@Override
		public int icon() {
			return BuffIndicator.NULLIFIED;
		}

		@Override
		public float iconFadePercent() {
			return Math.max(0, (DURATION - visualcooldown()) / DURATION);
		}

	}
}
