package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerCrossbow extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_CROSSBOW;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerCrossbow1.class;
    }

    @Override
    public int value() {
        return 40;
    }

}
