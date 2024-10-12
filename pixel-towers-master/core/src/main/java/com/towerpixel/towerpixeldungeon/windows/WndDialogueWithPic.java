package com.towerpixel.towerpixeldungeon.windows;

import static com.towerpixel.towerpixeldungeon.Dungeon.win;

import com.badlogic.gdx.utils.Timer;
import com.towerpixel.towerpixeldungeon.Chrome;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.GamesInProgress;
import com.towerpixel.towerpixeldungeon.items.Amulet;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.scenes.PixelScene;
import com.towerpixel.towerpixeldungeon.scenes.RankingsScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.RenderedTextBlock;
import com.towerpixel.towerpixeldungeon.ui.Window;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.PointerArea;
import com.watabou.utils.Callback;

import java.util.ArrayList;


public class WndDialogueWithPic extends Window {
    public Image icon;


    private RenderedTextBlock ttl;
    private RenderedTextBlock tf;
    private static final int MARGIN = 2;

    ArrayList<Runnable> runnableArrayList;


    private int textNum = 0;
    public boolean lastDialogue = false;

    private String[] texts;


    private float textSpeed;
    private String curText;
    private int letterNum = 0;
    private CharSprite image;
    private boolean typing = false;

    public byte spriteActionIndexes[];

    public static final byte IDLE = 0;
    public static final byte RUN = 1;
    public static final byte ATTACK = 2;
    public static final byte DIE = 3;

    public enum WndType {
        NORMAL,
        FINAL,
        CUSTOM
    }

    public static void dialogue(CharSprite icon, String title, String[] text) {
        dialogue(icon, title, text, new byte[]{});
    }

    public static void dialogue(CharSprite icon, String title, String[] text, byte spriteActionIndexes[]) {
        dialogue(icon, title, text, spriteActionIndexes, WndType.NORMAL, new ArrayList<>());
    }
    public static void dialogue(CharSprite icon, String title, String[] text, byte[] spriteActionIndexes, WndType type, ArrayList<Runnable> runnableArrayList) {

        if (Dungeon.level.mode == WndModes.Modes.NORMAL || type == WndType.FINAL) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    WndDialogueWithPic wnd = new WndDialogueWithPic(icon, title, text, spriteActionIndexes);
                    if (type == WndType.FINAL) wnd.lastDialogue = true;
                    wnd.runnableArrayList = runnableArrayList;
                    GameScene.show(wnd);
                }
            });
        }
    }


    public WndDialogueWithPic(CharSprite icon, String title, String[] text) {
        this(icon, title, text, new byte[]{});
    }

    public WndDialogueWithPic(CharSprite icon, String title, String[] text, byte spriteActionIndexes[]) {
        super(0, 0, Chrome.get(Chrome.Type.TOAST_TR));
/*
        if (Dungeon.level.mode == WndModes.Modes.CHALLENGE) {
            icon = new RatKingSprite();
            title = Messages.get(WndDialogueWithPic.class, "rkavatar");
            icon.hardlight(0x00EEEE);
            if (Dungeon.level.wave == 1 && hero.buff(WaveBuff.class) != null) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "chalstart" + Random.NormalIntRange(1, 3))
                };
            } else if (Dungeon.level.wave > 2 && Dungeon.level.wave < ((Arena) Dungeon.level).maxWaves) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "chalmiddle" + Random.NormalIntRange(1, 5))
                };
            } else if (Dungeon.level.wave == ((Arena) Dungeon.level).maxWaves) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "chalend" + Random.NormalIntRange(1, 3))
                };
            } else {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "onlybeginning")
                };
            }
        }
        if (Dungeon.level.mode == WndModes.Modes.HARDMODE) {
            icon = new GorematiaSpiritSprite();
            title = "#???#";
            if (Dungeon.level.wave == 1) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "hardstart" + Random.NormalIntRange(1, 3))
                };
            } else if (Dungeon.level.wave > 2 && Dungeon.level.wave < ((Arena) Dungeon.level).maxWaves) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "hardmiddle" + Random.NormalIntRange(1, 5))
                };
            } else if (Dungeon.level.wave >= ((Arena) Dungeon.level).maxWaves) {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "hardend" + Random.NormalIntRange(1, 3))
                };
            } else {
                text = new String[]{
                        Messages.get(WndDialogueWithPic.class, "onlysuffering")
                };
            }
        }
*/
        shadow.visible = false;
        resize(PixelScene.uiCamera.width, PixelScene.uiCamera.height);
        texts = text;
        textNum = 0;

        image = icon;

        this.spriteActionIndexes = spriteActionIndexes;

        int chromeWidth = PixelScene.landscape() ? PixelScene.uiCamera.width / 2 : PixelScene.uiCamera.width - 4;
        int chromeHeight = Math.round(PixelScene.uiCamera.height * 0.3f);
        chrome.x = (PixelScene.uiCamera.width - chromeWidth) * 0.5f;
        chrome.y = (PixelScene.uiCamera.height - chromeHeight - 2);
        chrome.size(chromeWidth, chromeHeight);
        addToFront(chrome);

        float y = chrome.y + MARGIN;

        int scale = 8;
        icon.scale.set(scale);

        while (icon.width() > PixelScene.uiCamera.width / 2) {
            scale--;
            icon.scale.set(scale);
        }

        icon.x = chrome.x;
        icon.y = chrome.y - icon.height * (scale / 1.5f);
        addToBack(icon);


        ttl = PixelScene.renderTextBlock(title, 11);
        ttl.maxWidth(chromeWidth - 4 * MARGIN);
        ttl.setPos(chrome.x + icon.width() + 2 * MARGIN, chrome.y - 2 * MARGIN - ttl.height());
        add(ttl);

        tf = PixelScene.renderTextBlock("", 9);
        tf.maxWidth(chromeWidth - 4 * MARGIN);
        tf.setPos(chrome.x + 2 * MARGIN, y + 2 * MARGIN);
        add(tf);


        PointerArea blocker = new PointerArea(0, 0, PixelScene.uiCamera.width, PixelScene.uiCamera.height) {
            @Override
            protected void onClick(PointerEvent event) {
                onBackPressed();
            }
        };
        blocker.camera = PixelScene.uiCamera;
        addToBack(blocker);
        startText(texts[0]);

    }

    ;

    private Timer timer = new Timer();


    @Override
    public void onBackPressed() {
        if (typing) {
            typing = false;
            timer.stop();
            timer.clear();
            tf.text(texts[textNum]);
        } else {
            textNum++;
            if (textNum >= texts.length) {
                hide();
                if (lastDialogue) {
                    win(Amulet.class);
                    Dungeon.deleteGame(GamesInProgress.curSlot, true);
                    Game.switchScene(RankingsScene.class);
                }
            } else startText(texts[textNum]);
        }
        update();
    }

    private void startText(String text) {
        curText = "";
        tf.text(curText);
        update();
        letterNum = 0;
        typing = true;
        timer.clear();
        timer.start();
        try {
            runnableArrayList.get(textNum).run();
        } catch (Exception ignored){}
        if (textNum < spriteActionIndexes.length) switch (spriteActionIndexes[textNum]) {
            case 0:
            default:
                image.play(image.idle);
                break;
            case 1:
                image.play(image.run);
                break;
            case 2:
                image.play(image.attack);
                break;
            case 3:
                image.play(image.die);
                break;
        }
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                if (typing) nextLetter(text);
                WndDialogueWithPic.this.update();
            }
        }, 0f, 0.04f, text.length());

    }

    private void nextLetter(String text) {
        curText += text.charAt(letterNum);
        tf.text(curText);
        letterNum++;
        if (letterNum >= text.length()) {
            typing = false;
            timer.stop();
            timer.clear();
        }
    }

}
