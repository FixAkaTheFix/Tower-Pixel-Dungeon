package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.levels.rooms.special.GardenRoom;
import com.towerpixel.towerpixeldungeon.sprites.AlmostEmptySprite;
import com.watabou.utils.Bundle;

public class BossImage extends Mob{ //an image of the mob - an invisible and unkillable mob that acts as a body part of another mob

    {

        HP = HT = 1000;
        EXP = 0;
        defenseSkill = 0;
        spriteClass = AlmostEmptySprite.class;

        viewDistance = 10;

        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);

        state = SLEEPING;
        /*HUNTING = new Passive();
        WANDERING = new Passive();
        PASSIVE = new Passive();
        SLEEPING = new Passive();*/

        viewDistance = 0;
    }

    public Mob assignedMob;
    int id = -1;


    @Override
    protected boolean act() {
        if (assignedMob==null) super.die(this);
        /*HP = assignedMob.HP;
        HT = assignedMob.HT;
        defenseSkill = assignedMob.defenseSkill;*/
        spend(1);//those act infinetly if not spend time, as there is no act()
        return true;
    }


    @Override
    protected boolean canAttack(Char enemy) {
        if (assignedMob==null) return super.canAttack(enemy); else return false;
    }

    @Override
    public int drRoll() {
        if (assignedMob==null) return super.drRoll(); else  return assignedMob.drRoll();
    }

    @Override
    public void damage(int dmg, Object src) {
        if (assignedMob==null) super.damage(dmg, src); else  assignedMob.damage(dmg, src);
    }

    @Override
    public String description() {
        if (assignedMob==null) return super.description(); else  return assignedMob.description();
    }

    @Override
    public String name() {
        if (assignedMob==null) return super.name(); else  return assignedMob.name();
    }


    @Override
    public boolean isImmune(Class effect) {
        if (assignedMob==null) return super.isImmune(effect); else  return assignedMob.isImmune(effect);
    }

    @Override
    public float spawningWeight() {
        if (assignedMob==null) return super.spawningWeight(); else  return assignedMob.spawningWeight();
    }



    @Override
    public void die(Object cause) {//those dont die
         super.die(cause);
    }

    @Override
    public String info() {
        if (assignedMob==null) return super.info(); else   return assignedMob.info();
    }

    @Override
    public boolean isInvulnerable(Class effect) {
        if (assignedMob==null) return super.isInvulnerable(effect); else  return assignedMob.isInvulnerable(effect);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        /*super.restoreFromBundle(bundle);
        this.pos = 1;
        destroy();//see restoreFromBundle in BossOoze*/
    }
}
