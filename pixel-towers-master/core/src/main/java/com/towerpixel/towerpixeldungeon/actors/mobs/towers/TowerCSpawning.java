package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class TowerCSpawning extends Tower{

    {
        properties.add(Property.IMMOVABLE);
    }
    public int maxMinions; public int minionCount = 0;
    public int minionCooldown; public int minionCooldownLeft = 0;
    public int minionHP;
    public int minionDamageMax;
    public int minionDamageMin;
    public int minionAttackSkill;
    public int minionDefenseSkill;
    public int minionDR;

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT , maxMinions, minionCooldown, minionHP, minionDamageMin, minionDamageMax, minionAttackSkill*10, minionDefenseSkill*20, minionDR, minionCount, maxMinions, minionCooldownLeft));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }
    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

    public static final String MINIONCOOLDOWNLEFT = "minioncooldownleft";
    public static final String MINIONCOUNT = "minioncount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MINIONCOOLDOWNLEFT, minionCooldownLeft);
        bundle.put(MINIONCOUNT, minionCount);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        minionCount = bundle.getInt(MINIONCOUNT);
        minionCooldownLeft = bundle.getInt(MINIONCOOLDOWNLEFT);
    }
    @Override
    public void die(Object cause) {
        super.die(cause);
    }
    public void spawnMinion(int pos) {}

    @Override
    protected boolean act() {
        minionCooldownLeft--;
        if (minionCooldownLeft<=0) {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS8) {
                if (Char.findChar(pos + i) == null && level.passable[pos + i])
                    candidates.add(pos + i);
            }
            if (!candidates.isEmpty() && minionCount<maxMinions){
                spawnMinion(Random.element(candidates));
                minionCount++;
            }
            minionCooldownLeft = minionCooldown;
        }
        return super.act();
    }
}
