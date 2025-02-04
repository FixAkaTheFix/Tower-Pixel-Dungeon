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

package com.fixakathefix.towerpixeldungeon.items.potions.exotic;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.CorrosiveGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Infested;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Poison;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class PotionOfCorrosiveGas extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_CORROGAS;
		image = ItemSpriteSheet.EXOTIC_TOXIGGAS;
	}
	
	@Override
	public void shatter( int cell ) {
		
		if (Dungeon.level.heroFOV[cell]) {
			identify();
			
			splash( cell );
			Sample.INSTANCE.play( Assets.Sounds.SHATTER );
			Sample.INSTANCE.play( Assets.Sounds.GAS );
		}

		int centerVolume = 25;
		for (int i : PathFinder.NEIGHBOURS8){
			if (!Dungeon.level.solid[cell+i]){
				GameScene.add( Blob.seed( cell+i, 20, CorrosiveGas.class ).setStrength( 2 + Dungeon.scalingDepth()/5));
			} else {
				centerVolume += 25;
			}
		}

		GameScene.add( Blob.seed( cell, centerVolume, CorrosiveGas.class ).setStrength( 2 + Dungeon.scalingDepth()/5));
	}
	@Override
	public void apply( Hero hero ) {
		Buff.affect( hero, Poison.class ).set( 6 + Dungeon.scalingDepth() );
		Dungeon.observe();
		Buff.detach( hero, Infested.class );
		hero.speak("*cough*", CharSprite.NEGATIVE);
		GLog.n( Messages.get(this, "onpoisoning") );
	}
}
