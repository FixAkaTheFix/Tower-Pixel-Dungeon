package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.spells.SummonElemental;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.WandmakerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena19 extends Arena {

    {
        name = "The treasury";

        color1 = 0x801500;
        color2 = 0xa68521;
        viewDistance = 25;
        WIDTH = 101;
        HEIGHT = 101;

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
                new String[]{Assets.Music.SEWERS_BOSS},
                new float[]{1},
                false);
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 101, 101, Terrain.WALL);
        Painter.fill(this, 1, 1, 99, 99, Terrain.EMPTY);


        //again all in the add destinations



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
        for (int x = 1; x < 100;x+=44) for (int y = 1; y < 100;y+=80) {
            int i = Random.Int(5);
            switch (i){
                case 0:{
                    Painter.fill(this, x, y, 10, 10, Terrain.EMPTY);
                }
            }



        }







        super.addDestinations();
    }


    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RANDOM, 1);
    }


    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_MAGES;
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