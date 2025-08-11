package com.fixakathefix.towerpixeldungeon.items.herospells;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.badlogic.gdx.utils.Timer;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.Firebomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.ShockBomb;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrBombVolley extends HeroSpellTargeted {
    private static int bombcount = 0;
    private static final int TURNS_ADDED_PER_CANNON = 15;
    private static final int TURNS_ADDED_PER_NUKE_ADDITIONAL = 200;

    {
        image = ItemSpriteSheet.HEROSPELL_TR_AREABOMB;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Dungeon.level.passable[cell] && Dungeon.hero.fieldOfView[cell]) {
                        ArrayList<Tower> cannons = new ArrayList<>();
                        for (Mob mob : Level.mobs) {
                            if (mob instanceof TowerCannon1 || mob instanceof TowerCannonMissileLauncher && mob.alignment == Char.Alignment.ALLY) {
                                cannons.add((Tower) mob);
                            }
                        }
                        if (cannons.isEmpty()) {
                            GLog.w(Messages.get(AbTrBombVolley.class, "no_cannons"));
                            return;
                        }
                        hero.busy();
                        bombcount = 0;
                        for (Tower mob : cannons) {
                            PointF source = DungeonTilemap.raisedTileCenterToWorld(mob.pos);
                            PointF dest = new PointF(
                                    DungeonTilemap.tileCenterToWorld(cell).x,
                                    DungeonTilemap.tileCenterToWorld(cell).y - 250

                            );

                            mob.sprite.play(mob.sprite.attack.clone());
                            mob.sprite.turnTo(mob.pos, cell);
                            Sample.INSTANCE.play(Assets.Sounds.ATK_SPIRITBOW);
                            Item item =
                                    mob instanceof TowerCannonMissileLauncher ? new Firebomb() :
                                            mob instanceof TowerCannonNuke ? new ShockBomb() :
                                                    new Bomb();
                            ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                                    .reset(
                                            source,
                                            dest,
                                            item,
                                            new Callback() {
                                                @Override
                                                public void call() {
                                                }
                                            },
                                            Random.Int(600, 1000)+ Dungeon.level.distance(mob.pos, cell) * 50,
                                            Random.Int(200, 600));
                            bombcount++;

                        }
                        if (bombcount == 0) {
                            GLog.w(Messages.get(getClass(), "no_cannons"));
                            return;
                        }
                        for (Tower cannon : cannons) {
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    int shift = (int) (16 * Math.random()) - 6;
                                    PointF source = DungeonTilemap.raisedTileCenterToWorld(cell);
                                    source.y -= 250;
                                    source.x += shift;
                                    source.y += shift;

                                    int destcell = cell + PathFinder.NEIGHBOURS9[Random.Int(9)];
                                    PointF dest = DungeonTilemap.tileCenterToWorld(destcell);
                                    dest.y += shift;
                                    dest.x += shift;
                                    Item item =
                                            cannon instanceof TowerCannonMissileLauncher ? new Firebomb() :
                                                    cannon instanceof TowerCannonNuke ? new ShockBomb() :
                                                            new Bomb();
                                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                                            .reset(
                                                    source,
                                                    dest,
                                                    item,
                                                    new Callback() {
                                                        @Override
                                                        public void call() {
                                                            if (cannon instanceof TowerCannon1) ((TowerCannon1) cannon).boom(destcell);
                                                            if (cannon instanceof TowerCannonMissileLauncher) new TowerCannonMissileLauncher.RocketMinion().boom(destcell);
                                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                                            bombcount--;
                                                            if (bombcount == 0) hero.next();
                                                        }
                                                    }, Random.Int(500, 800),
                                                    0f);
                                }
                            }, (float) (Math.random() * 1.5));
                        }


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
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_ADDED_PER_CANNON);
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if (mob instanceof TowerCannon1 || mob instanceof TowerCannonMissileLauncher && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_CANNON;
                if (mob instanceof TowerCannonNuke && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_NUKE_ADDITIONAL;

            }
        } catch (NullPointerException ignored) {
        }
        return 150 + addturns;
    }

}
