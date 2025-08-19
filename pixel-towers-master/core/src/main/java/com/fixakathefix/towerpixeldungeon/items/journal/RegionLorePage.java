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

package com.fixakathefix.towerpixeldungeon.items.journal;

import com.fixakathefix.towerpixeldungeon.journal.Document;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class RegionLorePage {

	public static DocumentPage pageForDoc( Document doc ){
		switch (doc){
			case RK_LOG: default:     return new RegionLorePage.Sewers();
			case PRISONER:             return new RegionLorePage.Prison();
			case DM_LOG:            return new RegionLorePage.Caves();
			case COURTMAN:              return new RegionLorePage.City();
			case HALLS_KING:                return new RegionLorePage.Halls();
		}
	}

	public static class Sewers extends DocumentPage {
		{
			image = ItemSpriteSheet.SEWER_PAGE;
		}

		@Override
		public Document document() {
			return Document.RK_LOG;
		}
	}

	public static class Prison extends DocumentPage {
		{
			image = ItemSpriteSheet.PRISON_PAGE;
		}

		@Override
		public Document document() {
			return Document.PRISONER;
		}
	}

	public static class Caves extends DocumentPage {
		{
			image = ItemSpriteSheet.CAVES_PAGE;
		}

		@Override
		public Document document() {
			return Document.DM_LOG;
		}
	}

	public static class City extends DocumentPage {
		{
			image = ItemSpriteSheet.CITY_PAGE;
		}

		@Override
		public Document document() {
			return Document.COURTMAN;
		}
	}

	public static class Halls extends DocumentPage {
		{
			image = ItemSpriteSheet.HALLS_PAGE;
		}

		@Override
		public Document document() {
			return Document.HALLS_KING;
		}
	}

}
