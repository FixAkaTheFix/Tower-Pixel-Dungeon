package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTotemAttack extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TOTEMATTACK;
        stackable = true;
        defaultAction = AC_THROW;
        instanceOfTower = new TowerTotem.TotemAttack();
    }

    @Override
    public int value() {
        return 200;
    }

}