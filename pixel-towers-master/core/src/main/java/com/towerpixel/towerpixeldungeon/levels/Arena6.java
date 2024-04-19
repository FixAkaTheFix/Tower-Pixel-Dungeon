package com.towerpixel.towerpixeldungeon.levels;


import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoldenMimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Piranha;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.Shopkeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.food.FrozenCarpaccio;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.keys.IronKey;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfStamina;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
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
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndBag;
import com.towerpixel.towerpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena6 extends Arena{

    {
        name = "Prison";

        color1 = 0x6a723d;
        color2 = 0x88924c;
        viewDistance = 13;
        WIDTH = 101;
        HEIGHT = 101;

        towerShopKeeper = new TowerShopKeeperKeytrader();
        normalShopKeeper = new NormalShopKeeperVertical();

        maxWaves = 20;

        amuletCell = 10 + WIDTH*50;
        exitCell = amuletCell;
        towerShopKeeperCell = 4+WIDTH*43;
        normalShopKeeperCell = 1 + 50*WIDTH;
        waveCooldownNormal = 20;
        waveCooldownBoss = 200;
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

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,101,101, Terrain.WALL);
        Painter.fill(this, 1,1,99,99, Terrain.EMPTY);

        Painter.fill(this,2,48,1,5,Terrain.EMPTY_SP);
        Painter.fill(this,1,53,2,1,Terrain.BOOKSHELF);

        //warden's room
        Painter.fill(this,1,42,7,6,Terrain.WALL);
        Painter.fill(this,2,43,5,4,Terrain.EMPTY_SP);
        Painter.fill(this,2,44,5,1,Terrain.PEDESTAL);
        this.map[4+WIDTH*47] = Terrain.DOOR;

        //canteen
        Painter.fill(this,80,1,20,4,Terrain.WALL);
        Painter.fill(this,81,2,18,2,Terrain.EMPTY_SP);
        this.drop(new FrozenCarpaccio(), 82+WIDTH*3);
        this.drop(new FrozenCarpaccio(), 85+WIDTH*2);
        this.drop(new FrozenCarpaccio(), 87+WIDTH*3);
        this.map[95+WIDTH*4] = Terrain.DOOR;

        Painter.fill(this,81,6,19,1,Terrain.PEDESTAL);

        //bathroom near canteen
        Painter.fill(this,73,2,7,8,Terrain.EMPTY_SP);
        Painter.fill(this,80,2,1,8,Terrain.WALL);
        Painter.fill(this,73,2,7,1,Terrain.WELL);
        Painter.fill(this,73,10,6,1,Terrain.WALL);


        //some fillers

        Painter.fill(this,74,50,20,40,Terrain.GRASS);
        Painter.fill(this,76,54,17,30,Terrain.HIGH_GRASS);
        Painter.fill(this,80,59,11,8,Terrain.EMPTY_SP);




        //prison cells
        for (int y = 1; y <= 93;y+=7) {
            if (y>40 && y<50) y+= 15;
            for (int x = 1; x <= 67; ) {
                Painter.fill(this, x, y, 7, 7, Terrain.WALL);
                Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY_SP);
                int type = Random.Int(20);
                ArrayList<Integer> candidates = new ArrayList<>();
                for (int xt = x + 1; xt < x + 6; xt++)
                    for (int yt = y + 1; yt < y + 6; yt++) candidates.add(xt + WIDTH * yt);
                int exitpos;
                if (x % 2 == 1) exitpos = x + 6 + WIDTH * (y + 3);
                else exitpos = x + WIDTH * (y + 3);
                switch (type) {
                    //normal prison cell types:
                    case 1:
                    default: {//empty cell
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
                    case 5: {//overgrown cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.GRASS);
                        //for (int i = 0;i<2;i++) this.plant(randomSeed(), Random.element(candidates));
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
                    case 7: {//fully locked cell
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
                    case 13:
                    case 14: {//chest cell
                        Painter.fill(this, x + 1, y + 1, 5, 5, Terrain.EMPTY);
                        this.drop(Generator.random(Generator.Category.GOLD), Random.element(candidates)).type = Heap.Type.CHEST;
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
                    case 17:
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

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.WATER;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;

            }
            //water
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.WATER;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.WATER;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.WATER;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.WATER;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //burning traps
            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) {
                    this.map[cell] = Terrain.SECRET_TRAP;
                    this.setTrap(new ConfusionTrap().hide(), cell);
                }
            }
            //chill traps
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) {
                    this.map[cell] = Terrain.SECRET_TRAP;
                    this.setTrap(new ChillingTrap().hide(), cell);
                }
            }
            //toxic gas traps
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) {
                    this.map[cell] = Terrain.SECRET_TRAP;
                    this.setTrap(new ToxicTrap().hide(), cell);
                }
            }
            //alert traps
            if (Math.random()>0.95) {
                if (this.map[cell]==Terrain.EMPTY){
                    this.map[cell]=Terrain.SECRET_TRAP;
                    this.setTrap(new AlarmTrap().hide(), cell);
                }
            }
            //torches
            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.WALL) this.map[cell]=Terrain.WALL_DECO;
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
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));
        this.drop(new IronKey(6),Random.element(candidates));


        for (int m = 61*20; m<WIDTH*HEIGHT- WIDTH;m++){
            if (this.map[m]==Terrain.WATER) {
                Piranha piranha = new Piranha();
                piranha.pos = m;
                GameScene.add(piranha);
                piranha.state = piranha.SLEEPING;
                m+=WIDTH*10+Random.Int(1,36);
            }
        }

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 50;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    public void initNpcs() {
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell+WIDTH;
        GameScene.add(tower);
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
                return super.tileDesc( tile );
        }
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

            add( new Halo( 12, 0xFFFFCC, 0.4f ).point( p.x, p.y + 1 ) );
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
        public  ArrayList<Item> generateItems() {
            ArrayList<Item> itemsToSpawn = new ArrayList<>();
            if (Dungeon.isChallenged(Challenges.BOMBARDA_MAXIMA)) {
                if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(Generator.random(Generator.Category.BOMB));
                    itemsToSpawn.add( new IronKey(6));
                    itemsToSpawn.add( new IronKey(6));
                } else {
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerCannon());
                    itemsToSpawn.add(new SpawnerWall());
                    itemsToSpawn.add( new IronKey(6));
                    itemsToSpawn.add( new IronKey(6));
                }
            } else {
                if (Dungeon.isChallenged(Challenges.HEROIC_BATTLE)) {
                    itemsToSpawn.add(new ScrollOfUpgrade());
                    itemsToSpawn.add(new PotionOfStrength());
                    itemsToSpawn.add(new PotionOfStamina());
                    itemsToSpawn.add( new IronKey(6));
                    itemsToSpawn.add( new IronKey(6));
                } else {
                    itemsToSpawn.add(Generator.random(Generator.Category.TOWERP2));
                    itemsToSpawn.add(Generator.random(Generator.Category.TOWERP2));
                    itemsToSpawn.add(Generator.random(Generator.Category.TOWER));
                    itemsToSpawn.add( new IronKey(6));
                    itemsToSpawn.add( new IronKey(6));
                }
            }
            return itemsToSpawn;
        }
        public WndBag sell() {
            return GameScene.selectItem( itemSelector );
        }
        private WndBag.ItemSelector itemSelector = new WndBag.ItemSelector() {
            @Override
            public String textPrompt() {
                return Messages.get(Shopkeeper.class, "sell");
            }

            @Override
            public boolean itemSelectable(Item item) {
                return Shopkeeper.canSell(item);
            }

            @Override
            public void onSelect( Item item ) {
                if (item != null) {
                    WndBag parentWnd = sell();
                    GameScene.show( new WndTradeItem( item, parentWnd ) );
                }
            }
        };
        @Override
        public boolean interact(Char c) {
            if (c != Dungeon.hero) {
                return true;
            }
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    sell();
                }
            });
            return true;
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
        towerShopKeeper = new TowerShopKeeperKeytrader();
        towerShopKeeper.pos = bundle.getInt(TOWERSHOPKEEPERPOS);
        GameScene.add(towerShopKeeper);

    }



}
