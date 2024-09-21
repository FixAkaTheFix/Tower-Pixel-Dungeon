package com.towerpixel.towerpixeldungeon.levels;


import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoldenMimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Piranha;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuardPaladin;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.food.FrozenCarpaccio;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.keys.IronKey;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.traps.AlarmTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ChillingTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ConfusionTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.ToxicTrap;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.GuardSprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard3UpgradedSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena6 extends Arena {

    {
        name = "Prison";

        color1 = 0x6a723d;
        color2 = 0x88924c;
        viewDistance = 13;
        WIDTH = 101;
        HEIGHT = 101;

        towerShopKeeper = new TowerShopKeeperKeytrader();
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        maxWaves = 20;

        startGold = 500;

        amuletCell = 10 + WIDTH * 50;
        exitCell = amuletCell;
        towerShopKeeperCell = 4 + WIDTH * 43;
        normalShopKeeperCell = 1 + 50 * WIDTH;
        waveCooldownNormal = 5;
        waveCooldownBoss = 200;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
    }

    ArrayList<Integer> guardposes = new ArrayList<>();


    @Override
    protected boolean build() {

        setSize(WIDTH, HEIGHT);
        //base room
        Painter.fill(this, 0, 0, 101, 101, Terrain.WALL);
        Painter.fill(this, 1, 1, 99, 99, Terrain.EMPTY);

        Painter.fill(this, 2, 48, 1, 5, Terrain.EMPTY_SP);
        Painter.fill(this, 1, 53, 2, 1, Terrain.BOOKSHELF);

        //warden's room
        Painter.fill(this, 1, 42, 7, 6, Terrain.WALL);
        Painter.fill(this, 2, 43, 5, 4, Terrain.EMPTY_SP);
        Painter.fill(this, 2, 44, 5, 1, Terrain.PEDESTAL);
        this.map[4 + WIDTH * 47] = Terrain.DOOR;

        //canteen
        Painter.fill(this, 80, 1, 20, 4, Terrain.WALL);
        Painter.fill(this, 81, 2, 18, 2, Terrain.EMPTY_SP);
        this.drop(new FrozenCarpaccio(), 82 + WIDTH * 3);
        this.drop(new FrozenCarpaccio(), 85 + WIDTH * 2);
        this.drop(new FrozenCarpaccio(), 87 + WIDTH * 3);
        this.map[95 + WIDTH * 4] = Terrain.DOOR;

        Painter.fill(this, 81, 6, 19, 1, Terrain.PEDESTAL);

        //bathroom near canteen
        Painter.fill(this, 73, 2, 7, 8, Terrain.EMPTY_SP);
        Painter.fill(this, 80, 2, 1, 8, Terrain.WALL);
        Painter.fill(this, 73, 2, 7, 1, Terrain.WELL);
        Painter.fill(this, 73, 10, 6, 1, Terrain.WALL);


        //some fillers

        Painter.fill(this, 74, 50, 20, 40, Terrain.GRASS);
        Painter.fill(this, 76, 54, 17, 30, Terrain.HIGH_GRASS);
        Painter.fill(this, 80, 59, 11, 8, Terrain.EMPTY_SP);


        //prison cells
        for (int y = 1; y <= 93; y += 7) {
            if (y > 40 && y < 50) y += 15;
            for (int x = 1; x <= 67; ) {
                Painter.fill(this, x, y, 7, 7, Terrain.WALL);
                Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY_SP);
                int type = Random.Int(25);
                ArrayList<Integer> candidates = new ArrayList<>();
                for (int xt = x + 1; xt < x + 6; xt++)
                    for (int yt = y + 1; yt < y + 6; yt++) candidates.add(xt + WIDTH * yt);
                int exitpos;
                if (x % 2 == 1) exitpos = x + 6 + WIDTH * (y + 3);
                else exitpos = x + WIDTH * (y + 3);
                switch (type) {
                    //normal prison cell types:
                    default:
                    case 1: {//empty cell
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 2: {//pedestal cell
                        this.map[Random.element(candidates)] = Terrain.PEDESTAL;
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 3: {//alchemy cell
                        this.map[Random.element(candidates)] = Terrain.ALCHEMY;
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 4: {//cool cell
                        this.map[Random.element(candidates)] = Terrain.STATUE_SP;
                        this.map[Random.element(candidates)] = Terrain.WELL;
                        this.map[Random.element(candidates)] = Terrain.BOOKSHELF;
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 5: {//overgrown cell with a guard hiding
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.GRASS);
                        guardposes.add(x + 3 + WIDTH * (y + 3));
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 6: {//burned cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        for (int i = 0; i < 10; i++)
                            this.map[Random.element(candidates)] = Terrain.EMBERS;
                        this.map[exitpos] = Terrain.EMBERS;
                        break;
                    }
                    case 7: {//fully locked cell with a guard
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.WALL);
                        this.map[exitpos] = Terrain.EMPTY_SP;
                        this.map[exitpos - WIDTH] = Terrain.LOCKED_EXIT;
                        break;
                    }
                    case 8: {//flooded cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        for (int i = 0; i < 20; i++)
                            this.map[Random.element(candidates)] = Terrain.WATER;
                        this.map[exitpos] = Terrain.DOOR;
                        this.map[Random.element(candidates)] = Terrain.HIGH_GRASS;
                        break;
                    }
                    case 9: {//casualty cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.map[Random.element(candidates)] = Terrain.EMPTY_DECO;
                        this.map[Random.element(candidates)] = Terrain.EMPTY_DECO;
                        this.map[Random.element(candidates)] = Terrain.EMPTY_DECO;
                        this.drop(new MysteryMeat(), Random.element(candidates));
                        this.map[exitpos] = Terrain.DOOR;
                        break;
                    }
                    case 10: {//fake locked cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.map[Random.element(candidates)] = Terrain.EMPTY_WELL;
                        this.map[Random.element(candidates)] = Terrain.SIGN;
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    //special locked cells
                    case 11: {//mimic cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.mobs.add(Mimic.spawnAt(Random.element(candidates), Generator.random()));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 12: {//gmimic cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.mobs.add(GoldenMimic.spawnAt(Random.element(candidates), Generator.random(Generator.Category.ARTIFACTNOCHAINS)));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 13: {//chest cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.drop(Generator.random(Generator.Category.GOLD), Random.element(candidates)).type = Heap.Type.CHEST;
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 14: {//chest cell with a guard
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.drop(Generator.random(Generator.Category.GOLD), Random.element(candidates)).type = Heap.Type.CHEST;
                        guardposes.add(x + 3 + WIDTH * (y + 3));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 15: {//treasury cell
                        this.map[x + 3 + WIDTH * (y + 3)] = Terrain.STATUE_SP;
                        candidates.remove(12);
                        this.drop(Generator.random(), Random.element(candidates));
                        this.drop(Generator.random(), Random.element(candidates));
                        this.drop(Generator.random(), Random.element(candidates));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 16: {//flooded treasury cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.WATER);
                        this.drop(Generator.random(), Random.element(candidates));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 17: {//guard in a bloody room cell
                        this.map[Random.element(candidates)] = Terrain.EMPTY_DECO;
                        guardposes.add(x + 3 + WIDTH * (y + 3));
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                    case 18: {//bone heap cell
                        this.map[Random.element(candidates)] = Terrain.EMPTY_DECO;
                        this.drop(Generator.random(Generator.Category.GOLD), Random.element(candidates)).type = Heap.Type.SKELETON;
                        this.map[exitpos] = Terrain.LOCKED_DOOR;
                        break;
                    }
                }
                if (x % 2 == 1) {
                    x += 11;
                } else x += 7;
            }
        }

        for (int x = 1; x < WIDTH - 1; x++)
            for (int y = 1; y < HEIGHT - 1; y++) {
                //Random grass
                int cell = x + WIDTH * y;

                if (Math.random() > 0.98) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.GRASS;
                    if (this.map[cell + 1] == Terrain.EMPTY) this.map[cell + 1] = Terrain.WATER;
                    if (this.map[cell - 1] == Terrain.EMPTY) this.map[cell - 1] = Terrain.GRASS;
                    if (this.map[cell + WIDTH] == Terrain.EMPTY)
                        this.map[cell + WIDTH] = Terrain.GRASS;
                    if (this.map[cell - WIDTH] == Terrain.EMPTY)
                        this.map[cell - WIDTH] = Terrain.GRASS;

                }
                //water
                if (Math.random() > 0.98) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.WATER;
                    if (this.map[cell + 1] == Terrain.EMPTY) this.map[cell + 1] = Terrain.WATER;
                    if (this.map[cell - 1] == Terrain.EMPTY) this.map[cell - 1] = Terrain.WATER;
                    if (this.map[cell + WIDTH] == Terrain.EMPTY)
                        this.map[cell + WIDTH] = Terrain.WATER;
                    if (this.map[cell - WIDTH] == Terrain.EMPTY)
                        this.map[cell - WIDTH] = Terrain.GRASS;
                }
                //burning traps
                if (Math.random() > 0.99) {
                    if (this.map[cell] == Terrain.EMPTY) {
                        this.map[cell] = Terrain.SECRET_TRAP;
                        this.setTrap(new ConfusionTrap().hide(), cell);
                    }
                }
                //chill traps
                if (Math.random() > 0.98) {
                    if (this.map[cell] == Terrain.EMPTY) {
                        this.map[cell] = Terrain.SECRET_TRAP;
                        this.setTrap(new ChillingTrap().hide(), cell);
                    }
                }
                //toxic gas traps
                if (Math.random() > 0.98) {
                    if (this.map[cell] == Terrain.EMPTY) {
                        this.map[cell] = Terrain.SECRET_TRAP;
                        this.setTrap(new ToxicTrap().hide(), cell);
                    }
                }
                //alert traps
                if (Math.random() > 0.95) {
                    if (this.map[cell] == Terrain.EMPTY) {
                        this.map[cell] = Terrain.SECRET_TRAP;
                        this.setTrap(new AlarmTrap().hide(), cell);
                    }
                }
                //torches
                if (Math.random() > 0.9) {
                    if (this.map[cell] == Terrain.WALL) this.map[cell] = Terrain.WALL_DECO;
                }

            }


        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        super.deploymobs(wave, Direction.TOORIGHT, 1);
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.map[m] == Terrain.EMPTY_SP) candidates.add(m);
        }
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(new Gold(100), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5), Random.element(candidates));
        this.drop(new ThrowingStone(), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        this.drop(new IronKey(6), Random.element(candidates));
        if (mode == WndModes.Modes.CHALLENGE) {
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(new Gold(100), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.MIS_T4), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.MIS_T5), Random.element(candidates));
        }


        for (int m = 61 * 20; m < WIDTH * HEIGHT - WIDTH; m++) {
            if (this.map[m] == Terrain.WATER) {
                Piranha piranha = new Piranha();
                piranha.pos = m;
                GameScene.add(piranha);
                piranha.state = piranha.SLEEPING;
                m += WIDTH * 10 + Random.Int(1, 36);
            }
        }

        super.addDestinations();
    }

    private TowerGuard3 captain = new TowerGuard3();

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave == 1 && captain.isAlive()) {
            WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                    new String[]{
                            Messages.get(RatKing.class, "l6w1start1"),
                            Messages.get(RatKing.class, "l6w1start2"),
                            Messages.get(RatKing.class, "l6w1start3"),
                            Messages.get(RatKing.class, "l6w1start4"),
                            Messages.get(RatKing.class, "l6w1start5"),
                            Messages.get(RatKing.class, "l6w1start6"),
                            Messages.get(RatKing.class, "l6w1start7"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
        if (wave == 2 && captain.isAlive()) {
            WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                    new String[]{
                            Messages.get(RatKing.class, "l6w2start1"),
                            Messages.get(RatKing.class, "l6w2start2"),
                            Messages.get(RatKing.class, "l6w2start3"),
                            Messages.get(RatKing.class, "l6w2start4"),
                            Messages.get(RatKing.class, "l6w2start5"),
                            Messages.get(RatKing.class, "l6w2start6"),
                            Messages.get(RatKing.class, "l6w2start7"),
                            Messages.get(RatKing.class, "l6w2start8"),
                            Messages.get(RatKing.class, "l6w2start9")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
        if (wave == 8 && captain.isAlive()) {
            WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                    new String[]{
                            Messages.get(RatKing.class, "l6w8start1"),
                            Messages.get(RatKing.class, "l6w8start2"),
                            Messages.get(RatKing.class, "l6w8start3"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
        if (wave == 12 && captain.isAlive()) {
            WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                    new String[]{
                            Messages.get(RatKing.class, "l6w12start1"),
                            Messages.get(RatKing.class, "l6w12start2"),
                            Messages.get(RatKing.class, "l6w12start3"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
        if (wave == 16 && captain.isAlive()) {
            WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                    new String[]{
                            Messages.get(RatKing.class, "l6w16start1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }

    }

    @Override
    public void doStuffEndwave(int wave) {
        if (mode == WndModes.Modes.CHALLENGE) {
            int golddeplete = 77;
            Dungeon.gold -= golddeplete;
            Item.updateQuickslot();
            GLog.w(Messages.get(Arena.class, "golddepleteendwave", golddeplete));
        } else {
            int goldAdd = 50;
            Dungeon.gold += goldAdd;
            Item.updateQuickslot();
            GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        }
        if (wave == 20)
            if (captain.isAlive()) {
                WndDialogueWithPic.dialogue(new TowerGuard3Sprite(), "Prison guard captain",
                        new String[]{
                                Messages.get(RatKing.class, "l6w20end1"),
                                Messages.get(RatKing.class, "l6w20end2"),
                                Messages.get(RatKing.class, "l6w20end3"),
                                Messages.get(RatKing.class, "l6w20end4"),
                                Messages.get(RatKing.class, "l6w20end5"),
                                Messages.get(RatKing.class, "l6w20end6"),
                                Messages.get(RatKing.class, "l6w20end7"),
                                Messages.get(RatKing.class, "l6w20end8"),
                                Messages.get(RatKing.class, "l6w20end9")
                        },
                        new byte[]{
                                WndDialogueWithPic.IDLE
                        },
                        WndDialogueWithPic.WndType.FINAL);
            } else {
                WndDialogueWithPic.dialogue(new GuardSprite(), "Prison warden",
                        new String[]{
                                Messages.get(RatKing.class, "l6w20endbad1"),
                                Messages.get(RatKing.class, "l6w20endbad2"),
                                Messages.get(RatKing.class, "l6w20endbad3"),
                                Messages.get(RatKing.class, "l6w20endbad4"),
                                Messages.get(RatKing.class, "l6w20endbad5"),
                                Messages.get(RatKing.class, "l6w20endbad6"),
                                Messages.get(RatKing.class, "l6w20endbad7"),
                                Messages.get(RatKing.class, "l6w20endbad8"),
                                Messages.get(RatKing.class, "l6w20endbad9")
                        },
                        new byte[]{
                                WndDialogueWithPic.IDLE
                        },
                        WndDialogueWithPic.WndType.FINAL);
            }
        super.doStuffEndwave(wave);
    }


    @Override
    public void initNpcs() {

        for (int i : guardposes) {
            TowerGuard1 guard = new TowerGuard1();
            guard.sellable = false;
            guard.pos = i;
            GameScene.add(guard);
        }
        TowerGuard1 lefty = new TowerGuard1();
        lefty.sellable = false;
        lefty.pos = towerShopKeeperCell + WIDTH * 5 + 1;
        GameScene.add(lefty);

        TowerGuard1 righty = new TowerGuard1();
        righty.sellable = false;
        righty.pos = towerShopKeeperCell + WIDTH * 5 - 1;
        GameScene.add(righty);

        captain = new TowerGuard3();
        captain.sellable = false;
        captain.pos = amuletCell + WIDTH;
        GameScene.add(captain);
        super.initNpcs();
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RIGHT, 2);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_PRISON;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_PRISON;
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(PrisonLevel.class, "empty_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(PrisonLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc(tile);
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addPrisonVisuals(this, visuals);
        return visuals;
    }

    public static void addPrisonVisuals(Level level, Group group) {
        for (int i = 0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add(new PrisonLevel.Torch(i));
            }
        }
    }

    public static class Torch extends Emitter {

        private int pos;

        public Torch(int pos) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld(pos);
            pos(p.x - 1, p.y + 2, 2, 0);

            pour(FlameParticle.FACTORY, 0.15f);

            add(new Halo(12, 0xFFFFCC, 0.4f).point(p.x, p.y + 1));
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }

    public class TowerShopKeeperKeytrader extends TowerShopKeeper {

        {
            spriteClass = GuardSprite.class;

            properties.add(Property.IMMOVABLE);
        }

        @Override
        public ArrayList<Item> generateItems() {
            ArrayList<Item> itemsToSpawn = new ArrayList<>();


            itemsToSpawn.add(Random.oneOf(
                    TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot4)
            ));
            itemsToSpawn.add(Random.oneOf(
                    TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot4)
            ));
            itemsToSpawn.add(Random.oneOf(
                    TowerInfo.getTowerSpawner(Dungeon.level.slot1),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot2),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot3),
                    TowerInfo.getTowerSpawner(Dungeon.level.slot4)
            ));
            itemsToSpawn.add(new IronKey(6));
            itemsToSpawn.add(new IronKey(6));


            return itemsToSpawn;
        }

        @Override
        public boolean interact(Char c) {
            if (c != Dungeon.hero) {
                return true;
            }
            return true;
        }
    }

    private String WAVE = "wave";
    private String SHOPKEEPER = "shopkeeper";
    private String TOWERSHOPKEEPERPOS = "towershopkeeperpos";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(TOWERSHOPKEEPERPOS, towerShopKeeper.pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        towerShopKeeper = new TowerShopKeeperKeytrader();
        towerShopKeeper.pos = bundle.getInt(TOWERSHOPKEEPERPOS);
        GameScene.add(towerShopKeeper);

    }


}
