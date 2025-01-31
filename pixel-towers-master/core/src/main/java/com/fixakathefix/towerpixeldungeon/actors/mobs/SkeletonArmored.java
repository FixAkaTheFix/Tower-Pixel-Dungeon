package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.DamageSource;
import com.fixakathefix.towerpixeldungeon.sprites.SkeletonWarriorSprite;
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
    public void damage(int dmg, Object src) {
        if (!DamageSource.MAGICAL.contains(src.getClass())) dmg/=2;
        super.damage(dmg, src);
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 15, 22 );
    }
}
