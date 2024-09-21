package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goblin;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerMiner;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.quest.CorpseDust;
import com.towerpixel.towerpixeldungeon.items.quest.Pickaxe;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
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

        startGold = 3000;
        startLvl = 11;

        maxWaves = 10;

        amuletCell = WIDTH/2 +WIDTH*HEIGHT/2 + 3;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 10*WIDTH - 6;
        normalShopKeeperCell = amuletCell - 10*WIDTH - 1;

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
        if (wave % 2 == 1) {
            super.deploymobs(wave, Direction.TOORIGHT, 1);
        }
        super.deploymobs(wave, Direction.TOOLEFT, 1);
    }

    @Override
    protected boolean build() {
        boolean checkUp = true;
        do try {
            setSize(WIDTH, HEIGHT);

            Painter.fill(this, 0, 0, WIDTH, HEIGHT, Terrain.WALL);

            Painter.fillDiamond(this, 2, HEIGHT/4, WIDTH/2-2, HEIGHT/2-2, Terrain.WATER);
            Painter.fillDiamond(this, 7, HEIGHT/4+5, WIDTH/2-12, HEIGHT/2-12, Terrain.WALL);


            Painter.fillDiamond(this, WIDTH/2-2, HEIGHT/4, WIDTH/2-2, HEIGHT/2-2, Terrain.WATER);
            Painter.fillDiamond(this, WIDTH/2+3, HEIGHT/4+5, WIDTH/2-12, HEIGHT/2-12, Terrain.WALL);


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
            Painter.fillEllipse(this, WIDTH / 2 - 7, HEIGHT / 2 - 7, 14, 14, Terrain.WATER);
            Painter.fillEllipse(this, WIDTH / 2 - 6, HEIGHT / 2 - 6, 12, 12, Terrain.EMPTY);


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
    public void initNpcs() {
        super.initNpcs();
        TowerMiner miner1 = new TowerMiner();
        miner1.pos = amuletCell - 6;
        GameScene.add(miner1);
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY) candidates.add(m);
        }

        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(new CorpseDust(),Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        candidates.clear();

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        for (Mob mob : mobs){
            if (mob instanceof TowerMiner){
                TowerMiner miner = (TowerMiner) mob;
                miner.dropGold(200);
            }
        }
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
