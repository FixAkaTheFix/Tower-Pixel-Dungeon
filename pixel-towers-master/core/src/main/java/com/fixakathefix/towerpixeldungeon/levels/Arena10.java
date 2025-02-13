package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shinobi;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWave;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.levels.traps.GrimTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TenguSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena10 extends Arena{

    /**
     * a hard boss stage
     */

    {
        name = "Protected cell block";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 19;
        WIDTH = 100;
        HEIGHT = 21;
        startLvl = 10;
        startGold = 1000;
        arenaDepth = 10;

        maxWaves=20;

        amuletCell = 10 + WIDTH*10;
        exitCell = amuletCell;
        towerShopKeeperCell = 12+2*WIDTH;;
        normalShopKeeperCell = 17+2*WIDTH;

        waveCooldownNormal = 5;
        waveCooldownBoss = 120;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
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
                mob = Random.oneOf(new Thief()); break;
            case 3:
                mob = Random.oneOf(new Guard(), new Thief()); break;
            case 4:
                mob = Random.oneOf(new DM100()); break;
            case 5: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GnollTrickster();
                } else mob = Random.oneOf(new Thief());
                break;
            }
            case 6:
                mob = Random.oneOf(new Guard(), new Bandit()); break;
            case 7:
                mob = new Brute(); break;
            case 8:
                mob = Random.oneOf(new GnollTrickster()); break;
            case 9:
                mob = Random.oneOf(new Guard(), new Brute()); break;
            case 10: {
                mob = Random.oneOf(new Guard());
                break;
            }
            case 11:
                mob = Random.oneOf(new Snake()); break;
            case 12:
                mob = Random.oneOf(new Snake(), new Thief()); break;
            case 13:
                mob = Random.oneOf(new Bandit(), new Thief()); break;
            case 14:
                mob = Random.oneOf(new Bat()); break;
            case 15: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Shinobi();
                } else mob = new GnollTrickster();
                break;
            }
            case 16:
                mob = Random.oneOf(new Guard(), new Thief()); break;
            case 17:
                mob = new Shinobi(); break;
            case 18:
                mob = Random.oneOf(new Bandit()); break;
            case 19:
                mob = Random.oneOf(new Brute(), new Shinobi()); break;
            case 20: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Shinobi();
                } else mob = Random.oneOf(new Guard());
                break;
            }
            case 8055:
                mob = new Shinobi();
                Buff.affect(mob, Speed.class, 15);
                break;
            case 8056:
                mob = new Shinobi(); break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 13;
            case 2: return 8;
            case 3: return 6;
            case 4: return 8;
            case 5: return 15;
            case 6: return 10;
            case 7: return 5;
            case 8: return 7;
            case 9: return 7;
            case 10: return 11;
            case 11: return 60;
            case 12: return 40;
            case 13: return 40;
            case 14: return 20;
            case 15: return 22;
            case 16: return 60;
            case 17: return 17;
            case 18: return 110;
            case 19: return 30;
            case 20: return 50;
            case 8055: return 2;
            case 8056: return 20;
        }
        return 1;
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
    public void deploymobs(int wave, Direction direction, int group) {
        super.deploymobs(wave, Direction.RIGHT, 4);
        if (wave == 10) {
            EnemyPortal.createEnemyPortal(3 + WIDTH*4, 80);
        }
        if (wave == 16) {
            EnemyPortal.createEnemyPortal(3 + WIDTH*17, 80);
        }
    }

    @Override
    public void initNpcs() {
        super.initNpcs();

        if( mode == WndModes.Modes.CHALLENGE )for (int x = 1; x < 3; x++) for (int y = 6; y< 15; y++){
            TowerGuard1 towerGuard1 = new TowerGuard1();
            towerGuard1.sellable = false;
            towerGuard1.pos = x + WIDTH*y;
            GameScene.add(towerGuard1);
        }
    }

    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,100,21, Terrain.WALL);
        Painter.fill(this, 1,1,98,19, Terrain.EMPTY);
        Painter.fill(this, 20,1,50,19, Terrain.CHASM);
        Painter.fill(this, 20,1,7,19, Terrain.WALL);
        Painter.fill(this, 8,7,90,7, Terrain.EMPTY);
        Painter.fill(this,9,9,3,3,Terrain.EMPTY_SP);
        Painter.fill(this,0,0,6,6,Terrain.WALL);
        Painter.fill(this,1,1,4,4,Terrain.EMPTY);
        Painter.fill(this,0,15,6,6,Terrain.WALL);
        Painter.fill(this,1,16,4,4,Terrain.EMPTY);
        this.map[5+WIDTH*4]=Terrain.DOOR;
        this.map[5+WIDTH*16]=Terrain.DOOR;




        //pillars
        for (int i = 20 ;i<=75;i+=4) {
            this.map[i+1+6*WIDTH] = Terrain.WALL;
            this.map[i+1+7*WIDTH] = Terrain.WALL;
            this.map[i+7*WIDTH] = Terrain.WALL;
            this.map[i+6*WIDTH] = Terrain.WALL;
        }

        for (int i = 20;i<=75;i+=4) {
            this.map[i+1+13*WIDTH] = Terrain.WALL;
            this.map[i+1+14*WIDTH] = Terrain.WALL;
            this.map[i+14*WIDTH] = Terrain.WALL;
            this.map[i+13*WIDTH] = Terrain.WALL;
        }

        //pathways
        for (int i = 26;i<=55;i+=8) {
            int type = Random.Int(5);
            switch (type) {
                case 1:
                case 2: {
                    Painter.fill(this, i+1,12,4,8, Terrain.EMPTY);
                    break;
                }
            }
        }
        for (int i = 26;i<=60;i+=8) {
            int type = Random.Int(5);
            switch (type) {
                case 1:
                case 2: {
                    Painter.fill(this, i+1,1,4,7, Terrain.EMPTY);
                    break;
                }
            }
        }

        //Tengu's room

        Painter.fill(this, 75,5,11,11, Terrain.WALL);
        Painter.fill(this, 76,6,9,9, Terrain.EMPTY);
        this.map[75+WIDTH*11] = Terrain.LOCKED_DOOR;
        for (int x = 76;x<=84;x++) for (int y = 6;y<=15;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell]==Terrain.EMPTY){
                this.map[cell]=Terrain.TRAP;
                this.setTrap(new GrimTrap().hide(), cell);
            }
        }

        for (int x = 20;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //tall grass
            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.HIGH_GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.HIGH_GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.HIGH_GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.HIGH_GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.HIGH_GRASS;
            }
            //some puddles
            if (Math.random()>0.99) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.EMPTY_DECO;
            }
            //water
            if (Math.random()>0.96) {
                for (int i:PathFinder.NEIGHBOURS8) if (this.map[cell+i]==Terrain.EMPTY&&(cell<WIDTH*17||cell>WIDTH*34)) this.map[cell+i]=Terrain.WATER;
            }
            //rare statues
            if (Math.random()>0.999) {
                if (this.map[cell]==Terrain.EMPTY && (cell<WIDTH*12||cell>WIDTH*39)) this.map[cell]=Terrain.STATUE;
            }

        }
        Painter.fill(this,7,7,7,7,Terrain.EMPTY_SP);
        Painter.fill(this,8,8,5,5,Terrain.WATER);
        Painter.fill(this,9,9,3,3,Terrain.EMPTY_SP);



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }



    @Override
    public void endWave() {
        for (Heap heap: Dungeon.level.heaps.valueList()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                if (ShatteredPixelDungeon.scene() instanceof GameScene) {
                    CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
                }
                if (heap.size() == 1) {
                    heap.destroy();
                } else {
                    heap.items.remove(heap.size()-1);
                    heap.type = Heap.Type.HEAP;
                }
            }
        }
        towerShopKeeper.placeItems();
        normalShopKeeper.placeItems();
        doStuffEndwave(wave);
        if (wave==maxWaves) {
            for (Mob mob : mobs){
                if (mob instanceof TowerWave){
                    ((TowerWave)mob).isPrepared=true;
                }
            }
            BossTengu bossTengu = new BossTengu();
            this.map[75+11*WIDTH]=Terrain.EMBERS;
            bossTengu.pos = 75+11*WIDTH;
            GameScene.add(bossTengu);
            if (mode == WndModes.Modes.HARDMODE){
                BossTengu bossTengu2 = new BossTengu();
                bossTengu2.pos = 75+12*WIDTH;
                GameScene.add(bossTengu2);
                bossTengu.HP = bossTengu.HT/2;
                bossTengu2.HP = bossTengu2.HT/2;
            }
            for (int i : PathFinder.NEIGHBOURS25){
                CellEmitter.floor(bossTengu.pos+i).start(SmokeParticle.FACTORY,0.03f, 50);
            }
            Sample.INSTANCE.play(Assets.Sounds.BLAST,1.5f, 0.9f);
            this.seal();
        } else {
            Buff.detach(hero, WaveBuff.class);
            Buff.affect(hero, WaveCooldownBuff.class, (wave % 5 == 4 ? waveCooldownBoss : waveCooldownNormal));
        }
    };

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY && distance(amuletCell, m) > 13) candidates.add(m);
        }
        for (int i = 0; i < 12; i ++){
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SCROLL2),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        }
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T5),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND).identify(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates));
        candidates.clear();

        super.addDestinations();
    }
    @Override
    public void doStuffStartwave(int wave) {

        super.doStuffStartwave(wave);

        if (wave==20) {
            TenguSprite sprite = new TenguSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();

            WndDialogueWithPic.dialogue(sprite, "???",
                    new String[]{
                            Messages.get(RatKing.class, "l10w20start1"),
                            Messages.get(RatKing.class, "l10w20start2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
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

            add( new Halo( 12, 0xFFEEEE, 0.4f ).point( p.x, p.y + 1 ) );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
    }



}
