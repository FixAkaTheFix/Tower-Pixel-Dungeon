package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Acidic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM200;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM201;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMW;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWHead;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWWheels;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Eye;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Necromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Scorpio;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shinobi;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Spinner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Swarm;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.TimelessSpirit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogFist;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfHolyNova;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.EnteringScene2;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.scenes.InterlevelScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.DangerIndicator;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Scene;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.internal.net.http.common.Pair;

public class Arena24 extends Arena{
    {
        name = "Phantasmagoria";
        WIDTH = 50;
        HEIGHT = 50;
        viewDistance = 12;

        startGold = 200;
        startLvl = 1;
        arenaDepth = 24;
        maxWaves = 25;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.DOWN;

        amuletCell = 25 + WIDTH*24;
        exitCell = amuletCell;
        towerShopKeeperCell = 18 + 22*WIDTH;
        normalShopKeeperCell = 18  + 26*WIDTH;

        waveCooldownNormal = 20;
        waveCooldownBoss = 20;
    }
    @Override
    protected boolean build() {


        setSize(WIDTH, HEIGHT);

        Painter.fill(this, 0, 0, 50, 50, Terrain.WALL);
        Painter.fill(this, 17, 23, 5, 1, Terrain.PEDESTAL);
        Painter.fill(this, 17, 25, 5, 1, Terrain.PEDESTAL);
        Painter.fill(this, 21, 21, 28, 7, Terrain.EMPTY);
        Painter.fill(this, 16, 22, 5, 5, Terrain.EMPTY_SP);
        buildFlagMaps();

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = new Rat(); break;
            case 2:
                mob = new Gnoll(); break;
            case 3:
                mob = new Crab(); break;
            case 4:
                mob = new Slime(); break;
            case 5:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Goo();
                } else mob = new CausticSlime();
                break;
            case 6:
                mob = new Guard();
                break;
            case 7:
                mob = new Thief(); break;
            case 8:
                mob = new Necromancer(); break;
            case 9:
                mob = new Swarm(); break;
            case 10:
                mob = new Shinobi();
                break;
            case 11:
                mob = new Bat(); break;
            case 12:
                mob = new DM200();
                break;
            case 13:
                mob = new Spinner();
                break;
            case 14:
                mob = new DM200();
                break;
            case 15:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new DMWWheels();
                } else mob = new DMWHead();
                break;
            case 16:
                mob = new Ghoul();
                break;
            case 17:
                mob = new Monk();
                break;
            case 18:
                mob = new Warlock();
                break;
            case 19:
                mob = new Golem();
                break;
            case 20:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Senior();
                } else mob = new Monk();
                break;
            case 21:
                mob = new RipperDemon();
                break;
            case 22:
                mob = new Succubus();
                break;
            case 23:
                mob = new Eye();
                break;
            case 24:
                mob = new SkeletonArmored();
                break;
            case 25:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.BrightFist();
                } else mob = new Scorpio();
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
            case 1: return 4;
            case 2: return 5;
            case 3: return 6;
            case 4: return 7;
            case 5: return 5;
            case 6: return  6;
            case 7: return  7;
            case 8: return  8;
            case 9: return  15;
            case 10: return 7;
            case 11: return 8;
            case 12: return 9;
            case 13: return 10;
            case 14: return 11;
            case 15: return 8;
            case 16: return 8;
            case 17: return 14;
            case 18: return 15;
            case 19: return 7;
            case 20: return 17;
            case 21: return 18;
            case 22: return 19;
            case 23: return 20;
            case 24: return 21;
            case 25: return 22;
        } return 1;
    }

    boolean fifthWaveEnded = false;
    private static final String FIFTHWAVEENDED = "fifthwaveended";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(FIFTHWAVEENDED, fifthWaveEnded);
    }
    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        fifthWaveEnded = bundle.getBoolean(FIFTHWAVEENDED);
    }
    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd =  200 *(2 + wave/5);
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);

    }

    @Override
    public void endWave() {
        super.endWave();
        if (wave%5==0){
            fifthWaveEnded=true;
            AmuletTower tower = null;
            for (Mob mob: mobs){
                if (mob instanceof AmuletTower){
                    tower = (AmuletTower) mob; break;
                }
            }
            if (tower!=null) tower.itWasAWave=false;
            m_12121();
        } else {
            fifthWaveEnded=false;
        }
    }

    private void refill(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int cell = 0; cell < WIDTH*HEIGHT; cell++) {
            if (map[cell]!=Terrain.WALL && map[cell]!=Terrain.EMPTY_SP && Char.findChar(cell)==null){
                candidates.add(cell);
            }
        }
        HashMap<Integer, Float> chancesForTerrain = new HashMap<>();
        chancesForTerrain.put(Terrain.EMPTY, 0.7f);
        chancesForTerrain.put(Terrain.GRASS, 0.1f);
        chancesForTerrain.put(Terrain.STATUE, 0.03f);
        chancesForTerrain.put(Terrain.HIGH_GRASS, 0.1f);
        chancesForTerrain.put(Terrain.CHASM, 0.03f);
        chancesForTerrain.put(Terrain.WATER, 0.04f);
        for (int i : candidates){
            map[i]= Random.chances(chancesForTerrain);
        }
        for (int i = 0; i < 5; i++)dropMany(candidates,
                Generator.random(Generator.Category.SCROLL2),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.SEED),
                Generator.random()
        );
        buildFlagMaps();
    }

    protected void m_12121() {
        int w = wave;
        if (fifthWaveEnded) w++;
        if (w == 6) {
            Painter.fill(this, 21,1,7,27,Terrain.EMPTY);
            Painter.fill(this, 21, 21, 28, 7, Terrain.EMPTY);
        }
        if (w == 11) {
            Painter.fill(this, 21,21,7,28,Terrain.EMPTY);
        }
        if (w==6 || w==11 || w==16 || w==21){
            GameScene.flash(CharSprite.BLACK);
            buildFlagMaps();
            hero.lvl = wave;
            refill();
            Dungeon.switchLevel(this, hero.pos);
            InterlevelScene.mode= InterlevelScene.Mode.CONTINUE;
            Game.switchScene(InterlevelScene.class);

        }
    }

    public void deployMobs(int wave) {
        if (wave < 6)   deploymobs(wave, Direction.TOORIGHT, 2);         else
        if (wave < 11)  deploymobs(wave, Direction.TOOUP, 2);            else
        if (wave < 16)  deploymobs(wave, Direction.TOODOWN, 2);          else
        deploymobs(wave, Direction.RANDOM, 8);
    }
    @Override
    public String tilesTex() {
        int w = wave;
        if (fifthWaveEnded) w++;
        if (w < 6)  return Assets.Environment.TILES_SEWERS_OOZY;          else
        if (w < 11) return Assets.Environment.TILES_PRISON;          else
        if (w < 16) return Assets.Environment.TILES_GOBS;          else
        if (w < 21) return Assets.Environment.TILES_CITY;            else
        return Assets.Environment.TILES_SOULDESERT;
    }

    @Override
    public void affectMob(Mob mob) {
        mob.HP = mob.HT/3;
        Buff.affect(mob, ChampionEnemy.Rejuvenating.class);
    }

    @Override
    public String waterTex() {
        int w = wave;
        if (fifthWaveEnded) w++;
        if (w < 6)  return Assets.Environment.WATER_SEWERS;          else
        if (w < 11) return Assets.Environment.WATER_PRISON;          else
        if (w < 16) return Assets.Environment.WATER_CAVES;          else
        if (w < 21) return Assets.Environment.WATER_CITY;            else
            return Assets.Environment.WATER_HALLS;
    }
    @Override
    public void playLevelMusic() {
        int w = wave;
        if (fifthWaveEnded) w++;
        if (w < 6 ) Music.INSTANCE.play(Assets.Music.SEWERS_TENSE, true); else
        if (w < 11) Music.INSTANCE.play(Assets.Music.PRISON_TENSE, true); else
        if (w < 16) Music.INSTANCE.play(Assets.Music.CAVES_TENSE, true); else
        if (w < 21) Music.INSTANCE.play(Assets.Music.CITY_TENSE, true); else
            Music.INSTANCE.play(Assets.Music.HALLS_TENSE, true);
    }

    @Override
    public String tileName(int tile) {
        if (tile == Terrain.GRASS || tile == Terrain.HIGH_GRASS || tile == Terrain.FURROWED_GRASS) return Messages.get(Arena24.class, "grass_name");
        return super.tileName(tile);
    }

    @Override
    public String tileDesc(int tile) {
        if (tile == Terrain.GRASS || tile == Terrain.HIGH_GRASS || tile == Terrain.FURROWED_GRASS) return Messages.get(Arena24.class, "grass_desc");
        return super.tileDesc(tile);
    }
}
