package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

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

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        if (enemy == null) {
            TowerTotem.TotemNecrotic tower = new TowerTotem.TotemNecrotic();
            tower.pos = cell;
            GameScene.add(tower);
            Dungeon.level.occupyCell(tower);
        } else {
            super.onThrow( cell );
        }
    }
}