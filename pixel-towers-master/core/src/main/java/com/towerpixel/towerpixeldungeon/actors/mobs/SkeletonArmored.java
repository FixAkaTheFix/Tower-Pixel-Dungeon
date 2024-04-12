package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.sprites.walls.SkeletonWarriorSprite;
import com.watabou.utils.Random;

public class SkeletonArmored extends Skeleton{

    {
        HP = HT = 100;
        defenseSkill = 4;
        spriteClass = SkeletonWarriorSprite.class;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(2, 8);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 10, 15 );
    }
}
