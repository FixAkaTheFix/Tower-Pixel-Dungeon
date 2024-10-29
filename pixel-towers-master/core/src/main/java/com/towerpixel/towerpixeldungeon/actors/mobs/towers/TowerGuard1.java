package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.ToxicGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Drowsy;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;

public class TowerGuard1 extends SentientTower {

    {

        HP = HT = 50;
        spriteClass = TowerGuard1Sprite.class;


        viewDistance = 2;
        baseAttackDelay = 1f;

        cost = 100;
        damageMin = 2;
        damageMax = 4;
        upgradeLevel = 3;
        defMin = 0;
        defMax = 4;
        upgrade1Cost = 200;
        defenseSkill = 5;
        baseAttackSkill = 10;

    }

    public int regenNum = 5;
    public int guardPos = -1;

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HP, HT , damageMin, damageMax, defMin, defMax, regenNum ));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }


    @Override
    protected boolean act() {
        if (Dungeon.hero.buff(WaveCooldownBuff.class) != null){
            if (HP + regenNum <= HT) HP+=regenNum;
            else if (HT < HP + regenNum && HT>HP) HP = HT;
        }
        if (guardPos==-1) guardPos = pos;
        beckon(guardPos);
        return super.act();
    }
    public static final String GUARDPOS = "guardpos";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(GUARDPOS, guardPos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        guardPos = bundle.getInt(GUARDPOS);
    }

}
