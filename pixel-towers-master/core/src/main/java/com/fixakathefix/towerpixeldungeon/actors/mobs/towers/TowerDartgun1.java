package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Poison;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerDartgun1Sprite;

public class TowerDartgun1 extends TowerCShooting {
    {
        HP = HT = 25;
        spriteClass = TowerDartgun1Sprite.class;

        attackRange = 12;
        baseAttackDelay = 2f;//dpt/c = 0.0125 + p

        cost = 180;
        upgrade1Cost = 250;
        damageMin = 3;
        damageMax = 6;
        upgradeLevel = 3;
        excludeBuff = Poison.class;
    }

    public int poisonPower = 3;

    @Override
    public int attackSkill(Char target) {
        return 20;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//attacks only foes on the same x or y coordinates
        if (buff(Animated.class)!=null && (Dungeon.level.distance( pos, Dungeon.hero.pos )>2 || Dungeon.level.adjacent( pos, enemy.pos ))) return false;
        return (enemy.pos % Dungeon.level.width() == pos % Dungeon.level.width() || enemy.pos / Dungeon.level.width() == pos / Dungeon.level.width())
                && (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && Dungeon.level.distance(enemy.pos, this.pos)<=attackRange);
    }


    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Poison.class).set(poisonPower);
        return super.attackProc(enemy, damage);
    }
}
