package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.TowerDisintegration2Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDisintegration3Sprite;
import com.watabou.utils.PointF;

public class TowerDisintegration3 extends TowerDisintegration2 {
    {
        HP = HT = 100;
        spriteClass = TowerDisintegration3Sprite.class;

        attackRange = 8;
        baseAttackDelay = 1.6f;

        cost = 2000;
        damageMin = 15;
        damageMax = 15;
        upgCount = 0;
    }

    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 3);
        return p;
    }
}
