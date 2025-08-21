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

package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Small;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vulnerable;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.items.food.SmallRation;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.fixakathefix.towerpixeldungeon.items.spells.SummonElemental;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerDisintegration;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.levels.rooms.special.MassGraveRoom;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Plant;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena16 extends ArenaCity {

    {
        name = "City ruins";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 121;
        HEIGHT = 61;

        startGold = 2500;
        startLvl = 15;
        arenaDepth = 16;
        maxWaves = 15;

        amuletCell = 61 + WIDTH * 5;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 4 * WIDTH - 3;
        normalShopKeeperCell = amuletCell - 4 * WIDTH + 3;

        waveCooldownNormal = 5;
        waveCooldownBoss = 300;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = Random.oneOf(new Skeleton()); break;
            case 2:
                mob = Random.oneOf(new Monk()); break;
            case 3:
                mob = Random.oneOf(new Bandit()); break;
            case 4:
                mob = Random.oneOf(new Warlock()); break;
            case 5: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Golem();
                } else mob = Random.oneOf(new DM100());
                break;
            }
            case 6:
                mob = Random.oneOf(new Monk()); break;
            case 7:
                mob = new DM100(); break;
            case 8:
                mob = Random.oneOf(new SkeletonArmored()); break;
            case 9:
                mob = Random.oneOf(new Elemental.FrostElemental()); break;
            case 10: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new SkeletonArmored();
                } else mob = Random.oneOf(new Skeleton());
                break;
            }
            case 11:
                mob = Random.oneOf(new Monk(), new Warlock()); break;
            case 12:
                mob = Random.oneOf(new Monk()); break;
            case 13:
                mob = Random.oneOf(new DM100()); break;
            case 14:
                mob = Random.oneOf(new Golem()); break;
            case 15: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Senior();
                } else mob = new Monk();
                break;
            }
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
                return 20;
            case 2:
                return 6;
            case 3:
                return 25;
            case 4:
                return 7;
            case 5:
                return 20;
            case 6:
                return 13;
            case 7:
                return 40;
            case 8:
                return 10;
            case 9:
                return 25;
            case 10:
                return 100;
            case 11:
                return 30;
            case 12:
                return 40;
            case 13:
                return 100;
            case 14:
                return 23;
            case 15:
                return 50;
        }
        return 1;
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 121, 61, Terrain.WALL);

        Painter.fill(this, 3, 3, 117, 57, Terrain.EMPTY);

        Painter.fill(this, 1, 0, 54, 7, Terrain.WALL);
        Painter.fill(this, 68, 0, 54, 7, Terrain.WALL);

        this.map[amuletCell+1] = Terrain.STATUE;
        this.map[amuletCell-1] = Terrain.STATUE;
        this.map[amuletCell+6] = Terrain.STATUE;
        this.map[amuletCell-6] = Terrain.STATUE;
        this.map[amuletCell+6+2*WIDTH] = Terrain.STATUE;
        this.map[amuletCell-6+2*WIDTH] = Terrain.STATUE;
        this.map[amuletCell+6+4*WIDTH] = Terrain.STATUE;
        this.map[amuletCell-6+4*WIDTH] = Terrain.STATUE;

        Painter.fill(this, 55, 2, 13, 1, Terrain.PEDESTAL);
        Painter.fill(this, 55, 1, 13, 1, Terrain.EMPTY);

        //random buildings
        for (int y = 10; y < 46; y += 7)
            for (int x = 9; x < 47; x += Random.Int(1,3))
                try {
                    int type = Random.Int(18);
                    switch (type) {
                        case 1: case 2: case 3: case 4: {//broken house
                            int height = Random.Int(7, 9);
                            int width = Random.Int(7, 9);
                            for (int hnum = 0; hnum<height;hnum++) for (int wnum = 0; wnum<width;wnum++){
                                if (Math.random()>0.3) Painter.fill(this, x+wnum, y+hnum, 1, 1, Terrain.WALL);
                            }
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            int orientation = Random.Int(4);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.EMPTY_SP;
                            } else if (orientation == 2) {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.EMPTY_SP;
                            } else if (orientation == 3) {//vertical door orientation
                                this.map[x + Random.Int(1, width - 2) + WIDTH * y] = Terrain.DOOR;
                                this.map[x + Random.Int(1, width - 2) + WIDTH * (y + height - 1)] = Terrain.DOOR;
                            } else {//horizontal double pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.EMPTY_SP;
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.EMPTY_SP;
                            }
                            x+=Random.Int(7, 11);
                            y++;
                            break;
                        }
                        case 5: {//grasspot
                            int height = Random.Int(6, 8);
                            int width = Random.Int(6, 8);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.HIGH_GRASS);
                            x+=width;
                            break;
                        }
                        case 6: {//small grasspot
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                        case 7:  {//water vault
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.EMPTY_SP);
                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 8:  {//library
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.BOOKSHELF);

                            MassGraveRoom.Bones b = new MassGraveRoom.Bones();

                            b.setRect(x+4,y+4, width-8, height-8);
                            this.customTiles.add(b);

                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 9:  {//poolroom
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 11:case 12:case 13:case 14: {//a plant of a seedpod
                            int height = 3;
                            int width = 3;
                            Painter.fillEllipse(this, x+1, y+1, width, height, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
        //random buildings
        for (int y = 10; y < 43; y += 7)
            for (int x = 67; x < 105; x += Random.Int(1,3))
                try {
                    int type = Random.Int(18);
                    switch (type) {
                        case 1: case 2: case 3: case 4: {//broken house
                            int height = Random.Int(7, 9);
                            int width = Random.Int(7, 9);
                            for (int hnum = 0; hnum<height;hnum++) for (int wnum = 0; wnum<width;wnum++){
                                if (Math.random()>0.3) Painter.fill(this, x+wnum, y+hnum, 1, 1, Terrain.WALL);
                            }
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            int orientation = Random.Int(4);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.EMPTY_SP;
                            } else if (orientation == 2) {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.EMPTY_SP;
                            } else if (orientation == 3) {//vertical door orientation
                                this.map[x + Random.Int(1, width - 2) + WIDTH * y] = Terrain.DOOR;
                                this.map[x + Random.Int(1, width - 2) + WIDTH * (y + height - 1)] = Terrain.DOOR;
                            } else {//horizontal double pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.EMPTY_SP;
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.EMPTY_SP;
                            }
                            x+=Random.Int(7, 11);
                            y++;
                            break;
                        }
                        case 5: {//grasspot
                            int height = Random.Int(6, 8);
                            int width = Random.Int(6, 8);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.HIGH_GRASS);
                            x+=width;
                            break;
                        }
                        case 6: {//small grasspot
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                        case 7:  {//water vault
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.EMPTY_SP);
                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 8:  {//library
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.BOOKSHELF);

                            MassGraveRoom.Bones b = new MassGraveRoom.Bones();

                            b.setRect(x+4,y+4, width-8, height-8);
                            this.customTiles.add(b);

                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 9:  {//poolroom
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            int orientation = Random.Int(2);
                            if (orientation == 1) {//horizontal right pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.LOCKED_DOOR;
                            } else  {//horizontal left pass orientation
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.LOCKED_DOOR;
                            }
                            x+=width;
                            break;
                        }
                        case 11:case 12:case 13:case 14: {//a plant of a seedpod
                            int height = 3;
                            int width = 3;
                            Painter.fillEllipse(this, x+1, y+1, width, height, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            try {
                int spcount = 0;
                if (this.map[i] == Terrain.DOOR)
                    for (int cell : PathFinder.NEIGHBOURS8) {//same for doors || to delete doors spawning inside buildings
                        if (this.map[i + cell] == Terrain.EMPTY_SP) spcount++;
                    }
                if (spcount >= 4)
                    this.map[i] = Terrain.EMPTY_SP;//more strict because the doors are on the inside;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            try {
                if (this.map[i] == Terrain.WATER)
                    for (int cell : PathFinder.NEIGHBOURS8) {//for laggy water not to appear
                        if (this.map[i + cell] == Terrain.BARRICADE) this.map[i] = Terrain.EMPTY_SP;
                    }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }

        }



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }


        this.drop(new PotionOfHealing(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new PotionOfToxicGas(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new ScrollOfUpgrade(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new MeatPie(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new MysteryMeat(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));

        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));
        this.drop(new IronKey(16),Random.element(candidates));

        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));
        this.drop(new GoldenKey(16),Random.element(candidates));

        this.drop(new ScrollOfPrismaticImage(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new ElixirOfIcyTouch(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new ElixirOfHoneyedHealing(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new SummonElemental(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new SpawnerDisintegration(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new SpawnerDisintegration(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new SpawnerTotemNecrotic(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;

        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL2),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL2),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.SCROLL2),Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        grassExpand();
        for (int m = 0; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.EMPTY_SP && Math.random()>0.995) {
                this.map[m]=Terrain.PEDESTAL;
                this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
            }
        }
        for (int m = 0; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.WALL && Math.random()>0.95) this.map[m]=Terrain.WALL_DECO;
        }
        for (int x = 50; x< 70;x++) for (int y = 4; y<HEIGHT-3;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.GRASS && Math.random()>0.1) this.map[cell] = Terrain.EMPTY;
            if (this.map[cell] == Terrain.EMPTY && Math.random()>0.7) this.map[cell] = Terrain.EMPTY_DECO;
        }
        for (int m = 0; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.GRASS && Math.random()>0.7) this.map[m] = Terrain.HIGH_GRASS;
            if (this.map[m]==Terrain.GRASS && Math.random()>0.93) {
                Dungeon.level.plant( (Plant.Seed) Generator.random(Generator.Category.SEED), m );
            }
        }
        super.addDestinations();
    }
    private void grassExpand() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 2; x<WIDTH-2;x++) for (int y = 2; y<HEIGHT-2;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.GRASS) candidates.add(cell);
        }
        int[] cells;
        if (Math.random()>0.5) cells = PathFinder.NEIGHBOURS8; else cells = PathFinder.NEIGHBOURS4;
        for (int cellchosen : candidates) for (int i : cells) if (Math.random()>0.4f&& this.passable[cellchosen+i]) this.map[cellchosen+i] = Terrain.GRASS;
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 1000;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        if (wave == 4){
            EnemyPortal.createEnemyPortal(amuletCell + WIDTH*30, 25);
        }
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.DOWN, 1);
    }

    @Override
    public void affectMob(Mob mob) {
        Buff.affect(mob, Small.class);
        Buff.affect(mob, Bless.class, 100000);
        Buff.affect(mob, Vulnerable.class, 100000);
    }


}