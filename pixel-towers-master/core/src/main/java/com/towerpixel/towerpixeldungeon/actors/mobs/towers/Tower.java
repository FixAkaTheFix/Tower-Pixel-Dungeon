package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.ToxicGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Drowsy;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.windows.WndTower;
import com.watabou.noosa.Game;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Tower extends Mob {
    {
        defenseSkill = 1;
        HP = HT = 1;
        //properties.add(Property.IMMOVABLE);
        properties.add(Property.INORGANIC);

        alignment = Alignment.ALLY;
        viewDistance = 6; //SHOOTING RANGE!!!

        state = WANDERING;

        immunities.add(ToxicGas.class);
        immunities.add(Amok.class);
        immunities.add(Drowsy.class);


    }

    public int baseHP;
    public byte upgCount = 1;//the count of upgrades, if set more than there actually are, upgrading a tower will return a wall with the same lvl.
    public int baseAttackSkill;

    public float baseAttackDelay;

    public int cost = 100;
    public int upgrade1Cost = 150;//responsible for the first upgrade variant, the most common one as most towers have 1 straight upgrade
    public int upgrade2Cost = 150;//those two are responsible for the second and third upgrade variant, used in upgrade trees on lvl3 towers.
    public int upgrade3Cost = 150;
    public int damageMin;
    public int damageMax;
    public int defMin = 0;
    public int defMax = 1;
    public int upgradeLevel = 0;//the tower can only be upgraded if this level has been reached. Individual for each tower.


    @Override
    public int damageRoll() {
        return Random.NormalIntRange(damageMin,damageMax);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(defMin, defMax);
    }

    @Override
    public float attackDelay() {
        return baseAttackDelay;
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT ));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }
//the list of tower upgrades
    public Tower upgradeTower1(){
        if      (this.getClass() == TowerCrossbow1.class) return new TowerCrossbow2();
        else if (this.getClass() == TowerCrossbow2.class) return new TowerCrossbow3();
        else if (this.getClass() == TowerWand1.class) return new TowerWand2();
        else if (this.getClass() == TowerWand2.class) return new TowerWand3();
        else if (this.getClass() == TowerGrave1.class) return new TowerGrave2();
        else if (this.getClass() == TowerGrave2.class) return new TowerGrave3();
        else if (this.getClass() == TowerWall1.class) return new TowerWall2();
        else if (this.getClass() == TowerWall2.class) return new TowerWall3();
        else if (this.getClass() == TowerGrave3.class) return new TowerGraveCrypt();
        else if (this.getClass() == TowerWand3.class) return new TowerWandPrismatic();
        else if (this.getClass() == TowerCrossbow3.class) return new TowerCrossbowBallista();
        else if (this.getClass() == TowerCannon1.class) return new TowerCannon2();
        else if (this.getClass() == TowerCannon2.class) return new TowerCannon3();
        else if (this.getClass() == TowerCannon3.class) return new TowerCannonNuke();
        else if (this.getClass() == TowerPylonBroken.class) return new TowerPylon();
        else return new TowerWall1();
    };
    public Tower upgradeTower2(){
        if      (this.getClass() == TowerGrave3.class) return new TowerGraveElite();
        else if (this.getClass() == TowerWand3.class) return new TowerWandFireball();
        else if (this.getClass() == TowerCrossbow3.class) return new TowerCrossbowGatling();
        else if (this.getClass() == TowerCannon3.class) return new TowerCannonQuadbarrel();
        else return new TowerWall2();
    };
    public Tower upgradeTower3(){
        if      (this.getClass() == TowerCrossbow1.class) return new TowerCrossbow2();
        else if (this.getClass() == TowerCrossbow2.class) return new TowerCrossbow3();
        else if (this.getClass() == TowerWand3.class) return new TowerWandLightning();
        else return new TowerWall2();
    };



    public void upgrade1() {
        Tower tower = upgradeTower1();
        tower.pos = pos;
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }
    public void upgrade2() {
        Tower tower = upgradeTower2();
        tower.pos = pos;
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }
    public void upgrade3() {
        Tower tower = upgradeTower3();
        tower.pos = pos;
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }

    public void sell() {
        die(hero);
        Dungeon.level.drop( new Gold(Math.round(cost*0.5f)), pos ).sprite.drop();
    }

    public int attackSkill(Char target) {
        return Math.round(baseAttackSkill*towerAttackMult());
    }


    @Override
    public boolean interact(Char c) {
        if (c == Dungeon.hero) {
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndTower(Tower.this) );
                }
            });
            return true;
        } else return true;
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }

    public static float towerAttackMult(){//same
        return 1;//for future
    }
    public static float towerHPMult(){//for future talents and stuff, static due to all towers being affected
        return 1;
    }
    public static int towerHPAdd() {//same
        return 0;
    }

    public int towerHp() {
        return Math.round(baseHP*towerHPMult()) + towerHPAdd();
    }
}
