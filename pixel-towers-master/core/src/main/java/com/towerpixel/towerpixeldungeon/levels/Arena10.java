package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bandit;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTengu;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shinobi;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.traps.GrimTrap;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TenguSprite;
import com.towerpixel.towerpixeldungeon.sprites.YogSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena10 extends Arena{

    {
        name = "Protected cell block";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 15;
        WIDTH = 100;
        HEIGHT = 21;
        startLvl = 10;
        startGold = 1000;

        maxWaves=20;

        amuletCell = 10 + WIDTH*10;
        exitCell = amuletCell;
        towerShopKeeperCell = 12+2*WIDTH;;
        normalShopKeeperCell = 17+2*WIDTH;

        waveCooldownNormal = 5;
        waveCooldownBoss = 70;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
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
        if (wave >= 12 && wave <= 19) {
            for (int i = 0; i<wave-10;i++){
                Bandit shinobi1 = new Bandit();
                shinobi1.pos = 3 + WIDTH*4;
                GameScene.add(shinobi1);
                level.occupyCell(shinobi1);
            }
        }
        if (wave >= 16 && wave <= 19) {
            for (int i = 0; i<wave-15;i++){
                Shinobi shinobi1 = new Shinobi();
                shinobi1.pos = 3 + WIDTH*16;
                GameScene.add(shinobi1);
                level.occupyCell(shinobi1);
            }
        }
    }

    @Override
    public void initNpcs() {
        super.initNpcs();

        if( mode == WndModes.Modes.CHALLENGE )for (int x = 1; x < 4; x++) for (int y = 6; y< 15; y++){
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
        for (int i = 15;i<=70;i+=5) {
            this.map[i+1+6*WIDTH] = Terrain.WALL;
            this.map[i+1+7*WIDTH] = Terrain.WALL;
            this.map[i+7*WIDTH] = Terrain.WALL;
            this.map[i+6*WIDTH] = Terrain.WALL;
        }

        for (int i = 15;i<=70;i+=5) {
            this.map[i+1+13*WIDTH] = Terrain.WALL;
            this.map[i+1+14*WIDTH] = Terrain.WALL;
            this.map[i+14*WIDTH] = Terrain.WALL;
            this.map[i+13*WIDTH] = Terrain.WALL;
        }

        //pathways
        for (int i = 25;i<=55;i+=5) {
            int type = Random.Int(8);
            switch (type) {
                case 2: {
                    Painter.fill(this, i+2,12,3,8, Terrain.EMPTY);
                    break;
                }
                case 3: {
                    Painter.fill(this, i,13,7,7, Terrain.WALL);
                    Painter.fill(this, i+2,14,3,6, Terrain.EMPTY);
                    this.map[i+3+WIDTH*13] = Terrain.DOOR;
                    this.map[i+2+WIDTH*13] = Terrain.WALL_DECO;
                    this.map[i+4+WIDTH*13] = Terrain.WALL_DECO;
                    break;
                }
            }
        }
        for (int i = 25;i<=55;i+=5) {
            int type = Random.Int(8);
            switch (type) {
                case 1: {
                    Painter.fill(this, i,1,7,7, Terrain.WALL);
                    Painter.fill(this, i+2,1,3,7, Terrain.EMPTY);
                    break;
                }
                case 2: {
                    Painter.fill(this, i+2,1,3,7, Terrain.EMPTY);
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

            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //tall grass
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.HIGH_GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.HIGH_GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.HIGH_GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.HIGH_GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.HIGH_GRASS;
            }
            //some puddles
            if (Math.random()>0.98) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.EMPTY_DECO;
            }
            //water
            if (Math.random()>0.95) {
                for (int i:PathFinder.NEIGHBOURS8) if (this.map[cell+i]==Terrain.EMPTY&&(cell<WIDTH*17||cell>WIDTH*34)) this.map[cell+i]=Terrain.WATER;
            }
            //rare statues
            if (Math.random()>0.999) {
                if (this.map[cell]==Terrain.EMPTY && (cell<WIDTH*12||cell>WIDTH*39)) this.map[cell]=Terrain.STATUE;
            }

        }

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
            BossTengu bossTengu = new BossTengu();
            this.map[75+11*WIDTH]=Terrain.EMBERS;
            bossTengu.pos = 75+11*WIDTH;
            GameScene.add(bossTengu);
            Camera.main.snapTo(bossTengu.sprite.center());
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
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
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
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T1),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T5),Random.element(candidates));
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
