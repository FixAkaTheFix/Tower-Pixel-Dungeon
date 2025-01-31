package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerDartgun extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_DARTGUN;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerDartgun1.class;
    }

    @Override
    public int value() {
        return 36;
    }


}