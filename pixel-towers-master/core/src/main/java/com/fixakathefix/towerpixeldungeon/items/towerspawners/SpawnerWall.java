package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerWall extends TowerSpawner {
    {
        image = ItemSpriteSheet.TOWERSPAWNER_WALL;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerWall1();
    }

    @Override
    public int value() {
        return 20;
    }


}
