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

package com.fixakathefix.towerpixeldungeon.items.artifacts;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hunger;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MagicImmune;
import com.fixakathefix.towerpixeldungeon.actors.hero.Belongings;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.effects.SpellSprite;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.bags.Bag;
import com.fixakathefix.towerpixeldungeon.items.food.Blandfruit;
import com.fixakathefix.towerpixeldungeon.items.food.Food;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.food.Pasty;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfEnergy;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class HornOfPlenty extends Artifact {


	{
		image = ItemSpriteSheet.ARTIFACT_HORN1;

		levelCap = 10;

		charge = 0;
		partialCharge = 0;
		chargeCap = 5 + level()/2;



		defaultAction = AC_SNACK;
	}

	public float timeToEat = 3f;//per 1 point
	public float healingPercentage = 0.03f;//per 1 point
	private int storedFoodEnergy = 0;

	public static final String AC_SNACK = "SNACK";
	public static final String AC_EAT = "EAT";
	public static final String AC_STORE = "STORE";

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (hero.buff(MagicImmune.class) != null) return actions;
		if (isEquipped( hero ) && charge > 0) {
			actions.add(AC_SNACK);
			actions.add(AC_EAT);
		}
		if (isEquipped( hero ) && level() < levelCap && !cursed) {
			actions.add(AC_STORE);
		}
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {

		super.execute(hero, action);

		if (hero.buff(MagicImmune.class) != null) return;
		//spaghetti code

		if (action.equals(AC_EAT) || action.equals(AC_SNACK)){

			if (!isEquipped(hero)) GLog.i( Messages.get(Artifact.class, "need_to_equip") );
			else if (charge == 0)  GLog.i( Messages.get(this, "no_food") );
			else {
				//consume as much food as it takes to be full, to a minimum of 1
				int chargesToUse = charge;


				//always use 1 charge if snacking
				if (action.equals(AC_SNACK)){
					chargesToUse = 1;
				}

				charge -= chargesToUse;
				Talent.onArtifactUsed(hero);

				hero.sprite.operate(hero.pos);
				hero.busy();
				SpellSprite.show(hero, SpellSprite.FOOD);
				Sample.INSTANCE.play(Assets.Sounds.EAT);
				GLog.i( Messages.get(this, "eat") );

				if (Dungeon.hero.hasTalent(Talent.IRON_STOMACH)
						|| Dungeon.hero.hasTalent(Talent.ENERGIZING_MEAL)
						|| Dungeon.hero.hasTalent(Talent.MYSTICAL_MEAL)
						|| Dungeon.hero.hasTalent(Talent.INVIGORATING_MEAL)
						|| Dungeon.hero.hasTalent(Talent.FOCUSED_MEAL)){
					hero.spend(timeToEat * chargesToUse/2);
				} else {
					hero.spend(timeToEat * chargesToUse);
				}
				Buff.affect(hero, Healing.class).setHeal((int)(hero.HT*(healingPercentage*chargesToUse)),0.05f,1);

				Talent.onFoodEaten(hero, chargesToUse, this);

				int oldImage = image;
				if (charge >= 15)       image = ItemSpriteSheet.ARTIFACT_HORN4;
				else if (charge >= 10)  image = ItemSpriteSheet.ARTIFACT_HORN3;
				else if (charge >= 5)   image = ItemSpriteSheet.ARTIFACT_HORN2;
				else                    image = ItemSpriteSheet.ARTIFACT_HORN1;

				updateQuickslot();
			}

		} else if (action.equals(AC_STORE)){

			GameScene.selectItem(itemSelector);

		}
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new hornRecharge();
	}
	
	@Override
	public void charge(Hero target, float amount) {
		if (charge < chargeCap && !cursed && target.buff(MagicImmune.class) == null){
			partialCharge += 0.25f*amount;
			if (partialCharge >= 1){
				partialCharge--;
				charge++;
				
				if (charge == chargeCap){
					GLog.p( Messages.get(HornOfPlenty.class, "full") );
					partialCharge = 0;
				}

				int oldImage = image;
				if (charge >= 15)       image = ItemSpriteSheet.ARTIFACT_HORN4;
				else if (charge >= 10)  image = ItemSpriteSheet.ARTIFACT_HORN3;
				else if (charge >= 5)   image = ItemSpriteSheet.ARTIFACT_HORN2;
				else                    image = ItemSpriteSheet.ARTIFACT_HORN1;

				updateQuickslot();
			}
		}
	}
	
	@Override
	public String desc() {
		String desc = super.desc();

		if ( isEquipped( Dungeon.hero ) ){
			if (!cursed) {
				if (level() < levelCap)
					desc += "\n\n" +Messages.get(this, "desc_hint");
			} else {
				desc += "\n\n" +Messages.get(this, "desc_cursed");
			}
		}

		return desc;
	}

	@Override
	public void level(int value) {
		super.level(value);
		chargeCap = 5 + level()/2;
	}

	@Override
	public Item upgrade() {
		super.upgrade();
		chargeCap = 5 + level()/2;
		return this;
	}
	
	public void gainFoodValue( Food food ){
		if (level() >= 10) return;
		
		storedFoodEnergy += food.energy;
		//Pasties are worth two upgrades instead of 1.5, meat pies are worth 4 instead of 3!
		if (food instanceof Pasty){
			storedFoodEnergy += Hunger.HUNGRY/2;
		} else if (food instanceof MeatPie){
			storedFoodEnergy += Hunger.HUNGRY;
		}
		if (storedFoodEnergy >= Hunger.HUNGRY){
			int upgrades = storedFoodEnergy / (int)Hunger.HUNGRY;
			upgrades = Math.min(upgrades, 10 - level());
			upgrade(upgrades);
			storedFoodEnergy -= upgrades * Hunger.HUNGRY;
			if (level() == 10){
				storedFoodEnergy = 0;
				GLog.p( Messages.get(this, "maxlevel") );
			} else {
				GLog.p( Messages.get(this, "levelup") );
			}
		} else {
			GLog.i( Messages.get(this, "feed") );
		}
	}
	
	private static final String STORED = "stored";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put( STORED, storedFoodEnergy );
	}
	
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		//pre-2.0.0 saves
		if (partialCharge > 1){
			partialCharge /= Hunger.STARVING/5f;
		}

		storedFoodEnergy = bundle.getInt(STORED);
		
		if (charge >= 8)       image = ItemSpriteSheet.ARTIFACT_HORN4;
		else if (charge >= 5)  image = ItemSpriteSheet.ARTIFACT_HORN3;
		else if (charge >= 2)   image = ItemSpriteSheet.ARTIFACT_HORN2;
	}

	public class hornRecharge extends ArtifactBuff{

		public void gainCharge(float levelPortion) {
			if (cursed || target.buff(MagicImmune.class) != null) return;
			
			if (charge < chargeCap) {

				//generates 0.25x max hunger value every hero level, +0.125x max value per horn level
				//to a max of 1.5x max hunger value per hero level
				//This means that a standard ration will be recovered in ~5.333 hero levels
				float chargeGain = Hunger.STARVING * levelPortion * (0.25f + (0.125f*level()));
				chargeGain *= RingOfEnergy.artifactChargeMultiplier(target);

				//each charge is equal to 1/5 the max hunger value
				chargeGain /= Hunger.STARVING/5;
				partialCharge += chargeGain;

				//charge is in increments of 1/5 max hunger value.
				while (partialCharge >= 1) {
					charge++;
					partialCharge -= 1;

					int oldImage = image;
					if (charge >= 8)        image = ItemSpriteSheet.ARTIFACT_HORN4;
					else if (charge >= 5)   image = ItemSpriteSheet.ARTIFACT_HORN3;
					else if (charge >= 2)   image = ItemSpriteSheet.ARTIFACT_HORN2;
					else                    image = ItemSpriteSheet.ARTIFACT_HORN1;

					updateQuickslot();

					if (charge == chargeCap){
						GLog.p( Messages.get(HornOfPlenty.class, "full") );
						partialCharge = 0;
					}
				}
			} else {
				partialCharge = 0;
			}
		}

	}

	protected static WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {

		@Override
		public String textPrompt() {
			return Messages.get(HornOfPlenty.class, "prompt");
		}

		@Override
		public Class<?extends Bag> preferredBag(){
			return Belongings.Backpack.class;
		}

		@Override
		public boolean itemSelectable(Item item) {
			return item instanceof Food;
		}

		@Override
		public void onSelect( Item item ) {
			if (item != null && item instanceof Food) {
				if (item instanceof Blandfruit && ((Blandfruit) item).potionAttrib == null){
					GLog.w( Messages.get(HornOfPlenty.class, "reject") );
				} else {
					Hero hero = Dungeon.hero;
					hero.sprite.operate( hero.pos );
					hero.busy();
					hero.spend( 1f );

					((HornOfPlenty)curItem).gainFoodValue(((Food)item));
					item.detach(hero.belongings.backpack);
				}

			}
		}
	};
}
