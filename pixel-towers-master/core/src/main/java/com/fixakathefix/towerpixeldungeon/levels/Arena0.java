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
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Sign;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Target;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbowBallista;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.keys.IronKey;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerCrossbow;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWall;
import com.fixakathefix.towerpixeldungeon.items.towerspawners.SpawnerWand;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Arena0 extends Arena {

    {
        name = "Testing pathway";

        color1 = 0x801500;
        color2 = 0xa68521;
        viewDistance = 25;
        WIDTH = 50;
        HEIGHT = 90;

        startGold = 5000;
        startLvl = 1;

        maxWaves = 49271;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        amuletCell = WIDTH*HEIGHT/2+WIDTH*10+2;
        exitCell = WIDTH*HEIGHT/2 + 4;
        towerShopKeeperCell = amuletCell+7*WIDTH;;
        normalShopKeeperCell = amuletCell+15*WIDTH;

        waveCooldownNormal = 20;
        waveCooldownBoss = 250;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.SEWERS_1},
                new float[]{1},
                false);
    }

    @Override
    protected boolean build() {

        //base room
        setSize(WIDTH, HEIGHT);
        Painter.fill(this, 0, 0, WIDTH, HEIGHT, Terrain.WALL);
        Painter.fill(this, 1, 1, WIDTH-2, HEIGHT-2, Terrain.EMPTY);

        int x;
        int y;

        //several start rooms with signs
        x = 2;
        y = HEIGHT/2;
        Painter.fill(this, x, y - 3, 7,7,Terrain.WALL);
        Painter.fill(this, x+1, y - 2, 5,5,Terrain.EMPTY);
        Painter.fill(this, x + 6 , y - 3, 7,7,Terrain.WALL);
        Painter.fill(this, x + 7, y - 2, 5,5,Terrain.EMPTY);
        Painter.set(this, x+6, y,Terrain.DOOR);
        Painter.fill(this, x + 12 , y - 3, 7,7,Terrain.WALL);
        Painter.fill(this, x + 13, y - 2, 5,5,Terrain.EMPTY);
        Painter.set(this, x+12, y,Terrain.DOOR);
        Painter.fill(this, x + 18 , y - 3, 7,7,Terrain.WALL);
        Painter.fill(this, x + 19, y - 2, 5,5,Terrain.EMPTY);
        Painter.set(this, x+18, y,Terrain.DOOR);
        Painter.fill(this, x + 24 , y - 3, 7,7,Terrain.WALL);
        Painter.fill(this, x + 25, y - 2, 5,5,Terrain.EMPTY);
        Painter.set(this, x+24, y,Terrain.LOCKED_DOOR);

        //above rooms with towers and targets
        int y1 = 32;
        for (int x1 = 0; x1<48;x1+=8) {
            Painter.fill(this, x1, y1, 10, 9, Terrain.WALL);
            Painter.fill(this, x1 + 1, y1+1, 8, 8, Terrain.STATUE);
            Painter.fill(this, x1 + 1, y1+1, 8, 7, Terrain.EMPTY);
        }



        //def
        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }


    @Override
    public void initNpcs() {
        Sign tutorial1 = new Sign(
                Messages.get(Sign.class, "1tutorial1"),
                Messages.get(Sign.class, "1tutorial2"),
                Messages.get(Sign.class, "1tutorial3"),
                Messages.get(Sign.class, "1tutorial4")
        );
        tutorial1.pos = WIDTH*(HEIGHT/2) + 5;
        GameScene.add(tutorial1);


        Sign tutorial2 = new Sign(
                Messages.get(Sign.class, "2tutorial1"),
                Messages.get(Sign.class, "2tutorial2"),
                Messages.get(Sign.class, "2tutorial3")
        );
        tutorial2.pos = WIDTH*(HEIGHT/2) + 11;
        GameScene.add(tutorial2);
        TowerCrossbow1 towerCrossbow1 = new TowerCrossbow1();
        towerCrossbow1.pos = tutorial2.pos+1;
        GameScene.add(towerCrossbow1);
        this.drop(new SpawnerWand(), tutorial2.pos-1);

        Sign tutorial3 = new Sign(
                Messages.get(Sign.class, "3tutorial1"),
                Messages.get(Sign.class, "3tutorial2"),
                Messages.get(Sign.class, "3tutorial3")
        );
        tutorial3.pos = WIDTH*(HEIGHT/2) + 17;
        GameScene.add(tutorial3);

        TowerWall1 wall1 = new TowerWall1();
        TowerWall1 wall2 = new TowerWall1();
        TowerWall1 wall3 = new TowerWall1();

        wall1.pos = tutorial3.pos+2;
        wall2.pos = wall1.pos + WIDTH;
        wall3.pos = wall1.pos - WIDTH;

        GameScene.add(wall1);
        GameScene.add(wall2);
        GameScene.add(wall3);

        Sign tutorial4 = new Sign(
                Messages.get(Sign.class, "4tutorial1"),
                Messages.get(Sign.class, "4tutorial2"),
                Messages.get(Sign.class, "4tutorial3")
        );
        tutorial4.pos = WIDTH*(HEIGHT/2) + 23;
        GameScene.add(tutorial4);
        this.drop(new SpawnerWall(), tutorial4.pos-1);
        this.drop(new SpawnerCrossbow(), tutorial4.pos-2);
        this.drop(new PotionOfToxicGas(), tutorial4.pos+1);
        this.drop(new IronKey(0), tutorial4.pos+2 - WIDTH);
        this.drop(new Bomb(), tutorial4.pos + WIDTH);



        Sign tutorial5 = new Sign(
                Messages.get(Sign.class, "5tutorial1"),
                Messages.get(Sign.class, "5tutorial2"),
                Messages.get(Sign.class, "5tutorial3")
        );
        tutorial5.pos = WIDTH*(HEIGHT/2) + 29;
        GameScene.add(tutorial5);

        this.drop(new ScrollOfTeleportation(), tutorial5.pos + WIDTH);
        Rat rat = new Rat();
        rat.pos = tutorial5.pos + WIDTH;
        GameScene.add(rat);

        //the amulets guards

        TowerCrossbow1 towerCrossbowAmulet = new TowerCrossbow1();
        towerCrossbowAmulet.pos = amuletCell+1;
        GameScene.add(towerCrossbowAmulet);
        TowerCrossbow1 towerCrossbowAmulet2 = new TowerCrossbow1();
        towerCrossbowAmulet2.pos = amuletCell+1 + WIDTH;
        GameScene.add(towerCrossbowAmulet2);
        TowerCrossbow1 towerCrossbowAmulet3 = new TowerCrossbow1();
        towerCrossbowAmulet3.pos = amuletCell + WIDTH;
        GameScene.add(towerCrossbowAmulet3);


        Sign amulettutorial = new Sign(
                Messages.get(Sign.class, "amulettutorial1"),
                Messages.get(Sign.class, "amulettutorial2"),
                Messages.get(Sign.class, "amulettutorial3")
        );
        amulettutorial.pos = amuletCell + 3;
        GameScene.add(amulettutorial);

        Sign shoptutorial = new Sign(
                Messages.get(Sign.class, "shoptutorial1"),
                Messages.get(Sign.class, "shoptutorial2"),
                Messages.get(Sign.class, "shoptutorial3")
        );
        shoptutorial.pos = towerShopKeeperCell + 3;
        GameScene.add(shoptutorial);

        Sign loottutorial = new Sign(
                Messages.get(Sign.class, "loottutorial1"),
                Messages.get(Sign.class, "loottutorial2")
        );
        loottutorial.pos = amuletCell + 20;
        GameScene.add(loottutorial);

        for (int i : PathFinder.NEIGHBOURS25){
            this.drop(Generator.random(), loottutorial.pos + i);
        }

        int y1 = 32;
        for (int x1 = 1; x1<49;x1+=8) {
            Target target = new Target();
            target.pos = WIDTH * (y1 + 4) + x1 + 5;
            GameScene.add(target);
            switch (x1) {
                case 1:{
                    TowerCrossbow1 t1 = new TowerCrossbow1();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    break;
                }
                case 9:{
                    TowerCrossbowBallista t1 = new TowerCrossbowBallista();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    break;
                }
                case 17:{
                    TowerLightning1 t1 = new TowerLightning1();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 2;
                    GameScene.add(t1);
                    Target target2 = new Target();
                    target2.pos = WIDTH * (y1 + 4) + x1 + 4;
                    GameScene.add(target2);
                    break;
                }
                case 25:{
                    TowerWand1 t1 = new TowerWand1();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    break;
                }
                case 33:{
                    TowerCannon1 t1 = new TowerCannon1();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    break;
                }
                case 41:{
                    TowerGrave1 t1 = new TowerGrave1();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    break;
                }
                case 49:{
                    TowerTotem.TotemShield t1 = new TowerTotem.TotemShield();
                    t1.pos = WIDTH * (y1 + 4) + x1 + 1;
                    GameScene.add(t1);
                    TowerWall1 wall11 = new TowerWall1();
                    wall11.pos = t1.pos + WIDTH;
                    GameScene.add(wall11);
                    break;
                }

            }
        }

        super.initNpcs();
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 5000;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    public void deployMobs(int wave) {
        for (int i = 0; i < wave;i++){
            deploymobs(wave, Direction.TOODOWN, 1);
        }
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_SEWERS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_SEWERS;
    }


    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(CityLevel.class, "water_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CityLevel.class, "high_grass_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return Messages.get(CityLevel.class, "entrance_desc");
            case Terrain.EXIT:
                return Messages.get(CityLevel.class, "exit_desc");
            case Terrain.WALL_DECO:
            case Terrain.EMPTY_DECO:
                return Messages.get(CityLevel.class, "deco_desc");
            case Terrain.EMPTY_SP:
                return Messages.get(CityLevel.class, "sp_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(CityLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(CityLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addCityVisuals( this, visuals );
        return visuals;
    }

    public static void addCityVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Smoke( i ) );
            }
        }
    }

    public static class Smoke extends Emitter {

        private int pos;

        public static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                SmokeParticle p = (SmokeParticle)emitter.recycle( SmokeParticle.class );
                p.reset( x, y );
            }
        };

        public Smoke( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 6, p.y - 4, 12, 12 );

            pour( factory, 0.2f );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }

    public static final class SmokeParticle extends PixelParticle {

        public SmokeParticle() {
            super();

            color( 0x000000 );
            speed.set( Random.Float( -2, 4 ), -Random.Float( 3, 6 ) );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan = 2f;
        }

        @Override
        public void update() {
            super.update();
            float p = left / lifespan;
            am = p > 0.8f ? 1 - p : p * 0.25f;
            size( 6 - p * 3 );
        }
    }
}