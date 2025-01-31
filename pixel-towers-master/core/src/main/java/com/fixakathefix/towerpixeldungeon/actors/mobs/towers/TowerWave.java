package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.watabou.utils.Bundle;

public abstract class TowerWave extends TowerNotliving{

    /**
     * This type of tower summons mobs every time after it sees enemies first time in a wave.
     */

    {
        properties.add(Char.Property.IMMOVABLE);

        viewDistance = 9;
    }

    public boolean isPrepared = true;

    public int minionencodingsMax;//knife lead shield arch mag
    public int minionencodingsCurrent;

    public void prepareMobSpawn(){
        isPrepared = true;
    }
    @Override
    protected boolean act() {
        if (isPrepared && enemy!=null){
            spawnWaveMinions();
            isPrepared=false;
        }
        return super.act();
    }

    @Override
    protected boolean getCloser(int target) {
        if (buff(Animated.class) !=null) {
            beckon(hero.pos);
            return super.getCloser( hero.pos );
        } else return true;
    }

    @Override
    protected boolean getFurther(int target) {
        if (buff(Animated.class) !=null) return super.getFurther(target); else return true;
    }
    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }


    public void spawnWaveMinions(){
//does nothing by default
    }



    public static final String MINIONCOUNTMAX = "minioncountmax";
    public static final String MINIONCOUNTCURRRENT = "minioncountcurrent";
    public static final String ISPREPARED = "prepared";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(MINIONCOUNTCURRRENT, minionencodingsCurrent);
        bundle.put(MINIONCOUNTMAX, minionencodingsMax);
        bundle.put(ISPREPARED, isPrepared);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        minionencodingsCurrent = bundle.getInt(MINIONCOUNTCURRRENT);
        minionencodingsMax = bundle.getInt(MINIONCOUNTMAX);
        isPrepared = bundle.getBoolean(ISPREPARED);
    }
}
