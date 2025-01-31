package com.fixakathefix.towerpixeldungeon.windows;

import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

public class WndTextScroll extends Window {

    protected static final int WIDTH_MIN    = 120;
    protected static final int WIDTH_MAX    = 220;
    protected static final int GAP	= 2;

    public WndTextScroll(Image icon, String title, String message ) {

          this( new IconTitle( icon, title ), message );

    }

    public WndTextScroll(Component titlebar, String message ) {

        super();

        chrome = Chrome.get(Chrome.Type.SCROLL);

        int width = WIDTH_MIN;

        titlebar.setRect( 0, 0, width, 0 );
        add(titlebar);

        RenderedTextBlock text = PixelScene.renderTextBlock( 6 );
        text.text( message, width );
        text.setPos( titlebar.left(), titlebar.bottom() + 2*GAP );
        add( text );

        while (PixelScene.landscape()
                && text.bottom() > (PixelScene.MIN_HEIGHT_L - 10)
                && width < WIDTH_MAX){
            width += 20;
            titlebar.setRect(0, 0, width, 0);
            text.setPos( titlebar.left(), titlebar.bottom() + 2*GAP );
            text.maxWidth(width);
        }

        bringToFront(titlebar);

        resize( width, (int)text.bottom() + 2 );
    }
}