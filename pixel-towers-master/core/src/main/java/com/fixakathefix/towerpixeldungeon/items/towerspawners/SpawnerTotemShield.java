package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTotemShield extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TOTEMSHIELD;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerTotem.TotemShield();

    }

    @Override
    public int value() {
        return 180;
    }


}