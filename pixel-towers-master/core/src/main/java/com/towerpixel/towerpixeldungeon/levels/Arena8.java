package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.effects.particles.FlameParticle;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.items.weapon.Weapon;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Sword;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.rooms.special.MassGraveRoom;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.BossNecromancerSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena8 extends Arena{

    {
        name = "Prison graveyard";
        color1 = 0x6a723d;
        color2 = 0x88924c;

        viewDistance = 15;
        WIDTH = 81;
        HEIGHT = 81;
        startLvl = 8;
        startGold = 500;
        waveCooldownNormal = 5;
        waveCooldownBoss = 300;
        arenaDepth = 8;

        maxWaves=20;

        normalShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;
        towerShopKeeper.vertical = NewShopKeeper.ShopDirection.RIGHT;

        amuletCell = 3 + WIDTH*40;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell-2-3*WIDTH;;
        normalShopKeeperCell = amuletCell-2+3*WIDTH;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public int mobsToDeploy(int wave) {
        switch (wave){
            case 1: return 2;
            case 2: return 10;
            case 3: return 8;
            case 4: return 10;
            case 5: return 6;
            case 6: return 11;
            case 7: return 16;
            case 8: return 14;
            case 9: return 17;
            case 10: return 24;
            case 11: return 25;
            case 12: return 29;
            case 13: return 25;
            case 14: return 10;
            case 15: return 30;
            case 16: return 40;
            case 17: return 42;
            case 18: return 15;
            case 19: return 12;
            case 20: return 20;
            case 8055: return 1;
        }
        return 1;
    }

    int caveY = Random.Int(5,60);

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_PRISON;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(PrisonLevel.class, "water_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.EMPTY_DECO:
                return Messages.get(PrisonLevel.class, "empty_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(PrisonLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    protected boolean build() {

        setSize(WIDTH, HEIGHT);
        //base room
        Painter.fill(this, 0, 0, 81, 81, Terrain.WALL);
        Painter.fill(this, 1, 1, 79, 79, Terrain.EMPTY_SP);

        Painter.fillEllipse(this,70,caveY,7,19,Terrain.WALL);
        Painter.fill(this,71,caveY+4,3,9,Terrain.EMPTY);







        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void addDestinations() {
        super.addDestinations();

        for (int x = 3;x < WIDTH - 5; x+=5) for (int y = 3;y < WIDTH - 5; y+=5) if ( !((y<caveY+75/WIDTH+27 && y>caveY+75/WIDTH-11) && (x<80 && x>65) ||(y<amuletCell/WIDTH+7 && y>amuletCell/WIDTH-11) && (x<11 && x>1) ||(y<amuletCell/WIDTH+2 && y>amuletCell/WIDTH-6) && (x<45 && x>1) || (x>42 && x<48) && (y<80 && y>1) || (x>43 && x<79) && (y>caveY-3 && y<caveY+3))){
            int type = Random.Int( 15);
            ArrayList<Integer> candidates = new ArrayList<>();
            //graves
            for (int i = x;i<x+4;i++) for (int i2 = y;i2<y+4;i2++) candidates.add(i + i2*WIDTH);
            switch (type){
                //random bones yard
                case 1: default:{
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(), Random.element(candidates)).type = Heap.Type.SKELETON;
                    break;
                }
                //heros
                case 2: {
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(Generator.Category.ARTIFACT), Random.element(candidates)).type = Heap.Type.REMAINS;
                    break;
                }
                //empty
                case 3:{
                    Painter.fill(this, x,y,4,4,Terrain.EMPTY);
                    break;
                }
                //alchemists
                case 4: {
                    this.map[x-WIDTH+WIDTH*y] = Terrain.EMBERS;
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(Generator.Category.POTION), Random.element(candidates)).type = Heap.Type.SKELETON;
                    break;
                }
                //warriors
                case 5: {
                    this.map[x-WIDTH+WIDTH*y] = Terrain.STATUE_SP;
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(Generator.Category.WEAPON), Random.element(candidates)).type = Heap.Type.TOMB;
                    break;
                }
                //rogues
                case 6: {
                    Painter.fill(this, x,y,4,4,Terrain.EMPTY_DECO);
                    level.drop(Generator.random(Generator.Category.RING), Random.element(candidates)).type = Heap.Type.TOMB;
                    break;
                }
                //scholars
                case 7: {
                    this.map[x-WIDTH+WIDTH*y] = Terrain.EMPTY_WELL;
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(Generator.Category.SCROLL), Random.element(candidates)).type = Heap.Type.SKELETON;
                    break;
                }
                //mages
                case 8: {
                    this.map[x-WIDTH+WIDTH*y] = Terrain.WELL;
                    Painter.fill(this, x,y,4,4,Terrain.GRASS);
                    level.drop(Generator.random(Generator.Category.WAND), Random.element(candidates)).type = Heap.Type.TOMB;
                    break;
                }
                //huntresses
                case 9: {
                    Painter.fill(this, x,y,4,4,Terrain.HIGH_GRASS);
                    level.drop(Generator.random(Generator.Category.MIS_T2), Random.element(candidates)).type = Heap.Type.SKELETON;
                    break;
                }
            }
            this.map[x-1-WIDTH+WIDTH*y] = Terrain.WALL_DECO;
            this.map[x+4+WIDTH*(y+4)] = Terrain.WALL_DECO;
            this.map[x+4 -WIDTH+WIDTH*y] = Terrain.WALL_DECO;
            this.map[x-1+WIDTH*(y+4)] = Terrain.WALL_DECO;
        }

        for (int x = 1;x<WIDTH-1;x++) for (int y = 1;y<HEIGHT-1;y++){
            int cell = x+WIDTH*y;
            //random tall grass
            if (Math.random()>0.8) {
                if (this.map[cell]==Terrain.GRASS) this.map[cell]=Terrain.HIGH_GRASS;
            }

        }
        MassGraveRoom.Bones b = new MassGraveRoom.Bones();

        b.setRect(71, caveY+3, 3, 10);
        level.customTiles.add(b);

        Sword lostsword = new CursedNecroSword();
        lostsword.upgrade(1);
        lostsword.curseInfusionBonus = true;
        lostsword.enchant(Weapon.Enchantment.randomCurse());
        lostsword.cursed = true;
        lostsword.identify();

        level.drop(lostsword, 72+ WIDTH*(caveY+5)).type = Heap.Type.REMAINS;

        buildFlagMaps();
        cleanWalls();
    }


    public class CursedNecroSword extends Sword{
        @Override
        public String name() {
            return Messages.get(this, "name");
        }

        @Override
        public String desc() {
            return Messages.get(this, "desc");
        }

        @Override
        public boolean isIdentified() {
            return true;
        }

        @Override
        public boolean visiblyCursed() {
            return true;
        }


    }
    @Override
    public void doStuffStartwave(int wave) {

        if (wave == 1){
            WndDialogueWithPic.dialogue(new BossNecromancerSprite(), "Remac",
                    new String[]{
                            Messages.get(BossNecromancer.class, "start1"),
                            Messages.get(BossNecromancer.class, "start2"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }

        if (wave == 10){
            WndDialogueWithPic.dialogue(new BossNecromancerSprite(), "Remac",
                    new String[]{
                            Messages.get(BossNecromancer.class, "mid1"),
                            Messages.get(BossNecromancer.class, "mid1"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }

        if (wave == maxWaves){
            WndDialogueWithPic.dialogue(new BossNecromancerSprite(), "Remac",
                    new String[]{
                            Messages.get(BossNecromancer.class, "engage1"),
                            Messages.get(BossNecromancer.class, "engage2"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    });
        }






        super.doStuffStartwave(wave);
    }


    @Override
    public void deploymobs(int wave, Direction direction, int group) {
        if (wave == maxWaves) super.deploymobs(wave, Direction.TOORIGHT, 1);
        else super.deploymobs(wave, Direction.RIGHT, 1);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 180;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addPrisonVisuals(this, visuals);
        return visuals;
    }

    public static void addPrisonVisuals(Level level, Group group){
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new PrisonLevel.Torch( i ) );
            }
        }
    }

    public static class Torch extends Emitter {

        private int pos;

        public Torch( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 1, p.y + 2, 2, 0 );

            pour( FlameParticle.FACTORY, 0.15f );

            add( new Halo( 12, 0xFFDDDD, 0.4f ).point( p.x, p.y + 1 ) );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
                super.update();
            }
        }
    }




}
