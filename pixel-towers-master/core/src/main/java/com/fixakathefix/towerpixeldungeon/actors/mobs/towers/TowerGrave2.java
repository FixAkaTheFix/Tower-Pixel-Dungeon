package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.SkeletonSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGrave2Sprite;

public class TowerGrave2 extends TowerGrave1{

    {
        HP = HT = 100;

        spriteClass = TowerGrave2Sprite.class;

        maxMinions = 3;
        minionCooldown = 20;
        minionCooldownLeft = 1;

        minionHP = 20;
        minionDamageMax = 10;
        minionDamageMin = 5;
        minionAttackSkill = 10;
        minionDefenseSkill = 5;
        minionDR = 2;

        cost = 500;

        upgrade1Cost = 500;

        upgradeLevel = 8;
    }
    protected TowerCSpawningMinion minion(){
        return new TowerGrave2.SkeletonMinion2();
    }

    public static class SkeletonMinion2 extends SkeletonMinion1 {
        public SkeletonMinion2() {
            super();
        }
    }
}
