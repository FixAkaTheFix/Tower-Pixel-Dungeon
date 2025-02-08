package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerDisintegration3Sprite;
import com.watabou.utils.PointF;

public class TowerDisintegration3 extends TowerDisintegration2 {
    {
        HP = HT = 133;
        spriteClass = TowerDisintegration3Sprite.class;

        attackRange = 12;//dpt = 0.0028

        cost = 2000;
        damageMin = 22;
        damageMax = 22;
        upgCount = 0;
    }

    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 3);
        return p;
    }
}
