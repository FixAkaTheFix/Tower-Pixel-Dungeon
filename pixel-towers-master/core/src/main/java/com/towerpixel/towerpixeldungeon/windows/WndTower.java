package com.towerpixel.towerpixeldungeon.windows;


import static com.towerpixel.towerpixeldungeon.Dungeon.gold;
import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.items.Item.updateQuickslot;

import com.towerpixel.towerpixeldungeon.Chrome;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.SentientTower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerPylon;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerPylonBroken;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.ui.RedButton;
import com.towerpixel.towerpixeldungeon.utils.GLog;

import java.util.ArrayList;

public class WndTower extends WndInfoMob {
    private static final float BUTTON_HEIGHT	= 16;

    private static final float GAP	= 2;

    public WndTower(Tower tower) {

        super(tower);

        float y = height;

        if (Dungeon.hero.isAlive()) {
            {
                y += GAP;
                ArrayList<RedButton> buttons = new ArrayList<>();
                RedButton sellbtn = new RedButton(Messages.get(this, "sellbutton", Math.round(tower.cost * 0.5f)), 8, Chrome.Type.YELLOW_BUTTON) {
                    @Override
                    protected void onClick() {
                        hide();
                        hero.spendAndNext(1);
                        tower.sell();
                    }
                };
                if (!(tower instanceof TowerPylon || tower instanceof TowerPylonBroken || tower instanceof TowerTotem)) {//you cant sell special towers
                    sellbtn.setSize(sellbtn.reqWidth(), BUTTON_HEIGHT);
                    if (tower.sellable) buttons.add(sellbtn);
                }
                String name1 = "XXS";
                if (tower.upgCount>1)  name1 = tower.upgradeTower1().getClass().getSimpleName();else name1 = "upgradebutton";


                RedButton upgbtn1 = new RedButton(Messages.get(this, name1, tower.upgrade1Cost), 8, Chrome.Type.GREEN_BUTTON) {
                    @Override
                    protected void onClick() {
                        hide();
                        if (gold >= tower.upgrade1Cost) {
                            gold -= tower.upgrade1Cost;
                            tower.upgrade1();
                        } else {
                            GLog.w("Not enough money!");
                        }
                        updateQuickslot();
                    }
                };
                upgbtn1.setSize(upgbtn1.reqWidth(), BUTTON_HEIGHT);

                String name2;
                if (tower.upgCount>1) name2 = tower.upgradeTower2().getClass().getSimpleName(); else name2 = "upgradebutton";

                RedButton upgbtn2 = new RedButton(Messages.get(this, name2, tower.upgrade2Cost), 8, Chrome.Type.GREEN_BUTTON) {
                    @Override
                    protected void onClick() {
                        hide();
                        if (gold >= tower.upgrade2Cost) {
                            gold -= tower.upgrade2Cost;
                            tower.upgrade2();
                        } else {
                            GLog.w("Not enough money!");
                        }
                        updateQuickslot();
                    }
                };
                upgbtn2.setSize(upgbtn2.reqWidth(), BUTTON_HEIGHT);

                String name3;
                if (tower.upgCount>1) name3 = tower.upgradeTower3().getClass().getSimpleName(); else name3 = "upgradebutton3";

                RedButton upgbtn3 = new RedButton(Messages.get(this,name3, tower.upgrade3Cost), 8, Chrome.Type.GREEN_BUTTON) {
                    @Override
                    protected void onClick() {
                        hide();
                        if (gold >= tower.upgrade3Cost) {
                            gold -= tower.upgrade3Cost;
                            tower.upgrade3();
                        } else {
                            GLog.w("Not enough money!");
                        }
                        updateQuickslot();
                    }
                };
                upgbtn3.setSize(upgbtn3.reqWidth(), BUTTON_HEIGHT);

                if (!(tower instanceof TowerPylon || tower instanceof TowerPylonBroken || tower instanceof TowerTotem || !tower.sellable)) {add(sellbtn);}
                if (tower.upgCount>=1 && tower.upgradeLevel<=Dungeon.depth) {buttons.add(upgbtn1);add(upgbtn1);}
                if (tower.upgCount>=2 && tower.upgradeLevel<=Dungeon.depth) {buttons.add(upgbtn2);add(upgbtn2);}
                if (tower.upgCount>=3 && tower.upgradeLevel<=Dungeon.depth) {buttons.add(upgbtn3);add(upgbtn3);}


                RedButton moveSentientTowerButton = new RedButton(Messages.get(this,"towerguardmove"), 8, Chrome.Type.RED_BUTTON) {
                    @Override
                    protected void onClick() {
                        hide();
                        GameScene.selectCell(new CellSelector.Listener() {
                            @Override
                            public void onSelect(Integer cell) {
                                if (cell == null) return;
                                ((TowerGuard1) tower).directTocell(cell);
                            }

                            @Override
                            public String prompt() {
                                return Messages.get(WndTower.class,"towerguardmove_prompt");
                            }
                        });
                    }
                };
                moveSentientTowerButton.setSize(moveSentientTowerButton.reqWidth(), BUTTON_HEIGHT);

                if (tower instanceof SentientTower){
                    {buttons.add(moveSentientTowerButton);add(moveSentientTowerButton);}
                }


                y = layoutButtons(buttons, width, y);
            }
        }

        resize( width, (int)(y) );
    }

    private static float layoutButtons(ArrayList<RedButton> buttons, float width, float y){
        ArrayList<RedButton> curRow = new ArrayList<>();
        float widthLeftThisRow = width;

        while( !buttons.isEmpty() ){
            RedButton btn = buttons.get(0);

            widthLeftThisRow -= btn.width();
            if (curRow.isEmpty()) {
                curRow.add(btn);
                buttons.remove(btn);
            } else {
                widthLeftThisRow -= 1;
                if (widthLeftThisRow >= 0) {
                    curRow.add(btn);
                    buttons.remove(btn);
                }
            }

            //layout current row. Currently forces a max of 3 buttons but can work with more
            if (buttons.isEmpty() || widthLeftThisRow <= 0 || curRow.size() >= 3){

                //re-use this variable for laying out the buttons
                widthLeftThisRow = width - (curRow.size()-1);
                for (RedButton b : curRow){
                    widthLeftThisRow -= b.width();
                }

                //while we still have space in this row, find the shortest button(s) and extend them
                while (widthLeftThisRow > 0){

                    ArrayList<RedButton> shortest = new ArrayList<>();
                    RedButton secondShortest = null;

                    for (RedButton b : curRow) {
                        if (shortest.isEmpty()) {
                            shortest.add(b);
                        } else {
                            if (b.width() < shortest.get(0).width()) {
                                secondShortest = shortest.get(0);
                                shortest.clear();
                                shortest.add(b);
                            } else if (b.width() == shortest.get(0).width()) {
                                shortest.add(b);
                            } else if (secondShortest == null || secondShortest.width() > b.width()){
                                secondShortest = b;
                            }
                        }
                    }

                    float widthToGrow;

                    if (secondShortest == null){
                        widthToGrow = widthLeftThisRow / shortest.size();
                        widthLeftThisRow = 0;
                    } else {
                        widthToGrow = secondShortest.width() - shortest.get(0).width();
                        if ((widthToGrow * shortest.size()) >= widthLeftThisRow){
                            widthToGrow = widthLeftThisRow / shortest.size();
                            widthLeftThisRow = 0;
                        } else {
                            widthLeftThisRow -= widthToGrow * shortest.size();
                        }
                    }

                    for (RedButton toGrow : shortest){
                        toGrow.setRect(0, 0, toGrow.width()+widthToGrow, toGrow.height());
                    }
                }

                //finally set positions
                float x = 0;
                for (RedButton b : curRow){
                    b.setRect(x, y, b.width(), b.height());
                    x += b.width() + 1;
                }

                //move to next line and reset variables
                y += BUTTON_HEIGHT+1;
                widthLeftThisRow = width;
                curRow.clear();

            }

        }

        return y - 1;
    }




}
