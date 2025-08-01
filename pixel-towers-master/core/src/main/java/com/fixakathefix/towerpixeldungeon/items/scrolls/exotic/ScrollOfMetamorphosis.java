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
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hex;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Humongous;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Small;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

public class ScrollOfMetamorphosis extends ExoticScroll {
	
	{
		icon = ItemSpriteSheet.Icons.SCROLL_METAMORPH;
		image = ItemSpriteSheet.EXOTIC_METAMORPHOSIS;
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
			Char ch = Actor.findChar(cell);
			if (ch != null &&
					ch.alignment == Char.Alignment.ENEMY) //all non-boss enemies
			{
				target = (Mob)ch;
			}

			if (target == null){
				GLog.w(Messages.get(ScrollOfMetamorphosis.class, "useonenemies"));
			} else  if (target.properties().contains(Char.Property.BOSS) || target.properties().contains(Char.Property.MINIBOSS)){
				GLog.w(Messages.get(ScrollOfMetamorphosis.class, "usenotonbosses"));
			} else {
				detach(curUser.belongings.backpack);
				Sample.INSTANCE.play( Assets.Sounds.READ, 1f, 1f );
				Sample.INSTANCE.play( Assets.Sounds.RAY, 1f, 1.3f );
				target.sprite.centerEmitter().burst( Speck.factory( Speck.RED_LIGHT ), 3 );
				target.sprite.centerEmitter().burst( Speck.factory( Speck.STAR ), 3 );
				target.sprite.centerEmitter().burst( Speck.factory( Speck.TOXIC ), 2 );
				target.sprite.centerEmitter().burst( Speck.factory( Speck.STORM ), 2 );

				Mob mob = Reflection.newInstance(Random.oneOf(
						TowerCrossbow1.class,
						TowerWand1.class,
						TowerCannon1.class,
						TowerGrave1.class,
						TowerDartgun1.class,
						TowerLightning1.class,
						TowerDisintegration1.class,
						TowerTntLog.class,
						TowerCrossbow1.class,
						TowerWand1.class,
						TowerCannon1.class,
						TowerGrave1.class,
						TowerDartgun1.class,
						TowerLightning1.class,
						TowerDisintegration1.class,
						TowerTntLog.class,
						ChiefRat.class,
						MagiCrab.class
				));
				Buff.affect(mob, Barrier.class).setShield(Dungeon.depth * 20 + 15);
				Buff.affect(mob, Random.oneOf(
						ChampionEnemy.Giant.class,
						ChampionEnemy.Blessed.class,
						ChampionEnemy.Blazing.class,
						ChampionEnemy.Projecting.class,
						ChampionEnemy.Rejuvenating.class));
				Buff.affect(mob, Random.oneOf(Speed.class, Chill.class, Hex.class, Bless.class), 1000);
				Buff.affect(mob, Random.oneOf(Humongous.class, Small.class));
				if (mob instanceof Tower) ((Tower)mob).sellable = false;
				mob.alignment = Char.Alignment.ALLY;
				mob.state = mob.HUNTING;
				mob.pos = target.pos;
				target.die(ScrollOfMetamorphosis.class);
				GameScene.add(mob);

				readAnimation();

			}
		}

		@Override
		public String prompt() {
			return Messages.get(ScrollOfMetamorphosis.class, "prompt");
		}

	};
}
