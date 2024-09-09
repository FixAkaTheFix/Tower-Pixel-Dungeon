/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.towerpixel.towerpixeldungeon.ui;

import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.LevelSelectScene;
import com.towerpixel.towerpixeldungeon.scenes.TitleScene;
import com.towerpixel.towerpixeldungeon.scenes.TowersSelectionScene;
import com.towerpixel.towerpixeldungeon.windows.WndKeyBindings;
import com.watabou.input.GameAction;
import com.watabou.noosa.Game;

public class ExitButton extends IconButton {

	public ExitButton() {
		super(Icons.EXIT.get());

		width = 20;
		height = 20;
	}

	@Override
	protected void onClick() {
		if (Game.scene() instanceof TitleScene) {
			Game.instance.finish();
		} else if (Game.scene() instanceof TowersSelectionScene) {
			ShatteredPixelDungeon.switchNoFade( LevelSelectScene.class );
		} else {
			ShatteredPixelDungeon.switchNoFade( TitleScene.class );
		}
	}

	@Override
	public GameAction keyAction() {
		return GameAction.BACK;
	}

	@Override
	protected String hoverText() {
		return Messages.titleCase(Messages.get(WndKeyBindings.class, "back"));
	}
}
