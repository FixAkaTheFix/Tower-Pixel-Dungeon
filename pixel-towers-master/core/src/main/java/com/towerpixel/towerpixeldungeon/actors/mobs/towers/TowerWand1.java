package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerWand1Sprite;

public class TowerWand1 extends TowerCShooting {
    {
        HP = HT = 40;
        spriteClass = TowerWand1Sprite.class;

        attackRange = 5;//DPT =5.5*0.6 = 3.3 DPT/C = 3.3/100 = 0,033
        baseAttackDelay = 2f;

        cost = 200;
        upgrade1Cost = 250;
        damageMin = 4;
        damageMax = 5;
        upgradeLevel = 3;
    }
    @Override
    public int attackSkill(Char target) {
        return 50;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }
}
