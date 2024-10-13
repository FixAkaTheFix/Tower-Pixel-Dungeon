/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.scenes;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Chrome;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.messages.Languages;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.Archs;
import com.towerpixel.towerpixeldungeon.ui.ExitButton;
import com.towerpixel.towerpixeldungeon.ui.Icons;
import com.towerpixel.towerpixeldungeon.ui.RedButton;
import com.towerpixel.towerpixeldungeon.ui.RenderedTextBlock;
import com.towerpixel.towerpixeldungeon.ui.ScrollPane;
import com.towerpixel.towerpixeldungeon.ui.StyledButton;
import com.towerpixel.towerpixeldungeon.ui.Window;
import com.towerpixel.towerpixeldungeon.ui.changelist.ChangeButton;
import com.towerpixel.towerpixeldungeon.ui.changelist.ChangeInfo;
import com.towerpixel.towerpixeldungeon.ui.changelist.WndChanges;
import com.towerpixel.towerpixeldungeon.ui.changelist.WndChangesTabbed;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_1_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_2_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_3_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_4_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_5_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_6_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_7_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_8_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v0_9_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v1_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.changelist.v2_X_Changes;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.windows.IconTitle;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.PointF;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class TowersSelectionScene extends PixelScene {

    public static int changesSelected = 0;

    private NinePatch rightPanel; //panel/chrome type window to the right
    private ScrollPane rightScroll;//the scrolling pane for the right window
    private IconTitle towerInfoTitle;//the title of the rightscroll
    private RenderedTextBlock towerInfoBody;//the text body of the rightscroll

    private TowerEquipButton equippingBtn;//the link to the button that appears in the rightscroll to equip a tower.
    private Image towerPreview = new Image(); // the preview of the tower below its name

    private TowerSlotButton slotButton1;
    private TowerSlotButton slotButton2;
    private TowerSlotButton slotButton3;
    private TowerSlotButton slotButton4;


    private int delta = (int) Math.min(20, (Camera.main.height-36) * 0.08);
    @Override
    public void create() {
        super.create();

        int w = Camera.main.width;
        int h = Camera.main.height;

        RenderedTextBlock title = PixelScene.renderTextBlock(Messages.get(this,"uppertext"), 9);// the name above the chrome
        title.hardlight(Window.TITLE_COLOR);
        title.setPos(
                (w - title.width()) / 2f,
                (20 - title.height()) / 2f
        );
        align(title);
        add(title);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos(Camera.main.width - btnExit.width(), 0);
        add(btnExit);

        NinePatch panel = Chrome.get(Chrome.Type.TOAST);//the panel to the left

        int pw = TowerSelectionButton.WIDTH + panel.marginLeft() + panel.marginRight();
        int ph = h - 36;

        int rpw = Math.min(w - pw - 5 - 5, 200);

        {
            panel.size(pw, ph);
            panel.x = (w - pw - rpw)/2;
            panel.y = title.bottom() + 5;

            rightPanel = Chrome.get(Chrome.Type.TOAST);
            rightPanel.size(rpw, ph-delta);
            rightPanel.x = panel.x + panel.width();
            rightPanel.y = panel.y;
            add(rightPanel);

            rightScroll = new ScrollPane(new Component());
            add(rightScroll);
            rightScroll.setRect(
                    rightPanel.x + rightPanel.marginLeft(),
                    rightPanel.y + rightPanel.marginTop() - 1,
                    rightPanel.innerWidth() + 2,
                    rightPanel.innerHeight() + 2);
            rightScroll.scrollTo(0, 0);

            towerInfoTitle = new IconTitle(Icons.get(Icons.TOWER), Messages.get(this, "welcometitle"));
            towerInfoTitle.setPos(0, 1);
            towerInfoTitle.setSize(rpw, 20);

            rightScroll.content().add(towerInfoTitle);
            rightScroll.content().add(towerPreview);

            String body = Messages.get(this, "welcometext");

            towerInfoBody = PixelScene.renderTextBlock(body, 6);
            towerInfoBody.maxWidth(rpw - panel.marginHor());
            towerInfoBody.setPos(0, towerInfoTitle.bottom() + 2);
            rightScroll.content().add(towerInfoBody);

        }
        align(panel);
        add(panel);

        ArrayList<TowerSelectionButton> towerButtonList = new ArrayList<>();

        ArrayList<TowerInfo.AllTowers> unlockedTowers = new ArrayList<>();
        ArrayList<TowerInfo.AllTowers> lockedTowers = new ArrayList<>();
        ArrayList<TowerInfo.AllTowers> dungeonTowers = new ArrayList<>();



        for (TowerInfo.AllTowers t : TowerInfo.AllTowers.values()) {
            switch (TowerInfo.getTowerLock(t)){

                case UNLOCKED:unlockedTowers.add(t);break;
                case LOCKED:lockedTowers.add(t);break;
                case DUNGEON:dungeonTowers.add(t); break;
            }
        }


        towerButtonList.add(new TowerSelectionButton(TowerInfo.AllTowers.UNLOCKED));

        for (TowerInfo.AllTowers t : unlockedTowers) {
            towerButtonList.add(new TowerSelectionButton(t));
        }

        for (TowerInfo.AllTowers t : lockedTowers) {
            towerButtonList.add(new TowerSelectionButton(TowerInfo.AllTowers.LOCKED));
        }


        towerButtonList.add(new TowerSelectionButton(TowerInfo.AllTowers.DUNGEON));

        for (TowerInfo.AllTowers t : dungeonTowers) {
            towerButtonList.add(new TowerSelectionButton(t));
        }


        ScrollPane list = new ScrollPane(new Component()) {

            @Override
            public void onClick(float x, float y) {
                for (TowerSelectionButton button : towerButtonList) {
                    if (button.onClick(x, y)) {
                        return;
                    }
                }
            }

        };


        add(list);

        Component content = list.content();
        content.clear();

        int btny = 5;

        for (TowerSelectionButton button : towerButtonList) {
            button.setPos(list.left(), list.top() + btny);
            content.add(button);
            btny += button.height();
        }

        content.setSize(panel.innerWidth(), (int) Math.ceil(btny));

        list.setRect(
                panel.x + panel.marginLeft(),
                panel.y + panel.marginTop() - 1,
                panel.innerWidth() + 2,
                panel.innerHeight() + 2);
        list.scrollTo(0, 0);

        slotButton1 = new TowerSlotButton(1);
        slotButton2 = new TowerSlotButton(2);
        slotButton3 = new TowerSlotButton(3);
        slotButton4 = new TowerSlotButton(4);

        slotButton1.setSize(delta,delta);
        slotButton2.setSize(delta,delta);
        slotButton3.setSize(delta,delta);
        slotButton4.setSize(delta,delta);

        slotButton4.setPos(rightPanel.x + rightPanel.width() - slotButton4.width(), rightPanel.y + rightPanel.height());
        slotButton3.setPos(slotButton4.left()-slotButton3.width(), rightPanel.y + rightPanel.height());
        slotButton2.setPos(slotButton3.left()-slotButton2.width(), rightPanel.y + rightPanel.height());
        slotButton1.setPos(slotButton2.left()-slotButton1.width(), rightPanel.y + rightPanel.height());

        add(slotButton1);
        add(slotButton2);
        add(slotButton3);
        add(slotButton4);

        remove(equippingBtn);

        equippingBtn = new TowerEquipButton(TowerInfo.AllTowers.UNLOCKED);
        equippingBtn.setSize(rightPanel.width() - slotButton1.width()*4, delta);
        equippingBtn.setPos(rightPanel.x, rightPanel.y + rightPanel.height());
        add(equippingBtn);


        Archs archs = new Archs();
        archs.setSize(Camera.main.width, Camera.main.height);
        addToBack(archs);

        fadeIn();
    }

    private void updateTowerDescription(TowerInfo.AllTowers chosenTower) {
        if (chosenTower != null){
            towerInfoTitle.label(TowerInfo.getTowerName(chosenTower));
            towerInfoTitle.icon(Reflection.newInstance(TowerInfo.getTowerClass(chosenTower)).sprite());
            towerInfoTitle.setPos(towerInfoTitle.left(), towerInfoTitle.top());


            towerPreview.copy(new Image(TowerInfo.getTowerPreviewImage(chosenTower)));

            towerPreview.scale.set(0.8f * rightPanel.width() / towerPreview.width());
            towerPreview.x = towerInfoTitle.centerX() - towerPreview.width() / 2;
            towerPreview.y = towerInfoTitle.bottom() + 1;
            align(towerPreview);
            rightScroll.content().add(towerPreview);

            towerPreview.update();
            rightScroll.content().update();

            remove(equippingBtn);

            equippingBtn = new TowerEquipButton(chosenTower);
            equippingBtn.setSize(rightPanel.width() - slotButton1.width()*4, delta);
            equippingBtn.setPos(rightPanel.x, rightPanel.y + rightPanel.height());
            add(equippingBtn);



            String message = TowerInfo.getTowerDescription(chosenTower);

            towerInfoBody.setPos(0, towerPreview.y + towerPreview.height() + 1);
            towerInfoBody.text(message);
            rightScroll.content().setSize(rightScroll.width(), towerInfoBody.bottom() + 2);
            rightScroll.setSize(rightScroll.width(), rightScroll.height());
            rightScroll.scrollTo(0, 0);
            rightScroll.update();

        }
    }

    public static void showTowerInfo(TowerInfo.AllTowers chosenTower) {
        Scene s = ShatteredPixelDungeon.scene();
        if (s instanceof TowersSelectionScene) {
            ((TowersSelectionScene) s).updateTowerDescription(chosenTower);
        }
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(LevelSelectScene.class);
    }


    public class TowerEquipButton extends StyledButton {//the button that equips a tower into the slot
        protected TowerInfo.AllTowers equipTower;
        public TowerEquipButton(TowerInfo.AllTowers sometower) {

            super(Chrome.Type.GREY_BUTTON_TR, Messages.get(TowersSelectionScene.class, "select"),
                    9);
            if (TowerInfo.getTowerLock(sometower) == TowerInfo.Lock.UNLOCKED){
                text(Messages.get(TowersSelectionScene.class, "select"));
                equipTower = sometower;
                icon(new Image(Icons.get(Icons.ARROW)));
            } else {
                text(Messages.get(TowersSelectionScene.class, "no_select"));
                text.hardlight(0xAAAAAA);
                equipTower = null;
                icon(new Image(Icons.get(Icons.CROSS)));
            }
        }
        protected void onClick() {
            if (equipTower!= null){
                if (SPDSettings.towerslot4() == null) {
                    SPDSettings.towerslot4(equipTower);
                } else {
                    SPDSettings.towerslot4(SPDSettings.towerslot3());
                    SPDSettings.towerslot3(SPDSettings.towerslot2());
                    SPDSettings.towerslot2(SPDSettings.towerslot1());
                    SPDSettings.towerslot1(equipTower);
                }

                slotButton1.update();
                slotButton2.update();
                slotButton3.update();
                slotButton4.update();
            }
        }
        public boolean onClick(float x, float y) {
            if (inside(x, y)) {
                onClick();
                return true;
            }
            return false;
        }
    }

    public class TowerSlotButton extends StyledButton {//the button that equips a tower into the slot

        private int slotNum;

        public TowerSlotButton(int slotnum) {
            super(Chrome.Type.BLANK, ""/*Messages.get(TowersSelectionScene.class, "select")*/);
            slotNum = slotnum;
            setSize(delta,delta);
            update();
        }

        @Override
        public void update() {
            super.update();
            switch (slotNum){
                case 1: {
                    icon(new Image(Icons.get(Icons.SLOT1)));
                    Image x = TowerInfo.getTowerIcon(SPDSettings.towerslot1());
                    x.scale.set(delta/x.width());
                    if (SPDSettings.towerslot1()!=null) icon(x);
                    break;
                }
                case 2: {
                    icon(new Image(Icons.get(Icons.SLOT2)));
                    Image x = TowerInfo.getTowerIcon(SPDSettings.towerslot2());
                    x.scale.set(delta/x.width());
                    if (SPDSettings.towerslot2()!=null) icon(x);
                    break;
                }
                case 3: {
                    icon(new Image(Icons.get(Icons.SLOT3)));
                    Image x = TowerInfo.getTowerIcon(SPDSettings.towerslot3());
                    x.scale.set(delta/x.width());
                    if (SPDSettings.towerslot3()!=null) icon(x);
                    break;
                }
                case 4: {
                    icon(new Image(Icons.get(Icons.SLOT4)));
                    Image x = TowerInfo.getTowerIcon(SPDSettings.towerslot4());
                    x.scale.set(delta/x.width());
                    if (SPDSettings.towerslot4()!=null) icon(x);
                    break;
                }
            }
        }

        protected void onClick() {
            switch (slotNum){
                case 1: {
                    updateTowerDescription(SPDSettings.towerslot1());
                    break;
                }
                case 2: {
                    updateTowerDescription(SPDSettings.towerslot2());
                    break;
                }
                case 3: {
                    updateTowerDescription(SPDSettings.towerslot3());
                    break;
                }
                case 4: {
                    updateTowerDescription(SPDSettings.towerslot4());
                    break;
                }
            }
        }
        public boolean onClick(float x, float y) {
            if (inside(x, y)) {
                onClick();
                return true;
            }
            return false;
        }
    }

    public class TowerSelectionButton extends StyledButton {//the buttons with tower icons on the right

        protected Image icon;
        protected String title;
        protected String message;

        public final static int WIDTH = 27;
        public static final int HEIGHT = 27;
        protected TowerInfo.AllTowers towerch;
        protected TowerInfo.Lock lock;

        public TowerSelectionButton(TowerInfo.AllTowers tower) {
            super(Chrome.Type.BLANK, "");
            Image x = TowerInfo.getTowerIcon(tower);
            x.scale.set(1.6f);
            this.icon = x;
            this.setSize(WIDTH, HEIGHT);
            add(this.icon);
            this.lock = TowerInfo.getTowerLock(tower);
            //this.title = TowerInfo.getTowerName(tower);
            //this.title = Messages.titleCase(title);
            towerch = tower;
            layout();
        }

        protected void onClick() {
            TowersSelectionScene.showTowerInfo(towerch);
        }

        public boolean onClick(float x, float y) {
            if (inside(x, y)) {
                onClick();
                return true;
            }

            return false;
        }

        @Override
        protected void layout() {
            super.layout();
            icon.x = x;
            icon.y = y + (height - icon.height()) / 2f;
            PixelScene.align(icon);
        }
    }

}
