package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollBlind;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCWall;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall3;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfFrost;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfGolems;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfFlock;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.Toolbar;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Arena1 extends ArenaSewers {

    /**
     * Shows the basics, is extremely easy
     * Shows specifically the wall, and the crossbow.
     */
    {
        name = "Sewer entrance";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 51;
        HEIGHT = 51;

        amuletCell = 9 + WIDTH * 25;
        exitCell = amuletCell;
        towerShopKeeperCell = 6 + 21 * WIDTH;
        normalShopKeeperCell = 11 + 21 * WIDTH;

        arenaDepth = 1;

        startGold = 500;
        maxWaves = 10;
        waveCooldownNormal = 10;
        waveCooldownBoss = 10;
    }

    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave) {
            case 1:
                mob = new Rat();
                break;
            case 2:
                mob = Random.oneOf(new Rat(), new Snake(), new GnollBlind());
                break;
            case 3:
                mob = Random.oneOf(new Gnoll(), new GnollBlind());
                break;
            case 4:
                mob = Random.oneOf(new Rat(), new Rat(), new Snake());
                break;
            case 5:
                mob = Random.oneOf(new Snake());
                break;
            case 6:
                mob = Random.oneOf(new Albino(), new Gnoll());
                break;
            case 7:
                mob = new Rat();
                break;
            case 8:
                mob = Random.oneOf(new Albino());
                break;
            case 9:
                mob = Random.oneOf(new Crab(), new Snake());
                break;
            case 10:
                if (!bossSpawned) {
                bossSpawned = true;
                mob = new ChiefRat();
                } else mob = new Albino();
                break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }

    @Override
    public int mobsToDeploy(int wave) {
        switch (wave) {
            case 1:
                return 3;
            case 2:
                return 5;
            case 3:
                return 5;
            case 4:
                return 8;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 12;
            case 8:
                return 13;
            case 9:
                return 10;
            case 10:
                return 12;
        }
        return 1;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public void affectMob(Mob mob) {
        Buff.affect(mob, ChampionEnemy.Projecting.class);
    }

    @Override
    protected boolean build() {

        setSize(WIDTH, HEIGHT);
        //base room
        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);
        ;
        Painter.fill(this, 2, 2, 43, 46, Terrain.EMPTY);
        Painter.fill(this, 0, 0, 50, 21, Terrain.WALL);
        Painter.fill(this, 0, 30, 50, 20, Terrain.WALL);
        Painter.fill(this, 0, 0, 5, 50, Terrain.WALL);
        Painter.fill(this, 5, 21, 9, 9, Terrain.EMPTY_SP);
        Painter.fill(this, 7, 23, 5, 5, Terrain.EMPTY);

        this.map[5 + 21 * WIDTH] = Terrain.BOOKSHELF;

        Painter.fill(this, 5, 22, 9, 1, Terrain.PEDESTAL);

        this.map[13 + 21 * WIDTH] = Terrain.BOOKSHELF;

        Painter.fill(this, 5, 20, 8, 1, Terrain.BOOKSHELF);

        Painter.fill(this, 14, 21, 1, 3, Terrain.WALL);
        Painter.fill(this, 14, 27, 1, 3, Terrain.WALL);

        for (int x = 1; x < WIDTH - 1; x++)
            for (int y = 1; y < HEIGHT - 1; y++) {

                if (Math.random() > 0.5 && x > 15 && x < 44 && y > 20 && y < 30)
                    this.map[x + WIDTH * y] = Terrain.WATER;
                if (Math.random() > 0.9 && x > 18 && x < 44 && y > 20 && y < 29) {
                    this.map[x + WIDTH * y] = Terrain.WALL;
                    this.map[x + WIDTH * y + WIDTH] = Terrain.WALL;
                }
            }
        Painter.fill(this, 12, 24, 39, 3, Terrain.EMPTY_SP);


        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 10 && this.distance(amuletCell, m)< 35 && !cellAdjacentToBorderCells(m)) candidates.add(m);
        }

        dropMany(candidates,
                new Honeypot(),
                new PotionOfHealing(),
                new PotionOfLiquidFlame(),
                new PotionOfFrost(),
                new PotionOfLevitation(),
                new PotionOfToxicGas(),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SEED),
                Generator.random(Generator.Category.SEED),
                Generator.random(Generator.Category.SEED),
                Generator.randomUsingDefaults(Generator.Category.SCROLL),
                new ScrollOfGolems(),
                new ScrollOfAnimation(),
                new StoneOfFlock(),
                Generator.random(Generator.Category.MIS_T2),
                Generator.random(Generator.Category.MIS_T1),
                new GoldenKey(this.arenaDepth),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.SEED)
                );


        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;

        MeleeWeapon someCursedWeapon = (MeleeWeapon) Generator.random(Generator.Category.WEP_T5);
        someCursedWeapon.cursed = true;
        this.drop(someCursedWeapon, Random.element(candidates)).type = Heap.Type.SKELETON;
        super.addDestinations();
    }

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);


        if (wave == 1) {
            ArrayList<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i<4;i++) runnables.add(null);
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    Camera.main.panFollow(Char.findChar(amuletCell+WIDTH).sprite, 2f);
                }
            });
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    Camera.main.panFollow(Char.findChar(amuletCell).sprite, 2f);
                }
            });
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l1w1start1"),
                            Messages.get(RatKing.class, "l1w1start2"),
                            Messages.get(RatKing.class, "l1w1start3"),
                            Messages.get(RatKing.class, "l1w1start4"),
                            Messages.get(RatKing.class, "l1w1start5"),
                            Messages.get(RatKing.class, "l1w1start6"),
                            Messages.get(RatKing.class, "l1w1start7"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    }, WndDialogueWithPic.WndType.NORMAL, runnables);
        }
        if (wave == 5) {;
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l1w5start1"),
                            Messages.get(RatKing.class, "l1w5start2"),
                            Messages.get(RatKing.class, "l1w5start3")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                    }
            );
        }

        if (wave == 10) {
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l1w10start1"),
                            Messages.get(RatKing.class, "l1w10start2"),
                            Messages.get(RatKing.class, "l1w10start3"),
                            Messages.get(RatKing.class, "l1w10start4"),
                            Messages.get(RatKing.class, "l1w10start5"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    });
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 100;
        Dungeon.gold += goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
        if (wave == 1) {
            ArrayList<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i<4;i++) runnables.add(null);
            boolean isbowhere = true;

            runnables.add(new Runnable() {
                @Override
                public void run() {
                    int cell = amuletCell+1;
                    int cell2 = amuletCell+1+WIDTH;
                    int cell3 = amuletCell+1-WIDTH;
                    Camera.main.panTo(DungeonTilemap.tileCenterToWorld(cell), 2f);
                    PointF source = DungeonTilemap.raisedTileCenterToWorld(cell);
                    PointF dest = DungeonTilemap.tileCenterToWorld(cell);
                    PointF source2 = DungeonTilemap.raisedTileCenterToWorld(cell2);
                    PointF dest2 = DungeonTilemap.tileCenterToWorld(cell2);
                    PointF source3 = DungeonTilemap.raisedTileCenterToWorld(cell3);
                    PointF dest3 = DungeonTilemap.tileCenterToWorld(cell3);
                    source.y -= 150;
                    source2.y -= 150;
                    source3.y -= 150;
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source,
                                    dest,
                                    new SpawnerWand(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new SpawnerWand(), cell);
                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                        }
                                    },
                                    200f,
                                    100f);
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source2,
                                    dest2,
                                    new SpawnerCrossbow(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new SpawnerCrossbow(), cell2);
                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                        }
                                    },
                                    250f,
                                    50f);
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source3,
                                    dest3,
                                    new SpawnerWall(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new SpawnerWall(), cell3);
                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                        }
                                    },
                                    220f,
                                    50f);
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source2,
                                    dest2,
                                    new SpawnerCrossbow(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new SpawnerCrossbow(), cell2);
                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                        }
                                    },
                                    210f,
                                    60f);
                }
            });
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    Camera.main.panFollow(towerShopKeeper.sprite, 2f);
                }
            });
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    Camera.main.panFollow(normalShopKeeper.sprite, 2f);
                }
            });
            if (!(Char.findChar(amuletCell+WIDTH) instanceof TowerCrossbow1)) isbowhere = false;
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Dungeon.level.distance(Dungeon.hero.pos, amuletCell) < 10 ? (isbowhere ? Messages.get(RatKing.class, "l1w1end1") : Messages.get(RatKing.class, "l1w1end1nobow")) : Messages.get(RatKing.class, "l1w1end1herofar"),
                            Dungeon.level.distance(Dungeon.hero.pos, amuletCell) < 10 ? Messages.get(RatKing.class, "l1w1end2") : Messages.get(RatKing.class, "l1w1end2herofar"),
                            Messages.get(RatKing.class, "l1w1end3"),
                            Messages.get(RatKing.class, "l1w1end4"),
                            Messages.get(RatKing.class, "l1w1end5"),
                            Messages.get(RatKing.class, "l1w1end6"),
                            Messages.get(RatKing.class, "l1w1end7"),
                            Messages.get(RatKing.class, "l1w1end8")
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    }, WndDialogueWithPic.WndType.NORMAL, runnables);
        }
        if (wave == 5) {
            int totalprice = 0;
            int wallsprice = 0;
            int otherprice = 0;
            for (Mob mob : Level.mobs) if (mob instanceof Tower){
                Tower tower = (Tower) mob;
                totalprice+=tower.cost;
                if (tower instanceof TowerCWall || tower instanceof TowerGuard1) {
                    wallsprice+=tower.cost;
                } else otherprice+=tower.cost;
            }
            if (totalprice < 1000) {
                WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                        new String[]{
                                Messages.get(RatKing.class, "l1w5end_notowers")
                        },
                        new byte[]{
                                WndDialogueWithPic.RUN,
                        });
            } else if (otherprice < 1100) {
                WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                        new String[]{
                                Messages.get(RatKing.class, "l1w5end_noshootings")
                        },
                        new byte[]{
                                WndDialogueWithPic.RUN,
                        });
            } else if (wallsprice < 350) {
                WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                        new String[]{
                                Messages.get(RatKing.class, "l1w5end_nowalls")
                        },
                        new byte[]{
                                WndDialogueWithPic.RUN,
                        });
            } else {
                WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                        new String[]{
                                Messages.get(RatKing.class, "l1w5end_good")
                        },
                        new byte[]{
                                WndDialogueWithPic.RUN,
                        });
            }


        }
        if (wave == 10) {
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l1w10end1"),
                            Messages.get(RatKing.class, "l1w10end2"),
                            Messages.get(RatKing.class, "l1w10end3"),
                            Messages.get(RatKing.class, "l1w10end4"),
                            Messages.get(RatKing.class, "l1w10end5")
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN,
                    }, WndDialogueWithPic.WndType.FINAL);
        }
    }

    @Override
    public void initNpcs() {
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell + WIDTH;
        tower.sellable = false;
        GameScene.add(tower);
        TowerWall1 wall = new TowerWall1();
        wall.sellable = false;
        wall.pos = amuletCell + 3;
        TowerWall1 wall2 = new TowerWall1();
        wall2.sellable = false;
        wall2.pos = amuletCell + 3 + WIDTH;
        TowerWall1 wall3 = new TowerWall1();
        wall3.sellable = false;
        wall3.pos = amuletCell + 3 - WIDTH;
        GameScene.add(wall);
        GameScene.add(wall2);
        GameScene.add(wall3);
        super.initNpcs();
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RIGHT, 1);
    }



}
