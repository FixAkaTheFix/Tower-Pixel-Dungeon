package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.towerpixel.towerpixeldungeon.actors.buffs.Hex;
import com.towerpixel.towerpixeldungeon.actors.buffs.Humongous;
import com.towerpixel.towerpixeldungeon.actors.buffs.Ooze;
import com.towerpixel.towerpixeldungeon.actors.buffs.Slow;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vulnerable;
import com.towerpixel.towerpixeldungeon.actors.mobs.Albino;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.ChiefRat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goo;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Skeleton;
import com.towerpixel.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGraveCrypt;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGraveElite;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWandLightning;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.KingSprite;
import com.towerpixel.towerpixeldungeon.sprites.RatKingSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.ServiceLoader;

public class Arena20 extends Arena {



    {
        name = "The King's fall";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 71;
        HEIGHT = 71;

        startGold = 13000;
        startLvl = 25;

        maxWaves = 25;

        amuletCell = 7 + WIDTH*35;
        exitCell = amuletCell;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        normalShopKeeperCell = amuletCell-6 + WIDTH*3;
        towerShopKeeperCell = amuletCell-6 - WIDTH*3;
        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }

    private final int kingCell = 25 + WIDTH*35;

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 71, 71, Terrain.WALL);
        Painter.fill(this, 1, 1, 69, 69, Terrain.EMPTY);
        Painter.fill(this, 0, 0, 4, 29, Terrain.WALL);
        Painter.fill(this, 0, 42, 4, 29, Terrain.WALL);
        Painter.fill(this,3,1,1,28, Terrain.BOOKSHELF);
        Painter.fill(this,3,42,1,28, Terrain.BOOKSHELF);

        for (int x = 6;x<21;x+=4){
            Painter.fill(this,x,31,1,1,Terrain.STATUE_SP);
            Painter.fill(this,x,39,1,1,Terrain.STATUE_SP);
        }

        //carpet
        Painter.fill(this, 1, 31, 25, 9, Terrain.EMPTY_SP);

        //throne
        Painter.fillEllipse(this,17,27,17,17,Terrain.EMPTY_SP);
        Painter.fillEllipse(this,22,32,7,7,Terrain.EMPTY);
        Painter.fill(this, 25, 34, 1,3,Terrain.STATUE);
        Painter.fill(this, 25, 35, 1,1,Terrain.PEDESTAL);
        Painter.fill(this, 26, 34, 1,3,Terrain.WALL);

        //some water stripes
        Painter.fill(this, 3, 19, 40,5,Terrain.EMPTY_SP);
        Painter.fill(this, 3, 12, 40,5,Terrain.EMPTY_SP);
        Painter.fill(this, 3, 47, 66,5,Terrain.EMPTY_SP);
        Painter.fill(this, 3, 54, 66,5,Terrain.EMPTY_SP);

        Painter.fill(this, 3, 20, 40,3,Terrain.WATER);
        Painter.fill(this, 3, 13, 40,3,Terrain.EMPTY_SP);
        Painter.fill(this, 3, 48, 66,3,Terrain.WATER);
        Painter.fill(this, 3, 55, 66,3,Terrain.EMPTY_SP);

        for (int x = 5; x<60;x+=5){
            Painter.fill(this, x, 19, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 12, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 23, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 16, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 47, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 54, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 51, 1, 1, Terrain.STATUE_SP);
            Painter.fill(this, x, 58, 1, 1, Terrain.STATUE_SP);
        }

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    private BossDwarfKing dwarfKing;
    @Override
    public void initNpcs() {
        super.initNpcs();

        dwarfKing = new BossDwarfKing();
        dwarfKing.pos = kingCell;
        GameScene.add(dwarfKing);
        level.occupyCell(dwarfKing);

        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.EMPTY && distance(amuletCell, m)>30) candidates.add(m);
        }
        for (int i = 0; i < 50; i++){
            int x1 = Random.element(candidates);
            this.drop(Random.oneOf(
                            TowerInfo.getTowerSpawner(SPDSettings.towerslot1()),
                            TowerInfo.getTowerSpawner(SPDSettings.towerslot2()),
                            TowerInfo.getTowerSpawner(SPDSettings.towerslot3()),
                            TowerInfo.getTowerSpawner(SPDSettings.towerslot4()),
                            Generator.random(Generator.Category.POTION),
                            Generator.random(Generator.Category.SCROLL),
                            Generator.random(Generator.Category.STONE),
                            Generator.random(Generator.Category.SEED),
                            Generator.random(Generator.Category.SEED),
                            Generator.random(Generator.Category.WAND),
                            Generator.random(Generator.Category.RING),
                            Generator.random(Generator.Category.WEP_T4),
                            Generator.random(Generator.Category.WEP_T5),
                            Generator.random(Generator.Category.ARMOR),
                            new Gold(Random.Int(100,300)),
                            new Gold(Random.Int(100,300)),
                            new Gold(Random.Int(100,300))),
                    x1).type = Heap.Type.CHEST;;
        }

/*
        Tower s11 = TowerInfo.getTowerNewInstance(slot1);
        Tower s12 = TowerInfo.getTowerNewInstance(slot1);
        Tower s13 = TowerInfo.getTowerNewInstance(slot1);
        Tower s14 = TowerInfo.getTowerNewInstance(slot1);
        Tower s21 = TowerInfo.getTowerNewInstance(slot2);
        Tower s22 = TowerInfo.getTowerNewInstance(slot2);
        Tower s23 = TowerInfo.getTowerNewInstance(slot2);
        Tower s24 = TowerInfo.getTowerNewInstance(slot2);
        Tower s31 = TowerInfo.getTowerNewInstance(slot3);
        Tower s32 = TowerInfo.getTowerNewInstance(slot3);
        Tower s33 = TowerInfo.getTowerNewInstance(slot3);
        Tower s34 = TowerInfo.getTowerNewInstance(slot3);
        Tower s41 = TowerInfo.getTowerNewInstance(slot4);
        Tower s42 = TowerInfo.getTowerNewInstance(slot4);
        Tower s43 = TowerInfo.getTowerNewInstance(slot4);
        Tower s44 = TowerInfo.getTowerNewInstance(slot4);

        s11.sellable = false;
        s12.sellable = false;
        s13.sellable = false;
        s14.sellable = false;
        s21.sellable = false;
        s22.sellable = false;
        s23.sellable = false;
        s24.sellable = false;
        s31.sellable = false;
        s32.sellable = false;
        s33.sellable = false;
        s34.sellable = false;
        s41.sellable = false;
        s42.sellable = false;
        s43.sellable = false;
        s44.sellable = false;

        s11.pos = amuletCell+6*WIDTH;
        s12.pos = amuletCell+6*WIDTH+1;
        s13.pos = amuletCell-6*WIDTH;
        s14.pos = amuletCell-6*WIDTH+1;
        s21.pos = amuletCell+5*WIDTH;
        s22.pos = amuletCell+5*WIDTH+1;
        s23.pos = amuletCell-5*WIDTH;
        s24.pos = amuletCell-5*WIDTH+1;
        s31.pos = amuletCell+4*WIDTH;
        s32.pos = amuletCell+4*WIDTH+1;
        s33.pos = amuletCell-4*WIDTH;
        s34.pos = amuletCell-4*WIDTH+1;
        s41.pos = amuletCell+3*WIDTH;
        s42.pos = amuletCell+3*WIDTH+1;
        s43.pos = amuletCell-3*WIDTH;
        s44.pos = amuletCell-3*WIDTH+1;

        GameScene.add(s11);
        GameScene.add(s12);
        GameScene.add(s13);
        GameScene.add(s14);
        GameScene.add(s21);
        GameScene.add(s22);
        GameScene.add(s23);
        GameScene.add(s24);
        GameScene.add(s31);
        GameScene.add(s32);
        GameScene.add(s33);
        GameScene.add(s34);
        GameScene.add(s41);
        GameScene.add(s42);
        GameScene.add(s43);
        GameScene.add(s44);
*/

    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 1000;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        super.doStuffEndwave(wave);
    }

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        if (wave == 1){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "start1"),
                            Messages.get(BossDwarfKing.class, "start2"),
                            Messages.get(BossDwarfKing.class, "start3"),
                            Messages.get(BossDwarfKing.class, "start4"),
                    });
        }
    }

    @Override
    public void deployMobs(int wave) {
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        if (wave == 5){
            int crossbows       = 0;
            int wands           = 0;
            int walls           = 0;
            int lightnings      = 0;
            int dartguns        = 0;
            int disintegrations = 0;
            int guards          = 0;
            int tntlogs         = 0;
            int cannons         = 0;
            int graveyards      = 0;

            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if (m instanceof TowerCrossbow1) crossbows       +=coins;
                    if (m instanceof TowerWand1) wands           +=coins;
                    if (m instanceof TowerWall1) walls           +=coins;
                    if (m instanceof TowerLightning1) lightnings      +=coins;
                    if (m instanceof TowerDartgun1) dartguns        +=coins;
                    if (m instanceof TowerDisintegration1) disintegrations +=coins;
                    if (m instanceof TowerGuard1) guards          +=coins;
                    if (m instanceof TowerTntLog) tntlogs         +=coins;
                    if (m instanceof TowerCannon1 || m instanceof TowerCannonNuke || m instanceof TowerCannonMissileLauncher) cannons         +=coins;
                    if (m instanceof TowerGrave1 || m instanceof TowerGrave2 || m instanceof TowerGrave3 || m instanceof TowerGraveElite || m instanceof TowerGraveCrypt) graveyards+=coins;
                }
            }
            int maxcoinson = Math.max(crossbows,Math.max(wands,Math.max(walls,Math.max(lightnings,Math.max(dartguns,Math.max(disintegrations,Math.max(guards,Math.max(tntlogs,Math.max(cannons,graveyards)))))))));
            if (maxcoinson == crossbows) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_crossbow"),
                                Messages.get(BossDwarfKing.class, "strategy_crossbow"),
                        });
                deploymobs(5001, Direction.TOORIGHT,10 );
            } else if (maxcoinson == wands) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_wand"),
                                Messages.get(BossDwarfKing.class, "strategy_wand"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerWand1){
                        Buff.affect(x, Ooze.class).set(7);
                        CellEmitter.center(x.pos).start(ShadowParticle.UP, 0.1f, 10);
                    }
                }
                deploymobs(5002, Direction.TOORIGHT,10 );
            } else if (maxcoinson == walls) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_wall"),
                                Messages.get(BossDwarfKing.class, "strategy_wall"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerWall1){
                        Buff.affect(x, Vulnerable.class, 10000);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5003, Direction.TOORIGHT,10 );
            } else if (maxcoinson == cannons) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_cannon"),
                                Messages.get(BossDwarfKing.class, "strategy_cannon"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerCannon1 || x instanceof TowerCannonNuke){
                        Buff.affect(x, Hex.class, 10000);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                    if (x instanceof TowerCannonMissileLauncher){
                        Buff.affect(x, Blindness.class, 100);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5004, Direction.TOORIGHT,10 );
            } else if (maxcoinson == dartguns) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_dartgun"),
                                Messages.get(BossDwarfKing.class, "strategy_dartgun"),
                        });
                deploymobs(5005, Direction.RIGHT,50 );
            } else if (maxcoinson == disintegrations) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_disintegration"),
                                Messages.get(BossDwarfKing.class, "strategy_disintegration"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerDisintegration1){
                        Buff.affect(x, Amok.class, 15);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5006, Direction.TOORIGHT,10 );
            } else if (maxcoinson == lightnings) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_lightnings"),
                                Messages.get(BossDwarfKing.class, "strategy_lightnings"),
                        });
                deploymobs(5007, Direction.TOORIGHT,10 );
            } else if (maxcoinson == guards) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_guard"),
                                Messages.get(BossDwarfKing.class, "strategy_guard"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerGuard1){
                        Buff.affect(x, Amok.class, 3);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5008, Direction.TOORIGHT,10 );
            } else if (maxcoinson == graveyards) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_graveyard"),
                                Messages.get(BossDwarfKing.class, "strategy_graveyard"),
                        });
                deploymobs(5009, Direction.TOORIGHT,10 );
            } else if (maxcoinson == tntlogs) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_tntlog"),
                                Messages.get(BossDwarfKing.class, "strategy_tntlog"),
                        });
                deploymobs(5010, Direction.TOORIGHT,10 );
            }

        } else if (wave == 10){
            int magical       = 0;
            int nonmagical           = 0;

            int south = 0;
            int north = 0;
            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if ((m instanceof TowerWand1 || m instanceof TowerLightning1 || m instanceof TowerDisintegration1))
                        magical+=coins;
                    else if(!(m instanceof TowerWall1)) nonmagical+=coins;
                    if (m.pos/WIDTH > 35) south+=coins;
                    if (m.pos/WIDTH < 35) north+=coins;

                }
            }
            int mistakes = 0;
            String speechtype = "";
            String speechflank = "";
            String speechfinal = "";
            int num;
            Direction d;

            if (nonmagical > magical + 3000) {
                speechtype = Messages.get(BossDwarfKing.class,"direction_physical");
                num = 10001;
                mistakes++;
            } else if (magical > nonmagical + 1000) {;
                speechtype = Messages.get(BossDwarfKing.class,"direction_magical");
                num = 10002;
                mistakes++;
            } else {
                speechtype = Messages.get(BossDwarfKing.class,"direction_both");
                num = 10003;
            }
            if (north > south + 1000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction_south");
                d = Direction.TOODOWN;
                mistakes++;
            } else if (south > north + 1000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction_north");
                d = Direction.TOOUP;
                mistakes++;
            } else {
                speechflank = Messages.get(BossDwarfKing.class,"direction_balanced");
                d = Direction.RIGHT;
            }
            if (mistakes == 2){
                speechfinal = Messages.get(BossDwarfKing.class,"direction_verybad");
            } else if (mistakes == 1){
                speechfinal = Messages.get(BossDwarfKing.class,"direction_bad");
            } else {
                speechfinal = Messages.get(BossDwarfKing.class,"direction_good");
            }
            deploymobs(num, d, 5);
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            speechtype,
                            speechflank,
                            speechfinal
                    }
                    );
        } else if (wave == 14){
            if (level.distance(amuletCell, Dungeon.hero.pos)>20){
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_hero")
                        });
                for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(Dungeon.hero.pos + i)== null){
                    Mob skele = Random.oneOf(new Skeleton(), new Skeleton(), new SkeletonArmored());
                    skele.pos = Dungeon.hero.pos + i;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            } else if (level.distance(amuletCell, Dungeon.hero.pos)>10){
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_amulet")
                        });
                for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(amuletCell + i)== null){
                    Skeleton skele = new Skeleton();
                    skele.pos = amuletCell + i;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            } else {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_walls")
                        });
                for (Mob wall : level.mobs) if (wall instanceof TowerWall1) for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(amuletCell + i)== null){
                    Skeleton skele = new Skeleton();
                    skele.pos = amuletCell + i;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            }
            deploymobs(14, Direction.TOORIGHT, 10);
        }else if (wave == 17){
            int magical       = 0;
            int nonmagical           = 0;

            int south = 0;
            int north = 0;
            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if ((m instanceof TowerWand1 || m instanceof TowerLightning1 || m instanceof TowerDisintegration1))
                        magical+=coins;
                    else if(!(m instanceof TowerWall1)) nonmagical+=coins;
                    if (m.pos/WIDTH > 35) south+=coins;
                    if (m.pos/WIDTH < 35) north+=coins;

                }
            }
            int mistakes = 0;
            String speechtype = "";
            String speechflank = "";
            String speechfinal = "";
            int num;
            Direction d;

            if (nonmagical > magical + 5000) {
                speechtype = Messages.get(BossDwarfKing.class,"direction2_physical");
                num = 17001;
                mistakes++;
            } else if (magical > nonmagical + 3000) {;
                speechtype = Messages.get(BossDwarfKing.class,"direction2_magical");
                num = 17002;
                mistakes++;
            } else {
                speechtype = Messages.get(BossDwarfKing.class,"direction2_both");
                num = 17003;
            }
            if (north > south + 3000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_south");
                d = Direction.TOODOWN;
                mistakes++;
            } else if (south > north + 3000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_north");
                d = Direction.TOOUP;
                mistakes++;
            } else {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_balanced");
                d = Direction.RIGHT;
            }
            if (mistakes == 2){
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_verybad");
            } else if (mistakes == 1){
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_bad");
            } else {
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_good");
            }
            deploymobs(num, d, 5);
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            speechtype,
                            speechflank,
                            speechfinal
                    }
            );
        }else if (wave == 20){
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "rkdkrat1"),
                            Messages.get(BossDwarfKing.class, "rkdkrat2")
                    }
            );
            for (int y = 34; y<37; y++) {
                Rat helper = new Rat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new Rat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new Rat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new Rat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new Rat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new Albino();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
            for (int y = 34; y<37; y++) {
                Rat helper = new ChiefRat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
        } else if (wave == 23){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "prespeech1"),
                            Messages.get(BossDwarfKing.class, "prespeech2"),
                            Messages.get(BossDwarfKing.class, "prespeech3"),
                            Messages.get(BossDwarfKing.class, "prespeech4"),
                            Messages.get(BossDwarfKing.class, "prespeech5"),
                            Messages.get(BossDwarfKing.class, "prespeech6")
                    }
            );
            deploymobs(23, Direction.RIGHT, 10);
        } else if (wave == 25){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "speech1"),
                            Messages.get(BossDwarfKing.class, "speech2"),
                            Messages.get(BossDwarfKing.class, "speech3")
                    }
            );
            deploymobs(25, Direction.RIGHT, 10);
            if (dwarfKing == null) {
               if (Char.findChar(kingCell)!=null && Char.findChar(kingCell)instanceof BossDwarfKing) dwarfKing = (BossDwarfKing) Char.findChar(kingCell);
            }
            if (dwarfKing == null) {
                for (Mob mob : level.mobs) {
                    if (mob instanceof BossDwarfKing){
                        dwarfKing = (BossDwarfKing) mob;
                    }
                }
            }
            dwarfKing.awaken();
        } else {
            deploymobs(wave, Direction.TOORIGHT, 10);
        }
        if (wave > 20){
            for (int y = 34; y<37; y++) {
                Rat helper = new ChiefRat();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = 1+WIDTH*y;
                GameScene.add(helper);
            }
        }
    }

    @Override
    public void affectMob(Mob mob) {
        super.affectMob(mob);
        Buff.affect(mob, ChampionEnemy.Giant.class);
        Buff.affect(mob, Humongous.class);
        Buff.affect(mob, Slow.class,10000);
        Buff.affect(mob, Strength.class,10000);
    }

    @Override
    public void playLevelMusic() {

        boolean dkAliveAndReadyToKill = false;
        for (Mob m : mobs){
            if (m instanceof BossDwarfKing && ((BossDwarfKing)m).battleMode!=0) {
                dkAliveAndReadyToKill = true;
                break;
            }
        }
        if (dkAliveAndReadyToKill){
            Music.INSTANCE.play(Assets.Music.CITY_BOSS_SPEDUP, true);
            return;
        } else Music.INSTANCE.play(Assets.Music.CITY_BOSS, true);
    }
    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CITY;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CITY;
    }


    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(CityLevel.class, "water_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CityLevel.class, "high_grass_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return Messages.get(CityLevel.class, "entrance_desc");
            case Terrain.EXIT:
                return Messages.get(CityLevel.class, "exit_desc");
            case Terrain.WALL_DECO:
            case Terrain.EMPTY_DECO:
                return Messages.get(CityLevel.class, "deco_desc");
            case Terrain.EMPTY_SP:
                return Messages.get(CityLevel.class, "sp_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(CityLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(CityLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addCityVisuals( this, visuals );
        return visuals;
    }

    public static void addCityVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Arena19.Smoke( i ) );
            }
        }
    }

    public static class Smoke extends Emitter {

        private int pos;

        public static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                Arena19.SmokeParticle p = (Arena19.SmokeParticle)emitter.recycle( Arena19.SmokeParticle.class );
                p.reset( x, y );
            }
        };

        public Smoke( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 6, p.y - 4, 12, 12 );

            pour( factory, 0.2f );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }

    public static final class SmokeParticle extends PixelParticle {

        public SmokeParticle() {
            super();

            color( 0x000000 );
            speed.set( Random.Float( -2, 4 ), -Random.Float( 3, 6 ) );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan = 2f;
        }

        @Override
        public void update() {
            super.update();
            float p = left / lifespan;
            am = p > 0.8f ? 1 - p : p * 0.25f;
            size( 6 - p * 3 );
        }
    }

    private static final String DKID = "dkid";
}
