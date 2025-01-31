package com.fixakathefix.towerpixeldungeon.items.weapon;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.RevealedArea;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTroll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfSharpshooting;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.ui.QuickSlotButton;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DebugBow extends Weapon {

    public static final String AC_SHOOT = "SHOOT";

    {
        image = ItemSpriteSheet.DEBUG_BOW;

        defaultAction = AC_SHOOT;
        usesTargeting = true;

        unique = true;
        bones = false;
    }

    public boolean sniperSpecial = false;

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

    @Override
    public String info() {
        String info = desc();

        info += "\n\n" + Messages.get(DebugBow.class, "stats",
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
        return 1;
    } //tier 1, for fix only}

    @Override
    public int min(int lvl) {
        int dmg = 100 + this.level()*100
                + RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
        return Math.max(0, dmg);
    }

    @Override
    public int max(int lvl) {
        int dmg = 100 + this.level()*100
                + 2 * RingOfSharpshooting.levelDamageBonus(Dungeon.hero);
        return Math.max(0, dmg);
    }

    @Override
    public int targetingPos(Hero user, int dst) {
        return knockArrow().targetingPos(user, dst);
    }

    public DebugBow.DebugArrow knockArrow(){
        return new DebugBow.DebugArrow();
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
        return damage;
    }

    @Override
    protected float baseDelay(Char owner) {
        return super.baseDelay(owner);
    }

    @Override
    protected float speedMultiplier(Char owner) {
        return super.speedMultiplier(owner);
    }

    @Override
    public int buffedLvl() {
        //level isn't affected by buffs/debuffs
        return level();
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    public DebugArrow knockArrow = new DebugArrow();


    public class DebugArrow extends MissileWeapon {

        {
            image = ItemSpriteSheet.SEED_SUNGRASS;

            hitSound = Assets.Sounds.HIT_CRUSH;
        }

        @Override
        public Emitter emitter() {
            return super.emitter();
        }


        @Override
        public boolean hasEnchant(Class<? extends Enchantment> type, Char owner) {
            return DebugBow.this.hasEnchant(type, owner);
        }

        @Override
        public int proc(Char attacker, Char defender, int damage) {
            return DebugBow.this.proc(attacker, defender, damage);
        }

        @Override
        public float delayFactor(Char user) {
            return DebugBow.this.delayFactor(user);
        }

        @Override
        public float accuracyFactor(Char owner, Char target) {
            if (sniperSpecial && DebugBow.this.augment == Augment.DAMAGE) {
                return Float.POSITIVE_INFINITY;
            } else {
                return super.accuracyFactor(owner, target);
            }
        }

        @Override
        public int STRReq(int lvl) {
            return DebugBow.this.STRReq();
        }


        @Override
        protected void onThrow(int cell) {
            Char enemy = Actor.findChar(cell);
            if (enemy == null) {
                for (Mob mob : Dungeon.level.mobs){
                    GLog.w("mob");
                }
                Mob friend = new BossTroll();
                friend.state = friend.HUNTING;
                Buff.affect(friend, AscensionChallenge.AscensionBuffBlocker.class);
                GameScene.add(friend);
                ScrollOfTeleportation.appear(friend, cell);

                //GnollTwilight friend = new GnollTwilight();
                //AllyBuff.affect(friend, Corruption.class);
                //friend.state = friend.HUNTING;
                //Buff.affect(friend, AscensionChallenge.AscensionBuffBlocker.class);
                //GameScene.add(friend);
                //ScrollOfTeleportation.appear(friend, cell);


            }
            else {
                if (!curUser.shoot(enemy, new DebugArrow())) {
                    parent = null;
                }
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
            DebugBow.this.targetPos = cell;
            if (sniperSpecial && DebugBow.this.augment == Augment.SPEED) {
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
                                    int target = QuickSlotButton.autoAim(enemy, knockArrow);
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
        public void onSelect(Integer target) {
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


