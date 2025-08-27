package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGraveEliteSprite;
import com.fixakathefix.towerpixeldungeon.sprites.SkeletonWarriorSprite;

public class TowerGraveElite extends TowerGrave3{



    {
        HP = HT = 350;

        spriteClass = TowerGraveEliteSprite.class;

        maxMinions = 2;
        minionCooldown = 50;
        minionCooldownLeft = 1;

        minionHP = 400;
        minionDamageMax = 25;
        minionDamageMin = 20;
        minionAttackSkill = 10;
        minionDefenseSkill = 0;
        minionDR = 6;

        cost = 2500;
        upgradeLevel = 11;
        upgCount=0;
    }

    @Override
    public void spawnMinion(int pos) {
        SkeletonWarriorMinion minion = new SkeletonWarriorMinion();
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

    public class SkeletonWarriorMinion extends SkeletonMinion1 {

        {
            spriteClass = SkeletonWarriorSprite.class;
            viewDistance = 6;
        }

        public SkeletonWarriorMinion() {
            super();
            Buff.affect(this, Battlecry.class, 30);
        }
    }

}