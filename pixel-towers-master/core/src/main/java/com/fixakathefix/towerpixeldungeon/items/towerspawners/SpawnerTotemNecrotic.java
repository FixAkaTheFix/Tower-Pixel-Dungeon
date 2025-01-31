package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTotemNecrotic extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TOTEMNECROTIC;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerTotem.TotemNecrotic.class;

    }

    @Override
    public int value() {
        return 100;
    }


}