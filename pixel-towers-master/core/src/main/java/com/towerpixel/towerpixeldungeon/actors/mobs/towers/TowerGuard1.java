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


        viewDistance = 6;
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
        return super.act();
    }



}
