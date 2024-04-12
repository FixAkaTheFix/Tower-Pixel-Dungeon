package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.sprites.HermitCrabNoShellSprite;
import com.watabou.utils.Random;

public class HermitCrabNoShell extends Crab {

    {
        spriteClass = HermitCrabNoShellSprite.class;

        HT = 15;
        HP = 10;
        defenseSkill = 15;
        baseSpeed = 3.5f;

        state = FLEEING;

        EXP = 4;
        maxLvl = 10;

        targetingPreference = TargetingPreference.NOT_WALLS;

    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 15 );
    }

    @Override
    public int drRoll() {
        return 0;
    }

}
