package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTotemHealing extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TOTEMHEALING;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerTotem.TotemHealing.class;

    }

    @Override
    public int value() {
        return 180;
    }


}