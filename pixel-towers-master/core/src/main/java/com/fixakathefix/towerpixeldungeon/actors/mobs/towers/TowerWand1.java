package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerWand1Sprite;

public class TowerWand1 extends TowerCShooting {
    {
        HP = HT = 40;
        spriteClass = TowerWand1Sprite.class;

        attackRange = 7;//DPT/c = 0.0075
        baseAttackDelay = 3f;

        cost = 200;
        upgrade1Cost = 250;
        damageMin = 4;
        damageMax = 5;
        upgradeLevel = 3;
    }
    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }
}
