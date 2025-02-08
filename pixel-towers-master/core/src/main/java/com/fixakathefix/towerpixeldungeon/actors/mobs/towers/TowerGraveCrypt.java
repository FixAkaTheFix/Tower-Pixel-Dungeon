package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGraveCryptSprite;
import com.fixakathefix.towerpixeldungeon.sprites.WraithSprite;

public class TowerGraveCrypt extends TowerCSpawning{

    {
        HP = HT = 150;

        spriteClass = TowerGraveCryptSprite.class;

        maxMinions = 5;
        minionCooldown = 5;
        minionCooldownLeft = 1;

        minionHP = 1;
        minionDamageMax = 12;
        minionDamageMin = 8;
        minionAttackSkill = 10;
        minionDefenseSkill = 10;
        minionDR = 0;

        cost = 2000;

        upgrade1Cost = 1000;
        upgrade2Cost = 1500;

        upgradeLevel = 3;
        upgCount=0;

        spawnsExcessMinions = false;


    }
    @Override
    public void spawnMinion(int pos) {
        WraithMinion minion = new WraithMinion();
        minion.pos = pos;

        Dungeon.level.occupyCell(minion);
        minion.state = minion.WANDERING;

        minion.HT = minion.HP = minionHP;
        minion.minionDamageMax= minionDamageMax;
        minion.minionDamageMin= minionDamageMin;
        minion.minionAttackSkill= minionAttackSkill;
        minion.minionDefenseSkill= minionDefenseSkill;
        minion.minionDR= minionDR;

        minion.alignment = alignment;
        minion.momID = id();
        minion.momTower = this;

        addMinion(minion);
        GameScene.add(minion);

        CellEmitter.get(this.pos).burst(ShadowParticle.UP, 5);
        CellEmitter.get(minion.pos).burst(ShadowParticle.UP, 5);
    }

    public static class WraithMinion extends TowerCSpawningMinion{
        {
            spriteClass = WraithSprite.class;

            diesWithoutMomtower = false;
            staysNearMomTower = false;
            flying = true;
        }

        public WraithMinion() {
            super();
        }
    }

}