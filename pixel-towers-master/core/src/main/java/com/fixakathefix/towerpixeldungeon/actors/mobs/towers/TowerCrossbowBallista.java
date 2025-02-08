package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Cripple;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbowBallistaSprite;

public class TowerCrossbowBallista extends TowerCrossbow3{

    {
        HP = HT = 300;
        spriteClass = TowerCrossbowBallistaSprite.class;

        attackRange = 9;//DPT =0.2*150 =30 DPT/C = 33/1850= 0.01622
        baseAttackDelay = 6f;
        cost = 2000;

        upgCount = 0;
        upgrade1Cost = 10000;
        damageMin = 150;
        damageMax = 230;
    }
    @Override
    public int attackSkill(Char target) {
        return 500;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Cripple.class,4);
        return super.attackProc(enemy, damage);
    }
}