package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerGrave extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_GRAVE;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerGrave1.class;
    }

    @Override
    public int value() {
        return 50;
    }


}
