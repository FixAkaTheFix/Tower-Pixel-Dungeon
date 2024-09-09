package com.towerpixel.towerpixeldungeon.ui.towerlist;

import com.towerpixel.towerpixeldungeon.scenes.PixelScene;
import com.towerpixel.towerpixeldungeon.windows.WndTitledMessage;
import com.watabou.input.PointerEvent;
import com.watabou.noosa.Image;
import com.watabou.noosa.PointerArea;

public class WndTowerInfo extends WndTitledMessage {

    public WndTowerInfo(TowerInfo.AllTowers tower) {
        super( null, TowerInfo.getTowerName(tower), TowerInfo.getTowerDescription(tower));

        PointerArea blocker = new PointerArea( 0, 0, PixelScene.uiCamera.width, PixelScene.uiCamera.height ) {
            @Override
            protected void onClick( PointerEvent event ) {
                onBackPressed();
            }
        };
        blocker.camera = PixelScene.uiCamera;
        add(blocker);

    }

}