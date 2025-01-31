package com.fixakathefix.towerpixeldungeon.scenes;

import static com.watabou.noosa.Game.switchScene;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;

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
