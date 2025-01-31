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

import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.Recipe;
import com.fixakathefix.towerpixeldungeon.items.scrolls.Scroll;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfDrowsiness;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfRage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAntiMagic;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTerror;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExoticScroll extends Scroll {
	
	
	public static final HashMap<Class<?extends Scroll>, Class<?extends ExoticScroll>> regToExo = new HashMap<>();
	public static final HashMap<Class<?extends ExoticScroll>, Class<?extends Scroll>> exoToReg = new HashMap<>();
	static{
		regToExo.put(ScrollOfAnimation.class, ScrollOfGolems.class);
		exoToReg.put(ScrollOfGolems.class, ScrollOfAnimation.class);
		
		regToExo.put(ScrollOfUpgrade.class, ScrollOfEnchantment.class);
		exoToReg.put(ScrollOfEnchantment.class, ScrollOfUpgrade.class);
		
		regToExo.put(ScrollOfAntiMagic.class, ScrollOfHolyNova.class);
		exoToReg.put(ScrollOfHolyNova.class, ScrollOfAntiMagic.class);
		
		regToExo.put(ScrollOfDrowsiness.class, ScrollOfSirensSong.class);
		exoToReg.put(ScrollOfSirensSong.class, ScrollOfDrowsiness.class);
		
		regToExo.put(ScrollOfRage.class, ScrollOfChallenge.class);
		exoToReg.put(ScrollOfChallenge.class, ScrollOfRage.class);
		
		regToExo.put(ScrollOfTerror.class, ScrollOfUnspeakableHorrors.class);
		exoToReg.put(ScrollOfUnspeakableHorrors.class, ScrollOfTerror.class);
		
		regToExo.put(ScrollOfRecharging.class, ScrollOfMysticalEnergy.class);
		exoToReg.put(ScrollOfMysticalEnergy.class, ScrollOfRecharging.class);
		
		regToExo.put(ScrollOfMagicMapping.class, ScrollOfDiscoveries.class);
		exoToReg.put(ScrollOfDiscoveries.class, ScrollOfMagicMapping.class);
		
		regToExo.put(ScrollOfTeleportation.class, ScrollOfRatLegion.class);
		exoToReg.put(ScrollOfRatLegion.class, ScrollOfTeleportation.class);
		
		regToExo.put(ScrollOfSkulls.class, ScrollOfDemonicSkull.class);
		exoToReg.put(ScrollOfDemonicSkull.class, ScrollOfSkulls.class);
		
		regToExo.put(ScrollOfMirrorImage.class, ScrollOfPrismaticImage.class);
		exoToReg.put(ScrollOfPrismaticImage.class, ScrollOfMirrorImage.class);
		
		regToExo.put(ScrollOfTransmutation.class, ScrollOfMetamorphosis.class);
		exoToReg.put(ScrollOfMetamorphosis.class, ScrollOfTransmutation.class);
	}

	@Override
	public void reset() {
		super.reset();
		if (handler != null && handler.contains(exoToReg.get(this.getClass()))) {
			image = handler.image(exoToReg.get(this.getClass())) + 32;
			rune = handler.label(exoToReg.get(this.getClass()));
		}
	}
	
	@Override
	//20 gold more than its none-exotic equivalent
	public int value() {
		return (Reflection.newInstance(exoToReg.get(getClass())).value() + 30) * quantity;
	}

	@Override
	//6 more energy than its none-exotic equivalent
	public int energyVal() {
		return (Reflection.newInstance(exoToReg.get(getClass())).energyVal() + 6) * quantity;
	}
	
	public static class ScrollToExotic extends Recipe {
		
		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			if (ingredients.size() == 1 && regToExo.containsKey(ingredients.get(0).getClass())){
				return true;
			}

			return false;
		}
		
		@Override
		public int cost(ArrayList<Item> ingredients) {
			return 6;
		}
		
		@Override
		public Item brew(ArrayList<Item> ingredients) {
			for (Item i : ingredients){
				i.quantity(i.quantity()-1);
			}

			return Reflection.newInstance(regToExo.get(ingredients.get(0).getClass()));
		}
		
		@Override
		public Item sampleOutput(ArrayList<Item> ingredients) {
			return Reflection.newInstance(regToExo.get(ingredients.get(0).getClass()));
		}
	}
}
