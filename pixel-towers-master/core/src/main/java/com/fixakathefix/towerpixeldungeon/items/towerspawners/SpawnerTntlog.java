package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTntlog extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TNTLOG;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerTntLog.class;
    }

    @Override
    public int value() {
        return 40;
    }

}