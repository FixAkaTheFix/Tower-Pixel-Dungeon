package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.sprites.SkeletonWarriorShieldedSprite;
import com.watabou.utils.Random;

public class SkeletonArmoredShielded extends SkeletonArmored{

    {
        HP = HT = 150;
        defenseSkill = 4;
        spriteClass = SkeletonWarriorShieldedSprite.class;
        baseSpeed = 1.02f;
        targetingPreference = TargetingPreference.NOT_WALLS;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(50, 100);
    }


    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 15, 20 );
    }
}
