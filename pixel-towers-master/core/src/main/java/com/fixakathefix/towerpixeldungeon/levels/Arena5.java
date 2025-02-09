package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FireImbue;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GreatCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Ripple;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.artifacts.RoseSeed;
import com.fixakathefix.towerpixeldungeon.items.bombs.Firebomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.ShockBomb;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLevitation;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.potions.brews.CausticBrew;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfDragonsBlood;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfDragonsBreath;
import com.fixakathefix.towerpixeldungeon.items.quest.CorpseDust;
import com.fixakathefix.towerpixeldungeon.items.quest.CorruptedOoze;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAntiMagic;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfHolyNova;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Starflower;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.GooSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
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

    /**
     * The boss level, with its map reworked and loot rerolled. A big exploration map, with a ghost seed in the bushes.
     * introduces the player to the vast capabilities of Pixel Towers? the OOZE, some lore. Also it is the longest stage of all.
     */
    {
        name = "Caustic depths";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 101;
        HEIGHT = 71;

        startGold = 2000;
        startLvl = 5;

        maxWaves= 25;
        arenaDepth = 5;

        amuletCell = 35 + WIDTH*35;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 7 - WIDTH*4;
        normalShopKeeperCell = amuletCell- 7 + WIDTH*4;

        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.DOWN;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;

        waveCooldownBoss = 200;
        waveCooldownNormal = 2;
    }


    @Override
    public void playLevelMusic() {
        Music.INSTANCE.play(Assets.Music.CAVES_BOSS, true);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = new MagiCrab(); break;
            case 2:
                mob = Random.oneOf(new Snake(),new Rat()); break;
            case 3:
                mob = new Slime(); break;
            case 4:
                mob = new Slime(); break;
            case 5:
                mob = new CausticSlime(); break;
            case 6:
                mob = new Goo(); break;
            case 7:
                mob = new CausticSlime(); break;
            case 8:
                mob = new CausticSlime(); break;
            case 9:
                mob = Random.oneOf(new Slime(),  new CausticSlime()); break;
            case 10:
                mob = Random.oneOf(new Gnoll(), new GnollThrower()); break;
            case 11:
                mob = new Slime(); break;
            case 12:
                mob = new Crab(); break;
            case 13:
                mob = Random.oneOf(new CausticSlime(), new Slime()); break;
            case 14:
                mob = Random.oneOf(new HermitCrab()); break;
            case 15:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Goo();
                } else mob = new CausticSlime();
                break;
            case 16:
                mob = Random.oneOf(new Gnoll(), new GnollThrower(), new Albino(), new Rat(), new CausticSlime(), new CausticSlime()); break;
            case 17:
                mob = new Crab(); break;
            case 18:
                mob = new MagiCrab(); break;
            case 19:
                mob = Random.oneOf(new HermitCrab(), new CausticSlime()); break;
            case 20: if (!bossSpawned) {
                bossSpawned = true;
                mob = new GnollTrickster();
            } else mob = new MagiCrab();
                break;
            case 21:
                mob = new Gnoll(); break;
            case 22:
                mob = new Crab(); break;
            case 23:
                mob = Random.oneOf(new CausticSlime()); break;
            case 24:
                mob = new HermitCrab(); break;
            case 25:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new GreatCrab();
                } else mob = new Goo();
                break;
            case 8055:
                mob = new CausticSlime(); break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 1;
            case 2: return 7;
            case 3: return 3;
            case 4: return 5;
            case 5: return 7;
            case 6: return 2;
            case 7: return 13;
            case 8: return 13;
            case 9: return 14;
            case 10: return 21;
            case 11: return 19;
            case 12: return 20;
            case 13: return 20;
            case 14: return 12;
            case 15: return 25;
            case 16: return 31;
            case 17: return 27;
            case 18: return 22;
            case 19: return 20;
            case 20: return 27;
            case 21: return 37;
            case 22: return 37;
            case 23: return 37;
            case 24: return 22;
            case 25: return 6;
            case 8055: return 2;

        } return 1;
    }

    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room

        Painter.fill(this, 0,0, 101, 71, Terrain.WALL);
        Painter.fill(this, 1,1, 99, 69, Terrain.EMPTY);

        Painter.fill(this,24, 31, 9,9, Terrain.EMPTY_SP);
        Painter.fill(this,0, 33, 25,5, Terrain.EMPTY_SP);

        Painter.fill(this,25, 32, 7,1, Terrain.PEDESTAL);
        Painter.fill(this,25, 38, 7,1, Terrain.PEDESTAL);

        Painter.fill(this, 1,5, 42, 26, Terrain.WALL);
        Painter.fill(this, 2,6, 40, 24, Terrain.EMPTY);
        Painter.fill(this, 30,25, 5, 3, Terrain.EMPTY);
        Painter.set(this, 20, 5, Terrain.DOOR);
        Painter.fill(this, 18,5 , 1, 26, Terrain.WALL);
        Painter.fill(this, 22,5 , 1, 26, Terrain.WALL);
        Painter.set(this, 22, 11, Terrain.DOOR);
        Painter.set(this, 18, 9, Terrain.LOCKED_DOOR);
        Painter.set(this, 18, 15, Terrain.LOCKED_DOOR);
        Painter.set(this, 18, 29, Terrain.DOOR);
        Painter.fill(this, 1,11, 18, 1, Terrain.WALL);
        Painter.fill(this, 2,6, 16, 16, Terrain.WALL);


        Painter.fill(this, 46, 5, 10, 20, Terrain.WALL);
        Painter.fill(this, 55, 5, 31, 10, Terrain.WALL);
        Painter.fill(this, 56, 6, 18, 9, Terrain.EMPTY_SP);
        Painter.set(this, 57, 24, Terrain.DOOR);
        Painter.set(this, 76, 24, Terrain.DOOR);
        Painter.fill(this, 56, 15, 30, 10, Terrain.GRASS);
        Painter.fill(this, 57, 16, 28, 8, Terrain.HIGH_GRASS);
        Painter.fill(this, 58, 17, 26, 6, Terrain.GRASS);

        for (int x = 0; x < WIDTH; x++) for (int y = 0; y < HEIGHT; y++){
            if (Math.random()*(Math.abs(HEIGHT/2-y)) * 0.5f > HEIGHT/6 && this.map[x+WIDTH*y]==Terrain.EMPTY){
                Painter.set(this, x,y,Terrain.WALL);
            }//33% on the ouskirts, 0% in the middle
            else if (Math.random()>0.96 &&this.map[x+WIDTH*y]==Terrain.EMPTY && Math.abs(HEIGHT/2-y)>10){
                Painter.set(this, x,y,Terrain.GRASS);
            }
            else if (Math.random()>0.95 &&this.map[x+WIDTH*y]==Terrain.EMPTY && distance(amuletCell, x + WIDTH*y) > 15){
                Painter.set(this, x,y,Terrain.EMPTY_DECO);
            }
            else if (Math.random()>0.94 &&this.map[x+WIDTH*y]==Terrain.EMPTY && Math.abs(HEIGHT/2-y)>10){
                Painter.set(this, x,y,Terrain.WATER);
            }
        }

        for (int a = 0; a<1;a++) {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int x = 2; x < WIDTH - 2; x++)
                for (int y = 2; y < HEIGHT - 2; y++) {
                    int cell = x + WIDTH * y;
                    if (this.map[cell] == Terrain.WATER) candidates.add(cell);
                }
            int[] cells;
            if (Math.random() > 0.5) cells = PathFinder.NEIGHBOURS8;
            else cells = PathFinder.NEIGHBOURS4;
            for (int cellchosen : candidates)
                for (int i : cells)
                    if (Math.random() > 0.4f) this.map[cellchosen + i] = Terrain.WATER;

            ArrayList<Integer> candidates2 = new ArrayList<>();
            for (int x = 2; x < WIDTH - 2; x++)
                for (int y = 2; y < HEIGHT - 2; y++) {
                    int cell = x + WIDTH * y;
                    if (this.map[cell] == Terrain.GRASS) candidates2.add(cell);
                }
            int[] cells2;
            if (Math.random() > 0.5) cells2 = PathFinder.NEIGHBOURS8;
            else cells2 = PathFinder.NEIGHBOURS4;
            for (int cellchosen : candidates2)
                for (int i : cells2)
                    if (Math.random() > 0.4f && this.map[cellchosen + i] == Terrain.EMPTY) this.map[cellchosen + i] = Terrain.GRASS;
            ArrayList<Integer> candidates23 = new ArrayList<>();
            for (int x = 2; x < WIDTH - 2; x++)
                for (int y = 2; y < HEIGHT - 2; y++) {
                    int cell = x + WIDTH * y;
                    if (this.map[cell] == Terrain.EMPTY_DECO) candidates23.add(cell);
                }
            int[] cells23;
            if (Math.random() > 0.5) cells23 = PathFinder.NEIGHBOURS8;
            else cells23 = PathFinder.NEIGHBOURS4;
            for (int cellchosen : candidates23)
                for (int i : cells23)
                    if (Math.random() > 0.4f && this.map[cellchosen + i] == Terrain.EMPTY) this.map[cellchosen + i] = Terrain.EMPTY_DECO;
        }

        Painter.fill(this, 0,40, 20,26, Terrain.WALL );
        Painter.fill(this, 2,41, 17,24, Terrain.WATER );
        for (int x = 3;x<18;x+=2) for (int y = 42; y< 63; y += 2){
            Painter.set(this, x, y, Terrain.WALL_DECO);
        }
        Painter.set(this, 19, 50,Terrain.DOOR);
        Painter.set(this, 19, 56,Terrain.DOOR);
        Painter.set(this, 19, 60,Terrain.DOOR);
        Painter.set(this, 19, 62,Terrain.DOOR);
        Painter.set(this, 10, 65,Terrain.LOCKED_DOOR);

        Painter.fill(this, 23,40, 20,18, Terrain.WALL );
        Painter.fill(this, 44,50, 23,7, Terrain.WALL );
        Painter.set(this, 69,56, Terrain.DOOR);
        Painter.fill(this, 45,51, 21,5, Terrain.EMPTY_SP );
        Painter.fill(this, 47,53, 7,1, Terrain.WALL );
        Painter.fill(this, 57,53, 7,1, Terrain.WALL );
        Painter.set(this, 54, 56, Terrain.LOCKED_DOOR);

        Painter.fill(this, 78, 45, 20,20, Terrain.WALL );
        Painter.fill(this, 79, 46, 18,18, Terrain.EMPTY_SP);
        Painter.fill(this, 84, 51, 8,8, Terrain.WATER);
        Painter.fill(this, 87, 54, 2,2, Terrain.WALL);
        Painter.set(this, 78, 52,Terrain.LOCKED_DOOR);
        Painter.set(this, 78, 60,Terrain.LOCKED_DOOR);


        for (int x = 2; x < WIDTH-2; x++) for (int y = 2; y < HEIGHT-2; y++){
            ArrayList<Integer> mods = new ArrayList<>();
            int emptisnear = 0;
            for (int i : PathFinder.NEIGHBOURS8){
                if (map[x + WIDTH*y + i] == Terrain.EMPTY) emptisnear++;
            }
            if (emptisnear * Math.random()>5 &&this.map[x+WIDTH*y]==Terrain.WALL){
                mods.add(x + WIDTH*y);
            }
            for (int i : mods){
                Painter.set(this, i,Random.oneOf(Terrain.EMBERS, Terrain.BARRICADE));
            }
        }
        Painter.fill(this,36, 32, 62,1, Terrain.WATER);
        Painter.fill(this,37, 34, 60,3, Terrain.WATER);
        Painter.fill(this,36, 38, 62,1, Terrain.WATER);
        Painter.fill(this,44, 5, 1,30, Terrain.WATER);
        Painter.fill(this,21, 35, 1,20, Terrain.EMPTY_SP);

        Painter.fillDiamond(this, 43,29, 13,13, Terrain.WATER);
        Painter.fillDiamond(this, 45,31, 9,9, Terrain.EMPTY);
        Painter.fillDiamond(this, 46,32, 7,7, Terrain.WATER);
        Painter.fillDiamond(this, 47,33, 5,5, Terrain.EMPTY_SP);
        Painter.fillDiamond(this, 48,34, 3,3, Terrain.WATER);

        for (int x = 35; x < 95; x+=4){
            Painter.set(this, x, 31, Terrain.STATUE);
            Painter.set(this, x, 39, Terrain.STATUE);
        }
        Painter.fill(this, 42,27, 1,3, Terrain.EMPTY);
        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.EMPTY;

        if (mode == WndModes.Modes.CHALLENGE){
            for (int i = 0;i<WIDTH*HEIGHT;i++){
                if (map[i] == Terrain.WATER) map[i] = Terrain.EMPTY;
            }
        }

        return true;
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++) if (m<1300||m>3700){
            if (this.passable[m] && !cellAdjacentToBorderCells(m) && distance(amuletCell,m) > 15) candidates.add(m);
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
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new Gold(100),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLevitation(),Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(),Random.element(candidates));
        this.drop(new Starflower.Seed(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(new ScrollOfHolyNova(),Random.element(candidates));
        this.drop(new ScrollOfAntiMagic(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));

        this.drop(new ElixirOfToxicEssence(), Random.element(candidates));
        this.drop(new IronKey(arenaDepth),Random.element(candidates));
        this.drop(new IronKey(arenaDepth),Random.element(candidates));
        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m] == Terrain.EMPTY_SP  && !cellAdjacentToBorderCells(m)) candidates.add(m);
        }
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new Firebomb(),Random.element(candidates));
        this.drop(new Firebomb(),Random.element(candidates));
        this.drop(new ElixirOfDragonsBlood(),Random.element(candidates));
        this.drop(new ElixirOfDragonsBlood(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new Firebomb(),Random.element(candidates));
        this.drop(new ShockBomb(),Random.element(candidates));
        this.drop(new ElixirOfDragonsBlood(),Random.element(candidates));
        this.drop(new PotionOfDragonsBreath(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WEP_T1).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WEP_T2).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.MIS_T4),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.ARMOR).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WEP_T1).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WEP_T2).identify(),Random.element(candidates)).type = Heap.Type.CHEST;
        candidates.clear();

        candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m] == Terrain.HIGH_GRASS  && !cellAdjacentToBorderCells(m)) candidates.add(m);
        }
        this.drop(new RoseSeed(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
        candidates.clear();
        if (mode != WndModes.Modes.CHALLENGE){
            candidates = new ArrayList<>();
            for (int m = 0; m < WIDTH * HEIGHT; m++) {
                if (this.map[m] == Terrain.WATER && distance(amuletCell, m) > 20 && !cellAdjacentToBorderCells(m))
                    candidates.add(m);
            }
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new CorruptedOoze(), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new CorpseDust(), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new CausticBrew(), Random.element(candidates));
            this.drop(new CausticBrew(), Random.element(candidates));
            this.drop(new CausticBrew(), Random.element(candidates));
            this.drop(new PotionOfToxicGas(), Random.element(candidates));
            this.drop(new PotionOfToxicGas(), Random.element(candidates));
            this.drop(new ElixirOfToxicEssence(), Random.element(candidates));
            this.drop(new PotionOfToxicGas(), Random.element(candidates));
            this.drop(new PotionOfToxicGas(), Random.element(candidates));
            candidates.clear();
        }
        super.addDestinations();
    }

    @Override
    public void endWave() {
        int goldAdd = 100;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));

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

        if (wave==11) {
            EnemyPortal.createEnemyPortal(amuletCell - WIDTH*18, 41);
        }
        if (wave==22) {
            GooSprite sprite = new GooSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();

            Sample.INSTANCE.play(Assets.Sounds.WATER, 5, 0.2f);
            WndDialogueWithPic.dialogue(sprite, "#1???#1",
                    new String[]{
                            Messages.get(Goo.class, "defeated"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==maxWaves) {

            BossOoze ooze = new BossOoze();
            ooze.pos = 36*WIDTH+95;
            GameScene.add(ooze);
           // Camera.main.snapTo(ooze.sprite.center());
            GooSprite sprite = new GooSprite();
            Sample.INSTANCE.play(Assets.Sounds.WATER, 5, 0.2f);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE, 5, 0.2f);
            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();
            WndDialogueWithPic.dialogue(sprite, "#1THE OOZE#1",
                    new String[]{
                            Messages.get(Goo.class, "notice"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
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
        super.initNpcs();
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell+WIDTH;
        GameScene.add(tower);
    }

    @Override
    public void affectMob(Mob mob) {
        if (mob instanceof Goo || mob instanceof CausticSlime || mob instanceof BossOoze){
            Buff.affect(mob, FireImbue.class).set(1000);
        }
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.TOORIGHT, 1);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS_OOZY;
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
