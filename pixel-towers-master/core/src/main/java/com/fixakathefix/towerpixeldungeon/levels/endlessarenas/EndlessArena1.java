package com.fixakathefix.towerpixeldungeon.levels.endlessarenas;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.Honeypot;
import com.fixakathefix.towerpixeldungeon.items.food.SmallRation;
import com.fixakathefix.towerpixeldungeon.items.keys.GoldenKey;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfFrost;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import com.fixakathefix.towerpixeldungeon.items.potions.elixirs.ElixirOfToxicEssence;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfStormClouds;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EndlessArena1 extends Arena {

    //the default map is similar to sewer entrance.
    {
        name = "Sewer catacombs";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 70;
        HEIGHT = 49;

        amuletCell = 15 + WIDTH*24;
        exitCell = amuletCell;
        towerShopKeeperCell = 7 + 20*WIDTH;
        normalShopKeeperCell = 7 + 28*WIDTH;

        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.DOWN;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;

        arenaDepth = 1;

        startGold = 500;
        maxWaves = 10000;
        waveCooldownNormal = 10;
        waveCooldownBoss = 200;

    }


    @Override
    public int mobsToDeploy(int wave) {
        int num = 0;
        switch (wave % 20){
            case 1: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 10;break;
            case 2: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 3: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 4: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 5: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 1;break;
            case 6: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 4;break;
            case 7: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 5;break;
            case 8: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 4;break;
            case 9: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 3;break;
            case 10: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 1;break;
            case 11: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 10;break;
            case 12: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 13: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 7;break;
            case 14: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 5;break;
            case 15: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5 + 1) * 1;break;
            case 16: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 4;break;
            case 17: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 7;break;
            case 18: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 4;break;
            case 19: num = (wave* (int)Math.sqrt(Math.sqrt(wave))/5) * 3;break;
            case 0: num = (wave/19);break;
        }
        return num;
    }
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 10) candidates.add(m);
        }
        for (int i = 0;i<10;i++){
            this.drop(Generator.random(Generator.Category.POTION), Random.element(candidates));
            this.drop(Generator.randomUsingDefaults(Generator.Category.SCROLL2), Random.element(candidates));
            this.drop(new SmallRation(), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));
            this.drop(Generator.random(Generator.Category.MISSILE), Random.element(candidates));
        }
        this.drop(new IronKey(this.arenaDepth), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(new Honeypot(), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfFrost(), Random.element(candidates));
        this.drop(new Honeypot(), Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(), Random.element(candidates));
        this.drop(new PotionOfFrost(), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.CHEST;
        this.drop(Generator.random(Generator.Category.STONE), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED), Random.element(candidates));

        candidates.clear();
        for (int m = 0; m < WIDTH * HEIGHT; m++) {
            if (this.passable[m] && this.distance(amuletCell, m) > 15 && this.map[m] == Terrain.PEDESTAL) candidates.add(m);
        }
        MeleeWeapon someCursedWeapon = (MeleeWeapon) Generator.random(Generator.Category.WEAPON);
        someCursedWeapon.cursed = true;
        this.drop(someCursedWeapon, Random.element(candidates)).type = Heap.Type.SKELETON;
        this.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.BOMB), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates));
        this.drop(new ElixirOfToxicEssence(), Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(), Random.element(candidates));
        this.drop(new PotionOfStormClouds(), Random.element(candidates));
        this.drop(new ElixirOfAquaticRejuvenation(), Random.element(candidates));

        super.addDestinations();
    }

    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave % 20){
            case 1: mob = new Rat(); break;
            case 2: mob = new Gnoll(); break;
            case 3: mob = new Albino(); break;
            case 4: mob = new GnollThrower(); break;
            case 5: mob = new ChiefRat(); break;
            case 6: mob = new Crab(); break;
            case 7: mob = Random.oneOf(new GnollThrower(), new Gnoll()); break;
            case 8: mob = Random.oneOf(new Crab(), new Slime()); break;
            case 9: mob = Random.oneOf(new HermitCrab(), new CausticSlime()); break;
            case 10: mob = new Goo(); break;
            case 11: mob = new Rat(); break;
            case 12: mob = new Gnoll(); break;
            case 13: mob = new Albino(); break;
            case 14: mob = new GnollThrower(); break;
            case 15: mob = new GnollTrickster(); break;
            case 16: mob = new Crab(); break;
            case 17: mob = Random.oneOf(new Snake()); break;
            case 18: mob = Random.oneOf(new CausticSlime(), new Slime()); break;
            case 19: mob = Random.oneOf(new HermitCrab(), new Crab()); break;
            case 0:{
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new BossOoze();
                }
                else mob = new CausticSlime();
            }  break;
        }
        if (level.mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
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
        int goldAdd = 70;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m] && distance(amuletCell, m)>12) candidates.add(m);
        }

        GLog.p(Messages.get(Arena.class, "lootdrop", goldAdd));
        this.drop(new Gold(Random.Int(10,40)),  Random.element(candidates));
        this.drop(Random.oneOf(
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL2),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.SEED)),  Random.element(candidates));
        this.drop(Generator.random(),  Random.element(candidates));

        for (Heap heap : Dungeon.level.heaps.valueList()) {
            heap.sprite.link(heap);
            heap.sprite.update();
        }

        super.doStuffEndwave(wave);
    }


    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room

        int type = Random.Int(2);
        if (type == 1){
            map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 25, 1, 1, 1, 25, 1, 20, 20, 20, 13, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 2, 15, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 15, 15, 4, 4, 4, 20, 1, 1, 1, 1, 1, 20, 29, 29, 13, 13, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 2, 2, 15, 15, 4, 4, 21, 1, 1, 1, 20, 20, 1, 1, 20, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 1, 20, 1, 20, 1, 1, 1, 20, 13, 29, 13, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 29, 2, 2, 15, 4, 4, 4, 1, 25, 1, 1, 1, 25, 1, 20, 29, 29, 13, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 14, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 11, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 15, 15, 15, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 2, 2, 15, 2, 2, 2, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 2, 2, 2, 2, 1, 1, 1, 1, 4, 4, 4, 4, 1, 14, 14, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 2, 15, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 4, 4, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 4, 4, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 4, 4, 1, 1, 20, 1, 2, 15, 20, 2, 2, 2, 2, 2, 2, 29, 29, 29, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 25, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 5, 1, 1, 1, 1, 1, 4, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 11, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 11, 11, 11, 11, 11, 4, 4, 4, 29, 1, 1, 1, 29, 4, 4, 4, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 2, 15, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 11, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 1, 20, 1, 1, 1, 1, 20, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 2, 29, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 15, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 4, 11, 11, 11, 11, 11, 4, 4, 4, 29, 1, 1, 1, 29, 4, 4, 4, 4, 4, 1, 29, 4, 4, 4, 4, 1, 1, 20, 1, 4, 2, 2, 29, 29, 29, 1, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 5, 1, 1, 1, 1, 1, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 12, 1, 29, 1, 1, 4, 4, 4, 1, 1, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 29, 29, 29, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 11, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 1, 2, 2, 1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 2, 15, 2, 1, 1, 1, 1, 1, 1, 1, 2, 1, 29, 29, 29, 1, 1, 1, 1, 1, 2, 15, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 29, 29, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 2, 1, 1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 29, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 29, 14, 14, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 9, 9, 9, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 2, 4, 2, 4, 4, 4, 4, 1, 9, 1, 9, 1, 9, 1, 9, 1, 1, 1, 9, 9, 4, 14, 14, 29, 14, 14, 4, 16, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 16, 16, 14, 14, 14, 14, 14, 4, 1, 11, 1, 10, 9, 9, 9, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 15, 15, 15, 4, 4, 4, 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 29, 14, 14, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4, 14, 14, 14, 14, 14, 4, 1, 1, 1, 4, 9, 9, 9, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 15, 15, 15, 4, 4, 4, 4, 1, 9, 1, 1, 1, 1, 9, 1, 9, 9, 9, 9, 9, 4, 14, 14, 29, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 15, 15, 15, 4, 4, 4, 1, 4, 4, 4, 4, 4, 9, 4, 4, 4, 4, 4, 4, 4, 14, 14, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 4, 15, 15, 13, 1, 9, 1, 9, 1, 9, 9, 1, 11, 9, 1, 1, 1, 1, 9, 13, 14, 14, 29, 14, 29, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 9, 4, 4, 4, 4, 4, 4, 4, 14, 14, 29, 29, 29, 15, 12, 15, 15, 12, 15, 15, 12, 15, 15, 12, 15, 15, 12, 15, 15, 12, 15, 15, 12, 15, 15, 11, 15, 6, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 29, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 9, 4, 4, 26, 14, 14, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        } else {
            map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 12, 12, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 20, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 2, 4, 2, 2, 4, 4, 4, 4, 29, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 1, 1, 4, 1, 1, 4, 1, 1, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 5, 1, 1, 5, 1, 1, 5, 1, 1, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 1, 1, 4, 1, 1, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 20, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 2, 4, 1, 2, 4, 4, 4, 4, 4, 4, 4, 4, 16, 4, 4, 4, 4, 4, 1, 29, 29, 29, 20, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 16, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 9, 9, 9, 9, 10, 14, 14, 10, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 16, 16, 11, 20, 1, 10, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 9, 9, 9, 1, 4, 4, 4, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 2, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 1, 9, 1, 1, 10, 14, 14, 10, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 2, 2, 15, 15, 15, 2, 2, 4, 4, 4, 20, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 4, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 1, 1, 1, 29, 29, 29, 1, 4, 4, 2, 15, 15, 15, 15, 15, 2, 1, 1, 1, 1, 1, 1, 1, 20, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 14, 27, 27, 27, 14, 27, 27, 27, 14, 4, 29, 29, 29, 29, 29, 29, 29, 4, 4, 2, 15, 15, 15, 15, 15, 2, 2, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 14, 14, 27, 14, 11, 14, 27, 14, 14, 5, 29, 29, 29, 29, 29, 29, 29, 4, 4, 2, 15, 15, 15, 15, 15, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 14, 27, 27, 27, 14, 27, 27, 27, 14, 4, 29, 29, 29, 29, 29, 29, 29, 4, 4, 2, 15, 15, 15, 15, 15, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 1, 1, 1, 29, 29, 29, 1, 4, 4, 2, 15, 15, 15, 15, 15, 2, 1, 1, 1, 1, 1, 20, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 2, 2, 15, 15, 15, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 20, 4, 4, 4, 2, 2, 2, 2, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 16, 1, 16, 16, 16, 16, 16, 11, 16, 16, 16, 1, 16, 16, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 5, 1, 1, 1, 1, 1, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 11, 11, 11, 11, 11, 4, 4, 4, 29, 1, 1, 1, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 20, 1, 1, 2, 15, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 29, 2, 2, 2, 15, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 29, 1, 1, 1, 11, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 2, 2, 2, 2, 15, 4, 4, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 29, 29, 29, 14, 29, 29, 29, 29, 29, 2, 2, 15, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 29, 29, 29, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 20, 1, 2, 2, 2, 14, 14, 14, 1, 1, 1, 20, 1, 2, 2, 15, 15, 4, 4, 1, 1, 1, 4, 11, 11, 11, 11, 11, 4, 4, 4, 29, 1, 1, 1, 29, 4, 13, 13, 13, 13, 4, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 5, 1, 1, 1, 1, 1, 4, 4, 4, 1, 29, 29, 29, 1, 4, 13, 13, 13, 4, 13, 13, 13, 4, 13, 13, 4, 13, 13, 4, 13, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 13, 4, 13, 13, 13, 4, 13, 13, 4, 13, 13, 4, 13, 13, 13, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 15, 15, 2, 2, 1, 1, 1, 1, 1, 1, 1, 20, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 25, 4, 4, 15, 15, 15, 2, 2, 2, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 20, 20, 20, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 15, 15, 15, 2, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 20, 1, 20, 20, 20, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 21, 4, 4, 15, 15, 2, 2, 2, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 20, 20, 20, 1, 20, 20, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 15, 15, 2, 2, 1, 1, 1, 1, 1, 1, 20, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 25, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 21, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 29, 12, 29, 16, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 12, 29, 12, 29, 1, 1, 1, 1, 1, 20, 1, 29, 29, 29, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 25, 13, 13, 13, 25, 4, 4, 4, 4, 1, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 25, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 13, 15, 15, 15, 13, 4, 4, 4, 4, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 29, 12, 29, 12, 29, 12, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 13, 15, 15, 15, 13, 4, 4, 4, 4, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 21, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 13, 15, 15, 15, 13, 4, 4, 4, 4, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 29, 29, 12, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 13, 13, 13, 25, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 25, 4, 4, 4, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
        }
        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }


    @Override
    public void initNpcs() {
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell+WIDTH;
        GameScene.add(tower);
        super.initNpcs();
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.EXACTLYRIGHT, 1);
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }

}
