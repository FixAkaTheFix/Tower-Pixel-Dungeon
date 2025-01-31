package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.AlmostEmptySprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;

public class BossImage extends Mob{ //an image of the mob - an invisible and unkillable mob that acts as a body part of another mob

    {

        HP = HT = 1000;
        EXP = 0;
        defenseSkill = 0;
        spriteClass = AlmostEmptySprite.class;

        viewDistance = 10;

        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);

        state = PASSIVE;
        /*HUNTING = new Passive();
        WANDERING = new Passive();
        PASSIVE = new Passive();
        SLEEPING = new Passive();*/

        viewDistance = 0;
    }

    public Mob assignedMob;
    int id = -1;


    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        return true;
    }

    @Override
    protected boolean act() {
        if (assignedMob==null){
            sprite.killAndErase();
            destroy();
        }
        spend(0.3f);//they not act, but check whether the mob is null, then they die
        return true;
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }
    @Override
    protected boolean canAttack(Char enemy) {
        return false;
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
        if (assignedMob==null) return super.name(); else  return DeviceCompat.isDebug() ? assignedMob.name() + " " + "'s image" : assignedMob.name();
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
         if (sprite!=null) sprite.killAndErase();
         destroy();
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
