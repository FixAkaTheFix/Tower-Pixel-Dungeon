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
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.Flare;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.StatusPane;
import com.fixakathefix.towerpixeldungeon.ui.TalentsPane;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndHero;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class PotionOfDivineInspiration extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_DIVINE;
		image = ItemSpriteSheet.EXOTIC_BLESSING;
	}

	@Override
	//need to override drink so that time isn't spent right away
	protected void drink(final Hero hero) {
		curUser = hero;
		curItem = this;

		boolean[] enabled = new boolean[5];
		enabled[1] = enabled[2] = enabled[3] = enabled[4] = true;

		DivineInspirationTracker tracker = hero.buff(DivineInspirationTracker.class);

		if (tracker != null){
			boolean allBoosted = true;
			for (int i = 1; i <= 4; i++){
				if (tracker.isBoosted(i)){
					enabled[i] = false;
				} else {
					allBoosted = false;
				}
			}

			if (allBoosted){
				GLog.w(Messages.get(this, "no_more_points"));
				return;
			}
		}

		if (!isIdentified()) {
			curItem.detach(curUser.belongings.backpack);
		}

		GameScene.show(new WndOptions(
				new ItemSprite(this),
				Messages.titleCase(trueName()),
				Messages.get(PotionOfDivineInspiration.class, "select_tier"),
				Messages.titleCase(Messages.get(TalentsPane.class, "tier", 1)),
				Messages.titleCase(Messages.get(TalentsPane.class, "tier", 2)),
				Messages.titleCase(Messages.get(TalentsPane.class, "tier", 3)),
				Messages.titleCase(Messages.get(TalentsPane.class, "tier", 4))
		){
			@Override
			protected boolean enabled(int index) {
				return enabled[index+1];
			}

			@Override
			protected void onSelect(int index) {
				super.onSelect(index);

				if (index != -1){
					Buff.affect(curUser, DivineInspirationTracker.class).setBoosted(index+1);

					if (isIdentified()) {
						curItem.detach(curUser.belongings.backpack);
					}

					identify();
					curUser.busy();
					curUser.sprite.operate(curUser.pos);

					curUser.spendAndNext(1f);

					boolean unspentTalents = false;
					for (int i = 1; i <= Dungeon.hero.talents.size(); i++){
						if (Dungeon.hero.talentPointsAvailable(i) > 0){
							unspentTalents = true;
							break;
						}
					}
					if (unspentTalents){
						StatusPane.talentBlink = 10f;
						WndHero.lastIdx = 1;
					}

					GameScene.showlevelUpStars();

					Sample.INSTANCE.play( Assets.Sounds.DRINK );
					Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.3f, 0.7f, 1.2f);
					Sample.INSTANCE.playDelayed(Assets.Sounds.LEVELUP, 0.6f, 0.7f, 1.2f);
					new Flare( 6, 32 ).color(0xFFFF00, true).show( curUser.sprite, 2f );
					GLog.p(Messages.get(PotionOfDivineInspiration.class, "bonus"));

				}
			}

			@Override
			public void onBackPressed() {
				//do nothing, prevents accidentally closing
			}
		});

	}

	public static class DivineInspirationTracker extends Buff {

		{
			type = buffType.POSITIVE;
			revivePersists = true;
		}

		private boolean[] boostedTiers = new boolean[5];

		private static final String BOOSTED_TIERS = "boosted_tiers";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(BOOSTED_TIERS, boostedTiers);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			boostedTiers = bundle.getBooleanArray(BOOSTED_TIERS);
		}

		public void setBoosted( int tier ){
			boostedTiers[tier] = true;
		}

		public boolean isBoosted( int tier ){
			return boostedTiers[tier];
		}

	}
	
}
