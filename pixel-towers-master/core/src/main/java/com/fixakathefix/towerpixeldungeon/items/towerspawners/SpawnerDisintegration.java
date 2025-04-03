package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerDisintegration extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_DISINTEGRATION;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerDisintegration1();
    }

    @Override
    public int value() {
        return 60;
    }

}