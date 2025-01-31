package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCannon extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CANNON;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerCannon1.class;

    }

    @Override
    public int value() {
        return 60;
    }

}