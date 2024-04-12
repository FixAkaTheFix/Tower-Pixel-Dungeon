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

import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.levels.rooms.special.SacrificeRoom.prize;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.blobs.Blob;
import com.towerpixel.towerpixeldungeon.actors.blobs.SacrificialFire;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.food.Berry;
import com.towerpixel.towerpixeldungeon.items.potions.AlchemicalCatalyst;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHaste;
import com.towerpixel.towerpixeldungeon.items.potions.brews.InfernalBrew;
import com.towerpixel.towerpixeldungeon.items.potions.brews.ShockingBrew;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.towerpixel.towerpixeldungeon.items.spells.ArcaneCatalyst;
import com.towerpixel.towerpixeldungeon.items.spells.CurseInfusion;
import com.towerpixel.towerpixeldungeon.items.spells.MagicalInfusion;
import com.towerpixel.towerpixeldungeon.items.spells.SummonElemental;
import com.towerpixel.towerpixeldungeon.items.spells.WildEnergy;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.rooms.special.SentryRoom;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Plant;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.WandmakerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena0 extends Arena {

    {
        name = "Testing pathway";

        color1 = 0x801500;
        color2 = 0xa68521;
        viewDistance = 25;
        WIDTH = 101;
        HEIGHT = 71;

        startGold = 3000;
        startLvl = 15;

        maxWaves = 20;

        towerShopKeeper = new TowerShopKeeperVerticalWizard();
        normalShopKeeper = new NormalShopKeeperVertical();

        amuletCell = 7 + WIDTH*35;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell-4-3*WIDTH;;
        normalShopKeeperCell = amuletCell-4+3*WIDTH;

        waveCooldownNormal = 5;
        waveCooldownBoss = 250;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.SEWERS_1},
                new float[]{1},
                false);
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 101, 61, Terrain.WALL);

        Painter.fill(this, 3, 3, 97, 57, Terrain.EMPTY);

        for (int y = 6; y < 71; y += Random.Int(13, 16))
            for (int x = 3; x < 70; x += Random.Int(3,5))
                if (!(x<25&&y>15&&y<45))try {
                    y += Random.Int(-1, 1);
                    int type = Random.Int(15);
                    switch (type) {
                        case 1: {//spire?
                            int height = Random.Int(7, 11);
                            int width = Random.Int(7, 11);
                            Painter.fillDiamond(this, x, y, width, height, Terrain.WALL);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 4, height - 4, Terrain.EMPTY_SP);
                            Painter.fill(this, x + width/2-Random.Int(1), y , 1, height, Terrain.EMPTY_SP);
                            this.map[x+width/2+WIDTH*(y+height/2)] = Terrain.PEDESTAL;
                            x+=width;
                            y += Random.Int(-1,1);

                            break;
                        }
                        case 13: {//spire?
                            int height = Random.Int(7, 11);
                            int width = Random.Int(7, 11);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.WALL);
                            Painter.fillEllipse(this, x + 2, y + 2, width - 4, height - 4, Terrain.EMPTY_SP);
                            Painter.fill(this, x + width/2-Random.Int(1), y , 1, height, Terrain.EMPTY_SP);
                            this.map[x+width/2+WIDTH*(y+height/2)] = Terrain.PEDESTAL;
                            x+=width;
                            y += Random.Int(-1,1);

                            break;
                        }
                        case 2: {//Column
                            int height = Random.Int(3, 5);
                            int width = height;
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.WALL);
                            x+=width;
                            break;
                        }
                        case 3: {//
                            int height = Random.Int(3, 5);
                            int width = Random.Int(3, 5);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.WALL);
                            Painter.fillEllipse(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fillEllipse(this, x + 2, y + 2, width - 4, height - 4, Terrain.EMPTY_DECO);
                            x+=width;
                            break;
                        }
                        case 4: {//well
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.WALL);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 3, y + 3, width - 4, height - 4, Terrain.WELL);
                            x+=width;
                            break;
                        }
                        case 5: {//grasspot
                            int height = Random.Int(6, 8);
                            int width = Random.Int(6, 8);
                            Painter.fillDiamond(this, x+1, y+1, width-2, height-2, Terrain.WALL);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 4, height - 4, Terrain.HIGH_GRASS);
                            x+=width;
                            break;
                        }
                        case 6: {//small grasspot
                            int height = Random.Int(3, 5);
                            int width = Random.Int(3, 5);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.STATUE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                        case 7:  {//water vault
                            int height = Random.Int(9, 11);
                            int width = Random.Int(9, 11);
                            Painter.fill(this, x, y + height/2-Random.Int(1), width, 1, Terrain.DOOR);
                            Painter.fill(this, x + width/2-Random.Int(1), y , 1, height, Terrain.DOOR);
                            Painter.fill(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.EMPTY_SP);
                            x+=width;
                            break;
                        }
                        case 8:  {//library
                            int height = Random.Int(7, 11);
                            int width = Random.Int(7, 11);
                            Painter.fill(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            Painter.fill(this, x + width/2-Random.Int(1), y , 1, height, Terrain.EMPTY_SP);
                            Painter.fill(this, x + 3, y + 3, width - 6, height - 6, Terrain.BOOKSHELF);
                            x+=width;
                            break;
                        }
                        case 9:  {//sac room
                            int height = Random.Int(7, 11);
                            int width = Random.Int(7, 11);
                            Painter.fill(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            Painter.fill(this, x + width/2-Random.Int(1), y , 1, height, Terrain.EMPTY_SP);
                            this.map[x+width/2 +(y + height/2)*WIDTH] = Terrain.EMPTY_DECO;
                            x+=width;
                            break;
                        }
                        case 10:  {//bone
                            int height = Random.Int(7, 11);
                            int width = Random.Int(7, 11);
                            Painter.fill(this, x, y, width, height, Terrain.WALL);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            break;
                        }
                        case 11:  {//statues
                            int height = Random.Int(7, 8);
                            int width = Random.Int(7, 8);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.EMPTY);
                            Painter.fillEllipse(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);;
                            Painter.fillDiamond(this, x + 3, y + 3, width - 6, height - 6, Terrain.STATUE_SP);
                            x+=width;
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;
            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY&&Math.random()>0.8) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY&&Math.random()>0.8) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY&&Math.random()>0.8) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY&&Math.random()>0.8) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY&&Math.random()>0.8) this.map[cell-WIDTH]=Terrain.GRASS;
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
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));
        this.drop(new Berry(),Random.element(candidates));

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 100;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        deploymobs(8055, Direction.RIGHT, 3);
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RIGHT, 1);
    }


    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
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

    public class NormalShopKeeperVertical extends NormalShopKeeper {

        @Override
        public void placeItems(){

            ArrayList<Item> itemsToSpawn = generateItems();

            int b = -Math.round(itemsToSpawn.size()*0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop( item, pos + 1 + WIDTH*b ).type = Heap.Type.FOR_SALE;//places stuff before the shopkeeper
                CellEmitter.center(pos + 1 + WIDTH*b).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        }

        @Override
        public ArrayList<Item> generateItems() {
            int type = Random.Int(3);
            ArrayList<Item> itemsToSpawn = new ArrayList<>();
            switch (type) {
                case 0: {
                    itemsToSpawn.add(new ElixirOfHoneyedHealing());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.RING).upgrade(Random.IntRange(0, 1)).identify());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.ARMOR).identify().upgrade(2));
                    break;
                }
                case 1: {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SCROLL));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.POTION));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WEAPON).identify().upgrade(2));
                    break;
                }
                case 2: {
                    itemsToSpawn.add(new SummonElemental());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.SCROLL));
                    itemsToSpawn.add(new SpawnerTotemNecrotic());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.FOOD));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WAND).upgrade(Random.IntRange(1, 3)).identify());
                    break;
                }
                case 3: {
                    itemsToSpawn.add(new WandOfWarding().upgrade(2).identify());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.STONE));
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.WAND).upgrade(Random.IntRange(1, 2)).identify());
                    itemsToSpawn.add(new SpawnerTotemShield());
                    itemsToSpawn.add(Generator.randomUsingDefaults(Generator.Category.POTION));
                    break;
                }
            }
            return itemsToSpawn;
        }


    }
    public class TowerShopKeeperVerticalWizard extends TowerShopKeeper {

        {
            spriteClass = WandmakerSprite.class;
        }

        @Override
        public void placeItems(){

            ArrayList<Item> itemsToSpawn = generateItems();

            int b = -Math.round(itemsToSpawn.size()*0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop( item, pos + 1 + WIDTH*b ).type = Heap.Type.FOR_SALE;//places before under the shopkeeper
                CellEmitter.center(pos + 1 + WIDTH*b).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        }

        @Override
        public  ArrayList<Item> generateItems() {
            ArrayList<Item> itemsToSpawn = new ArrayList<>();
            itemsToSpawn.add(new SpawnerWall());
            itemsToSpawn.add(new SpawnerWand());
            itemsToSpawn.add(new SpawnerGrave());
            itemsToSpawn.add(new SpawnerWand());
            itemsToSpawn.add(new SpawnerWall());
            return itemsToSpawn;
        }
    }


    private String WAVE = "wave";
    private String SHOPKEEPER = "shopkeeper";
    private String TOWERSHOPKEEPERPOS = "towershopkeeperpos";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(WAVE, wave);
        bundle.put(SHOPKEEPER,normalShopKeeper.pos);
        bundle.put(TOWERSHOPKEEPERPOS,towerShopKeeper.pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wave = bundle.getInt(WAVE);
        normalShopKeeper = new NormalShopKeeperVertical();
        normalShopKeeper.pos = bundle.getInt(SHOPKEEPER);
        GameScene.add(normalShopKeeper);
        towerShopKeeper = new TowerShopKeeperVerticalWizard();
        towerShopKeeper.pos = bundle.getInt(TOWERSHOPKEEPERPOS);
        GameScene.add(towerShopKeeper);

    }
}