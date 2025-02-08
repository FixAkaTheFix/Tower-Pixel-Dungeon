package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerDisintegration2Sprite;
import com.watabou.utils.PointF;

public class TowerDisintegration2 extends TowerDisintegration1 {
    {
        HP = HT = 88;
        spriteClass = TowerDisintegration2Sprite.class;

        attackRange = 11;//dpt/c = 0.00313

        cost = 800;
        upgrade1Cost = 1200;
        damageMin = 10;
        damageMax = 10;
        upgradeLevel = 10;
    }

    @Override
    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 4);
        return p;
    }
}
