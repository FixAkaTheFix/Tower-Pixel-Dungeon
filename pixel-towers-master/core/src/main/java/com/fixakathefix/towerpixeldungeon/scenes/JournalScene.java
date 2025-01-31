/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2024 Evan Debenham
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

package com.fixakathefix.towerpixeldungeon.scenes;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Chrome;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.journal.Journal;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.tiles.TerrainFeaturesTilemap;
import com.fixakathefix.towerpixeldungeon.ui.Archs;
import com.fixakathefix.towerpixeldungeon.ui.ExitButton;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.StyledButton;
import com.fixakathefix.towerpixeldungeon.windows.IconTitle;
import com.fixakathefix.towerpixeldungeon.windows.WndJournal;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.SparseArray;

public class JournalScene extends PixelScene {

	public static final int WIDTH_P     = 126;
	public static final int WIDTH_L     = 216;

	private static int lastIDX = 0;

	@Override
	public void create() {

		super.create();

		Dungeon.hero = new Hero();
		Badges.loadGlobal();
		Journal.loadGlobal();

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		float top = 20;

		IconTitle title = new IconTitle( Icons.COPY.get(), Messages.get(this, "title") );
		title.setSize(title.tfLabel.width() + 30, 0);
		title.setPos(
				(w - title.width()) / 2f + 5,
				(top - title.height()) / 2f
		);
		align(title);
		add(title);

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = (landscape() ? WIDTH_L : WIDTH_P) + panel.marginHor();
		int ph = h - 50 + panel.marginVer();

		panel.size(pw, ph);
		panel.x = (w - pw) / 2f;
		panel.y = top;
		add(panel);

		switch (lastIDX){
			case 0: default:
				WndJournal.BadgesTab badges = new WndJournal.BadgesTab();
				add(badges);
				badges.setRect(panel.x + panel.marginLeft(),
						panel.y + panel.marginTop(),
						panel.width() - panel.marginHor(),
						panel.height() - panel.marginVer());
				break;
			case 1:
				WndJournal.CatalogTab catalog = new WndJournal.CatalogTab();
				add(catalog);
				catalog.setRect(panel.x + panel.marginLeft(),
						panel.y + panel.marginTop(),
						panel.width() - panel.marginHor(),
						panel.height() - panel.marginVer());
				catalog.updateList();
				break;
			case 2:
				WndJournal.GuideTab guidebook = new WndJournal.GuideTab();
				add(guidebook);
				guidebook.setRect(panel.x + panel.marginLeft(),
						panel.y + panel.marginTop(),
						panel.width() - panel.marginHor(),
						panel.height() - panel.marginVer());
				guidebook.updateList();
				break;
		}

		StyledButton btnBadges =  new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
			@Override
			protected void onClick() {
				if (lastIDX != 0) {
					lastIDX = 0;
				}
				ShatteredPixelDungeon.seamlessResetScene();
				super.onClick();
			}

			@Override
			protected String hoverText() {
				return Messages.get(WndJournal.BadgesTab.class, "title");
			}
		};
		btnBadges.icon(Icons.BADGES.get());
		btnBadges.setRect(panel.x, panel.y + ph - 3, pw/3f + 1.33f, lastIDX == 0 ? 25 : 20);
		align(btnBadges);
		if (lastIDX != 0) btnBadges.icon().brightness(0.6f);
		addToBack(btnBadges);

		StyledButton btnCatalog =  new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
			@Override
			protected void onClick() {
				if (lastIDX != 1) {
					lastIDX = 1;
				}
				ShatteredPixelDungeon.seamlessResetScene();
				super.onClick();
			}
			@Override
			protected String hoverText() {
				return Messages.get(WndJournal.CatalogTab.class, "title");
			}
		};
		btnCatalog.icon(Icons.COPY.get());
		btnCatalog.setRect(btnBadges.right()-2, btnBadges.top(), pw/3f + 1.33f, lastIDX == 1 ? 25 : 20);
		align(btnCatalog);
		if (lastIDX != 1) btnCatalog.icon().brightness(0.6f);
		addToBack(btnCatalog);

		StyledButton btnGuide =  new StyledButton(Chrome.Type.GREY_BUTTON_TR, ""){
			@Override
			protected void onClick() {
				if (lastIDX != 2) {
					lastIDX = 2;
				}
				ShatteredPixelDungeon.seamlessResetScene();
				super.onClick();
			}
			@Override
			protected String hoverText() {
				return Messages.get(WndJournal.GuideTab.class, "title");
			}
		};
		btnGuide.icon(new ItemSprite(ItemSpriteSheet.MASTERY));
		btnGuide.setRect(btnCatalog.right()-2, btnBadges.top(), pw/3f + 1.33f, lastIDX == 2 ? 25 : 20);
		align(btnGuide);
		if (lastIDX != 2) btnGuide.icon().brightness(0.6f);
		addToBack(btnGuide);

		Archs archs = new Archs();
		archs.setSize( w, h );
		addToBack( archs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		fadeIn();
	}

	@Override
	public void destroy() {

		Badges.saveGlobal();

		super.destroy();
	}

	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade( TitleScene.class );
	}

}
