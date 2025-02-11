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

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.SoulBleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.UnspeakableHorrorSprite;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfUnspeakableHorrors extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_DREAD;
		image = ItemSpriteSheet.EXOTIC_UNSPEAKABLE;
	}
	
	@Override
	public void doRead() {
		GameScene.flash(0x000000);
		Sample.INSTANCE.play( Assets.Sounds.READ, 1f,0.5f);
		Badges.validateBloodCultist(1);

		Buff.affect(Dungeon.hero, Silentio.class, 20f);
		Buff.affect(Dungeon.hero, Blindness.class, 20f);
		Buff.affect(Dungeon.hero, SoulBleeding.class).prolong(Dungeon.hero.HP);

		for (Mob mob : Dungeon.level.mobs){
			Buff.affect(mob, Blindness.class, 20);
			Buff.affect(mob, Vertigo.class, 19);
		}
		ArrayList<Integer> candidates = new ArrayList<>();
		for (int i : PathFinder.NEIGHBOURS25) if (Dungeon.level.passable[Dungeon.hero.pos + i]){
			candidates.add(Dungeon.hero.pos + i);
		}
		for (int i = 0; i< 5; i++) if (!candidates.isEmpty()){
			UnspeakableHorror fluffy = new UnspeakableHorror();
			fluffy.pos = Random.element(candidates);
			GameScene.add(fluffy);
		}
		readAnimation();
	}

	public static class UnspeakableHorror extends Mob {
		{
			alignment = Alignment.ALLY;
			HP = HT = 666;
			spriteClass = UnspeakableHorrorSprite.class;
			viewDistance = 666;
			state = HUNTING;
		}

		@Override
		protected boolean act() {
			if (Dungeon.hero.buff(Silentio.class)==null){
				die(Dungeon.hero);
				return true;
			}
			return super.act();
		}

		@Override
		public int attackSkill(Char target) {
			return 1000000;
		}

		@Override
		public int damageRoll() {
			return super.damageRoll() + Random.Int(Dungeon.depth+5, Dungeon.depth + 10);
		}

		@Override
		public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
			String i = Random.oneOf(Assets.Sounds.CHARMS, Assets.Sounds.CURSED, Assets.Sounds.CHALLENGE, Assets.Sounds.DEATH, Assets.Sounds.MIMIC, Assets.Sounds.FALLING);
			Sample.INSTANCE.play(i, (float)Math.random(), 0.2f + (float)(Math.random()*0.1));
			return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
		}
	}
	public static class Silentio extends FlavourBuff {
		{
			type = buffType.NEUTRAL;
		}

		@Override
		protected void onAdd() {
			super.onAdd();
			Music.INSTANCE.volume(0f);
			GameScene.scene.menu.active =
					GameScene.scene.menu.visible = false;
			GameScene.scene.status.active =
					GameScene.scene.status.visible = false;
		}

		@Override
		public void detach() {
			super.detach();
			Music.INSTANCE.volume(SPDSettings.musicVol() * 0.1f);
			GameScene.scene.menu.active =
					GameScene.scene.menu.visible = true;
			GameScene.scene.status.active =
					GameScene.scene.status.visible = true;
		}
	}
}
