package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ArmoredBrute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollBlind;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollGuard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.effects.Ripple;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.food.Blandfruit;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.items.food.StewedMeat;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena3 extends Arena {

    /**
     * First real level.
     * Explains food and healing, potions due to loot
     * May have a cool interaction of fire burning all the village
     */

    {
        name = "Gnoll village";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 20;
        WIDTH = 51;
        HEIGHT = 81;

        startGold = 1500;
        startLvl = 3;
        arenaDepth = 3;
        maxWaves = 15;

        amuletCell = 27 + WIDTH * 5;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 4 * WIDTH - 3;
        normalShopKeeperCell = amuletCell - 4 * WIDTH + 3;

        waveCooldownNormal = 5;
        waveCooldownBoss = 300;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.GOBLIN_GROTTO},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = Random.oneOf(new GnollGuard()); break;
            case 2:
                mob = Random.oneOf(new GnollThrower(), new GnollBlind(), new Gnoll()); break;
            case 3:
                mob = new GnollBlind(); break;
            case 4:
                mob = new GnollThrower(); break;
            case 5:
                mob = new Gnoll(); break;
            case 6:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Brute();
                } else mob = new GnollThrower();
                break;
            case 7:
                mob = new Gnoll(); break;
            case 8:
                mob = Random.oneOf(new Snake(), new Crab()); break;
            case 9:
                mob = Random.oneOf(new GnollBlind(),new Gnoll(), new GnollThrower()); break;
            case 10:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GnollTrickster();
                } else mob = new Gnoll();
                break;
            case 11:
                mob = new GnollThrower(); break;
            case 12:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new ArmoredBrute();
                } else mob = new GnollTrickster();
                break;
            case 13:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Shaman.PurpleShaman();
                } else mob = new Shaman.BlueShaman();
                break;
            case 14:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Brute();
                } else mob = new GnollGuard();
                break;
            case 15: {
                mob = new Gnoll();
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
            case 1: return 1;
            case 2: return 7;
            case 3: return 10;
            case 4: return 6;
            case 5: return 13;
            case 6: return 17;
            case 7: return 25;
            case 8: return 24;
            case 9: return 36;
            case 10: return 39;
            case 11: return 42;
            case 12: return 13;
            case 13: return 6;
            case 14: return 10;
            case 15: return 50;
        } return 1;
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 51, 81, Terrain.WALL);

        Painter.fill(this, 3, 3, 47, 77, Terrain.EMPTY);
        Painter.fill(this, 22, 2, 11, 1, Terrain.PEDESTAL);
        Painter.fill(this, 22, 1, 11, 1, Terrain.EMPTY);
        Painter.fill(this, 26,5, 3, 1, Terrain.STATUE);
        this.map[amuletCell+8] = Terrain.BARRICADE;
        this.map[amuletCell-8] = Terrain.BARRICADE;
        this.map[amuletCell+8+2*WIDTH] = Terrain.BARRICADE;
        this.map[amuletCell-8+2*WIDTH] = Terrain.BARRICADE;
        this.map[amuletCell+8+4*WIDTH] = Terrain.BARRICADE;
        this.map[amuletCell-8+4*WIDTH] = Terrain.BARRICADE;
        //gnoll base
        Painter.fill(this, 10, 10, 41, 71, Terrain.EMPTY);


        //random buildings
        for (int y = 16; y < 70; y += Random.Int(8,9))//max height is 7
            for (int x = Random.Int(4, 7); x < 44; x += 2)//max width is 7
                try {
                    int type = Random.Int(18);
                    switch (type) {
                        case 8:  {//library
                            if (x < 36){
                                int height = 7;
                                int width = Random.Int(7, 13);
                                Painter.fill(this, x, y, width, height, Terrain.WALL);
                                Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                                Painter.fill(this, x + 2, y + 2, width - 4, height - 4, Terrain.BOOKSHELF);
                                int orientation = Random.Int(2);
                                if (orientation == 1) {//vertical right pass orientation
                                    this.map[x + width - 1 + WIDTH * (y + Random.NormalIntRange(1, height - 2))] = Terrain.LOCKED_DOOR;
                                } else  {//vertical left pass orientation
                                    this.map[x + WIDTH * (y + Random.NormalIntRange(1, height - 2))] = Terrain.LOCKED_DOOR;
                                }
                                x+=width;
                                break;
                            }//if there is not enough place creates a default house
                        }
                        case 1: default: {//house
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fill(this, x, y, width, height, Terrain.BARRICADE);
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
                            x+=width;
                            break;
                        }
                        case 3: {//tub?
                            int height = Random.Int(5, 6);
                            int width = Random.Int(5, 6);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fillEllipse(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fillEllipse(this, x + 2, y + 2, width - 4, height - 4, Terrain.EMPTY_DECO);
                            int orientation = Random.Int(2);
                            if (orientation == 1) {//vertical pass orientation
                                this.map[x + Random.Int(1, width - 2) + WIDTH * y] = Terrain.EMPTY;
                                this.map[x + Random.Int(1, width - 2) + WIDTH * (y + height - 1)] = Terrain.EMPTY;
                            } else {//horizontal double pass orientation
                                this.map[x + width - 1 + WIDTH * (y + Random.Int(1, height - 2))] = Terrain.EMPTY_SP;
                                this.map[x + WIDTH * (y + Random.Int(1, width - 2))] = Terrain.EMPTY_SP;
                            }
                            x+=width;
                            break;
                        }
                        case 4: {//well
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.EMPTY_SP);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fill(this, x + 3, y + 3, width - 4, height - 4, Terrain.WATER);
                            x+=width;
                            break;
                        }
                        case 5: {//grasspot
                            int height = Random.Int(5, 6);
                            int width = Random.Int(5, 6);
                            Painter.fillEllipse(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillEllipse(this, x + 2, y + 2, width - 2, height - 2, Terrain.HIGH_GRASS);
                            x+=width;
                            break;
                        }
                        case 6: {//small grasspot
                            int height = Random.Int(3, 4);
                            int width = Random.Int(3, 4);
                            Painter.fillDiamond(this, x+1, y+1, width, height, Terrain.BARRICADE);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 2, height - 2, Terrain.GRASS);
                            x+=width;
                            break;
                        }
                        case 7:  {//water vault
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fill(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
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

                        case 9:  {//pedestial
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.BARRICADE);
                            Painter.fillEllipse(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);
                            Painter.fillDiamond(this, x + 2, y + 2, width - 4, height - 4, Terrain.PEDESTAL);
                            x+=width;
                            break;
                        }
                        case 10:  {//fountainpipe?
                            int height = Random.Int(5, 7);
                            int width = Random.Int(5, 7);
                            Painter.fill(this, x, y, width, height, Terrain.EMPTY_SP);
                            Painter.fill(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY);
                            Painter.fillEllipse(this, x + 2, y + 2, width - 4, height - 4, Terrain.WATER);
                            x+=width;
                            break;
                        }
                        case 11:  {//statues
                            int height = Random.Int(5, 6);
                            int width = Random.Int(5, 6);
                            Painter.fillEllipse(this, x, y, width, height, Terrain.EMPTY);
                            Painter.fillEllipse(this, x + 1, y + 1, width - 2, height - 2, Terrain.EMPTY_SP);;
                            Painter.fillDiamond(this, x + 2, y + 2, width - 4, height - 4, Terrain.STATUE_SP);
                            x+=width;
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
        //conjoin buildings
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            try {
                int spcount = 0;
                if (this.map[i] == Terrain.BARRICADE) for (int cell : PathFinder.NEIGHBOURS8) {
                    if (this.map[i + cell] == Terrain.EMPTY_SP) spcount++;
                }
                if (spcount >= 6) this.map[i] = Terrain.EMPTY_SP;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            try {
                int spcount = 0;
                if (this.map[i] == Terrain.DOOR||this.map[i] == Terrain.LOCKED_DOOR)
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
        Painter.fill(this, 0, 0, 3, 81, Terrain.WALL);
        Painter.fill(this, 50, 0, 1, 81, Terrain.WALL);
        Painter.fill(this, 3, 77, 47, 4, Terrain.EMPTY);
        Painter.fill(this, 3, 79, 47, 2, Terrain.WALL);

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.95) {
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
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave == 1){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(BossRatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l3w1start1"),
                            Messages.get(RatKing.class, "l3w1start2"),
                            Messages.get(RatKing.class, "l3w1start3"),
                            Messages.get(RatKing.class, "l3w1start4"),
                            Messages.get(RatKing.class, "l3w1start5"),
                            Messages.get(RatKing.class, "l3w1start6")

                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                    });
        }
        if (wave == 5){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(BossRatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l3w5start1"),
                            Messages.get(RatKing.class, "l3w5start2"),
                            Messages.get(RatKing.class, "l3w5start3"),
                            Messages.get(RatKing.class, "l3w5start4")

                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                    });
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 120 + wave*10;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.TOODOWN, 1);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_PRISON;
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addSewerVisuals(this, visuals);
        return visuals;
    }

    public static void addSewerVisuals(Level level, Group group) {
        for (int i = 0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add(new Arena3.Sink(i));
            }
        }
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }
        for (int m = 0; m<WIDTH*HEIGHT;m++ ){
            if (this.map[m]==Terrain.EMPTY_SP && this.distance(amuletCell, m) > 40 && Math.random()>0.3) candidates.add(m);//places more than 40 tiles away have 70% more chance for loot to appear on them
        }
        for (int i = 0; i<10; i++){
            this.drop(new Bomb(),Random.element(candidates));
            this.drop(new StewedMeat(),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
            this.drop(new Honeypot(),Random.element(candidates));
            this.drop(new Honeypot(),Random.element(candidates));
            this.drop(Blandfruit.randomFruit(),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates)).type = Heap.Type.CHEST;
            this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates)).type = Heap.Type.CHEST;
            this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        }

        this.drop(new ElixirOfHoneyedHealing(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new ElixirOfHoneyedHealing(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));

        this.drop(new IronKey(3),Random.element(candidates));
        this.drop(new IronKey(3),Random.element(candidates));
        this.drop(new IronKey(3),Random.element(candidates));
        this.drop(new IronKey(3),Random.element(candidates));
        this.drop(new IronKey(3),Random.element(candidates));

        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));


        for (int m = WIDTH*20 + WIDTH/2; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.EMPTY_SP) {
                GnollGuard goll = new GnollGuard();
                goll.mapGuard = true;
                goll.pos = m;
                GameScene.add(goll);
                m+=(WIDTH+30);
            }
        }
        for (int m = WIDTH*50; m<WIDTH*HEIGHT- 7*WIDTH;m++){
            if (this.map[m]==Terrain.EMPTY_SP && Char.findChar(m)==null) {
                GnollGuard goll = new GnollGuard();
                goll.mapGuard = true;
                goll.pos = m;
                goll.state = goll.HUNTING;
                GameScene.add(goll);
                m+=(WIDTH+30);
            }
        }
        super.addDestinations();
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit(Emitter emitter, int index, float x, float y) {
                Arena3.WaterParticle p = (Arena3.WaterParticle) emitter.recycle(Arena3.WaterParticle.class);
                p.reset(x, y);
            }
        };

        public Sink(int pos) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld(pos);
            pos(p.x - 2, p.y + 3, 4, 0);

            pour(factory, 0.1f);
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if (!isFrozen() && (rippleDelay -= Game.elapsed) <= 0) {
                    Ripple ripple = GameScene.ripple(pos + Dungeon.level.width());
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

            color(ColorMath.random(0xb6ccc2, 0x3b6653));
            size(2);
        }

        public void reset(float x, float y) {
            revive();

            this.x = x;
            this.y = y;

            speed.set(Random.Float(-2, +2), 0);

            left = lifespan = 0.4f;
        }
    }

}
