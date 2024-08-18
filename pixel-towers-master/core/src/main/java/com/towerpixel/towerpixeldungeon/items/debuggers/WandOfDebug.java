package com.towerpixel.towerpixeldungeon.items.debuggers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.artifacts.EtherealChains;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.windows.WndOptions;

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
                "Wand Of Debug测试法杖:  built mode铸造模式",
                "Choose the tile type选择放置在地块上的类型",
                "CHASM深渊",
                "EMPTY删除",
                "GRASS	草",
                "EMPTY_WELL	干涸的井",
                "WALL墙面",
                "DOOR门",
                "OPEN_DOOR开启的门",
                "ENTRANCE下层入口",
                "EXIT上层出口",
                "EMBERS	余烬",
                "LOCKED_DOOR锁上的门🚪",
                "CRYSTAL_DOOR水晶门",
                "PEDESTAL基座"
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
                "Wand Of Debug测试法杖:  built mode铸造模式",
                "Choose the tile type选择放置在地块上的类型",
                "WALL_DECO	岩金",
                "BARRICADE	木路障栅栏",
                "EMPTY_SP删除＿sp",
                "HIGH_GRASS	高草",
                "SECRET_DOOR未发现门",
                "SECRET_TRAP未发现陷阱",
                "TRAP陷阱",
                "INACTIVE_TRAP 失效陷阱",

                "EMPTY_DECO	有附着物的地块"
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
                "Wand Of Debug测试法杖:  built mode铸造模式",
                "Choose the tile type选择放置在地块上的类型",
                "LOCKED_EXIT锁着的楼层出口",
                "UNLOCKED_EXIT已解锁的楼层出口",
                "SIGN告示牌",
                "WELL井",
                "STATUE垒台柱或雕像",
                "STATUE_SP状态_SP",
                "BOOKSHELF书架",
                "ALCHEMY炼金锅",
                "WATER水",
                "FURROWED_GRASS枯萎的植被") {
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

