package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GreatCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.HermitCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Spinner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Swarm;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerMiner;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.quest.CorpseDust;
import com.fixakathefix.towerpixeldungeon.items.quest.Pickaxe;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena12 extends Arena{

    {

        name = "Abandoned mines";
        WIDTH = 50;
        HEIGHT = 50;
        color1 = 0x534f3e;
        color2 = 0xb9d661;

        startGold = 3000;
        startLvl = 11;

        viewDistance = 13;
        arenaDepth = 12;
        maxWaves = 10;

        amuletCell = WIDTH/2 +WIDTH*HEIGHT/2 + 4;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell + 3*WIDTH - 1;
        normalShopKeeperCell = amuletCell +  3*WIDTH - 6;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;

        waveCooldownNormal = 20;
        waveCooldownBoss = 200;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CAVES_BOSS},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
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
                } else mob = Random.oneOf(new Bat(),new Spinner());
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
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 12;
            case 2: return 12;
            case 3: return 13;
            case 4: return 16;
            case 5: return 8;
            case 6: return 25;
            case 7: return 16;
            case 8: return 20;
            case 9: return 11;
            case 10: return 37;
        }
        return 1;
    }

    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        if (wave == 10){
            super.deploymobs(wave, Direction.EXACTLYUP, 5);
        } else if (wave % 2 == 1) {
            super.deploymobs(wave, Direction.TOORIGHT, 1);
        } else super.deploymobs(wave, Direction.TOOLEFT, 1);
    }

    @Override
    protected boolean build() {
        boolean checkUp = true;
        do try {
            setSize(WIDTH, HEIGHT);

            map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 16, 16, 29, 29, 29, 16, 4, 4, 4, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 29, 16, 16, 29, 16, 4, 4, 29, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 4, 4, 4, 4, 16, 16, 16, 16, 29, 16, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 1, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 16, 4, 4, 4, 4, 4, 16, 16, 16, 29, 4, 16, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 16, 4, 4, 16, 16, 16, 16, 29, 16, 4, 4, 4, 29, 4, 4, 4, 4, 4, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 16, 16, 16, 16, 29, 16, 16, 29, 16, 4, 4, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 16, 16, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 16, 1, 20, 1, 4, 4, 4, 4, 16, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 1, 20, 1, 1, 1, 1, 4, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 20, 1, 4, 1, 29, 1, 1, 10, 1, 1, 1, 16, 16, 16, 16, 16, 4, 4, 4, 4, 4, 4, 4, 29, 12, 12, 12, 12, 29, 4, 4, 4, 4, 4, 12, 4, 4, 4, 29, 4, 4, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 10, 16, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 2, 1, 2, 2, 29, 1, 1, 4, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 29, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 29, 12, 2, 2, 2, 12, 1, 29, 1, 1, 4, 1, 1, 1, 4, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 29, 20, 1, 4, 1, 1, 1, 4, 20, 1, 4, 4, 4, 4, 4, 4, 29, 12, 4, 12, 12, 12, 12, 4, 29, 4, 4, 4, 4, 13, 4, 4, 4, 4, 29, 4, 4, 4, 1, 4, 4, 4, 1, 4, 4, 13, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 1, 29, 2, 2, 2, 15, 2, 2, 2, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 4, 4, 4, 29, 29, 12, 12, 2, 2, 2, 4, 2, 20, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 29, 1, 4, 4, 4, 4, 29, 4, 12, 12, 12, 2, 12, 12, 12, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 1, 4, 4, 1, 4, 1, 4, 12, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 1, 29, 1, 1, 4, 2, 2, 2, 12, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 20, 1, 1, 4, 1, 1, 1, 4, 1, 29, 1, 1, 4, 4, 1, 29, 2, 2, 2, 2, 15, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 20, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 1, 4, 4, 1, 29, 1, 2, 12, 12, 2, 2, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 29, 1, 4, 4, 12, 29, 4, 4, 4, 12, 1, 12, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 1, 4, 1, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 13, 4, 4, 4, 13, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 1, 29, 1, 20, 12, 2, 2, 1, 4, 1, 1, 1, 20, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 4, 27, 27, 27, 4, 1, 1, 29, 1, 4, 4, 1, 29, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 14, 14, 14, 4, 1, 1, 29, 1, 4, 4, 1, 29, 1, 1, 12, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 4, 1, 1, 1, 4, 1, 20, 1, 1, 14, 14, 14, 14, 14, 1, 29, 29, 1, 4, 4, 1, 29, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 4, 14, 14, 14, 4, 1, 29, 1, 1, 4, 4, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 20, 1, 1, 1, 1, 20, 1, 4, 14, 14, 14, 4, 1, 29, 1, 20, 4, 4, 1, 1, 20, 1, 4, 1, 1, 1, 4, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 29, 1, 1, 4, 4, 1, 1, 1, 1, 4, 1, 1, 1, 12, 1, 1, 1, 1, 4, 1, 1, 1, 4, 29, 4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 1, 1, 20, 4, 1, 1, 1, 1, 4, 14, 14, 14, 4, 1, 29, 1, 1, 4, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 29, 29, 29, 29, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 4, 27, 27, 27, 4, 1, 29, 1, 1, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 1, 4, 4, 4, 4, 29, 4, 4, 1, 4, 13, 13, 13, 4, 4, 4, 4, 13, 13, 13, 4, 4, 1, 4, 12, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 20, 1, 1, 4, 1, 29, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 29, 1, 1, 4, 4, 1, 20, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 1, 20, 1, 1, 1, 20, 1, 1, 29, 1, 1, 4, 4, 1, 1, 1, 1, 12, 1, 1, 1, 13, 1, 1, 1, 1, 4, 1, 29, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 29, 1, 1, 4, 4, 4, 1, 1, 4, 4, 4, 1, 13, 13, 13, 1, 4, 4, 4, 4, 29, 4, 4, 4, 13, 4, 4, 4, 1, 4, 12, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 1, 1, 1, 4, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 4, 29, 29, 29, 4, 4, 4, 4, 1, 1, 1, 5, 1, 29, 1, 13, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 1, 4, 4, 5, 29, 13, 13, 13, 1, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 29, 29, 4, 4, 4, 4, 4, 4, 1, 1, 5, 1, 29, 1, 13, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 29, 29, 1, 1, 1, 1, 25, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 4, 1, 29, 29, 29, 29, 29, 20, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 9, 9, 9, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 13, 13, 13, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 12, 4, 4, 4, 4, 12, 4, 4, 4, 29, 4, 9, 9, 9, 9, 9, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 13, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 9, 9, 9, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 13, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 20, 20, 20, 1, 1, 1, 1, 25, 4, 1, 1, 25, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 13, 13, 13, 13, 1, 4, 4, 4, 4, 4, 4, 4, 4, 12, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 20, 4, 4, 4, 4, 12, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 4, 4, 4, 4, 13, 13, 1, 1, 1, 1, 20, 20, 1, 1, 4, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 4, 20, 1, 1, 4, 1, 1, 1, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 29, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1, 4, 4, 4, 4, 29, 4, 29, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 29, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 29, 4, 4, 4, 29, 4, 4, 4, 4, 4, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
            buildFlagMaps();
            LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

            transitions.add(exit);

            this.map[exitCell] = Terrain.EXIT;
            this.map[amuletCell] = Terrain.PEDESTAL;

        } catch (Exception tryAgain){checkUp=false;} while (!checkUp);
        systemMapDisplay();
        return true;
    }

    public void systemMapDisplay(){
        for (int x = 0;x<WIDTH;x++){
            System.out.print("\n\n\n");
            System.out.println(x+":");
            for (int y = 0;y<HEIGHT;y++){
                System.out.print(this.map[x+WIDTH*y] + "  ");
            }
        }
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        TowerMiner miner1 = new TowerMiner();
        miner1.pos = amuletCell - 9;
        GameScene.add(miner1);
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.EMPTY) candidates.add(m);
        }

        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(new Gold(50),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WEP_T2),Random.element(candidates));
        this.drop(new CorpseDust(),Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]&&this.map[m]==Terrain.WATER) candidates.add(m);
        }
        for (int i = 0; i < 30; i++){
            this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        }
        this.drop(new CorpseDust(),Random.element(candidates));
        this.drop(new Pickaxe() ,Random.element(candidates));
        candidates.clear();

        super.addDestinations();
    }

    @Override
    public void doStuffEndwave(int wave) {
        for (Mob mob : mobs){
            if (mob instanceof TowerMiner){
                TowerMiner miner = (TowerMiner) mob;
                miner.dropGold(200);
            }
        }
        super.doStuffEndwave(wave);
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return Messages.get(CavesLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_name");
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "water_name");
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
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
    }

}
