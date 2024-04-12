package com.towerpixel.towerpixeldungeon.levels;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.effects.Ripple;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.Honeypot;
import com.towerpixel.towerpixeldungeon.items.armor.LeatherArmor;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.food.MeatPie;
import com.towerpixel.towerpixeldungeon.items.food.MysteryMeat;
import com.towerpixel.towerpixeldungeon.items.food.SmallRation;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfExperience;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfFrost;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfStrength;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfToxicGas;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTerror;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.towerpixel.towerpixeldungeon.levels.features.LevelTransition;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.traps.BurningTrap;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.ColorMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Arena2 extends Arena{
    {
        name = "Deeper Sewers";

        color1 = 0x48763c;
        color2 = 0x59994a;
        viewDistance = 10;
        WIDTH = 101;
        HEIGHT = 101;

        startGold = 400;
        startLvl = 3;

        maxWaves = 20;

        amuletCell = 51 + WIDTH*51;
        exitCell = amuletCell;
        towerShopKeeperCell = amuletCell - 5*WIDTH - 3;
        normalShopKeeperCell = amuletCell - 5*WIDTH + 2;

        waveCooldownNormal = 5;
        waveCooldownBoss = 200;
    }

    @Override
    public void playLevelMusic() {
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.CITY_BOSS},
                new float[]{1},
                false);
    }

    @Override
    public void doStuffEndwave(int wave) {
        int goldAdd = 70 + wave;
        Dungeon.gold+=goldAdd;
        GLog.w(Messages.get(Arena.class, "goldaddendwave", goldAdd));
        super.doStuffEndwave(wave);
    }


    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);
        //base room
        Painter.fill(this, 6,6,91,91, Terrain.WALL);
        //Painter.fill(this, 1,1,45,48, Terrain.HEROBARRIER);

        Painter.fill(this, 7,7,89,89, Terrain.EMPTY);
        Painter.fill(this, 8,8,87,87, Terrain.WATER);
        Painter.fill(this,9,9,85,85, Terrain.EMPTY);
        Painter.fill(this, 10,10,83,83, Terrain.WALL);
        Painter.fill(this,11,11,81,81, Terrain.EMPTY);
        Painter.fill(this, 12,12,79,79, Terrain.WATER);
        Painter.fill(this,13,13,77,77, Terrain.EMPTY);
        Painter.fill(this, 14,14,75,75, Terrain.WALL);
        Painter.fill(this,15,15,73,73, Terrain.EMPTY);
        Painter.fill(this, 16,16,71,71, Terrain.WALL);
        Painter.fill(this,17,17,69,69, Terrain.EMPTY);
        Painter.fill(this, 18,18,67,67, Terrain.WALL);
        Painter.fill(this,19,19,65,65, Terrain.EMPTY);
        Painter.fill(this, 20,20,63,63, Terrain.WATER);
        Painter.fill(this,21,21,61,61, Terrain.EMPTY);
        Painter.fill(this, 22,22,59,59, Terrain.WALL);
        Painter.fill(this,23,23,57,57, Terrain.EMPTY_SP);
        Painter.fill(this, 26,26, 51,51, Terrain.WALL);
        Painter.fill(this,27,27,49,49, Terrain.EMPTY);
        Painter.fill(this, 28,28,47,47, Terrain.WALL);
        Painter.fill(this,29,29,45,45, Terrain.EMPTY);
        Painter.fill(this, 30,30,43,43, Terrain.WATER);
        Painter.fill(this,31,31,41,41, Terrain.EMPTY);
        Painter.fill(this, 32,32,39,39, Terrain.WALL);
        Painter.fill(this,33,33,37,37, Terrain.EMPTY);
        Painter.fill(this, 34,34,35,35, Terrain.WATER);
        Painter.fill(this,35,35,33,33, Terrain.EMPTY);
        Painter.fill(this, 36,36,31,31, Terrain.WALL);
        Painter.fill(this,37,37,29,29, Terrain.EMPTY);
        Painter.fill(this, 38,38,27,27, Terrain.WATER);
        Painter.fill(this,39,39,25,25, Terrain.EMPTY);
        Painter.fill(this, 40,40,23,23, Terrain.WALL);
        Painter.fillEllipse(this,46,46,11,11, Terrain.EMPTY);
        Painter.fill(this,46,46,11,3, Terrain.EMPTY);
        Painter.fill(this,46,47,11,1, Terrain.PEDESTAL);
        this.map[46+WIDTH*48] = Terrain.ALCHEMY;

        for (int x = 6;x<WIDTH-6;x++) for (int y = 6;y<HEIGHT-6;y++) if (x < 30 || x > 70) if (y < 30 || y > 70){
            //Random wet flooring
            int cell = x+WIDTH*y;
            if (this.map[cell]==Terrain.EMPTY){
                if (Math.random()>0.995) {
                    this.map[cell]=Terrain.WATER;
                    for (int i1: PathFinder.NEIGHBOURS8){
                        if (Math.random()>0.5) this.map[cell+i1] = Terrain.WATER;
                    }
                    for (int i1: PathFinder.NEIGHBOURS25){
                        if (Math.random()>0.9) this.map[cell+i1] = Terrain.WATER;//90% at 1 range, 50% at 2 range will become water cells
                    }
                }
            }
            //Random passages between wall layers
            if (this.map[cell]==Terrain.WALL){
                if (Math.random()>0.95) this.map[cell]=Terrain.EMPTY;
                if (Math.random()>0.99) this.map[cell]=Terrain.WATER;
            }
            if (this.map[cell]==Terrain.WALL){//random tubes
                if (Math.random()>0.994) {
                    this.map[cell] = Terrain.WALL_DECO;
                    if (Math.random() > 0.3) this.map[cell + WIDTH] = Terrain.WATER;
                    if (Math.random() > 0.8) this.map[cell + WIDTH - 1] = Terrain.WATER;
                    if (Math.random() > 0.8) this.map[cell + WIDTH + 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell - 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell + 1] = Terrain.WATER;
                    if (Math.random() > 0.9) this.map[cell + WIDTH + WIDTH] = Terrain.WATER;
                }
            }
        }
        Painter.fill(this, 0, 49, 101, 5,Terrain.EMPTY);
        Painter.fill(this, 50, 57, 3, 20,Terrain.EMPTY_SP);
        Painter.fill(this, 50, 57, 3, 1,Terrain.BARRICADE);



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);
        transitions.add(exit);
        int x;
        int y;
        //TreasuryX2
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>40);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>40);
        Painter.fill(this,x,y,7,7, Terrain.EMPTY);
        Painter.fill(this,x+1,y+1,5,5, Terrain.WALL);
        Painter.fill(this,x+2,y+2,3,3, Terrain.EMPTY_SP);
        this.map[x+3 + WIDTH*(y+1)] = Terrain.DOOR;
        this.map[x+3 + WIDTH*(y+3)] = Terrain.STATUE_SP;
        MeleeWeapon meleeWeapon = (MeleeWeapon) Generator.random(Generator.Category.WEP_T2);
        meleeWeapon.upgrade(1);
        meleeWeapon.identify();
        this.drop(meleeWeapon, x+2 + WIDTH*(y+2));

        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>40);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>40);
        Painter.fill(this,x,y,9,9, Terrain.EMPTY);
        Painter.fill(this,x+1,y+1,7,7, Terrain.WALL);
        Painter.fill(this,x+2,y+2,5,5, Terrain.EMPTY_SP);
        this.map[x+3 + WIDTH*(y+1)] = Terrain.DOOR;

        this.map[x+4 + WIDTH*(y+4)] = Terrain.STATUE_SP;
        LeatherArmor armor = new LeatherArmor();
        armor.upgrade(1);
        armor.identify();
        this.drop(armor, x+4 + WIDTH*(y+5));
        this.drop(new Bomb(), x+2+WIDTH*(y+4));

        //trapcircle
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>40);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>40);
        for(int i : PathFinder.NEIGHBOURS8){
            this.map[x+y*WIDTH+i] = Terrain.TRAP;
            this.setTrap(new BurningTrap().reveal(), x+y*WIDTH+i);
        }
        this.map[x+y*WIDTH] = Terrain.EMPTY_DECO;
        this.drop(new PotionOfStrength(), x + WIDTH*(y));
        //A pathway room
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>30);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>30);
        Painter.fillEllipse(this,x,y,11,11, Terrain.EMPTY);
        Painter.fillEllipse(this,x+1,y+1,9,9, Terrain.WALL);
        Painter.fillEllipse(this,x+2,y+2,7,7, Terrain.WATER);
        Painter.fill(this, x, y+5, 11,1,Terrain.EMPTY_SP);
        Painter.fill(this, x+5, y, 1,11,Terrain.EMPTY_SP);
        //an explosion centre
        do {x = Random.Int(5,WIDTH-10);} while (x<60&&x>30);
        do {y = Random.Int(5,HEIGHT-10);} while (y<60&&y>30);
        Painter.fillEllipse(this,x,y,3,8, Terrain.EMBERS);
        this.drop(new Bomb(), x+2+y*(WIDTH+3));

        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    @Override
    public void addDestinations() {
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int m = 0; m<WIDTH*HEIGHT;m++){
            if (this.passable[m]) candidates.add(m);
        }
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(new Honeypot(),Random.element(candidates));
        this.drop(new PotionOfHealing(),Random.element(candidates));
        this.drop(new PotionOfLiquidFlame(),Random.element(candidates));
        this.drop(new PotionOfFrost(),Random.element(candidates));
        this.drop(new PotionOfExperience(),Random.element(candidates));
        this.drop(new PotionOfToxicGas(),Random.element(candidates));
        this.drop(new ScrollOfUpgrade(),Random.element(candidates));
        this.drop(new ScrollOfIdentify(),Random.element(candidates));
        this.drop(new ScrollOfLullaby(),Random.element(candidates));
        this.drop(new ScrollOfTerror(),Random.element(candidates));
        this.drop(new ScrollOfMirrorImage(),Random.element(candidates));
        this.drop(new MeatPie(),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(new SmallRation(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SCROLL),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T3),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T2),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.MIS_T1),Random.element(candidates));
        this.drop(new MysteryMeat(),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARMOR),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.ARTIFACT),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.RING),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.WAND),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.POTION),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.STONE),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.SEED),Random.element(candidates));
        this.drop(Generator.random(Generator.Category.GOLD),Random.element(candidates));

        super.addDestinations();
    }

    public void deployMobs(int wave) {
        switch (wave%4){
            case 0: case 1:deploymobs(wave, Direction.TOORIGHT, 1); break;
            case 2: case 3:deploymobs(wave, Direction.TOOLEFT, 1); break;
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
    public Group addVisuals() {
        super.addVisuals();
        addSewerVisuals(this, visuals);
        return visuals;
    }

    public static void addSewerVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Arena2.Sink( i ) );
            }
        }
    }

    private static class Sink extends Emitter {

        private int pos;
        private float rippleDelay = 0;

        private static final Emitter.Factory factory = new Factory() {

            @Override
            public void emit( Emitter emitter, int index, float x, float y ) {
                Arena2.WaterParticle p = (Arena2.WaterParticle)emitter.recycle( Arena2.WaterParticle.class );
                p.reset( x, y );
            }
        };

        public Sink( int pos ) {
            super();

            this.pos = pos;

            PointF p = DungeonTilemap.tileCenterToWorld( pos );
            pos( p.x - 2, p.y + 3, 4, 0 );

            pour( factory, 0.1f );
        }

        @Override
        public void update() {
            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if (!isFrozen() && (rippleDelay -= Game.elapsed) <= 0) {
                    Ripple ripple = GameScene.ripple( pos + Dungeon.level.width() );
                    if (ripple != null) {
                        ripple.y -= DungeonTilemap.SIZE / 2;
                        rippleDelay = Random.Float(0.4f, 0.6f);
                    }
                }
            }
        }
    }

    public static final class WaterParticle extends PixelParticle {

        public WaterParticle() {
            super();

            acc.y = 50;
            am = 0.5f;

            color( ColorMath.random( 0xb6ccc2, 0x3b6653 ) );
            size( 2 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            speed.set( Random.Float( -2, +2 ), 0 );

            left = lifespan = 0.4f;
        }
    }

}