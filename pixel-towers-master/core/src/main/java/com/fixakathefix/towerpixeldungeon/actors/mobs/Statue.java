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

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon.Enchantment;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Grim;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.journal.Notes;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.StatueSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Statue extends Mob {
	
	{
		spriteClass = StatueSprite.class;

		EXP = 0;
		state = PASSIVE;
		
		properties.add(Property.INORGANIC);
	}
	
	protected Weapon weapon;

	public boolean levelGenStatue = true;
	
	public Statue() {
		super();
		
		do {
			weapon = (MeleeWeapon) Generator.random(Generator.Category.WEAPON);
		} while (weapon.cursed);
		
		HP = HT = 100;
		defenseSkill = 5;
	}

	public Statue(Weapon weapon1) {
		super();

		weapon = weapon1;

		HP = HT = 100;
		defenseSkill = 5;
	}
	public Statue(Weapon weapon1, Enchantment enchantment) {
		super();

		weapon = weapon1;

		weapon.enchant( enchantment);

		HP = HT = 100;
		defenseSkill = 5;
	}
	
	private static final String WEAPON	= "weapon";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( WEAPON, weapon );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		weapon = (Weapon)bundle.get( WEAPON );
	}
	
	@Override
	protected boolean act() {
		if (levelGenStatue && Dungeon.level.visited[pos]) {
			Notes.add( Notes.Landmark.STATUE );
		}
		if (alignment == Alignment.ALLY) beckon(hero.pos);
		return super.act();
	}
	
	@Override
	public int damageRoll() {
		return weapon.damageRoll(this);
	}
	
	@Override
	public int attackSkill( Char target ) {
		return (int)((9 + Dungeon.depth) * weapon.accuracyFactor( this, target ));
	}
	
	@Override
	public float attackDelay() {
		return super.attackDelay()*weapon.delayFactor( this );
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return super.canAttack(enemy) || weapon.canReach(this, enemy.pos);
	}

	@Override
	public int drRoll() {
		return super.drRoll() + Random.NormalIntRange(0, weapon.defenseFactor(this));
	}
	
	@Override
	public boolean add(Buff buff) {
		if (super.add(buff)) {
			if (state == PASSIVE && buff.type == Buff.buffType.NEGATIVE) {
				state = HUNTING;
			}
			return true;
		}
		return false;
	}

	@Override
	public void damage( int dmg, Object src ) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		
		super.damage( dmg, src );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		damage = weapon.proc( this, enemy, damage );
		if (!enemy.isAlive() && enemy == hero){
			Dungeon.fail(getClass());
			GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
		}
		return damage;
	}

	
	@Override
	public void die( Object cause ) {
		weapon.identify(false);
		Dungeon.level.drop( weapon, pos ).sprite.drop();
		super.die( cause );
	}
	
	@Override
	public void destroy() {
		if (levelGenStatue) {
			Notes.remove( Notes.Landmark.STATUE );
		}
		super.destroy();
	}

	@Override
	public float spawningWeight() {
		return 0f;
	}

	@Override
	public boolean reset() {
		state = PASSIVE;
		return true;
	}

	@Override
	public String description() {
		return Messages.get(this, "desc", weapon.name());
	}
	
	{
		resistances.add(Grim.class);
	}

	public static Statue random(){
		if (Random.Int(10) == 0){
			return new ArmoredStatue();
		} else {
			return new Statue();
		}
	}
	
}
