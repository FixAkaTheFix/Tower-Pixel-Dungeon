package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTroll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goblin;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinFat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinGiant;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinNinja;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinSand;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.items.food.SmallRation;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.GoblinSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbow2Sprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena14 extends Arena{
    {

        name = "Goblin outpost";
        WIDTH = 100;
        HEIGHT = 70;
        color1 = 0x534f3e;
        color2 = 0xb9d661;
        viewDistance = 100;

        startGold = 6000;
        startLvl = 11;
        arenaDepth = 14;
        maxWaves = 15;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        amuletCell = 9 + WIDTH*35;
        exitCell = amuletCell;
        towerShopKeeperCell = 2 + 30*WIDTH;
        normalShopKeeperCell = 2 + 40*WIDTH;

        waveCooldownNormal = 5;
        waveCooldownBoss = 400;
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = Random.oneOf(new Goblin()); break;
            case 2:
                mob = Random.oneOf(new GoblinFat()); break;
            case 3:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GoblinShaman.ShamanShield();
                } else mob = Random.oneOf(new Goblin(), new GoblinFat());
                break;
            case 4:
                mob = Random.oneOf(new Goblin(),new GoblinFat(), new GoblinGiant(), new Goblin(),new Goblin()); break;
            case 5:
                mob = Random.oneOf(new GoblinGiant()); break;
            case 6:
                mob = Random.oneOf(new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new Goblin(), new GoblinFat(), new GoblinShaman.ShamanRegen()); break;
            case 7:
                mob = new GoblinNinja(); break;
            case 8:
                mob = Random.oneOf(new GoblinSand(), new Goblin()); break;
            case 9:
                mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanStrength()); break;
            case 10:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new BossTroll();
                } else mob = new GoblinGiant(); break;
            case 11:
                mob = new Goblin(); break;
            case 12:
                mob = Random.oneOf(new GoblinShaman.ShamanShield(), new Goblin(), new GoblinSand(),new GoblinFat()); break;
            case 13:
                mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanStrength()); break;
            case 14:
                mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanShield(),new GoblinShaman.ShamanStrength(),new GoblinShaman.ShamanRegen()); break;
            case 15: {
                mob = Random.oneOf(new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(),  new GoblinShaman.ShamanRegen());
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
        switch (wave){
            case 1: return 10;//gobs
            case 2: return 20;//gobs
            case 3: return 20;//gobs and shaman
            case 4: return 20;//gobs and gigas
            case 5: return 11;//gigas
            case 6: return 15;//shamans, gobs and gigas
            case 7: return 20;//ninja gobs
            case 8: return 41;//gobs and sand gobs
            case 9: return 20;//shamans and gigas
            case 10: return 15;//gigas and a troll
            case 11: return 70;//gobs
            case 12: return 75;//gobs and sand gobs
            case 13: return 40;//gigas
            case 14: return 50;//gigas and shamans
            case 15: return 7;//trolls
        }
        return 1;
    }

    public void deploymobs(int wave, Direction direction, int group) {

        if (wave == 10) super.deploymobs(wave, Direction.RIGHT, 6);
        else if (wave == 7) {
            super.deploymobs(wave, Direction.LEFT, 10);
            Sample.INSTANCE.play(Assets.Sounds.ALERT);
        } else super.deploymobs(wave, Direction.RIGHT, 1);
    }

    @Override
    protected boolean build() {


        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,100,70, Terrain.WALL);
        Painter.fill(this, 1,1,98,68, Terrain.EMPTY);

        Painter.fill(this, 0,0,32,20, Terrain.WALL);
        Painter.fill(this, 0,50,32,20, Terrain.WALL);

        Painter.fill(this, 6,32,7,7,Terrain.EMPTY_SP);
        Painter.fill(this, 7,33,5,5,Terrain.WATER);
        Painter.fill(this, 8,34,3,3,Terrain.EMPTY_SP);


        for (int y = 1; y<HEIGHT-1;y++){
            if (y % 2 == 0) Painter.fill(this, 30,y,3,1,Terrain.BARRICADE);
            else Painter.fill(this, 30, y,3,1,Terrain.WALL);
        }

        for (int x1 = 33; x1 < WIDTH - 7; x1++)
            for (int y1 = 1; y1 < HEIGHT - 7; y1++) {
                //columns
                if (Math.random()>0.99)Painter.fillEllipse(this,x1,y1,Random.Int(2,3), Random.Int(2,3), Terrain.WALL);
            }
        Painter.fill(this, 15,33,44,1,Terrain.EMPTY);
        Painter.fill(this, 13,34,87,3,Terrain.EMPTY_SP);
        Painter.fill(this, 15,37,44,1,Terrain.EMPTY);
        Painter.fillEllipse(this,59,31,9,9,Terrain.EMPTY_SP);
        Painter.fillEllipse(this,60,32,7,7,Terrain.WATER);

        Painter.fill(this, 23,23,36,1,Terrain.EMPTY);
        Painter.fill(this, 23,24,77,3,Terrain.EMPTY_SP);
        Painter.fill(this, 23,27,36,1,Terrain.EMPTY);

        Painter.fill(this, 23,43,36,1,Terrain.EMPTY);
        Painter.fill(this, 23,44,77,3,Terrain.EMPTY_SP);
        Painter.fill(this, 23,47,36,1,Terrain.EMPTY);


        //the paths
        int pathnum = 12;//VARIABLES YOU CAN CHANGE IF THE CAVES SEEM NOT BIG ENOUGH

        int xcan = Random.Int(33, WIDTH - 10);
        int ycan = Random.Int(5, HEIGHT - 5);

        int pathnummax = pathnum;

        int xcur;
        int ycur;
        xcur = Random.Int(34, WIDTH - 10);
        ycur = Random.Int(5, HEIGHT - 5);
        for (int i = 0; i < pathnummax; i++) {
            pathnum--;

            if (i<pathnummax/2){
                xcur = 33 ;
                ycur = 25;
                xcan = 36 + Random.Int(6, 55);
                ycan = Random.Int(6, 14);
            } else {
                xcur = 33;
                ycur = 45;
                xcan = 36 + Random.Int(6, 55);
                ycan = Random.Int(52, 60);
            }



            while (!(xcur == xcan && ycur == ycan)) {

                if (xcan > xcur && xcur < WIDTH - 7) xcur++;
                else if (xcan < xcur && xcur > 34) xcur--;
                else if (ycan < ycur && ycur > 5) ycur--;
                else if (ycan > ycur && ycur < HEIGHT - 5) ycur++;



                if (this.map[xcur+WIDTH*ycur]!=Terrain.BARRICADE && this.map[xcur-1+WIDTH*ycur]!=Terrain.BARRICADE && this.map[xcur+WIDTH*ycur-WIDTH]!=Terrain.BARRICADE && this.map[xcur+WIDTH*ycur-1-WIDTH]!=Terrain.BARRICADE)Painter.fill(this,xcur-1,ycur-1,2,2,Terrain.EMPTY_SP);
                if (Math.random()>0.3){
                    if (Math.random()>0.95)if (this.map[xcur+WIDTH*ycur+1]!=Terrain.EMPTY_SP && this.map[xcur+WIDTH*ycur+1]!=Terrain.BARRICADE) this.map[xcur+WIDTH*ycur+1] =Terrain.STATUE;
                    if (Math.random()>0.95)if (this.map[xcur+WIDTH*ycur-2]!=Terrain.EMPTY_SP && this.map[xcur+WIDTH*ycur-2]!=Terrain.BARRICADE) this.map[xcur+WIDTH*ycur-2] =Terrain.STATUE;
                    if (Math.random()>0.95)if (this.map[xcur+WIDTH*ycur+WIDTH+WIDTH]!=Terrain.EMPTY_SP && this.map[xcur+WIDTH*ycur+WIDTH+WIDTH]!=Terrain.BARRICADE) this.map[xcur+WIDTH*ycur+WIDTH+WIDTH] =Terrain.STATUE;
                    if (Math.random()>0.95)if (this.map[xcur+WIDTH*ycur-WIDTH-WIDTH]!=Terrain.EMPTY_SP && this.map[xcur+WIDTH*ycur-WIDTH-WIDTH]!=Terrain.BARRICADE) this.map[xcur+WIDTH*ycur-WIDTH-WIDTH] =Terrain.STATUE;
                }
            }
            int houseWidth = Random.IntRange(6,9);
            int houseHeight = Random.IntRange(6,9);

            Painter.fill(this, xcur-houseWidth/2,ycur-houseHeight/2,houseWidth,houseHeight,Terrain.BARRICADE);
            Painter.fill(this, xcur-houseWidth/2+1,ycur-houseHeight/2+1,houseWidth-2,houseHeight-2,Terrain.EMPTY_SP);
            if (Math.random()>0.8) Painter.fill(this, xcur-houseWidth/2+2,ycur-houseHeight/2+2,houseWidth-5,houseHeight-5,Terrain.WATER);
            if (ycur>30){
                Painter.fill(this, xcur-houseWidth/2+2,ycur-houseHeight/2,3,1,Terrain.EMPTY_SP);
            } else Painter.fill(this, xcur-houseWidth/2+2,ycur+houseHeight/2,3,1,Terrain.EMPTY_SP);
        }

        for (int x1 = 30; x1 < WIDTH - 1; x1++)
            for (int y1 = 1; y1 < HEIGHT - 1; y1++) {

                int cell = x1 + WIDTH * y1;
                //Random grass
                if (Math.random() > 0.93) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.GRASS;

                }
                //water
                if (Math.random() > 0.96) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.WATER;
                }
            }
        for (int x1 = 3; x1 < WIDTH - 1; x1++)
            for (int y1 = 1; y1 < HEIGHT - 1; y1++) {
                int cell = x1 + WIDTH * y1;
                //Random pebbles
                if (Math.random() > 0.9) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.EMPTY_DECO;

                }
            }

        /*for (int y1 = 3; y1 < HEIGHT - 8;y1+=8)
            for (int x1 = 33; x1 < WIDTH - 8;)
             {

                int houseWidth = Random.IntRange(4,6);
                int houseHeight = Random.IntRange(4,6);

                Painter.fillEllipse(this, x1,y1,houseWidth,houseHeight,Terrain.BARRICADE);
                Painter.fillEllipse(this, x1+1,y1+1,houseWidth-2,houseHeight-2,Terrain.EMPTY_SP);
                x1+= houseWidth+2+ Random.Int(4);
            }*/




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
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));

        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
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
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));

        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.EMPTY) candidates.add(m);
        }
        this.drop(new Honeypot(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new PotionOfHealing(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new MeatPie(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new MysteryMeat(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new Honeypot(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new PotionOfHealing(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new PotionOfToxicGas(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new ScrollOfUpgrade(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new MeatPie(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new MysteryMeat(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new SmallRation(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new SmallRation(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new SmallRation(),Random.element(candidates)).type= Heap.Type.REMAINS;
        this.drop(new SmallRation(),Random.element(candidates)).type= Heap.Type.REMAINS;


        for (int m = 0; m<WIDTH*HEIGHT- 5*WIDTH;m++){
            if (this.map[m]==Terrain.EMPTY_SP && m % WIDTH > 40) {
                GoblinCrossbow crossbow = new GoblinCrossbow();
                crossbow.pos = m;
                GameScene.add(crossbow);
                m+=Random.Int(10, WIDTH*4);
            }
        }
        super.addDestinations();
    }

    @Override
    public void initNpcs() {
        super.initNpcs();

    }

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave==1) {
            WndDialogueWithPic.dialogue(new GoblinSprite(), Messages.get(RatKing.class, "goblin"),
                    new String[]{
                            Messages.get(RatKing.class, "l14w1start1"),
                            Messages.get(RatKing.class, "l14w1start2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==10) {
            WndDialogueWithPic.dialogue(new GoblinSprite(), Messages.get(RatKing.class, "goblin"),
                    new String[]{
                            Messages.get(RatKing.class, "l14w10start1"),
                            Messages.get(RatKing.class, "l14w10start2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==15) {
            WndDialogueWithPic.dialogue(new GoblinSprite(), Messages.get(RatKing.class, "goblin"),
                    new String[]{
                            Messages.get(RatKing.class, "l14w15start1"),
                            Messages.get(RatKing.class, "l14w15start2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 700;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CAVES_BOSS},
                new float[]{1},
                false);
    }


    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return Messages.get(CavesLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_name");
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "water_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return Messages.get(CavesLevel.class, "entrance_desc");
            case Terrain.EXIT:
                return Messages.get(CavesLevel.class, "exit_desc");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_desc");
            case Terrain.WALL_DECO:
                return Messages.get(CavesLevel.class, "wall_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(CavesLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addCavesVisuals( this, visuals );
        return visuals;
    }

    public static void addCavesVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Arena11.Vein( i ) );
            }
        }
    }

    private String WAVE = "wave";
    private String SHOPKEEPER = "shopkeeper";
    private String TOWERSHOPKEEPERPOS = "towershopkeeperpos";

    public static class Vein extends Group {

        private int pos;

        private float delay;

        public Vein( int pos ) {
            super();

            this.pos = pos;

            delay = Random.Float( 2 );
        }

        @Override
        public void update() {

            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if ((delay -= Game.elapsed) <= 0) {

                    //pickaxe can remove the ore, should remove the sparkling too.
                    if (Dungeon.level.map[pos] != Terrain.WALL_DECO){
                        kill();
                        return;
                    }

                    delay = Random.Float();

                    PointF p = DungeonTilemap.tileToWorld( pos );
                    ((Arena11.Sparkle)recycle( Arena11.Sparkle.class )).reset(
                            p.x + Random.Float( DungeonTilemap.SIZE ),
                            p.y + Random.Float( DungeonTilemap.SIZE ) );
                }
            }
        }
    }

    public static final class Sparkle extends PixelParticle {

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan = 0.5f;
        }

        @Override
        public void update() {
            super.update();

            float p = left / lifespan;
            size( (am = p < 0.5f ? p * 2 : (1 - p) * 2) * 2 );
        }
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_GOBS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }

    public static class GoblinCrossbow extends TowerCrossbow1 {

        {
            spriteClass = TowerCrossbow2Sprite.class;
            state = HUNTING;
            alignment = Alignment.ENEMY;

            //no loot or exp
            maxLvl = 0;

            damageMin = 3;

            //a bit more tough than those of a gnoll village
            HT = HP = 35;
        }

        @Override
        public CharSprite sprite() {
            CharSprite sprite1 = super.sprite();
            sprite1.rm = sprite1.bm = 0.7f;
            return sprite1;
        }

        @Override
        public void updateSpriteState() {
            super.updateSpriteState();
        }


        @Override
        public float spawningWeight() {
            return 0;
        }

        @Override
        public boolean interact(Char c) {
            return false;
        }
    }

}
