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
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MagicImmune;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Earthroot;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ChaliceOfBlood extends Artifact {

	{
		image = ItemSpriteSheet.ARTIFACT_CHALICE1;

		cursed = true;
		visiblyCursed();

		levelCap = 10;
	}

	public static final String AC_PRICK = "PRICK";

	public int attempts=0;


	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = new ArrayList<>();
		actions.add( AC_DROP );
		actions.add( AC_THROW );
		if (cursed) actions.add( isEquipped( hero ) ? AC_UNEQUIP : AC_EQUIP );
		if (isEquipped( hero )
				&& level() < levelCap
				&& !hero.isInvulnerable(getClass())
				&& hero.buff(MagicImmune.class) == null)
			actions.add(AC_PRICK);
		return actions;
	}

	@Override
	public boolean doEquip(Hero hero) {
		if (cursed){
			Badges.validateBloodCultist(1);
			return super.doEquip(hero);
		}
		else {
			GLog.i(Messages.get(ChaliceOfBlood.class, "no_equip"));
			return false;
		}
	}

	@Override
	public void execute(Hero hero, String action ) {
		super.execute(hero, action);

		if (action.equals(AC_PRICK)){

			int damage = 5 + 3*(level()*level()) + (int)(((float)level()*hero.HT)/50);

			if (damage > hero.HP*0.75) {

				GameScene.show(
					new WndOptions(new ItemSprite(this),
							Messages.titleCase(name()),
							Messages.get(this, "prick_warn"),
							Messages.get(this, "yes"),
							Messages.get(this, "no")) {
						@Override
						protected void onSelect(int index) {
							if (index == 0)
								prick(Dungeon.hero);
						}
					}
				);

			} else {
				prick(hero);
			}
		}
	}

	private void prick(Hero hero){
		int damage = 10 + 5*(level());

		Earthroot.Armor armor = hero.buff(Earthroot.Armor.class);
		if (armor != null) {
			damage = armor.absorb(damage);
		}

		WandOfLivingEarth.RockArmor rockArmor = hero.buff(WandOfLivingEarth.RockArmor.class);
		if (rockArmor != null) {
			damage = rockArmor.absorb(damage);
		}

		damage -= hero.drRoll();

		hero.sprite.operate( hero.pos );
		hero.busy();
		hero.spend(3f);
		GLog.w( Messages.get(this, "onprick") );
		if (damage <= 0){
			damage = 1;
		} else {
			Sample.INSTANCE.play(Assets.Sounds.CURSED);
			hero.sprite.emitter().burst( ShadowParticle.CURSE, 4+(damage/10) );
		}

		hero.damage(damage, this);

		if (!hero.isAlive()) {
			Badges.validateDeathFromFriendlyMagic();
			Dungeon.fail( getClass() );
			GLog.n( Messages.get(this, "ondeath") );
		} else {
			upgrade();
		}
	}

	@Override
	public Item upgrade() {
		if (level() >=9) Badges.validateBloodCultist(2);
		if (level() >= 6)
			image = ItemSpriteSheet.ARTIFACT_CHALICE3;
		else if (level() >= 2)
			image = ItemSpriteSheet.ARTIFACT_CHALICE2;
		return super.upgrade();
	}



	@Override
	protected ArtifactBuff passiveBuff() {
		return new ChaliceRegen();
	}
	
	@Override
	public void charge(Hero target, float amount) {
		if (cursed || target.buff(MagicImmune.class) != null) return;

		//grants 5 turns of healing up-front, if hero isn't starving
		if (target.isStarving()) return;

		float healDelay = 10f - (1f + level()*0.85f);
		healDelay /= amount;
		float heal = 5f/healDelay;
		//effectively 0.5/1/1.5/2/2.5 HP per turn at +0/+6/+8/+9/+10
		if (Random.Float() < heal%1){
			heal++;
		}
		if (heal >= 1f) {
			target.HP = Math.min(target.HT, target.HP + (int)heal);
		}
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (attempts<5){
			GLog.w(Messages.get(ChaliceOfBlood.class, "no_unequip"));
			hero.damage(hero.HP/10*attempts + hero.HT/20*attempts, this);
			attempts++;
			Camera.main.shake(attempts*10+1, 0.15f*attempts);
			Sample.INSTANCE.play(Assets.Sounds.HEALTH_WARN);
			return false;
		} else {
			Badges.validateRedemption();
			Sample.INSTANCE.play(Assets.Sounds.CURSED);
			Sample.INSTANCE.play(Assets.Sounds.SHATTER);
			for (int i : PathFinder.NEIGHBOURS9){
				CellEmitter.get(i+hero.pos).start(ShadowParticle.UP, 0.05f, 10);
			}
			level(0);
			this.cursed =this.cursedKnown = false;
			return super.doUnequip(hero, collect, single);
		}
	}

	@Override
	public String desc() {
		String desc = super.desc();

		if (!cursed){
			desc = Messages.get(this, "uncursed_desc");
		} else if (isEquipped (Dungeon.hero)){
			desc += "\n\n";
			if (level() == 0)
				desc += Messages.get(this, "desc_1");
			else if (level() < levelCap)
				desc += Messages.get(this, "desc_2");
			else
				desc += Messages.get(this, "desc_3");
		}

		return desc;
	}

	public class ChaliceRegen extends ArtifactBuff {
		//see Regeneration.class for effect
	}


	public static final String ATTEMPTS = "attempts";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ATTEMPTS, attempts);
	}
	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		attempts = bundle.getInt(ATTEMPTS);
		if (level() >= 7) image = ItemSpriteSheet.ARTIFACT_CHALICE3;
		else if (level() >= 3) image = ItemSpriteSheet.ARTIFACT_CHALICE2;
	}

}
