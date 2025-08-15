/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Eye;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RatKingAvatar;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.YogDzewa;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuardSpearman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall2;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall3;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWallRunic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand3;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.RatKingAvatarSprite;
import com.fixakathefix.towerpixeldungeon.sprites.YogSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.ui.towerlist.TowerInfo;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena17 extends Arena {

    {
        name = "???";

        color1 = 0x00DD00;
        color2 = 0x218521;
        viewDistance = 50;
        WIDTH = 51;
        HEIGHT = 51;

        startGold = 0;
        startLvl = 20;

        arenaDepth = 17;
        maxWaves = 22;

        normalShopKeeper = null;
        towerShopKeeper = null;

        amuletCell = 25 + WIDTH*25;
        exitCell = amuletCell;


        waveCooldownNormal = 5;
        waveCooldownBoss = 5;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.HALLS_BOSS},
                new float[]{1},
                false);
    }
    @Override
    public Mob chooseMob(int wave) {
        Mob mob = new Rat();
        switch (wave){
            case 1: case 3: case 6: case 10: case 15:
                mob = new Skeleton(); break;
            case 2: case 4: case 5: case 7: case 9: case 13: case 18:
                mob = new Succubus(); break;
            case 8: case 11: case 14: case 16:
                mob = new Eye(); break;
            default:
                mob = new RipperDemon(); break;
        }
        if (mode == WndModes.Modes.CHALLENGE){
            affectMob(mob);
        }
        return mob;
    }
    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 5;
            case 2: return 1;
            case 3: return 10;
            case 4: return 2;
            case 5: return 2;
            case 6: return 15;
            case 7: return 3;
            case 8: return 3;
            case 9: return 4;
            case 10: return 25;
            case 11: return 5;
            case 12: return 6;
            case 13: return 7;
            case 14: return 5;
            case 15: return 40;
            case 16: return 6;
            case 17: return 10;
            case 18: return 11;
            case 19: return 13;
            case 20: return 17;
            case 21: return 21;
            case 22: return 25;
        }
        return 1;
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, 51, 51, Terrain.CHASM);


        Painter.fillEllipse(this, 10, 10, 31, 31, Terrain.EMPTY_DECO);
        Painter.fillEllipse(this, 11, 11, 29, 29, Terrain.CHASM);
        Painter.fill(this,4,24,43,3, Terrain.EMPTY);
        Painter.fill(this,24,4,3,43, Terrain.EMPTY);
        Painter.fillDiamond(this, 12, 12, 27, 27, Terrain.EMPTY);
        Painter.fillEllipse(this, 18, 18, 15, 15, Terrain.CHASM);
        Painter.fillEllipse(this, 20, 20, 11, 11, Terrain.EMPTY);
        Painter.fill(this, 24, 18, 3, 15, Terrain.EMPTY);
        Painter.fillEllipse(this, 23, 23, 5, 5, Terrain.EMPTY_DECO);
        Painter.fillEllipse(this, 25, 25, 1, 1, Terrain.EMPTY);

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            //Random candles
            int cell = x+WIDTH*y;

            if (Math.random()>0.95 && distance(amuletCell, cell) > 8 && distance(amuletCell, cell) < 20) {
                if (this.map[cell]==Terrain.EMPTY) this.map[cell]=Terrain.EMPTY_SP;

            }

        }

        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.CHASM;

        return true;
    }

    @Override
    public void initNpcs() {
        super.initNpcs();
        TowerTotem.TotemHealing tsouth = new TowerTotem.TotemHealing();
        tsouth.pos = amuletCell + WIDTH*3;
        GameScene.add(tsouth);
        TowerTotem.TotemShield tnorth = new TowerTotem.TotemShield();
        tnorth.pos = amuletCell - WIDTH*3;
        GameScene.add(tnorth);
        TowerTotem.TotemNecrotic teast = new TowerTotem.TotemNecrotic();
        teast.pos = amuletCell + 2;
        GameScene.add(teast);
        TowerTotem.TotemAttack twest = new TowerTotem.TotemAttack();
        twest.pos = amuletCell - 2;
        GameScene.add(twest);

        Tower cleft = new TowerCrossbow3();
        Tower cright = new TowerCrossbow3();
        Tower wleft = new TowerWall3();
        Tower wright = new TowerWall3();
        Tower wup = new TowerWall3();
        Tower wdown = new TowerWall3();

        if (mode == WndModes.Modes.CHALLENGE){
            cright = Random.oneOf(new TowerDartgun3(), new TowerCrossbow3(), new TowerDisintegration3(), new TowerWand3());
            cleft = Random.oneOf(new TowerDartgun3(), new TowerCrossbow3(), new TowerDisintegration3(), new TowerWand3());

            wleft = new TowerWallRunic();
            wright = new TowerGuardSpearman();
            wup = new TowerWall2();
            wdown = new TowerTotem.TotemShield();
        }




        cleft.pos = amuletCell-3;
        GameScene.add(cleft);
        cright.pos = amuletCell+3;
        GameScene.add(cright);
        wleft.pos = amuletCell-4;
        GameScene.add(wleft);
        wright.pos = amuletCell+4;
        GameScene.add(wright);
        wup.pos = amuletCell-4*WIDTH;
        GameScene.add(wup);
        wdown.pos = amuletCell+4*WIDTH;
        GameScene.add(wdown);
    }



    @Override
    public void doStuffStartwave(int wave) {
        super.doStuffStartwave(wave);

        if (wave==1) {
            YogSprite sprite = new YogSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();
            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "your"),
                            Messages.get(RatKing.class, "hope"),
                            Messages.get(RatKing.class, "isanillusion"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==8) {
            YogSprite sprite = new YogSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();

            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "surrender")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==16) {
            YogSprite sprite = new YogSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();

            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "nohope")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });

        }
        if (wave==20) {
            YogSprite sprite = new YogSprite();

            sprite.rm = sprite.bm = sprite.gm = 0;
            sprite.update();
            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "legion"),
                            Messages.get(RatKing.class, "youarenone")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "legion"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
            WndDialogueWithPic.dialogue(sprite, "#???#",
                    new String[]{
                            Messages.get(RatKing.class, "legion"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });


        }

        if (wave == 10) {
            RatKingAvatar rk = new RatKingAvatar();
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int m = 0; m<WIDTH*HEIGHT;m++){
                if (this.map[m]==Terrain.EMPTY && distance(amuletCell, m)<13 && Char.findChar(m)==null) candidates.add(m);
            }
            rk.pos = Random.element(candidates);
            GameScene.add(rk);
            WndDialogueWithPic.dialogue(new RatKingAvatarSprite(), Messages.get(RatKing.class, "projectionname"),
                    new String[]{
                            Messages.get(RatKing.class, "l17w10start1"),
                            Messages.get(RatKing.class, "l17w10start2"),
                            Messages.get(RatKing.class, "l17w10start3"),
                            Messages.get(RatKing.class, "l17w10start4"),
                            Messages.get(RatKing.class, "l17w10start5"),
                            Messages.get(RatKing.class, "l17w10start6"),
                            Messages.get(RatKing.class, "l17w10start7"),
                            Messages.get(RatKing.class, "l17w10start8"),
                    },
                    new byte[]{
                            WndDialogueWithPic.RUN,
                            WndDialogueWithPic.IDLE
                    });
        }
    }

    @Override
    public void doStuffEndwave(int wave) {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.map[m]==Terrain.EMPTY && distance(amuletCell, m)<12) candidates.add(m);
        }

        int x1 = Random.element(candidates);
        CellEmitter.get(x1).burst(ShadowParticle.UP, 5);
        if (mode == WndModes.Modes.CHALLENGE){
            this.drop(Random.oneOf(
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.LIGHTNING),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.DISINTEGRATION),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.DARTGUN),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.WALL),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.TNTLOG),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.CROSSBOW),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.MAGICMISSILE),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.CANNON),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.GRAVE),
                    TowerInfo.getTowerSpawner(TowerInfo.AllTowers.GUARD)),x1).type = Heap.Type.REMAINS;
        } else this.drop(Random.oneOf(
                TowerInfo.getTowerSpawner(SPDSettings.towerslot1()),
                TowerInfo.getTowerSpawner(SPDSettings.towerslot2()),
                TowerInfo.getTowerSpawner(SPDSettings.towerslot3()),
                TowerInfo.getTowerSpawner(SPDSettings.towerslot4())),x1).type = Heap.Type.REMAINS;

        int x2 = Random.element(candidates);
        CellEmitter.get(x2).burst(ShadowParticle.UP, 5);
        this.drop(Random.oneOf(
                Generator.random(Generator.Category.POTION),
                Generator.random(Generator.Category.SCROLL),
                Generator.random(Generator.Category.STONE),
                Generator.random(Generator.Category.SEED)),x2).type= Heap.Type.SKELETON;
        int x3 = Random.element(candidates);
        CellEmitter.get(x3).burst(ShadowParticle.UP, 5);
        this.drop(new Gold(333),x3).type= Heap.Type.CHEST;

        for (Heap heap: Dungeon.level.heaps.valueList()){
            heap.sprite.link(heap);
            heap.sprite.update();
        }
        if (wave==22) {
            WndDialogueWithPic.dialogue(new YogSprite(), Messages.get(YogDzewa.class, "name"),
                    new String[]{
                            Messages.get(RatKing.class, "l17w22end1"),
                            Messages.get(RatKing.class, "l17w22end2"),
                            Messages.get(RatKing.class, "l17w22end3"),
                            Messages.get(RatKing.class, "l17w22end4"),
                            Messages.get(RatKing.class, "l17w22end5")
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    },
                    WndDialogueWithPic.WndType.FINAL, new ArrayList<>());
        }


        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        if (wave == 22) deploymobs(wave, Direction.RANDOM, 12);
        deploymobs(wave, Direction.RANDOM, 2);
    }


    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SOULDESERT;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }


    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_name");
            case Terrain.GRASS:
                return Messages.get(HallsLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(HallsLevel.class, "high_grass_name");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(HallsLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addHallsVisuals( this, visuals );
        return visuals;
    }

    public static void addHallsVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WATER) {
                group.add( new Stream( i ) );
            }
        }
    }
    private static class Stream extends Group {

        private int pos;

        private float delay;

        public Stream( int pos ) {
            super();

            this.pos = pos;

            delay = Random.Float( 2 );
        }

        @Override
        public void update() {

            if (!Dungeon.level.water[pos]){
                killAndErase();
                return;
            }

            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if ((delay -= Game.elapsed) <= 0) {

                    delay = Random.Float( 2 );

                    PointF p = DungeonTilemap.tileToWorld( pos );
                    ((HallsLevel.FireParticle)recycle( HallsLevel.FireParticle.class )).reset(
                            p.x + Random.Float( DungeonTilemap.SIZE ),
                            p.y + Random.Float( DungeonTilemap.SIZE ) );
                }
            }
        }

        @Override
        public void draw() {
            Blending.setLightMode();
            super.draw();
            Blending.setNormalMode();
        }
    }
}