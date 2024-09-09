package com.towerpixel.towerpixeldungeon.scenes;

import static com.watabou.noosa.Game.switchScene;

import com.towerpixel.towerpixeldungeon.Assets;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;

public class EnteringScene2 extends PixelScene {

    @Override
    public void create() {

        super.create();

        uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;

        Image fix = new Image(Assets.Splashes.LOGO ) {
            private float time = 0;
            @Override
            public void update() {
                super.update();
                am = Math.max(0f, (float)Math.sin( time += Game.elapsed ));
                if (time >= 1.5f*Math.PI) {
                    time = 0;
                    switchScene(TitleScene.class);
                }
            }
        };



        Image evandarling = new Image(Assets.Splashes.BASED ) {
            private float time = 0;
            @Override
            public void update() {
                super.update();
                am = Math.max(0f, (float)Math.sin( time += Game.elapsed ));
                if (time >= 1.5f*Math.PI) {
                    time = 0;
                    switchScene(TitleScene.class);
                }
            }
        };
        fix.scale.set(Math.max(w,h) / fix.width/4f);
        evandarling.scale.set(Math.max(w,h) / evandarling.width/4f);
        add( fix );
        add( evandarling );
        if (h>w){
            fix.x = (w - fix.width()) / 2f;
            fix.y = h/6f;
            evandarling.x = (w - evandarling.width()) / 2f;
            evandarling.y = 7*h/12f;
        } else {
            fix.x = w/6f;
            fix.y = (h - fix.height()) / 2f;
            evandarling.x = 7*w/12f;
            evandarling.y = (h - evandarling.height()) / 2f;
        }


        align(evandarling);


        fadeIn();
    }
}
