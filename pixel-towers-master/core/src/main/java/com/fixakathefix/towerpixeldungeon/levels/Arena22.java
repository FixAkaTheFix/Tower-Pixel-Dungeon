package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Eye;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Golem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Senior;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmored;
import com.fixakathefix.towerpixeldungeon.actors.mobs.SkeletonArmoredShielded;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.armor.PlateArmor;
import com.fixakathefix.towerpixeldungeon.items.armor.ScaleArmor;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfExperience;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfStrength;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCorrosiveGas;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfImmortality;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfAnimation;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfChallenge;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfEnchantment;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfGolems;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAggression;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.BerserkerAxe;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.DarksteelSaber;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.DualHatchet;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Glaive;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.HandAxe;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.IronWhip;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Sword;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.WarHammer;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.Bolas;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingClub;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.ThrowingHammer;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.plants.Firebloom;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena22 extends ArenaHalls {
    {
        name = "The Forge";
        WIDTH = 50;
        HEIGHT = 50;
        viewDistance = 13;

        startGold = 2000;
        startLvl = 21;
        arenaDepth = 22;
        maxWaves = 15;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        amuletCell = 9 + WIDTH*24;
        exitCell = amuletCell;
        towerShopKeeperCell = 4 + 20*WIDTH;
        normalShopKeeperCell = 4 + 28*WIDTH;

        waveCooldownNormal = 50;
        waveCooldownBoss = 200;
    }
    @Override
    protected boolean build() {
        if (mode == WndModes.Modes.CHALLENGE) {
            slot1 = TowerInfo.AllTowers.GUARD;
            slot2 = TowerInfo.AllTowers.CROSSBOW;
            slot3 = TowerInfo.AllTowers.CROSSBOW;
            slot4 = TowerInfo.AllTowers.GUARD;
        }

        setSize(WIDTH, HEIGHT);

        map = new int[]{
                4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 29, 29, 29, 4, 1, 4, 29, 29, 29, 4, 1, 4, 29, 29, 29, 4, 1, 4, 29, 29, 29, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 12, 11, 11, 11, 12, 1, 12, 11, 11, 11, 12, 1, 12, 11, 11, 11, 12, 1, 12, 11, 11, 11, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 1, 1, 4, 11, 11, 11, 4, 1, 4, 11, 11, 11, 4, 1, 4, 11, 11, 11, 4, 1, 4, 11, 11, 11, 4, 1, 1, 14, 14, 14, 1, 1, 1, 4, 1, 1, 1, 13, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 3, 3, 3, 4, 1, 4, 3, 3, 3, 4, 1, 4, 3, 3, 3, 4, 1, 4, 3, 3, 3, 4, 1, 1, 14, 14, 14, 1, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 12, 4, 4, 4, 12, 1, 1, 14, 14, 14, 1, 1, 1, 4, 1, 9, 1, 29, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 9, 11, 9, 1, 1, 1, 9, 11, 9, 1, 1, 1, 9, 11, 9, 1, 1, 1, 9, 11, 9, 1, 1, 1, 14, 14, 14, 1, 1, 1, 4, 9, 11, 9, 29, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 4, 1, 9, 1, 29, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 12, 16, 12, 4, 4, 4, 12, 16, 12, 4, 4, 4, 12, 16, 12, 4, 4, 4, 12, 16, 12, 4, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 13, 13, 4, 20, 20, 1, 1, 20, 1, 20, 1, 20, 20, 1, 1, 1, 1, 20, 1, 1, 1, 20, 1, 20, 4, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 9, 9, 4, 20, 11, 20, 11, 1, 11, 1, 11, 20, 11, 20, 11, 1, 11, 1, 11, 1, 11, 1, 11, 20, 4, 13, 13, 13, 13, 13, 13, 10, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 12, 4, 4, 12, 4, 4, 12, 4, 4, 12, 4, 4, 12, 4, 4, 12, 4, 4, 12, 4, 4, 9, 13, 14, 14, 14, 9, 9, 9, 13, 13, 13, 9, 9, 13, 13, 13, 9, 9, 9, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 9, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 9, 13, 1, 1, 1, 1, 9, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 9, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 1, 1, 9, 9, 1, 9, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 11, 11, 29, 11, 11, 29, 11, 11, 29, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 14, 1, 14, 1, 14, 14, 14, 14, 14, 1, 1, 1, 29, 4, 4, 29, 4, 4, 29, 4, 4, 29, 1, 1, 1, 14, 14, 14, 20, 20, 1, 1, 20, 1, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 14, 14, 14, 14, 14, 14, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 4, 4, 21, 14, 14, 14, 14, 14, 14, 1, 11, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 4, 4, 14, 14, 14, 14, 14, 14, 14, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 14, 14, 14, 1, 14, 1, 14, 14, 14, 14, 14, 1, 1, 1, 29, 4, 4, 29, 4, 4, 29, 4, 4, 29, 1, 1, 1, 14, 14, 14, 1, 1, 1, 20, 20, 20, 20, 1, 1, 20, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 29, 11, 11, 29, 11, 11, 29, 11, 11, 29, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 9, 1, 9, 1, 1, 9, 9, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 13, 13, 4, 1, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 9, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 11, 11, 11, 11, 11, 11, 11, 14, 5, 14, 27, 4, 4, 4, 4, 4, 4, 4, 1, 1, 9, 13, 9, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 13, 13, 13, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 4, 14, 14, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 13, 4, 4, 4, 4, 4, 4, 4, 4, 4, 13, 13, 13, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 14, 14, 1, 1, 11, 11, 11, 11, 11, 11, 11, 14, 4, 11, 11, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 4, 29, 29, 29, 1, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 4, 15, 15, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 4, 29, 29, 29, 29, 1, 1, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 29, 29, 0, 0, 29, 29, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 25, 0, 0, 0, 0, 25, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 29, 29, 0, 0, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 4, 4, 29, 29, 29, 29, 29, 29, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 29, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 29, 29, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 1, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 1, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 27, 27, 27, 1, 20, 20, 1, 25, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 1, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 20, 20, 1, 25, 29, 9, 29, 25, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 14, 14, 14, 14, 14, 20, 20, 1, 1, 9, 11, 9, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 1, 1, 14, 1, 1, 1, 14, 20, 20, 20, 25, 29, 9, 29, 25, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 29, 29, 29, 29, 29, 29, 29, 29, 1, 1, 1, 1, 1, 1, 1, 1, 5, 14, 14, 14, 14, 14, 5, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 25, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4
        };

        buildFlagMaps();

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }
    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.HALLS_TENSE},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1:
                mob = new Eye(); break;
            case 2:
                mob = new SkeletonArmored(); break;
            case 3:
                mob = new Golem(); break;
            case 4:
                mob = Random.oneOf(new Golem(), new Eye()); break;
            case 5:
                mob = new RipperDemon(); break;
            case 6:
                if (!bossSpawned) {
                    bossSpawned = true;
                    mob = new Eye();
                } else mob = new SkeletonArmored();
                break;
            case 7:
                mob = new Golem(); break;
            case 8:
                mob = Random.oneOf(new Eye(), new RipperDemon()); break;
            case 9:
                mob = new SkeletonArmored(); break;
            case 10:
                mob = new RipperDemon();
                break;
            case 11:
                mob = new Eye();
                break;
            case 12:
                mob = new Golem();
                break;
            case 13:
                mob = new SkeletonArmored();
                break;
            case 14:
                mob = Random.oneOf(new Eye(), new Golem());
                break;
            case 15:
                mob = new RipperDemon();
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
            case 1: return 2;
            case 2: return 3;
            case 3: return 3;
            case 4: return 3;
            case 5: return 7;
            case 6: return 6;
            case 7: return 6;
            case 8: return 7;
            case 9: return 10;
            case 10: return 20;
            case 11: return 10;
            case 12: return 10;
            case 13: return 10;
            case 14: return 20;
            case 15: return 40;
        } return 1;
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 500;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.TOORIGHT, 2);
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.PEDESTAL && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }

        for (int i = 0;i < 5; i++) dropMany(candidates,
                new ThrowingHammer(),
                new HandAxe(),
                new BerserkerAxe(),
                new Glaive(),
                new ScaleArmor(),
                new PlateArmor(),
                new ThrowingClub(),
                new Bolas(),
                new IronWhip(),
                new Sword(),
                new DualHatchet()
        );
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.WATER && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 4; i++) dropMany(candidates,
                new PotionOfStrength(),
                new PotionOfImmortality(),
                new PotionOfCorrosiveGas(),
                new PotionOfExperience()
        );
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.EMPTY && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 8; i++) dropMany(candidates,
                new ScrollOfChallenge(),
                new ScrollOfAnimation(),
                new Firebloom.Seed()
        );
        for (int i = 0;i < 15; i++) drop(new ScrollOfGolems(), Random.element(candidates));
        for (int i = 0;i < 3; i++) dropMany(candidates,
                new ScrollOfMirrorImage(),
                new StoneOfAggression()
        );
        candidates.clear();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (map[m]==Terrain.EMPTY_SP && !cellAdjacentToBorderCells(m) && distance(m, amuletCell)>10) candidates.add(m);
        }
        for (int i = 0;i < 4; i++) dropMany(candidates,
                new PotionOfLiquidFlame(),
                new ScrollOfUpgrade(),
                new ScrollOfEnchantment(),
                Generator.random(Generator.Category.RING)
        );
        drop(new WarHammer().upgrade(2), 36 + WIDTH*10);
        drop(new DarksteelSaber().upgrade(3), 36 + WIDTH*46);
        for (int x = 5 ; x < 24 ; x += 2){
            drop(Generator.randomWeapon().upgrade(), x + WIDTH*15);
        }

        super.addDestinations();
    }
}
