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

package com.fixakathefix.towerpixeldungeon.items.food;

import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hunger;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Recharging;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

import java.util.Calendar;

public class Pasty extends Food {

	//TODO: implement fun stuff for other holidays
	//TODO: probably should externalize this if I want to add any more festive stuff.
	private enum Holiday{
		NONE,
		EASTER, //TBD
		HWEEN,//2nd week of october though first day of november
		XMAS //3rd week of december through first week of january
	}

	private static Holiday holiday;

	static{

		holiday = Holiday.NONE;

		final Calendar calendar = Calendar.getInstance();
		switch(calendar.get(Calendar.MONTH)){
			case Calendar.JANUARY:
				if (calendar.get(Calendar.WEEK_OF_MONTH) == 1)
					holiday = Holiday.XMAS;
				break;
			case Calendar.OCTOBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 2)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.NOVEMBER:
				if (calendar.get(Calendar.DAY_OF_MONTH) == 1)
					holiday = Holiday.HWEEN;
				break;
			case Calendar.DECEMBER:
				if (calendar.get(Calendar.WEEK_OF_MONTH) >= 3)
					holiday = Holiday.XMAS;
				break;
		}
	}

	{
		reset();

		timeToEat = 40f;

		healingPercentage = 0.7f;

		energy = Hunger.STARVING;

		bones = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		switch(holiday){
			case NONE:
				image = ItemSpriteSheet.PASTY;
				break;
			case HWEEN:
				image = ItemSpriteSheet.PUMPKIN_PIE;
				break;
			case XMAS:
				image = ItemSpriteSheet.CANDY_CANE;
				break;
		}
	}
	
	@Override
	protected void satisfy(Hero hero) {
		super.satisfy(hero);
		
		switch(holiday){
			case NONE:
				break; //do nothing extra
			case HWEEN:
				//heals for 10% max hp
				hero.HP = Math.min(hero.HP + hero.HT/10, hero.HT);
				hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				break;
			case XMAS:
				Buff.affect( hero, Recharging.class, 2f ); //half of a charge
				ScrollOfRecharging.charge( hero );
				break;
		}
	}

	@Override
	public String name() {
		switch(holiday){
			case NONE: default:
				return Messages.get(this, "pasty");
			case HWEEN:
				return Messages.get(this, "pie");
			case XMAS:
				return Messages.get(this, "cane");
		}
	}

	@Override
	public String desc() {
		switch(holiday){
			case NONE: default:
				return Messages.get(this, "pasty_desc");
			case HWEEN:
				return Messages.get(this, "pie_desc");
			case XMAS:
				return Messages.get(this, "cane_desc");
		}
	}
	
	@Override
	public int value() {
		return 20 * quantity;
	}
}
