package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRatCamp;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCamp extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CAMP;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerRatCamp.class;
    }

    @Override
    public int value() {
        return 40;
    }



}