/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
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

package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Bless;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Small;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.buffs.Stamina;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vulnerable;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Honeypot;
import com.towerpixel.towerpixeldungeon.items.food.MeatPie;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.food.SmallRation;
import com.towerpixel.towerpixeldungeon.items.keys.GoldenKey;
import com.towerpixel.towerpixeldungeon.items.keys.IronKey;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.towerpixel.towerpixeldungeon.items.spells.SummonElemental;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerDisintegration;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.rooms.special.MassGraveRoom;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Plant;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena16 extends Arena {

    {
        name = "City ruins";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 121;
        HEIGHT = 61;

        startGold = 2000;
        startLvl = 15;

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
        grassExpand();
        for (int m = 0; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.EMPTY_SP && Math.random()>0.995) {
                this.map[m]=Terrain.PEDESTAL;
                this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS),Random.element(candidates));
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
    protected void grassExpand() {
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
                group.add( new Smoke( i ) );
            }
        }
    }

    public static class Smoke extends Emitter {

        private int pos;

        public static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                SmokeParticle p = (SmokeParticle)emitter.recycle( SmokeParticle.class );
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
}