package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgun2;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Honeypot;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.food.MeatPie;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.food.SmallRation;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow2Sprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena13 extends Arena {
    {

        name = "The temple";
        WIDTH = 71;
        HEIGHT = 71;
        color1 = 0x534f3e;
        color2 = 0xb9d661;
        viewDistance = 14;

        startGold = 500;
        startLvl = 11;

        maxWaves = 15;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.DOWN;

        amuletCell = 35 + WIDTH * 35;
        exitCell = amuletCell;
        towerShopKeeperCell = 30 + 29 * WIDTH;
        normalShopKeeperCell = 40 + 41 * WIDTH;

        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }

    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 2;
            case 2: return 5;
            case 3: return 3;
            case 4: return 13;
            case 5: return 8;
            case 6: return 9;
            case 7: return 10;
            case 8: return 7;
            case 9: return 8;
            case 10: return 13;
            case 11: return 7;
            case 12: return 11;
            case 13: return 15;
            case 14: return 17;
            case 15: return 15;
        }
        return 1;
    }

    public void deploymobs(int wave, Direction direction, int group) {
        switch (wave) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 10:
            case 14:
            default: {
                super.deploymobs(wave, Direction.TOOUP, 1);
                break;
            }
            case 2:
            case 4:
            case 6:
            case 8:
            case 11:
            case 13:
            case 15:{
                super.deploymobs(wave, Direction.TOODOWN, 1);
                break;
            }
            case 9: {
                super.deploymobs(wave, Direction.TOOLEFT, 1);
                break;
            }
            case 12: {
                super.deploymobs(wave, Direction.TOORIGHT, 1);
                break;
            }
        }
    }

    @Override
    protected boolean build() {


        setSize(WIDTH, HEIGHT);
        //base room
        Painter.fill(this, 0, 0, 71, 71, Terrain.WALL);
        Painter.fill(this, 1, 1, 69, 69, Terrain.EMPTY);

        //corners
        Painter.fillEllipse(this, 0, 0, 25, 25, Terrain.WATER);
        Painter.fillEllipse(this, 0, 15, 10, 10, Terrain.WATER);
        Painter.fillEllipse(this, 16, 0, 8, 8, Terrain.WATER);
        Painter.fill(this, 0, 0, 71, 1, Terrain.WALL);
        Painter.fill(this, 0, 0, 1, 71, Terrain.WALL);
        Painter.fill(this, 70, 0, 1, 71, Terrain.WALL);
        Painter.fill(this, 0, 70, 71, 1, Terrain.WALL);


        Painter.fill(this, 0, 0, 20, 20, Terrain.WALL);
        Painter.fill(this, 51, 51, 20, 20, Terrain.WALL);
        Painter.fill(this, 0, 42, 25, 29, Terrain.WALL);
        Painter.fill(this, 46, 0, 25, 29, Terrain.WALL);


        //spawn
        if (mode != WndModes.Modes.CHALLENGE) Painter.fill(this, 25, 28, 21, 15, Terrain.WALL);
        Painter.fill(this, 26, 29, 19, 13, Terrain.EMPTY_SP);

        Painter.fill(this, 1, 36, 26, 6, Terrain.EMPTY_SP);
        Painter.fill(this, 45, 29, 25, 6, Terrain.EMPTY_SP);

        for (int x = 53; x < 70; x += 3) {
            Painter.set(this, x, 35, Terrain.STATUE_SP);
        }

        for (int x = 17; x > 1; x -= 3) {
            Painter.set(this, x, 35, Terrain.STATUE_SP);
        }

        for (int x = 28; x < 33; x += 2) {
            Painter.set(this, x, 28, Terrain.STATUE_SP);
        }
        Painter.fill(this, 27, 29,1,2, Terrain.WALL);
        for (int x = 38; x < 43; x += 2) {
            Painter.set(this, x, 42, Terrain.STATUE_SP);
        }
        Painter.fill(this, 43, 40,1,2, Terrain.WALL);

        Painter.fill(this, 31, 31, 9,9, Terrain.WATER);
        Painter.fill(this, 32, 32, 7,7, Terrain.EMPTY_SP);
        Painter.fill(this, 33, 33, 5,5, Terrain.WATER);
        Painter.fill(this, 34, 34, 3,3, Terrain.EMPTY_SP);

        Painter.set(this, normalShopKeeperCell-3, Terrain.WALL);
        Painter.set(this, towerShopKeeperCell+3, Terrain.WALL);
        Painter.set(this, normalShopKeeperCell-3-WIDTH, Terrain.WALL);
        Painter.set(this, towerShopKeeperCell+3+WIDTH, Terrain.WALL);

        //random rocks around temple walls
        //this forces more rocks on the east/south side
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.WALL || this.map[x + WIDTH * y] == Terrain.EMPTY_SP)
                    for (int i : PathFinder.NEIGHBOURS8) {
                        if (this.map[x + i + WIDTH * y] == Terrain.EMPTY && Math.random() > 0.6)
                            this.map[x + i + WIDTH * y] = Terrain.EMPTY_DECO;
                    }
            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.WALL || this.map[x + WIDTH * y] == Terrain.EMPTY_SP || this.map[x + WIDTH * y] == Terrain.EMPTY_DECO)
                    for (int i : PathFinder.NEIGHBOURS8) {
                        if (this.map[x + i + WIDTH * y] == Terrain.EMPTY && Math.random() > 0.90)
                            this.map[x + i + WIDTH * y] = Terrain.EMPTY_DECO;
                    }
            }

        //random grass, with stone prophecies covered in grass fully
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.EMPTY && Math.random() > 0.89)
                    this.map[x + WIDTH * y] = Terrain.GRASS;
            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.GRASS) for (int i : PathFinder.NEIGHBOURS8) {
                    if (this.map[x + i + WIDTH * y] == Terrain.EMPTY && Math.random() > 0.89)
                        this.map[x + i + WIDTH * y] = Terrain.GRASS;
                }
            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.GRASS) for (int i : PathFinder.NEIGHBOURS8) {
                    if (this.map[x + i + WIDTH * y] == Terrain.EMPTY && Math.random() > 0.89)
                        this.map[x + i + WIDTH * y] = Terrain.GRASS;
                }
            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.GRASS && Math.random() > 0.7) {
                    this.map[x + WIDTH * y] = Terrain.HIGH_GRASS;
                }
            }

        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.GRASS && Math.random() > 0.90 && this.distance(x + WIDTH * y, amuletCell) > 25) {
                    this.map[x + WIDTH * y] = Terrain.WALL_DECO;
                    for (int i : PathFinder.NEIGHBOURS8) {
                        if (this.map[x + i + WIDTH * y] == Terrain.EMPTY || this.map[x + i + WIDTH * y] == Terrain.GRASS)
                            this.map[x + i + WIDTH * y] = Terrain.HIGH_GRASS;
                    }
                }
            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.HIGH_GRASS)
                    for (int i : PathFinder.NEIGHBOURS8) {
                        if (this.map[x + i + WIDTH * y] == Terrain.EMPTY)
                            this.map[x + i + WIDTH * y] = Terrain.GRASS;
                    }

            }
        for (int x = 1; x < 70; x++)
            for (int y = 1; y < 70; y++) {
                if (this.map[x + WIDTH * y] == Terrain.HIGH_GRASS && Math.random() > 0.8) {
                    this.map[x + WIDTH * y] = Terrain.STATUE;
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
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.map[m] == Terrain.EMPTY_SP) candidates.add(m);
        }
        this.drop(new Honeypot(), Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(), Random.element(candidates));
        this.drop(new MeatPie(), Random.element(candidates));
        this.drop(new Honeypot(), Random.element(candidates));
        this.drop(new PotionOfHealing(), Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(), Random.element(candidates));
        this.drop(new MeatPie(), Random.element(candidates));
        this.drop(new Honeypot(), Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(), Random.element(candidates));
        this.drop(new MeatPie(), Random.element(candidates));
        this.drop(new MysteryMeat(), Random.element(candidates));

        this.drop(Generator.random(Generator.Category.ARMOR).identify(), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WEAPON).identify(), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.CHEST;


        candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.map[m] == Terrain.EMPTY) candidates.add(m);
        }
        this.drop(new Honeypot(), Random.element(candidates)).type = Heap.Type.REMAINS;
        this.drop(new PotionOfHealing(), Random.element(candidates)).type = Heap.Type.REMAINS;
        this.drop(new ScrollOfMirrorImage(), Random.element(candidates)).type = Heap.Type.REMAINS;


        super.addDestinations();
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        TowerDartgun2 t1 = new TowerDartgun2();
        t1.pos = 26 + 29 * WIDTH;
        GameScene.add(t1);
        TowerDartgun2 t2 = new TowerDartgun2();
        t2.pos = 44 + 41 * WIDTH;
        GameScene.add(t2);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 300;
        Dungeon.gold += goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.SEWERS_BOSS},
                new float[]{1},
                false);
    }


    @Override
    public String tileName(int tile) {
        switch (tile) {
            case Terrain.GRASS:
                return Messages.get(CavesLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_name");
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "water_name");
            case Terrain.WALL_DECO:
                return Messages.get(CavesLevel.class, "temple_brick_name");
            default:
                return super.tileName(tile);
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return Messages.get(CavesLevel.class, "entrance_desc");
            case Terrain.EXIT:
                return Messages.get(CavesLevel.class, "exit_desc");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_desc");
            case Terrain.WALL_DECO:
                return Random.oneOf(
                        Messages.get(CavesLevel.class, "temple_brick_desc_1"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_2"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_3"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_4"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_5"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_6"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_7"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_8"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_9"),
                        Messages.get(CavesLevel.class, "temple_brick_desc_10")
                );
            case Terrain.BOOKSHELF:
                return Messages.get(CavesLevel.class, "bookshelf_desc");
            case Terrain.EMPTY_DECO:
                return Messages.get(CavesLevel.class, "temple_floor_desc");
            default:
                return super.tileDesc(tile);
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        return visuals;
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_TEMPLE;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
    }

    public static class GoblinCrossbow extends TowerCrossbow1 {

        {
            spriteClass = TowerCrossbow2Sprite.class;
            state = HUNTING;
            alignment = Alignment.ENEMY;

            //no loot or exp
            maxLvl = 0;

            damageMin = 3;

            //a bit more tough than those of a gnoll village
            HT = HP = 35;
        }

        @Override
        public CharSprite sprite() {
            CharSprite sprite1 = super.sprite();
            sprite1.rm = sprite1.bm = 0.7f;
            return sprite1;
        }

        @Override
        public void updateSpriteState() {
            super.updateSpriteState();
        }


        @Override
        public float spawningWeight() {
            return 0;
        }

        @Override
        public boolean interact(Char c) {
            return false;
        }
    }

}