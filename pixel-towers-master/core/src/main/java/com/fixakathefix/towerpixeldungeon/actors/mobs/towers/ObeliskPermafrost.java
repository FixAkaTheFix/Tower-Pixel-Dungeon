package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ShieldBuff;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.ObeliskPermafrostSprite;
import com.watabou.noosa.audio.Sample;

public class ObeliskPermafrost extends Obelisk {
    {
        HP = HT = 15;
        spriteClass = ObeliskPermafrostSprite.class;

        attackRange = 9;
        baseAttackDelay = 4f;

        cost = 0;
        upgrade1Cost = 0;
        damageMin = 1;
        damageMax = 2;
        upgCount = 0;
        sellable = false;
        if (Dungeon.hero != null){
            HP = HT = Dungeon.hero.lvl * 5 + 15;
        }
    }



    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }
    @Override
    public void hitSound(float pitch) {
        Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC, 1, pitch);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Chill.class, 5);
        Buff.affect(this, Barrier.class).setShield(Dungeon.hero.lvl * 5 + 10);
        return super.attackProc(enemy, damage);
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }
}