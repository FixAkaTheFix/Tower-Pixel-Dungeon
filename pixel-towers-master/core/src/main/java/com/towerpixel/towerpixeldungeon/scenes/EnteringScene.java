package com.towerpixel.towerpixeldungeon.scenes;

import static com.watabou.noosa.Game.switchScene;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Chrome;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.GamesInProgress;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.effects.BannerSprites;
import com.towerpixel.towerpixeldungeon.effects.Fireball;
import com.towerpixel.towerpixeldungeon.messages.Languages;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.services.updates.AvailableUpdateData;
import com.towerpixel.towerpixeldungeon.services.updates.Updates;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.Archs;
import com.towerpixel.towerpixeldungeon.ui.ExitButton;
import com.towerpixel.towerpixeldungeon.ui.Icons;
import com.towerpixel.towerpixeldungeon.ui.StyledButton;
import com.towerpixel.towerpixeldungeon.ui.Window;
import com.towerpixel.towerpixeldungeon.windows.WndOptions;
import com.towerpixel.towerpixeldungeon.windows.WndSettings;
import com.towerpixel.towerpixeldungeon.windows.WndStyles;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.ColorMath;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PointF;

public class EnteringScene extends PixelScene {

    @Override
    public void create() {

        super.create();

        uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;

        Image title = new Image(Assets.Splashes.GDX ) {
            private float time = 0;
            @Override
            public void update() {
                super.update();
                am = Math.max(0f, (float)Math.sin( time += Game.elapsed ));
                if (time >= 1.5f*Math.PI) {
                    time = 0;
                    switchScene(EnteringScene2.class);
                }
            }
        };
        title.scale.set(Math.min(w,h) / title.width/1.5f);
        add( title );
        title.x = (w - title.width()) / 2f;
        title.y = (h - title.height()) / 2f;

        align(title);


        fadeIn();
    }
}
