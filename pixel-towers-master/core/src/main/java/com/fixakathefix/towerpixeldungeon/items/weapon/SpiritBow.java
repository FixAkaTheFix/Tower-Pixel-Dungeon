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

package com.fixakathefix.towerpixeldungeon.items.weapon;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.RevealedArea;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.huntress.NaturesPower;
import com.fixakathefix.towerpixeldungeon.effects.Splash;
import com.fixakathefix.towerpixeldungeon.effects.particles.LeafParticle;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfSharpshooting;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Blindweed;
import com.fixakathefix.towerpixeldungeon.plants.Firebloom;
import com.fixakathefix.towerpixeldungeon.plants.Icecap;
import com.fixakathefix.towerpixeldungeon.plants.Plant;
import com.fixakathefix.towerpixeldungeon.plants.Sorrowmoss;
import com.fixakathefix.towerpixeldungeon.plants.Stormvine;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.ui.QuickSlotButton;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class SpiritBow extends Weapon {

    public static final String AC_SHOOT = "SHOOT";

    {
        image = ItemSpriteSheet.SPIRIT_BOW;
        rarity = 6;
        defaultAction = AC_SHOOT;
        usesTargeting = true;

        unique = true;
        bones = false;
    }

    public boolean sniperSpecial = false;
    public float sniperSpecialBonusDamage = 0f;

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.remove(AC_EQUIP);
        actions.add(AC_SHOOT);
        return actions;
    }

    @Override
    public void execute(Hero hero, String action) {

        super.execute(hero, action);

        if (action.equals(AC_SHOOT)) {

            curUser = hero;
            curItem = this;
            GameScene.selectCell(shooter);

        }
    }

    private static Class[] harmfulPlants = new Class[]{
            Blindweed.class, Firebloom.class, Icecap.class, Sorrowmoss.class, Stormvine.class
    };
    final boolean[] addShotWasMade = {false};

    @Override
    public int proc(Char attacker, Char defender, int damage) {


        if (attacker.buff(NaturesPower.naturesPowerTracker.class) != null && !sniperSpecial) {
            addShotWasMade[0] = false;


            Actor.add(new Actor() {
                {
                    actPriority = VFX_PRIO;
                }

                @Override
                protected boolean act() {

                    if (Random.Int(12) < ((Hero) attacker).pointsInTalent(Talent.NATURES_WRATH)) {
                        Plant plant = (Plant) Reflection.newInstance(Random.element(harmfulPlants));
                        plant.pos = defender.pos;
                        plant.activate(defender.isAlive() ? defender : null);
                    }

                    if (!defender.isAlive()) {
                        NaturesPower.naturesPowerTracker tracker = attacker.buff(NaturesPower.naturesPowerTracker.class);
                        if (tracker != null) {
                            tracker.extend(((Hero) attacker).pointsInTalent(Talent.WILD_MOMENTUM));
                        }
                    }
                    Actor.remove(this);
                    return true;
                }
            });

        }

        return super.proc(attacker, defender, damage);
    }


    @Override
    public String info() {
        String info = desc();

        info += "\n\n" + Messages.get(SpiritBow.class, "stats",
                Math.round(augment.damageFactor(min())),
                Math.round(augment.damageFactor(max())),
                STRReq());

        if (STRReq() > Dungeon.hero.STR()) {
            info += " " + Messages.get(Weapon.class, "too_heavy");
        } else if (Dungeon.hero.STR() > STRReq()) {
            info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
        }

        switch (augment) {
            case SPEED:
                info += "\n\n" + Messages.get(Weapon.class, "faster");
                break;
            case DAMAGE:
                info += "\n\n" + Messages.get(Weapon.class, "stronger");
                break;
            case NONE:
        }

        if (enchantment != null && (cursedKnown || !enchantment.curse())) {
            info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
            info += " " + Messages.get(enchantment, "desc");
        }

        if (cursed && isEquipped(Dungeon.hero)) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
        } else if (cursedKnown && cursed) {
            info += "\n\n" + Messages.get(Weapon.class, "cursed");
        } else if (!isIdentified() && cursedKnown) {
            info += "\n\n" + Messages.get(Weapon.class, "not_cursed");
        }

        info += "\n\n" + Messages.get(MissileWeapon.class, "distance");

        return info;
    }

    @Override
    public int STRReq(int lvl) {
        return STRReq(1, lvl); //tier 1
    }

    @Override
    public int min(int lvl) {
        int dmg = 2 + (int) MeleeWeapon.damageModifier()
                + (int)(lvl*MeleeWeapon.damageModifier())
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 1 + Dungeon.hero.lvl / 30 : 0);
        return Math.max(0, dmg);
    }

    @Override
    public int max(int lvl) {
        int dmg = 5 + (int) (2*MeleeWeapon.damageModifier())
                + (int)(2*lvl*MeleeWeapon.damageModifier())
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero)
                + (curseInfusionBonus ? 2 + Dungeon.hero.lvl / 15 : 0);
        return Math.max(0, dmg);
    }

    @Override
    public int targetingPos(Hero user, int dst) {
        return knockArrow().targetingPos(user, dst);
    }

    private int targetPos;

    @Override
    public int damageRoll(Char owner) {
        int damage = augment.damageFactor(super.damageRoll(owner));

        if (owner instanceof Hero) {
            int exStr = ((Hero) owner).STR() - STRReq();
            if (exStr > 0) {
                damage += Random.IntRange(0, exStr);
            }
        }

        if (sniperSpecial) {
            damage = Math.round(damage * (1f + sniperSpecialBonusDamage));

            switch (augment) {
                case NONE:
                    damage = Math.round(damage * 0.667f);
                    break;
                case SPEED:
                    damage = Math.round(damage * 0.5f);
                    break;
                case DAMAGE:
                    //as distance increases so does damage, capping at 3x:
                    //1.20x|1.35x|1.52x|1.71x|1.92x|2.16x|2.43x|2.74x|3.00x
                    int distance = Dungeon.level.distance(owner.pos, targetPos) - 1;
                    float multiplier = Math.min(3f, 1.2f * (float) Math.pow(1.125f, distance));
                    damage = Math.round(damage * multiplier);
                    break;
            }
        }

        return damage;
    }

    @Override
    protected float baseDelay(Char owner) {
        if (sniperSpecial) {
            switch (augment) {
                case NONE:
                default:
                    return 0f;
                case SPEED:
                    return 1f;
                case DAMAGE:
                    return 2f;
            }
        } else {
            return super.baseDelay(owner);
        }
    }

    @Override
    protected float speedMultiplier(Char owner) {
        float speed = super.speedMultiplier(owner);
        if (owner.buff(NaturesPower.naturesPowerTracker.class) != null) {
            // +33% speed to +50% speed, depending on talent points
            speed += ((8 + ((Hero) owner).pointsInTalent(Talent.GROWING_POWER)) / 24f);
        }
        return speed;
    }

    @Override
    public int buffedLvl() {
        //level isn't affected by buffs/debuffs
        return level();
    }

    public SpiritArrow knockArrow() {
        return new SpiritArrow();
    }

    public class SpiritArrow extends MissileWeapon {

        {
            image = ItemSpriteSheet.SPIRIT_ARROW;

            hitSound = Assets.Sounds.HIT_ARROW;
        }

        @Override
        public Emitter emitter() {
            if (Dungeon.hero.buff(NaturesPower.naturesPowerTracker.class) != null && !sniperSpecial) {
                Emitter e = new Emitter();
                e.pos(5, 5);
                e.fillTarget = false;
                e.pour(LeafParticle.GENERAL, 0.01f);
                return e;
            } else {
                return super.emitter();
            }
        }

        @Override
        public int damageRoll(Char owner) {
            return SpiritBow.this.damageRoll(owner);
        }

        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return SpiritBow.this.hasEnchant(type, owner);
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return SpiritBow.this.proc(attacker, defender, damage);
        }

        @Override
        public float delayFactor(Char user) {
            return SpiritBow.this.delayFactor(user);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            if (sniperSpecial && SpiritBow.this.augment == Augment.DAMAGE) {
                return Float.POSITIVE_INFINITY;
            } else {
                return super.accuracyFactor(owner, target);
            }
        }

        @Override
        public int STRReq(int lvl) {
            return SpiritBow.this.STRReq();
        }

        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar(cell);
            if (enemy == null || enemy == curUser) {
                parent = null;
                Splash.at(cell, 0xCC99FFFF, 1);
            } else {
                if (!curUser.shoot(enemy, this)) {
                    Splash.at(cell, 0xCC99FFFF, 1);
                }
                if (sniperSpecial && SpiritBow.this.augment != Augment.SPEED) sniperSpecial = false;
            }
        }

        @Override
        public void throwSound() {
            Sample.INSTANCE.play(Assets.Sounds.ATK_SPIRITBOW, 1, Random.Float(0.87f, 1.15f));
        }

        int flurryCount = -1;
        Actor flurryActor = null;

        @Override
        public void cast(final Hero user, final int dst) {
            final int cell = throwPos(user, dst);
            SpiritBow.this.targetPos = cell;
            if (sniperSpecial && SpiritBow.this.augment == Augment.SPEED) {
                if (flurryCount == -1) flurryCount = 3;

                final Char enemy = Actor.findChar(cell);

                if (enemy == null) {
                    if (user.buff(Talent.LethalMomentumTracker.class) != null) {
                        user.buff(Talent.LethalMomentumTracker.class).detach();
                        user.next();
                    } else {
                        user.spendAndNext(castDelay(user, dst));
                    }
                    sniperSpecial = false;
                    flurryCount = -1;

                    if (flurryActor != null) {
                        flurryActor.next();
                        flurryActor = null;
                    }
                    return;
                }
                QuickSlotButton.target(enemy);

                final boolean last = flurryCount == 1;

                user.busy();

                throwSound();

                ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                        reset(user.sprite,
                                cell,
                                this,
                                new Callback() {
                                    @Override
                                    public void call() {
                                        if (enemy.isAlive()) {
                                            curUser = user;
                                            onThrow(cell);
                                        }

                                        if (last) {
                                            if (user.buff(Talent.LethalMomentumTracker.class) != null) {
                                                user.buff(Talent.LethalMomentumTracker.class).detach();
                                                user.next();
                                            } else {
                                                user.spendAndNext(castDelay(user, dst));
                                            }
                                            sniperSpecial = false;
                                            flurryCount = -1;
                                        }

                                        if (flurryActor != null) {
                                            flurryActor.next();
                                            flurryActor = null;
                                        }
                                    }
                                });

                user.sprite.zap(cell, new Callback() {
                    @Override
                    public void call() {
                        flurryCount--;
                        if (flurryCount > 0) {
                            Actor.add(new Actor() {

                                {
                                    actPriority = VFX_PRIO - 1;
                                }

                                @Override
                                protected boolean act() {
                                    flurryActor = this;
                                    int target = QuickSlotButton.autoAim(enemy, SpiritArrow.this);
                                    if (target == -1) target = cell;
                                    cast(user, target);
                                    Actor.remove(this);
                                    return false;
                                }
                            });
                            curUser.next();
                        }
                    }
                });

            } else {

                if (user.hasTalent(Talent.SEER_SHOT)
                        && user.buff(Talent.SeerShotCooldown.class) == null) {
                    int shotPos = throwPos(user, dst);
                    if (Actor.findChar(shotPos) == null) {
                        RevealedArea a = Buff.affect(user, RevealedArea.class, 5 * user.pointsInTalent(Talent.SEER_SHOT));
                        a.depth = Dungeon.depth;
                        a.pos = shotPos;
                        Buff.affect(user, Talent.SeerShotCooldown.class, 20f);
                    }
                }

                super.cast(user, dst);
            }
        }
    }

    private CellSelector.Listener shooter = new CellSelector.Listener() {
        @Override
        public void onSelect( Integer target ) {
            if (target != null) {
                knockArrow().cast(curUser, target);
            }
        }
        @Override
        public String prompt() {
            return Messages.get(SpiritBow.class, "prompt");
        }
    };
}
