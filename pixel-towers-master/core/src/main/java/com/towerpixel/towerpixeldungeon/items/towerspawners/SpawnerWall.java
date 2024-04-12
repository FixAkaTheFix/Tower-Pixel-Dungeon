package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

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

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        if (enemy == null) {
            TowerWall1 tower = new TowerWall1();
            tower.pos = cell;
            GameScene.add(tower);
            Dungeon.level.occupyCell(tower);
        } else {
            super.onThrow( cell );
        }
    }
}
