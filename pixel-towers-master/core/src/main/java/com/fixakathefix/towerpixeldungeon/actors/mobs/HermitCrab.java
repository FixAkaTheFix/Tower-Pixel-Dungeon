package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Terror;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.HermitCrabSprite;
import com.watabou.utils.Random;

public class HermitCrab extends Crab {

    {
        spriteClass = HermitCrabSprite.class;

        HP = HT = 15;
        defenseSkill = 2;
        baseSpeed = 0.95f;

        EXP = 0;
        maxLvl = 10;

    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(2, 7);
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        HermitCrabNoShell hermitCrabNoShell = new HermitCrabNoShell();
        hermitCrabNoShell.pos = pos;
        Buff.affect(hermitCrabNoShell, Terror.class, 5);
        GameScene.add(hermitCrabNoShell);
    }
}
