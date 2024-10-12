package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.RotLasher;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena7 extends Arena{

    {
        name = "Mage training grounds";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 15;
        WIDTH = 61;
        HEIGHT = 61;
        startLvl = 7;
        startGold = 2000;
        waveCooldownNormal = 50;
        waveCooldownBoss = 50;

        amuletCell = 30 + WIDTH*30;
        exitCell = amuletCell;
        towerShopKeeperCell = 26 + 25*WIDTH;
        normalShopKeeperCell = 34 + 25*WIDTH;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.GOBLIN_GROTTO},
                new float[]{1},
                false);
    }

    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 3;
            case 2: return 3;
            case 3: return 10;
            case 4: return 7;
            case 5: return 4;
            case 6: return 9;
            case 7: return 9;
            case 8: return 30;
            case 9: return 5;
            case 10: return 3;
            case 11: return 12;
            case 12: return 12;
            case 13: return 16;
            case 14: return 12;
            case 15: return 20;
        }
        return 1;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_PRISON;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
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

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,61,61, Terrain.WALL);
        Painter.fill(this, 1,1,59,59, Terrain.EMPTY_SP);

        for (int x = 2; x < 25; x+=3){
            Painter.fill(this, x,5,1,51, Terrain.BOOKSHELF);
        }
        for (int x = 37; x < 60; x+=3){
            Painter.fill(this, x,5,1,51, Terrain.BOOKSHELF);
        }

        for (int x = 1;x < WIDTH - 5; x++) for (int y = 1;y < WIDTH - 5; y++)
            if ((x>35 || x<20) && (y>35 || y<20) && Math.random()>0.988) {
                int type = Random.Int(9);

                switch (type){
                    case 1: default: //rounded shelves
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Terrain.BOOKSHELF);
                        break;
                    }
                    case 2://altar basic
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Terrain.EMPTY);
                        Painter.fill(this, x+2,y+2,1,1,Terrain.PEDESTAL);
                        break;
                    }
                    case 3://altar statues
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Terrain.EMPTY);
                        Painter.fill(this, x+2,y+2,1,1,Terrain.PEDESTAL);
                        this.map[x+1+WIDTH*(y+1)] = Terrain.STATUE;
                        this.map[x+1+WIDTH*(y+3)] = Terrain.STATUE;
                        this.map[x+3+WIDTH*(y+1)] = Terrain.STATUE;
                        this.map[x+3+WIDTH*(y+3)] = Terrain.STATUE;
                        this.map[x+2+WIDTH*(y+3)] = Terrain.EMPTY_DECO;
                        break;
                    }
                    case 4://altar well
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Terrain.EMPTY);
                        Painter.fill(this, x+2,y+2,1,1,Terrain.WELL);
                        break;
                    }
                    case 5://alchemy point
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        this.map[x+1+WIDTH*(y+1)] = Terrain.BOOKSHELF;
                        this.map[x+2+WIDTH*(y+1)] = Terrain.ALCHEMY;
                        this.map[x+3+WIDTH*(y+1)] = Terrain.PEDESTAL;
                        break;
                    }
                    case 6://pillar
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Terrain.WALL);
                        break;
                    }

                    case 7://blood no pedestial
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Random.oneOf(Terrain.EMPTY_DECO,Terrain.EMPTY));
                        break;
                    }
                    case 8://blood pedestial
                    {
                        Painter.fill(this, x,y,5,5,Terrain.EMPTY_SP);
                        Painter.fill(this, x+1,y+1,3,3,Random.oneOf(Terrain.EMPTY_DECO,Terrain.EMPTY));
                        Painter.fill(this, x+2,y+2,1,1,Terrain.PEDESTAL);
                        break;
                    }

                }


            }

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random torches
            int cell = x+WIDTH*y;
            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.BOOKSHELF) this.map[cell]=Terrain.WALL_DECO;
            }
        }


        Painter.fill(this, 2,28,58,5,Terrain.EMPTY_SP);
        Painter.fill(this, 2,29,58,3,Terrain.EMPTY);
        Painter.fill(this, 29,2,3,58,Terrain.EMPTY);
        Painter.fillEllipse(this, 27,27,7,7,Terrain.EMPTY);

        for (int x = 28;x<33;x+=4) for (int y = 3;y<HEIGHT-3;y+=6){
            this.map[x+WIDTH*y] = Terrain.STATUE_SP;
        }







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
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }
        if (mode == WndModes.Modes.CHALLENGE){
            for (int i = 0; i < 30; i++){
                this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            }
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        } else {
            this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WAND).identify(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        }

        candidates.clear();

        super.addDestinations();
    }


    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave ==7||wave==9||wave == 11||wave ==13||wave == 15) for (int q = 0; q<wave/3;q++){
            ArrayList<Integer> candidates = new ArrayList<>();
            for (Mob mob : mobs){
                for (int i : PathFinder.NEIGHBOURS8){
                    if (mob.alignment== Char.Alignment.ALLY&&this.passable[mob.pos + i] && Char.findChar(mob.pos+i)==null ) candidates.add(mob.pos+i);
                }
            }
            RotLasher rotLasher = new RotLasher();
            rotLasher.pos = Random.element(candidates);
            GameScene.add(rotLasher);
        }
    }



    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        switch (wave%4){
            case 0:super.deploymobs(wave, Direction.TOOUP, 1);break;
            case 1:super.deploymobs(wave, Direction.TOORIGHT, 1);break;
            case 2:super.deploymobs(wave, Direction.TOODOWN, 1);break;
            case 3:super.deploymobs(wave, Direction.TOOLEFT, 1);break;
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 200;
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






}
