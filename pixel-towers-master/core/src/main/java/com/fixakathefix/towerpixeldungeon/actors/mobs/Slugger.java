package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.DamageSource;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Ooze;
import com.fixakathefix.towerpixeldungeon.sprites.SluggerSprite;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.Random;

public class Slugger extends Mob {

    {
        spriteClass = SluggerSprite.class;

        HP = HT = 40;
        defenseSkill = 1;

        viewDistance = 10;
        EXP = 12;
        maxLvl = 20;
    }

    @Override
    public int damageRoll() {
        return (Dungeon.level.mode== WndModes.Modes.CHALLENGE && Dungeon.depth==15) ? Random.NormalIntRange( 30,45 ) : Random.NormalIntRange( 10,15 );
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