package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCannon extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CANNON;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerCannon1.class;

    }

    @Override
    public int value() {
        return 60;
    }

}