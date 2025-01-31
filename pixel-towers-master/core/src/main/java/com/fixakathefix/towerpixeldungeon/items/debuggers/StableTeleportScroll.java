package com.fixakathefix.towerpixeldungeon.items.debuggers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.depth;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.scenes.InterlevelScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

import java.util.ArrayList;

public class StableTeleportScroll extends Item {

    {
        image = ItemSpriteSheet.READABLESCROLL_ETHER;
        defaultAction = AC_RESET;
    }
    protected int chosenLevel = 0;
    public static final String AC_RESET = "reset";
    public static final String AC_DESCEND = "descend";
    public static final String AC_ASCEND = "ascend";
    public static final String AC_PRELASTWAVE = "prelastwave";


    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_RESET);
        actions.add(AC_ASCEND);
        actions.add(AC_DESCEND);
        actions.add(AC_PRELASTWAVE);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action == AC_RESET) {
            InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.REGULAR_EXIT, depth, Dungeon.branch, null);
            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
            Game.switchScene( InterlevelScene.class );
        }
        if (action == AC_ASCEND) {
            depth--;
            InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.REGULAR_EXIT, depth, Dungeon.branch, null);
            InterlevelScene.mode = InterlevelScene.Mode.ASCEND;
            Game.switchScene(InterlevelScene.class);
        }
        if (action == AC_DESCEND) {
            depth++;
            InterlevelScene.curTransition = new LevelTransition(Dungeon.level, -1, LevelTransition.Type.REGULAR_EXIT, depth, Dungeon.branch, null);
            InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
            Game.switchScene(InterlevelScene.class);
        }
        if (action == AC_PRELASTWAVE) {
            Dungeon.level.wave=((Arena)Dungeon.level).maxWaves-1;
        }
    }
}