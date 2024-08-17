package com.towerpixel.towerpixeldungeon.items.debuggers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossOoze;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossRatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTengu;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTroll;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMW;
import com.towerpixel.towerpixeldungeon.actors.mobs.Drill;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goo;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Ghost;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.artifacts.EtherealChains;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.windows.WndOptions;

import java.util.ArrayList;

public class StaffOfBeasts extends Item {
    {
        image = ItemSpriteSheet.STAFF_BEAST;
        defaultAction = AC_SPAWN;
    }
    private static final String AC_SPAWN = "spawn";
    private static final String AC_NPCS = "npcs";
    private static final String AC_ALLYOFF = "allyoff";
    private static final String AC_ALLYON = "allyon";

    private static final String AC_BOSSES = "bosses";
    private boolean allyon = false;


    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_NPCS);
        actions.add(AC_BOSSES);
        actions.add(AC_SPAWN);
        if(allyon) actions.add(AC_ALLYON); else actions.add(AC_ALLYOFF);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero, action);
        if (action.equals(AC_NPCS)){
            chooseNPCS();
        }
        if (action.equals(AC_BOSSES)){
            chooseBosses();
        }
        if (action.equals(AC_SPAWN)){
            GameScene.selectCell(caster);
        }
        if (action.equals(AC_ALLYOFF)||action.equals(AC_ALLYON)) {
            allyon = !allyon;
        }
    }
    public Char.Alignment ali = Char.Alignment.ENEMY;

    public int chosenIndex = 0;

    private void chooseNPCS() {

        GameScene.show( new WndOptions(new ItemSprite(this),
                "Staff of beasts野兽之杖: NPC mod非玩家角色模式",
                "Choose any wanted NPC选择任何想要的npc",
                "Shopkeep店主",
                "Sadghost悲伤幽灵",
                "RK",
                "OldWMaker老牌制造商"

        ) {
            @Override
            protected void onSelect( int index ) {

                chosenIndex = index;
            }
            public void onBackPressed() {}
        });

    }
    private void chooseBosses() {

        GameScene.show( new WndOptions(new ItemSprite(this),
                "Staff of beasts野兽之杖: NPC mod非玩家角色模式",
                "Choose any wanted NPC选择任何想要的npc, X= not working如果是x选项的话就无效",
                "GOO黏咕",
                "BossTengu天狗首领",
                "DWM",
                "BossRatKing鼠王首领",
                "Remac雷马克",
                "OOZE",
                "TRICKSTER诡术士",
                "BossTroll巨魔首领",
                "DRILL3000"

        ) {
            @Override
            protected void onSelect( int index ) {

                chosenIndex = index + 20;
            }
            public void onBackPressed() {}
        });

    }



    private CellSelector.Listener caster = new CellSelector.Listener(){
        @Override
        public void onSelect(Integer target) {
            if (target != null){
                Mob z = null;
                switch (chosenIndex) {
                    case 0 : {
                        z = new Shopkeeper();
                        break;
                    }
                    case 1 : {
                        z = new Ghost();
                        break;
                    }
                    case 2 : {
                        z = new RatKing();
                        break;
                    }
                    case 3 : {
                        z = new Wandmaker();
                        break;
                    }
                    case 20 : {
                        z = new Goo();
                        break;
                    }
                    case 21 : {
                        z = new BossTengu();
                        break;
                    }
                    case 22 : {
                        z = new DMW();
                        break;
                    }
                    case 23 : {
                        z = new BossRatKing();
                        break;
                    }
                    case 24 : {
                        z = new BossNecromancer();
                        break;
                    }
                    case 25 : {
                        z = new BossOoze();
                        break;
                    }
                    case 26 : {
                        z = new GnollTrickster();
                        break;
                    }
                    case 27 : {
                        z = new BossTroll();
                        break;
                    }
                    case 28 : {
                        z = new Drill();
                        break;
                    }
                }
                if (z == null) z = new Rat();
                z.pos = target;
                if (allyon) z.alignment = Char.Alignment.ALLY; else z.alignment = Char.Alignment.ENEMY;
                if (z.pos != -1){
                    GameScene.add((Mob)z);
                }
            }
        }
        @Override
        public String prompt() {
            return Messages.get(EtherealChains.class, "prompt");
        }
    };
}

