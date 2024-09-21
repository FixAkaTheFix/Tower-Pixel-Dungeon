package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Bless;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard3UpgradedSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerGuardPaladin extends TowerGuard3{

    {
        HP = HT = 400;
        spriteClass = TowerGuard3UpgradedSprite.class;

        cost = 1800;
        damageMin = 13;
        damageMax = 20;
        upgradeLevel = 8;
        defMin = 2;
        defMax = 15;
        regenNum = 30;
        upgCount = 0;
        baseAttackDelay = 0.99f;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        ArrayList<Char> candidates = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS25){
            Char chh = Char.findChar(pos+i);
            if (chh!=null && chh instanceof Tower && chh.alignment == Alignment.ALLY && chh!=this){
                candidates.add(chh);
            }
        }
        if (!(candidates.isEmpty())){
            Char ch = Random.element(candidates);
            if (ch != null) {
                if (ch.buff(Bless.class)!=null) Buff.affect(ch, Bless.class, 10);
                Buff.affect(ch, Healing.class).setHeal(10,0.2f,1);
            }

        }
        return super.attackProc(enemy, damage);
    }
}
