package com.fixakathefix.towerpixeldungeon.items.debuggers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.artifacts.EtherealChains;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;

import java.util.ArrayList;

public class WandOfDebug extends Item {
    {
        image = ItemSpriteSheet.WAND_DEBUG;
        defaultAction = AC_BUILD;
    }
    private static final String AC_BUILD = "build";
    private static final String AC_BUILDCHOOSE = "buildchoose";
    private static final String AC_BUILDCHOOSE2 = "buildchoose2";
    private static final String AC_BUILDCHOOSE3 = "buildchoose3";
    private static final String AC_SPAWN = "spawn";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_BUILDCHOOSE);
        actions.add(AC_BUILDCHOOSE2);
        actions.add(AC_BUILDCHOOSE3);
        actions.add(AC_BUILD);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_BUILDCHOOSE)){
            chooseTile();
        }
        if (action.equals(AC_BUILDCHOOSE2)){
            chooseTile2();
        }
        if (action.equals(AC_BUILDCHOOSE3)){
            chooseTile3();
        }
        if (action.equals(AC_BUILD)){
            GameScene.selectCell(caster);
        }

    }
    public int chosenIndex;
    private int chooseTile() {
        GameScene.show( new WndOptions(new ItemSprite(this),
                "Wand Of Debug:  built mode",
                "Choose the tile type",
                "CHASM",
                "EMPTY",
                "GRASS	",
                "EMPTY_WELL	",
                "WALL",
                "DOOR",
                "OPEN_DOOR",
                "ENTRANCE",
                "EXIT",
                "EMBERS	",
                "LOCKED_DOOR",
                "CRYSTAL_DOOR",
                "PEDESTAL"
        ) {
            @Override
            protected void onSelect( int index ) {
                chosenIndex = index;
            }
            public void onBackPressed() {}
        });
        return 0;
    }
    private int chooseTile2() {
        GameScene.show( new WndOptions(new ItemSprite(this),
                "Wand Of Debug:  built mode",
                "Choose the tile type",
                "WALL_DECO	",
                "BARRICADE	",
                "EMPTY_SP",
                "HIGH_GRASS	",
                "SECRET_DOOR",
                "SECRET_TRAP",
                "TRAP",
                "INACTIVE_TRAP ",

                "EMPTY_DECO	"
                ) {
            @Override
            protected void onSelect( int index ) {
                chosenIndex = index + 12;
            }
            public void onBackPressed() {}
        });
        return 0;
    }
    private int chooseTile3() {
        GameScene.show( new WndOptions(new ItemSprite(this),
                "Wand Of Debug:  built mode",
                "Choose the tile type",
                "LOCKED_EXIT",
                "UNLOCKED_EXIT",
                "SIGN",
                "WELL",
                "STATUE",
                "STATUE_SP",
                "BOOKSHELF",
                "ALCHEMY",
                "WATER",
                "FURROWED_GRASS") {
            @Override
            protected void onSelect( int index ) {
                chosenIndex = index + 21;
            }
            public void onBackPressed() {}
        });
        return 0;
    }


    private CellSelector.Listener caster = new CellSelector.Listener(){
        @Override
        public void onSelect(Integer target) {
            if (target != null){
                Dungeon.level.map[target] = chosenIndex;
                Level.set( target, chosenIndex);
                GameScene.updateMap( target );
            }
        }
        @Override
        public String prompt() {
            return Messages.get(EtherealChains.class, "prompt");
        }
    };
}

