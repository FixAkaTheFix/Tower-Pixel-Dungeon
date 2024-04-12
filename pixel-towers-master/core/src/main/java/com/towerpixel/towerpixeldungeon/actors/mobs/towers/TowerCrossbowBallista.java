package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Cripple;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbowBallistaSprite;

public class TowerCrossbowBallista extends TowerCrossbow2{

    {
        HP = HT = 100;
        spriteClass = TowerCrossbowBallistaSprite.class;

        viewDistance = 10;//DPT =0.2*150 =30 DPT/C = 33/1850= 0.01622
        baseAttackDelay = 5f;
        cost = 2000;

        upgCount = 0;
        upgrade1Cost = 10000;
        damageMin = 150;
        damageMax = 230;
    }
    @Override
    public int attackSkill(Char target) {
        return 50;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Cripple.class,4);
        return super.attackProc(enemy, damage);
    }
}