package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.watabou.utils.Reflection;

public abstract class TowerSpawner extends Item {
    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    Class <? extends Tower> towerClass = TowerWall1.class;

    @Override
    public String desc() {
        return Reflection.newInstance(towerClass).info();
    }
}
