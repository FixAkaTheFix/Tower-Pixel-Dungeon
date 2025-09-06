package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Amok;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Frost;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hex;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Humongous;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Ooze;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Slow;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vulnerable;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Monk;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Statue;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatArcher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatMage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatShield;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.IceWall;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCWall;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGraveCrypt;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRatCamp;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.KingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

public class Arena20 extends ArenaCity {



    {
        name = "The King's fall";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 25;
        WIDTH = 71;
        HEIGHT = 71;

        startGold = 13000;
        startLvl = 25;
        arenaDepth = 20;
        maxWaves = 25;

        amuletCell = 35 + WIDTH*57;
        exitCell = amuletCell;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.LEFT;

        normalShopKeeperCell = 41 + WIDTH*67;
        towerShopKeeperCell = 29 + WIDTH*67;
        waveCooldownNormal = 5;
        waveCooldownBoss = 100;
    }

    private final int kingCell = 35 + WIDTH*44;
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
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
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
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
        return 1;
    }

    @Override
    protected boolean build() {


        setSize(WIDTH, HEIGHT);

        map = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 4, 4, 4, 21, 4, 4, 4, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 25, 13, 13, 13, 25, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 13, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 13, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 4, 4, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 4, 4, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 4, 4, 1, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 4, 4, 9, 1, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 1, 9, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2, 2, 1, 4, 4, 1, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 4, 4, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 4, 4, 1, 1, 1, 9, 9, 1, 1, 1, 1, 9, 9, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 4, 4, 1, 1, 1, 1, 1, 9, 9, 9, 9, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 1, 20, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 12, 4, 20, 4, 1, 29, 12, 1, 12, 20, 1, 4, 29, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 1, 1, 20, 20, 1, 20, 1, 1, 20, 1, 9, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 11, 20, 11, 11, 11, 11, 11, 20, 11, 9, 20, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 11, 2, 2, 9, 9, 9, 2, 9, 9, 9, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 4, 4, 1, 11, 2, 9, 9, 9, 2, 9, 9, 9, 2, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 2, 11, 1, 14, 14, 14, 1, 11, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 4, 4, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 0, 0, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 0, 0, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 14, 14, 20, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 14, 14, 14, 14, 14, 14, 20, 14, 14, 14, 20, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 0, 0, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 26, 14, 14, 14, 26, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 0, 0, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 26, 29, 29, 29, 29, 14, 0, 0, 26, 29, 29, 29, 29, 14, 1, 14, 29, 29, 11, 14, 14, 14, 11, 29, 29, 14, 1, 14, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 14, 29, 29, 29, 11, 14, 14, 14, 11, 29, 29, 29, 14, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 1, 4, 4, 1, 11, 29, 25, 29, 29, 29, 29, 29, 29, 20, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 29, 29, 29, 29, 11, 14, 14, 14, 11, 29, 29, 29, 29, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 25, 29, 29, 29, 29, 29, 29, 25, 29, 11, 1, 4, 4, 1, 11, 29, 29, 3, 29, 29, 29, 29, 24, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 14, 14, 14, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 24, 29, 29, 29, 29, 24, 29, 29, 11, 1, 4, 4, 1, 11, 29, 29, 29, 11, 11, 11, 11, 29, 29, 29, 11, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 14, 14, 14, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 29, 11, 11, 11, 11, 29, 29, 29, 11, 1, 4, 4, 1, 11, 29, 29, 29, 11, 20, 20, 11, 29, 29, 29, 11, 1, 9, 29, 29, 29, 29, 29, 0, 0, 26, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 14, 14, 14, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 11, 29, 29, 29, 11, 26, 26, 11, 29, 29, 29, 11, 1, 4, 4, 1, 11, 29, 29, 29, 11, 20, 26, 11, 29, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 29, 29, 29, 14, 14, 26, 14, 14, 14, 26, 14, 14, 29, 29, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 29, 11, 26, 26, 11, 29, 29, 29, 11, 1, 4, 4, 1, 11, 29, 29, 29, 20, 20, 11, 11, 29, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 29, 29, 14, 1, 1, 1, 14, 14, 14, 1, 1, 1, 14, 29, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 29, 11, 11, 11, 11, 29, 29, 29, 11, 1, 4, 4, 1, 11, 29, 29, 20, 29, 29, 29, 29, 3, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 14, 29, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 29, 24, 29, 29, 29, 29, 24, 29, 29, 11, 1, 4, 4, 1, 11, 29, 25, 29, 29, 29, 29, 29, 29, 20, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 29, 25, 29, 29, 29, 29, 29, 29, 25, 29, 11, 1, 4, 4, 1, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 1, 26, 29, 29, 29, 29, 26, 0, 0, 9, 29, 29, 29, 29, 26, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 11, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 11, 1, 4, 4, 1, 11, 11, 11, 11, 11, 11, 11, 13, 29, 29, 29, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 14, 14, 1, 1, 1, 14, 14, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 14, 14, 20, 14, 20, 14, 14, 14, 14, 29, 29, 29, 29, 29, 29, 29, 29, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 25, 11, 11, 11, 25, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 14, 14, 20, 20, 1, 20, 14, 14, 14, 14, 14, 14, 14, 14, 14, 29, 29, 29, 29, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 11, 14, 11, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 14, 14, 14, 20, 20, 20, 20, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 29, 29, 29, 29, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 25, 1, 1, 1, 25, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 29, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 14, 14, 1, 1, 1, 14, 14, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 11, 11, 9, 9, 11, 11, 11, 11, 11, 9, 9, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 11, 9, 9, 9, 2, 2, 2, 2, 2, 9, 9, 11, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 11, 2, 9, 2, 2, 2, 9, 9, 2, 2, 2, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 4, 4, 1, 11, 11, 11, 11, 11, 11, 9, 9, 11, 11, 11, 11, 1, 14, 29, 29, 29, 29, 14, 0, 0, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 11, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 4, 4, 1, 4, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 4, 9, 1, 9, 1, 13, 1, 9, 1, 13, 1, 9, 1, 26, 29, 29, 29, 29, 9, 0, 0, 26, 29, 29, 29, 29, 26, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 13, 1, 27, 1, 13, 1, 13, 1, 14, 29, 29, 29, 29, 14, 0, 0, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 9, 1, 13, 1, 13, 1, 13, 1, 14, 29, 29, 29, 29, 29, 0, 0, 29, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 13, 1, 9, 1, 13, 1, 9, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 9, 1, 9, 1, 13, 1, 9, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 14, 14, 14, 11, 14, 14, 14, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 13, 1, 9, 1, 13, 1, 9, 1, 9, 1, 13, 1, 26, 29, 29, 29, 29, 26, 0, 0, 9, 29, 29, 29, 29, 26, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 13, 1, 27, 1, 9, 1, 9, 1, 13, 1, 13, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 14, 14, 14, 14, 1, 14, 14, 14, 14, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 13, 1, 9, 1, 9, 1, 9, 1, 13, 1, 13, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 14, 14, 14, 1, 1, 1, 14, 14, 14, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 13, 1, 9, 1, 9, 1, 13, 1, 13, 1, 13, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 14, 14, 14, 1, 1, 1, 14, 14, 14, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 9, 1, 14, 29, 29, 29, 29, 29, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 14, 14, 14, 1, 1, 1, 14, 14, 14, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 13, 1, 13, 1, 13, 1, 9, 1, 9, 1, 13, 1, 26, 29, 29, 29, 29, 9, 0, 0, 26, 29, 29, 29, 29, 26, 1, 1, 14, 14, 14, 1, 1, 1, 14, 14, 14, 1, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 13, 1, 13, 1, 9, 1, 9, 1, 13, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 4, 12, 4, 14, 14, 14, 1, 1, 1, 14, 14, 14, 4, 4, 4, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 13, 1, 13, 1, 27, 1, 13, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 4, 1, 11, 14, 14, 14, 1, 1, 1, 14, 14, 14, 11, 1, 4, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 13, 1, 9, 1, 9, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 4, 1, 11, 14, 14, 14, 1, 1, 1, 14, 14, 14, 11, 1, 4, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 4, 9, 1, 9, 1, 13, 1, 9, 1, 13, 1, 9, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 4, 1, 11, 14, 14, 14, 1, 1, 1, 14, 14, 14, 11, 1, 4, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 12, 9, 1, 9, 1, 13, 1, 9, 1, 9, 1, 9, 1, 26, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 4, 1, 11, 14, 14, 14, 1, 1, 1, 14, 14, 14, 11, 1, 4, 29, 29, 29, 29, 26, 0, 0, 26, 29, 29, 29, 29, 26, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 1, 27, 4, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 4, 1, 11, 14, 14, 14, 1, 1, 1, 14, 14, 14, 11, 1, 4, 29, 29, 29, 29, 14, 0, 0, 14, 29, 29, 29, 29, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};

        buildFlagMaps();

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    private BossDwarfKing dwarfKing;
    @Override
    public void initNpcs() {
        super.initNpcs();

        dwarfKing = new BossDwarfKing();
        dwarfKing.pos = kingCell;
        GameScene.add(dwarfKing);
        level.occupyCell(dwarfKing);

        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m] && distance(amuletCell, m)>20) candidates.add(m);
        }
        for (int i = 0; i < 100; i++){
            int x1 = Random.element(candidates);
            this.drop(Random.oneOf(
                    NormalShopKeeper.eliteShopItem(),
                            NormalShopKeeper.normalSecondTierShopItem(),
                            NormalShopKeeper.normalSecondTierShopItem(),
                            NormalShopKeeper.normalSecondTierShopItem(),
                            NormalShopKeeper.normalSecondTierShopItem(),
                            new Gold(Random.Int(50,150))),
                    x1).type = Heap.Type.CHEST;;
        }


    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 1000;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        if (wave == 19) {
            EnemyPortal.createEnemyPortal(kingCell+5, 44);
            EnemyPortal.createEnemyPortal(kingCell-5, 44);
        }
        if (wave == 14) {
            EnemyPortal.createEnemyPortal(kingCell+WIDTH*3, 47);
        }
        super.doStuffEndwave(wave);
    }

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        if (wave == 1){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "start1"),
                            Messages.get(BossDwarfKing.class, "start2"),
                            Messages.get(BossDwarfKing.class, "start3"),
                            Messages.get(BossDwarfKing.class, "start4"),
                    });
        }
    }

    @Override
    public void deployMobs(int wave) {
        if (dwarfKing == null) {
            for (Mob mob : level.mobs) {
                if (mob instanceof BossDwarfKing){
                    dwarfKing = (BossDwarfKing) mob;
                }
            }
        }
        if (wave == 5){
            int crossbows       = 0;
            int wands           = 0;
            int walls           = 0;
            int lightnings      = 0;
            int dartguns        = 0;
            int disintegrations = 0;
            int guards          = 0;
            int tntlogs         = 0;
            int cannons         = 0;
            int graveyards      = 0;
            int rats            = 0;

            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if (m instanceof TowerCrossbow1) crossbows       +=coins;
                    if (m instanceof TowerWand1) wands           +=coins;
                    if (m instanceof TowerCWall &&!(m instanceof IceWall)) walls           +=coins;
                    if (m instanceof TowerLightning1) lightnings      +=coins;
                    if (m instanceof TowerDartgun1) dartguns        +=coins;
                    if (m instanceof TowerDisintegration1) disintegrations +=coins;
                    if (m instanceof TowerGuard1) guards          +=coins;
                    if (m instanceof TowerTntLog) tntlogs         +=coins;
                    if (m instanceof TowerRatCamp) rats        +=coins  + 300*((TowerRatCamp)m).levelCurrent;
                    if (m instanceof TowerCannon1 || m instanceof TowerCannonNuke || m instanceof TowerCannonMissileLauncher) cannons         +=coins;
                    if (m instanceof TowerGrave1 || m instanceof TowerGraveCrypt) graveyards+=coins;
                }
            }
            int maxcoinson = Math.max(crossbows,Math.max(wands,Math.max(walls,Math.max(lightnings,Math.max(dartguns,Math.max(disintegrations,Math.max(guards,Math.max(tntlogs,Math.max(cannons,graveyards)))))))));
            if (maxcoinson == crossbows) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_crossbow"),
                                Messages.get(BossDwarfKing.class, "strategy_crossbow"),
                        });
                deploymobs(5001, Direction.TOOUP,10 );
            } else if (maxcoinson == wands) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_wand"),
                                Messages.get(BossDwarfKing.class, "strategy_wand"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerWand1){
                        Buff.affect(x, Ooze.class).set(300);
                        CellEmitter.center(x.pos).start(ShadowParticle.UP, 0.1f, 10);
                    }
                }
                deploymobs(5002, Direction.TOOUP,10 );
            } else if (maxcoinson == walls) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_wall"),
                                Messages.get(BossDwarfKing.class, "strategy_wall"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerCWall&&!(x instanceof IceWall)){
                        Buff.affect(x, Vulnerable.class, 10000);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5003, Direction.TOOUP,10 );
            } else if (maxcoinson == cannons) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_cannon"),
                                Messages.get(BossDwarfKing.class, "strategy_cannon"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerCannon1 || x instanceof TowerCannonNuke){
                        Buff.affect(x, Hex.class, 10000);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                    if (x instanceof TowerCannonMissileLauncher){
                        Buff.affect(x, Blindness.class, 100);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5004, Direction.TOOUP,10 );
            } else if (maxcoinson == dartguns) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_dartgun"),
                                Messages.get(BossDwarfKing.class, "strategy_dartgun"),
                        });
                deploymobs(5005, Direction.UP,50 );
            } else if (maxcoinson == disintegrations) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_disintegration"),
                                Messages.get(BossDwarfKing.class, "strategy_disintegration"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerDisintegration1){
                        Buff.affect(x, Amok.class, 8);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5006, Direction.TOOUP,10 );
            } else if (maxcoinson == lightnings) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_lightning"),
                                Messages.get(BossDwarfKing.class, "strategy_lightning"),
                        });
                deploymobs(5007, Direction.TOOUP,10 );
            } else if (maxcoinson == guards) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_guard"),
                                Messages.get(BossDwarfKing.class, "strategy_guard"),
                        });
                for (Mob x : level.mobs){
                    if (x instanceof TowerGuard1){
                        Buff.affect(x, Amok.class, 8);
                        CellEmitter.center(x.pos).start(ShadowParticle.CURSE, 0.1f, 10);
                    }
                }
                deploymobs(5008, Direction.TOOUP,10 );
            } else if (maxcoinson == rats) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_ratcamp"),
                                Messages.get(BossDwarfKing.class, "strategy_ratcamp"),
                        });
                Sample.INSTANCE.play(Assets.Sounds.SHATTER);
                for (Mob x : level.mobs){
                    if (x instanceof TowerRatCamp){
                        Buff.affect(x, Frost.class, 200);
                    }
                }
                deploymobs(5008, Direction.TOOUP,10 );
            } else if (maxcoinson == graveyards) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_graveyard"),
                                Messages.get(BossDwarfKing.class, "strategy_graveyard"),
                        });
                deploymobs(5009, Direction.TOOUP,10 );
            } else if (maxcoinson == tntlogs) {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "favtower_tntlog"),
                                Messages.get(BossDwarfKing.class, "strategy_tntlog"),
                        });
                deploymobs(5010, Direction.TOOUP,10 );
            }

        } else if (wave == 10){
            int magical       = 0;
            int nonmagical           = 0;

            int east = 0;
            int west = 0;
            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if ((m instanceof TowerWand1 || m instanceof TowerLightning1 || m instanceof TowerDisintegration1))
                        magical+=coins;
                    else if (m instanceof TowerRatCamp){
                        magical += 500 * (((TowerRatCamp)m).minionencodingsCurrent/10000)%10;
                    }
                    else if(!(m instanceof TowerCWall)) nonmagical+=coins;
                    if (m.pos%WIDTH > 35) east+=coins;
                    if (m.pos%WIDTH < 35) west+=coins;

                }
            }
            int mistakes = 0;
            String speechtype = "";
            String speechflank = "";
            String speechfinal = "";
            int num;
            Direction d;

            if (nonmagical > magical + 3000) {
                speechtype = Messages.get(BossDwarfKing.class,"direction_physical");
                num = 10001;
                mistakes++;
            } else if (magical > nonmagical + 1000) {;
                speechtype = Messages.get(BossDwarfKing.class,"direction_magical");
                num = 10002;
                mistakes++;
            } else {
                speechtype = Messages.get(BossDwarfKing.class,"direction_both");
                num = 10003;
            }
            if (west > east + 1000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction_westpower");
                d = Direction.TOORIGHT;
                mistakes++;
            } else if (east > west + 1000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction_eastpower");
                d = Direction.TOOLEFT;
                mistakes++;
            } else {
                speechflank = Messages.get(BossDwarfKing.class,"direction_balanced");
                d = Direction.TOOUP;
            }
            if (mistakes == 2){
                speechfinal = Messages.get(BossDwarfKing.class,"direction_verybad");
            } else if (mistakes == 1){
                speechfinal = Messages.get(BossDwarfKing.class,"direction_bad");
            } else {
                speechfinal = Messages.get(BossDwarfKing.class,"direction_good");
            }
            deploymobs(num, d, 5);
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            speechtype,
                            speechflank,
                            speechfinal
                    }
                    );
        } else if (wave == 14){
            if (level.distance(amuletCell, Dungeon.hero.pos)>20){
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_hero")
                        });
                for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(Dungeon.hero.pos + i)== null){
                    Mob skele = Random.oneOf(new Skeleton(), new Skeleton(), new SkeletonArmored());
                    skele.pos = Dungeon.hero.pos + i;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            } else if (level.distance(amuletCell, Dungeon.hero.pos)>10){
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_amulet")
                        });
                for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(amuletCell + i)== null){
                    Skeleton skele = Random.oneOf(new Skeleton(), new Skeleton(), new SkeletonArmored());
                    skele.pos = amuletCell + i;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            } else {
                WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                        new String[]{
                                Messages.get(BossDwarfKing.class, "rise_walls")
                        });

                HashSet<Integer> fudgeconcurrent = new HashSet<>();
                for (Mob wall : level.mobs) if (wall instanceof TowerCWall || wall instanceof TowerGuard1) for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(wall.pos + i)== null)
                fudgeconcurrent.add(wall.pos);
                for (int wall2 : fudgeconcurrent) {
                    Skeleton skele = Random.oneOf(new Skeleton(), new Skeleton(), new SkeletonArmored());
                    skele.pos = wall2;
                    skele.state = skele.HUNTING;
                    GameScene.add(skele);
                }

            }
            deploymobs(14, Direction.TOOUP, 10);
        }else if (wave == 17){
            int magical       = 0;
            int nonmagical           = 0;

            int west = 0;
            int east = 0;
            for (Mob m : level.mobs){
                if (m instanceof Tower){
                    int coins = ((Tower) m).cost;
                    if ((m instanceof TowerWand1 || m instanceof TowerLightning1 || m instanceof TowerDisintegration1))
                        magical+=coins;
                    else if(!(m instanceof TowerCWall)) nonmagical+=coins;
                    if (m.pos%WIDTH > 35) west+=coins;
                    if (m.pos%WIDTH < 35) east+=coins;

                }
            }
            int mistakes = 0;
            String speechtype = "";
            String speechflank = "";
            String speechfinal = "";
            int num;
            Direction d;

            if (nonmagical > magical + 5000) {
                speechtype = Messages.get(BossDwarfKing.class,"direction2_physical");
                num = 17001;
                mistakes++;
            } else if (magical > nonmagical + 3000) {;
                speechtype = Messages.get(BossDwarfKing.class,"direction2_magical");
                num = 17002;
                mistakes++;
            } else {
                speechtype = Messages.get(BossDwarfKing.class,"direction2_both");
                num = 17003;
            }
            if (east > west + 3000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_eastpower");
                d = Direction.TOOLEFT;
                mistakes++;
            } else if (west > east + 3000) {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_westpower");
                d = Direction.TOORIGHT;
                mistakes++;
            } else {
                speechflank = Messages.get(BossDwarfKing.class,"direction2_balanced");
                d = Direction.TOOUP;
            }
            if (mistakes == 2){
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_verybad");
            } else if (mistakes == 1){
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_bad");
            } else {
                speechfinal = Messages.get(BossDwarfKing.class,"direction2_good");
            }
            deploymobs(num, d, 5);
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            speechtype,
                            speechflank,
                            speechfinal
                    }
            );
        }else if (wave == 20){
            WndDialogueWithPic.dialogue(new RatKingSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "rkdkrat1"),
                            Messages.get(BossDwarfKing.class, "rkdkrat2")
                    }
            );

            for (int x = 34; x<37; x++) {
                for (int i = 0; i <5;i++){
                Rat helper = new CampRatMage();
                helper.alignment = Char.Alignment.ALLY;
                helper.pos = x+WIDTH*69;
                GameScene.add(helper);
                Rat helper1 = new CampRatShield();
                helper1.alignment = Char.Alignment.ALLY;
                helper1.pos = x+WIDTH*69;
                GameScene.add(helper1);
                Rat helper2 = new CampRatArcher();
                helper2.alignment = Char.Alignment.ALLY;
                helper2.pos = x+WIDTH*69;
                GameScene.add(helper2);
                }
            }
        } else if (wave == 23){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "prespeech1"),
                            Messages.get(BossDwarfKing.class, "prespeech2"),
                            Messages.get(BossDwarfKing.class, "prespeech3"),
                            Messages.get(BossDwarfKing.class, "prespeech4"),
                            Messages.get(BossDwarfKing.class, "prespeech5"),
                            Messages.get(BossDwarfKing.class, "prespeech6")
                    }
            );
            deploymobs(23, Direction.TOORIGHT, 10);
        } else if (wave == 24){
            deploymobs(24, Direction.TOOLEFT, 10);
        } else if (wave == 25){
            WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(BossDwarfKing.class, "name"),
                    new String[]{
                            Messages.get(BossDwarfKing.class, "speech1"),
                            Messages.get(BossDwarfKing.class, "speech2"),
                            Messages.get(BossDwarfKing.class, "speech3")
                    }
            );
            deploymobs(25, Direction.UP, 10);
            if (dwarfKing == null) {
               if (Char.findChar(kingCell)!=null && Char.findChar(kingCell)instanceof BossDwarfKing) dwarfKing = (BossDwarfKing) Char.findChar(kingCell);
            }
            if (dwarfKing == null) {
                for (Mob mob : level.mobs) {
                    if (mob instanceof BossDwarfKing){
                        dwarfKing = (BossDwarfKing) mob;
                    }
                }
            }
            dwarfKing.awaken();
        } else {
            deploymobs(wave, Direction.TOOUP, 5);
        }
        if (wave > 20){
            for (int x = 34; x<37; x++) {
                for (int i = 0; i <2;i++){
                    Rat helper = new CampRatMage();
                    helper.alignment = Char.Alignment.ALLY;
                    helper.pos = x+WIDTH*69;
                    GameScene.add(helper);
                    Rat helper1 = new CampRatShield();
                    helper1.alignment = Char.Alignment.ALLY;
                    helper1.pos = x+WIDTH*69;
                    GameScene.add(helper1);
                    Rat helper2 = new CampRatArcher();
                    helper2.alignment = Char.Alignment.ALLY;
                    helper2.pos = x+WIDTH*69;
                    GameScene.add(helper2);
                }
            }
        }
    }

    @Override
    public void affectMob(Mob mob) {
        super.affectMob(mob);
        Buff.affect(mob, ChampionEnemy.Giant.class);
        Buff.affect(mob, Humongous.class);
        Buff.affect(mob, Slow.class,10000);
        Buff.affect(mob, Strength.class,10000);
    }

    @Override
    public void playLevelMusic() {

        boolean dkAliveAndReadyToKill = false;
        for (Mob m : mobs){
            if (m instanceof BossDwarfKing && ((BossDwarfKing)m).battleMode!=0) {
                dkAliveAndReadyToKill = true;
                break;
            }
        }
        if (dkAliveAndReadyToKill){
            Music.INSTANCE.play(Assets.Music.CITY_BOSS_SPEDUP, true);
            return;
        } else Music.INSTANCE.play(Assets.Music.CITY_BOSS, true);
    }
}
