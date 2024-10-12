package com.towerpixel.towerpixeldungeon.actors.mobs;


import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Haste;
import com.towerpixel.towerpixeldungeon.actors.buffs.Levitation;
import com.towerpixel.towerpixeldungeon.actors.buffs.Roots;
import com.towerpixel.towerpixeldungeon.actors.buffs.Terror;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.GnollTwilightSprite;
import com.watabou.utils.Random;

public class GnollTwilight extends Gnoll {
    public int fleeingTime;


    {
        spriteClass = GnollTwilightSprite.class;
        fleeingTime = 0;

        HP = HT = 40;
        defenseSkill = 5;
        baseSpeed = 1f;
        viewDistance = 8;

        EXP = 5;

        state = WANDERING;

        ranged = true;

        //at half quantity, see createLoot()
        loot = Generator.Category.MISSILE;
        lootChance = 1f;

        properties.add(Property.MINIBOSS);
    }

    @Override
    public int attackSkill( Char target ) {
        return 16;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return (super.canAttack(enemy) || new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        damage = super.attackProc( enemy, damage );
        int effect = Random.Int(4);
        if ((effect == 1) && (enemy.buff(Roots.class) == null)){Buff.affect( enemy, Roots.class, 5);}
        if ((effect == 2) && (enemy.buff(Blindness.class) == null)){Buff.affect( enemy, Blindness.class, 5);}
        if ((effect == 3) && (enemy.buff(Levitation.class) == null)){Buff.affect( enemy, Levitation.class, 5);}
        if ((effect == 0) && (enemy.buff(Weakness.class) == null)){Buff.affect( enemy, Weakness.class, 5);}
        Buff.affect(this, Terror.class, 30); Buff.affect(this, Haste.class, 30);
        return damage;
    }

@Override
    protected boolean getCloser( int target ) {
        if (state == HUNTING) {
            return enemySeen && getFurther( target );
        } else {
            return super.getCloser( target );
        }
    }

    @Override
    public Item createLoot() {
        MissileWeapon drop = (MissileWeapon)super.createLoot();
        //half quantity, rounded up
        drop.quantity((drop.quantity()+1)/2);
        return drop;
    }

}

