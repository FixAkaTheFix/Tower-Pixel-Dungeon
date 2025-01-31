package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbowGatlingSprite;
import com.watabou.utils.Bundle;

public class TowerCrossbowGatling extends TowerCrossbow3{

    {
        HP = HT = 250;
        spriteClass = TowerCrossbowGatlingSprite.class;

        attackRange = 7;//DPT =11,5*(0.5~3.5) =5,75~40,25 DPT/C = 5,75~40,25/1850= 0,003~0,0218
        baseAttackDelay = 1f;
        cost = 2000;

        upgCount=0;

        upgrade1Cost = 10000;
        damageMin = 6;
        damageMax = 13;
    }

    public float atCooldown = 1;

    @Override
    protected boolean act() {
        if ( atCooldown < 13 ) atCooldown+=0.2f;
        baseAttackDelay = 0.1f + (atCooldown * 0.1f);
        sprite.ra = 1 / (5*baseAttackDelay);
        if (baseAttackDelay<0.8f) sprite.emitter().start(SmokeParticle.FACTORY, 1f, 3);
        return super.act();
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        if ( atCooldown > 1.2f ) atCooldown -= 0.4f;
        if ( atCooldown > 1.2f ) atCooldown -= 0.4f;
        if ( atCooldown > 1.2f ) atCooldown -= 0.4f;
        baseAttackDelay = 0.1f + (atCooldown * 0.1f);//max cooldown = 0.2
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    private static final String ATCOOLDOWN = "atcooldown";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(ATCOOLDOWN, atCooldown);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        atCooldown = bundle.getInt(ATCOOLDOWN);
    }
}