package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbowGatlingSprite;
import com.watabou.utils.Bundle;

public class TowerCrossbowGatling extends TowerCrossbow3{

    {
        HP = HT = 250;
        spriteClass = TowerCrossbowGatlingSprite.class;

        attackRange = 7;//DPT/c was 0.0019 -> 0.004 which is very weak. Now it is 0.00236 -> 0.0176
        baseAttackDelay = 2f;
        cost = 2000;

        upgCount=0;

        upgrade1Cost = 10000;
        damageMin = 6;
        damageMax = 13;
    }
    private float attackRegulator = 2f;

    @Override
    protected boolean act() {

        if (attackRegulator < 2) attackRegulator+=0.05;// max ~ 2.01
        sprite.ra = (2 - attackRegulator)*0.2f;
        if (attackRegulator<0.5f) sprite.emitter().start(SmokeParticle.FACTORY, 1f, 3);
        return super.act();
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        if (attackRegulator > 0.4) attackRegulator-=0.20;//min ~ 0.3
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    public float attackDelay() {
        return super.attackDelay() * attackRegulator;
    }


    private static final String ATTACKREG = "attackreg";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(ATTACKREG, attackRegulator);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        attackRegulator = bundle.getFloat(ATTACKREG);
    }
}