package com.fixakathefix.towerpixeldungeon.items.artifacts;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRoseBush;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.TowerSpawner;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class RoseSeed extends Artifact {

    {
        image = ItemSpriteSheet.ARTIFACT_ROSESEED;
        stackable = true;
        defaultAction = AC_THROW;

    }

    public Class <? extends Tower> towerClass = TowerRoseBush.class;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add( AC_DROP );
        actions.add( AC_THROW );
        return actions;
    }
    @Override
    protected void onThrow( int cell ) {
        Char enemy = Actor.findChar( cell );
        boolean towerhere = false;
        if (Dungeon.depth==12 && Dungeon.level.mode== WndModes.Modes.CHALLENGE){

            for (int i : PathFinder.NEIGHBOURS25){
                if (Char.findChar(i) != null && Char.findChar(i) instanceof Tower){
                    towerhere = true;
                }
            }
        }
        if (enemy == null && !towerhere) {
            Tower tower = Reflection.newInstance(towerClass);
            tower.pos = cell;
            GameScene.add(tower);
            Dungeon.level.occupyCell(tower);

        } else {
            if (towerhere) GLog.w(Messages.get(TowerSpawner.class, "towersnear"));
            super.onThrow( cell );
        }
    }

    @Override
    public int value() {
        return 100;
    }


}
