package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Fire;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.FetidRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollBlind;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatArcher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatMage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatShield;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.effects.Ripple;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.armor.LeatherArmor;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.food.MeatPie;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.items.food.SmallRation;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrArrowVolley;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfExperience;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfFrost;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfDrowsiness;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTerror;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfRatLegion;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCamp;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.levels.traps.BurningTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.Callback;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Arena2 extends ArenaSewers{

    /**
     * Has a ton of loot.
     * General weak enemies come from the left, enemies for magic (evasive snakes and armored crabs) come from the right.
     * Explains magic, as the second wave of snakes forces you to put a wand, as snakes can't dodge wands + RK explanation
     * Explains exploration, as there is a lot of loot + RK explanation
     * allows the player to sandbox a bit.
     */
    {
        name = "Deeper Sewers";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 20;
        WIDTH = 101;
        HEIGHT = 101;

        startGold = 200;
        startLvl = 3;
        arenaDepth = 2;
        maxWaves = 15;

        amuletCell = 51 + WIDTH*51;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 5*WIDTH - 3;
        normalShopKeeperCell = amuletCell - 5*WIDTH + 2;

        waveCooldownNormal = 5;
        waveCooldownBoss = 200;
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
                mob = new Rat(); break;
            case 2:
                mob = new Snake(); break;
            case 3:
                mob = Random.oneOf(new GnollBlind(), new Gnoll()); break;
            case 4:
                mob = Random.oneOf(new Snake()); break;
            case 5:
                mob = Random.oneOf(new FetidRat()); break;
            case 6:
                mob = Random.oneOf(new Albino(), new Crab()); break;
            case 7:
                mob = new Rat(); break;
            case 8:
                mob = Random.oneOf(new Crab(), new Snake()); break;
            case 9:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new FetidRat();
                } else mob = Random.oneOf(new Rat(), new Albino()); break;
            case 10:
                mob = new HermitCrab(); break;
            case 11:
                mob = Random.oneOf(new Gnoll(), new GnollThrower()); break;
            case 12:
                mob = new Albino(); break;
            case 13:
                mob = Random.oneOf(new CausticSlime(), new Slime()); break;
            case 14:
                mob = Random.oneOf(new HermitCrab()); break;
            case 15:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new BossRatKing();
                } else {
                    if (mode == WndModes.Modes.HARDMODE) mob = Random.oneOf(new CampRatMage(), new CampRatShield());
                    else mob = new CampRatArcher();
                    mob.alignment = Char.Alignment.ENEMY;
                }
                break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 3;
            case 2: return 6;
            case 3: return 4;
            case 4: return 9;
            case 5: return 2;
            case 6: return 6;
            case 7: return 12;
            case 8: return 9;
            case 9: return 13;
            case 10: return 6;
            case 11: return 12;
            case 12: return 15;
            case 13: return 11;
            case 14: return 9;
            case 15: return mode == WndModes.Modes.HARDMODE ? 6 : 3;
        }
        return 1;
    }
    public void deployMobs(int wave) {
        if (wave == 12){
            for (int i : PathFinder.NEIGHBOURS9) GameScene.add(Blob.seed(51+WIDTH*58 + i, 10, Fire.class));
            for (int i : PathFinder.NEIGHBOURS9) GameScene.add(Blob.seed(51+WIDTH*61 + i, 10, Fire.class));

            (new Bomb()).explode(51 + WIDTH*57);
            for (int i=0;i<20;i++){
                Albino rat = new Albino();
                rat.pos = 51 + WIDTH*62;
                GameScene.add(rat);

            }
        } else if (wave % 2 == 0){
            deploymobs(wave, Direction.TOORIGHT, 1);
        } else {
            deploymobs(wave, Direction.TOOLEFT, 1);
        }
    }
    @Override
    public void doStuffStartwave(int wave) {
        if (wave == 1){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w1start1"),
                            Messages.get(RatKing.class, "l2w1start2"),
                            Messages.get(RatKing.class, "l2w1start3"),
                            Messages.get(RatKing.class, "l2w1start4"),
                            Messages.get(RatKing.class, "l2w1start5")

                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave == 4){
            int wands = 0;
            for (Mob mob : Level.mobs) {
                if ((mob instanceof TowerWand1 || mob instanceof TowerDisintegration1 || mob instanceof TowerLightning1 || mob instanceof TowerCannon1) && mob.alignment== Char.Alignment.ALLY) wands++;
            }
            if (wands == 0){
                WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(RatKing.class, "name"),
                        new String[]{
                                Messages.get(RatKing.class, "l2w4start1_nowands"),
                                Messages.get(RatKing.class, "l2w4start2_nowands")
                        },
                        new byte[]{
                                WndDialogueWithPic.RUN,
                                WndDialogueWithPic.IDLE,
                        });
            }

        }
        if (wave==5){
            ArrayList<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i<4;i++) runnables.add(null);
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    int cell = amuletCell+WIDTH;
                    Camera.main.panTo(DungeonTilemap.tileCenterToWorld(cell), 2f);
                    PointF source = DungeonTilemap.raisedTileCenterToWorld(cell);
                    PointF dest = DungeonTilemap.tileCenterToWorld(cell);
                    source.y-=150;
                    source.x-=30;
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source,
                                    dest,
                                    new AbTrArrowVolley(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new AbTrArrowVolley(), cell);
                                            Sample.INSTANCE.play(Assets.Sounds.BLAST);
                                        }
                                    },
                                    200f,
                                    100f);
                }
            });
            runnables.add(null);

            WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w5start1"),
                            Messages.get(RatKing.class, "l2w5start2"),
                            Messages.get(RatKing.class, "l2w5start3"),
                            Messages.get(RatKing.class, "l2w5start4"),
                            Messages.get(RatKing.class, "l2w5start5"),
                            Messages.get(RatKing.class, "l2w5start6"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.RUN
                    }, WndDialogueWithPic.WndType.NORMAL, runnables);
        }
        if (wave==10){
            ArrayList<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i<2;i++) runnables.add(null);
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    int cell = normalShopKeeperCell;
                    Camera.main.panTo(DungeonTilemap.tileCenterToWorld(cell), 2f);
                }
            });
            runnables.add(null);
            WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w10start1"),
                            Messages.get(RatKing.class, "l2w10start2"),
                            Messages.get(RatKing.class, "l2w10start3"),
                            Messages.get(RatKing.class, "l2w10start4"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    }, WndDialogueWithPic.WndType.NORMAL, runnables);
        }
        if (wave == 12){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w12start1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }

        if (wave==14){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w14start1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }
        if (wave==15){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(),  Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w15start1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }

        super.doStuffStartwave(wave);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 70 + wave*5;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
        if (wave == 2){
            ArrayList<Runnable> runnables = new ArrayList<>();
            for (int i = 0; i<2;i++) runnables.add(null);
            runnables.add(new Runnable() {
                @Override
                public void run() {
                    int cell = amuletCell-WIDTH;
                    Camera.main.panTo(DungeonTilemap.tileCenterToWorld(cell), 2f);
                    PointF source = DungeonTilemap.raisedTileCenterToWorld(cell);
                    PointF dest = DungeonTilemap.tileCenterToWorld(cell);
                    source.y-=150;
                    source.x-=30;
                    ((MissileSprite) hero.sprite.parent.recycle(MissileSprite.class))
                            .reset(
                                    source,
                                    dest,
                                    new Gold(),
                                    new Callback() {
                                        @Override
                                        public void call() {
                                            drop(new Gold(626), cell);
                                            Sample.INSTANCE.play(Assets.Sounds.GOLD);
                                        }
                                    },
                                    200f,
                                    100f);
                }
            });
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w2end1"),
                            Messages.get(RatKing.class, "l2w2end2"),
                            Messages.get(RatKing.class, "l2w2end3"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE,
                            WndDialogueWithPic.RUN
                    }, WndDialogueWithPic.WndType.NORMAL,
                    runnables);
        }
        if (wave==4){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w4end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave==12){
            WndDialogueWithPic.dialogue(new BossRatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l2w12end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
    }

    @Override
    public void affectMob(Mob mob) {
        if (mob instanceof Rat) Buff.affect(mob, Invisibility.class, 300);
    }


    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 6,6,91,91, Terrain.WALL);


        Painter.fill(this, 7,7,89,89, Terrain.EMPTY);
        Painter.fill(this, 8,8,87,87, Terrain.WATER);
        Painter.fill(this,9,9,85,85, Terrain.EMPTY);
        Painter.fill(this, 10,10,83,83, Terrain.WALL);

        Painter.fill(this,15,15,73,73, Terrain.EMPTY);
        Painter.fill(this, 16,16,71,71, Terrain.WATER);
        Painter.fill(this,17,17,69,69, Terrain.EMPTY);
        Painter.fill(this, 18,18,67,67, Terrain.WALL);

        Painter.fill(this,23,23,57,57, Terrain.EMPTY_SP);
        Painter.fill(this, 26,26, 51,51, Terrain.WALL);
        Painter.fill(this,27,27,49,49, Terrain.EMPTY);
        Painter.fill(this, 28,28,47,47, Terrain.WALL);

        Painter.fill(this,33,33,37,37, Terrain.EMPTY);
        Painter.fill(this, 34,34,35,35, Terrain.WATER);
        Painter.fill(this,35,35,33,33, Terrain.EMPTY);
        Painter.fill(this, 36,36,31,31, Terrain.WALL);

        Painter.fill(this,37,37,29,29, Terrain.EMPTY);
        Painter.fill(this, 38,38,27,27, Terrain.WATER);
        Painter.fill(this,39,39,25,25, Terrain.EMPTY);
        Painter.fill(this, 40,40,23,23, Terrain.WALL);
        Painter.fillEllipse(this,46,46,11,11, Terrain.EMPTY);
        Painter.fill(this,46,46,11,3, Terrain.EMPTY);
        Painter.fill(this,46,47,11,1, Terrain.PEDESTAL);

        for (int x = 13;x<WIDTH-13;x++) for (int y = 13;y<HEIGHT-13;y++) if (x < 30 || x > 70) if (y < 30 || y > 70){
            //Random wet flooring
            int cell = x+WIDTH*y;
            if (this.map[cell]==Terrain.EMPTY){
                if (Math.random()>0.99) {
                    this.map[cell]=Terrain.WATER;
                    for (int i1: PathFinder.NEIGHBOURS8){
                        if (Math.random()>0.5 && this.map[cell+i1]==Terrain.EMPTY) this.map[cell+i1] = Terrain.WATER;
                    }
                    for (int i1: PathFinder.NEIGHBOURS25){
                        if (Math.random()>0.9 && this.map[cell+i1]==Terrain.EMPTY) this.map[cell+i1] = Terrain.WATER;//90% at 1 range, 50% at 2 range will become water cells
                    }
                }
            }
            //Random passages between wall layers
            if (this.map[cell]==Terrain.WALL){
                if (Math.random()>0.95) this.map[cell]=Terrain.EMPTY;
                if (Math.random()>0.99) this.map[cell]=Terrain.WATER;
            }
            if (this.map[cell]==Terrain.WALL){//random tubes
                if (Math.random()>0.994) {
                    this.map[cell] = Terrain.WALL_DECO;
                    if (Math.random() > 0.3) this.map[cell + WIDTH] = Terrain.WATER;
                    if (Math.random() > 0.8) this.map[cell + WIDTH - 1] = Terrain.WATER;
                    if (Math.random() > 0.8) this.map[cell + WIDTH + 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell - 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell + 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell + WIDTH + WIDTH] = Terrain.WATER;
                }
            }
        }
        Painter.fill(this, 0, 49, 101, 5,Terrain.EMPTY);
        Painter.fill(this, 50, 57, 3, 20,Terrain.EMPTY_SP);
        Painter.fill(this, 50, 57, 3, 1,Terrain.BARRICADE);



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);
        int x;
        int y;
        //TreasuryX2
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>40);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>40);
        Painter.fill(this,x,y,7,7, Terrain.EMPTY);
        Painter.fill(this,x+1,y+1,5,5, Terrain.WALL);
        Painter.fill(this,x+2,y+2,3,3, Terrain.EMPTY_SP);
        this.map[x+3 + WIDTH*(y+1)] = Terrain.DOOR;
        this.map[x+3 + WIDTH*(y+3)] = Terrain.STATUE_SP;
        MeleeWeapon meleeWeapon = (MeleeWeapon) Generator.random(Generator.Category.WEP_T2);
        meleeWeapon.upgrade(1);
        meleeWeapon.identify();
        this.drop(meleeWeapon, x+2 + WIDTH*(y+2));

        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>40);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>40);
        Painter.fill(this,x,y,9,9, Terrain.EMPTY);
        Painter.fill(this,x+1,y+1,7,7, Terrain.WALL);
        Painter.fill(this,x+2,y+2,5,5, Terrain.EMPTY_SP);
        this.map[x+3 + WIDTH*(y+1)] = Terrain.DOOR;

        this.map[x+4 + WIDTH*(y+4)] = Terrain.STATUE_SP;
        LeatherArmor armor = new LeatherArmor();
        armor.upgrade(1);
        armor.identify();
        this.drop(armor, x+4 + WIDTH*(y+5));
        this.drop(new Bomb(), x+2+WIDTH*(y+4));

        //trapcircle
        do {x = Random.Int(12,WIDTH-15);} while (x<60&&x>40);
        do {y = Random.Int(12,HEIGHT-15);} while (y<60&&y>40);
        for(int i : PathFinder.NEIGHBOURS8){
            this.map[x+y*WIDTH+i] = Terrain.TRAP;
            this.setTrap(new BurningTrap().reveal(), x+y*WIDTH+i);
        }
        this.map[x+y*WIDTH] = Terrain.EMPTY_DECO;
        this.drop(new PotionOfStrength(), x + WIDTH*(y));
        //A pathway room
        do {x = Random.Int(12,WIDTH-15);} while (x<60&&x>30);
        do {y = Random.Int(12,HEIGHT-15);} while (y<60&&y>30);
        Painter.fillEllipse(this,x,y,11,11, Terrain.EMPTY);
        Painter.fillEllipse(this,x+1,y+1,9,9, Terrain.WALL);
        Painter.fillEllipse(this,x+2,y+2,7,7, Terrain.WATER);
        Painter.fill(this, x, y+5, 11,1,Terrain.EMPTY_SP);
        Painter.fill(this, x+5, y, 1,11,Terrain.EMPTY_SP);
        //an explosion centre
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>30);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>30);
        Painter.fillEllipse(this,x,y,3,8, Terrain.EMBERS);
        this.drop(new Bomb(), x+2+y*(WIDTH+3));

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m] && this.distance(amuletCell, m) > 15 && !cellAdjacentToBorderCells(m) ) candidates.add(m);
        }


        dropMany(Heap.Type.CHEST,candidates,
                new PotionOfHealing(),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND),
                Generator.random(Generator.Category.RING),
                Generator.random(Generator.Category.WAND)
                );
        for (int i = 0; i < 3; i++) {
            dropMany(candidates,
                    new Honeypot(),
                    new PotionOfLiquidFlame(),
                    new PotionOfFrost(),
                    new PotionOfExperience(),
                    new PotionOfToxicGas(),
                    new ScrollOfUpgrade(),
                    new ScrollOfDrowsiness(),
                    new ScrollOfTerror(),
                    new ScrollOfMirrorImage(),
                    new MeatPie(),
                    new MysteryMeat(),
                    new SmallRation(),
                    new Honeypot(),
                    new PotionOfHealing(),
                    new PotionOfLiquidFlame(),
                    new PotionOfFrost(),
                    new PotionOfExperience(),
                    new PotionOfToxicGas(),
                    new ScrollOfUpgrade(),
                    new ScrollOfAnimation(),
                    new ScrollOfDrowsiness(),
                    new ScrollOfTerror(),
                    new ScrollOfMirrorImage(),
                    new MeatPie(),
                    new MysteryMeat(),
                    new SmallRation(),
                    Generator.random(Generator.Category.MIS_T3),
                    Generator.random(Generator.Category.MIS_T2),
                    Generator.random(Generator.Category.MIS_T1),
                    new MysteryMeat(),
                    Generator.random(Generator.Category.ARMOR),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.GOLD),
                    Generator.random(Generator.Category.SEED),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.SEED),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.SEED),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.SEED),
                    Generator.random(Generator.Category.STONE),
                    Generator.random(Generator.Category.SEED),
                    Generator.random(Generator.Category.GOLD)
            );
        }


        this.drop(new GoldenKey(arenaDepth),Random.element(candidates));
        this.drop(new ScrollOfRatLegion(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;
        this.drop(new GoldenKey(arenaDepth),Random.element(candidates));
        this.drop(new SpawnerCamp(),Random.element(candidates)).type = Heap.Type.LOCKED_CHEST;



        super.addDestinations();
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        ArrayList<Integer> cellstoplacewalls = new ArrayList<>(Arrays.asList(
                amuletCell+4+WIDTH,
                amuletCell+4,
                amuletCell+4-WIDTH,
                amuletCell-4+WIDTH,
                amuletCell-4,
                amuletCell-4-WIDTH
        ));
        for (int cell : cellstoplacewalls){
            TowerWall1 wall = new TowerWall1();
            wall.sellable = false;
            wall.pos = cell;
            GameScene.add(wall);
        }
        ArrayList<Integer> cellstoplacecrossbows= new ArrayList<>(Arrays.asList(
                amuletCell+3-WIDTH,
                amuletCell-3-WIDTH,
                amuletCell+3+WIDTH,
                amuletCell-3+WIDTH
        ));
        for (int cell : cellstoplacecrossbows){
            TowerCrossbow1 crossbow = new TowerCrossbow1();
            crossbow.sellable = false;
            crossbow.pos = cell;
            GameScene.add(crossbow);
        }
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


}