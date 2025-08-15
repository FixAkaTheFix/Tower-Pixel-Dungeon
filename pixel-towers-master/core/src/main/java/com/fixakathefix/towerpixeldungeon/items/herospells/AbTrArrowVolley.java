package com.fixakathefix.towerpixeldungeon.items.herospells;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbowBallista;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbowGatling;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrArrowVolley extends HeroSpellTargeted {
    private static int arrowcount = 0;
    private static final int TURNS_ADDED_PER_CROSSBOW = 20;

    {
        image = ItemSpriteSheet.HEROSPELL_TR_ARROWVALLEY;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Dungeon.level.passable[cell] && Dungeon.hero.fieldOfView[cell]) {
                        ArrayList<TowerCrossbow1> crossbows = new ArrayList<>();
                        for (Mob mob : Level.mobs) {
                            if (mob instanceof TowerCrossbow1 && mob.alignment == Char.Alignment.ALLY) {
                                crossbows.add((TowerCrossbow1) mob);
                            }
                        }
                        if (crossbows.isEmpty()) {
                            GLog.w(Messages.get(AbTrArrowVolley.class, "no_crossbow"));
                            return;
                        }
                        hero.busy();
                        int damage = 0;
                        int arrowamount = 0;
                        for (TowerCrossbow1 mob : crossbows) {
                            arrowamount++;
                            damage += 6;
                            if (mob instanceof TowerCrossbow2) {
                                damage += 7;
                                if (mob instanceof TowerCrossbow3) {
                                    damage += 13;
                                    if (mob instanceof TowerCrossbowBallista) {
                                        damage += 40;
                                    }
                                    if (mob instanceof TowerCrossbowGatling) {
                                        arrowamount += 3;
                                        damage += 30;
                                    }
                                }
                            }

                            PointF source = DungeonTilemap.raisedTileCenterToWorld(mob.pos);
                            PointF dest = new PointF(
                                    DungeonTilemap.tileCenterToWorld(cell).x,
                                    DungeonTilemap.tileCenterToWorld(cell).y - 250

                            );
                            mob.sprite.play(mob.sprite.attack.clone());
                            mob.sprite.turnTo(mob.pos, cell);
                            Sample.INSTANCE.play(Assets.Sounds.ATK_SPIRITBOW);
                            ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                                    .reset(
                                            source,
                                            dest,
                                            new Dart(),
                                            new Callback() {
                                                @Override
                                                public void call() {
                                                }
                                            },
                                            700f + Dungeon.level.distance(mob.pos, cell) * 50,
                                            0f);

                        }

                        final float spellinterval = 1.0f / arrowamount;
                        arrowcount = arrowamount;
                        int finalDamage = damage;//for the call()
                        int finalArrowamount = arrowamount;//for the call()
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                int shift = (int) (16 * Math.random()) - 6;
                                PointF source = DungeonTilemap.raisedTileCenterToWorld(cell);
                                PointF dest = DungeonTilemap.tileCenterToWorld(cell);
                                source.y -= 250;
                                source.x += shift;
                                dest.x += shift;
                                source.y += shift;
                                dest.y += shift;
                                ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                                        .reset(
                                                source,
                                                dest,
                                                new Dart(),
                                                new Callback() {
                                                    @Override
                                                    public void call() {

                                                        Char ch = Char.findChar(cell);

                                                        if (ch != null){
                                                            int drroll = ch.buff(PotionOfCleansing.Cleanse.class)==null ? ch.drRoll() : 0;
                                                            ch.damage(finalDamage / finalArrowamount - drroll, hero);
                                                        }
                                                        Sample.INSTANCE.play(Assets.Sounds.HIT_ARROW);
                                                        arrowcount--;
                                                        if (arrowcount == 0) hero.next();
                                                    }
                                                },
                                                500f,
                                                0f);
                            }
                        }, 0.2f, spellinterval, finalArrowamount - 1);
                        cooldown();
                    } else GLog.w(Messages.get(HeroSpellTargeted.class, "cell_in_view"));
                }
            }

            @Override
            public String prompt() {
                return Messages.get(HeroSpellTargeted.class, "cell_choose");
            }
        };
    }

    @Override
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_ADDED_PER_CROSSBOW);
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if (mob instanceof TowerCrossbow1 && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_CROSSBOW;
            }
        } catch (NullPointerException ignored) {
        }
        return 40 + addturns;
    }

}
