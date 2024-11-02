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

package com.towerpixel.towerpixeldungeon.items.scrolls;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Cripple;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vertigo;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.Statue;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.GnollSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.sprites.SkullSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ScrollOfSkulls extends Scroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_SKULL;
	}
	
	@Override
	public void doRead() {
		for (int i = 0;i<3;i++){
			ArrayList<Integer> respawnPoints = new ArrayList<>();
			for (int iz = 0; iz < PathFinder.NEIGHBOURS8.length; iz++) {
				int p = hero.pos + PathFinder.NEIGHBOURS8[iz];
				if (Actor.findChar( p ) == null && Dungeon.level.passable[p]) {
					respawnPoints.add( p );
				}
			}
			if (!respawnPoints.isEmpty()){
				ExplosiveSkull skully = new ExplosiveSkull();
				skully.pos = Random.element(respawnPoints);
				CellEmitter.get(skully.pos).start(FlameParticle.FACTORY, 0.1f,10);
				skully.state = skully.HUNTING;
				GameScene.add(skully);
			}
		}
		Sample.INSTANCE.play(Assets.Sounds.BONES);

		readAnimation();
		
	}
	
	@Override
	public int value() {
		return 40 * quantity;
	}

	public static class ExplosiveSkull extends Mob {
		{
			HP = HT = 10;
			alignment = Alignment.ALLY;
			spriteClass = SkullSprite.class;

			defenseSkill = 50;

			viewDistance = 4;

			flying = true;

		}

		@Override
		protected boolean act() {
			beckon(hero.pos);
			return super.act();
		}

		@Override
		public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
			die(Hero.class);
			return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			int cell;

			for (int i : PathFinder.NEIGHBOURS9){
				cell = pos + i;
				Char ch = Char.findChar(cell);
				if (ch!=null){
					if (ch.alignment == Alignment.ALLY){
						//friends receive 0 damage
					} else {
						ch.damage (Math.round(damageRoll()) - ch.drRoll(),this);
						Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
						throwChar(ch, trajectory, 4, false, true, getClass());
					};
					if (ch.equals(enemy)){
						Buff.affect(ch, Vertigo.class, 3);
					}
				}
				if (level.heroFOV[cell]) {
					CellEmitter.center(cell).burst(BlastParticle.FACTORY, 5);
				}
			}
		}

		@Override
		public int attackSkill(Char target) {
			return 123456;
		}

		@Override
		public int damageRoll() {
			return Random.Int(hero.lvl*2 + 4, hero.lvl*3 + 5);
		}
	}
}
