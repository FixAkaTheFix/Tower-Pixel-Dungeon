package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.Statistics;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.SentientTower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.levels.Arena12;
import com.fixakathefix.towerpixeldungeon.levels.Arena14;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Reflection;

public abstract class TowerSpawner extends Item {
    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public Class <? extends Tower> towerClass = TowerWall1.class;

    @Override
    public String desc() {
        return Reflection.newInstance(towerClass).info();
    }

    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        boolean towerhere = false;
        if (Dungeon.level instanceof Arena12 && Dungeon.level.mode== WndModes.Modes.CHALLENGE){

            for (int i : PathFinder.NEIGHBOURS25){
                if (Char.findChar(cell + i) != null && Char.findChar(cell + i) instanceof Tower){
                    towerhere = true;
                }
            }
        }
        if (Dungeon.level instanceof Arena14 && Dungeon.level.mode== WndModes.Modes.CHALLENGE){

            for (int i : PathFinder.NEIGHBOURS4){
                if (Char.findChar(cell + i) != null && Char.findChar(cell + i) instanceof Tower){
                    towerhere = true;
                }
            }
        }
        if (enemy == null && !towerhere) {
            Tower tower = Reflection.newInstance(towerClass);
            tower.pos = cell;
            GameScene.add(tower);
            Statistics.towersbuilt++;
            Dungeon.level.occupyCell(tower);
            if (tower instanceof SentientTower){
                ((SentientTower)tower).defendPos(cell);
            }

        } else {
            if (towerhere) GLog.w(Messages.get(this, "towersnear"));
            super.onThrow( cell );
        }
    }
}
