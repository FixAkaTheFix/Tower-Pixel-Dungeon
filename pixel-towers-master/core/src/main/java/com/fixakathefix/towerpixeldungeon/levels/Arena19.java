package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.armor.ClothArmor;
import com.fixakathefix.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.fixakathefix.towerpixeldungeon.items.bombs.Firebomb;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbImmortality;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.fixakathefix.towerpixeldungeon.items.rings.RingOfMight;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfDiscoveries;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfSirensSong;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemHealing;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemNecrotic;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerTotemShield;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Unstable;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Banhammer;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.DarksteelSaber;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.RedKnife;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.IronWhip;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.levels.traps.AlarmTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.BurningTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FrostTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.SummoningTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Firebloom;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
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

public class Arena19 extends ArenaCity {

    {
        name = "The treasury";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 61;
        HEIGHT = 61;

        startGold = 4000;
        startLvl = 20;
        arenaDepth = 19;
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
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave % 4){
            case 0: mob = new Warlock(); break;
            case 1: mob = new Monk(); break;
            case 2: mob = new Golem(); break;
            case 3: mob = new Ghoul(); break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave % 4){
            case 0: return wave * 3;
            case 1: return wave * 3;
            case 2: return wave;
            case 3: return (int) (wave * 1.5f);
        }
        return 1;
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

        dropMany(candidates,
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.SCROLL),
                new PotionOfLiquidFlame(),
                new PotionOfDragonsBreath(),
                new Firebomb(),
                new Firebloom.Seed(),
                new ScrollOfPrismaticImage(),
                new ScrollOfSirensSong(),
                new ScrollOfEnchantment(),
                new ScrollOfDiscoveries()
                );

        this.drop(TowerInfo.getTowerSpawner(slot1),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot1),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot2),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot2),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot3),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot4),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot4),Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot3),Random.element(candidates));



        ChaliceOfBlood chalice = new ChaliceOfBlood();
        chalice.upgrade(10);
        this.drop(chalice, 25 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        ThrowingStone rok = new ThrowingStone();
        rok.upgrade(16);
        this.drop(rok, 26 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        SpawnerTotemShield tot = new SpawnerTotemShield();
        this.drop(tot, 25 + WIDTH*26).type = Heap.Type.LOCKED_CHEST;

        Banhammer banhammer = new Banhammer();
        banhammer.upgrade(3);
        this.drop(banhammer, 34 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        AbImmortality immortality = new AbImmortality();
        this.drop(immortality, 35 + WIDTH*25).type = Heap.Type.LOCKED_CHEST;

        ClothArmor armor = new ClothArmor();
        armor.upgrade(12);
        this.drop(armor, 35 + WIDTH*26).type = Heap.Type.LOCKED_CHEST;

        IronWhip infipike = new IronWhip();
        infipike.upgrade(10);
        infipike.enchant(new Unstable());
        this.drop(infipike, 34 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        SpawnerTotemNecrotic nectot = new SpawnerTotemNecrotic();
        this.drop(nectot, 35 + WIDTH*35).type = Heap.Type.LOCKED_CHEST;

        DarksteelSaber knife = new DarksteelSaber();
        knife.upgrade(3);
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
}