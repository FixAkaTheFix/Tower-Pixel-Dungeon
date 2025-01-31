package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.Statistics;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Drowsy;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.windows.WndTower;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.HashSet;

public class Tower extends Mob {
    {
        defenseSkill = 1;
        HP = HT = 1;
        //properties.add(Property.IMMOVABLE);


        alignment = Alignment.ALLY;
        viewDistance = 6; //SHOOTING RANGE!!!

        state = WANDERING;


        immunities.add(Drowsy.class);



    }

    public boolean sellable = true;
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
        return super.drRoll() + Random.NormalIntRange(defMin, defMax);
    }

    @Override
    public float attackDelay() {
        return super.attackDelay() + baseAttackDelay;
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
        else if (this.getClass() == TowerWall3.class) return new TowerWallRunic();
        else if (this.getClass() == TowerGrave3.class) return new TowerGraveCrypt();
        else if (this.getClass() == TowerCrossbow3.class) return new TowerCrossbowBallista();
        else if (this.getClass() == TowerCannon1.class) return new TowerCannon2();
        else if (this.getClass() == TowerCannon2.class) return new TowerCannon3();
        else if (this.getClass() == TowerCannon3.class) return new TowerCannonNuke();
        else if (this.getClass() == TowerPylonBroken.class) return new TowerPylon();
        else if (this.getClass() == TowerGuard1.class) return new TowerGuard2();
        else if (this.getClass() == TowerGuard2.class) return new TowerGuard3();
        else if (this.getClass() == TowerGuard3.class) return new TowerGuardPaladin();
        else if (this.getClass() == TowerLightning1.class) return new TowerLightning2();
        else if (this.getClass() == TowerLightning2.class) return new TowerLightning3();
        else if (this.getClass() == TowerDartgun1.class) return new TowerDartgun2();
        else if (this.getClass() == TowerDartgun2.class) return new TowerDartgun3();
        else if (this.getClass() == TowerDartgun3.class) return new TowerDartgunSpitter();
        else if (this.getClass() == TowerDisintegration1.class) return new TowerDisintegration2();
        else if (this.getClass() == TowerDisintegration2.class) return new TowerDisintegration3();
        else return new TowerWall1();
    };
    public Tower upgradeTower2(){
        if      (this.getClass() == TowerGrave3.class) return new TowerGraveElite();
        else if (this.getClass() == TowerCrossbow3.class) return new TowerCrossbowGatling();
        else if (this.getClass() == TowerCannon3.class) return new TowerCannonMissileLauncher();
        else if (this.getClass() == TowerDartgun3.class) return new TowerDartgunSniper();
        else if (this.getClass() == TowerGuard3.class) return new TowerGuardSpearman();
        else if (this.getClass() == TowerWall3.class) return new TowerWallSpiked();
        else return new TowerWall2();
    };
    public Tower upgradeTower3(){
        if      (this.getClass() == TowerCrossbow1.class) return new TowerCrossbow2();
        else if (this.getClass() == TowerCrossbow2.class) return new TowerCrossbow3();
        else return new TowerWall2();
    };


    public void upgrade1() {
        Tower tower = upgradeTower1();
        tower.sellable = sellable;
        tower.pos = pos;
        HashSet<ChampionEnemy> champBuffs = buffs(ChampionEnemy.class);
        for (ChampionEnemy champ : champBuffs){
            Buff.affect(tower, champ.getClass());
        }
        if (buff(Animated.class)!=null) Buff.affect(tower, Animated.class);
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }
    public void upgrade2() {
        Tower tower = upgradeTower2();
        tower.sellable = sellable;
        tower.pos = pos;
        HashSet<ChampionEnemy> champBuffs = buffs(ChampionEnemy.class);
        for (ChampionEnemy champ : champBuffs){
            Buff.affect(tower, champ.getClass());
        }
        if (buff(Animated.class)!=null) Buff.affect(tower, Animated.class);
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }
    public void upgrade3() {
        Tower tower = upgradeTower3();
        tower.sellable = sellable;
        tower.pos = pos;
        HashSet<ChampionEnemy> champBuffs = buffs(ChampionEnemy.class);
        for (ChampionEnemy champ : champBuffs){
            Buff.affect(tower, champ.getClass());
        }
        if (buff(Animated.class)!=null) Buff.affect(tower, Animated.class);
        die(hero);
        GameScene.add(tower);
        Dungeon.level.occupyCell(tower);
    }

    public void sell() {
        die(hero);
        int totalcost = (this instanceof TowerTntLog) ? Math.round(this.cost * 0.3f)
                : Math.round(this.cost * 0.3f + this.cost * 0.5f *((float)Math.min(this.HP, this.HT)/this.HT));
        Dungeon.level.drop( new Gold(totalcost), pos ).sprite.drop();
    }

    public int attackSkill(Char target) {
        return Math.round(baseAttackSkill*towerAttackMult());//???
    }

    @Override
    public void die(Object cause) {
        super.die(cause);
        if (cause!=hero && cause!=this){
            if (this instanceof TowerCWall &&!(this instanceof IceWall)) Statistics.wallsdestroyed++;
            else if (!(
                    this instanceof IceWall ||
                    this instanceof Banner ||
                    this instanceof ObeliskPermafrost ||
                    this instanceof ObeliskNecrotic ||
                    this instanceof ObeliskBloodstone
                    ))
            {
                Statistics.notwallsdestroyed++;
            }
        }
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


    private static final String UNCHANGABLEFORCED = "unchangable";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(UNCHANGABLEFORCED, sellable);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        sellable = bundle.getBoolean(UNCHANGABLEFORCED);
    }
}
