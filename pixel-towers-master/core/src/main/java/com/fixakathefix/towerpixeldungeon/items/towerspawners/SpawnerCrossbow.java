package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCrossbow extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CROSSBOW;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerCrossbow1();
    }

    @Override
    public int value() {
        return 40;
    }

}
