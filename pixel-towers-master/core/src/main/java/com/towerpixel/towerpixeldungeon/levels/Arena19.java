package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.armor.ClothArmor;
import com.towerpixel.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.towerpixel.towerpixeldungeon.items.bombs.ArcaneBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Firebomb;
import com.towerpixel.towerpixeldungeon.items.herospells.MageImmortality;
import com.towerpixel.towerpixeldungeon.items.keys.GoldenKey;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.towerpixel.towerpixeldungeon.items.rings.RingOfMight;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfDread;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfForesight;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.towerpixel.towerpixeldungeon.items.spells.SummonElemental;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemHealing;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfDisintegration;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Unstable;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Banhammer;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Infipike;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.RedKnife;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Whip;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.features.Maze;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.traps.AlarmTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.BurningTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.FrostTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.SummoningTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.Trap;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Firebloom;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.WandmakerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena19 extends Arena {

    {
        name = "The treasury";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 61;
        HEIGHT = 61;

        startGold = 4000;
        startLvl = 20;

        maxWaves = 15;

        amuletCell = 30 + WIDTH*30;
        exitCell = amuletCell;
        towerShopKeeper = null;
        normalShopKeeper = null;

        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.METROPOLIS},
                new float[]{1},
                false);
    }
    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 500;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
        if (wave == 7||wave == 9){
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int m = 0; m<WIDTH*HEIGHT;m++){
                if (this.map[m]==Terrain.WATER) candidates.add(m);
            }
            for (int i = 0; i<10;i++){

            }
        }
    }

    @Override
    protected boolean build() {

        //base level
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 61, 61, Terrain.WALL);
        Painter.fill(this, 1, 1, 59, 59, Terrain.EMPTY);
        //treasury itself
        Painter.fill(this, 0, 0, 28, 28, Terrain.WALL);

        Painter.fill(this, 0, 33, 28, 28, Terrain.WALL);

        Painter.fill(this, 33, 0, 28, 28, Terrain.WALL);

        Painter.fill(this, 33, 33, 28, 28, Terrain.WALL);
        Painter.fill(this,29, 1, 3, 59, Terrain.EMPTY_SP);
        Painter.fill(this,1, 29, 59, 3, Terrain.EMPTY_SP);



        buildRandomRoom(17,0, 3);
        buildRandomRoom(17,10, 3);
        buildRandomRoom(17,41, 3);
        buildRandomRoom(17,50, 3);

        buildRandomRoom(33,0, 1);
        buildRandomRoom(33,10, 1);
        buildRandomRoom(33,41, 1);
        buildRandomRoom(33,50, 1);

        buildRandomRoom(0, 17,2);
        buildRandomRoom(10,17, 2);
        buildRandomRoom(40,17, 2);
        buildRandomRoom(50,17, 2);

        buildRandomRoom(0, 33,0);
        buildRandomRoom(10,33, 0);
        buildRandomRoom(40,33, 0);
        buildRandomRoom(50,33, 0);

        Painter.fill(this,25, 25, 11, 11, Terrain.EMPTY_SP);
        Painter.fill(this,28, 28, 5, 5, Terrain.EMPTY);

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.EMPTY_SP;

        return true;
    }
    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.PEDESTAL) candidates.add(m);
        }
        for (int i : candidates){
            this.drop(new GoldenKey(19),i);
        }
        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if ((this.map[m]==Terrain.EMPTY || this.map[m]==Terrain.EMPTY_SP) && m != amuletCell) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot1),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot1),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot2),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot2),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot3),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot4),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot4),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot3),Random.element(candidates));

        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfDragonsBreath(),Random.element(candidates));
        this.drop(new Firebomb(),Random.element(candidates));
        this.drop(new Firebloom.Seed(),Random.element(candidates));
        this.drop(new ScrollOfPrismaticImage(),Random.element(candidates));
        this.drop(new ScrollOfSirensSong(),Random.element(candidates));
        this.drop(new ScrollOfEnchantment(),Random.element(candidates));
        this.drop(new ScrollOfForesight(),Random.element(candidates));

        ChaliceOfBlood chalice = new ChaliceOfBlood();
        chalice.upgrade(10);
        this.drop(chalice, 25 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        ThrowingStone rok = new ThrowingStone();
        rok.upgrade(16);
        this.drop(rok, 26 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        SpawnerTotemShield tot = new SpawnerTotemShield();
        this.drop(tot, 25 + WIDTH*26).type = Heap.Type.LOCKED_CHEST;

        Banhammer banhammer = new Banhammer();
        this.drop(banhammer, 34 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        MageImmortality immortality = new MageImmortality();
        this.drop(immortality, 35 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        ClothArmor armor = new ClothArmor();
        armor.upgrade(12);
        this.drop(armor, 35 + WIDTH*26).type = Heap.Type.LOCKED_CHEST;

        Whip infipike = new Whip();
        infipike.upgrade(10);
        infipike.enchant(new Unstable());
        this.drop(infipike, 34 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        SpawnerTotemNecrotic nectot = new SpawnerTotemNecrotic();
        this.drop(nectot, 35 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        RedKnife knife = new RedKnife();
        knife.upgrade(10);
        this.drop(knife, 35 + WIDTH*34).type = Heap.Type.LOCKED_CHEST;

        WandOfMagicMissile wand = new WandOfMagicMissile();
        wand.upgrade(6);
        this.drop(wand, 25 + WIDTH*34).type = Heap.Type.LOCKED_CHEST;

        SpawnerTotemHealing ttt = new SpawnerTotemHealing();
        this.drop(ttt, 25 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        RingOfMight ring = new RingOfMight();
        ring.upgrade(8);
        this.drop(ring, 26 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        this.drop(TowerInfo.getTowerSpawner(slot1), amuletCell+1);
        this.drop(TowerInfo.getTowerSpawner(slot1), amuletCell+1);
        this.drop(TowerInfo.getTowerSpawner(slot2), amuletCell-1);
        this.drop(TowerInfo.getTowerSpawner(slot2), amuletCell-1);
        this.drop(TowerInfo.getTowerSpawner(slot3), amuletCell+WIDTH);
        this.drop(TowerInfo.getTowerSpawner(slot3), amuletCell+WIDTH);
        this.drop(TowerInfo.getTowerSpawner(slot4), amuletCell-WIDTH);
        this.drop(TowerInfo.getTowerSpawner(slot4), amuletCell-WIDTH);

        super.addDestinations();
    }

    private void buildRandomRoom (int x, int y, int door){
        Painter.fill(this, x, y, 11, 11, Terrain.WALL);
        int type = Random.Int(9);
        switch (type){
            //basic room
            case 0: default:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                Painter.fill(this, x+2, y+2, 7, 7, Terrain.EMPTY_SP);
                break;
            }
            //library, with a secret key
            case 1:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY_SP);
                Painter.fill(this, x+3, y+3, 5, 5, Terrain.BOOKSHELF);
                Painter.fill(this, x+4, y+4, 3, 3, Terrain.EMPTY_DECO);
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
            //alarm room
            case 2:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    int cell = xx + WIDTH*yy;
                    this.map[cell]=Terrain.SECRET_TRAP;
                    this.setTrap(new AlarmTrap().hide(), cell);
                }
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
            //chill room
            case 3:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    int cell = xx + WIDTH*yy;
                    this.map[cell]=Terrain.SECRET_TRAP;
                    this.setTrap(new FrostTrap().hide(), cell);
                }
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
            //Summon room
            case 4:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int i : PathFinder.NEIGHBOURS8){
                    this.map[x+5 + (y+5)*WIDTH + i]=Terrain.SECRET_TRAP;
                    this.setTrap(new SummoningTrap().hide(), x+5 + (y+5)*WIDTH + i);
                    Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                }
                break;
            }
            //barricaded room
            case 5:{
                Painter.fill(this, x+1, y+1,9,9,Terrain.BARRICADE);
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                            break;
            }
            //strange room 1
            case 6:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    Painter.fill(this, xx, yy, 1, 1, Random.oneOf(Terrain.DOOR, Terrain.DOOR, Terrain.CHASM,Terrain.LOCKED_DOOR));
                }
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
            //grass room
            case 7:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    Painter.fill(this, xx, yy, 1, 1, Random.oneOf(Terrain.HIGH_GRASS, Terrain.GRASS, Terrain.SECRET_TRAP,Terrain.GRASS));
                }
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    int cell = xx + WIDTH*yy;
                    if (this.map[cell]==Terrain.SECRET_TRAP) this.setTrap(new BurningTrap().hide(), cell);
                }
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
            //strange room 2
            case 8:{
                Painter.fill(this, x+1, y+1, 9, 9, Terrain.EMPTY);
                for (int xx = x+1;xx<x+10;xx++) for (int yy = y+1;yy<y+10;yy++){
                    Painter.fill(this, xx, yy, 1, 1, Random.oneOf(Terrain.STATUE_SP, Terrain.EMPTY_SP, Terrain.DOOR,Terrain.WATER));
                }
                Painter.fill(this, x+5, y+5, 1, 1, Terrain.PEDESTAL);
                break;
            }
        }
        switch (door){
            case 0:Painter.fill(this, x+5, y, 1, 1, Terrain.DOOR);break;
            case 1:Painter.fill(this, x, y+5, 1, 1, Terrain.DOOR);break;
            case 2:Painter.fill(this, x+5, y+10, 1, 1, Terrain.DOOR);break;
            case 3:Painter.fill(this, x+10, y+5, 1, 1, Terrain.DOOR);break;
        }

    }




    public void deployMobs(int wave) {
        switch (wave % 4){
            case 0: default:{
                deploymobs(wave, Direction.EXACTLYRIGHT, 1);
                break;
            }
            case 1:{
                deploymobs(wave, Direction.EXACTLYDOWN, 1);
                break;
            }
            case 2:{
                deploymobs(wave, Direction.EXACTLYUP, 1);
                break;
            }
            case 3:{
                deploymobs(wave, Direction.EXACTLYLEFT, 1);
                break;
            }
        }
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


}