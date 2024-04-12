package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goblin;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.quest.Pickaxe;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena12 extends Arena{

    {

        name = "Cave lakes";
        WIDTH = 100;
        HEIGHT = 100;
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        startGold = 2000;
        startLvl = 11;

        maxWaves = 10;

        amuletCell = WIDTH/2 +WIDTH*HEIGHT/2;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 10*WIDTH - 3;
        normalShopKeeperCell = amuletCell - 10*WIDTH + 2;

        waveCooldownNormal = 5;
        waveCooldownBoss = 50;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CAVES_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        if (chooseMob(wave) instanceof Goblin) {
            super.deploymobs(wave, Direction.DOWN, 1);
        }
        super.deploymobs(wave, Direction.RANDOM, 1);
    }

    @Override
    protected boolean build() {
        boolean checkUp = true;
        do try {
            setSize(WIDTH, HEIGHT);

            Painter.fill(this, 0, 0, WIDTH, HEIGHT, Terrain.WALL);
            Painter.fill(this, 4, HEIGHT / 2, WIDTH - 8, 4, Terrain.WATER);
            Painter.fill(this, WIDTH / 2, 4, 4, HEIGHT - 8, Terrain.WATER);
            Painter.fillDiamond(this, 9, 9, WIDTH - 18, HEIGHT - 18, Terrain.WATER);
            Painter.fillDiamond(this, 10, 10, WIDTH - 20, HEIGHT - 20, Terrain.WALL);

            ArrayList<Integer> empty = new ArrayList<>();
            for (int m = 5; m < WIDTH - 5; m++)
                for (int n = 5; n < HEIGHT - 5; n++) {
                    if (this.map[m + WIDTH * n] == Terrain.WATER) empty.add(m + WIDTH * n);
                }


            int x = WIDTH / 2;
            int y = HEIGHT / 2;
            ArrayList<Integer> candidatesforspawn = new ArrayList<>();
            for (int m = 10; m < WIDTH - 10; m++)
                for (int n = 10; n < HEIGHT - 10; n++) {
                    if (((m < 11) || (m > WIDTH - 11)) || ((n < 11) || (n > HEIGHT - 11)))
                        candidatesforspawn.add(m + WIDTH * n);
                }

            int pathnum = 10;//VARIABLES YOU CAN CHANGE IF THE CAVES SEEM NOT BIG ENOUGH
            int connectionpathsnum = 2;


            int pathnummax = pathnum;

            for (int i = 0; i < pathnummax; i++) {
                int candidate = candidatesforspawn.get(Random.Int(candidatesforspawn.size() - 1));
                int xcan = candidate % WIDTH;
                int ycan = candidate / WIDTH;
                pathnum--;
                if (pathnum <= connectionpathsnum) {
                    x = Random.Int(10, WIDTH - 10);
                    y = Random.Int(10, HEIGHT - 10);
                    xcan = Random.Int(10, WIDTH - 10);
                    ycan = Random.Int(10, HEIGHT - 10);
                }
                int xcur = x;
                int ycur = y;

                while (!(xcur == xcan && ycur == ycan)) {
                    int ran = Random.Int(6);
                    switch (ran) {
                        case 0:
                        case 1: {
                            if (xcan < xcur && xcur > 9) {
                                xcur--;
                            }
                            if (xcan > xcur && xcur < WIDTH - 9) xcur++;
                            if (ycan < ycur && ycur > 9) ycur--;
                            if (ycan > ycur && ycur < HEIGHT - 9) ycur++;
                            break;
                        }
                        case 3:
                            if (xcur < WIDTH - 9) xcur++;
                            break;
                        case 4:
                            if (xcur > 9) xcur--;
                            break;
                        case 5:
                            if (ycur < HEIGHT - 9) ycur++;
                            break;
                        case 6:
                            if (ycur > 9) ycur--;
                            break;

                    }
                    this.map[xcur + WIDTH * ycur] = Terrain.WATER;
                }
            }
            ArrayList<Integer> emptyTiles = new ArrayList<>();
            for (int m = 5; m < WIDTH - 5; m++)
                for (int n = 5; n < HEIGHT - 5; n++) {
                    if (this.map[m + WIDTH * n] == Terrain.WATER) emptyTiles.add(m + WIDTH * n);
                }
            for (int tile : emptyTiles) {
                this.map[tile + 1] = Terrain.WATER;
                this.map[tile - 1] = Terrain.WATER;
                this.map[tile + WIDTH] = Terrain.WATER;
                this.map[tile - WIDTH] = Terrain.WATER;
            }
            Painter.fill(this, 0, 0, WIDTH, 1, Terrain.WALL);
            Painter.fill(this, 0, 0, 1, HEIGHT, Terrain.WALL);
            Painter.fill(this, WIDTH - 1, 0, 1, HEIGHT, Terrain.WALL);
            Painter.fill(this, 0, HEIGHT - 1, WIDTH, 1, Terrain.WALL);


            for (int m = 0; m < WIDTH; m++)
                for (int n = 0; n < HEIGHT; n++) {
                    if (this.map[m + WIDTH * n] == Terrain.WALL && Math.random() > 0.95)
                        this.map[m + WIDTH * n] = Terrain.WALL_DECO;
                }

            for (int x1 = 3; x1 < WIDTH - 3; x1++)
                for (int y1 = 3; y1 < HEIGHT - 3; y1++) {

                    int cell = x1 + WIDTH * y1;
                    //"islands"
                    if (Math.random() > 0.98) {
                        if (this.map[cell] == Terrain.WATER) this.map[cell] = Terrain.EMPTY;
                        if (this.map[cell + 1] == Terrain.WATER) this.map[cell + 1] = Terrain.EMPTY;
                        if (this.map[cell - 1] == Terrain.WATER) this.map[cell - 1] = Terrain.EMPTY;
                        if (this.map[cell + WIDTH] == Terrain.WATER)
                            this.map[cell + WIDTH] = Terrain.EMPTY;
                        if (this.map[cell - WIDTH] == Terrain.WATER)
                            this.map[cell - WIDTH] = Terrain.EMPTY;
                    }
                    //Some random walls for the map must not be empty
                    if (Math.random() > 0.999) {
                        if (this.map[cell] == Terrain.WATER) this.map[cell] = Terrain.BARRICADE;
                    }
                }
            Painter.fillEllipse(this, WIDTH / 2 - 7, HEIGHT / 2 - 7, 14, 14, Random.oneOf(Terrain.EMPTY,Terrain.WATER,Terrain.EMPTY_SP));
            Painter.fillEllipse(this, WIDTH / 2 - 6, HEIGHT / 2 - 6, 12, 12, Random.oneOf(Terrain.EMPTY,Terrain.WATER,Terrain.EMPTY_SP));
            Painter.fill(this, WIDTH / 2 - 8, HEIGHT/2-1,16,3, Terrain.WATER);
            Painter.fill(this, WIDTH / 2-1, HEIGHT/2 - 8,3,16, Terrain.WATER);

            for (int x1 = 1; x1 < WIDTH - 1; x1++)
                for (int y1 = 1; y1 < HEIGHT - 1; y1++) {
                    int cell = x1 + WIDTH * y1;
                    //some puddles
                    if (Math.random() > 0.92) {
                        if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.EMPTY_DECO;
                    }
                }
            Painter.fill(this, WIDTH / 2 - 6, HEIGHT / 2 - 11, 12, 7, Terrain.WATER);
            Painter.fillEllipse(this, WIDTH / 2 - 6, HEIGHT / 2 - 11, 12, 3, Terrain.BARRICADE);
            Painter.fill(this, WIDTH / 2 - 5, HEIGHT / 2 - 10, 10, 2, Terrain.EMPTY_SP);

            LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

            transitions.add(exit);

            this.map[exitCell] = Terrain.EXIT;
            this.map[amuletCell] = Terrain.PEDESTAL;





        } catch (Exception tryAgain){checkUp=false;} while (!checkUp);
        systemMapDisplay();
        return true;
    }

    public void systemMapDisplay(){
        for (int x = 0;x<WIDTH;x++){
            System.out.print("\n\n\n");
            System.out.println(x+":");
            for (int y = 0;y<HEIGHT;y++){
                System.out.print(this.map[x+WIDTH*y] + "  ");
            }
        }
    }


    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY) candidates.add(m);
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
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
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
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T5),Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        candidates.clear();

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 200;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
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
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
    }

}
