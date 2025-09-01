package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.Dungeon.updateLevelExplored;
import static com.fixakathefix.towerpixeldungeon.Dungeon.win;

import com.badlogic.gdx.utils.Timer;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Minion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Acidic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossYog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Eye;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Necromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Scorpio;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SpectralNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.TimelessSpirit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Warlock;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogDzewa;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogFist;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatArcher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatLeader;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatMage;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRatShield;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Pushing;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.armor.MailArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.PlateArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.ScaleArmor;
import com.fixakathefix.towerpixeldungeon.items.artifacts.BrokenHourglass;
import com.fixakathefix.towerpixeldungeon.items.artifacts.ChaliceOfBlood;
import com.fixakathefix.towerpixeldungeon.items.artifacts.EtherealChains;
import com.fixakathefix.towerpixeldungeon.items.artifacts.HornOfPlenty;
import com.fixakathefix.towerpixeldungeon.items.artifacts.MasterThievesArmband;
import com.fixakathefix.towerpixeldungeon.items.artifacts.RoseSeed;
import com.fixakathefix.towerpixeldungeon.items.artifacts.SandalsOfNature;
import com.fixakathefix.towerpixeldungeon.items.artifacts.TalismanOfForesight;
import com.fixakathefix.towerpixeldungeon.items.artifacts.UnstableSpellbook;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfExperience;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfImmortality;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfDemonicSkull;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfGolems;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfHolyNova;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfMetamorphosis;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAggression;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.BerserkerAxe;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.BoneMachete;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.DarksteelSaber;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.DualHatchet;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Glaive;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.HandAxe;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.IronWhip;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.StoneHammer;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Sword;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.WarHammer;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.Bolas;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingClub;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Firebloom;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingAvatarSprite;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.YogSprite;
import com.fixakathefix.towerpixeldungeon.tiles.CustomTilemap;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.AttackIndicator;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Tilemap;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena25 extends ArenaHalls {


    {
        name = "Altar";
        WIDTH = 71;
        HEIGHT = 71;
        viewDistance = 100;

        startGold = 10000;
        startLvl = 20;
        arenaDepth = 25;
        maxWaves = 25;

        normalShopKeeperCell = 30 + WIDTH*68;
        towerShopKeeperCell = 40  + WIDTH*68;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.UP;

        amuletCell = 35 + 62 * WIDTH;
        exitCell = amuletCell;

        waveCooldownNormal = 30;
        waveCooldownBoss = 30;
    }

    public final static int ROOM_LEFT	= 71 / 2 - 4;
    public final static int ROOM_RIGHT	= 71 / 2 + 4;
    public final static int ROOM_TOP		= 8;
    public final static int ROOM_BOTTOM	= ROOM_TOP + 8;

    public BossYog yog;

    @Override
    protected boolean build() {

        setSize(WIDTH, HEIGHT);
        viewDistance = 3;
        map = new int[]{
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 21, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 21, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 11, 11, 11, 1, 29, 0, 0, 0, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 0, 0, 0, 29, 1, 11, 11, 11, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 11, 15, 11, 1, 29, 0, 12, 12, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 12, 12, 0, 29, 1, 11, 15, 11, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 11, 15, 11, 1, 29, 0, 12, 12, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 12, 12, 0, 29, 1, 11, 15, 11, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 29, 0, 0, 0, 0, 29, 1, 26, 14, 14, 14, 14, 14, 26, 1, 29, 0, 0, 0, 0, 29, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 11, 11, 11, 11, 11, 15, 11, 1, 29, 29, 29, 29, 29, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 29, 29, 29, 29, 29, 1, 11, 15, 11, 11, 11, 11, 11, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 11, 15, 15, 15, 15, 15, 11, 1, 29, 1, 1, 1, 1, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 1, 1, 1, 1, 29, 1, 11, 15, 15, 15, 15, 15, 11, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 11, 11, 11, 11, 11, 11, 11, 1, 29, 1, 25, 25, 1, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 1, 25, 25, 1, 29, 1, 11, 11, 11, 11, 11, 11, 11, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 1, 1, 1, 1, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 1, 1, 1, 1, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 26, 14, 14, 14, 14, 14, 26, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 1, 25, 1, 29, 0, 12, 12, 0, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 1, 1, 1, 29, 0, 0, 0, 0, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 1, 11, 14, 14, 14, 14, 14, 11, 1, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 14, 14, 14, 14, 14, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 14, 14, 14, 14, 14, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 29, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 1, 1, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 14, 14, 14, 14, 14, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 26, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 14, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 14, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 11, 1, 16, 14, 14, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 14, 14, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 14, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 14, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 14, 14, 1, 1, 1, 1, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 14, 14, 1, 11, 15, 11, 1, 14, 14, 14, 1, 11, 15, 11, 1, 1, 1, 1, 1, 14, 14, 16, 1, 11, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 14, 14, 1, 11, 11, 11, 1, 14, 14, 14, 1, 11, 11, 11, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 10, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 10, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 11, 11, 11, 11, 11, 14, 14, 14, 14, 14, 11, 11, 11, 11, 11, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 14, 14, 14, 14, 14, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 21, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4
        };
        {
            Painter.fill(this, ROOM_LEFT, ROOM_TOP, 9, 9, Terrain.EMPTY_SP );

            Painter.fill(this, ROOM_LEFT, ROOM_TOP, 9, 2, Terrain.WALL_DECO );
            Painter.fill(this, ROOM_LEFT, ROOM_BOTTOM-1, 2, 2, Terrain.WALL_DECO );
            Painter.fill(this, ROOM_RIGHT-1, ROOM_BOTTOM-1, 2, 2, Terrain.WALL_DECO );

            Painter.fill(this, ROOM_LEFT+3, ROOM_TOP+2, 3, 4, Terrain.EMPTY );

            CustomTilemap vis = new CenterPieceVisuals();
            vis.pos(ROOM_LEFT, ROOM_TOP+1);
            customTiles.add(vis);
            vis = new CenterPieceWalls();
            vis.pos(ROOM_LEFT, ROOM_TOP);
            customWalls.add(vis);
        }

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        for( CustomTilemap t : customTiles){
            if (t instanceof CenterPieceVisuals){
                ((CenterPieceVisuals) t).updateState();
            }
        }
        for( CustomTilemap t : customWalls){
            if (t instanceof CenterPieceWalls){
                ((CenterPieceWalls) t).updateState();
            }
        }


        return true;
    }

    public void startDialogue(){
        if (yog.phase == 0) WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                new String[]{
                        Messages.get(RatKing.class, "l25w1start1"),
                        Messages.get(RatKing.class, "l25w1start2"),
                        Messages.get(RatKing.class, "l25w1start3"),
                        Messages.get(RatKing.class, "l25w1start4"),
                        Messages.get(RatKing.class, "l25w1start5"),
                },
                new byte[]{
                        WndDialogueWithPic.RUN,
                        WndDialogueWithPic.IDLE
                });
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        yog = new BossYog();
        yog.pos = WIDTH/2 + 12*WIDTH;
        GameScene.add(yog);
        amuletTower.stalled = true;
        for (int y = 0; y<WIDTH; y++){
            passable[32 + WIDTH*y] = false;
            passable[38 + WIDTH*y] = false;
        }
    }


    @Override
    public void playLevelMusic() {
        if (yog.phase > 0) Music.INSTANCE.playTracks(
                new String[]{Assets.Music.BOSS_TRIAL},
                new float[]{1},
                false);
        else Music.INSTANCE.end();
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            //riiper = 60, scorpio = 110, Succubus = 80 + heal, Necromancer = 40 + skeleton = 25, TS = 5 hits, eye = 100 + 2 hitsav
            case 1:
                mob = new RipperDemon(); break;
            case 2:
                mob = new Succubus(); break;
            case 3:
                mob = new RipperDemon(); break;
            case 4:
                mob = new Eye(); break;
            case 5:
                mob = new Succubus(); break;
            case 6:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Acidic();
                } else mob = new Scorpio();
                break;
            case 7:
                mob = new Eye(); break;
            case 8:
                mob = new Skeleton(); break;
            case 9:
                mob = new RipperDemon(); break;
            case 10:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.BurningFist();
                } else mob = new RipperDemon();
                break;
            case 11:
                mob = new Acidic(); break;
            case 12:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.DarkFist();
                } else mob = new Wraith();
                break;
            case 13:
                mob = new Wraith();
                break;
            case 14:
                mob = new Eye();
                break;
            case 15:
                mob = new Acidic();
                break;
            case 16:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.RottingFist();
                } else mob = new Succubus();
                break;
            case 17:
                mob = new Skeleton();
                break;
            case 18:
                mob = new RipperDemon();
                break;
            case 19:
                mob = new Succubus();
                break;
            case 20:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.RustedFist();
                } else mob = new Scorpio();
                break;
            case 21:
                mob = new Skeleton();
                break;
            case 22:
                mob = new Warlock();
                break;
            case 23:
                mob = new RipperDemon();
                break;
            case 24:
                mob = new Warlock();
                break;
            case 25:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new YogFist.SoiledFist();
                } else mob = Random.oneOf(new Succubus(), new RipperDemon());
                break;
            case 8055:
                RipperDemon demon = new RipperDemon();
                Buff.affect(demon, Minion.class);
                mob = demon;
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
            case 1: return 10;
            case 2: return 9;
            case 3: return 12;
            case 4: return 9;
            case 5: return 12;
            case 6: return 12;
            case 7: return 12;
            case 8: return 40;
            case 9: return 20;
            case 10: return 23;
            case 11: return 15;
            case 12: return 40;
            case 13: return 100;
            case 14: return 16;
            case 15: return 17;
            case 16: return 21;
            case 17: return 50;
            case 18: return 30;
            case 19: return 23;
            case 20: return 25;
            case 21: return 150;
            case 22: return 50;
            case 23: return 60;
            case 24: return 50;
            case 25: return 50;
            case 8055: return 2 + level.wave/10;
        } return 1;
    }

    @Override
    public void affectMob(Mob mob) {
        Buff.affect(mob, ChampionEnemy.Copying.class);
    }

    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);
        if (wave == 3){
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w3start1"),
                            Messages.get(RatKing.class, "l25w3start2"),

                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE
                    });
        }
    }

    private void spawnRats(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS25){
            int cell = amuletCell + i;
            if (Char.findChar(cell)==null && Dungeon.level.passable[cell]) candidates.add(cell);
        }
        if (!candidates.isEmpty())for (int i = 0; i < 5; i++){
            Mob rat = Random.oneOf(new CampRatMage(), new CampRatArcher(), new CampRatLeader(), new CampRatShield());
            rat.pos = Random.element(candidates);
            GameScene.add(rat);
            rat.beckon(yog.pos);
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 2000;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        if (wave == 5){
            yog.weaken();
            spawnRats();

            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w5end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave == 10){
            yog.weaken();
            spawnRats();
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w10end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave == 15){
            yog.weaken();
            spawnRats();
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w15end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave == 20){
            yog.weaken();
            spawnRats();
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w20end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }
        if (wave == 25){
            yog.weaken();
            spawnRats();
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l25w25end1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN
                    });
        }

        super.doStuffEndwave(wave);

    }

    public void deployMobs(int wave) {
        if (wave % 5 == 2) deploymobs(wave, Direction.TOORIGHT, 3);
        else if (wave % 5 == 4) deploymobs(wave, Direction.TOOLEFT, 3);
        else deploymobs(wave, Direction.UP, 10);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        for (Mob mob : mobs){
            if (mob instanceof BossYog){
                yog = (BossYog) mob;
                break;
            }
        }
        if (yog.phase == 0){
            viewDistance = 3;
            for (int y = 0; y<WIDTH; y++){
                passable[32 + WIDTH*y] = false;
                passable[38 + WIDTH*y] = false;
            }
        }
        else viewDistance = 100;
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.PEDESTAL && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        dropMany(candidates,
                new ChaliceOfBlood(),
                new HornOfPlenty(),
                new TalismanOfForesight(),
                new EtherealChains(),
                new BrokenHourglass(),
                new SandalsOfNature(),
                new MasterThievesArmband(),
                new UnstableSpellbook(),
                new RoseSeed()
                );
        candidates.clear();

        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.GRASS && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 10; i++) dropMany(candidates,
                Generator.random(Generator.Category.SEED),
                new Firebloom.Seed(),
                Generator.random()
        );
        candidates.clear();

        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.EMPTY && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 3; i++) dropMany(candidates,
                Generator.random(Generator.Category.SCROLL2),
                Generator.random(Generator.Category.POTION)
        );
        candidates.clear();

        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.EMPTY_SP && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 4; i++) dropMany(candidates,
                Generator.random(Generator.Category.BOMB),
                Generator.random(Generator.Category.STONE)
        );
        drop(new ScrollOfDemonicSkull(), 11 + 52*((Arena25)level).width());
        drop(new ScrollOfMetamorphosis(), 59 + 58*((Arena25)level).width());
        MailArmor armor2= new MailArmor();
        armor2.upgrade(6);
        BoneMachete machete = new BoneMachete();
        machete.upgrade(4);
        Glaive glaive = new Glaive();
        glaive.upgrade(4);
        StoneHammer hammer = new StoneHammer();
        hammer.upgrade(4);
        drop(armor2, 35 + WIDTH*55);
        drop(glaive, 36 + WIDTH*43);
        drop(hammer, 35 + WIDTH*31);
        drop(machete, 34 + WIDTH*25);
        super.addDestinations();
    }


    public static class CenterPieceVisuals extends CustomTilemap {

        {
            texture = Assets.Environment.HALLS_SP;

            tileW = 9;
            tileH = 8;
        }

        private static final int[] map = new int[]{
                8,  9, 10, 11, 11, 11, 12, 13, 14,
                16, 17, 18, 27, 19, 27, 20, 21, 22,
                24, 25, 26, 19, 19, 19, 28, 29, 30,
                24, 25, 26, 19, 19, 19, 28, 29, 30,
                24, 25, 26, 19, 19, 19, 28, 29, 30,
                24, 25, 34, 35, 35, 35, 34, 29, 30,
                40, 41, 36, 36, 36, 36, 36, 40, 41,
                48, 49, 36, 36, 36, 36, 36, 48, 49
        };

        @Override
        public Tilemap create() {
            Tilemap v = super.create();
            updateState();
            return v;
        }

        private void updateState(){
            if (vis != null){
                int[] data = map.clone();
                if (Dungeon.level.map[Dungeon.level.exit()] == Terrain.EXIT) {
                    data[4] = 19;
                    data[12] = data[14] = 31;
                }
                vis.map(data, tileW);
            }
        }
    }

    public static class CenterPieceWalls extends CustomTilemap {

        {
            texture = Assets.Environment.HALLS_SP;

            tileW = 9;
            tileH = 9;
        }

        private static final int[] map = new int[]{
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                -1, -1, -1, -1, -1, -1, -1, -1, -1,
                32, 33, -1, -1, -1, -1, -1, 32, 33,
                40, 41, -1, -1, -1, -1, -1, 40, 41,
        };

        @Override
        public Tilemap create() {
            Tilemap v = super.create();
            updateState();
            return v;
        }

        private void updateState(){
            if (vis != null){
                int[] data = map.clone();
                if (Dungeon.level.map[Dungeon.level.exit()] == Terrain.EXIT) {
                    data[3] = 1;
                    data[4] = 0;
                    data[5] = 2;
                    data[13] = 23;
                }
                vis.map(data, tileW);
            }
        }

    }

}
