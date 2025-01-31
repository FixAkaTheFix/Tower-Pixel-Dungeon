package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GreatCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Piranha;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RotLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.effects.Ripple;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfRatLegion;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.AlmostEmptySprite;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena4 extends Arena {
    /**
     * Introduces portals, in a manner that allows for killing them easy (summons snek).
     * Introduces a new tower: cannon, after that the tower menu is unlocked, with 2 towers appearing: Rat camp and Cannon
     */

    {
        name = "Crab lake";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 61;
        HEIGHT = 61;

        startGold = 1000;
        startLvl = 3;
        arenaDepth = 4;
        maxWaves = 15;

        amuletCell = 31 + WIDTH * 4;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 3 * WIDTH - 4;
        normalShopKeeperCell = amuletCell - 3 * WIDTH + 2;

        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }


    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = Random.oneOf(new Snake()); break;
            case 2:
                mob = Random.oneOf(new Crab()); break;
            case 3:
                mob = Random.oneOf(new Crab(), new Slime()); break;
            case 4:
                mob = new Slime(); break;
            case 5:
                mob = new HermitCrab(); break;
            case 6:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new HermitCrab();
                } else mob = new Crab();
                break;
            case 7:
                mob = new Slime(); break;
            case 8:
                mob = Random.oneOf(new Snake(), new Rat()); break;
            case 9:
                mob = Random.oneOf(new Crab(), new Slime()); break;
            case 10:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GreatCrab();
                } else mob = new Crab();
                break;
            case 11:
                mob = new Crab(); break;
            case 12:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new HermitCrab();
                } else mob = new Slime();
                break;
            case 13:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Crab();
                } else mob = new MagiCrab();
                break;
            case 14:
                mob = new Crab();
                break;
            case 15: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Goo();
                } else mob = new CausticSlime();
                break;
            }
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
            if (mob instanceof Crab) {
                MagiCrab cr = new MagiCrab();
                cr.buffs().addAll(mob.buffs());
                return cr;
            }
        }
        return mob;
    }


    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 3;
            case 2: return 3;
            case 3: return 5;
            case 4: return 6;
            case 5: return 6;
            case 6: return 7;
            case 7: return 9;
            case 8: return 25;
            case 9: return 14;
            case 10: return 15;
            case 11: return 16;
            case 12: return 17;
            case 13: return 9;
            case 14: return 20;
            case 15: return 18;
        } return 1;
    }
    protected void lakeExpand() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 2; x<WIDTH-2;x++) for (int y = 2; y<HEIGHT-2;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.WATER) candidates.add(cell);
        }
        int[] cells;
        if (Math.random()>0.5) cells = PathFinder.NEIGHBOURS8; else cells = PathFinder.NEIGHBOURS4;
        for (int cellchosen : candidates) for (int i : cells) if (Math.random()>0.4f) this.map[cellchosen+i] = Terrain.WATER;
    }
    protected void createIslands(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 2; x<WIDTH-2;x++) for (int y = 2; y<HEIGHT-2;y++){
            int cell = x+WIDTH*y;
            if (this.map[cell] == Terrain.WATER) candidates.add(cell);
        }
        for (int cellchosen : candidates) if (Math.random()>0.98f) for (int i : PathFinder.NEIGHBOURS8) if (Math.random()>0.7f) this.map[cellchosen+i] = Terrain.HIGH_GRASS;
    }
    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this,0,0,61,61, Terrain.WALL);
        Painter.fillEllipse(this,1,1,59,59,Terrain.EMPTY);

        //lake
        this.map[30+30*WIDTH]=Terrain.WATER;
        for (int times = 0;times < 26; times++) try {
            lakeExpand();
        } catch (ArrayIndexOutOfBoundsException ignored) {};
        createIslands();

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random grass
            int cell = x+WIDTH*y;

            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.GRASS;
            }
            //tall grass
            if (Math.random()>0.9) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.HIGH_GRASS;
                if (this.map[cell+1]==Terrain.EMPTY) this.map[cell+1]=Terrain.HIGH_GRASS;
                if (this.map[cell-1]==Terrain.EMPTY) this.map[cell-1]=Terrain.HIGH_GRASS;
                if (this.map[cell+WIDTH]==Terrain.EMPTY) this.map[cell+WIDTH]=Terrain.HIGH_GRASS;
                if (this.map[cell-WIDTH]==Terrain.EMPTY) this.map[cell-WIDTH]=Terrain.HIGH_GRASS;
            }

        }

        Painter.fillEllipse(this, 24, 1,15,9,Terrain.EMPTY);
        Painter.fill(this, 25, 1,11,1,Terrain.EMPTY_SP);
        Painter.fill(this, 25, 2,11,1,Terrain.PEDESTAL);
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
            if (this.passable[m]&&this.map[m]!=Terrain.WATER&&this.map[m]!=Terrain.EMPTY&&this.map[m]!=Terrain.PEDESTAL) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(new ElixirOfToxicEssence(),Random.element(candidates));
        this.drop(new ElixirOfToxicEssence(),Random.element(candidates));
        this.drop(new ElixirOfToxicEssence(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        this.drop(new SpawnerCannon(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new SpawnerCannon(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new SpawnerWand(),Random.element(candidates));
        this.drop(new ThrowingStone(),Random.element(candidates));
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.WATER) candidates.add(m);
        }
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));


        for (int m = 61*15; m<WIDTH*HEIGHT- WIDTH;m++){
            if (this.map[m]==Terrain.GRASS) {
                RotLasher rotLasher = new RotLasher();
                rotLasher.pos = m;
                GameScene.add(rotLasher);
                m+=2*WIDTH+Random.Int(2,10);
            }
        }
        for (int m = 61*20; m<WIDTH*HEIGHT- WIDTH;m++){
            if (this.map[m]==Terrain.WATER) {
                Piranha piranha = new Piranha();
                piranha.pos = m;
                GameScene.add(piranha);
                piranha.state = piranha.SLEEPING;
                m+=WIDTH*6+Random.Int(1,36);
            }
        }

        TowerCrossbow1 tower1 = new TowerCrossbow1();
        tower1.pos = amuletCell+1;
        GameScene.add(tower1);

        TowerCrossbow1 tower2 = new TowerCrossbow1();
        tower2.pos = amuletCell-1;
        GameScene.add(tower2);

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 70;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);

        if (wave == 4){


            EnemyPortal.createEnemyPortal(WIDTH*HEIGHT/2, 30);
            Camera.main.panFollow(Char.findChar(WIDTH*HEIGHT/2).sprite,1f);
            GameScene.updateFog(WIDTH*HEIGHT/2, 5);

            WndDialogueWithPic.dialogue(new AlmostEmptySprite(), " ",
                    new String[]{
                            Messages.get(RatKing.class, "l4w4end1"),
                            Messages.get(RatKing.class, "l4w4end2")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE,
                    });
        }
        if (wave == 7){
            ArrayList<Runnable> r = new ArrayList<>();
            for (int i = 0; i < 4;i++)r.add(null);
            r.add(4, new Runnable() {
                @Override
                public void run() {
                    new ScrollOfRatLegion().collect();
                    new ScrollOfAnimation().collect();
                    Sample.INSTANCE.play(Assets.Sounds.DEWDROP);
                }
            });
            ArrayList<Runnable> r2 = new ArrayList<>();
            for (int i = 0; i < 6;i++)r2.add(null);
            r2.add(6, new Runnable() {
                @Override
                public void run() {
                    new ScrollOfRatLegion().collect();
                    new ScrollOfAnimation().collect();
                    Sample.INSTANCE.play(Assets.Sounds.DEWDROP);
                }
            });
            boolean theresaportal = false;
            for (Mob mob : mobs){
                if (mob instanceof EnemyPortal) theresaportal = true;
            }

            if (theresaportal) WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(BossRatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l4w7end1"),
                            Messages.get(RatKing.class, "l4w7end2"),
                            Messages.get(RatKing.class, "l4w7end3"),
                            Messages.get(RatKing.class, "l4w7end4"),
                            Messages.get(RatKing.class, "l4w7end5"),
                            Messages.get(RatKing.class, "l4w7end6"),
                            Messages.get(RatKing.class, "l4w7end7")
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                    }, WndDialogueWithPic.WndType.NORMAL, r);
            else WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(BossRatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l4w7end1"),
                            Messages.get(RatKing.class, "l4w7end2noportal"),
                            Messages.get(RatKing.class, "l4w7end3noportal"),
                            Messages.get(RatKing.class, "l4w7end4noportal"),
                            Messages.get(RatKing.class, "l4w7end5noportal"),
                            Messages.get(RatKing.class, "l4w7end6noportal"),
                            Messages.get(RatKing.class, "l4w7end7noportal"),
                            Messages.get(RatKing.class, "l4w7end8noportal")
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                    }, WndDialogueWithPic.WndType.NORMAL, r2);
        }

    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.DOWN, 1);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
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
                group.add( new Arena4.Sink( i ) );
            }
        }
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                Arena4.WaterParticle p = (Arena4.WaterParticle)emitter.recycle( Arena4.WaterParticle.class );
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

            color( ColorMath.random( 0xb6ccc2, 0x3b6653 ) );
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
