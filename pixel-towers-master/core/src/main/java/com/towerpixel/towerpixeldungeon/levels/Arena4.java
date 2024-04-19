package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Piranha;
import com.towerpixel.towerpixeldungeon.actors.mobs.RotLasher;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.effects.Ripple;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.WandmakerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena4 extends Arena {

    {
        name = "Crab lake";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 10;
        WIDTH = 61;
        HEIGHT = 61;

        startGold = 600;
        startLvl = 3;

        maxWaves = 15;

        towerShopKeeper = new TowerShopKeeperMagic();

        amuletCell = 31 + WIDTH * 4;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 3 * WIDTH - 3;
        normalShopKeeperCell = amuletCell - 3 * WIDTH + 2;

        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }
    protected void lakeExpand() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 2; x<WIDTH-2;x++) for (int y = 2; y<HEIGHT-2;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.WATER) candidates.add(cell);
        }
        int[] cells;
        if (Math.random()>0.5) cells = PathFinder.NEIGHBOURS8; else cells = PathFinder.NEIGHBOURS4;
        for (int cellchosen : candidates) for (int i : cells) if (Math.random()>0.4f) this.map[cellchosen+i] = Terrain.WATER;
    }
    protected void createIslands(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 2; x<WIDTH-2;x++) for (int y = 2; y<HEIGHT-2;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.WATER) candidates.add(cell);
        }
        for (int cellchosen : candidates) if (Math.random()>0.98f) for (int i : PathFinder.NEIGHBOURS8) if (Math.random()>0.7f) this.map[cellchosen+i] = Terrain.HIGH_GRASS;
    }

    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this,0,0,61,61, Terrain.WALL);
        Painter.fillEllipse(this,1,1,59,59,Terrain.EMPTY);

        //lake
        this.map[30+30*WIDTH]=Terrain.WATER;
        for (int times = 0;times < 26; times++) try {
            lakeExpand();
        } catch (ArrayIndexOutOfBoundsException ignored) {};
        createIslands();

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //tall grass
            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.HIGH_GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.HIGH_GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.HIGH_GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.HIGH_GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.HIGH_GRASS;
            }

        }

        Painter.fillEllipse(this, 24, 1,15,9,Terrain.EMPTY);
        Painter.fill(this, 25, 1,11,1,Terrain.EMPTY_SP);
        Painter.fill(this, 25, 2,11,1,Terrain.PEDESTAL);
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
            if (this.passable[m]&&this.map[m]!=Terrain.WATER&&this.map[m]!=Terrain.EMPTY&&this.map[m]!=Terrain.PEDESTAL) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new SpawnerCannon(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.WATER) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));


        for (int m = 61*15; m<WIDTH*HEIGHT- WIDTH;m++){
            if (this.map[m]==Terrain.GRASS) {
                RotLasher rotLasher = new RotLasher();
                rotLasher.pos = m;
                GameScene.add(rotLasher);
                m+=2*WIDTH+Random.Int(2,10);
            }
        }
        for (int m = 61*20; m<WIDTH*HEIGHT- WIDTH;m++){
            if (this.map[m]==Terrain.WATER) {
                Piranha piranha = new Piranha();
                piranha.pos = m;
                GameScene.add(piranha);
                piranha.state = piranha.SLEEPING;
                m+=WIDTH*6+Random.Int(1,36);
            }
        }

        TowerCrossbow1 tower1 = new TowerCrossbow1();
        tower1.pos = amuletCell+1;
        GameScene.add(tower1);

        TowerCrossbow1 tower2 = new TowerCrossbow1();
        tower2.pos = amuletCell-1;
        GameScene.add(tower2);

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 70;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.DOWN, 1);
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
    public Group addVisuals() {
        super.addVisuals();
        addSewerVisuals(this, visuals);
        return visuals;
    }

    public static void addSewerVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Arena4.Sink( i ) );
            }
        }
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                Arena4.WaterParticle p = (Arena4.WaterParticle)emitter.recycle( Arena4.WaterParticle.class );
                p.reset( x, y );
            }
        };

        public Sink( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 2, p.y + 3, 4, 0 );

            pour( factory, 0.1f );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if (!isFrozen() && (rippleDelay -= Game.elapsed) <= 0) {
                    Ripple ripple = GameScene.ripple( pos + Dungeon.level.width() );
                    if (ripple != null) {
                        ripple.y -= DungeonTilemap.SIZE / 2;
                        rippleDelay = Random.Float(0.4f, 0.6f);
                    }
                }
            }
        }
    }

    public static final class WaterParticle extends PixelParticle {

        public WaterParticle() {
            super();

            acc.y = 50;
            am = 0.5f;

            color( ColorMath.random( 0xb6ccc2, 0x3b6653 ) );
            size( 2 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            speed.set( Random.Float( -2, +2 ), 0 );

            left = lifespan = 0.4f;
        }
    }

    public class TowerShopKeeperMagic extends TowerShopKeeper {

        {
            spriteClass = WandmakerSprite.class;

            properties.add(Property.IMMOVABLE);
        }

        @Override
        public  ArrayList<Item> generateItems() {
            ArrayList<Item> itemsToSpawn = new ArrayList<>();
            if (Dungeon.isChallenged(Challenges.BOMBARDA_MAXIMA)) {
                if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                } else {
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerWall());
                }
            } else {
                if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.WAND));
                } else {
                    itemsToSpawn.add(Generator.random(Generator.Category.TOWER));
                    itemsToSpawn.add(new SpawnerWand());
                    itemsToSpawn.add(Generator.random(Generator.Category.TOWER));
                }
            }
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
        bundle.put(SHOPKEEPER,normalShopKeeper);
        bundle.put(TOWERSHOPKEEPERPOS,towerShopKeeper.pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wave = bundle.getInt(WAVE);
        normalShopKeeper = (NormalShopKeeper) bundle.get(SHOPKEEPER);
        towerShopKeeper = new Arena4.TowerShopKeeperMagic();
        towerShopKeeper.pos = bundle.getInt(TOWERSHOPKEEPERPOS);
        GameScene.add(towerShopKeeper);

    }
}
