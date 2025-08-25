package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.NoTowerWithering;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.SkeletonSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGrave1Sprite;

public class TowerGrave1 extends TowerCSpawning{

    {
        HP = HT = 50;

        spriteClass = TowerGrave1Sprite.class;

        maxMinions = 3;
        minionCooldown = 20;
        minionCooldownLeft = 1;

        minionHP = 10;
        minionDamageMax = 6;
        minionDamageMin = 3;
        minionAttackSkill = 10;
        minionDefenseSkill = 5;
        minionDR = 1;

        spawnsExcessMinions = true;

        cost = 200;

        upgrade1Cost = 300;

        upgradeLevel = 3;

    }
    @Override
    public void spawnMinion(int pos) {
        TowerCSpawningMinion minion = minion();
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

    @Override
    public void affectExcessMinion(TowerCSpawningMinion minion) {
        super.affectExcessMinion(minion);
        Buff.affect(minion, NoTowerWithering.class);
    }

    protected TowerCSpawningMinion minion(){
        return new SkeletonMinion1();
    }

    public static class SkeletonMinion1 extends TowerCSpawningMinion {
        {
            spriteClass = SkeletonSprite.class;
            properties.add(Property.UNDEAD);

            staysNearMomTower = true;
            diesWithoutMomtower = true;
        }
        public SkeletonMinion1() {
            super();
        }
    }

}
