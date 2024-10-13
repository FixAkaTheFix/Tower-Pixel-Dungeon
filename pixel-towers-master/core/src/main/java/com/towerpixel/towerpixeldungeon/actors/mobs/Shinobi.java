package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Charm;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.ShinobiSprite;
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
			spend( -1 / speed() );
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

		int direction = PathFinder.NEIGHBOURS25[Random.Int(8)];
		
		Ballistica route = new Ballistica( pos+direction, target, Ballistica.PROJECTILE);
		if (route.dist == 0){
			teleport();
			return;
		}
		int cell = route.collisionPos;

		//can't occupy the same cell as another char, so move back one.
		if (Actor.findChar( cell ) != null && cell != this.pos)
			cell = route.path.get(route.dist-1);

		if (Dungeon.level.avoid[ cell ]){
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int n : PathFinder.NEIGHBOURS8) {
				cell = route.collisionPos + n;
				if (Dungeon.level.passable[cell] && Actor.findChar( cell ) == null) {
					candidates.add( cell );
				}
			}
			if (candidates.size() > 0)
				cell = Random.element(candidates);
			else {
				teleportCooldown = Random.IntRange(1, 6);
				return;
			}
		}
		
		ScrollOfTeleportation.appear( this, cell );

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
