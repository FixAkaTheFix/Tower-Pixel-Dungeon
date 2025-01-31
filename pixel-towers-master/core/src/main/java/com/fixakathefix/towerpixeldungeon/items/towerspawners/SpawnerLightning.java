package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerLightning extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_LIGHTNING;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerLightning1.class;
    }

    @Override
    public int value() {
        return 40;
    }


}