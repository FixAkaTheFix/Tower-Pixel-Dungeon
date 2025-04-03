package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerDartgun extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_DARTGUN;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerDartgun1();
    }

    @Override
    public int value() {
        return 36;
    }


}