package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Charm;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.ShinobiSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Shinobi extends Mob {

	private int teleportCooldown = 0;

	{
		spriteClass = ShinobiSprite.class;

		HP = HT = 80;
		defenseSkill = 8;
		viewDistance = 4;
		EXP = 7;

		immunities.add( Paralysis.class );
		immunities.add( Charm.class );
		immunities.add( Blindness.class );

		ranged = true;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 5, 14 );
	}

	@Override
	protected boolean canAttack( Char enemy ) {
		Ballistica attack = new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE);
		return attack.collisionPos == enemy.pos;
	}
	
	@Override
	protected boolean getCloser( int target ) {
		if (fieldOfView[target] && Dungeon.level.distance( pos, target ) <= 3 && teleportCooldown <= 0) {
			teleport( );
			spend(1);
			return true;
		} else {
			teleportCooldown--;
			return super.getCloser( target );
		}
	}

	@Override
	public float attackDelay() {
		return super.attackDelay() * 0.6f;
	}

	private void teleport() {

		ArrayList<Integer> candidates = new ArrayList<>();
		for (int i : PathFinder.NEIGHBOURS25){
			int cell = pos + i;
			if (cell > 0 && cell < Dungeon.level.width() * Dungeon.level.height()){
				if (Dungeon.level.passable[cell]
						&& Char.findChar(cell) == null
						&& Dungeon.level.pit[cell]){
					candidates.add(cell);
				}
			}
		}
		if (!(candidates.isEmpty())) ScrollOfTeleportation.appear( this, Random.element(candidates) );

		teleportCooldown = Random.IntRange(1, 6);
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20;
	}

	private static final String TELEPORTING_COOLDOWN = "teleportingcooldown";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(TELEPORTING_COOLDOWN, teleportCooldown);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		teleportCooldown = bundle.getInt(TELEPORTING_COOLDOWN);
	}
}
