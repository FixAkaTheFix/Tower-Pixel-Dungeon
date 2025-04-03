package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerGrave extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_GRAVE;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerGrave1();
    }

    @Override
    public int value() {
        return 50;
    }


}
