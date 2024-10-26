package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.Item.updateQuickslot;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Dread;
import com.towerpixel.towerpixeldungeon.actors.buffs.Exposed;
import com.towerpixel.towerpixeldungeon.actors.buffs.RunningToReport;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goblin;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.bombs.FrostBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.HolyBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Noisemaker;
import com.towerpixel.towerpixeldungeon.items.bombs.RegrowthBomb;
import com.towerpixel.towerpixeldungeon.items.bombs.ShockBomb;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfInvisibility;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfDivineInspiration;
import com.towerpixel.towerpixeldungeon.items.quest.DarkGold;
import com.towerpixel.towerpixeldungeon.items.quest.Pickaxe;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena13Previous extends Arena{

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
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new DarkGold() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfMindVision() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfInvisibility() ,Random.element(candidates));
        this.drop(new PotionOfDivineInspiration() ,Random.element(candidates));
        this.drop(new PotionOfDivineInspiration() ,Random.element(candidates));
        this.drop(new PotionOfDivineInspiration() ,Random.element(candidates));
        this.drop(new PotionOfDivineInspiration() ,Random.element(candidates));
        this.drop(new PotionOfDivineInspiration() ,Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        this.drop(new ParalyticDart(), Random.element(candidates));
        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY_SP) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(new HolyBomb(),Random.element(candidates));
        this.drop(new FrostBomb(),Random.element(candidates));
        this.drop(new RegrowthBomb(),Random.element(candidates));
        this.drop(new ShockBomb(),Random.element(candidates));
        this.drop(new Noisemaker(),Random.element(candidates));
        this.drop(new Noisemaker(),Random.element(candidates));
        this.drop(new Noisemaker(),Random.element(candidates));
        this.drop(new Noisemaker(),Random.element(candidates));
        this.drop(new Noisemaker(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACTNOCHAINS),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));

        candidates.clear();

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {;
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

            delay = Random.Float( 1 );
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


    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
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
