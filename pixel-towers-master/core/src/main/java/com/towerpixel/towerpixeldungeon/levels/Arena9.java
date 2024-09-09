package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave2;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.NecromancerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena9 extends Arena{

    {
        name = "Bandit camp/Grave testing polygon";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 15;
        WIDTH = 50;
        HEIGHT = 50;
        startLvl = 8;
        startGold = 1000;
        waveCooldownNormal = 5;
        waveCooldownBoss = 50;

        maxWaves=10;

        towerShopKeeper = new TowerShopKeeperNecro();


        amuletCell = 25 + WIDTH*6;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell-2-5*WIDTH;;
        normalShopKeeperCell = amuletCell+2-5*WIDTH;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.GOBLIN_GROTTO},
                new float[]{1},
                false);
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
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(PrisonLevel.class, "water_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(PrisonLevel.class, "empty_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(PrisonLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    protected boolean build() {

        setSize(WIDTH, HEIGHT);
        //base room
        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);
        Painter.fill(this, 1, 1, 48, 48, Terrain.EMPTY);

        for (int x = 3;x<WIDTH-10;x+=Random.Int(7, 14)) for (int y = 14;y<WIDTH-10;y+=Random.Int(7, 15)) if (Math.random()>0.1){
          Painter.fillEllipse(this, x,y,Random.Int(6,7), Random.Int(6,7), Terrain.BARRICADE);
          Painter.fill(this, x+1,y+1,Random.Int(4,5), 4, Terrain.EMPTY_SP);
        }

        for (int x = 1;x<WIDTH-10;x+=Random.Int(2, 10)) for (int y = 14;y<WIDTH-10;y+=Random.Int(2, 10)) if (Math.random()>0.99){
            Painter.fillDiamond(this, x,y,Random.Int(4,5), Random.Int(4,5), Terrain.STATUE);
            Painter.fill(this, x+1,y+1,Random.Int(2,4), Random.Int(2,3), Terrain.EMPTY);
        }

        for (int x = 1;x<WIDTH-1;x++) for (int y = 4;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //tall grass
            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.HIGH_GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.HIGH_GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.HIGH_GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.HIGH_GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.HIGH_GRASS;
            }
            //some puddles
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.EMPTY_DECO;
            }
            //water
            if (Math.random()>0.98) {
                for (int i:PathFinder.NEIGHBOURS8) if (this.map[cell+i]==Terrain.EMPTY) this.map[cell+i]=Terrain.WATER;
            }
            //rare statues
            if (Math.random()>0.996) {
                if (this.map[cell]==Terrain.EMPTY && (cell>WIDTH*39)) this.map[cell]=Terrain.STATUE;
            }
            if (Math.random()>0.95) {
                if (this.map[cell]==Terrain.EMPTY && (cell>WIDTH*39)) this.map[cell]=Terrain.WALL;
            }
        }








        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void initNpcs() {
        super.initNpcs();

        TowerGrave1 tower = new TowerGrave1();
        tower.pos = amuletCell+1;
        GameScene.add(tower);

        TowerGrave1 tower2 = new TowerGrave1();
        tower2.pos = amuletCell-1;
        GameScene.add(tower2);

    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T5),Random.element(candidates));
        candidates.clear();

        super.addDestinations();
    }
    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
    }


    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        super.deploymobs(wave, Direction.DOWN, 1);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 100;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addPrisonVisuals(this, visuals);
        return visuals;
    }

    public static void addPrisonVisuals(Level level, Group group){
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new PrisonLevel.Torch( i ) );
            }
        }
    }

    public static class Torch extends Emitter {

        private int pos;

        public Torch( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 1, p.y + 2, 2, 0 );

            pour( FlameParticle.FACTORY, 0.15f );

            add( new Halo( 12, 0xFFDDDD, 0.4f ).point( p.x, p.y + 1 ) );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }

    class TowerShopKeeperNecro extends TowerShopKeeper{

        {
            spriteClass = NecromancerSprite.class;
        }

        @Override
        public  ArrayList<Item> generateItems() {
            ArrayList<Item> itemsToSpawn = new ArrayList<>();
            if (mode == WndModes.Modes.CHALLENGE) {
                itemsToSpawn.add(new SpawnerGrave());
                itemsToSpawn.add(new SpawnerGrave());
                itemsToSpawn.add(new SpawnerGrave());
                itemsToSpawn.add(new SpawnerGrave());
            } else {
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
                itemsToSpawn.add(new SpawnerGrave());
                itemsToSpawn.add(new SpawnerGrave());
            }
            return itemsToSpawn;
        }


    }

    private String TOWERSHOPKEEPERPOS = "towershopkeeperpos";


    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(TOWERSHOPKEEPERPOS,towerShopKeeper.pos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        towerShopKeeper = new Arena9.TowerShopKeeperNecro();
        towerShopKeeper.pos = bundle.getInt(TOWERSHOPKEEPERPOS);
        GameScene.add(towerShopKeeper);

    }





}
