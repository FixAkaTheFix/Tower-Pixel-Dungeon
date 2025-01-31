package com.fixakathefix.towerpixeldungeon.scenes;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.NightmareRiftSprite;
import com.fixakathefix.towerpixeldungeon.ui.Archs;
import com.fixakathefix.towerpixeldungeon.ui.ExitButton;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.StyledButton;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.fixakathefix.towerpixeldungeon.windows.WndTitledMessage;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;

public class GamemodeSelectionScene extends PixelScene{//WIP


    public static int chosenBranch = 0;//basic is normal mode. Chosen and sent to the descend().
    private static IconTextVerticalButton chosenBtnLink = null;// a link to the button which is chosen.
    private static Group modeButtons;


    @Override
    public void create() {

        super.create();

        //uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;




        StyledButton startBtn = new StyledButton(Chrome.Type.GREY_BUTTON_TR, Messages.get(GamemodeSelectionScene.class, "continue"), 15) {
            @Override
            protected void onClick() {
                Game.switchScene(LevelSelectScene.class);
            }

            @Override
            public void update() {
                icon(new Image(Icons.get(Icons.STAIRS)));
                super.update();
            }
        };
        startBtn.visible = startBtn.active = false;
        IconTextVerticalButton storyModeBtn = new IconTextVerticalButton(Messages.get(GamemodeSelectionScene.class, "story_name"), 0, Icons.get(Icons.STORYMODE)) {
            @Override
            protected void onClick() {
                super.onClick();
                if (GamemodeSelectionScene.chosenBtnLink != this) {
                    if (chosenBranch != btnBranch) LevelSelectScene.chosenLevel = 1;
                    chosenBranch = btnBranch;
                    GamemodeSelectionScene.chosenBtnLink = this;
                    startBtn.visible = startBtn.active = true;
                    GamemodeSelectionScene.modeButtons.update();
                    Game.scene().update();
                } else
                    ShatteredPixelDungeon.scene().add(new WndTitledMessage(Icons.get(Icons.INFO), Messages.get(GamemodeSelectionScene.class, "story_name"), Messages.get(GamemodeSelectionScene.class, "story_desc")));
            }
        };
        IconTextVerticalButton endlessModeBtn = new IconTextVerticalButton(Messages.get(GamemodeSelectionScene.class, "endless_name"), 1, Icons.get(Icons.ENDLESSMODE)) {
            @Override
            protected void onClick() {
                super.onClick();
                if (SPDSettings.maxlevelunlocked() >= 6){
                    if (GamemodeSelectionScene.chosenBtnLink != this) {
                        if (chosenBranch != btnBranch) LevelSelectScene.chosenLevel = 1;
                        chosenBranch = btnBranch;
                        GamemodeSelectionScene.chosenBtnLink = this;
                        startBtn.visible = startBtn.active = true;
                        GamemodeSelectionScene.modeButtons.update();
                        Game.scene().update();
                    } else
                        ShatteredPixelDungeon.scene().add(new WndTitledMessage(Icons.get(Icons.INFO), Messages.get(GamemodeSelectionScene.class, "endless_name"), Messages.get(GamemodeSelectionScene.class, "endless_desc")));

                }else ShatteredPixelDungeon.scene().add(new WndTitledMessage(Icons.get(Icons.REDLOCK), Messages.get(GamemodeSelectionScene.class, "endless_name"), Messages.get(GamemodeSelectionScene.class, "endless_locked")));
            }
        };
        IconTextVerticalButton gaunletModeBtn = new IconTextVerticalButton(Messages.get(GamemodeSelectionScene.class, "gaunlet_name"), 2, Icons.get(Icons.REDPORTAL)) {
            @Override
            protected void onClick() {
                super.onClick();
                if (SPDSettings.maxlevelunlocked() >= 10){
                    if (GamemodeSelectionScene.chosenBtnLink != this) {
                        if (chosenBranch != btnBranch) LevelSelectScene.chosenLevel = 1;
                        chosenBranch = btnBranch;
                        GamemodeSelectionScene.chosenBtnLink = this;
                        startBtn.visible = startBtn.active = false;
                        GamemodeSelectionScene.modeButtons.update();
                        Game.scene().update();
                    } else
                        ShatteredPixelDungeon.scene().add(new WndTitledMessage(new NightmareRiftSprite(), Messages.get(GamemodeSelectionScene.class, "gaunlet_name"), Messages.get(GamemodeSelectionScene.class, "gaunlet_desc")));
                } else ShatteredPixelDungeon.scene().add(new WndTitledMessage(Icons.get(Icons.REDLOCK), Messages.get(GamemodeSelectionScene.class, "gaunlet_name"), Messages.get(GamemodeSelectionScene.class, "gaunlet_locked")));
            }
        };

        IconTextVerticalButton vsModeBtn = new IconTextVerticalButton(Messages.get(GamemodeSelectionScene.class, "vs_name"), 3, Icons.get(Icons.VSMODE)) {
            @Override
            protected void onClick() {
                super.onClick();
                if (GamemodeSelectionScene.chosenBtnLink != this) {
                    if (chosenBranch != btnBranch) LevelSelectScene.chosenLevel = 1;
                    chosenBranch = btnBranch;
                    GamemodeSelectionScene.chosenBtnLink = this;
                    startBtn.visible = startBtn.active = false;
                    GamemodeSelectionScene.modeButtons.update();
                    Game.scene().update();
                } else
                    ShatteredPixelDungeon.scene().add(new WndTitledMessage(Icons.get(Icons.REDLOCK), Messages.get(GamemodeSelectionScene.class, "vs_name"), Messages.get(GamemodeSelectionScene.class, "vs_desc")));
            }
        };


        RenderedTextBlock title = PixelScene.renderTextBlock(Messages.get(GamemodeSelectionScene.class,"title"), 12);// the name above the chrome
        title.hardlight(Window.TITLE_COLOR);
        title.setPos(
                (w - title.width()) / 2f,
                10
        );
        align(title);

        Archs archs = new Archs();
        archs.setSize( w, h );
        add( archs );

        modeButtons = new Group();
        modeButtons.add(storyModeBtn);
        modeButtons.add(endlessModeBtn);
        //modeButtons.add(vsModeBtn);
        //modeButtons.add(gaunletModeBtn);
        modeButtons.add(startBtn);




        storyModeBtn.setSize(w*0.2f, h*0.25f);
        endlessModeBtn.setSize(w*0.2f, h*0.25f);
        gaunletModeBtn.setSize(w*0.2f, h*0.25f);

        if (landscape()){
            storyModeBtn.setPos(w*0.33f - storyModeBtn.width()/2,0.5f*h - startBtn.height() - storyModeBtn.height()/2);
            endlessModeBtn.setPos(w*0.66f - endlessModeBtn.width()/2,0.5f*h- startBtn.height() - endlessModeBtn.height()/2);
        } else {
            storyModeBtn.setPos(w*0.5f - storyModeBtn.width()/2,0.33f*h - startBtn.height() - storyModeBtn.height()/2);
            endlessModeBtn.setPos(w*0.5f - endlessModeBtn.width()/2,0.66f*h- startBtn.height() - endlessModeBtn.height()/2);
        }

        //gaunletModeBtn.setPos(w*0.66f - gaunletModeBtn.width()/2,0.66f*h - startBtn.height() - gaunletModeBtn.height()/2);
        //vsModeBtn.setSize(w*0.2f, h*0.25f);
        //vsModeBtn.setPos(w*0.33f - vsModeBtn.width()/2,0.65f*h - startBtn.height()/2 - vsModeBtn.height()/2);



        startBtn.setSize(w/2f, 30);
        startBtn.setPos(w*0.5f - startBtn.width()/2, h*0.9f- startBtn.height()/2);

        ExitButton btnExit = new ExitButton();
        btnExit.setPos( w - btnExit.width(), 0 );


        add( btnExit );
        add(endlessModeBtn);
        add(storyModeBtn);
        add(title);
        //add(vsModeBtn);
        //add(gaunletModeBtn);
        add(startBtn);


        fadeIn();
    }



    private static class IconTextVerticalButton extends StyledButton {

        int btnBranch = 0;
        public IconTextVerticalButton(String label, int branch, Image icon) {
            super(Chrome.Type.BLANK, label, 15);
            icon(icon);
            btnBranch = branch;
        }

        @Override
        public void update() {
            super.update();
            icon.scale.set(0.8f*width()/Math.max(icon.width, icon.height));
            icon(icon);
            if (GamemodeSelectionScene.chosenBtnLink == this) {
                this.alpha(1f);

            } else this.alpha(0.6f);
        }

        @Override
        protected void layout() {
            super.layout();
            icon.x = x + (width() - icon.width())/2;
            icon.y = y + (height() - text.height() - icon.height())/2;
            text.setPos( icon.center().x-text.width()/2, icon.y + icon.height() + 3);
            PixelScene.align(icon);
            PixelScene.align(text);

        }


    }

}
