package com.towerpixel.towerpixeldungeon.levels.endlessarenas;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Honeypot;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfFrost;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.RatKingSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class EndlessArena extends Arena {

    //the default map is similar to sewer entrance.
    {
        name = "Sewer entrance, infinite";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 15;
        WIDTH = 51;
        HEIGHT = 51;

        amuletCell = 9 + WIDTH*25;
        exitCell = amuletCell;
        towerShopKeeperCell = 6 + 21*WIDTH;
        normalShopKeeperCell = 11 + 21*WIDTH;

        startGold = 0;
        maxWaves = 15;
        waveCooldownNormal = 5;
        waveCooldownBoss = 30;
    }


    @Override
    public int mobsToDeploy(int wave) {
        switch (wave) {
        }
        return 1;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.BOSS_TRIAL},
                new float[]{1},
                false);
    }

    @Override
    public void affectMob(Mob mob) {
        Buff.affect(mob, ChampionEnemy.Projecting.class);
    }

    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 0,0,50,50, Terrain.WALL);;
        Painter.fill(this, 2,2,43,46, Terrain.EMPTY);
        Painter.fill(this,0,0,50,21, Terrain.WALL);
        Painter.fill(this,0,30,50,20, Terrain.WALL);
        Painter.fill(this,0,0,5,50, Terrain.WALL);
        Painter.fill(this, 5,21,9,9,Terrain.EMPTY_SP);
        Painter.fill(this, 7,23,5,5,Terrain.EMPTY);

        Painter.fill(this, 14,21,1,3,Terrain.WALL);
        Painter.fill(this, 14,27,1,3,Terrain.WALL);

        Painter.fill(this, 12,24,40,3,Terrain.EMPTY_SP);

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);

        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }
    @Override
    public void doStuffEndwave(int wave) {

    }

    @Override
    public void initNpcs() {
        TowerCrossbow1 tower = new TowerCrossbow1();
        tower.pos = amuletCell+WIDTH;
        GameScene.add(tower);
        super.initNpcs();
    }

    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RIGHT, 1);
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
