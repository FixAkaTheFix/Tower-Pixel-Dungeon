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

package com.fixakathefix.towerpixeldungeon.actors;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.items.Item.updateQuickslot;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Challenges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Electricity;
import com.fixakathefix.towerpixeldungeon.actors.blobs.ToxicGas;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Adrenaline;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AllyBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ArcaneArmor;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barkskin;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Berserk;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Charm;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Corrosion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Cripple;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Doom;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Dread;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Eating;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FireImbue;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Frost;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FrostImbue;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Fury;
import com.fixakathefix.towerpixeldungeon.actors.buffs.GoldArmor;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Haste;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hex;
import com.fixakathefix.towerpixeldungeon.actors.buffs.KeenEye;
import com.fixakathefix.towerpixeldungeon.actors.buffs.LifeLink;
import com.fixakathefix.towerpixeldungeon.actors.buffs.LostInventory;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MagicalSleep;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Momentum;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MonkEnergy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Ooze;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Poison;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Preparation;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Protected;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Rush;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ShieldBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Slow;
import com.fixakathefix.towerpixeldungeon.actors.buffs.SnipersMark;
import com.fixakathefix.towerpixeldungeon.actors.buffs.SoulBleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Stamina;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Terror;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Undying;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vile;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vulnerable;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WallStance;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Weakness;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroSubClass;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.duelist.Challenge;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.DeathMark;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.warrior.Endure;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Tengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.MirrorImage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.PrismaticImage;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.armor.glyphs.Potential;
import com.fixakathefix.towerpixeldungeon.items.armor.glyphs.Viscosity;
import com.fixakathefix.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.fixakathefix.towerpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfElements;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfDemonicSkull;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfFireblast;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfFrost;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLightning;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Blazing;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Empowering;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Grim;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Kinetic;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Pure;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Shocking;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.ShockingDart;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.features.Chasm;
import com.fixakathefix.towerpixeldungeon.levels.features.Door;
import com.fixakathefix.towerpixeldungeon.levels.traps.GrimTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Earthroot;
import com.fixakathefix.towerpixeldungeon.plants.Swiftthistle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.utils.BArray;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public abstract class Char extends Actor {
	
	public int pos = 0;
	
	public CharSprite sprite;

	public int HT;
	public int HP;
	public float critChance = 0.025f;
	public float critMult = 1.5f;
	
	protected float baseSpeed	= 1;
	protected PathFinder.Path path;

	public int paralysed	    = 0;
	public boolean rooted		= false;
	public boolean flying		= false;
	public int invisible		= 0;
	
	//these are relative to the hero
	public enum Alignment{
		ENEMY,
		NEUTRAL,
		ALLY
	}
	public Alignment alignment;
	
	public int viewDistance	= 12;
	
	public boolean[] fieldOfView = null;

	public float critMult(){
		return this.critMult;
	}

	public float critChance(){
		return this.critChance;
	}
	
	private LinkedHashSet<Buff> buffs = new LinkedHashSet<>();
	
	@Override
	protected boolean act() {
		if (DeviceCompat.isDebug()) DeviceCompat.log( "CHAR ACTING",
				name()+
						" with HP of "+
						HP+
						"/"+
						HT+
						" on position of "+
						"("+
						pos % Dungeon.level.width()+
						";"+
						pos / Dungeon.level.width()+
						")" + " acted" );
		if (fieldOfView == null || fieldOfView.length != Dungeon.level.length()){
			fieldOfView = new boolean[Dungeon.level.length()];
		}
		Dungeon.level.updateFieldOfView( this, fieldOfView );

		//throw any items that are on top of an immovable char
		if (properties().contains(Property.IMMOVABLE)){
			throwItems();
		}
		return false;
	}

	protected void throwItems(){
		Heap heap = Dungeon.level.heaps.get( pos );
		if (heap != null && heap.type == Heap.Type.HEAP) {
			int n;
			do {
				n = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			} while (!Dungeon.level.passable[n] && !Dungeon.level.avoid[n]);
			Item item = heap.peek();
			if (!(item instanceof Tengu.BombAbility.BombItem || item instanceof Tengu.ShockerAbility.ShockerItem)){
				Dungeon.level.drop( heap.pickUp(), n ).sprite.drop( pos );
			}
		}
	}

	public String name(){
		return Messages.get(this, "name");
	}

	public boolean canInteract(Char c){
		if (Dungeon.level.adjacent( pos, c.pos )){
			return true;
		} else if (c instanceof Hero
				&& alignment == Alignment.ALLY
				&& Dungeon.level.distance(pos, c.pos) <= 2* hero.pointsInTalent(Talent.ALLY_WARP)){
			return true;
		} else {
			return false;
		}
	}
	
	//swaps places by default
	public boolean interact(Char c){

		//don't allow char to swap onto hazard unless they're flying
		//you can swap onto a hazard though, as you're not the one instigating the swap
		if (!Dungeon.level.passable[pos] && !c.flying){
			return true;
		}

		if (Dungeon.level.herounpassable[pos] && this instanceof Hero){
			return true;
		}


		//can't swap into a space without room
		if (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[c.pos]
			|| c.properties().contains(Property.LARGE) && !Dungeon.level.openSpace[pos]){
			return true;
		}

		int curPos = pos;

		//warp instantly with allies in this case
		if (c == hero && hero.hasTalent(Talent.ALLY_WARP)){
			PathFinder.buildDistanceMap(c.pos, BArray.or(Dungeon.level.passable, Dungeon.level.avoid, null));
			if (PathFinder.distance[pos] == Integer.MAX_VALUE){
				return true;
			}
			ScrollOfTeleportation.appear(this, c.pos);
			ScrollOfTeleportation.appear(c, curPos);
			Dungeon.observe();
			GameScene.updateFog();
			return true;
		}

		//can't swap places if one char has restricted movement
		if (rooted || c.rooted || buff(Vertigo.class) != null || c.buff(Vertigo.class) != null){
			return true;
		}
		//don't swap with chars on herobarriers
		if (!(this instanceof Hero && Dungeon.level.herounpassable[c.pos])) {
			moveSprite(pos, c.pos);
			move(c.pos);

			c.sprite.move(c.pos, curPos);
			c.move(curPos);
		}
		
		c.spend( 1 / c.speed() );

		if (c == hero){
			if (hero.subClass == HeroSubClass.FREERUNNER){
				Buff.affect(hero, Momentum.class).gainStack();
			}

			hero.busy();
		}
		
		return true;
	}
	public void swapPlacesWith (Char c){
		int curPos = pos;
		moveSprite(pos, c.pos);
		move(c.pos);

		c.sprite.move(c.pos, curPos);
		c.move(curPos);
	}
	
	protected boolean moveSprite( int from, int to ) {
		
		if (sprite.isVisible() && (Dungeon.level.heroFOV[from] || Dungeon.level.heroFOV[to])) {
			sprite.move( from, to );
			return true;
		} else {
			sprite.turnTo(from, to);
			sprite.place( to );
			return true;
		}
	}

	public void hitSound( float pitch ){
		Sample.INSTANCE.play(Assets.Sounds.HIT, 1, pitch);
	}

	public boolean blockSound( float pitch ) {
		return false;
	}
	
	protected static final String POS       = "pos";
	protected static final String TAG_HP    = "HP";
	protected static final String TAG_HT    = "HT";
	protected static final String TAG_SHLD  = "SHLD";
	protected static final String BUFFS	    = "buffs";

	protected static final String ALIGNMENT = "alignment";

	
	@Override
	public void storeInBundle( Bundle bundle ) {
		
		super.storeInBundle( bundle );
		bundle.put(ALIGNMENT, alignment);
		bundle.put( POS, pos );
		bundle.put( TAG_HP, HP );
		bundle.put( TAG_HT, HT );
		bundle.put( BUFFS, buffs );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		
		super.restoreFromBundle( bundle );
		
		pos = bundle.getInt( POS );
		HP = bundle.getInt( TAG_HP );
		HT = bundle.getInt( TAG_HT );
		alignment = bundle.getEnum(ALIGNMENT, Alignment.class);
		
		for (Bundlable b : bundle.getCollection( BUFFS )) {
			if (b != null) {
				((Buff)b).attachTo( this );
			}
		}
	}


	final public boolean attack( Char enemy ){
		return attack(enemy, 1f, 0f, 1f);
	}
	public boolean critProc;
	public boolean attack( Char enemy, float dmgMulti, float dmgBonus, float accMulti ) {
		if (DeviceCompat.isDebug())
			try {
				DeviceCompat.log("CHAR ATTACKING",
						name() +
								" on position of " +
								"(" +
								pos % Dungeon.level.width() +
								";" +
								pos / Dungeon.level.width() +
								")" + " attacked " + enemy.name());
			} catch (Exception exception) {
				DeviceCompat.log("NULL ENEMY ATTACKED BY",
						name() + " on position of " +
								"(" +
								pos % Dungeon.level.width() +
								";" +
								pos / Dungeon.level.width() +
								")");

		};


		if (enemy == null) return false;
		
		boolean visibleFight = Dungeon.level.heroFOV[pos] || Dungeon.level.heroFOV[enemy.pos];

		if (enemy.isInvulnerable(getClass())) {

			if (visibleFight) {
				enemy.sprite.showStatus( CharSprite.HOLY, Messages.get(this, "invulnerable") );

				Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY, 1f, Random.Float(0.96f, 1.05f));
			}

			return false;

		} else if (hit( this, enemy, accMulti, DamageType.PHYSICAL )) {
			
			int dr = Math.round(enemy.drRoll() * AscensionChallenge.statModifier(enemy));

			
			if (this instanceof Hero){
				Hero h = (Hero)this;
				if (h.belongings.attackingWeapon() instanceof MissileWeapon
						&& h.subClass == HeroSubClass.SNIPER
						&& !Dungeon.level.adjacent(h.pos, enemy.pos)){
					dr = 0;
				}

				if (h.buff(MonkEnergy.MonkAbility.UnarmedAbilityTracker.class) != null){
					dr = 0;
				} else if (h.subClass == HeroSubClass.MONK) {
					//3 turns with standard attack delay
					Buff.prolong(h, MonkEnergy.MonkAbility.JustHitTracker.class, 4f);
				}

				if (this instanceof Hero && h.belongings.weapon!=null) if (((Weapon)h.belongings.attackingWeapon()).hasEnchant(Pure.class, hero)) dr = 0;//pure damage
			}


			//we use a float here briefly so that we don't have to constantly round while
			// potentially applying various multiplier effects
			float dmg;
			Preparation prep = buff(Preparation.class);
			if (prep != null){
				dmg = prep.damageRoll(this);
				if (this == hero && hero.hasTalent(Talent.BOUNTY_HUNTER)) {
					Buff.affect(hero, Talent.BountyHunterTracker.class, 0.0f);
				}
			} else {
				dmg = damageRoll();
			}

			dmg = Math.round(dmg*dmgMulti);

			Berserk berserk = buff(Berserk.class);
			if (berserk != null) dmg = berserk.damageFactor(dmg);

			if (buff( Fury.class ) != null) {
				dmg *= 1.5f;
			}

			for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
				dmg *= buff.meleeDamageFactor();
			}

			if (buff( Vile.class ) != null) {
				dmg *= 1.3f;
			}

			dmg *= AscensionChallenge.statModifier(this);

			//flat damage bonus is applied after positive multipliers, but before negative ones
			dmg += dmgBonus;

			//friendly endure
			Endure.EndureTracker endure = buff(Endure.EndureTracker.class);
			if (endure != null) dmg = endure.damageFactor(dmg);

			//enemy endure
			endure = enemy.buff(Endure.EndureTracker.class);
			if (endure != null){
				dmg = endure.adjustDamageTaken(dmg);
			}

			if (enemy.buff(ScrollOfChallenge.ChallengeArena.class) != null){
				dmg *= 0.67f;
			}

			if (enemy.buff(MonkEnergy.MonkAbility.Meditate.MeditateResistance.class) != null){
				dmg *= 0.2f;
			}

			if ( buff(Weakness.class) != null ){
				dmg *= 0.67f;
			}
			if ( buff(Strength.class) != null ){
				dmg *= 1.5f;
			}
			if ( buff(Battlecry.class) != null ){
				dmg *= 1.2f;
			}
			//Code for crit procs
			float critChance = this.critChance();
			if (buff(KeenEye.class)!=null)critChance = 1f;
			if (critChance < 0) critChance = 0;

			float critMult = this.critMult();
			if (critMult < 0) critMult = 0;

			int effectiveDamage = enemy.defenseProc( this, Math.round(dmg) );
			float ch = Random.Float();// is the hit critical?
			if  (ch < critChance) {
				enemy.critProc = true;// the hit is critical!
				effectiveDamage = Math.round(effectiveDamage * critMult);

			} else enemy.critProc = false;

			//if DamageSource.MAGICAL.contains()


			if (!DamageSource.MAGICAL.contains(this.getClass())) {
				effectiveDamage = Math.max( effectiveDamage - dr, 0 );
			} // damage reduction applied, is not applied when hit by a magical mob, such as towerwands.

			if (!DamageSource.MAGICAL.contains(this.getClass()) && enemy.buff( Protected.class ) != null){
				effectiveDamage *= 0.5f;
			}

			if (enemy.buff(Viscosity.ViscosityTracker.class) != null){
				effectiveDamage = enemy.buff(Viscosity.ViscosityTracker.class).deferDamage(effectiveDamage);
				enemy.buff(Viscosity.ViscosityTracker.class).detach();
			}

			//vulnerable specifically applies after armor reductions
			if ( enemy.buff( Vulnerable.class ) != null){
				effectiveDamage *= 1.33f;
			}
			ChaliceOfBlood.ChaliceRegen chaliceRegen = enemy.buff( ChaliceOfBlood.ChaliceRegen.class );
			if ( chaliceRegen != null){
				Buff.affect(enemy, SoulBleeding.class).prolong((int) ((float)(chaliceRegen.itemLevel() * hero.HT)/70));
				effectiveDamage /= (((float)chaliceRegen.itemLevel()*2 + 10)/10);
			}

			
			effectiveDamage = attackProc( enemy, effectiveDamage );
			
			if (visibleFight) {
				if (effectiveDamage > 0 || !enemy.blockSound(Random.Float(0.96f, 1.05f))) {
					hitSound(Random.Float(0.87f, 1.15f));
				}
			}

			// If the enemy is already dead, interrupt the attack.
			// This matters as defence procs can sometimes inflict self-damage, such as armor glyphs.
			if (!enemy.isAlive()){
				return true;
			}

			enemy.damage( effectiveDamage, this );
			//if (buff(Vampirism.class) != null) Buff.affect(this, Healing.class).setHeal(effectiveDamage/3,0.01f,0);

			if (buff(FireImbue.class) != null)  buff(FireImbue.class).proc(enemy);
			if (buff(FrostImbue.class) != null) buff(FrostImbue.class).proc(enemy);

			if (enemy.isAlive() && enemy.alignment != alignment && prep != null && prep.canKO(enemy)){
				enemy.HP = 0;
				if (!enemy.isAlive()) {
					enemy.die(this);
				} else {
					//helps with triggering any on-damage effects that need to activate
					enemy.damage(-1, this);
					DeathMark.processFearTheReaper(enemy);
				}
				enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Preparation.class, "assassinated"));
			}

			Talent.CombinedLethalityTriggerTracker combinedLethality = buff(Talent.CombinedLethalityTriggerTracker.class);
			if (combinedLethality != null){
				if ( enemy.isAlive() && enemy.alignment != alignment && !Char.hasProp(enemy, Property.BOSS)
						&& !Char.hasProp(enemy, Property.MINIBOSS) && this instanceof Hero &&
						(enemy.HP/(float)enemy.HT) <= 0.10f*((Hero)this).pointsInTalent(Talent.COMBINED_LETHALITY)) {
					enemy.HP = 0;
					if (!enemy.isAlive()) {
						enemy.die(this);
					} else {
						//helps with triggering any on-damage effects that need to activate
						enemy.damage(-1, this);
						DeathMark.processFearTheReaper(enemy);
					}
					enemy.sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Talent.CombinedLethalityTriggerTracker.class, "executed"));
				}
				combinedLethality.detach();
			}

			enemy.sprite.bloodBurstA( sprite.center(), effectiveDamage );
			enemy.sprite.flash();
			if (!enemy.isAlive() && visibleFight) {
				if (enemy == hero) {
					
					if (this == hero) {
						return true;
					}

					if (this instanceof WandOfLivingEarth.EarthGuardian
							|| this instanceof MirrorImage || this instanceof PrismaticImage){
						Badges.validateDeathFromFriendlyMagic();
					}
					Dungeon.fail( getClass() );
					GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
					
				} else if (this == hero) {
					GLog.i( Messages.capitalize(Messages.get(Char.class, "defeat", enemy.name())) );
				}
			}
			if (this == hero && Dungeon.isChallenged(Challenges.VAMPIRE)) Buff.affect(hero, Healing.class).setHeal(Math.max(effectiveDamage/5, 1),0.25f,0);

			return true;
			
		} else {

			enemy.sprite.showStatus( CharSprite.NEUTRAL, enemy.defenseVerb() );
			if (visibleFight) {
				//TODO enemy.defenseSound? currently miss plays for monks/crab even when they parry
				Sample.INSTANCE.play(Assets.Sounds.MISS);
			}
			
			return false;
			
		}
	}

	public static int INFINITE_ACCURACY = 1_000_000;
	public static int INFINITE_EVASION = 1_000_000;

	final public static boolean hit( Char attacker, Char defender, DamageType type ) {
		return hit(attacker, defender, type==DamageType.MAGICAL ? 2f : 1f, type);
	}

	public static boolean hit( Char attacker, Char defender, float accMulti, DamageType type ) {
		float acuStat = attacker.attackSkill( defender );
		float defStat = defender.defenseSkill( attacker );
		//float mDefStat = defender.magicDefenseSkill( attacker );

		//invisible chars always hit (for the hero this is surprise attacking)
		if (attacker.invisible > 0 && attacker.canSurpriseAttack()){
			acuStat = INFINITE_ACCURACY;
		}

		if (defender.buff(MonkEnergy.MonkAbility.Focus.FocusBuff.class) != null && (type == DamageType.PHYSICAL)){
			defStat = INFINITE_EVASION;
		}

		//if accuracy or evasion are large enough, treat them as infinite.
		//note that infinite evasion beats infinite accuracy
		if (defStat >= INFINITE_EVASION){
			return false;
		} else if (acuStat >= INFINITE_ACCURACY){
			return true;
		}

		float acuRoll = Random.Float( acuStat );
		if (attacker.buff(Bless.class) != null) acuRoll *= 1.25f;
		if (attacker.buff(  Hex.class) != null) acuRoll *= 0.8f;
		Weapon temp = (Weapon)hero.belongings.weapon;
		if (hero.belongings.weapon!=null) if (attacker == hero &&  temp.hasEnchant(Empowering.class, hero)) acuRoll *= 1.05f; //makes ALL the hero's attacks more accurate while he is holding an empowering weapon
		for (ChampionEnemy buff : attacker.buffs(ChampionEnemy.class)){
			acuRoll *= buff.evasionAndAccuracyFactor();
		}
		acuRoll *= AscensionChallenge.statModifier(attacker);
		
		float defRoll = Random.Float( defStat );
		if (defender.buff(Bless.class) != null) defRoll *= 1.25f;//THERE ARE THE BUFFS
		if (defender.buff(  Hex.class) != null) defRoll *= 0.8f;
		//if defender.buff( Susceptible.class) != null) mDefRoll *= 0.8
		for (ChampionEnemy buff : defender.buffs(ChampionEnemy.class)){
			defRoll *= buff.evasionAndAccuracyFactor();
		}
		defRoll *= AscensionChallenge.statModifier(defender);
		
		return (acuRoll * accMulti) >= defRoll;
	}
	
	public int attackSkill( Char target ) {
		return 0;
	}
	
	public int defenseSkill( Char enemy ) {
		return 0;
	}

	//public int magicDefenseSkill( Char enemy ) {
	//	return 0;
	//}
	
	public String defenseVerb() {
		return Messages.get(this, "def_verb");
	}

	
	public int drRoll() {
		int dr = 0;

		Barkskin bark = buff(Barkskin.class);
		if (bark != null)   dr += Random.NormalIntRange( 0 , bark.level() );

		return dr;
	}

	public void speak(String speakText, int color)
	{
		this.sprite.showStatus(color, speakText);
	}
	
	public int damageRoll() {
		return 1;
	}
	
	//TODO it would be nice to have a pre-armor and post-armor proc.
	// atm attack is always post-armor and defence is already pre-armor
	
	public int attackProc( Char enemy, int damage ) {
		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			buff.onAttackProc( enemy );
		}
		return damage;
	}
	
	public int defenseProc( Char enemy, int damage ) {

		Earthroot.Armor armor = buff( Earthroot.Armor.class );
		if (armor != null) {
			damage = armor.absorb( damage );
		}

		return damage;
	}
	
	public float speed() {
		float speed = baseSpeed;
		if ( buff( Cripple.class ) != null ) speed /= 2f;
		if ( buff( Stamina.class ) != null) speed *= 1.5f;
		if ( buff( Rush.class ) != null) speed *= 5f;
		if ( buff( Adrenaline.class ) != null) speed *= 2f;
		if ( buff( Haste.class ) != null) speed *= 3f;
		if ( buff( Dread.class ) != null) speed *= 2f;
		return speed;
	}

	//currently only used by invisible chars, or by the hero
	public boolean canSurpriseAttack(){
		return true;
	}
	public boolean canGetSurpriseAttacked(){
		return true;
	}
	
	//used so that buffs(Shieldbuff.class) isn't called every time unnecessarily
	private int cachedShield = 0;
	public boolean needsShieldUpdate = true;
	
	public int shielding(){
		if (!needsShieldUpdate){
			return cachedShield;
		}
		
		cachedShield = 0;
		for (ShieldBuff s : buffs(ShieldBuff.class)){
			cachedShield += s.shielding();
		}
		needsShieldUpdate = false;
		return cachedShield;
	}
	
	public void damage( int dmg, Object src) {
		
		if (!isAlive() || dmg < 0) {
			return;
		}

		if(isInvulnerable(src.getClass())){
			sprite.showStatus(CharSprite.HOLY, Messages.get(this, "invulnerable"));
			return;
		}

		for (ChampionEnemy buff : buffs(ChampionEnemy.class)){
			if (DamageSource.MAGICAL.contains(src)){
				dmg = (int) Math.ceil(dmg * buff.magicalDamageTakenFactor());
			} else {
				dmg = (int) Math.ceil(dmg * buff.physicalDamageTakenFactor());
			}
		}
		dmg = (int)Math.ceil(dmg / AscensionChallenge.statModifier(this));

		if (!(src instanceof LifeLink) && buff(LifeLink.class) != null){
			HashSet<LifeLink> links = buffs(LifeLink.class);
			for (LifeLink link : links.toArray(new LifeLink[0])){
				if (Actor.findById(link.object) == null){
					links.remove(link);
					link.detach();
				}
			}
			dmg = (int)Math.ceil(dmg / (float)(links.size()+1));
			for (LifeLink link : links){
				Char ch = (Char)Actor.findById(link.object);
				if (ch != null) {
					ch.damage(dmg, link);
					if (!ch.isAlive()) {
						link.detach();
					}
				}
			}
		}

		Terror t = buff(Terror.class);
		if (t != null){
			t.recover();
		}
		Dread d = buff(Dread.class);
		if (d != null){
			d.recover();
		}
		Charm c = buff(Charm.class);
		if (c != null){
			c.recover(src);
		}
		if (this.buff(Frost.class) != null){
			Buff.detach( this, Frost.class );
		}
		if (this.buff(MagicalSleep.class) != null){
			Buff.detach(this, MagicalSleep.class);
		}
		if (this.buff(Doom.class) != null && !isImmune(Doom.class)){
			dmg *= 1.67f;
		}
		if (this.buff(Vile.class) != null && !isImmune(Doom.class)){
			dmg *= 1.15f;
		}
		if (alignment != Alignment.ALLY && this.buff(DeathMark.DeathMarkTracker.class) != null){
			dmg *= 1.25f;
		}
		
		Class<?> srcClass = src.getClass();
		if (isImmune( srcClass )) {
			dmg = 0;
		} else {
			dmg = Math.round( dmg * resist( srcClass ));
		}
		
		//TODO improve this when I have proper damage source logic
		if (DamageSource.MAGICAL.contains(src.getClass()) && buff(ArcaneArmor.class) != null){
			dmg -= Random.NormalIntRange(0, buff(ArcaneArmor.class).level());
			if (dmg < 0) dmg = 0;
		}
		if (dmg < 1 && (this.alignment==Alignment.ALLY || this instanceof Hero)) dmg = 1; // balance for rats being able to destroy stuff if there is a lot of high-level defenses and no armor being able to fully negate the damage
		
		if (buff( Paralysis.class ) != null) {
			buff( Paralysis.class ).processDamage(dmg);
		}
		if (buff( Eating.class ) != null) {
			buff( Eating.class ).processDamage();
		}
		if (buff(WallStance.class)!=null && !(DamageSource.MAGICAL.contains(src.getClass()))){
			dmg /=2;
		}
		if (this == Dungeon.hero && hero.heroClass == HeroClass.TANK && !(DamageSource.MAGICAL.contains(src.getClass()))) {
			dmg = Math.round(dmg * 0.75f);
		}

		//for GoldArmor buff
		if (buff(GoldArmor.class) != null){
			if (Dungeon.gold >= (dmg * 1.5f)) {
				Dungeon.gold -= (dmg * 1.5f);
				updateQuickslot();
				sprite.showStatus(CharSprite.WARNING, Integer.toString(dmg));
				return;
			} else {
				Buff.detach(this, GoldArmor.class);
			}
		}

		int shielded = dmg;
		//FIXME: when I add proper damage properties, should add an IGNORES_SHIELDS property to use here. FixakaTheFix: DONE!)!)!)!)
		if (!DamageSource.SHIELDIGNORING.contains(src.getClass())){
			for (ShieldBuff s : buffs(ShieldBuff.class)){
				dmg = s.absorbDamage(dmg);
				if (dmg == 0) break;
			}
		}

		shielded -= dmg;
		HP -= dmg;

		if (HP < 0 && src instanceof Char){
			if (((Char) src).buff(Kinetic.KineticTracker.class) != null){
				int dmgToAdd = -HP;
				dmgToAdd -= ((Char) src).buff(Kinetic.KineticTracker.class).conservedDamage;
				dmgToAdd = Math.round(dmgToAdd * Weapon.Enchantment.genericProcChanceMultiplier((Char) src));
				if (dmgToAdd > 0) {
					Buff.affect((Char) src, Kinetic.ConservedDamage.class).setBonus(dmgToAdd);
				}
				((Char) src).buff(Kinetic.KineticTracker.class).detach();
			}
		}



		
		if (sprite != null) {
			if (sprite instanceof BossRatKingSprite && dmg == 0 || DamageSource.NOTDISPLAYED.contains(src.getClass())) {

			}
			else if (DamageSource.ICE.contains(src.getClass())) {
				sprite.showStatus(CharSprite.CYAN, Integer.toString(dmg + shielded));
			}
			else if (DamageSource.BLOOD.contains(src.getClass())) {
				sprite.showStatus(CharSprite.BLOOD, Integer.toString(dmg + shielded));
			}
			else if (DamageSource.FIRE.contains(src.getClass())) {
				sprite.showStatus(CharSprite.ORANGE, Integer.toString(dmg + shielded));
			}
			else if (DamageSource.LIGHTNING.contains(src.getClass())) {
				sprite.showStatus(CharSprite.YELLOW, Integer.toString(dmg + shielded));
			}
			else if (DamageSource.POISON.contains(src.getClass())) {
				sprite.showStatus(CharSprite.GREEN, Integer.toString(dmg + shielded));
			}
			else if (DamageSource.MAGICAL.contains(src.getClass())) {
				sprite.showStatus(CharSprite.MYSTERIOUS, Integer.toString(dmg + shielded));
			} else if (critProc){
				sprite.showStatus(CharSprite.NEGATIVE, "CRIT " + Integer.toString(dmg + shielded));
				critProc = false;
			}
			else { sprite.showStatus(CharSprite.DEFAULT, Integer.toString(dmg + shielded));
				//if (DamageSource.PHYSICAL.contains(src.getClass())||DamageSource.ELEMENTAL.contains(src.getClass())) {
				//TODO CHECK THE DamageSource.class, make the PHYSICAL sources include weaponry and other stuff
			}
		}

		if (HP < 0) HP = 0;

		if (!isAlive()) {
			if (this instanceof Hero) {
				if ( buff(Undying.class) != null ){
					Badges.validateUndead();
					buff(Undying.class).detach();
					HP = HT/2;
					CellEmitter.center(pos).burst(ShadowParticle.CURSE, 20);
					Sample.INSTANCE.play(Assets.Sounds.CURSED);
					Buff.affect(this, Healing.class).setHeal(HT*2, 0.05f, 5);
					Buff.affect(this, AbilityCooldown.class, 200);
				} else hero.faint(src);
			} else die( src );
		} else if (HP == 0) if (buff(DeathMark.DeathMarkTracker.class) != null){
			DeathMark.processFearTheReaper(this);
		}
	}

	
	public void destroy() {
		HP = 0;
		Actor.remove( this );

		for (Char ch : Actor.chars().toArray(new Char[0])){
			if (ch.buff(Charm.class) != null && ch.buff(Charm.class).object == id()){
				ch.buff(Charm.class).detach();
			}
			if (ch.buff(Dread.class) != null && ch.buff(Dread.class).object == id()){
				ch.buff(Dread.class).detach();
			}
			if (ch.buff(Terror.class) != null && ch.buff(Terror.class).object == id()){
				ch.buff(Terror.class).detach();
			}
			if (ch.buff(SnipersMark.class) != null && ch.buff(SnipersMark.class).object == id()){
				ch.buff(SnipersMark.class).detach();
			}
		}
	}
	
	public void die( Object src ) {

		destroy();
		if (src != Chasm.class) sprite.die();
		if (DeviceCompat.isDebug()) DeviceCompat.log( "CHAR DYING",
				name()+
						" on position of "+
						"("+
						pos % Dungeon.level.width()+
						";"+
						pos / Dungeon.level.width()+
						")" + " died" );

	}

	public void damagePortal(int postp){
		CellEmitter.get(postp).burst(SacrificialParticle.FACTORY, 3);
		Camera.main.panFollow(sprite,2f);

		CharSprite s = sprite;
		destroy();

		s.jump(this.pos, postp, 5,0.5f, new Callback() {
			@Override
			public void call() {
				s.killAndErase();
				Sample.INSTANCE.play(Assets.Sounds.CHARGEUP,1f,2f);
			}
		});

	}

	//we cache this info to prevent having to call buff(...) in isAlive.
	//This is relevant because we call isAlive during drawing, which has both performance
	//and thread coordination implications
	public boolean deathMarked = false;
	
	public boolean isAlive() {
		return HP > 0 || deathMarked;
	}

	public boolean isActive() {
		return isAlive();
	}

	@Override
	protected void spendConstant(float time) {
		TimekeepersHourglass.timeFreeze freeze = buff(TimekeepersHourglass.timeFreeze.class);
		if (freeze != null) {
			freeze.processTime(time);
			return;
		}

		Swiftthistle.TimeBubble bubble = buff(Swiftthistle.TimeBubble.class);
		if (bubble != null){
			bubble.processTime(time);
			return;
		}

		super.spendConstant(time);
	}

	@Override
	public void spend(float time) {

		float timeScale = 1f;
		if (buff( Slow.class ) != null) {
			timeScale *= 0.5f;
			//slowed and chilled do not stack
		}
		if (buff( WallStance.class ) != null) {
			timeScale *= 0.5f;
			//slowed and chilled do not stack
		}else if (buff( Chill.class ) != null) {
			timeScale *= buff( Chill.class ).speedFactor();
		}
		if (buff( Speed.class ) != null) {
			timeScale *= 2.0f;
		}



		super.spend( time / timeScale );
	}
	
	public synchronized LinkedHashSet<Buff> buffs() {
		return new LinkedHashSet<>(buffs);
	}
	
	@SuppressWarnings("unchecked")
	//returns all buffs assignable from the given buff class
	public synchronized <T extends Buff> HashSet<T> buffs( Class<T> c ) {
		HashSet<T> filtered = new HashSet<>();
		for (Buff b : buffs) {
			if (c.isInstance( b )) {
				filtered.add( (T)b );
			}
		}
		return filtered;
	}

	@SuppressWarnings("unchecked")
	//returns an instance of the specific buff class, if it exists. Not just assignable
	public synchronized  <T extends Buff> T buff( Class<T> c ) {
		for (Buff b : buffs) {
			if (b.getClass() == c) {
				return (T)b;
			}
		}
		return null;
	}

	public synchronized boolean isCharmedBy( Char ch ) {
		int chID = ch.id();
		for (Buff b : buffs) {
			if (b instanceof Charm && ((Charm)b).object == chID) {
				return true;
			}
		}
		return false;
	}

	public synchronized boolean add( Buff buff ) {

		if (buff(PotionOfCleansing.Cleanse.class) != null) { //cleansing buff
			if (buff.type == Buff.buffType.NEGATIVE
					&& !(buff instanceof AllyBuff)
					&& !(buff instanceof LostInventory)){
				return false;
			}
		}

		if (sprite != null && buff(Challenge.SpectatorFreeze.class) != null){
			return false; //can't add buffs while frozen and game is loaded
		}

		buffs.add( buff );
		if (Actor.chars().contains(this)) Actor.add( buff );

		if (sprite != null && buff.announced) {
			switch (buff.type) {
				case POSITIVE:
					sprite.showStatus(CharSprite.POSITIVE, Messages.titleCase(buff.name()));
					break;
				case NEGATIVE:
					sprite.showStatus(CharSprite.NEGATIVE, Messages.titleCase(buff.name()));
					break;
				case NEUTRAL:
				default:
					sprite.showStatus(CharSprite.NEUTRAL, Messages.titleCase(buff.name()));
					break;
			}
		}

		return true;

	}
	
	public synchronized boolean remove( Buff buff ) {
		
		buffs.remove( buff );
		Actor.remove( buff );

		return true;
	}
	
	public synchronized void remove( Class<? extends Buff> buffClass ) {
		for (Buff buff : buffs( buffClass )) {
			remove( buff );
		}
	}
	
	@Override
	protected synchronized void onRemove() {
		for (Buff buff : buffs.toArray(new Buff[buffs.size()])) {
			buff.detach();
		}
	}
	
	public synchronized void updateSpriteState() {
		for (Buff buff:buffs) {
			buff.fx( true );
		}
	}

	
	public float stealth() {
		return 0;
	}

	public final void move( int step ) {
		move( step, true );
	}

	//travelling may be false when a character is moving instantaneously, such as via teleportation
	public void move( int step, boolean travelling ) {

		if (this instanceof Hero && Dungeon.level.herounpassable[step]) return;

		if (travelling && Dungeon.level.adjacent( step, pos ) && buff( Vertigo.class ) != null) {
			sprite.interruptMotion();
			int newPos = pos + PathFinder.NEIGHBOURS8[Random.Int( 8 )];
			if (!(Dungeon.level.passable[newPos] || Dungeon.level.avoid[newPos] )//FIXME
					|| (properties().contains(Property.LARGE) && !Dungeon.level.openSpace[newPos])
					|| Actor.findChar( newPos ) != null)
				return;


			else {
				sprite.move(pos, newPos);
				step = newPos;
			}
		}

		if (Dungeon.level.map[pos] == Terrain.OPEN_DOOR) {
			Door.leave( pos );
		}


		pos = step;
		
		if (this != hero) {
			sprite.visible = Dungeon.level.heroFOV[pos];
		}
		
		Dungeon.level.occupyCell(this );
	}
	
	public int distance( Char other ) {
		return Dungeon.level.distance( pos, other.pos );
	}
	
	public void onMotionComplete() {
		//Does nothing by default
		//The main actor thread already accounts for motion,
		// so calling next() here isn't necessary (see Actor.process)
	}
	
	public void onAttackComplete() {
		next();
	}
	
	public void onOperateComplete() {
		next();
	}
	
	protected final HashSet<Class> resistances = new HashSet<>();
	
	//returns percent effectiveness after resistances
	//TODO currently resistances reduce effectiveness by a static 50%, and do not stack.
	public float resist( Class effect ){
		HashSet<Class> resists = new HashSet<>(resistances);
		for (Property p : properties()){
			resists.addAll(p.resistances());
		}
		for (Buff b : buffs()){
			resists.addAll(b.resistances());
		}
		
		float result = 1f;
		for (Class c : resists){
			if (c.isAssignableFrom(effect)){
				result *= 0.5f;
			}
		}
		return result * RingOfElements.resist(this, effect);
	}
	
	protected final HashSet<Class> immunities = new HashSet<>();
	
	public boolean isImmune(Class effect ){
		HashSet<Class> immunes = new HashSet<>(immunities);
		for (Property p : properties()){
			immunes.addAll(p.immunities());
		}
		for (Buff b : buffs()){
			immunes.addAll(b.immunities());
		}
		
		for (Class c : immunes){
			if (c.isAssignableFrom(effect)){
				return true;
			}
		}
		return false;
	}

	//similar to isImmune, but only factors in damage.
	//Is used in AI decision-making
	public boolean isInvulnerable( Class effect ){
		return buff(Challenge.SpectatorFreeze.class) != null;
	}

	protected HashSet<Property> properties = new HashSet<>();

	public HashSet<Property> properties() {
		HashSet<Property> props = new HashSet<>(properties);
		//TODO any more of these and we should make it a property of the buff, like with resistances/immunities
		return props;
	}

	public enum Property{
		BOSS ( new HashSet<Class>( Arrays.asList(Grim.class, GrimTrap.class, ScrollOfSkulls.class, ScrollOfDemonicSkull.class)),
				new HashSet<Class>( Arrays.asList(AllyBuff.class, Dread.class) )),
		MINIBOSS ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(Dread.class, AllyBuff.class) )),
		BOSS_MINION,
		UNDEAD,
		DEMONIC,
		INORGANIC ( new HashSet<Class>(),
				new HashSet<Class>( Arrays.asList(Bleeding.class, ToxicGas.class, Poison.class) )),
		FIERY ( new HashSet<Class>( Arrays.asList(WandOfFireblast.class, Elemental.FireElemental.class)),
				new HashSet<Class>( Arrays.asList(Burning.class, Blazing.class))),
		ICY ( new HashSet<Class>( Arrays.asList(WandOfFrost.class, Elemental.FrostElemental.class)),
				new HashSet<Class>( Arrays.asList(Frost.class, Chill.class))),
		ACIDIC ( new HashSet<Class>( Arrays.asList(Corrosion.class)),
				new HashSet<Class>( Arrays.asList(Ooze.class))),
		ELECTRIC ( new HashSet<Class>( Arrays.asList(WandOfLightning.class, Shocking.class, Potential.class, Electricity.class, ShockingDart.class, Elemental.ShockElemental.class )),
				new HashSet<Class>()),
		LARGE,
		IMMOVABLE;
		
		private HashSet<Class> resistances;
		private HashSet<Class> immunities;
		
		Property(){
			this(new HashSet<Class>(), new HashSet<Class>());
		}
		
		Property( HashSet<Class> resistances, HashSet<Class> immunities){
			this.resistances = resistances;
			this.immunities = immunities;
		}
		
		public HashSet<Class> resistances(){
			return new HashSet<>(resistances);
		}
		
		public HashSet<Class> immunities(){
			return new HashSet<>(immunities);
		}

	}

	public static boolean hasProp( Char ch, Property p){
		return (ch != null && ch.properties().contains(p));
	}
}
