package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard1Sprite;

public class TowerGuard1 extends SentientTower {

    {

        HP = HT = 75;
        spriteClass = TowerGuard1Sprite.class;

        viewDistance = 6;
        baseAttackDelay = 1f;

        cost = 150;
        damageMin = 3;//dpt/c = 0.00267
        damageMax = 5;
        upgradeLevel = 3;
        defMin = 0;
        defMax = 4;
        upgrade1Cost = 300;
        defenseSkill = 5;
    }

    public int regenNum = 6;

    public TowerGuard1(){
        super();

    }

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

    @Override
    public int attackSkill(Char target) {
        return 11;
    }
}
