package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerWand extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_WAND;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerWand1();
    }

    @Override
    public int value() {
        return 40;
    }


}
