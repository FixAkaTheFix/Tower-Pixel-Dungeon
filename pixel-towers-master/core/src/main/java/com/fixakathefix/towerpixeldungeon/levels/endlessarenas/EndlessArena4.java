package com.fixakathefix.towerpixeldungeon.levels.endlessarenas;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Minion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MinionBoss;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTroll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.FetidRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goblin;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinFat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinGiant;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinNinja;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinSand;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GoblinShaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Necromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shinobi;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatArcher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatKnife;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatMage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatShield;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuardSpearman;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.food.SmallRation;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfFrost;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfParalyticGas;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfIcyTouch;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfMastery;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfShroudingFog;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.ArenaCaves;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EndlessArena4 extends ArenaCaves {

    {
        name = "Gnolls and Goblins";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 79;
        HEIGHT = 39;

        amuletCell = 36 + WIDTH*19;
        exitCell = amuletCell;
        towerShopKeeperCell = 35 + 13*WIDTH;
        normalShopKeeperCell = 35 + 24*WIDTH;

        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.LEFT;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.LEFT;

        arenaDepth = 13;

        startGold = 7000;
        maxWaves = 10000;
        waveCooldownNormal = 10;
        waveCooldownBoss = 10;
    }


    @Override
    public int mobsToDeploy(int wave) {
        int num = 0;
        switch (wave % 10){
            case 1: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 10;break;
            case 2: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 9;break;
            case 3: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 8;break;
            case 4: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 8;break;
            case 5: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 6: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 10;break;
            case 7: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 3;break;
            case 8: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 10;break;
            case 9: num = wave/6;break;
            case 0: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 3;break;
        }
        return num;
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave % 10){
            case 1: mob = new Goblin(); break;
            case 2: mob = new GoblinFat(); break;
            case 3: mob = Random.oneOf(
                    new GoblinShaman.ShamanRegen(),
                    new Goblin(), new GoblinFat(), new Goblin(), new GoblinFat()
            ); break;
            case 4: mob = new GoblinSand(); break;
            case 5: mob = Random.oneOf(
                    new GoblinShaman.ShamanShield(),
                    new GoblinSand(), new GoblinSand(), new GoblinSand(), new GoblinGiant()
            ); break;
            case 6: mob = new GoblinNinja(); break;
            case 7: mob = Random.oneOf(
                    GoblinShaman.randomInstance(),
                    new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new GoblinGiant()
            ); break;
            case 8: mob = Random.oneOf(
                    GoblinShaman.randomInstance(),
                    new Goblin(), new GoblinFat(), new Goblin(), new GoblinFat()
            ); break;
            case 9: mob = new BossTroll(); break;
            case 0:{
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new BossTroll();
                }
                else mob = Random.oneOf(
                        GoblinShaman.randomInstance(),
                        new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new GoblinGiant(),new GoblinGiant());
            }  break;
        }
        if (level.mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        mob.alignment = Char.Alignment.ENEMY;
        return mob;
    }

    public void deployMobs(int wave) {
        if (wave % 10 == 6) deploymobs(wave, Direction.RANDOM, 2);
        else deploymobs(wave, Direction.TOORIGHT, 1);
        for (int i = 0; i < level.wave/4 + 5; i++){
            Mob gnoll = Random.oneOf(
                    new Gnoll(), new Gnoll(),
                    new GnollThrower(), new GnollThrower(),
                    new GnollTrickster(),
                    new Brute());
            gnoll.pos = WIDTH * 19 + 4;
            gnoll.alignment = Char.Alignment.ALLY;
            gnoll.state = gnoll.WANDERING;
            GameScene.add(gnoll);
            gnoll.beckon(WIDTH * 19 + 60);
        }
    }




    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 10) candidates.add(m);
        }
        for (int i = 0;i<3;i++){
            this.drop(new Gold(Random.Int(30, 50)), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.randomUsingDefaults(Generator.Category.SCROLL2), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new SmallRation(), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates)).type = Heap.Type.CHEST;
            this.drop(Generator.random(Generator.Category.BOMB), Random.element(candidates));
        }
        candidates.clear();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 15 && this.map[m] == Terrain.EMPTY_SP) candidates.add(m);
        }
        super.addDestinations();
    }



    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CAVES_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 500;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }


    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room


        map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 12, 12, 4, 4, 4, 1, 1, 1, 4, 4, 1, 1, 12, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 1, 12, 4, 1, 1, 4, 4, 1, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 13, 1, 13, 1, 1, 1, 1, 1, 1, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 20, 1, 1, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 13, 1, 12, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 13, 13, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 13, 13, 1, 1, 1, 1, 1, 13, 13, 1, 1, 1, 1, 13, 13, 6, 13, 13, 13, 13, 1, 20, 1, 12, 4, 13, 1, 1, 1, 1, 1, 1, 1, 13, 4, 4, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 12, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 13, 13, 13, 6, 13, 13, 13, 13, 4, 4, 1, 1, 20, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 20, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 12, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 13, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 14, 1, 1, 1, 1, 4, 4, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 13, 1, 1, 1, 1, 13, 1, 1, 1, 1, 20, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 13, 13, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 14, 1, 1, 1, 1, 11, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 13, 1, 1, 1, 1, 13, 1, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 12, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 1, 1, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 14, 1, 1, 1, 1, 11, 1, 4, 1, 1, 1, 1, 4, 4, 12, 4, 4, 4, 4, 14, 1, 13, 13, 6, 13, 13, 13, 1, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 13, 13, 13, 6, 13, 13, 13, 1, 1, 1, 1, 14, 1, 1, 20, 1, 11, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 13, 1, 14, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 11, 1, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 13, 13, 14, 14, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 20, 1, 14, 1, 1, 1, 1, 11, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 4, 4, 4, 4, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 25, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 13, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 20, 1, 1, 1, 20, 1, 14, 1, 1, 1, 20, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 20, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 20, 1, 1, 11, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 2, 2, 29, 29, 29, 29, 2, 2, 2, 2, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 25, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 2, 2, 1, 1, 14, 1, 1, 1, 11, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 13, 14, 14, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 15, 2, 1, 14, 1, 1, 1, 11, 1, 4, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 4, 4, 4, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 15, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 15, 2, 14, 1, 1, 1, 11, 1, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 13, 13, 13, 13, 13, 4, 12, 1, 15, 29, 29, 29, 29, 29, 29, 29, 29, 15, 15, 15, 2, 1, 14, 1, 1, 1, 11, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 20, 1, 1, 12, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 15, 15, 15, 15, 15, 15, 15, 15, 2, 2, 2, 1, 1, 14, 1, 1, 1, 11, 1, 4, 1, 1, 1, 1, 4, 4, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 2, 2, 2, 2, 11, 11, 11, 11, 11, 11, 11, 1, 1, 14, 1, 1, 1, 4, 4, 4, 1, 1, 1, 1, 1, 1, 14, 4, 1, 1, 14, 1, 1, 1, 20, 13, 13, 13, 6, 13, 13, 13, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 12, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 11, 29, 29, 29, 29, 29, 11, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 13, 13, 6, 13, 13, 1, 1, 13, 1, 1, 1, 1, 1, 1, 13, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 11, 2, 2, 2, 2, 2, 11, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 13, 1, 1, 13, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 4, 4, 4, 4, 4, 12, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 12, 12, 4, 12, 12, 4, 12, 12, 12, 12, 12, 1, 1, 20, 1, 1, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 13, 13, 13, 13, 13, 6, 1, 13, 13, 13, 13, 1, 1, 1, 13, 1, 1, 1, 13, 12, 4, 13, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 4, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 20, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 4, 1, 1, 13, 1, 1, 1, 13, 4, 4, 13, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 4, 4, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 4, 1, 1, 13, 13, 13, 13, 13, 1, 4, 13, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 4, 12, 12, 4, 12, 12, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 12, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 13, 1, 1, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 12, 4, 1, 1, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 12, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        buildFlagMaps();
        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }




    @Override
    public void initNpcs() {
        Buff.affect(hero, WaveCooldownBuff.class, 30);
        super.initNpcs();
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }

}