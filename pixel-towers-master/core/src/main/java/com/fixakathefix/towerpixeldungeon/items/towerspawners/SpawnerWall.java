package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerWall extends TowerSpawner {
    {
        image = ItemSpriteSheet.TOWERSPAWNER_WALL;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerWall1.class;
    }

    @Override
    public int value() {
        return 20;
    }


}
