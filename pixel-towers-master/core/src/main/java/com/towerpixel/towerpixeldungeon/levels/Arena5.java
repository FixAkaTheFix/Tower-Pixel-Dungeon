package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossOoze;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Ripple;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.potions.brews.CausticBrew;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfArcaneArmor;
import com.towerpixel.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfPrismaticImage;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.plants.Sorrowmoss;
import com.towerpixel.towerpixeldungeon.plants.Starflower;
import com.towerpixel.towerpixeldungeon.plants.Stormvine;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena5 extends Arena{
    {
        name = "Caustic depths";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 101;
        HEIGHT = 51;

        startGold = 1500;
        startLvl = 5;

        maxWaves= 25;

        amuletCell = 39 + WIDTH*25;
        exitCell = amuletCell;
        towerShopKeeperCell = 36 + 17*WIDTH;
        normalShopKeeperCell = 41 + 17*WIDTH;

        waveCooldownBoss = 70;
        waveCooldownNormal = 3;
    }


    @Override
    public void playLevelMusic() {
        if (locked){
            Music.INSTANCE.play(Assets.Music.CAVES_BOSS, true);
            return;
        }

        boolean gooAlive = false;
        for (Mob m : mobs){
            if (m instanceof BossOoze) {
                gooAlive = true;
                break;
            }
        }

        if (gooAlive){
            Music.INSTANCE.end();
        } else {
            Music.INSTANCE.playTracks(
                    new String[]{Assets.Music.SEWERS_BOSS},
                    new float[]{1},
                    false);
        }

    }

    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,101,51, Terrain.WALL);
        Painter.fill(this, 1,1,99,49, Terrain.EMPTY);
        Painter.fill(this,0,10,50,11, Terrain.WALL);
        Painter.fill(this,0,30,50,11, Terrain.WALL);
        Painter.fill(this,55,9,45,13, Terrain.WALL);
        Painter.fill(this,55,30,45,12, Terrain.WALL);
        Painter.fill(this,0,0,10,50, Terrain.WALL);
        Painter.fill(this, 35,21,12,9,Terrain.EMPTY_SP);
        Painter.fill(this, 37,23,5,5,Terrain.EMPTY);
        Painter.fill(this, 1,21,34,9,Terrain.WALL);

        Painter.fill(this,0,0,50,21, Terrain.WALL);
        Painter.fill(this,0,30,50,20, Terrain.WALL);

        Painter.fill(this,1,1,49,8, Terrain.EMPTY);
        Painter.fill(this,1,42,49,8, Terrain.EMPTY);

        Painter.fillDiamond(this,44,17,17,17,Terrain.EMPTY_SP);
        Painter.fillDiamond(this,46,19,13,13,Terrain.EMPTY);
        Painter.fillDiamond(this,48,21,9,9,Terrain.WATER);

        Painter.fill(this, 55,21, 45,9, Terrain.EMPTY);

        Painter.fill(this, 55,25, 41,1, Terrain.WATER);
        Painter.fill(this, 55,23, 42,1, Terrain.WATER);
        Painter.fill(this, 55,27, 43,1, Terrain.WATER);


        Painter.fill(this, 35,17,9,1,Terrain.EMPTY_SP);
        Painter.fill(this, 35,18,9,1,Terrain.PEDESTAL);
        Painter.fill(this, 35,19,9,1,Terrain.EMPTY);
        Painter.fill(this, 35,20,9,1,Terrain.WALL);
        Painter.fill(this, 38,20,3,1,Terrain.WALL_DECO);
        Painter.fill(this, 39,20,1,1,Terrain.DOOR);

        Painter.fill(this, 52,22,1,1,Terrain.STATUE);
        Painter.fill(this, 52,28,1,1,Terrain.STATUE);
        Painter.fill(this, 59,22,1,1,Terrain.STATUE);
        Painter.fill(this, 59,28,1,1,Terrain.STATUE);
        Painter.fill(this, 66,22,1,1,Terrain.STATUE);
        Painter.fill(this, 66,28,1,1,Terrain.STATUE);
        Painter.fill(this, 73,22,1,1,Terrain.STATUE);
        Painter.fill(this, 73,28,1,1,Terrain.STATUE);
        Painter.fill(this, 80,22,1,1,Terrain.STATUE);
        Painter.fill(this, 80,28,1,1,Terrain.STATUE);
        Painter.fill(this, 87,22,1,1,Terrain.STATUE);
        Painter.fill(this, 87,28,1,1,Terrain.STATUE);

        this.map[25*WIDTH + 99] = Terrain.LOCKED_EXIT;
        this.map[24*WIDTH + 99] = Terrain.WALL;

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
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
            if (Math.random()>0.92) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.EMPTY_DECO;
            }
            //water
            if (Math.random()>0.95) {
                for (int i:PathFinder.NEIGHBOURS8) if (this.map[cell+i]==Terrain.EMPTY&&(cell<WIDTH*17||cell>WIDTH*34)) this.map[cell+i]=Terrain.WATER;
            }
            //rare statues
            if (Math.random()>0.996) {
                if (this.map[cell]==Terrain.EMPTY && (cell<WIDTH*12||cell>WIDTH*39)) this.map[cell]=Terrain.STATUE;
            }
            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY && (cell<WIDTH*12||cell>WIDTH*39)) this.map[cell]=Terrain.WALL;
            }
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
        for (int m = 0; m<WIDTH*HEIGHT;m++) if (m<1300||m>3700){
            if (this.passable[m]) candidates.add(m);
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
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLevitation(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new PotionOfStormClouds(),Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Sorrowmoss.Seed(),Random.element(candidates));
        this.drop(new Starflower.Seed(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLevitation(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new PotionOfStormClouds(),Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates));
        this.drop(new Starflower.Seed(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
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
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new ElixirOfToxicEssence(), Random.element(candidates));
        this.drop(new CausticBrew(), Random.element(candidates));
        this.drop(new CausticBrew(), Random.element(candidates));
        this.drop(new CausticBrew(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));


        candidates.clear();

        super.addDestinations();
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
            BossOoze ooze = new BossOoze();
            ooze.pos = 25*WIDTH+96;
            GameScene.add(ooze);
            Camera.main.snapTo(ooze.sprite.center());
            for (int i : PathFinder.NEIGHBOURS25){
                CellEmitter.floor(ooze.pos+i).start(ElmoParticle.FACTORY,0.1f, 50);
                CellEmitter.floor(ooze.pos+i).start(ElmoParticle.FACTORY,1f, 5);
            }
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.3f, 0.5f);
            this.seal();
        } else {
            Buff.detach(hero, WaveBuff.class);
            Buff.affect(hero, WaveCooldownBuff.class, (wave % 5 == 4 ? waveCooldownBoss : waveCooldownNormal));
        }
    };

    @Override
    public void initNpcs() {
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell+WIDTH;
        GameScene.add(tower);
        super.initNpcs();
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.TOORIGHT, 1);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addSewerVisuals(this, visuals);
        return visuals;
    }

    public static void addSewerVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Arena5.Sink( i ) );
            }
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        super.doStuffEndwave(wave);
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                Arena5.WaterParticle p = (Arena5.WaterParticle)emitter.recycle( Arena5.WaterParticle.class );
                p.reset( x, y );
            }
        };

        public Sink( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 2, p.y + 3, 4, 0 );

            pour( factory, 0.1f );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if (!isFrozen() && (rippleDelay -= Game.elapsed) <= 0) {
                    Ripple ripple = GameScene.ripple( pos + Dungeon.level.width() );
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

            color( ColorMath.random( 0x001111, 0x112222 ) );
            size( 2 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            speed.set( Random.Float( -2, +2 ), 0 );

            left = lifespan = 0.4f;
        }
    }

}
