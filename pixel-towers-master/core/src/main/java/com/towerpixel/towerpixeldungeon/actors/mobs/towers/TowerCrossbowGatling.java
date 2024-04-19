package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbowGatlingSprite;

public class TowerCrossbowGatling extends TowerCrossbow3{

    {
        HP = HT = 100;
        spriteClass = TowerCrossbowGatlingSprite.class;

        viewDistance = 8;//DPT =11,5*(0.5~3.5) =5,75~40,25 DPT/C = 5,75~40,25/1850= 0,003~0,0218
        baseAttackDelay = 1f;
        cost = 2000;

        upgCount=0;

        upgrade1Cost = 10000;
        damageMin = 6;
        damageMax = 12;
    }

    public int atCooldown = 1;

    @Override
    protected boolean act() {
        if ( atCooldown < 13 ) atCooldown++;
        baseAttackDelay = 0.1f + (atCooldown * 0.1f);
        sprite.ra = 1 / (5*baseAttackDelay);
        if (baseAttackDelay<0.8f) sprite.emitter().start(SmokeParticle.FACTORY, 1f, 3);
        return super.act();
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        if ( atCooldown > 1 ) atCooldown -= 1;
        if ( atCooldown > 1 ) atCooldown -= 1;
        if ( atCooldown > 1 ) atCooldown -= 1;
        baseAttackDelay = 0.1f + (atCooldown * 0.1f);//max cooldown = 0.2
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }
}