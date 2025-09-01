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

import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroSubClass;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.IconButton;
import com.fixakathefix.towerpixeldungeon.ui.Icons;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.TalentButton;
import com.fixakathefix.towerpixeldungeon.ui.TalentsPane;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class WndHeroInfo extends WndTabbed {

	private HeroInfoTab heroInfo;
	private TalentInfoTab talentInfo;
	private SubclassInfoTab subclassInfo;
	private ArmorAbilityInfoTab abilityInfo;

	private static int WIDTH = 120;
	private static int MIN_HEIGHT = 125;
	private static int MARGIN = 2;

	public WndHeroInfo( HeroClass cl ){

		Image tabIcon;
		switch (cl){
			case WARRIOR: default:
				tabIcon = new ItemSprite(ItemSpriteSheet.LONGSWORD, null);
				break;
			case MAGE:
				tabIcon = new ItemSprite(ItemSpriteSheet.MAGES_STAFF, null);
				break;
			case ROGUE:
				tabIcon = new ItemSprite(ItemSpriteSheet.ARTIFACT_CLOAK, null);
				break;
			case HUNTRESS:
				tabIcon = new ItemSprite(ItemSpriteSheet.SPIRIT_BOW, null);
				break;
			case DUELIST:
				tabIcon = new ItemSprite(ItemSpriteSheet.RAPIER, null);
				break;
			case TANK:
				tabIcon = Icons.get(Icons.TANK);
				break;
			case NECROHERO:
				tabIcon = Icons.get(Icons.NECROHERO);
				break;
			case PRIEST:
				tabIcon = Icons.get(Icons.PRIEST);
				break;
		}

		int finalHeight = MIN_HEIGHT;

		heroInfo = new HeroInfoTab(cl);
		add(heroInfo);
		heroInfo.setSize(WIDTH, MIN_HEIGHT);
		finalHeight = (int)Math.max(finalHeight, heroInfo.height());

		add( new IconTab( tabIcon ){
			@Override
			protected void select(boolean value) {
				super.select(value);
				heroInfo.visible = heroInfo.active = value;
			}
		});
		if (false) {
			talentInfo = new TalentInfoTab(cl);
			add(talentInfo);
			talentInfo.setSize(WIDTH, MIN_HEIGHT);
			finalHeight = (int) Math.max(finalHeight, talentInfo.height());

			add(new IconTab(Icons.get(Icons.TALENT)) {
				@Override
				protected void select(boolean value) {
					super.select(value);
					talentInfo.visible = talentInfo.active = value;
				}
			});
		}
		if (false) {
			subclassInfo = new SubclassInfoTab(cl);
			add(subclassInfo);
			subclassInfo.setSize(WIDTH, MIN_HEIGHT);
			finalHeight = (int)Math.max(finalHeight, subclassInfo.height());

			add(new IconTab(new ItemSprite(ItemSpriteSheet.MASK, null)) {
				@Override
				protected void select(boolean value) {
					super.select(value);
					subclassInfo.visible = subclassInfo.active = value;
				}
			});
		}

		{
			abilityInfo = new ArmorAbilityInfoTab(cl);
			add(abilityInfo);
			abilityInfo.setSize(WIDTH, MIN_HEIGHT);
			finalHeight = (int)Math.max(finalHeight, abilityInfo.height());

			add(new IconTab(new ItemSprite(ItemSpriteSheet.HEROSPELL_NOAMULET, null)) {
				@Override
				protected void select(boolean value) {
					super.select(value);
					abilityInfo.visible = abilityInfo.active = value;
				}
			});
		}

		resize(WIDTH, finalHeight);

		layoutTabs();
		//talentInfo.layout();

		select(0);

	}

	@Override
	public void offset(int xOffset, int yOffset) {
		super.offset(xOffset, yOffset);
		//talentInfo.layout();
	}

	private static class HeroInfoTab extends Component {

		private RenderedTextBlock title;
		private RenderedTextBlock[] info;
		private Image[] icons;

		public HeroInfoTab(HeroClass cls){
			super();
			title = PixelScene.renderTextBlock(Messages.titleCase(cls.title()), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			String[] desc_entries = cls.desc().split("\n\n");

			info = new RenderedTextBlock[desc_entries.length];

			for (int i = 0; i < desc_entries.length; i++){
				info[i] = PixelScene.renderTextBlock(desc_entries[i], 6);
				add(info[i]);
			}

			switch (cls){
				case WARRIOR: default:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.LONGSWORD),
							new ItemSprite(ItemSpriteSheet.ARMOR_MAIL),
							new ItemSprite(ItemSpriteSheet.SCROLL_BATTLECRY)};
					break;
				case NECROHERO:
					icons = new Image[]{ Icons.get(Icons.NECROHERO),
							new ItemSprite(ItemSpriteSheet.DAGGER),
							new ItemSprite(ItemSpriteSheet.SCROLL_SKULLS)};
					break;
				case PRIEST:
					icons = new Image[]{ Icons.get(Icons.PRIEST),
							new ItemSprite(ItemSpriteSheet.WEAPON_HOLDER),
							new ItemSprite(ItemSpriteSheet.SCROLL_UPGRADE)};
					break;
				case TANK:
					icons = new Image[]{ Icons.get(Icons.TANK),
							new ItemSprite(ItemSpriteSheet.EXOTIC_HEAL),
							new ItemSprite(ItemSpriteSheet.SCROLL_MAGICMAPPING)};
					break;
				case MAGE:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.MAGES_STAFF),
							new ItemSprite(ItemSpriteSheet.STONE_INTUITION),
							new ItemSprite(ItemSpriteSheet.SCROLL_TELEPORT)};
					break;
				case ROGUE:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.ARTIFACT_CLOAK),
							new ItemSprite(ItemSpriteSheet.POTION_PARALYTICGAS),
							new ItemSprite(ItemSpriteSheet.SCROLL_MIRRORIMAGE)};
					break;
				case HUNTRESS:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.SPIRIT_BOW),
							new ItemSprite(ItemSpriteSheet.ARMOR_CLOTH),
							new ItemSprite(ItemSpriteSheet.SCROLL_DROWSINESS)};
					break;
				case DUELIST:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.RAPIER),
							new ItemSprite(ItemSpriteSheet.WAR_HAMMER),
							new ItemSprite(ItemSpriteSheet.SCROLL_TRANSMUTATION)};
					break;

			}
			for (Image im : icons) {
				add(im);
			}

		}

		@Override
		protected void layout() {
			super.layout();

			title.setPos((width-title.width())/2, MARGIN);

			float pos = title.bottom()+4*MARGIN;

			for (int i = 0; i < info.length; i++){
				info[i].maxWidth((int)width - 20);
				info[i].setPos(20, pos);

				icons[i].x = (20-icons[i].width())/2;
				icons[i].y = info[i].top() + (info[i].height() - icons[i].height())/2;

				pos = info[i].bottom() + 4*MARGIN;
			}

			height = Math.max(height, pos - 4*MARGIN);

		}
	}

	private static class TalentInfoTab extends Component {

		private RenderedTextBlock title;
		private RenderedTextBlock message;
		private TalentsPane talentPane;

		public TalentInfoTab( HeroClass cls ){
			super();
			title = PixelScene.renderTextBlock(Messages.titleCase(Messages.get(WndHeroInfo.class, "talents")), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			message = PixelScene.renderTextBlock(Messages.get(WndHeroInfo.class, "talents_msg"), 6);
			add(message);

			ArrayList<LinkedHashMap<Talent, Integer>> talents = new ArrayList<>();
			Talent.initClassTalents(cls, talents);
			talents.get(2).clear(); //we show T3 talents with subclasses

			talentPane = new TalentsPane(TalentButton.Mode.INFO, talents);
			add(talentPane);
		}

		@Override
		protected void layout() {
			super.layout();

			title.setPos((width-title.width())/2, MARGIN);
			message.maxWidth((int)width);
			message.setPos(0, title.bottom()+4*MARGIN);

			talentPane.setRect(0, message.bottom() + 3*MARGIN, width, 85);

			height = Math.max(height, talentPane.bottom());
		}
	}

	private static class SubclassInfoTab extends Component {

		private RenderedTextBlock title;
		private RenderedTextBlock message;
		private RenderedTextBlock[] subClsDescs;
		private IconButton[] subClsInfos;

		public SubclassInfoTab( HeroClass cls ){
			super();
			title = PixelScene.renderTextBlock(Messages.titleCase(Messages.get(WndHeroInfo.class, "subclasses")), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			message = PixelScene.renderTextBlock(Messages.get(WndHeroInfo.class, "subclasses_msg"), 6);
			add(message);

			HeroSubClass[] subClasses = cls.subClasses();

			subClsDescs = new RenderedTextBlock[subClasses.length];
			subClsInfos = new IconButton[subClasses.length];

			for (int i = 0; i < subClasses.length; i++){
				subClsDescs[i] = PixelScene.renderTextBlock(subClasses[i].shortDesc(), 6);
				int finalI = i;
				subClsInfos[i] = new IconButton( Icons.get(Icons.INFO) ){
					@Override
					protected void onClick() {
						Game.scene().addToFront(new WndInfoSubclass(cls, subClasses[finalI]));
					}
				};
				add(subClsDescs[i]);
				add(subClsInfos[i]);
			}

		}

		@Override
		protected void layout() {
			super.layout();

			title.setPos((width-title.width())/2, MARGIN);
			message.maxWidth((int)width);
			message.setPos(0, title.bottom()+4*MARGIN);

			float pos = message.bottom()+4*MARGIN;

			for (int i = 0; i < subClsDescs.length; i++){
				subClsDescs[i].maxWidth((int)width - 20);
				subClsDescs[i].setPos(0, pos);

				subClsInfos[i].setRect(width-20, subClsDescs[i].top() + (subClsDescs[i].height()-20)/2, 20, 20);

				pos = subClsDescs[i].bottom() + 4*MARGIN;
			}

			height = Math.max(height, pos - 4*MARGIN);

		}
	}

	private static class ArmorAbilityInfoTab extends Component {

		private RenderedTextBlock title;
		private RenderedTextBlock[] info;
		private Image[] icons;

		public ArmorAbilityInfoTab(HeroClass cls){
			super();
			title = PixelScene.renderTextBlock(Messages.titleCase(cls.title()), 9);
			title.hardlight(TITLE_COLOR);
			add(title);

			String[] ab_entries = cls.heroSpells().split("\n\n");

			info = new RenderedTextBlock[ab_entries.length];

			for (int i = 0; i < ab_entries.length; i++){
				info[i] = PixelScene.renderTextBlock(ab_entries[i], 6);
				add(info[i]);
			}

			switch (cls){
				case WARRIOR: default:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_TREATWOUNDS),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_GLOWUP),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_RAGE)};
					break;
				case MAGE:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_GIBBERISH),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_TELEPORTATION),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_ICEWALL)};
					break;
				case ROGUE:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_SWIFT),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_DISENGAGE),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_SHADOWCLONE)};
					break;
				case HUNTRESS:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_OAKSKIN),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_LASHER),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_INSIGHT)};
					break;
				case DUELIST:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_ARCANESWORD),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_BANNER),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_FOCUS)};
					break;
				case TANK:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_SHIELD),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_WALLSTANCE),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_TAUNT)};
					break;
				case NECROHERO:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_NECROMANCY),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_UNDYING),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_OBELISK)};
					break;
				case PRIEST:
					icons = new Image[]{ new ItemSprite(ItemSpriteSheet.HEROSPELL_PRAY),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_HOLY_WATER),
							new ItemSprite(ItemSpriteSheet.HEROSPELL_SUN)};
					break;
			}
			for (Image im : icons) {
				add(im);
			}

		}

		@Override
		protected void layout() {
			super.layout();

			title.setPos((width-title.width())/2, MARGIN);

			float pos = title.bottom()+4*MARGIN;

			for (int i = 0; i < info.length; i++){
				info[i].maxWidth((int)width - 20);
				info[i].setPos(20, pos);

				icons[i].x = (20-icons[i].width())/2;
				icons[i].y = info[i].top() + (info[i].height() - icons[i].height())/2;

				pos = info[i].bottom() + 4*MARGIN;
			}

			height = Math.max(height, pos - 4*MARGIN);

		}
	}

}
