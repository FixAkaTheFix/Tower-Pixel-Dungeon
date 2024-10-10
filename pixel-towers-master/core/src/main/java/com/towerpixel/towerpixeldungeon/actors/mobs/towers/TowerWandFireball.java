package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Burning;
import com.towerpixel.towerpixeldungeon.sprites.TowerWandFireballSprite;

public class TowerWandFireball extends TowerWand3{

    {
        HP = HT = 120;
        spriteClass = TowerWandFireballSprite.class;

        attackRange = 6;//DPT =70*0.6 + 2 + HT/40 = 44 + HT/40 DPT/C = 0,0187 + HT/94000
        upgCount = 0;

        cost = 2000;
        upgrade1Cost = 1000;
        damageMin = 20;
        damageMax = 60;
    }

    @Override
    public int attackSkill(Char target) {
        return 100;
    }

    @Override
    public int attackProc( Char enemy, int damage ) {
        Buff.affect(enemy, Burning.class).reignite( enemy );
        return damage;
    }

    @Override
    protected Char chooseEnemy() {
        return super.chooseEnemy();
    }
}
