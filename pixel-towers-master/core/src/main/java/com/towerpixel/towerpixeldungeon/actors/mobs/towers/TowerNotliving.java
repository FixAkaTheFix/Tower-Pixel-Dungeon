package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.blobs.ToxicGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Charm;

public abstract class TowerNotliving extends Tower{
    {
        immunities.add(Charm.class);
        properties.add(Property.INORGANIC);
        immunities.add(ToxicGas.class);
        immunities.add(Amok.class);
    }
}
