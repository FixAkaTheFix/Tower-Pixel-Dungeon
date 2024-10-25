package com.towerpixel.towerpixeldungeon.scenes;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Chrome;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.GamesInProgress;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.Statistics;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.ui.ActionIndicator;
import com.towerpixel.towerpixeldungeon.ui.ExitButton;
import com.towerpixel.towerpixeldungeon.ui.IconButton;
import com.towerpixel.towerpixeldungeon.ui.Icons;
import com.towerpixel.towerpixeldungeon.ui.RenderedTextBlock;
import com.towerpixel.towerpixeldungeon.ui.StyledButton;
import com.towerpixel.towerpixeldungeon.ui.Window;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.NoosaScriptNoLighting;
import com.watabou.noosa.SkinnedBlock;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.DeviceCompat;

public class LevelSelectScene extends PixelScene {

    private Image mainWindow;
    private Image scheme;
    //UI elements
    private RenderedTextBlock levelName;
    private RenderedTextBlock levelDesc;
    private RenderedTextBlock levelKnowledge;
    private RenderedTextBlock levelNum;
    private RenderedTextBlock lockedText;
    private StyledButton startBtn;
    private IconButton arrowLeftButton;
    private IconButton arrowRightButton;
    private IconButton btnExit;

    private IconButton btnTowers;

    private IconButton btnModes;

    int chosenLevel = Statistics.chosenLevel;
    int gamemode = Dungeon.gamemode;

    public String backgroundArt() {
        if (gamemode == 0) switch (chosenLevel) {
            default:
                return Assets.Splashes.MAINWINDOW;
            case 1:
                return Assets.Splashes.ARENA1;
            case 2:
                return Assets.Splashes.ARENA2;
            case 3:
                return Assets.Splashes.ARENA3;
            case 4:
                return Assets.Splashes.ARENA4;
            case 5:
                return Assets.Splashes.ARENA5;
            case 6:
                return Assets.Splashes.ARENA6;
            case 7:
                return Assets.Splashes.ARENA7;
            case 8:
                return Assets.Splashes.ARENA8;
            case 9:
                return Assets.Splashes.ARENA9;
            case 10:
                return Assets.Splashes.ARENA10;
            case 11:
                return Assets.Splashes.ARENA11;
            case 12:
                return Assets.Splashes.ARENA12;
            case 13:
                return Assets.Splashes.ARENA13;
            case 14:
                return Assets.Splashes.ARENA14;
            case 15:
                return Assets.Splashes.ARENA15;
            case 16:
                return Assets.Splashes.ARENA16;
            case 17:
                return Assets.Splashes.ARENA17;
            case 18:
                return Assets.Splashes.ARENA18;
            case 19:
                return Assets.Splashes.ARENA19;
            case 20:
                return Assets.Splashes.ARENA20;
            /*case 21:
                return Assets.Splashes.ARENA21;
            case 22:
                return Assets.Splashes.ARENA22;
            case 23:
                return Assets.Splashes.ARENA23;
            case 24:
                return Assets.Splashes.ARENA24;
            case 25:
                return Assets.Splashes.ARENA25;
*/
        } else if (gamemode == 1) switch (chosenLevel) {
            default:
                return Assets.Splashes.MAINWINDOW;
        }
        return Assets.Splashes.MAINWINDOW;
    }

    public String mainWindow() {// for future, if i want to change the window view for some levels
        return backgroundArt();
    }

    @Override
    public void create() {

        switch (SPDSettings.mode()){
            case NORMAL: default: {
                Music.INSTANCE.playTracks(
                        new String[]{Assets.Music.GNOLL_GROTTO},
                        new float[]{1},
                        false);

                break;
            }
            case HARDMODE: {
                Music.INSTANCE.playTracks(
                        new String[]{Assets.Music.HALLS_BOSS},
                        new float[]{1},
                        false);
                break;
            }
            case CHALLENGE: {
                Music.INSTANCE.playTracks(
                        new String[]{Assets.Music.CITY_1, Assets.Music.CITY_2},
                        new float[]{1,1},
                        false);
                break;
            }
        }

        super.create();



        int maxLevelUnlockedForMode;
        switch (SPDSettings.mode()){
            case NORMAL: default:{
                maxLevelUnlockedForMode = SPDSettings.maxlevelunlocked();
                break;
            }
            case HARDMODE: {
                maxLevelUnlockedForMode = SPDSettings.maxlevelunlockedHardmode();
                if (maxLevelUnlockedForMode>SPDSettings.maxlevelunlocked()) maxLevelUnlockedForMode = SPDSettings.maxlevelunlocked();
                break;
            }
            case CHALLENGE: {
                maxLevelUnlockedForMode = SPDSettings.maxlevelunlockedChalmode();
                if (maxLevelUnlockedForMode>SPDSettings.maxlevelunlocked()) maxLevelUnlockedForMode = SPDSettings.maxlevelunlocked();
                break;
            }
        }



        int xcentre = Math.round(Camera.main.width / 2f);
        int ycentre = Math.round(Camera.main.height / 2f);
        float leftArea = Math.max(50, Camera.main.width / 5f);//x
        float rightArea = 0.8f * Camera.main.width;//x

        float upperArea = Math.max(50, Camera.main.height / 8f);//x
        float lowerArea = 0.8f * Camera.main.height;//x

        float uiHeight = Math.min(Camera.main.height - 20, 300);//y
        float uiSpacing = (uiHeight - 120) / 2f;//y

        String loadingAsset;

        loadingAsset = Assets.Interfaces.ARCS_FG;

        switch (SPDSettings.mode()){
            case NORMAL: default:{
                if (chosenLevel >= 1 && chosenLevel <= 5)
                    loadingAsset = Assets.Interfaces.LOADING_SEWERS;
                else if (chosenLevel >= 6 && chosenLevel <= 10)
                    loadingAsset = Assets.Interfaces.LOADING_PRISON;
                else if (chosenLevel >= 11 && chosenLevel <= 15)
                    loadingAsset = Assets.Interfaces.LOADING_CAVES;
                else if (chosenLevel >= 16 && chosenLevel <= 20)
                    loadingAsset = Assets.Interfaces.LOADING_CITY;
                else if (chosenLevel >= 21 && chosenLevel <= 25)
                    loadingAsset = Assets.Interfaces.LOADING_HALLS;
                break;
            }
            case HARDMODE:{
                if (chosenLevel >= 1 && chosenLevel <= 5)
                    loadingAsset = Assets.Interfaces.LOADING_SEWERS_HARD;
                else if (chosenLevel >= 6 && chosenLevel <= 10)
                    loadingAsset = Assets.Interfaces.LOADING_PRISON_HARD;
                else if (chosenLevel >= 11 && chosenLevel <= 15)
                    loadingAsset = Assets.Interfaces.LOADING_CAVES_HARD;
                else if (chosenLevel >= 16 && chosenLevel <= 20)
                    loadingAsset = Assets.Interfaces.LOADING_CITY_HARD;
                else if (chosenLevel >= 21 && chosenLevel <= 25)
                    loadingAsset = Assets.Interfaces.LOADING_HALLS_HARD;
                break;
            }
            case CHALLENGE: {
                if (chosenLevel >= 1 && chosenLevel <= 5)
                    loadingAsset = Assets.Interfaces.LOADING_SEWERS_CHAL;
                else if (chosenLevel >= 6 && chosenLevel <= 10)
                    loadingAsset = Assets.Interfaces.LOADING_PRISON_CHAL;
                else if (chosenLevel >= 11 && chosenLevel <= 15)
                    loadingAsset = Assets.Interfaces.LOADING_CAVES_CHAL;
                else if (chosenLevel >= 16 && chosenLevel <= 20)
                    loadingAsset = Assets.Interfaces.LOADING_CITY_CHAL;
                else if (chosenLevel >= 21 && chosenLevel <= 25)
                    loadingAsset = Assets.Interfaces.LOADING_HALLS_CHAL;
                break;
            }
        }





        SkinnedBlock bg = new SkinnedBlock(Camera.main.width, Camera.main.height, loadingAsset) {
            @Override
            protected NoosaScript script() {
                return NoosaScriptNoLighting.get();
            }

            @Override
            public void draw() {
                Blending.disable();
                super.draw();
                Blending.enable();
            }

            @Override
            public void update() {
                super.update();
                offset(0, Game.elapsed);
            }
        };
        bg.scale(4, 4);
        bg.autoAdjust = true;
        add(bg);

        Image im = new Image(TextureCache.createGradient(0xAA000000, 0xBB000000, 0xCC000000, 0xDD000000, 0xFF000000)) {
            @Override
            public void update() {
                super.update();
                aa = 0;
            }
        };
        im.angle = 90;
        im.x = Camera.main.width;
        im.scale.x = Camera.main.height / 5f;
        im.scale.y = Camera.main.width;
        add(im);

        mainWindow = new Image(chosenLevel <= maxLevelUnlockedForMode || DeviceCompat.isDebug() ? mainWindow() : Assets.Splashes.LOCKED) {
            @Override
            public void update() {

                    if (rm > 1f) {
                        rm -= Game.elapsed;
                        gm = bm = rm;
                    } else {
                        rm = gm = bm = 1;
                    }

            }
        };

        mainWindow.scale.set(0.5f * Math.round(Camera.main.height / mainWindow.height));
        mainWindow.x = (Camera.main.width - mainWindow.width()) / 2f;
        mainWindow.y = (Camera.main.height - mainWindow.height()) / 2f;
        PixelScene.align(mainWindow);
        add(mainWindow);


        String arenaname = "arena" + chosenLevel + "name";
        String arenadesc = "arena" + chosenLevel + "desc";
        String arenaknowledge = "arena" + chosenLevel + "knowledge";
        String arenachal = "arena" + chosenLevel + "challenge";

        levelName = PixelScene.renderTextBlock(Messages.get(this, arenaname), 11);
        levelName.hardlight(Window.TITLE_COLOR);
        levelName.align(RenderedTextBlock.CENTER_ALIGN);


        align(levelName);
        add(levelName);

        if(SPDSettings.mode()== WndModes.Modes.CHALLENGE){
            levelDesc = PixelScene.renderTextBlock(Messages.get(this, arenachal), Messages.get(this, arenachal).length() > 300 ? 5 : Messages.get(this, arenachal).length() > 230 ? 6 : Messages.get(this, arenachal).length() > 130 ? 7 : 8);
        } else levelDesc = PixelScene.renderTextBlock(Messages.get(this, arenadesc), Messages.get(this, arenadesc).length() > 300 ? 5 : Messages.get(this, arenadesc).length() > 230 ? 6 : Messages.get(this, arenadesc).length() > 130 ? 7 : 8);

        levelDesc.hardlight(Window.WHITE);
        levelDesc.align(RenderedTextBlock.CENTER_ALIGN);
        levelDesc.maxWidth(100);
        levelDesc.setSize(levelDesc.maxWidth(), mainWindow.height);

        levelName.setPos(leftArea - levelName.width() / 2f, mainWindow.center().y - levelDesc.height() / 2f - levelName.height());
        levelDesc.setPos(leftArea - levelDesc.width() / 2f, mainWindow.center().y - levelDesc.height() / 2f + levelName.height());

        align(levelDesc);
        add(levelDesc);

        levelKnowledge = PixelScene.renderTextBlock(Messages.get(this, arenaknowledge), Messages.get(this, arenaknowledge).length() > 150 ? 6 : (arenaknowledge).length() > 100 ? 7 : 8);

        levelKnowledge.hardlight(Window.WHITE);
        levelKnowledge.align(RenderedTextBlock.CENTER_ALIGN);
        levelKnowledge.maxWidth(100);
        levelKnowledge.setPos(rightArea - (levelKnowledge.maxWidth() / 2f), mainWindow.center().y - (levelKnowledge.height() / 2f));
        levelKnowledge.setSize(mainWindow.width, mainWindow.height);


        align(levelKnowledge);
        add(levelKnowledge);

        lockedText = PixelScene.renderTextBlock(Messages.get(this, "lockedtext"), 10);

        lockedText.hardlight(Window.WHITE);
        lockedText.align(RenderedTextBlock.CENTER_ALIGN);
        lockedText.maxWidth(100);
        lockedText.setSize(lockedText.maxWidth(), mainWindow.height);

        lockedText.setPos(leftArea - lockedText.width() / 2f, mainWindow.center().y - lockedText.height() / 2f);

        align(lockedText);
        add(lockedText);
        if (chosenLevel <= maxLevelUnlockedForMode || DeviceCompat.isDebug()) {
            lockedText.visible = lockedText.active = false;
        } else {
            levelName.visible = levelName.active = false;
            levelDesc.visible = levelDesc.active = false;
            levelKnowledge.visible = levelKnowledge.active = false;
        }
        levelNum = PixelScene.renderTextBlock(Integer.toString(chosenLevel), 18);
        String specialName = "";
        if (chosenLevel < 1) {
            switch (chosenLevel) {
                case 0:
                    specialName = "Tutorial";
                    break;
                case -1:
                    specialName = "Endless mode";
                    break;
            }
            levelNum.text(specialName);
        }

        levelNum.hardlight(Window.WHITE);
        levelNum.align(RenderedTextBlock.LEFT_ALIGN);
        levelNum.setPos(mainWindow.center().x - (levelNum.width() / 2f), lowerArea);

        align(levelNum);
        add(levelNum);


        startBtn = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "") {
            @Override
            protected void onClick() {
                super.onClick();

                Dungeon.hero = null;
                ActionIndicator.action = null;
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

                Game.switchScene(InterlevelScene.class);
            }
        };
        startBtn.icon(Icons.get(Icons.ENTER));
        startBtn.textColor(Window.TITLE_COLOR);
        startBtn.text(Messages.titleCase(Messages.get(this, "start")));
        startBtn.setSize(80, 20);
        startBtn.setPos(levelNum.centerX() - (startBtn.width() / 2f), levelNum.centerY() + 0.05f * Camera.main.height);
        align(startBtn);
        add(startBtn);
        startBtn.visible = startBtn.active = false;

        arrowLeftButton = new IconButton() {
            @Override
            protected void onClick() {
                super.onClick();
                if (chosenLevel > 1 || DeviceCompat.isDebug()) Statistics.chosenLevel--;
                Game.switchScene(LevelSelectScene.class);
            }
        };
        arrowLeftButton.icon(Icons.get(Icons.ARROWREVERSE));
        arrowLeftButton.setSize(15, 15);
        arrowLeftButton.setPos(levelNum.centerX() - 0.05f * Camera.main.height - (arrowLeftButton.width()) / 2f - levelNum.width() / 2, levelNum.centerY() - arrowLeftButton.width() / 2f);
        align(arrowLeftButton);
        add(arrowLeftButton);
        arrowLeftButton.visible = arrowLeftButton.active = true;
        if (chosenLevel <= 1 && !DeviceCompat.isDebug()) arrowLeftButton.visible = arrowLeftButton.active = false;

        arrowRightButton = new IconButton() {
            @Override
            protected void onClick() {
                super.onClick();
                if (chosenLevel < Statistics.maxLevelDeveloped) Statistics.chosenLevel++;
                Game.switchScene(LevelSelectScene.class);
            }
        };
        arrowRightButton.icon(Icons.get(Icons.ARROW));
        arrowRightButton.setSize(15, 15);
        arrowRightButton.setPos(levelNum.centerX() + 0.05f * Camera.main.height - (arrowRightButton.width()) / 2f + levelNum.width() / 2, levelNum.centerY() - arrowRightButton.width() / 2f);
        align(arrowRightButton);
        add(arrowRightButton);
        arrowRightButton.visible = arrowRightButton.active = false;
        if (chosenLevel < Statistics.maxLevelDeveloped && (chosenLevel <= maxLevelUnlockedForMode || DeviceCompat.isDebug()))
            arrowRightButton.visible = arrowRightButton.active = true;


        if (landscape()) {
        } else {


            levelName.align(RenderedTextBlock.CENTER_ALIGN);
            levelName.setPos(Camera.main.width / 2f - levelName.width() / 2f, 10);


            mainWindow.scale.set(0.5f * Math.round(Camera.main.width / mainWindow.width));
            mainWindow.x = 3;
            mainWindow.y = (levelName.centerY() + levelName.height()/2 + 5);
            PixelScene.align(mainWindow);
            levelDesc.setPos(mainWindow.x + mainWindow.width() + 3, mainWindow.y);
            levelDesc.maxWidth((int) (Camera.main.width / 2.6f));
            levelDesc.align(RenderedTextBlock.LEFT_ALIGN);
            levelDesc.setSize(levelDesc.maxWidth(), levelDesc.height());

            align(levelDesc);

            mainWindow.y = (levelDesc.centerY() - mainWindow.height()/2);
            PixelScene.align(mainWindow);

            levelKnowledge.align(RenderedTextBlock.CENTER_ALIGN);
            levelKnowledge.maxWidth((int) (Camera.main.width));
            levelKnowledge.setPos( Camera.main.width/2f - levelKnowledge.width()/2f, Math.max(levelDesc.centerY() + levelDesc.height()/2 + 5, mainWindow.y + mainWindow.height() + 5));
            levelKnowledge.setSize(levelKnowledge.width(), levelKnowledge.height());


            align(levelName);

            align(levelKnowledge);

            lockedText.align(RenderedTextBlock.CENTER_ALIGN);
            lockedText.maxWidth(100);
            lockedText.setSize(lockedText.maxWidth(), mainWindow.height);
            lockedText.setPos(Camera.main.width/2f - levelKnowledge.width()/2f, Math.max(levelDesc.centerY() + levelDesc.height()/2 + 5, mainWindow.y + mainWindow.height() + 5));
            align(lockedText);

        }

        btnExit = new ExitButton();
        btnExit.setPos(Camera.main.width - btnExit.width(), 0);
        add(btnExit);
        btnExit.visible = btnExit.active = !SPDSettings.intro();

        btnTowers = new IconButton(Icons.get(Icons.GREYTOWER)) {
            @Override
            protected void onClick() {
                super.onClick();
                ShatteredPixelDungeon.switchNoFade( TowersSelectionScene.class );
            }
        };
        btnTowers.setSize(20, 20);
        btnTowers.setPos(startBtn.right(), startBtn.centerY() - btnTowers.height()/2);
        align(btnTowers);
        if (SPDSettings.maxlevelunlocked()>=4 || DeviceCompat.isDebug()) add(btnTowers);

        Icons icon;
        switch (SPDSettings.mode()){
            case NORMAL: default: icon = Icons.AMULET;break;
            case HARDMODE:icon = Icons.BLOODAMULET;break;
            case CHALLENGE:icon = Icons.ETHERIALAMULET;break;
        }

        btnModes = new IconButton(Icons.get(icon)) {
            @Override
            protected void onClick() {
                super.onClick();
                ShatteredPixelDungeon.scene().add(new WndModes(true));
            }
        };
        btnModes.setSize(20, 20);
        btnModes.setPos(startBtn.left() - btnModes.width(), startBtn.centerY() - btnModes.height()/2);
        align(btnModes);
        if (SPDSettings.maxlevelunlocked()>=6) add(btnModes);

        fadeIn();

        if (chosenLevel <= maxLevelUnlockedForMode || DeviceCompat.isDebug())
            startBtn.visible = startBtn.active = true;

    }

    private float uiAlpha;

    @Override
    public void update() {
        super.update();
        btnExit.visible = btnExit.active = !SPDSettings.intro();
        //do not fade when a window is open
        for (Object v : members) {
            if (v instanceof Window) ;
        }
        if (!PixelScene.landscape() && GamesInProgress.selectedClass != null) {
            if (uiAlpha > 0f) {
                uiAlpha -= Game.elapsed / 4f;
            }
        }
    }

    @Override
    protected void onBackPressed() {
        if (btnExit.active) {
            ShatteredPixelDungeon.switchScene(HeroSelectScene.class);
        } else {
            super.onBackPressed();
        }
    }


}
