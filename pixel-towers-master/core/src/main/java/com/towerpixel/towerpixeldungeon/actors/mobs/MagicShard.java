package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.sprites.MagicShardSprite;
import com.watabou.utils.Random;

public class MagicShard extends Mob {

    public int level = 1;
    {
        spriteClass = MagicShardSprite.class;
        flying = true;
        baseSpeed = 2;

        HP = HT = 1;
        defenseSkill = 15;
    }



    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, level);
    }

    @Override
    public int attackSkill( Char target ) {
        return 10;
    }

    @Override
    public int drRoll() {
        return 0;
    }
}
