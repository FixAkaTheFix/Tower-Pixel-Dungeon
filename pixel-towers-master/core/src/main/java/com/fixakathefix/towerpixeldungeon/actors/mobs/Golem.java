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

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MagicImmune;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.Imp;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.GolemSprite;
import com.fixakathefix.towerpixeldungeon.utils.BArray;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Golem extends Mob {
	
	{
		spriteClass = GolemSprite.class;
		
		HP = HT = 200;
		defenseSkill = 4;
		
		EXP = 12;
		maxLvl = 22;
		viewDistance = 8;

		loot = Random.oneOf(Generator.Category.WEAPON, Generator.Category.ARMOR);
		lootChance = 0.125f; //initially, see lootChance()

		properties.add(Property.INORGANIC);

		WANDERING = new Wandering();
		HUNTING = new Hunting();
	}

	@Override
	public void die(Object cause) {
		if (Dungeon.depth == 19 && Dungeon.level.mode == WndModes.Modes.CHALLENGE){
			HermitCrab swarm = new HermitCrab();
			swarm.pos = pos;
			GameScene.add(swarm);
			Dungeon.level.occupyCell(swarm);
		}
		super.die(cause);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 25, 30 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}
	
	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(4, 8);
	}

	@Override
	public float lootChance() {
		//each drop makes future drops 1/2 as likely
		// so loot chance looks like: 1/8, 1/16, 1/32, 1/64, etc.
		return super.lootChance() * (float)Math.pow(1/2f, Dungeon.LimitedDrops.GOLEM_EQUIP.count);
	}

	@Override
	public void rollToDropLoot() {
		Imp.Quest.process( this );
		super.rollToDropLoot();
	}

	public Item createLoot() {
		Dungeon.LimitedDrops.GOLEM_EQUIP.count++;
		//uses probability tables for demon halls
		if (loot == Generator.Category.WEAPON){
			return Generator.randomWeapon(5);
		} else {
			return Generator.randomArmor(5);
		}
	}

	private boolean teleporting = false;
	private int selfTeleCooldown = 0;
	private int enemyTeleCooldown = 0;

	private static final String TELEPORTING = "teleporting";
	private static final String SELF_COOLDOWN = "self_cooldown";
	private static final String ENEMY_COOLDOWN = "enemy_cooldown";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TELEPORTING, teleporting);
		bundle.put(SELF_COOLDOWN, selfTeleCooldown);
		bundle.put(ENEMY_COOLDOWN, enemyTeleCooldown);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		teleporting = bundle.getBoolean( TELEPORTING );
		selfTeleCooldown = bundle.getInt( SELF_COOLDOWN );
		enemyTeleCooldown = bundle.getInt( ENEMY_COOLDOWN );
	}

	@Override
	protected boolean act() {
		selfTeleCooldown--;
		enemyTeleCooldown--;
		if (teleporting){
			((GolemSprite)sprite).teleParticles(false);
			if (Actor.findChar(target) == null && Dungeon.level.openSpace[target]) {
				ScrollOfTeleportation.appear(this, target);
				selfTeleCooldown = 30;
			} else {
				target = Dungeon.level.randomDestination(this);
			}
			teleporting = false;
			spend(TICK);
			return true;
		}
		return super.act();
	}

	public void onZapComplete(){
		teleportEnemy();
		next();
	}

	public void teleportEnemy(){
		spend(TICK);

		int bestPos = enemy.pos;
		for (int i : PathFinder.NEIGHBOURS8){
			if (Dungeon.level.passable[pos + i]
				&& Actor.findChar(pos+i) == null
				&& Dungeon.level.trueDistance(pos+i, enemy.pos) > Dungeon.level.trueDistance(bestPos, enemy.pos)){
				bestPos = pos+i;
			}
		}

		if (enemy.buff(MagicImmune.class) != null){
			bestPos = enemy.pos;
		}

		if (bestPos != enemy.pos){
			ScrollOfTeleportation.appear(enemy, bestPos);
			if (enemy instanceof Hero){
				((Hero) enemy).interrupt();
				Dungeon.observe();
				GameScene.updateFog();
			}
		}

		enemyTeleCooldown = 20;
	}

	private boolean canTele(int target){
		if (enemyTeleCooldown > 0) return false;
		PathFinder.buildDistanceMap(target, BArray.not(Dungeon.level.solid, null), Dungeon.level.distance(pos, target)+1);
		//zaps can go around blocking terrain, but not through it
		if (PathFinder.distance[pos] == Integer.MAX_VALUE){
			return false;
		}
		return true;
	}

	private class Wandering extends Mob.Wandering{

		@Override
		protected boolean continueWandering() {
			enemySeen = false;

			int oldPos = pos;
			if (target != -1 && getCloser( target )) {
				spend( 1 / speed() );
				return moveSprite( oldPos, pos );
			} else if (!Dungeon.bossLevel() && target != -1 && target != pos && selfTeleCooldown <= 0) {
				((GolemSprite)sprite).teleParticles(true);
				teleporting = true;
				spend( 2*TICK );
			} else {
				target = Dungeon.level.randomDestination( Golem.this );
				spend( TICK );
			}

			return true;
		}
	}

	private class Hunting extends Mob.Hunting{

		@Override
		public boolean act(boolean enemyInFOV, boolean justAlerted) {
			if (!enemyInFOV || canAttack(enemy)) {
				return super.act(enemyInFOV, justAlerted);
			} else {
				enemySeen = true;
				target = enemy.pos;

				int oldPos = pos;

				if (distance(enemy) >= 1 && Random.Int(100/distance(enemy)) == 0
						&& !Char.hasProp(enemy, Property.IMMOVABLE) && canTele(target)){
					if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
						sprite.zap( enemy.pos );
						return false;
					} else {
						teleportEnemy();
						return true;
					}

				} else if (getCloser( target )) {
					spend( 1 / speed() );
					return moveSprite( oldPos,  pos );

				} else if (!Char.hasProp(enemy, Property.IMMOVABLE) && canTele(target)) {
					if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
						sprite.zap( enemy.pos );
						return false;
					} else {
						teleportEnemy();
						return true;
					}

				} else {
					spend( TICK );
					return true;
				}

			}
		}
	}

}
