package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.DamageSource;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Ooze;
import com.towerpixel.towerpixeldungeon.sprites.SluggerSprite;
import com.watabou.utils.Random;

public class Slugger extends Mob {

    {
        spriteClass = SluggerSprite.class;

        HP = HT = 50;
        defenseSkill = 1;

        viewDistance = 10;
        EXP = 12;
        maxLvl = 20;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 15,20 );
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, Ooze.class);
        return super.attackProc(enemy, damage);
    }

    @Override
    public int attackSkill( Char target ) {
        return 10;
    }

    @Override
    public void damage(int dmg, Object src) {
        if (DamageSource.POISON.contains(src.getClass())) {//
            } else if (DamageSource.MAGICAL.contains(src.getClass())) {
            super.damage(dmg, src);
        } else super.damage(dmg/4, src);
    }

    @Override
    public int drRoll() {
        return super.drRoll();
    }
}