package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbow1Sprite;

public class TowerCrossbow1 extends TowerCShooting {
    {
        HP = HT = 40;
        spriteClass = TowerCrossbow1Sprite.class;

        attackRange = 8;//DPT =3.5*1.1 = 3.85 DPT/C = 3.85/100 = 0,0385
        baseAttackDelay = 0.9f;

        cost = 200;
        upgrade1Cost = 250;
        damageMin = 2;
        damageMax = 5;
        upgradeLevel = 3;
    }

    @Override
    public int attackSkill(Char target) {
        return 13;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return !Dungeon.level.adjacent( pos, enemy.pos )
                && (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && Dungeon.level.distance(enemy.pos, this.pos)<=attackRange);
    }
}
