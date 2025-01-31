package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.actors.blobs.ToxicGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Amok;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Charm;

public abstract class TowerNotliving extends Tower{
    {
        immunities.add(Charm.class);
        properties.add(Property.INORGANIC);
        immunities.add(ToxicGas.class);
        immunities.add(Amok.class);
    }
}
