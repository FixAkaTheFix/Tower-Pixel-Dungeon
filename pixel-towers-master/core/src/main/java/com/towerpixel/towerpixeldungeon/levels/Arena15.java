package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.Item.updateQuickslot;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.blobs.Blob;
import com.towerpixel.towerpixeldungeon.actors.blobs.Fire;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerPylonBroken;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.armor.LeatherArmor;
import com.towerpixel.towerpixeldungeon.items.armor.MailArmor;
import com.towerpixel.towerpixeldungeon.items.armor.ScaleArmor;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Firebomb;
import com.towerpixel.towerpixeldungeon.items.bombs.Flashbang;
import com.towerpixel.towerpixeldungeon.items.food.Food;
import com.towerpixel.towerpixeldungeon.items.food.MeatPie;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfMindVision;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfShielding;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.towerpixel.towerpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.towerpixel.towerpixeldungeon.items.quest.RatSkull;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCannon;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerGrave;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.towerpixel.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena15 extends Arena{

    {

        name = "Warehouses";
        WIDTH = 101;
        HEIGHT = 101;
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        startGold = 3000;
        startLvl = 11;

        maxWaves = 25;

        amuletCell = 51 + 101*51;
        exitCell = amuletCell;

        towerShopKeeper=null;
        normalShopKeeper=null;

        waveCooldownNormal = 1;
        waveCooldownBoss = 1;
        waterIsToxic = true;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CAVES_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public void initNpcs() {
        hero.lvl = startLvl;
        hero.updateHT(true);
        Dungeon.gold += startGold;
        updateQuickslot();
        amuletTower.pos = amuletCell;
        amuletTower.HP = amuletTower.HT;
        level = this;
        GameScene.add(amuletTower);
        this.occupyCell(amuletTower);
        Buff.affect(hero, WaveCooldownBuff.class, 200);
    }

    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        switch (wave){
            case 1: case 2: case 3:case 4: case 6:case 11:case 17:case 20: case 23: case 25:super.deploymobs(wave, Direction.TOORIGHT, 1); break;
            case 5: case 7: case 9:case 16:case 18: case 21: super.deploymobs(wave, Direction.TOOUP, 1); break;
            case 8: case 10:case 13:case 15: case 24: super.deploymobs(wave, Direction.TOODOWN, 1);break;
            case 12: case 14: case 19: case 22: super.deploymobs(wave, Direction.TOOLEFT, 1);break;
        }
        switch (wave){
            case 5:{
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(47+WIDTH*44 + i, 2, Fire.class));
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(55+WIDTH*44 + i, 2, Fire.class));
                break;
            }
            case 8:{
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(47+WIDTH*60 + i, 2, Fire.class));
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(55+WIDTH*60 + i, 2, Fire.class));
                break;
            }
            case 12:{
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(44+WIDTH*54 + i, 2, Fire.class));
                for (int i : PathFinder.NEIGHBOURS8) GameScene.add(Blob.seed(44+WIDTH*48 + i, 2, Fire.class));
                break;
            }
        }
    }
    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 1000;
        Dungeon.gold+=goldAdd;
        updateQuickslot();
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    protected boolean build() {
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, WIDTH, HEIGHT, Terrain.WALL);
        Painter.fillEllipse(this, 2, 2, WIDTH-2, HEIGHT-2, Terrain.EMPTY_SP);
        Painter.fillEllipse(this, 7, 7, WIDTH-12, HEIGHT-12, Terrain.WALL);
        Painter.fillEllipse(this, 42, 42, WIDTH-82, HEIGHT-82, Terrain.EMPTY_SP);
        Painter.fill(this, 0, 0, 30, HEIGHT, Terrain.WALL);

        //horizontal path
        Painter.fill(this,51,47, 50, 9, Terrain.EMPTY_SP);
        Painter.fill(this,51,49, 50, 5, Terrain.EMPTY);
        Painter.fill(this,51,51, 50, 1, Terrain.WATER);
        // vertical path
        Painter.fill(this,47,1, 9, HEIGHT-2, Terrain.EMPTY_SP);
        Painter.fill(this,49,1, 5, HEIGHT-2, Terrain.EMPTY);
        Painter.fill(this,51,1, 1, HEIGHT-2, Terrain.WATER);

        //amulet circle
        Painter.fillEllipse(this, 44,44, HEIGHT-86, WIDTH-86, Terrain.WATER);
        Painter.fillEllipse(this, 45,45, HEIGHT-88, WIDTH-88, Terrain.EMPTY);
        Painter.fillEllipse(this, 48,48, HEIGHT-94, WIDTH-94, Terrain.WATER);

        //empty squares
        Painter.fill(this, 58, 58, 7, 7, Terrain.EMPTY_SP);
        Painter.fill(this, 58, 39, 7, 7, Terrain.EMPTY_SP);
        Painter.fill(this, 39, 39, 7, 7, Terrain.EMPTY_SP);
        Painter.fill(this, 39, 58, 7, 7, Terrain.EMPTY_SP);

        //temp barricades
        Painter.fill(this, 46,43, 11, 1, Terrain.BARRICADE);
        Painter.fill(this, 43,46, 1, 11, Terrain.BARRICADE);
        Painter.fill(this, 46,59, 11, 1, Terrain.BARRICADE);
        //acidic pool
        Painter.fillEllipse(this, 3,46, 40, 11, Terrain.WATER);
        Painter.fillEllipse(this, 13,41, 20, 21, Terrain.WATER);



            LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

            transitions.add(exit);

            this.map[exitCell] = Terrain.EXIT;
            this.map[amuletCell] = Terrain.PEDESTAL;


        return true;
    }


    @Override
    public void addDestinations() {

        TowerPylonBroken pylon1 = new TowerPylonBroken();
        pylon1.pos = 57+57*WIDTH;
        GameScene.add(pylon1);
        Dungeon.level.occupyCell(pylon1);

        TowerPylonBroken pylon2 = new TowerPylonBroken();
        pylon2.pos = 57+45*WIDTH;
        GameScene.add(pylon2);
        Dungeon.level.occupyCell(pylon2);

        TowerPylonBroken pylon3 = new TowerPylonBroken();
        pylon3.pos = 45+57*WIDTH;
        GameScene.add(pylon3);
        Dungeon.level.occupyCell(pylon3);

        TowerPylonBroken pylon4 = new TowerPylonBroken();
        pylon4.pos = 45+45*WIDTH;
        GameScene.add(pylon4);
        Dungeon.level.occupyCell(pylon4);


        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 58; x<65;x++) for (int y = 58; y<65;y++){
            if (this.passable[x+WIDTH*y]) candidates.add(x+WIDTH*y);
        }
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfToxicGas(), Random.element(candidates));
        this.drop(new PotionOfLevitation(), Random.element(candidates));
        this.drop(new PotionOfLevitation(), Random.element(candidates));
        this.drop(new PotionOfLevitation(), Random.element(candidates));
        this.drop(new PotionOfMindVision(), Random.element(candidates));
        this.drop(new Flashbang(), Random.element(candidates));
        this.drop(new Flashbang(), Random.element(candidates));
        this.drop(new Bomb(), Random.element(candidates));
        this.drop(new Bomb(), Random.element(candidates));
        this.drop(new Bomb(), Random.element(candidates));
        this.drop(new Bomb(), Random.element(candidates));
        this.drop(new Bomb(), Random.element(candidates));
        this.drop(new Bomb.DoubleBomb(), Random.element(candidates));
        this.drop(new Bomb.DoubleBomb(), Random.element(candidates));
        this.drop(new Bomb.DoubleBomb(), Random.element(candidates));
        this.drop(new Firebomb(), Random.element(candidates));
        this.drop(new Firebomb(), Random.element(candidates));
        this.drop(new Firebomb(), Random.element(candidates));
        this.drop(new Firebomb(), Random.element(candidates));
        this.drop(new Firebomb(), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfShroudingFog(), Random.element(candidates));
        this.drop(new PotionOfStormClouds(), Random.element(candidates));
        candidates.clear();

        for (int x = 58; x<65;x++) for (int y = 39; y<46;y++){
            if (this.passable[x+WIDTH*y]) candidates.add(x+WIDTH*y);
        }
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerWall(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCannon(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerCrossbow(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerGrave(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        this.drop(new SpawnerWand(), Random.element(candidates));
        candidates.clear();
        for (int x = 39; x<46;x++) for (int y = 39; y<46;y++){
            if (this.passable[x+WIDTH*y]) candidates.add(x+WIDTH*y);
        }
        this.drop(new Food(), Random.element(candidates));
        this.drop(new Food(), Random.element(candidates));
        this.drop(new Food(), Random.element(candidates));
        this.drop(new Food(), Random.element(candidates));
        this.drop(new Food(), Random.element(candidates));
        this.drop(new Food(), Random.element(candidates));
        this.drop(new PotionOfHealing(), Random.element(candidates));
        this.drop(new PotionOfHealing(), Random.element(candidates));
        this.drop(new PotionOfHealing(), Random.element(candidates));
        this.drop(new PotionOfShielding(), Random.element(candidates));
        this.drop(new MeatPie(), Random.element(candidates));
        this.drop(new RatSkull(), Random.element(candidates));
        candidates.clear();
        for (int x = 39; x<46;x++) for (int y = 58; y<65;y++){
            if (this.passable[x+WIDTH*y]) candidates.add(x+WIDTH*y);
        }
        this.drop(new StoneOfIntuition(), Random.element(candidates));
        this.drop(new StoneOfIntuition(), Random.element(candidates));
        this.drop(new StoneOfIntuition(), Random.element(candidates));
        this.drop(new StoneOfIntuition(), Random.element(candidates));
        this.drop(new StoneOfIntuition(), Random.element(candidates));
        this.drop(new ScrollOfUpgrade(), Random.element(candidates));
        this.drop(new ScrollOfUpgrade(), Random.element(candidates));
        this.drop(new ScrollOfUpgrade(), Random.element(candidates));
        this.drop(new ScrollOfUpgrade(), Random.element(candidates));
        this.drop(new ScrollOfIdentify(), Random.element(candidates));
        this.drop(new ScrollOfIdentify(), Random.element(candidates));
        this.drop(new ScrollOfIdentify(), Random.element(candidates));
        this.drop(new ScrollOfIdentify(), Random.element(candidates));
        this.drop(new ScrollOfRemoveCurse(), Random.element(candidates));
        this.drop(new LeatherArmor(), Random.element(candidates));
        this.drop(new MailArmor(), Random.element(candidates));
        this.drop(new ScaleArmor(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T3), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T4), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates));






        super.addDestinations();
    }



    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return Messages.get(CavesLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_name");
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "watertoxic_name");
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
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "watertoxic_desc");
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
        return Assets.Environment.TILES_MINES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_TOXIC;
    }

}