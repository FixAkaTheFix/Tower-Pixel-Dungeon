package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerWand extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_WAND;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerWand1.class;
    }

    @Override
    public int value() {
        return 40;
    }


}
