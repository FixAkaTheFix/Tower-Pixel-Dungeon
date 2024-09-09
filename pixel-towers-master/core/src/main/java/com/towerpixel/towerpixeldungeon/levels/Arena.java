package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.depth;
import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.Dungeon.win;
import static com.towerpixel.towerpixeldungeon.items.Item.updateQuickslot;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Challenges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.GamesInProgress;
import com.towerpixel.towerpixeldungeon.SPDSettings;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.Freezing;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;

import com.towerpixel.towerpixeldungeon.actors.buffs.Burning;
import com.towerpixel.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.towerpixel.towerpixeldungeon.actors.buffs.Corrosion;
import com.towerpixel.towerpixeldungeon.actors.buffs.Frost;
import com.towerpixel.towerpixeldungeon.actors.buffs.GoldArmor;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Levitation;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Slow;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Albino;
import com.towerpixel.towerpixeldungeon.actors.mobs.ArmoredBrute;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bandit;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Bee;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossRatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossTroll;
import com.towerpixel.towerpixeldungeon.actors.mobs.Brute;
import com.towerpixel.towerpixeldungeon.actors.mobs.CausticSlime;
import com.towerpixel.towerpixeldungeon.actors.mobs.ChiefRat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Crab;
import com.towerpixel.towerpixeldungeon.actors.mobs.CrystalMimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM100;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM200;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM201;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMW;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWHead;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWMinion;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWWheels;
import com.towerpixel.towerpixeldungeon.actors.mobs.Elemental;
import com.towerpixel.towerpixeldungeon.actors.mobs.Eye;
import com.towerpixel.towerpixeldungeon.actors.mobs.FetidRat;
import com.towerpixel.towerpixeldungeon.actors.mobs.Ghoul;
import com.towerpixel.towerpixeldungeon.actors.mobs.Gnoll;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollBlind;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollThrower;
import com.towerpixel.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goblin;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinFat;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinGiant;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinNinja;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinSand;
import com.towerpixel.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.towerpixel.towerpixeldungeon.actors.mobs.Golem;
import com.towerpixel.towerpixeldungeon.actors.mobs.Goo;
import com.towerpixel.towerpixeldungeon.actors.mobs.GreatCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.Guard;
import com.towerpixel.towerpixeldungeon.actors.mobs.HermitCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.MagiCrab;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mimic;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.Monk;
import com.towerpixel.towerpixeldungeon.actors.mobs.Necromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.Piranha;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.actors.mobs.RipperDemon;
import com.towerpixel.towerpixeldungeon.actors.mobs.RotLasher;
import com.towerpixel.towerpixeldungeon.actors.mobs.Senior;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shaman;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shinobi;
import com.towerpixel.towerpixeldungeon.actors.mobs.Skeleton;
import com.towerpixel.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.towerpixel.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.towerpixel.towerpixeldungeon.actors.mobs.Slime;
import com.towerpixel.towerpixeldungeon.actors.mobs.Slugger;
import com.towerpixel.towerpixeldungeon.actors.mobs.Snake;
import com.towerpixel.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.Spinner;
import com.towerpixel.towerpixeldungeon.actors.mobs.Statue;
import com.towerpixel.towerpixeldungeon.actors.mobs.Succubus;
import com.towerpixel.towerpixeldungeon.actors.mobs.Swarm;
import com.towerpixel.towerpixeldungeon.actors.mobs.Thief;
import com.towerpixel.towerpixeldungeon.actors.mobs.Warlock;
import com.towerpixel.towerpixeldungeon.actors.mobs.Wraith;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGraveCrypt;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGraveElite;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.MagicMissile;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.items.Amulet;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.scenes.RankingsScene;
import com.towerpixel.towerpixeldungeon.sprites.AmuletTowerSprite;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.GoblinFatSprite;
import com.towerpixel.towerpixeldungeon.sprites.MissileSprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWall1Sprite;
import com.towerpixel.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashSet;

public class Arena extends Level {

    {
        color1 = 0x801500;
        color2 = 0xa68521;

        viewDistance = 15;

    }
    public int startGold = 5;
    public int startLvl = 6;

    /**
     * Wave system works like that:
     * wave 1, cooldown 1 (=waveCooldownNormal), wave 2, ..., cooldown time 4 (=waveCooldownBoss), wave 5 (boss or miniboss wave) etc.
     */


    public AmuletTower amuletTower = new AmuletTower();
    NewShopKeeper towerShopKeeper = new TowerShopKeeper();
    NormalShopKeeper normalShopKeeper = new NormalShopKeeper();

    public boolean shopkeepVertical = false;

    public int WIDTH = 101;
    public int HEIGHT = 101;

    public String name = "Testers' Chamber";

    int exitCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH+1;//center
    public int amuletCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH;//where is the amulet mob
    int towerShopKeeperCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH - WIDTH*3;//3 cells under the amulet for now
    int normalShopKeeperCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH - WIDTH*3-6;//3 cells under the amulet for now

    private Rect fullArena = new Rect(2,2,WIDTH-1,HEIGHT-1);//the whole arena rect
    private Rect barrierArena = new Rect(9,9,WIDTH-8,HEIGHT-8);//used for restricting, to be placed around the playerArena, where the enemies can't spawn
    private Rect playerArena = new Rect(10,10,WIDTH-9,HEIGHT-9);//places with no barriers, where the player can walk

    public final int cornersize = 38;//width and length of a cornercube
    public final int pathWidth = 2;

    public Rect upleftcorner = new Rect(2,2,2+cornersize,2 + cornersize);//TODO change the logic from cornersize to centersize
    public Rect downleftcorner = new Rect(2,HEIGHT - 1 - cornersize,2 + cornersize,HEIGHT-1);
    public Rect uprightcorner = new Rect(WIDTH - 1 - cornersize,2,WIDTH-1,2 + cornersize);
    public Rect downrightcorner = new Rect(WIDTH - 1 - cornersize,HEIGHT - 1 - cornersize,WIDTH - 1,HEIGHT - 1);

    public Rect horizontalPath = new Rect(2, (int) (Math.round(HEIGHT*0.5) - pathWidth),WIDTH - 1, (int) (Math.round(HEIGHT*0.5) + pathWidth));

    public Rect verticalPath = new Rect((int)Math.round(WIDTH*0.5)-pathWidth,2,(int)Math.round(WIDTH*0.5)+pathWidth, 2);


    public Rect middle = new Rect(2 + cornersize, 2 + cornersize, WIDTH - 1 - cornersize, HEIGHT - 1 - cornersize);
    public Rect middle1less = new Rect(3 + cornersize, 3 + cornersize, WIDTH - 2 - cornersize, HEIGHT - 2 - cornersize);//a shape inside the middle rect

    public HashSet<Rect> deployingRects = new HashSet<>();




    /**
     * Stuff you can change probably. All turns handled by the amulet.
     */

    public int maxWaves = 15;

    public boolean waterIsToxic = false;




    @Override
    public void playLevelMusic() {
        if (locked){
            Music.INSTANCE.play(Assets.Music.CAVES_BOSS, true);
            return;
        }
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.SEWERS_BOSS},
                new float[]{1},
                false);
    }



    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_HALLS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }

    @Override
    protected boolean build() {




        setSize(WIDTH,HEIGHT);

        //base room
        Painter.fill(this, fullArena, Terrain.EMPTY);
        Painter.fill(this, barrierArena, Terrain.ADDITIONALBARRIER);
        Painter.fill(this, playerArena, Terrain.EMPTY);

        Painter.fill(this, upleftcorner, Terrain.WALL);
        Painter.fill(this, uprightcorner, Terrain.WALL);
        Painter.fill(this, downleftcorner, Terrain.WALL);
        Painter.fill(this, downrightcorner, Terrain.WALL);

        Painter.fill(this, middle, Terrain.EMPTY_SP);
        Painter.fillEllipse(this, middle1less, Terrain.EMPTY);



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);


        exit.set(Math.round(WIDTH*0.5f),Math.round(HEIGHT*0.5f),Math.round(WIDTH*0.5f),Math.round(HEIGHT*0.5f));//under the amulet so the hero may pathetically leave after it is destroyed
        transitions.add(exit);
        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    /** Mob deploying system
     * On a wave start choose a random cell which is canDeployOnCell, choose a random of some mobs and summon them, so each run is kind of unique.
     * for
     *
     */

    public int waveLast = 100;
    public int[] wavePrepareTime = new int[waveLast+1];//+1 for last wave existing; Cooldown 0 being the pre-start cooldown

    public int waveCooldownNormal = 20;
    public int waveCooldownBoss   = 50;

    public int mobsToDeploy(int wave){//amount of mobs deployed
        switch (Dungeon.depth){
            case 1: switch (wave){
                case 1: return 3;
                case 2: return 5;
                case 3: return 8;
                case 4: return 10;
                case 5: return 8;
                case 6: return 10;
                case 7: return 25;
                case 8: return 20;
                case 9: return 13;
                case 10: return 17;
                case 11: return 18;
                case 12: return 19;
                case 13: return 21;
                case 14: return 70;
                case 15: return 50;
            }

            case 2: switch (wave){
                case 1: return 4;
                case 2: return 7;
                case 3: return 8;
                case 4: return 9;
                case 5: return 3;
                case 6: return 12;
                case 7: return 17;
                case 8: return 15;
                case 9: return 15;
                case 10: return 19;
                case 11: return 2;
                case 12: return 17;
                case 13: return 17;
                case 14: return 10;
                case 15: return 21;
                case 16: return 32;
                case 17: return 21;
                case 18: return 24;
                case 19: return 15;
                case 20: return 1;
            }
            case 3: switch (wave){
                case 1: return 3;
                case 2: return 7;
                case 3: return 10;
                case 4: return 6;
                case 5: return 13;
                case 6: return 17;
                case 7: return 25;
                case 8: return 24;
                case 9: return 36;
                case 10: return 39;
                case 11: return 42;
                case 12: return 13;
                case 13: return 6;
                case 14: return 5;
                case 15: return 100;
            }
            case 4: switch (wave){
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
            }
            case 5: switch (wave){
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

            }
            case 6: switch (wave){
                case 1: return 3;
                case 2: return 1;
                case 3: return 5;
                case 4: return 10;
                case 5: return 7;
                case 6: return 10;
                case 7: return 11;
                case 8: return 10;
                case 9: return 12;
                case 10: return 12;
                case 11: return 13;
                case 12: return 8;
                case 13: return 15;
                case 14: return 12;
                case 15: return 10;
                case 16: return 8;
                case 17: return 12;
                case 18: return 10;
                case 19: return 15;
                case 20: return 20;

            }
            case 7: switch (wave){
                case 1: return 3;
                case 2: return 3;
                case 3: return 10;
                case 4: return 7;
                case 5: return 4;
                case 6: return 9;
                case 7: return 9;
                case 8: return 30;
                case 9: return 5;
                case 10: return 3;
                case 11: return 12;
                case 12: return 12;
                case 13: return 16;
                case 14: return 12;
                case 15: return 20;
            }
            case 8: switch (wave){
                case 1: return 2;
                case 2: return 10;
                case 3: return 8;
                case 4: return 10;
                case 5: return 6;
                case 6: return 11;
                case 7: return 16;
                case 8: return 14;
                case 9: return 17;
                case 10: return 24;
                case 11: return 25;
                case 12: return 29;
                case 13: return 25;
                case 14: return 10;
                case 15: return 30;
                case 16: return 40;
                case 17: return 42;
                case 18: return 15;
                case 19: return 12;
                case 20: return 20;
                case 8055: return 1;
            }
            case 9: switch (wave){
                case 1: return 1;
                case 2: return 4;
                case 3: return 5;
                case 4: return 6;
                case 5: return 4;
                case 6: return 8;
                case 7: return 9;
                case 8: return 10;
                case 9: return 11;
                case 10: return 10;
            }
            case 10: switch (wave){
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
                case 14: return 18;
                case 15: return 18;
                case 16: return 50;
                case 17: return 13;
                case 18: return 90;
                case 19: return 20;
                case 20: return 10;
                case 8055: return 1;
            }
            case 11: switch (wave){
                case 1: return 20;
                case 2: return 4;
                case 3: return 17;
                case 4: return 6;
                case 5: return 8;
                case 6: return 8;
                case 7: return 20;
                case 8: return 12;
                case 9: return 30;
                case 10: return 15;
                case 11: return 8;
                case 12: return 15;
                case 13: return 40;
                case 14: return 13;
                case 15: return 25;
            }
            case 12: switch (wave){
                case 1: return 10;
                case 2: return 10;
                case 3: return 12;
                case 4: return 15;
                case 5: return 10;
                case 6: return 30;
                case 7: return 21;
                case 8: return 4;
                case 9: return 22;
                case 10: return 40;
            }
            case 13: switch (wave){
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
            case 14: switch (wave){
                case 1: return 10;//gobs
                case 2: return 20;//gobs
                case 3: return 20;//gobs and shaman
                case 4: return 20;//gobs and gigas
                case 5: return 11;//gigas
                case 6: return 15;//shamans, gobs and gigas
                case 7: return 20;//ninja gobs
                case 8: return 41;//gobs and sand gobs
                case 9: return 20;//shamans and gigas
                case 10: return 15;//gigas and a troll
                case 11: return 70;//gobs
                case 12: return 75;//gobs and sand gobs
                case 13: return 40;//gigas
                case 14: return 50;//gigas and shamans
                case 15: return 7;//trolls
            }
            case 15: switch (wave){
                case 1: return 5;
                case 2: return 15;
                case 3: return 7;
                case 4: return 2;
                case 5: return 25;
                case 6: return 20;
                case 7: return 20;
                case 8: return 16;
                case 9: return 30;
                case 10: return 35;
                case 11: return 25;
                case 12: return 15;
                case 13: return 50;
                case 14: return 22;
                case 15: return 20;
                case 16: return 8;
                case 17: return 1;
                case 18: return 30;
                case 19: return 40;
                case 20: return 2;
                case 21: return 10;
                case 22: return 40;
                case 23: return 70;
                case 24: return 40;
                case 25: return 3;
            }
            case 16: switch (wave) {
                case 1:
                    return 20;
                case 2:
                    return 6;
                case 3:
                    return 25;
                case 4:
                    return 7;
                case 5:
                    return 20;
                case 6:
                    return 13;
                case 7:
                    return 40;
                case 8:
                    return 10;
                case 9:
                    return 25;
                case 10:
                    return 100;
                case 11:
                    return 30;
                case 12:
                    return 40;
                case 13:
                    return 100;
                case 14:
                    return 23;
                case 15:
                    return 50;
            }
            case 17: switch (wave){
                case 1: return 5;
                case 2: return 1;
                case 3: return 10;
                case 4: return 2;
                case 5: return 2;
                case 6: return 15;
                case 7: return 3;
                case 8: return 3;
                case 9: return 4;
                case 10: return 25;
                case 11: return 5;
                case 12: return 6;
                case 13: return 7;
                case 14: return 5;
                case 15: return 40;
                case 16: return 6;
                case 17: return 10;
                case 18: return 11;
                case 19: return 13;
                case 20: return 17;
                case 21: return 21;
                case 22: return 25;
            }
            case 19: switch (wave % 4){
                    case 0: return wave * 3;
                    case 1: return wave * 3;
                    case 2: return wave;
                    case 3: return (int) (wave * 1.5f);
            }
            case 20: switch (wave){
                case 1: return 70;
                case 2: return 15;
                case 3: return 7;
                case 4: return 15;
                case 5002:
                case 5003:
                case 5006:
                case 5007:
                case 5008: return 17;
                case 5001:
                case 5004:
                case 5005: return 14;
                case 5009: return 8;
                case 5010: return 120;
                case 6: return 25;
                case 7: return 60;
                case 8: return 23;
                case 9: return 30;
                case 10001: return 31;
                case 10002: return 32;
                case 10003: return 33;
                case 11: return 38;
                case 12: return 39;
                case 13: return 25;
                case 14: return 20;
                case 15: return 43;
                case 16: return 20;
                case 17001: return 40;
                case 17002: return 40;
                case 17003: return 40;
                case 18: return 50;
                case 19: return 30;
                case 20: return 50;
                case 21: return 35;
                case 22: return 25;
                case 23: return 55;
                case 24: return 45;
                case 25: return 200;
            }

        }
        return 1;
    }

    public void doStuffStartwave(int wave){}/// does something in the beginning of each wave, overriden in arenas that need it.
    public void doStuffEndwave(int wave){}/// does something in the end of each wave
    public void addDestinations(){}//for stuff placing around the map
    public Mob chooseMob(int wave){ //Chooses a mob to spawn on a wave;
        Mob mob = new Rat();


        switch (Dungeon.depth){
            case 1:{
                switch (wave){
                    case 1:
                        mob = new Albino(); break;
                    case 2:
                        mob = Random.oneOf(new Rat(), new Snake(), new GnollBlind()); break;
                    case 3:
                        mob = Random.oneOf(new Gnoll(), new GnollBlind()); break;
                    case 4:
                        mob = Random.oneOf(new Rat(), new Rat(), new Snake()); break;
                    case 5:
                        mob = Random.oneOf(new Snake()); break;
                    case 6:
                        mob = Random.oneOf(new Albino(), new Gnoll()); break;
                    case 7:
                        mob = new Rat(); break;
                    case 8:
                        mob = Random.oneOf(new Albino()); break;
                    case 9:
                        mob = Random.oneOf(new Crab(), new Snake()); break;
                    case 10:
                        mob = Random.oneOf(new Snake(), new Gnoll(), new Crab()); break;
                    case 11:
                        mob = new GnollThrower(); break;
                    case 12:
                        mob = new Crab(); break;
                    case 13:
                        mob = Random.oneOf(new Slime(), new Crab()); break;
                    case 14:
                        mob = Random.oneOf(new Rat(), new Rat(), new Albino()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ChiefRat();
                        } else mob = new Albino();
                        break;
                    }
                }
                break;
            }

            case 2:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Rat(), new Rat(), new Albino()); break;
                    case 2:
                        mob = Random.oneOf(new Snake(),new Rat()); break;
                    case 3:
                        mob = Random.oneOf(new GnollBlind(), new Gnoll()); break;
                    case 4:
                        mob = Random.oneOf(new Rat(), new Crab()); break;
                    case 5:
                        mob = Random.oneOf(new FetidRat()); break;
                    case 6:
                        mob = Random.oneOf(new Albino(), new Gnoll()); break;
                    case 7:
                        mob = new Rat(); break;
                    case 8:
                        mob = Random.oneOf(new Albino()); break;
                    case 9:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new FetidRat();
                        } else mob = Random.oneOf(new Crab(), new Snake()); break;
                    case 10:
                        mob = Random.oneOf(new GnollBlind(), new Gnoll(), new GnollThrower()); break;
                    case 11:
                        mob = new Brute(); break;
                    case 12:
                        mob = new Crab(); break;
                    case 13:
                        mob = Random.oneOf(new CausticSlime(), new Slime()); break;
                    case 14:
                        mob = Random.oneOf(new HermitCrab()); break;
                    case 15:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ChiefRat();
                        } else mob = new Albino();
                        break;
                    case 16:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ChiefRat();
                        } else mob = Random.oneOf(new Gnoll(), new GnollThrower(), new Albino(), new Rat()); break;
                    case 17:
                        mob = new Crab(); break;
                    case 18:
                        mob = Random.oneOf(new Slime()); break;
                    case 19:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GreatCrab();
                        } else mob = new Crab();
                        break;
                    case 20:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new BossRatKing();
                        } else mob = new Rat();
                        break;
                }
                break;
            }
            case 3:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Rat()); break;
                    case 2:
                        mob = Random.oneOf(new GnollThrower(), new GnollBlind(), new Gnoll()); break;
                    case 3:
                        mob = new GnollBlind(); break;
                    case 4:
                        mob = new GnollThrower(); break;
                    case 5:
                        mob = new Gnoll(); break;
                    case 6:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Brute();
                        } else mob = new GnollThrower();
                        break;
                    case 7:
                        mob = new Gnoll(); break;
                    case 8:
                        mob = Random.oneOf(new Snake(), new Crab()); break;
                    case 9:
                        mob = Random.oneOf(new GnollBlind(),new Gnoll(), new GnollThrower()); break;
                    case 10:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GnollTrickster();
                        } else mob = new Gnoll();
                        break;
                    case 11:
                        mob = new GnollThrower(); break;
                    case 12:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ArmoredBrute();
                        } else mob = new GnollTrickster();
                        break;
                    case 13:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Shaman.PurpleShaman();
                        } else mob = new Shaman.BlueShaman();
                        break;
                    case 14:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ArmoredBrute();
                        } else mob = new Brute();
                        break;
                    case 15: {
                        mob = new Gnoll();
                        break;
                    }
                }
                break;
            }
            case 4:{
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
                break;
            }
            case 5:{
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
                break;
            }
            case 6:{
                switch (wave){
                    case 1:
                        mob = new Thief(); break;
                    case 2:
                        mob = new Guard(); break;
                    case 3:
                        mob = Random.oneOf(new Snake(), new Slime(), new CausticSlime()); break;
                    case 4:
                        mob = Random.oneOf(new Rat(), new Albino()); break;
                    case 5:
                        mob = new Bandit(); break;
                    case 6:
                        mob = new Thief(); break;
                    case 7:
                        mob = new Snake(); break;
                    case 8:
                        mob = new GnollThrower(); break;
                    case 9:
                        mob = Random.oneOf(new GnollThrower(), new Gnoll()); break;
                    case 10:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GnollTrickster();
                        } else mob = new GnollThrower();
                        break;
                    case 11:
                        mob = new Thief(); break;
                    case 12:
                        mob = new Guard(); break;
                    case 13:
                        mob = Random.oneOf(new Thief(),new Gnoll()); break;
                    case 14:
                        mob = Random.oneOf(new Shaman.RedShaman()); break;
                    case 15:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Bandit();
                        } else mob = new Guard();
                        break;
                    case 16:
                        mob = Random.oneOf(new Bandit(), new Brute()); break;
                    case 17:
                        mob = new Guard(); break;
                    case 18:
                        mob = new Necromancer(); break;
                    case 19:
                        mob = Random.oneOf(new HermitCrab(), new CausticSlime()); break;
                    case 20: if (!bossSpawned) {
                        bossSpawned = true;
                        mob = new SkeletonArmored();
                    } else mob = new Skeleton();
                        break;
                }
                break;
            }
            case 7:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Snake()); break;
                    case 2:
                        mob = Random.oneOf(new Shaman.BlueShaman(), new Shaman.PurpleShaman()); break;
                    case 3:
                        mob = Random.oneOf(new Skeleton(), new Rat()); break;
                    case 4:
                        mob = Random.oneOf(new Skeleton()); break;
                    case 5:
                        mob = Random.oneOf(new Necromancer()); break;
                    case 6:
                        mob = Random.oneOf(new Shaman.BlueShaman(), new Shaman.PurpleShaman()); break;
                    case 7:
                        mob = new Shaman.RedShaman(); break;
                    case 8:
                        mob = Random.oneOf(new Snake()); break;
                    case 9:
                        mob = Random.oneOf(new Elemental.FrostElemental()); break;
                    case 10:
                        mob = Random.oneOf(new Ghoul()); break;
                    case 11:
                        mob = new Shaman.RedShaman(); break;
                    case 12:
                        mob = new Necromancer(); break;
                    case 13:
                        mob = Random.oneOf(new Shaman.BlueShaman(),new Shaman.PurpleShaman()); break;
                    case 14:
                        mob = Random.oneOf(new MagiCrab()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new MagiCrab();
                        } else mob = Random.oneOf(new Necromancer(), new Shaman.RedShaman(), new Shaman.BlueShaman(), new Shaman.PurpleShaman());
                        break;
                    }
                }
                break;
            }
            case 8:{
                switch (wave){
                    case 1:
                        mob = new Skeleton(); break;
                    case 2:
                        mob = new Rat(); break;
                    case 3:
                        mob = new Wraith(); break;
                    case 4:
                        mob = Random.oneOf(new Wraith(), new Skeleton()); break;
                    case 5:
                        mob = new Necromancer(); break;
                    case 6:
                        mob = new Shaman.PurpleShaman(); break;
                    case 7:
                        mob = new Skeleton(); break;
                    case 8:
                        mob = new Swarm(); break;
                    case 9:
                        mob = Random.oneOf(new Wraith(), new Swarm()); break;
                    case 10:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SpectralNecromancer();
                        } else mob = new Wraith();
                        break;
                    case 11:
                        mob = new Skeleton(); break;
                    case 12:
                        mob = new Wraith(); break;
                    case 13:
                        mob = Random.oneOf(new Shaman.PurpleShaman(), new Wraith()); break;
                    case 14:
                        mob = Random.oneOf(new Necromancer()); break;
                    case 15:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmored();
                        } else mob = new Skeleton();
                        break;
                    case 16:
                        mob = Random.oneOf(new Skeleton(), new Swarm()); break;
                    case 17:
                        mob = new Skeleton(); break;
                    case 18:
                        mob = new SpectralNecromancer(); break;
                    case 19:
                        mob = new SkeletonArmored(); break;
                    case 20: if (!bossSpawned) {
                        bossSpawned = true;
                        mob = new BossNecromancer();
                    } else mob = new Skeleton();
                        break;
                    case 8055:
                        mob = new Necromancer(); break;
                }
                break;
            }
            case 9:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Thief()); break;
                    case 2:
                        mob = Random.oneOf(new Thief(), new Gnoll()); break;
                    case 3:
                        mob = Random.oneOf(new Gnoll(), new Thief()); break;
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
                        mob = Random.oneOf(new Wraith(), new Bandit()); break;
                    case 7:
                        mob = new Shaman.RedShaman(); break;
                    case 8:
                        mob = Random.oneOf(new Thief()); break;
                    case 9:
                        mob = Random.oneOf(new Thief(), new Shaman.RedShaman()); break;
                    case 10: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Thief();
                        } else mob = Random.oneOf(new Guard());
                        break;
                    }
                }
                break;
            }
            case 10:{
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
                        mob = new Thief(); break;
                }
                break;
            }

            case 11:{
                switch (wave){
                    case 1:
                        mob = new Rat(); break;
                    case 2:
                        mob = new Bat(); break;
                    case 3:
                        mob = new Gnoll(); break;
                    case 4:
                        mob = new Spinner(); break;
                    case 5:
                        mob = Random.oneOf(new Goblin(), new GoblinSand(), new GoblinFat()); break;
                    case 6:
                        mob = new Bat(); break;
                    case 7:
                        mob = new Swarm(); break;
                    case 8:
                        mob = new Spinner(); break;
                    case 9:
                        mob = new Albino(); break;
                    case 10:
                        mob = Random.oneOf(new Goblin(), new GoblinSand(), new GoblinFat()); break;
                    case 11:
                        mob = new ChiefRat(); break;
                    case 12:
                        mob = Random.oneOf(new Spinner(), new Bat()); break;
                    case 13:
                        mob = Random.oneOf(new GnollThrower()); break;
                    case 14:
                        mob = Random.oneOf(new Brute()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GoblinGiant();
                        } else mob = Random.oneOf(new Goblin(), new GoblinFat(), new GoblinSand());
                        break;
                    }
                }
                break;
            }
            case 12:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Crab()); break;
                    case 2:
                        mob = Random.oneOf(new HermitCrab()); break;
                    case 3:
                        mob = Random.oneOf(new Swarm()); break;
                    case 4:
                        mob = Random.oneOf(new HermitCrab(), new Crab(), new Swarm()); break;
                    case 5:
                        mob = Random.oneOf(new Bat()); break;
                    case 6:
                        mob = new Crab(); break;
                    case 7:
                        mob = Random.oneOf(new Bat(), new HermitCrab()); break;
                    case 8:
                        mob = new HermitCrab(); break;
                    case 9: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GreatCrab();
                        } else mob = Random.oneOf(new Bat(),new Swarm());
                        break;
                    }
                    case 10: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new MagiCrab();
                        } else mob = Random.oneOf(new HermitCrab(),new Crab(), new Swarm());
                        break;
                    }
                }
                break;
            }
            case 13:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Swarm()); break;
                    case 2:
                        mob = Random.oneOf(new Gnoll()); break;
                    case 3:
                        mob = Random.oneOf(new Bat(), new Spinner()); break;
                    case 4:
                        mob = Random.oneOf(new Gnoll(), new GnollThrower()); break;
                    case 5:
                        mob = Random.oneOf(new Swarm()); break;
                    case 6:
                        mob = new GnollTrickster(); break;
                    case 7:
                        mob = new Bat(); break;
                    case 8:
                        mob = new Shaman.BlueShaman(); break;
                    case 9:
                        mob = new MagiCrab(); break;
                    case 10:
                        mob = new Spinner(); break;
                    case 11:
                        mob = new Brute(); break;
                    case 12:
                        mob = Random.oneOf(new DM200()); break;
                    case 13: if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ArmoredBrute();
                        } else mob = new GnollTrickster();
                    break;
                    case 14:
                        mob = Random.oneOf(new Spinner(), new Bat()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new ArmoredBrute();
                        } else mob = new Brute();
                        break;
                    }
                }
                break;
            }
            case 14:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Goblin()); break;
                    case 2:
                        mob = Random.oneOf(new GoblinFat()); break;
                    case 3:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new GoblinShaman.ShamanShield();
                        } else mob = Random.oneOf(new Goblin(), new GoblinFat());
                        break;
                    case 4:
                        mob = Random.oneOf(new Goblin(),new GoblinFat(), new GoblinGiant(), new Goblin(),new Goblin()); break;
                    case 5:
                        mob = Random.oneOf(new GoblinGiant()); break;
                    case 6:
                        mob = Random.oneOf(new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new Goblin(), new GoblinFat(), new GoblinShaman.ShamanRegen()); break;
                    case 7:
                        mob = new GoblinNinja(); break;
                    case 8:
                        mob = Random.oneOf(new GoblinSand(), new Goblin()); break;
                    case 9:
                        mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanStrength()); break;
                    case 10:
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new BossTroll();
                        } else mob = new GoblinGiant(); break;
                    case 11:
                        mob = new Goblin(); break;
                    case 12:
                        mob = Random.oneOf(new GoblinShaman.ShamanShield(), new Goblin(), new GoblinSand(),new GoblinFat()); break;
                    case 13:
                        mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanStrength()); break;
                    case 14:
                        mob = Random.oneOf(new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinGiant(), new GoblinShaman.ShamanShield(),new GoblinShaman.ShamanStrength(),new GoblinShaman.ShamanRegen()); break;
                    case 15: {
                        mob = Random.oneOf(new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(), new BossTroll(),  new GoblinShaman.ShamanRegen());
                        break;
                    }
                }
                break;
            }
            case 15:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Bat()); break;
                    case 2:
                        mob = Random.oneOf(new DM100()); break;
                    case 3:
                        mob = Random.oneOf(new DMWMinion()); break;
                    case 4:
                        mob = Random.oneOf(new DMWHead()); break;
                    case 5: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new DMWMinion();
                        } else mob = Random.oneOf(new DM100());
                        break;
                    }
                    case 6:
                        mob = Random.oneOf(new Spinner()); break;
                    case 7:
                        mob = new DM200(); break;
                    case 8:
                        mob = Random.oneOf(new DM201()); break;
                    case 9:
                        mob = Random.oneOf(new DM100()); break;
                    case 10: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new DM201();
                        } else mob = Random.oneOf(new DM100());
                        break;
                    }
                    case 11:
                        mob = Random.oneOf(new Bat(), new Spinner()); break;
                    case 12:
                        mob = Random.oneOf(new Slugger()); break;
                    case 13:
                        mob = Random.oneOf(new DM100()); break;
                    case 14:
                        mob = Random.oneOf(new Slugger()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new DM200();
                        } else mob = new DMWMinion();
                        break;
                    }
                    case 16:
                        mob = Random.oneOf(new DMWWheels(), new DMWHead()); break;
                    case 17:
                        mob = new DMW(); break;
                    case 18:
                        mob = Random.oneOf(new DMWMinion()); break;
                    case 19:
                        mob = Random.oneOf(new Slugger()); break;
                    case 20: {
                        mob = Random.oneOf(new DMW());
                        break;
                    }
                    case 21:
                        mob = Random.oneOf(new DMWWheels(), new DMWHead()); break;
                    case 22:
                        mob = new Slugger(); break;
                    case 23:
                        mob = Random.oneOf(new DM100()); break;
                    case 24:
                        mob = Random.oneOf(new DM201()); break;
                    case 25: {
                        mob = Random.oneOf(new DMW());
                        break;
                    }
                }
                break;
            }
            case 16:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Skeleton()); break;
                    case 2:
                        mob = Random.oneOf(new Monk()); break;
                    case 3:
                        mob = Random.oneOf(new Bandit()); break;
                    case 4:
                        mob = Random.oneOf(new Warlock()); break;
                    case 5: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Golem();
                        } else mob = Random.oneOf(new DM100());
                        break;
                    }
                    case 6:
                        mob = Random.oneOf(new Monk()); break;
                    case 7:
                        mob = new DM100(); break;
                    case 8:
                        mob = Random.oneOf(new SkeletonArmored()); break;
                    case 9:
                        mob = Random.oneOf(new Elemental.FrostElemental()); break;
                    case 10: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmored();
                        } else mob = Random.oneOf(new Skeleton());
                        break;
                    }
                    case 11:
                        mob = Random.oneOf(new Monk(), new Warlock()); break;
                    case 12:
                        mob = Random.oneOf(new Monk()); break;
                    case 13:
                        mob = Random.oneOf(new DM100()); break;
                    case 14:
                        mob = Random.oneOf(new Golem()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Monk();
                        break;
                    }
                }
                break;
            }
            case 17:{
                switch (wave){
                    case 1: case 3: case 6: case 10: case 15:
                        mob = new Skeleton(); break;
                    case 2: case 4: case 5: case 7: case 9: case 13: case 18:
                        mob = new Succubus(); break;
                    case 8: case 11: case 14: case 16:
                        mob = new Eye(); break;
                    default:
                        mob = new RipperDemon(); break;
                }
                break;
            }
            case 19:{
                switch (wave % 4){
                    case 0: mob = new Warlock(); break;
                    case 1: mob = new Monk(); break;
                    case 2: mob = new Golem(); break;
                    case 3: mob = new Ghoul(); break;
                }
                break;
            }
            case 20:{
                switch (wave){
                    case 1:
                        mob = Random.oneOf(new Skeleton()); break;
                    case 2:
                        mob = Random.oneOf(new Monk()); break;
                    case 3:
                        mob = Random.oneOf(new Senior()); break;
                    case 4:
                        mob = Random.oneOf(new Warlock()); break;
                    case 5002:
                    case 5003:
                    case 5006:
                    case 5007:
                    case 5008: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmored();
                        } else mob = new Warlock();
                        break;
                    }
                    case 5001:
                    case 5004:
                    case 5005: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmoredShielded();
                        } else mob = new SkeletonArmored();
                        break;
                    }
                    case 5009: {
                        mob = new Golem();
                        break;
                    }
                    case 5010: {
                        mob = new Skeleton();
                        break;
                    }
                    case 6:
                        mob = Random.oneOf(new Monk()); break;
                    case 7:
                        mob = new DM100(); break;
                    case 8:
                        mob = Random.oneOf(new SkeletonArmored()); break;
                    case 9:
                        mob = Random.oneOf(new Elemental.FrostElemental()); break;
                    case 10001: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmoredShielded();
                        } else mob = Random.oneOf(new SkeletonArmored());
                        break;
                    }
                    case 10002: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Warlock();
                        break;
                    }
                    case 10003: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Monk();
                        break;
                    }
                    case 11:
                        mob = Random.oneOf(new Monk(), new Warlock()); break;
                    case 12:
                        mob = Random.oneOf(new Monk()); break;
                    case 13:
                        mob = Random.oneOf(new Ghoul()); break;
                    case 14:
                        mob = Random.oneOf(new Golem()); break;
                    case 15: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Monk();
                        break;
                    }
                    case 16:
                        mob = Random.oneOf(new Golem()); break;
                    case 17001: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new SkeletonArmoredShielded();
                        } else mob = Random.oneOf(new SkeletonArmored());
                        break;
                    }
                    case 17002: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Warlock();
                        break;
                    }
                    case 17003: {
                        if (!bossSpawned) {
                            bossSpawned = true;
                            mob = new Senior();
                        } else mob = new Monk();
                        break;
                    }
                    case 18:
                        mob = new Monk(); break;
                    case 19:
                        mob = new Senior(); break;
                    case 20:
                        mob = Random.oneOf(new Warlock(), new Monk()); break;
                    case 21:
                        mob = Random.oneOf(new Ghoul()); break;
                    case 22:
                        mob = Random.oneOf(new Golem()); break;
                    case 23:
                        mob = Random.oneOf(new Warlock()); break;
                    case 24:
                        mob = Random.oneOf(new Statue()); break;
                    case 25:
                        mob = Random.oneOf(new Skeleton(), new Skeleton(), new Skeleton(),new Skeleton(), new Skeleton(), new SkeletonArmored());
                        Buff.affect(mob, Speed.class, 25);
                        break;
                }
                break;
            }
        }
        if (depth == 4 && level.mode== WndModes.Modes.CHALLENGE && mob instanceof Crab){
            MagiCrab cr = new MagiCrab();
            cr.buffs().addAll(mob.buffs());
            return cr;
        }
        if (level.mode == WndModes.Modes.CHALLENGE){
            ((Arena)level).affectMob(mob);
        }

        return mob;
    }

    public Actor addRespawner() {
        return null;
    }

    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,

        TOOUP,
        TOODOWN,
        TOOLEFT,
        TOORIGHT,
        RANDOM,
        CENTRAL,
        EVENMORECENTRAL,
        NOTONEDGE,
        EXACTLYRIGHT,
        EXACTLYLEFT,
        EXACTLYUP,
        EXACTLYDOWN
    }

    private boolean bossSpawned = false;

    public void deploymobs(int wave, Direction direction, int groupnum){
        ArrayList<Integer> candidatecells = new ArrayList<>();
        for (int x = 0; x < WIDTH;x++) for (int y = 0; y < HEIGHT; y++){
            switch (direction) {
                case RANDOM: {
                    if (((x >= WIDTH - 5) || (x <= 6) || (y >= HEIGHT - 5) || (y <= 6)) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case UP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case DOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case LEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case RIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOOUP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+5 && x > WIDTH/2-5) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOODOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+5 && x > WIDTH/2-5) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOOLEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y]) && y < HEIGHT/2+5 && y > HEIGHT/2-5 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOORIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])&& y < HEIGHT/2+5 && y > HEIGHT/2-5 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case NOTONEDGE: {
                    if ( ( x > 6) && (x < WIDTH - 6) && ( y > 6) && (y < HEIGHT - 6) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case CENTRAL: {
                    if ( Math.abs(x-WIDTH/2f)<10 && Math.abs(y-HEIGHT/2f)<10 && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EVENMORECENTRAL: {
                    if ( Math.abs(x-WIDTH/2f)<5 && Math.abs(y-HEIGHT/2f)<5 && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYUP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYDOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYLEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y]) && y < HEIGHT/2+2 && y > HEIGHT/2-2 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYRIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])&& y < HEIGHT/2+2 && y > HEIGHT/2-2 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }

            }
        }
        bossSpawned = false;
        int grouppos = -1;
        int mobsDeployed = 0;
        int mobsToDeployFinal = mobsToDeploy(wave);
        if (Dungeon.level.mode == WndModes.Modes.HARDMODE) {
            mobsToDeployFinal = mobsToDeployFinal + mobsToDeployFinal*2*(wave*wave/maxWaves/maxWaves);
        }
        float onexd = ((float)mobsToDeployFinal/groupnum);
        float onexdsum = onexd;
        while (mobsDeployed<mobsToDeployFinal) {
            grouppos = Random.element(candidatecells);
            while ((float)mobsDeployed<onexdsum) {
                Mob mob = chooseMob(wave);
                if (Dungeon.level.mode == WndModes.Modes.HARDMODE && mobsDeployed % 8 == 1){
                    int r = Random.Int(6);
                    switch (r){
                        case 0: Buff.affect(mob, ChampionEnemy.Blazing.class); break;
                        case 1: Buff.affect(mob, ChampionEnemy.Projecting.class); break;
                        case 2: Buff.affect(mob, ChampionEnemy.AntiMagic.class); break;
                        case 3: Buff.affect(mob, ChampionEnemy.Blessed.class); break;
                        case 4: Buff.affect(mob, ChampionEnemy.Growing.class); break;
                        case 5: Buff.affect(mob, ChampionEnemy.Giant.class); break;
                    }
                }
                if (Dungeon.isChallenged(Challenges.SHAMANISM) && mobsDeployed == 0 && level.wave>=5){
                    Mob shaman = Reflection.newInstance(GoblinShaman.random());
                    shaman.pos = grouppos;
                    GameScene.add(shaman);
                    shaman.state = shaman.HUNTING;
                }
                if (depth == 14 && mobsDeployed == 0 && mode == WndModes.Modes.CHALLENGE){
                    Bandit bandit = new Bandit();
                    bandit.spriteClass = Random.oneOf(TowerCrossbow1Sprite.class, TowerWall1Sprite.class, TowerGuard1Sprite.class, GoblinFatSprite.class);
                    bandit.targetingPreference = Mob.TargetingPreference.ONLY_AMULET;
                    if (Math.random()>0.9)Buff.affect(bandit, Invisibility.class, 100000);
                    Buff.affect(bandit, Speed.class, 100000);
                    Buff.affect(bandit, Strength.class, 100000);
                    bandit.pos = grouppos;
                    GameScene.add(bandit);
                    bandit.state = bandit.HUNTING;
                }
                mob.pos = grouppos;
                GameScene.add(mob);
                mob.state = mob.HUNTING;
                mobsDeployed++;
            }
            onexdsum+=onexd;
        }
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RANDOM,0);
    }

    @Override
    public int mobCount() {//an important thing to count mobs on level.
        return super.mobCount();
    }



    @Override
    public void initNpcs() {
        hero.lvl = startLvl;
        hero.updateHT(true);
        Dungeon.gold += startGold;
        updateQuickslot();
        amuletTower.pos = amuletCell;
        amuletTower.HP = amuletTower.HT;
        if (towerShopKeeper!=null){
            towerShopKeeper.pos = towerShopKeeperCell;
        }
        if (normalShopKeeper!=null){
            normalShopKeeper.pos = normalShopKeeperCell;
        }

        level = this;
        GameScene.add(amuletTower);
        this.occupyCell(amuletTower);
        if (towerShopKeeper!=null){
            GameScene.add(towerShopKeeper);
            this.occupyCell(towerShopKeeper);
        }
        if (normalShopKeeper!=null){
            GameScene.add(normalShopKeeper);
            this.occupyCell(normalShopKeeper);
        }
    }

    public void startWave() {
        wave++;
        if (wave == 1){
            if (towerShopKeeper!=null) towerShopKeeper.placeItems();
            if (normalShopKeeper!=null) normalShopKeeper.placeItems();
        }
        hero.speak(Messages.get(this, "wavestart", wave), CharSprite.NEUTRAL);
        Buff.detach(hero, WaveCooldownBuff.class);
        int coldow = ((Arena)Dungeon.level).WIDTH + ((Arena)Dungeon.level).HEIGHT;
        if (((Arena)Dungeon.level).WIDTH < 70 || ((Arena)Dungeon.level).HEIGHT < 70) coldow += 50;
        Buff.affect(hero, WaveBuff.class,   ((Arena) level).maxWaves==((Arena) level).wave ? 10000 : coldow);
        deployMobs(wave);
        doStuffStartwave(wave);
        amuletTower.attractMobs();


    };
    public void endWave() {
        for (Heap heap: Dungeon.level.heaps.valueList()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                if (ShatteredPixelDungeon.scene() instanceof GameScene) {
                    CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
                }
                //heap.destroy();//FIXME the sale heaps either double when they have something on them or just steal these items
                if (heap.size() == 1) {
                    heap.destroy();
                } else {
                    heap.items.remove(heap.size()-1);
                    heap.type = Heap.Type.HEAP;
                }
            }
        }

        if (towerShopKeeper!=null) towerShopKeeper.placeItems();
        if (normalShopKeeper!=null) normalShopKeeper.placeItems();
        doStuffEndwave(wave);
        if (wave==maxWaves && (depth!=6) && (depth!=17) && (depth!=20)) {
            win( Amulet.class );
            if (Dungeon.depth == 15)Badges.validateBossSlain();
            Dungeon.deleteGame( GamesInProgress.curSlot, true );
            Game.switchScene( RankingsScene.class );
        }
        Buff.detach(hero, WaveBuff.class);
        Buff.affect(hero, WaveCooldownBuff.class, (wave % 5 == 4 ? waveCooldownBoss : waveCooldownNormal));
    };


    //a method used for challenges with mobs being affected
    public void affectMob(Mob mob){
        //nothing by default
    }





    @Override
    protected void createMobs() {
        initNpcs();
        addDestinations();
    }
    @Override
    protected void createItems() {
    }

    @Override
    public int randomRespawnCell( Char ch ) {
            return -1;
    }


    public static class AmuletTower extends Mob {//this is the must-kill tower and the wave handler class at the same time

        {
            spriteClass = AmuletTowerSprite.class;

            HP = HT = 50;

            viewDistance = 3;

            defenseSkill = 0;

            EXP = 0;

            state = PASSIVE;

            flying = true;

            properties.add(Property.IMMOVABLE);
            properties.add(Property.BOSS);
            properties.add(Property.INORGANIC);

            immunities.add(Healing.class);
            immunities.add(Sungrass.Health.class);

            immunities.add(Paralysis.class);
            immunities.add(Slow.class);
            immunities.add(Frost.class);
            immunities.add(Freezing.class);

            alignment = Alignment.ALLY;
        }
        public int counter = 0;

        @Override
        public void die(Object cause) {
            hero.die(this);
            GLog.cursed("Amulet was lost...");
            super.die(cause);
        }
        public boolean itWasAWave = false;
        @Override
        protected boolean act() {
            boolean enemyspotted = false;


            counter++;
            if (Dungeon.depth == 18) GameScene.updateFog(pos, 8);
            else GameScene.updateFog(pos, 3);

            for (Mob mob : level.mobs.toArray( new Mob[0] )) {
                if (mob.alignment!=Alignment.ALLY) mob.beckon( this.pos );
                if (((Arena)level).waterIsToxic && level.map[mob.pos] == Terrain.WATER && !mob.flying){
                    if (!(level.mode == WndModes.Modes.CHALLENGE && depth==15)) mob.damage(1, Corrosion.class);
                    else if (mob.alignment == Alignment.ALLY) mob.damage(3, Corrosion.class);
                }

            }
            if (((Arena)level).waterIsToxic && level.map[hero.pos] == Terrain.WATER && !hero.buffs().contains(Levitation.class)){
                hero.damage((level.mode == WndModes.Modes.CHALLENGE && depth==15) ? 3 : 1, Corrosion.class);
            }
            if (depth==5 && level.mode == WndModes.Modes.CHALLENGE){
                for (Mob mob : level.mobs){
                    if (mob.alignment==Alignment.ALLY && level.map[pos]==Terrain.WATER){
                        Buff.affect(mob, Burning.class).setTime(20);
                    }
                }
            }
            if (depth==8 && level.mode == WndModes.Modes.CHALLENGE){
                for (Mob mob : level.mobs){
                    if (mob.alignment==Alignment.ALLY && mob.buff(GoldArmor.class)==null){
                        Buff.affect(mob, GoldArmor.class).setTime(10000);
                    }
                }
            }
            state = PASSIVE;
            for (Mob mob : mobs.toArray( new Mob[0] )) {
                if (mob.alignment!=Alignment.ALLY) mob.beckon( this.pos );
                if (mob.alignment==Alignment.ENEMY && !(mob instanceof Tower) && !(mob instanceof Piranha) && !(mob instanceof RotLasher) && !(mob instanceof Mimic) && !(mob instanceof Bee)&& !(mob instanceof BossDwarfKing)) enemyspotted = true;
            }
            if (Dungeon.depth==11 && Math.random()*1000+level.wave>999){
                if (mobs!=null && hero.buff(WaveCooldownBuff.class)==null) Arena11.dropRock(Random.element(mobs));
            }
            if (Dungeon.depth!=18 && !(depth==20 && level.wave == 25)){
                if (hero.buff(WaveBuff.class) != null && !enemyspotted) {
                    ((Arena) level).endWave();
                    itWasAWave = false;
                }
                if (hero.buff(WaveCooldownBuff.class) == null && hero.buff(WaveBuff.class) == null) {//starts the wave at cooldowns end
                    if (!itWasAWave) {
                        ((Arena) level).startWave();
                        itWasAWave = true;
                    } else {
                        ((Arena) level).endWave();
                        itWasAWave = false;}
                }
            }
            if (mobs!=null) {
                MissileSprite.SPEED = 640f + mobs.size()*30;
                MagicMissile.SPEED = 600 + mobs.size()*20;
            }




            alerted = false;
            return super.act();
        }

        @Override
        public void damage(int dmg, Object src) {
            super.damage(1, src);
        }

        @Override
        public boolean reset() {
            return true;
        }

        @Override
        public void beckon(int cell) {
            //do nothing
        }

        @Override
        public boolean interact(Char c) {
            if (c == Dungeon.hero && Dungeon.depth!=18) {
                if (level.wave==0){
                    ((Arena) level).startWave();
                }
                return true;
            } else return true;
        }

        public void attractMobs(){
            for (Mob mob : level.mobs.toArray( new Mob[0] )) {
                mob.beckon( this.pos );
            }
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return false;
        }

        private String COUNTER = "counter";
        private String LASTWASCOOLDOWN= "lastwascooldown";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(COUNTER, counter);
            bundle.put(LASTWASCOOLDOWN, itWasAWave);
        }
        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            counter = bundle.getInt(COUNTER);
            itWasAWave = bundle.getBoolean(LASTWASCOOLDOWN);
        }
    }

    private String WAVE = "wave";

    private String SHOPKEEPER = "shopkeeper";
    private String TOWERSHOPKEEPER = "towershopkeeper";



    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(WAVE, wave);
        bundle.put(SHOPKEEPER,normalShopKeeper);
        bundle.put(TOWERSHOPKEEPER,towerShopKeeper);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wave = bundle.getInt(WAVE);
        normalShopKeeper = (NormalShopKeeper) bundle.get(SHOPKEEPER);
        towerShopKeeper = (TowerShopKeeper) bundle.get(TOWERSHOPKEEPER);
    }

}
