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

package com.fixakathefix.towerpixeldungeon.items.spells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.EquipableItem;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.armor.Armor;
import com.fixakathefix.towerpixeldungeon.items.quest.MetalShard;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfMight;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAntiMagic;
import com.fixakathefix.towerpixeldungeon.items.wands.Wand;
import com.fixakathefix.towerpixeldungeon.items.weapon.SpiritBow;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MagesStaff;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class CurseInfusion extends InventorySpell {
	
	{
		image = ItemSpriteSheet.CURSE_INFUSE;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return ((item instanceof EquipableItem && !(item instanceof MissileWeapon)) || item instanceof Wand);
	}

	@Override
	protected void onItemSelected(Item item) {
		
		CellEmitter.get(curUser.pos).burst(ShadowParticle.UP, 5);
		Sample.INSTANCE.play(Assets.Sounds.CURSED);
		
		item.cursed = true;
		if (item instanceof MeleeWeapon || item instanceof SpiritBow) {
			Weapon w = (Weapon) item;
			if (w.enchantment != null) {
				w.enchant(Weapon.Enchantment.randomCurse(w.enchantment.getClass()));
			} else {
				w.enchant(Weapon.Enchantment.randomCurse());
			}
			w.curseInfusionBonus = true;
			if (w instanceof MagesStaff){
				((MagesStaff) w).updateWand(true);
			}
		} else if (item instanceof Armor){
			Armor a = (Armor) item;
			if (a.glyph != null){
				a.inscribe(Armor.Glyph.randomCurse(a.glyph.getClass()));
			} else {
				a.inscribe(Armor.Glyph.randomCurse());
			}
			a.curseInfusionBonus = true;
		} else if (item instanceof Wand){
			((Wand) item).curseInfusionBonus = true;
			((Wand) item).updateLevel();
		} else if (item instanceof RingOfMight){
			curUser.updateHT(false);
		}
		Badges.validateItemLevelAquired(item);
		updateQuickslot();
	}
	
	@Override
	public int value() {
		//prices of ingredients, divided by output quantity, rounds down
		return (int)((30 + 50) * (quantity/3f));
	}
	
	public static class Recipe extends com.fixakathefix.towerpixeldungeon.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ScrollOfAntiMagic.class, MetalShard.class};
			inQuantity = new int[]{1, 1};
			
			cost = 6;
			
			output = CurseInfusion.class;
			outQuantity = 4;
		}
		
	}
}
