package com.fixakathefix.towerpixeldungeon.windows;

import static com.fixakathefix.towerpixeldungeon.Badges.loadGlobal;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.Styles;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.EnteringScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.scenes.TitleScene;
import com.fixakathefix.towerpixeldungeon.ui.*;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.glwrap.Texture;
import com.watabou.glwrap.Vertexbuffer;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.FileHandler;

public class WndStyles extends Window {

    private final int WIDTH = Math.min((int) (PixelScene.uiCamera.width * 0.85), 200);
    private final int HEIGHT = (int) (PixelScene.uiCamera.height * 0.85);
    private static final int TTL_HEIGHT = 18;
    private static final int BTN_HEIGHT = 18;
    private static final int GAP = 1;
    private ArrayList<IconButton> styleIconButtons = new ArrayList<>();
    private ArrayList<StyleButton> styleCheckBoxes = new ArrayList<>();

    public WndStyles() {

        super();
        loadGlobal();

        resize(WIDTH, HEIGHT);

        RenderedTextBlock title = PixelScene.renderTextBlock(Messages.get(this, "title"), 12);
        title.hardlight(TITLE_COLOR);
        title.setPos(
                (WIDTH - title.width()) / 2,
                (TTL_HEIGHT - title.height()) / 2
        );
        PixelScene.align(title);
        add(title);
        ArrayList<Styles.Style> allStyles = new ArrayList<>();

        for (Styles.Style style : Styles.Style.values()){
            if (style.exists(style)) {
                allStyles.add(style);
            }
        }
        ScrollPane pane = new ScrollPane(new Component()) {
            @Override
            public void onClick(float x, float y) {
                int size = styleCheckBoxes.size();
                for (int i = 0; i < size; i++) {
                    if (styleCheckBoxes.get(i).onClick(x, y)) break;
                }
                size = styleIconButtons.size();
                for (int i = 0; i < size; i++) {
                    if (styleIconButtons.get(i).inside(x, y)) {
                        int index = i;

                        String message = allStyles.get(index).desc();
                        String title = allStyles.get(index).btName();
                        ShatteredPixelDungeon.scene().add(
                                new WndTitledMessage(
                                        new Image(Assets.Interfaces.STYLEICONS, (allStyles.get(index).ordinal()) * 16, 0, 16, 16),
                                        title, message)
                        );

                        break;
                    }
                }
            }
        };
        add(pane);
        pane.setRect(0, title.bottom() + 2, WIDTH, HEIGHT - title.bottom() - 2);
        Component content = pane.content();

        float pos = 2;
        for (Styles.Style i : allStyles) {
            if (i.exists(i)) {

                final String style = i.btName();

                StyleButton styleCB = new StyleButton("       " + style, 9, i.condition(i) ? Chrome.Type.RED_BUTTON : Chrome.Type.GREY_BUTTON);
                //styleCB.checked(i.checked(i));
                styleCB.active = true;
                styleCB.style = i;

                pos += GAP;
                styleCB.setRect(0, pos, WIDTH - 16, BTN_HEIGHT);

                content.add(styleCB);
                styleCheckBoxes.add(styleCB);

                IconButton info = new IconButton(i.condition(i) ? new Image(Assets.Interfaces.UNLOCKICONS, 0, 0, 14, 14) : new Image(Assets.Interfaces.UNLOCKICONS, 14, 0, 14, 14)) {
                    @Override
                    protected void layout() {
                        super.layout();
                        hotArea.y = -5000;
                    }


                };
                info.setRect(styleCB.right(), pos, 16, BTN_HEIGHT);
                content.add(info);
                styleIconButtons.add(info);
                Image icon = new Image(Assets.Interfaces.STYLEICONS, (i.ordinal()) * 16, 0, 16, 16);
                icon.x = styleCB.left() + 1;
                icon.y = styleCB.top() + 1;
                content.add(icon);


                pos = styleCB.bottom();
            }
        }

        content.setSize(WIDTH, pos);
        this.visible = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class StyleButton extends RedButton {

        public Styles.Style style;

        public StyleButton(String label) {
            super(label);
        }
        public StyleButton(String label, int size, Chrome.Type chrometype) {
            super(label,  size, chrometype);
        }

        @Override
        protected void onClick() {
            super.onClick();
            if (active) {
                if (style.condition(style)){
                SPDSettings.style(style.index(style));
                ShatteredPixelDungeon.scene().add(new WndTitledMessage( Icons.INFO.get(),"Reload to set the interface", "The "+style.name()+" interface style was set, but the game needs to be _FULLY RELOADED_ for the interfaces to change. Close it and then reopen it: the interface will change automatically."){
                    @Override
                    public void onBackPressed() {
                        //ShatteredPixelDungeon.instance.dispose();
                        super.onBackPressed();
                    }
                });
                }
            }
        }

        protected boolean onClick(float x, float y) {
            if (!inside(x, y)) return false;
            Sample.INSTANCE.play(Assets.Sounds.CLICK);
            onClick();
            return true;
        }

        @Override
        protected void layout() {
            super.layout();
            hotArea.width = hotArea.height = 0;
        }
    }
}
