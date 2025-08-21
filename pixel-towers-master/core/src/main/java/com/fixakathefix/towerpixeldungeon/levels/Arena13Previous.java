package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.Item.updateQuickslot;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Dread;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Exposed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.RunningToReport;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goblin;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.bombs.FrostBomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.HolyBomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.Noisemaker;
import com.fixakathefix.towerpixeldungeon.items.bombs.RegrowthBomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.ShockBomb;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfInvisibility;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.fixakathefix.towerpixeldungeon.items.quest.DarkGold;
import com.fixakathefix.towerpixeldungeon.items.quest.Pickaxe;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena13Previous extends ArenaCaves{

    {

        name = "Gobs' caves";
        WIDTH = 100;
        HEIGHT = 100;
        color1 = 0x534f3e;
        color2 = 0xb9d661;
        viewDistance = 12;

        startGold = 0;
        startLvl = 11;
        arenaDepth = 13;
        maxWaves = 1;

        amuletCell = 9 + WIDTH*25;
        exitCell = amuletCell;
        towerShopKeeperCell = 6 + 21*WIDTH;
        normalShopKeeperCell = 11 + 21*WIDTH;

        waveCooldownNormal = 5000;
        waveCooldownBoss = 2;
    }
    public int goldCounter;

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.GOBLIN_GROTTO},
                new float[]{1},
                false);
    }

    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        super.deploymobs(wave, Direction.RIGHT, 3);
    }



    @Override
    protected boolean build() {
        setSize(WIDTH, HEIGHT);

        //main
        Painter.fill(this, 0,0,100,100, Terrain.WALL);
        Painter.fill(this, 2,2,96,93, Terrain.EMPTY);

        //the caves gen
        for (int x = 1; x < WIDTH-8; x+=Random.Int(1,7)) for (int y = 1; y < HEIGHT - 8; y+=Random.Int(1,7)){
            int i = Random.Int(8);
            switch (i){
                case 0:case 1:case 2:{
                    Painter.fillDiamond(this, x+Random.Int(3), y+Random.Int(3),1+Random.Int(4),1+Random.Int(4),Terrain.WALL );
                    break;
                }
                case 3:case 4:{
                    Painter.fill(this, x+Random.Int(3), y+Random.Int(3),1+Random.Int(4),1+Random.Int(4),Terrain.WALL );
                    break;
                }
                case 5:case 6:case 7:{
                    Painter.fillEllipse(this, x+Random.Int(3), y+Random.Int(3),1+Random.Int(4),1+Random.Int(4),Terrain.WALL );
                    break;
                }
            }
        }
        //the structures
        for (int x1 = 3; x1 < WIDTH - 11; x1++)
            for (int y1 = 3; y1 < HEIGHT - 11; y1++) {

                int cell = x1 + WIDTH * y1;
                //Random grass
                if (Math.random() > 0.92) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.GRASS;

                }
                //tall grass
                if (Math.random() > 0.95) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.HIGH_GRASS;
                }
                //water
                if (Math.random() > 0.98) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.WATER;
                    if (this.map[cell + 1] == Terrain.EMPTY) this.map[cell + 1] = Terrain.WATER;
                    if (this.map[cell - 1] == Terrain.EMPTY) this.map[cell - 1] = Terrain.WATER;
                    if (this.map[cell + WIDTH] == Terrain.EMPTY)
                        this.map[cell + WIDTH] = Terrain.WATER;
                    if (this.map[cell - WIDTH] == Terrain.EMPTY)
                        this.map[cell - WIDTH] = Terrain.WATER;
                }
            }

        //the paths
        int pathnum = 10;//VARIABLES YOU CAN CHANGE IF THE CAVES SEEM NOT BIG ENOUGH
        int x = Random.Int(10, WIDTH - 10);//sets the starting point
        int y = Random.Int(10, HEIGHT - 10);

        int xcan = Random.Int(10, WIDTH - 10);
        int ycan = Random.Int(10, HEIGHT - 10);

        int pathnummax = pathnum;

        for (int i = 0; i < pathnummax; i++) {
            pathnum--;

            int xcur = xcan;
            int ycur = ycan;

            xcan = Random.Int(10, WIDTH - 10);
            ycan = Random.Int(10, HEIGHT - 10);



            while (!(xcur == xcan && ycur == ycan)) {

                if (xcan < xcur && xcur > 9) xcur--;
                else if (xcan > xcur && xcur < WIDTH - 9) xcur++;
                else if (ycan < ycur && ycur > 9) ycur--;
                else if (ycan > ycur && ycur < HEIGHT - 9) ycur++;

                Painter.fill(this,xcur-1,ycur-1,2,2,Terrain.EMPTY_SP);
            }
        }
        ArrayList<Integer> emptyspTiles = new ArrayList<>();
        for (int m = 4; m < WIDTH - 4; m++)
            for (int n = 4; n < HEIGHT - 4; n++) {
                if (this.map[m + WIDTH * n] == Terrain.EMPTY_SP) emptyspTiles.add(m + WIDTH * n);
            }
        for (int tile : emptyspTiles) {
            if (this.map[tile+1]!=Terrain.EMPTY_SP)this.map[tile + 1] = Terrain.EMPTY;
            if (this.map[tile-1]!=Terrain.EMPTY_SP) this.map[tile - 1] = Terrain.EMPTY;
            if (this.map[tile+WIDTH]!=Terrain.EMPTY_SP) this.map[tile + WIDTH] = Terrain.EMPTY;
            if (this.map[tile-WIDTH]!=Terrain.EMPTY_SP) this.map[tile - WIDTH] = Terrain.EMPTY;
        }


        for (int x1 = 1; x1 < WIDTH - 1; x1++)
            for (int y1 = 1; y1 < HEIGHT - 1; y1++) {
                int cell = x1 + WIDTH * y1;
                //some pebbles and gold
                if (Math.random() > 0.75) {
                    if (this.map[cell] == Terrain.EMPTY) this.map[cell] = Terrain.EMPTY_DECO;
                    if (this.map[cell] == Terrain.WALL) this.map[cell] = Terrain.WALL_DECO;
                }
            }







        Painter.fill(this, 4,21,10,10,Terrain.EMPTY_SP);
        Painter.fill(this, 6,23,6,6,Terrain.EMPTY);
        Painter.fill(this, 4, 22,10,1,Terrain.PEDESTAL);
        Painter.fill(this, 4,20,10,1,Terrain.BOOKSHELF);
        this.map[13+21*WIDTH]=Terrain.BOOKSHELF;
        this.map[4+21*WIDTH]=Terrain.BOOKSHELF;
        Painter.fill(this, 14,21,1,3,Terrain.WALL);
        Painter.fill(this, 14,27,1,3,Terrain.WALL);
        this.map[5+23*WIDTH]=Terrain.ALCHEMY;

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[amuletCell] = Terrain.PEDESTAL;
        return true;
    }

    @Override
    public void startWave() {
        Buff.affect(hero, Exposed.class);
        super.startWave();
    }

    @Override
    public void initNpcs() {
        hero.lvl = startLvl;
        hero.updateHT(true);
        Dungeon.gold += startGold;
        updateQuickslot();
        towerShopKeeper.pos = towerShopKeeperCell;
        normalShopKeeper.pos = normalShopKeeperCell;
        level = this;
        GameScene.add(towerShopKeeper);
        this.occupyCell(towerShopKeeper);
        GameScene.add(normalShopKeeper);
        this.occupyCell(normalShopKeeper);
        Buff.affect(hero, WaveCooldownBuff.class, 2000);
        exitCell = 0;
        Dungeon.level.map[amuletCell] = Terrain.EMPTY;
        hero.pos = amuletCell;
        level.occupyCell(hero);
        level.seal();
        Pickaxe pickaxe = new Pickaxe();
        pickaxe.identify().collect();
        for (int i = 0;i<15;i++) this.deploymobs(2,Direction.RIGHT,2);
    }


    @Override
    public void addDestinations() {
        this.drop(new Pickaxe() ,amuletCell);
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT-1;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY) candidates.add(m);
        }
        for (int i = 0; i < 5; i++) dropMany(candidates,
                new Pickaxe() ,
                new DarkGold() ,
                new DarkGold() ,
                new DarkGold() ,
                new PotionOfMindVision() ,
                new PotionOfMindVision() ,
                new PotionOfInvisibility() ,
                new PotionOfInvisibility() ,
                new PotionOfDivineInspiration() ,
                new ParalyticDart(),
                new ParalyticDart()
                );

        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }

        dropMany(candidates,
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                new HolyBomb(),
                new FrostBomb(),
                new RegrowthBomb(),
                new ShockBomb(),
                new Noisemaker(),
                new Noisemaker(),
                new Noisemaker(),
                new Noisemaker(),
                new Noisemaker(),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.ARMOR),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.ARMOR),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.ARMOR),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.ARTIFACT),
                Generator.random(Generator.Category.MIS_T3),
                Generator.random(Generator.Category.MIS_T2),
                Generator.random(Generator.Category.MIS_T4),
                Generator.random(Generator.Category.MIS_T5),
                Generator.random(Generator.Category.WEP_T4),
                Generator.random(Generator.Category.WEP_T2),
                Generator.random(Generator.Category.WEP_T3),
                new Pickaxe() ,
                new Pickaxe() ,
                new Pickaxe() ,
                new SpawnerCrossbow(),
                new SpawnerCrossbow(),
                new SpawnerWand(),
                new SpawnerWand(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerGrave(),
                new SpawnerGrave(),
                new SpawnerCannon(),
                new SpawnerCannon(),
                new SpawnerCannon(),
                new SpawnerCrossbow(),
                new SpawnerCrossbow(),
                new SpawnerWand(),
                new SpawnerWand(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerWall(),
                new SpawnerGrave(),
                new SpawnerGrave(),
                new SpawnerCannon(),
                new SpawnerCannon(),
                new SpawnerCannon()
                );


        candidates.clear();

        super.addDestinations();
    }

    public static final class GoblinGuard extends Goblin {
        {
            HP = HT = 40;
            EXP = -1;
            viewDistance = Random.IntRange(6,8);
            baseSpeed =0.7f;
        }
        public int suspicion = 0;
        public int suscooldown = 0;
        @Override
        protected boolean act() {
            if (level.wave==1) Buff.affect(this, Dread.class);
            if (HP<HT) HP+=4;
            if (suscooldown>0) suscooldown--;
            ArrayList <Integer> candidates = new ArrayList<>();
            if (suspicion<2&& Math.random()>0.99f) {
                for (int m = 0; m < level.width * level.height; m++) {
                    if (Dungeon.level.passable[m] && level.map[m] == Terrain.EMPTY_SP)
                        candidates.add(m);
                }
                this.beckon(candidates.get(Random.Int(candidates.size()-1)));
            }
            if (suspicion<2&& Math.random()>0.99f&& enemy==null) {//they go to sleep at random. A job of a guard is hard.
                this.state = SLEEPING;
            }
            if (Math.random()>0.95f&&this.state==SLEEPING) {
                this.state = WANDERING;
            }
            if (enemy != null && suscooldown<=0){
                if (suspicion<10)suspicion++;
                suscooldown=2;
                int i = Random.Int(3);
                int suslevel = suspicion/3;
                if (suslevel>2) suslevel = 2;
                int speakcolor = CharSprite.NEUTRAL;
                switch (suslevel){
                    case 0: {
                        enemy = null;//thinks that a player was wind blowing or smth
                        Buff.shorten(hero, WaveCooldownBuff.class, 5f);
                        speakcolor = CharSprite.NEUTRAL;
                        break;
                    }
                    case 1: {
                        enemy = null;
                        baseSpeed = 0.9f; //speeds up, as he knows someone is there
                        Buff.shorten(hero, WaveCooldownBuff.class, 30f);
                        speakcolor = CharSprite.WARNING;
                        break;
                    }
                    case 2: {
                        Sample.INSTANCE.play(Assets.Sounds.FALLING,1.1f,(float)Math.random()+1f);
                        baseSpeed = 1.3f;//speeds up
                        viewDistance = 50;
                        Buff.affect(this, RunningToReport.class);//runs away and disappears if out of sight, if dies by this calls for help, decreasing the counter for the wave
                        speakcolor = CharSprite.NEGATIVE;
                        break;
                    }
                }

                speak(Messages.get(this,"s"+ suslevel+"i"+i), speakcolor);
            }
            return super.act();
        }

        @Override
        public void die(Object cause) {
            if (cause == RunningToReport.class){//death by reporting speeds up the reinforcements cometh
                if (enemy == hero){
                    GLog.n(Messages.get(this, "escaped"));
                    Buff.shorten(hero, WaveCooldownBuff.class, 200f);
                } else {
                    GLog.n(Messages.get(this, "escapednotseenhero"));//less suspicion if the goblin received damage without seeing the hero
                    Buff.shorten(hero, WaveCooldownBuff.class, 100f);
                }
                ((Arena)Dungeon.level).deploymobs(2, Arena.Direction.RIGHT, 2);
            }
            super.die(cause);
        }

        @Override
        public int defenseProc(Char enemy, int damage) {
            Sample.INSTANCE.play(Assets.Sounds.FALLING,1.1f,(float)Math.random()+1f);
            baseSpeed = 1.3f;//speeds up
            this.suspicion +=10;
            this.beckon(Random.Int(level.width) + level.width*Random.Int(level.height));//runs away in a random direction
            Buff.affect(this, RunningToReport.class);//calls for help, may not have a target but still reports, this causes less suspicion
            return super.defenseProc(enemy, damage);
        }
        private final String SUSPICION = "suspicion";

        @Override
        public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
            return true;
        }

        @Override
        protected boolean getCloser( int target ) {
            if (state == HUNTING && suspicion <= 2) {
                return enemySeen && getFurther( target );
            } else {
                return super.getCloser( target );
            }
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(SUSPICION, suspicion);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            suspicion = bundle.getInt(SUSPICION);
        }
    }

    private final String GOLDCOUNT = "goldcount";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(GOLDCOUNT, goldCounter);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        goldCounter = bundle.getInt(GOLDCOUNT);
    }
}
