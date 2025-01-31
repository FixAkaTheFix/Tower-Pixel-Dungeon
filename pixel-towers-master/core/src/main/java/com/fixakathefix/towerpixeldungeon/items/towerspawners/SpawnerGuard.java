package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerGuard extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_GUARD;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerGuard1.class;
    }

    @Override
    public int value() {
        return 20;
    }


}