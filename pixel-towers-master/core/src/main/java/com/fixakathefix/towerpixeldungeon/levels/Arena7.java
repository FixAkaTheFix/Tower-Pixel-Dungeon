package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Necromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RotLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard2;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena7 extends ArenaPrison{
    /**
     * a filler stage with lasher invasions
     * introduction to the power of wands and scrolls
     */

    {
        name = "Mage training grounds";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 15;
        WIDTH = 61;
        HEIGHT = 61;
        startLvl = 7;
        startGold = 2000;
        waveCooldownNormal = 30;
        waveCooldownBoss = 50;
        arenaDepth = 7;

        amuletCell = 30 + WIDTH*30;
        exitCell = amuletCell;
        towerShopKeeperCell = 26 + 25*WIDTH;
        normalShopKeeperCell = 34 + 25*WIDTH;

        maxWaves = 15;
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
                mob = Random.oneOf(new Snake()); break;
            case 2:
                mob = Random.oneOf(new Shaman.BlueShaman(), new Shaman.PurpleShaman()); break;
            case 3:
                mob = Random.oneOf(new Skeleton(), new Rat()); break;
            case 4:
                mob = Random.oneOf(new Skeleton()); break;
            case 5:
                mob = Random.oneOf(new Necromancer()); break;
            case 6:
                mob = Random.oneOf(new Shaman.BlueShaman(), new Shaman.PurpleShaman()); break;
            case 7:
                mob = new Shaman.RedShaman(); break;
            case 8:
                mob = Random.oneOf(new Snake()); break;
            case 9:
                mob = Random.oneOf(new Elemental.FrostElemental()); break;
            case 10:
                mob = Random.oneOf(new Ghoul()); break;
            case 11:
                mob = new Shaman.RedShaman(); break;
            case 12:
                mob = new Necromancer(); break;
            case 13:
                mob = Random.oneOf(new Shaman.BlueShaman(),new Shaman.PurpleShaman()); break;
            case 14:
                mob = Random.oneOf(new MagiCrab()); break;
            case 15: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new MagiCrab();
                } else mob = Random.oneOf(new Necromancer(), new Shaman.RedShaman(), new Shaman.BlueShaman(), new Shaman.PurpleShaman());
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
                this.drop(Generator.random(Generator.Category.SCROLL2),Random.element(candidates));
            }
            for (int i = 0; i < 8; i++){
                this.drop(new ScrollOfUpgrade(),Random.element(candidates));
            }
        } else {
            dropMany(candidates,
                    Generator.random(Generator.Category.ARTIFACT),
                    Generator.random(Generator.Category.RING),
                    Generator.random(Generator.Category.WAND),
                    Generator.random(Generator.Category.RING),
                    Generator.random(Generator.Category.WAND),
                    Generator.random(Generator.Category.RING),
                    Generator.random(Generator.Category.WAND),
                    Generator.random(Generator.Category.WAND),
                    Generator.random(Generator.Category.WAND),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.SCROLL),
                    Generator.random(Generator.Category.POTION),
                    Generator.random(Generator.Category.ARMOR),
                    Generator.random(Generator.Category.ARMOR),
                    Generator.random(Generator.Category.WEP_T2),
                    Generator.random(Generator.Category.WEP_T2),
                    Generator.random(Generator.Category.WEP_T2)
                    );
        }

        candidates.clear();

        super.addDestinations();
    }


    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave ==7||wave==9||wave == 11||wave ==13||wave == 15) for (int q = 0; q<wave/2;q++){
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
    public void initNpcs() {
        TowerGuard2 guard1 = new TowerGuard2();
        TowerGuard2 guard2 = new TowerGuard2();
        guard1.pos = amuletCell+1;
        guard2.pos = amuletCell-1;
        GameScene.add(guard1);
        GameScene.add(guard2);

        super.initNpcs();
    }
}
