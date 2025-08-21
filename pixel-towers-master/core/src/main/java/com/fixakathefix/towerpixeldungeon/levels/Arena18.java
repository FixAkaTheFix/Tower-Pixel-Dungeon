package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.Item.updateQuickslot;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DrillBig;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Statue;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.bombs.Firebomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.HolyBomb;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.fixakathefix.towerpixeldungeon.items.spells.SummonElemental;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemAttack;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemHealing;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.fixakathefix.towerpixeldungeon.journal.Bestiary;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena18 extends ArenaCity {

    {
        name = "Storm the gates";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 40;
        WIDTH = 500;
        HEIGHT = 30;

        startGold = 3300;
        startLvl = 20;
        arenaDepth = 18;
        maxWaves = 15;

        amuletCell = 7 + WIDTH * 15;
        exitCell = amuletCell;
        towerShopKeeper = null;
        normalShopKeeper = null;

        waveCooldownNormal = 20;
        waveCooldownBoss = 20;
    }


    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.RAT_PUNCHER_SPEDUP},
                new float[]{1},
                false);
    }
    @Override
    protected boolean build() {
        if (mode == WndModes.Modes.CHALLENGE) {
            slot1 = TowerInfo.AllTowers.GUARD;
            slot2 = TowerInfo.AllTowers.WALL;
            slot3 = TowerInfo.AllTowers.CANNON;
            slot4 = TowerInfo.AllTowers.TNTLOG;
        }


        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 400, 30, Terrain.WALL);
        Painter.fill(this, 1, 1, 15, 28, Terrain.EMPTY);

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }


    //those generate on the spot
    private void buildGraveyard(int x) {
        Painter.fill(level, x, 11, 5, 10, Terrain.BARRICADE);
        Painter.fill(level, x + 5, 1, 25, 28, Terrain.GRASS);
        for (int x1 = x; x1 < x + 25; x1 += 5)
            for (int y1 = 15; y1 < 26; y1 += 10) {
                if (Math.random() > 0.2) level.map[x1 + WIDTH * y1] = Terrain.STATUE;
            }
        level.buildFlagMaps();
        GameScene.updateMap();
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 1; y1 < 28; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }

        dropMany(candidates,
                Random.oneOf(
                        TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot4)
                ),
                Random.oneOf(
                        TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot4)
                ),
                Random.oneOf(
                        TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                        TowerInfo.getTowerSpawner(Dungeon.level.slot4)
                ));
        dropMany(Heap.Type.SKELETON, candidates,
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION)
                );


        dropMany(Heap.Type.TOMB,candidates,
                Generator.random(Generator.Category.WAND).upgrade(3),
                Generator.random(Generator.Category.WAND).upgrade(3),
                Generator.random(Generator.Category.WAND).upgrade(3),
                Generator.random(Generator.Category.RING).upgrade(3),
                Generator.random(Generator.Category.RING).upgrade(3),
                Generator.random(Generator.Category.RING).upgrade(3)
                );



        candidates.clear();
        for (int x1 = x; x1 < x + 25; x1++)
            for (int y1 = 1; y1 < 28; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        if (Math.random() > 0.66) for (int i = 0; i < (wave+1) * 20; i++) {
            Wraith wraith = new Wraith();
            wraith.pos = Random.element(candidates);
            wraith.state = wraith.WANDERING;
            GameScene.add(wraith);
            level.occupyCell(wraith);
        }
        else if (Math.random() > 0.66) for (int i = 0; i < (wave+1) * 8; i++) {
            Mob skele;
            if (i % 6 == 0) skele = new SkeletonArmored();
            else skele = new Skeleton();
            skele.pos = Random.element(candidates);
            skele.state = skele.WANDERING;
            GameScene.add(skele);
            level.occupyCell(skele);
        }
        else for (int i = 0; i < (wave+1) * 5; i++) {
                Mob necro;
                if (i % 5 == 0) necro = new Skeleton();
                else necro = new Warlock();
                necro.pos = Random.element(candidates);
                necro.state = necro.WANDERING;
                GameScene.add(necro);
                level.occupyCell(necro);
            }

    }
    private void buildLine(int x) {
        Painter.fill(level, x, 15, 5, 10, Terrain.WALL);
        Painter.fill(level, x + 5, 1, 25, 28, Terrain.EMPTY_SP);
        Painter.fill(level, x+19, 8, 1, 14, Terrain.PEDESTAL);
        level.buildFlagMaps();
        GameScene.updateMap();
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 6; y1 < 29; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        for (int x1 = x; x1 < x + 13; x1 += 2)
            for (int y1 = 8; y1 < 28; y1 += 13) {
                level.map[x1 + WIDTH * y1] = Terrain.BARRICADE;
            }
        candidates.clear();
        for (int yb = 8;yb<24;yb+=2){
            LineCannon cann = new LineCannon();
            cann.pos = x+20 + yb*WIDTH;
            cann.state = cann.HUNTING;
            GameScene.add(cann);
            level.occupyCell(cann);
        }

    }
    private void buildGolem(int x) {
        Painter.fill(level, x, 10, 5, 10, Terrain.WALL);
        Painter.fill(level, x + 5, 1, 25, 28, Terrain.EMPTY_SP);
        level.buildFlagMaps();
        ArrayList<Integer> candidates = new ArrayList<>();

        for (int x1 = x; x1 < x + 25; x1 += 3)
            for (int y1 = 8; y1 < 24; y1 += 12) {
                level.map[x1 + WIDTH * y1] = Terrain.BARRICADE;
            }


        Painter.fill(level, x+19, 8, 1, 15, Terrain.BARRICADE);
        Painter.fill(level, x+15, 12, 1, 15, Terrain.BARRICADE);
        Painter.fill(level, x+11, 8, 1, 15, Terrain.BARRICADE);
        level.buildFlagMaps();
        GameScene.updateMap();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 6; y1 < 28; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        level.drop(new SpawnerTotemHealing(), Random.element(candidates));
        level.drop(new SpawnerTotemAttack(), Random.element(candidates));
        level.drop(new SpawnerTotemNecrotic(), Random.element(candidates));
        level.drop(new SpawnerTotemShield(), Random.element(candidates));
        candidates.clear();

        for (int yb = 8;yb<16;yb+=1){
            Golem crossbow = new Golem();
            crossbow.pos = x+20 + yb*WIDTH;
            crossbow.state = crossbow.HUNTING;
            GameScene.add(crossbow);
            level.occupyCell(crossbow);
        }

    }

    private void buildParade(int x) {
        Painter.fill(level, x, 10, 5, 10, Terrain.GRASS);
        Painter.fill(level, x + 5, 1, 25, 28, Terrain.EMPTY);
        Painter.fill(level, x+5,10,25,10,Terrain.EMPTY_SP);
        level.buildFlagMaps();
        for (int x1 = x+5; x1 < x + 25; x1 += 3)
            for (int y1 = 10; y1 < 21; y1 += 9) {
                level.map[x1 + WIDTH * y1] = Terrain.STATUE_SP;
            }
        for (int x1 = x+5; x1 < x + 25; x1 += 1)
            for (int y1 = 1; y1 < 23; y1 += 15) {
                if (Math.random()>0.5) level.map[x1 + WIDTH * y1] = Terrain.HIGH_GRASS;
            }
        for (int x1 = x+5; x1 < x + 25; x1 += 3)
            for (int y1 = 1; y1 < 23; y1 += 10) {
                if (Math.random()>0.95) level.map[x1 + WIDTH * y1] = Terrain.WALL_DECO;
            }
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 1; y1 < 28; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }

        dropMany(candidates,
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerWall(),
                Generator.random(Generator.Category.SEED),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.MIS_T3),
                Generator.random(Generator.Category.ARMOR),
                Generator.random(Generator.Category.FOOD),
                Generator.random(Generator.Category.FOOD),
                Generator.random(Generator.Category.FOOD),
                Generator.random(Generator.Category.FOOD),
                Generator.random(Generator.Category.FOOD)
        );




        candidates.clear();
        for (int x1 = x; x1 < x + 25; x1++)
            for (int y1 = 1; y1 < 29; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        if (Math.random() > 0.5) for (int i = 0; i < (wave+1) * 4; i++) {
            Elemental wraith = new Elemental.ShockElemental();
            wraith.pos = Random.element(candidates);
            wraith.state = wraith.WANDERING;
            GameScene.add(wraith);
            level.occupyCell(wraith);
        }
        else for (int i = 0; i < (wave+1) * 4; i++) {
                Mob necro;
                if (i % 5 == 0) necro = new Elemental.FrostElemental();
                else necro = new Elemental.FireElemental();
                necro.pos = Random.element(candidates);
                necro.state = necro.WANDERING;
                GameScene.add(necro);
                level.occupyCell(necro);
            }
        level.buildFlagMaps();
        GameScene.updateMap();
    }
    private void buildGates(int x) {
        Painter.fill(level, x, 10, 5, 10, Terrain.WATER);
        Painter.fill(level, x + 5, 1, 25, 23, Terrain.EMPTY);
        Painter.fill(level, x+5,10,25,5,Terrain.EMPTY_SP);
        Painter.fill(level, x+5,20,25,5,Terrain.EMPTY_SP);
        Painter.fill(level, x+25, 10, 5, 10, Terrain.WALL);
        Painter.fill(level, x+25, 13, 5, 4, Terrain.EMPTY_SP);
        level.buildFlagMaps();
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 1; y1 < 29; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }


        for (int x1 = x+5; x1 < x + 25; x1 += 3)
            for (int y1 = 10; y1 < 21; y1 += 10) {
                level.map[x1 + WIDTH * y1] = Terrain.STATUE_SP;
            }
        for (int x1 = x+5; x1 < x + 25; x1 += 3)
            for (int y1 = 1; y1 < 23; y1 += 10) {
                if (Math.random()>0.8) level.map[x1 + WIDTH * y1] = Terrain.WATER;
            }
        for (int x1 = x+5; x1 < x + 25; x1 += 3)
            for (int y1 = 1; y1 < 23; y1 += 10) {
                if (Math.random()>0.95) level.map[x1 + WIDTH * y1] = Terrain.STATUE_SP;
            }
        candidates.clear();
        for (int x1 = x; x1 < x + 25; x1++)
            for (int y1 = 1; y1 < 29; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        for (int i = 0; i < (wave)*2; i++) {
            Mob necro;
            necro = new Golem();
            necro.pos = Random.element(candidates);
            necro.state = necro.WANDERING;
            GameScene.add(necro);
            level.occupyCell(necro);
        }
        level.buildFlagMaps();
        GameScene.updateMap();
    }

    private void buildTown(int x) {
        Painter.fill(this, x + 5, 1, 25, 28, Terrain.EMPTY);
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x1 = x+5 ; x1 < x + 25; x1 += 1)
            for (int y1 = 1; y1 < 25; y1 += 3) {
                if (Math.random() > 0.94) {
                    int width = Random.Int(6, 8);
                    int height = Random.Int(4, 6);
                    Painter.fill(level, x1, y1, width, height, Terrain.WALL);
                    Painter.fill(level, x1, y1+height/2, width, 1, Terrain.EMPTY_SP);
                    Painter.fill(level, x1 + 1, y1 + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                }
            }
        level.buildFlagMaps();
        for (int x1 = x + 5; x1 < x + 20; x1++)
            for (int y1 = 5; y1 < 25; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));
        level.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                TowerInfo.getTowerSpawner(Dungeon.level.slot4)
        ), Random.element(candidates));

        dropMany(Heap.Type.CHEST, candidates,
                Generator.random(Generator.Category.WEAPON),
                Generator.random(Generator.Category.WEAPON),
                Generator.random(Generator.Category.WEAPON),
                Generator.random(Generator.Category.WEAPON),
                Generator.random(Generator.Category.WEAPON),
                Generator.random(Generator.Category.MIS_T1),
                Generator.random(Generator.Category.MIS_T2),
                Generator.random(Generator.Category.MIS_T3),
                Generator.random(Generator.Category.ARMOR)
                );

        level.drop(new GoldenKey(18), Random.element(candidates));
        level.drop(new GoldenKey(18), Random.element(candidates));
        level.drop(Generator.random(Generator.Category.ARMOR).identify().upgrade(3), Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        level.drop(Generator.random(Generator.Category.WEAPON).identify().upgrade(3), Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        candidates.clear();
        for (int x1 = x; x1 < x + 25; x1++)
            for (int y1 = 1; y1 < 29; y1++) {
                if (level.passable[x1 + WIDTH * y1]) candidates.add(x1 + WIDTH * y1);
            }
        if (Math.random() > 0.66) for (int i = 0; i < (wave/1.5f+1) * 4; i++) {
            Monk monk = new Monk();
            monk.pos = Random.element(candidates);
            monk.state = monk.WANDERING;
            GameScene.add(monk);
            level.occupyCell(monk);
        }
        else if (Math.random() > 0.66) for (int i = 0; i < (wave+1) * 2; i++) {
            Mob guard;
            guard = new Statue();
            guard.pos = Random.element(candidates);
            guard.state = guard.WANDERING;
            GameScene.add(guard);
            level.occupyCell(guard);
        }
        else for (int i = 0; i < (wave/4+1); i++) {
                Mob sen;
                sen = new Senior();
                sen.pos = Random.element(candidates);
                sen.state = sen.WANDERING;
                GameScene.add(sen);
                level.occupyCell(sen);
            }
        level.buildFlagMaps();
        GameScene.updateMap();
    }
    @Override
    public void initNpcs() {
        hero.lvl = startLvl;
        hero.updateHT(true);
        Dungeon.gold += startGold;
        updateQuickslot();
        level = this;
        Buff.affect(hero, WaveCooldownBuff.class, 100);
        exitCell = 0;
        amuletTower.pos = amuletCell;
        amuletTower.HP = amuletTower.HT;
        GameScene.add(amuletTower);
        this.occupyCell(amuletTower);
        hero.pos = amuletCell + 1;
        level.occupyCell(hero);
        level.seal();
        GenDrill drill = new GenDrill();
        drill.pos = amuletCell - WIDTH - WIDTH - 3;
        GameScene.add(drill);
        level.occupyCell(drill);

        level.drop(Generator.random(Generator.Category.WEAPON).identify(), hero.pos - WIDTH*5).type = Heap.Type.SKELETON;
        level.drop(Generator.random(Generator.Category.WEAPON).identify(), hero.pos + WIDTH*5).type = Heap.Type.CHEST;

        ArrayList<Item> itemsToSpawn = new ArrayList<>();


        itemsToSpawn.add(new Firebomb());
        itemsToSpawn.add(new SummonElemental());
        itemsToSpawn.add(new HolyBomb());
        itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot1));
        itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot2));
        itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot3));
        itemsToSpawn.add(TowerInfo.getTowerSpawner(Dungeon.level.slot4));

        itemsToSpawn.add(new SpawnerTotemShield());
        itemsToSpawn.add(new SpawnerTotemNecrotic());
        itemsToSpawn.add(new SpawnerTotemHealing());
        itemsToSpawn.add(new SpawnerTotemAttack());
        itemsToSpawn.add(new PotionOfHealing());
        itemsToSpawn.add(new PotionOfStrength());
        itemsToSpawn.add(new ScrollOfSirensSong());
        int b = -Math.round(itemsToSpawn.size()*0.5f) + 1;
        for (Item item : itemsToSpawn) {
            level.drop( item, amuletCell-5 + level.width()*b ).type = Heap.Type.FOR_SALE;//places stuff under the shopkeeper
            b++;
        }
    }

    public static class GenDrill extends DrillBig {

        {
            stepcount = 26;
        }

        @Override
        protected boolean act() {
            if (counter==101) Buff.affect(hero, WaveBuff.class);
            return super.act();
        }

        @Override
        public void moveForward() {
            stepcount++;
            Bestiary.setSeen(DrillBig.class);

            if (stepcount == 32) {
                if (level.wave == 5){
                    ((Arena18) level).buildLine(pos % ((Arena18) level).WIDTH + 10);
                }else if (level.wave == 10){
                    ((Arena18) level).buildGolem(pos % ((Arena18) level).WIDTH + 10);
                } else if (level.wave == 14){
                    ((Arena18) level).buildGates(pos % ((Arena18) level).WIDTH + 10);
                } else {
                    int i = Random.IntRange(1, 3);
                    switch (i) {
                        case 1:
                            ((Arena18) level).buildTown(pos % ((Arena18) level).WIDTH + 10);
                            break;
                        case 2:
                            ((Arena18) level).buildGraveyard(pos % ((Arena18) level).WIDTH + 10);
                            break;
                        case 3:
                            ((Arena18) level).buildParade(pos % ((Arena18) level).WIDTH + 10);
                            break;
                    }
                }
                ((Arena18) Dungeon.level).startWave();
                stepcount = 0;
            }

            if (stepcount == 25) {
                ((Arena18) Dungeon.level).endWave();
            }

            for (Heap heap: Dungeon.level.heaps.valueList()){
                heap.sprite.link(heap);
                heap.sprite.update();
            }
            int[] ints2 = new int[]{
                    pos+6-Dungeon.level.width(),
                    pos+6,
                    pos+6+Dungeon.level.width(),
                    pos+6+Dungeon.level.width()+Dungeon.level.width(),
                    pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                    pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                    pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+ level.width(),

                    pos+7,
                    pos+7+Dungeon.level.width(),
                    pos+7+Dungeon.level.width()+Dungeon.level.width(),
                    pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                    pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                    pos+8+Dungeon.level.width(),
                    pos+8+Dungeon.level.width()+Dungeon.level.width(),
                    pos+8+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                    pos+9+Dungeon.level.width()+Dungeon.level.width(),
            };

            for (int cell : ints2){
                GameScene.updateMap(cell);
                //GameScene.resetMap(cell);
            }

            super.moveForward();
        }
    }


    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 600;
        if (mode == WndModes.Modes.CHALLENGE) goldAdd = 1800;
        Dungeon.gold += goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        //mobs are created by the GenDrill
    }

    public static class LineCannon extends TowerCannon1 {

        {
            state = HUNTING;
            alignment = Alignment.ENEMY;
            //no loot or exp
            maxLvl = 0;
        }

        @Override
        public boolean interact(Char c) {
            return false;
        }
    }
}
