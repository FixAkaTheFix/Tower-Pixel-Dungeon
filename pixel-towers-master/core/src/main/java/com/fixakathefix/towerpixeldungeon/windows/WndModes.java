/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
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

package com.fixakathefix.towerpixeldungeon.windows;

import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.LevelSelectScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.StyledButton;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;

import java.util.ArrayList;
import java.util.Locale;

public class WndModes extends Window {

    private static final int WIDTH		= (int) Math.min(180, Camera.main.width*0.9f);
    private static final int TTL_HEIGHT = 16;
    private static final int BTN_HEIGHT = 40;
    private static final int GAP        = 1;

    public enum Modes {
        NORMAL,
        HARDMODE,
        CHALLENGE
    }


    private boolean editable;
    private ArrayList<StyledButton> boxes;

    public WndModes(boolean editable ) {

        super();

        this.editable = editable;

        RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 12 );
        title.hardlight( TITLE_COLOR );
        title.setPos(
                (WIDTH - title.width()) / 2,
                (TTL_HEIGHT - title.height()) / 2
        );
        PixelScene.align(title);
        add( title );

        boxes = new ArrayList<>();

        float pos = TTL_HEIGHT;
        for (WndModes.Modes mode : Modes.values()){

            final String modeName = mode.toString().toLowerCase(Locale.ROOT);

            Chrome.Type corType = Chrome.Type.GREY_BUTTON;
            switch (mode){
                case NORMAL: corType = Chrome.Type.GREEN_BUTTON; break;
                case HARDMODE: corType = Chrome.Type.CARMINE_BUTTON; break;
                case CHALLENGE: corType = Chrome.Type.ETHERIAL_BUTTON; break;
            }
            Image icon = Icons.get(Icons.AMULET);
            switch (mode){
                case NORMAL: icon = Icons.get(Icons.AMULET); break;
                case HARDMODE: icon = Icons.get(Icons.BLOODAMULET); break;
                case CHALLENGE: icon = Icons.get(Icons.ETHERIALAMULET); break;
            }

            ModeButton cb = new ModeButton(corType, Messages.titleCase(Messages.get(WndModes.class, modeName)), mode){
                @Override
                protected void onClick() {
                    SPDSettings.mode(mode);
                    hide();
                    Game.switchScene(LevelSelectScene.class);
                }
            };
            cb.icon(icon);
            cb.active = editable;

            cb.setSize(WIDTH, cb.reqHeight());
            cb.setRect(0, pos, WIDTH, cb.reqHeight()+2);

            add( cb );
            boxes.add( cb );

            pos = cb.bottom() + 1;
        }

        resize( WIDTH, (int)pos );
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
    public class ModeButton extends StyledButton {

        private WndModes.Modes assignedMode = Modes.NORMAL;
        {
            multiline = true;
        }

        public ModeButton(Chrome.Type type, String label, WndModes.Modes mode) {
            super(type, label, 8);
            assignedMode = mode;
            text.maxWidth(WIDTH - 6 - 16);
        }
        protected void onClick() {
            SPDSettings.mode(assignedMode);
            hide();
            Game.switchScene(LevelSelectScene.class);
        }
        @Override
        protected void layout() {
            super.layout();
        }
    }
}