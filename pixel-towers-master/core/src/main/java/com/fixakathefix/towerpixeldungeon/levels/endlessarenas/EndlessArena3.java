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
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.FetidRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
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
import com.fixakathefix.towerpixeldungeon.levels.ArenaPrison;
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

public class EndlessArena3 extends ArenaPrison {

    {
        name = "Sector 95";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 99;
        HEIGHT = 99;

        amuletCell = 49 + WIDTH*49;
        exitCell = amuletCell;
        towerShopKeeperCell = 42 + 53*WIDTH;
        normalShopKeeperCell = 36 + 53*WIDTH;

        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;

        arenaDepth = 10;

        startGold = 2000;
        maxWaves = 10000;
        waveCooldownNormal = 10;
        waveCooldownBoss = 250;
    }


    @Override
    public int mobsToDeploy(int wave) {
        int num = 0;
        switch (wave % 15){
            case 1: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 10;break;
            case 2: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 3: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 6;break;
            case 4: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 5: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 6: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 3;break;
            case 7: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 2;break;
            case 8: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 5;break;
            case 9: num = (wave*  (int)Math.sqrt(Math.sqrt(wave))/5) * 7;break;
            case 10: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 7;break;
            case 11: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 12: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 13: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 1;break;
            case 14: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 0: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 4;break;
        }
        return num;
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave % 15){
            case 1: mob = new GnollThrower(); break;
            case 2: mob = new Thief(); break;
            case 3: mob = new Necromancer(); break;
            case 4: mob = new Bandit(); break;
            case 5: mob = new Guard(); break;
            case 6: mob = new GnollTrickster(); break;
            case 7: mob = new Shinobi(); break;
            case 8: mob = Random.oneOf(new Necromancer(), new SpectralNecromancer()); break;
            case 9: mob = Random.oneOf(new Bandit(), new Thief()); break;
            case 10: mob = new DM100(); break;
            case 11: mob = new Guard(); break;
            case 12: mob = new Bandit(); break;
            case 13:{
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new SkeletonArmoredShielded();
                }
                else mob = new SkeletonArmored();
            }  break;
            case 14: mob = new Thief(); break;
            case 0:{
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Warlock();
                }
                else mob = new Guard();
            }  break;
        }
        if (level.mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        mob.alignment = Char.Alignment.ENEMY;
        return mob;
    }

    public void deployMobs(int wave) {

        if (wave % 5 == 2) deploymobs(wave, Direction.TOOUP, 1);
        else if (wave % 5 == 3) deploymobs(wave, Direction.TOOLEFT, 1);
        else if (wave % 5 == 4) deploymobs(wave, Direction.TOODOWN, 1);
        else deploymobs(wave, Direction.TOORIGHT, 1);
    }




    public void addDestinations() {
        this.drop(TowerInfo.getTowerSpawner(slot1), amuletCell + 1);
        this.drop(TowerInfo.getTowerSpawner(slot2), amuletCell - 1);
        this.drop(TowerInfo.getTowerSpawner(slot3), amuletCell + width());
        this.drop(TowerInfo.getTowerSpawner(slot4), amuletCell - width());
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 10) candidates.add(m);
        }
        for (int i = 0;i<10;i++){
            this.drop(new Gold(Random.Int(30, 50)), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.randomUsingDefaults(Generator.Category.SCROLL2), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new SmallRation(), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates)).type = Heap.Type.CHEST;
            this.drop(Generator.random(Generator.Category.BOMB), Random.element(candidates));
        }
        this.drop(new IronKey(this.arenaDepth), Random.element(candidates));
        this.drop(new IronKey(this.arenaDepth), Random.element(candidates));this.drop(new IronKey(this.arenaDepth), Random.element(candidates));

        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfFrost(), Random.element(candidates));
        this.drop(new PotionOfShroudingFog(), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(new PotionOfFrost(), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates)).type = Heap.Type.CHEST;

        candidates.clear();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 15 && this.map[m] == Terrain.EMPTY_SP) candidates.add(m);
        }
        MeleeWeapon someCursedWeapon = (MeleeWeapon) Generator.random(Generator.Category.WEAPON);
        someCursedWeapon.cursed = true;
        this.drop(someCursedWeapon, Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type     = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type     = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type     = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type     = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.TOWER), Random.element(candidates)).type    = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.TOWER), Random.element(candidates)).type    = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates)).type   = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.BOMB), Random.element(candidates)).type     = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates)).type   = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates)).type   = Heap.Type.SKELETON;
        this.drop(new ElixirOfIcyTouch(), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(new PotionOfParalyticGas(), Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(new PotionOfMastery(), Random.element(candidates)).type = Heap.Type.SKELETON;
        for (int i = 0;i<10;i++){
            this.drop(new Gold(Random.Int(30, 50)), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.randomUsingDefaults(Generator.Category.SCROLL2), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(new SmallRation(), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates)).type = Heap.Type.SKELETON;
            this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates)).type = Heap.Type.CHEST;
            this.drop(Generator.random(Generator.Category.BOMB), Random.element(candidates));
        }

        super.addDestinations();
    }



    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.BOSS_TRIAL},
                new float[]{1},
                false);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 300;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m] && distance(amuletCell, m)>12) candidates.add(m);
        }

        if (wave % 3 == 2) {
            GLog.p(Messages.get(Arena.class, "lootdrop", goldAdd));
            for (int i = 0; i < 4; i++) {
                this.drop(Random.oneOf(
                        Generator.random(Generator.Category.POTION),
                        Generator.random(Generator.Category.SCROLL2),
                        Generator.random(Generator.Category.STONE),
                        Generator.random(Generator.Category.MISSILE),
                        Generator.random(Generator.Category.BOMB)),  Random.element(candidates));
                this.drop(Generator.random(),  Random.element(candidates));

                for (Heap heap : Dungeon.level.heaps.valueList()) {
                    heap.sprite.link(heap);
                    heap.sprite.update();
                }
            }
            this.drop(new IronKey(arenaDepth),  Random.element(candidates));
        }
        candidates.clear();
        if (wave % 10 == 9){
            for (int m = 0; m<WIDTH*HEIGHT;m++){
                if (this.passable[m] && distance(amuletCell, m)>12 && distance(amuletCell, m)<40 && this.map[m] == Terrain.EMPTY) candidates.add(m);
            }
            if (!candidates.isEmpty()){
            int x = Random.element(candidates);
            EnemyPortal.createEnemyPortal(x, 50 - distance(amuletCell, x));
            }
        }
        super.doStuffEndwave(wave);
    }


    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room


        map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 20, 20, 20, 4, 21, 4, 20, 20, 20, 20, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 1, 1, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 1, 1, 1, 1, 20, 20, 20, 1, 1, 1, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 4, 1, 1, 20, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 15, 15, 15, 2, 1, 1, 1, 1, 1, 4, 25, 21, 25, 4, 1, 1, 1, 1, 1, 1, 1, 2, 2, 15, 15, 4, 4, 4, 14, 26, 14, 10, 1, 1, 20, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 15, 15, 15, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 15, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 10, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 15, 4, 4, 4, 4, 16, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 4, 4, 4, 4, 4, 14, 14, 26, 14, 14, 4, 2, 1, 1, 1, 1, 1, 4, 1, 4, 4, 1, 9, 4, 4, 1, 4, 4, 1, 1, 1, 1, 1, 1, 2, 2, 4, 4, 4, 14, 26, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 20, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 4, 4, 4, 4, 16, 4, 4, 4, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 4, 1, 1, 1, 1, 1, 1, 2, 4, 4, 4, 14, 26, 14, 10, 1, 1, 1, 10, 14, 14, 26, 16, 16, 26, 14, 14, 6, 1, 20, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 20, 1, 10, 14, 14, 26, 16, 16, 26, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 26, 4, 4, 14, 14, 26, 14, 14, 4, 1, 1, 1, 1, 4, 9, 29, 29, 9, 29, 29, 29, 29, 4, 9, 29, 29, 9, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 16, 4, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 9, 4, 4, 4, 9, 29, 4, 9, 1, 4, 9, 29, 29, 29, 9, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 4, 4, 16, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 9, 9, 9, 9, 9, 9, 29, 9, 9, 9, 4, 9, 9, 9, 9, 9, 9, 1, 1, 1, 1, 1, 4, 4, 4, 14, 26, 14, 4, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 26, 16, 16, 26, 14, 14, 4, 20, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 26, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 9, 29, 29, 29, 9, 29, 1, 9, 1, 29, 9, 29, 29, 29, 9, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 26, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 20, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 26, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 9, 4, 4, 9, 29, 29, 29, 29, 29, 9, 29, 29, 9, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 16, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 9, 9, 9, 9, 9, 9, 29, 9, 9, 9, 9, 4, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 16, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 9, 29, 4, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 26, 14, 4, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 26, 14, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 9, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 20, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 20, 1, 6, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 9, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 12, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 29, 1, 25, 1, 1, 1, 25, 1, 29, 1, 1, 1, 1, 29, 1, 25, 1, 1, 1, 25, 1, 29, 1, 1, 1, 1, 29, 1, 25, 1, 1, 1, 25, 1, 29, 1, 1, 1, 1, 29, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 29, 1, 25, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 12, 5, 5, 5, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 5, 5, 5, 12, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 4, 4, 16, 4, 4, 4, 1, 1, 4, 4, 4, 16, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 15, 15, 15, 2, 2, 2, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 2, 2, 2, 15, 15, 15, 15, 4, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 26, 14, 14, 4, 1, 1, 4, 14, 14, 26, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 25, 2, 2, 2, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 2, 2, 2, 25, 1, 1, 15, 4, 1, 4, 14, 14, 14, 14, 14, 16, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 4, 14, 14, 14, 14, 14, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 4, 16, 4, 16, 4, 16, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 29, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 4, 14, 14, 14, 14, 14, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 4, 14, 14, 14, 14, 14, 16, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 12, 1, 1, 1, 1, 1, 1, 12, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 11, 11, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 11, 11, 11, 11, 11, 11, 11, 11, 1, 2, 2, 2, 1, 1, 1, 15, 12, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 29, 1, 12, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 11, 29, 29, 29, 29, 29, 29, 11, 1, 2, 2, 2, 1, 1, 1, 15, 11, 11, 11, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 29, 29, 29, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 11, 29, 25, 29, 29, 25, 29, 11, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 12, 4, 4, 4, 15, 1, 12, 1, 2, 2, 2, 1, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 21, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 11, 29, 25, 29, 29, 25, 29, 11, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 12, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 11, 29, 29, 29, 29, 29, 29, 11, 1, 2, 2, 2, 1, 1, 1, 15, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 1, 1, 29, 29, 29, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 11, 11, 11, 11, 11, 11, 11, 11, 1, 2, 2, 2, 1, 1, 1, 15, 12, 4, 27, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 27, 11, 1, 1, 29, 1, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 29, 1, 12, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 4, 27, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 27, 4, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 4, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 4, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 29, 1, 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 15, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 12, 1, 1, 1, 1, 1, 1, 12, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 4, 4, 4, 16, 4, 4, 4, 1, 1, 4, 4, 4, 16, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 1, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 29, 1, 4, 14, 14, 26, 14, 14, 4, 1, 1, 4, 14, 14, 26, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 29, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 27, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 27, 1, 1, 14, 14, 14, 14, 1, 1, 27, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 13, 13, 1, 2, 2, 2, 1, 13, 13, 1, 1, 1, 1, 13, 13, 1, 2, 2, 2, 1, 13, 13, 15, 4, 1, 27, 1, 1, 14, 14, 14, 14, 1, 1, 27, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 1, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 1, 1, 1, 15, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 27, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 1, 1, 25, 2, 2, 2, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 2, 2, 2, 25, 1, 1, 15, 4, 1, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 15, 15, 15, 15, 2, 2, 2, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 2, 2, 2, 15, 15, 15, 15, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 29, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 12, 5, 5, 5, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 5, 5, 5, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 4, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 29, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 25, 1, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 1, 1, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 12, 4, 4, 4, 4, 4, 4, 4, 4, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 12, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 20, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 16, 16, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 25, 1, 1, 13, 1, 1, 2, 13, 2, 1, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 10, 1, 20, 1, 4, 1, 1, 1, 1, 1, 1, 2, 2, 15, 2, 2, 1, 3, 1, 1, 1, 13, 1, 27, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 1, 1, 1, 2, 3, 2, 15, 15, 9, 9, 1, 1, 1, 1, 1, 1, 1, 11, 1, 1, 4, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 20, 1, 20, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 4, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 1, 1, 1, 2, 15, 15, 15, 15, 9, 15, 2, 2, 20, 1, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 16, 16, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 20, 10, 14, 14, 14, 16, 16, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 13, 1, 13, 15, 15, 15, 29, 15, 15, 15, 2, 1, 20, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 1, 1, 2, 15, 9, 15, 15, 3, 15, 2, 1, 9, 9, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 4, 1, 1, 1, 1, 3, 9, 15, 15, 15, 2, 1, 2, 9, 1, 9, 1, 13, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 20, 1, 4, 14, 14, 14, 16, 16, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 14, 14, 14, 16, 16, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 1, 1, 1, 13, 1, 9, 2, 2, 2, 2, 13, 1, 1, 1, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 10, 1, 20, 20, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 16, 16, 14, 14, 14, 10, 20, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 1, 20, 1, 1, 1, 9, 1, 1, 1, 1, 1, 9, 1, 9, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 16, 16, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 25, 1, 1, 1, 1, 1, 1, 9, 9, 9, 1, 1, 1, 9, 1, 1, 13, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 16, 16, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 10, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 4, 1, 1, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 20, 4, 14, 14, 14, 16, 16, 14, 14, 14, 4, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 10, 1, 1, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 20, 1, 20, 10, 14, 14, 14, 16, 16, 14, 14, 14, 10, 1, 20, 1, 10, 14, 14, 14, 4, 4, 14, 14, 14, 10, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 14, 14, 14, 16, 16, 14, 14, 14, 14, 14, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 20, 20, 1, 20, 20, 1, 1, 1, 1, 20, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 20, 20, 20, 20, 20, 20, 4, 21, 4, 20, 20, 20, 20, 20, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

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

}
