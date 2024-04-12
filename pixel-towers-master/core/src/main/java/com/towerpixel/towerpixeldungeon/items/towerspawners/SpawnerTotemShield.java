package com.towerpixel.towerpixeldungeon.items.towerspawners;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class SpawnerTotemShield extends TowerSpawner {

    {
        image = ItemSpriteSheet.TOWERSPAWNER_TOTEMSHIELD;
        stackable = true;
        defaultAction = AC_THROW;
        towerClass = TowerTotem.TotemShield.class;

    }

    @Override
    public int value() {
        return 180;
    }

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        if (enemy == null) {
            TowerTotem.TotemShield tower = new TowerTotem.TotemShield();
            tower.pos = cell;
            GameScene.add(tower);
            Dungeon.level.occupyCell(tower);
        } else {
            super.onThrow( cell );
        }
    }
}