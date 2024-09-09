package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTntlog extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TNTLOG;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerTntLog.class;
    }

    @Override
    public int value() {
        return 40;
    }

}