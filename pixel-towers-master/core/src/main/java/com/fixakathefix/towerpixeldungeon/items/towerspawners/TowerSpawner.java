package com.fixakathefix.towerpixeldungeon.items.towerspawners;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.Statistics;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.SentientTower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.levels.Arena12;
import com.fixakathefix.towerpixeldungeon.levels.Arena14;
import com.fixakathefix.towerpixeldungeon.levels.Arena22;
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

    public Tower instanceOfTower = new TowerWall1();

    @Override
    public String desc() {
        return instanceOfTower.info();
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
            Tower tower = instanceOfTower;
            tower.pos = cell;
            GameScene.add(tower);
            if (Dungeon.level instanceof Arena22 && Dungeon.level.mode == WndModes.Modes.CHALLENGE && (tower instanceof TowerCrossbow1 || tower instanceof TowerGuard1)){
                Buff.affect(tower, Bless.class, 10000);
            }
            Statistics.towersbuilt++;
            Dungeon.level.occupyCell(tower);
            if (tower instanceof SentientTower){
                ((SentientTower)tower).defendPos(cell);
                tower.beckon(cell);
                tower.state = tower.HUNTING;
            }

        } else {
            if (towerhere) GLog.w(Messages.get(this, "towersnear"));
            super.onThrow( cell );
        }
    }
}
