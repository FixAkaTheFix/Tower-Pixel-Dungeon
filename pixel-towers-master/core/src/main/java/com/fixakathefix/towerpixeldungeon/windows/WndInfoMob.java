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

import com.fixakathefix.towerpixeldungeon.actors.mobs.DrillBig;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.DMWBodySprite;
import com.fixakathefix.towerpixeldungeon.sprites.DrillBigSprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.fixakathefix.towerpixeldungeon.ui.HealthBar;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.watabou.noosa.ui.Component;

public class WndInfoMob extends WndTitledMessage {
	
	public WndInfoMob( Mob mob ) {

		super( new MobTitle( mob ), mob.info() );
		
	}
	
	private static class MobTitle extends Component {

		private static final int GAP	= 2;
		
		private CharSprite image;
		private RenderedTextBlock name;
		private HealthBar health;
		private BuffIndicator buffs;
		
		public MobTitle( Mob mob ) {
			
			name = PixelScene.renderTextBlock( Messages.titleCase( mob.name() ), 9 );
			name.hardlight( TITLE_COLOR );
			add( name );

			image = mob.sprite();
			if (image instanceof DrillBigSprite) image = new DMWBodySprite();
			add( image );

			health = new HealthBar();
			health.level(mob);
			add( health );

			buffs = new BuffIndicator( mob, false );
			add( buffs );
		}
		
		@Override
		protected void layout() {
			
			image.x = 0;
			image.y = Math.max( 0, name.height() + health.height() - image.height() );

			float w = width - image.width() - GAP;
			int extraBuffSpace = 0;

			//Tries to make space for up to 11 visible buffs
			do {
				name.maxWidth((int)w - extraBuffSpace);
				buffs.setSize(w - name.width() - 8, 8);
				extraBuffSpace += 8;
			} while (extraBuffSpace <= 40 && !buffs.allBuffsVisible());

			name.setPos(x + image.width + GAP,
					image.height() > name.height() ? y +(image.height() - name.height()) / 2 : y);

			health.setRect(image.width() + GAP, name.bottom() + GAP, w, health.height());

			buffs.setPos(name.right(), name.bottom() - BuffIndicator.SIZE_SMALL-2);

			height = health.bottom();
		}
	}
}
