package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard3UpgradedSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerGuardPaladin extends TowerGuard3{

    {
        HP = HT = 800;
        spriteClass = TowerGuard3UpgradedSprite.class;

        cost = 2550;
        damageMin = 18;
        damageMax = 30;//dpt/c = 0.00941
        upgradeLevel = 8;
        defMin = 3;
        defMax = 10;
        regenNum = 15;
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
                Buff.affect(ch, Healing.class).increaseHeal(30);
            }

        }
        return super.attackProc(enemy, damage);
    }
}
