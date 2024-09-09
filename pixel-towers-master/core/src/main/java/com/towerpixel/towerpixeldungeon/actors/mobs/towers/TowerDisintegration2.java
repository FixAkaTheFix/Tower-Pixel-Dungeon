package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.Beam;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.PurpleParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerDisintegration1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDisintegration2Sprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.PointF;

public class TowerDisintegration2 extends TowerDisintegration1 {
    {
        HP = HT = 70;
        spriteClass = TowerDisintegration2Sprite.class;

        viewDistance = 6;//DPT =3.5*1.1 = 3.85 DPT/C = 3.85/100 = 0,0385
        baseAttackDelay = 1.6f;

        cost = 800;
        upgrade1Cost = 1200;
        damageMin = 7;
        damageMax = 7;
        upgradeLevel = 10;
    }

    @Override
    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 4);
        return p;
    }
}
