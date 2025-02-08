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

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ArmoredStatue;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Statue;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BloodParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.armor.Armor;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfGolems extends ExoticInventoryScroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_GOLEM;
		image = ItemSpriteSheet.EXOTIC_GOLEMS;

		bones = true;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return (item instanceof MeleeWeapon  ||
				item instanceof Armor) && !item.isEquipped(hero);
	}

	@Override
	protected void onItemSelected(Item item) {

		boolean cursedd = false;

		if (item instanceof MeleeWeapon){
			Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1f,0.8f);
			ArrayList<Integer> respawnPoints = new ArrayList<>();
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
					respawnPoints.add( p );
				}
			}
			if (!respawnPoints.isEmpty()){
				if (item.cursed) {
					item.cursed = false;
					cursedd = true;
				}
				detach(hero.belongings.backpack);
				item.detach(hero.belongings.backpack);
				Statue statuewithmelee = new Statue((MeleeWeapon)item);
				if (((MeleeWeapon)item).enchantment!=null) statuewithmelee = new Statue((MeleeWeapon)item, ((MeleeWeapon)item).enchantment);
				statuewithmelee.HP = statuewithmelee.HT = hero.HP;
				if (cursedd) statuewithmelee.alignment = Char.Alignment.ENEMY; else statuewithmelee.alignment = Char.Alignment.ALLY;
				statuewithmelee.pos = Random.element(respawnPoints);
				if (!cursedd)
					CellEmitter.get(statuewithmelee.pos).burst(ElmoParticle.FACTORY, 10);
				else CellEmitter.get(statuewithmelee.pos).burst(BloodParticle.FACTORY, 10);
				statuewithmelee.state = statuewithmelee.HUNTING;
				GameScene.add(statuewithmelee);
			}

		} else if (item instanceof Armor){
			Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1f,0.8f);
			ArrayList<Integer> respawnPoints = new ArrayList<>();
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				int p = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
					respawnPoints.add( p );
				}
			}
			if (!respawnPoints.isEmpty()){
				if (item.cursed) {
					item.cursed = false;
					cursedd = true;
				}
				detach(hero.belongings.backpack);
				item.detach(hero.belongings.backpack);
				Statue statuewitharmor = new ArmoredStatue((Armor)item);
				if (((Armor)item).glyph!=null) statuewitharmor = new ArmoredStatue((Armor)item, ((Armor)item).glyph);
				statuewitharmor.HP = statuewitharmor.HT = hero.HP;
				if (cursedd) statuewitharmor.alignment = Char.Alignment.ENEMY; else statuewitharmor.alignment = Char.Alignment.ALLY;
				statuewitharmor.pos = Random.element(respawnPoints);
				if (!cursedd)
				CellEmitter.get(statuewitharmor.pos).burst(ElmoParticle.FACTORY, 10);
				else CellEmitter.get(statuewitharmor.pos).burst(BloodParticle.FACTORY, 10);
				statuewitharmor.state = statuewitharmor.HUNTING;
				GameScene.add(statuewitharmor);
			}

		}
	}


	@Override
	public int value() {
		return 50 * quantity;
	}

	@Override
	public int energyVal() {
		return 8 * quantity ;
	}
}
