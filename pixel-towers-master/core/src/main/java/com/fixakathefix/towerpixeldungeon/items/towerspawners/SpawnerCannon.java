package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCannon extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CANNON;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerCannon1();

    }

    @Override
    public int value() {
        return 60;
    }

}