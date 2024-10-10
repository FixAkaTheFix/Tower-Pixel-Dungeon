package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgun1Sprite;

public class TowerDartgun1 extends TowerCShooting {
    {
        HP = HT = 25;
        spriteClass = TowerDartgun1Sprite.class;

        attackRange = 13;
        baseAttackDelay = 1f;

        cost = 180;
        upgrade1Cost = 250;
        damageMin = 3;
        damageMax = 7;
        upgradeLevel = 3;
        excludeBuff = Poison.class;
    }

    public int poisonPower = 3;

    @Override
    public int attackSkill(Char target) {
        return 12;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//attacks only foes on the same x or y coordinates
        return (enemy.pos % Dungeon.level.width() == pos % Dungeon.level.width() || enemy.pos / Dungeon.level.width() == pos / Dungeon.level.width())
                && (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && Dungeon.level.distance(enemy.pos, this.pos)<=attackRange);
    }


    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Poison.class).set(poisonPower);
        return super.attackProc(enemy, damage);
    }
}
