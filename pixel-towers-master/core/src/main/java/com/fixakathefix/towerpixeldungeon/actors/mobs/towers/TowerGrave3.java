package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.TowerGrave3Sprite;

public class TowerGrave3 extends TowerGrave2{


    {
        HP = HT = 180;

        spriteClass = TowerGrave3Sprite.class;

        maxMinions = 3;
        minionCooldown = 20;
        minionCooldownLeft = 1;

        minionHP = 35;
        minionDamageMax = 20;
        minionDamageMin = 10;
        minionAttackSkill = 10;
        minionDefenseSkill = 5;
        minionDR = 3;

        cost = 1000;

        upgrade1Cost = 1000;
        upgrade2Cost = 1500;

        upgradeLevel = 11;
        upgCount = 2;


    }
}