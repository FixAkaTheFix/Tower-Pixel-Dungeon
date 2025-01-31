package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall3;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.LiquidMetal;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfHoneyedHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.items.quest.CorruptedOoze;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAntiMagic;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfDemonicSkull;
import com.fixakathefix.towerpixeldungeon.items.spells.AquaBlast;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAggression;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfBlink;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfDeepSleep;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Blindweed;
import com.fixakathefix.towerpixeldungeon.plants.Swiftthistle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena9 extends Arena{

    /**
     * A short puzzle stage
     */

    {
        name = "Guard post";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 50;
        WIDTH = 27;
        HEIGHT = 27;
        startLvl = 8;
        startGold = 100;
        waveCooldownNormal = 3;
        waveCooldownBoss = 20;
        arenaDepth = 9;

        maxWaves=10;



        towerShopKeeper = null;
        normalShopKeeper = null;


        amuletCell = 5 + WIDTH * 20;
        exitCell = amuletCell;
        towerShopKeeperCell = 5 + WIDTH * 20;
        normalShopKeeperCell = 5 + WIDTH * 20;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.DESCENT},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = Random.oneOf(new Thief()); break;
            case 2:
                mob = Random.oneOf(new Thief(), new Gnoll()); break;
            case 3:
                mob = Random.oneOf(new Bandit(), new Thief()); break;
            case 4:
                mob = Random.oneOf(new Guard()); break;
            case 5: {
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GnollTrickster();
                } else mob = Random.oneOf(new Thief());
                break;
            }
            case 6:
                mob = Random.oneOf(new Wraith(), new Bandit()); break;
            case 7:
                mob = new Shaman.RedShaman(); break;
            case 8:
                mob = new MagiCrab(); break;
            case 9:
                mob = new Brute(); break;
            case 10: {
                mob = new Snake();
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
            case 1: return 5;
            case 2: return 6;
            case 3: return 7;
            case 4: return 5;
            case 5: return 10;
            case 6: return 12;
            case 7: return 15;
            case 8: return 10;
            case 9: return 10;
            case 10: return 100;
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
    protected boolean build() {
        setSize(WIDTH, HEIGHT);

        map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 1, 25, 1, 1, 1, 1, 25, 1, 1, 1, 1, 25, 1, 1, 1, 4, 15, 2, 1, 2, 0, 0, 4, 4, 4, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 5, 1, 1, 1, 0, 0, 4, 4, 2, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 2, 0, 4, 4, 2, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 4, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 25, 1, 1, 1, 1, 1, 1, 1, 25, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 20, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 12, 4, 1, 1, 27, 4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 20, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 31, 1, 1, 20, 1, 4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 12, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 4, 4, 1, 1, 25, 4, 1, 20, 1, 1, 1, 4, 4, 25, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 27, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 27, 4, 1, 1, 1, 1, 20, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 27, 4, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 27, 4, 1, 1, 20, 1, 1, 1, 2, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 4, 4, 1, 1, 1, 1, 1, 1, 1, 2, 1, 4, 4, 1, 1, 1, 1, 11, 1, 1, 20, 1, 4, 4, 4, 5, 4, 4, 4, 25, 1, 1, 1, 1, 1, 1, 2, 1, 4, 4, 25, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 2, 2, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 13, 14, 14, 14, 14, 14, 13, 20, 1, 1, 1, 1, 2, 2, 1, 1, 4, 1, 10, 1, 1, 1, 1, 1, 1, 1, 1, 13, 14, 14, 14, 14, 14, 13, 1, 20, 1, 1, 2, 2, 2, 1, 1, 4, 4, 4, 20, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 14, 14, 13, 20, 1, 1, 2, 2, 15, 2, 2, 1, 4, 4, 25, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 14, 14, 13, 1, 1, 2, 2, 15, 15, 15, 2, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
        buildFlagMaps();

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        if (mode == WndModes.Modes.CHALLENGE){

                TowerWall3 defRodLeft = new TowerWall3();
                defRodLeft.sellable = false;
                defRodLeft.pos = 15 * WIDTH + 2;
                GameScene.add(defRodLeft);


                TowerWall3 defRodRight = new TowerWall3();
                defRodRight.sellable = false;
                defRodRight.pos = 15 * WIDTH + 8;
                GameScene.add(defRodRight);

        } else
        {
        for (int y = 5; y < 16;y+=10){
            TowerLightning1 defRodLeft = new TowerLightning1();
            defRodLeft.sellable = false;
            defRodLeft.pos = y * WIDTH + 2;
            GameScene.add(defRodLeft);
        }
        for (int y = 5; y < 16;y+=10){
            TowerLightning1 defRodRight = new TowerLightning1();
            defRodRight.sellable = false;
            defRodRight.pos = y * WIDTH + 8;
            GameScene.add(defRodRight);
        }
        }
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&m!=amuletCell&&distance(amuletCell,m)<3) candidates.add(m);
        }
        this.drop(TowerInfo.getTowerSpawner(slot1), Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot2), Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot3), Random.element(candidates));
        this.drop(TowerInfo.getTowerSpawner(slot4), Random.element(candidates));

        this.drop(new ScrollOfAnimation(), Random.element(candidates));
        this.drop(new ScrollOfAnimation(), Random.element(candidates));
        this.drop(new ScrollOfDemonicSkull(), Random.element(candidates));
        this.drop(new ElixirOfHoneyedHealing(), Random.element(candidates));
        this.drop(new CorruptedOoze(),Random.element(candidates));
        this.drop(new LiquidMetal(),Random.element(candidates));
        this.drop(new ScrollOfAntiMagic(),Random.element(candidates));
        this.drop(new PotionOfCleansing(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new Swiftthistle.Seed(), Random.element(candidates));
        this.drop(new Blindweed.Seed(), Random.element(candidates));
        this.drop(new AquaBlast(), Random.element(candidates));
        this.drop(new AquaBlast(), Random.element(candidates));
        this.drop(new AquaBlast(), Random.element(candidates));
        this.drop(new AquaBlast(), Random.element(candidates));
        this.drop(new StoneOfAggression(), Random.element(candidates));
        this.drop(new StoneOfDeepSleep(), Random.element(candidates));
        this.drop(new StoneOfBlink(), Random.element(candidates));

        candidates.clear();

        super.addDestinations();
    }
    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
    }


    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        super.deploymobs(wave, Direction.TOORIGHT, 1);
    }

    @Override
    public void doStuffEndwave(int wave) {
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
