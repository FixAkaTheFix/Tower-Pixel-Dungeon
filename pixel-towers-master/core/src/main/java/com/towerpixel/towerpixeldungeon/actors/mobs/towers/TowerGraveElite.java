package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TowerGraveEliteSprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.SkeletonWarriorSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerGraveElite extends TowerCSpawning{

    public static int minionHP;
    public static int minionDamageMax;
    public static int minionDamageMin;
    public static int minionAttackSkill;
    public static int minionDefenseSkill;
    public static int minionDR;

    {
        HP = HT = 300;

        spriteClass = TowerGraveEliteSprite.class;

        maxMinions = 1;
        minionCooldown = 70;
        minionCooldownLeft = 1;

        minionHP = 150;
        minionDamageMax = 25;
        minionDamageMin = 20;
        minionAttackSkill = 10;
        minionDefenseSkill = 1;
        minionDR = 6;

        cost = 2500;

        upgrade1Cost = 1000;
        upgrade2Cost = 1500;

        upgradeLevel = 3;
        upgCount=0;
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT , maxMinions, minionCooldown, minionHP, minionDamageMin, minionDamageMax, minionAttackSkill*10, minionDefenseSkill*20, minionDR, minionCount, maxMinions, minionCooldownLeft));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }
    @Override
    public void spawnMinion(int pos) {
        SkeletonWarriorMinion minion = new SkeletonWarriorMinion();
        minion.alignment = this.alignment;
        minion.mompos = this.pos;
        minion.pos = pos;
        GameScene.add(minion);
        Dungeon.level.occupyCell(minion);
        minion.state = minion.WANDERING;
        CellEmitter.get(this.pos).burst(ShadowParticle.UP, 5);
        CellEmitter.get(minion.pos).burst(ShadowParticle.UP, 5);
    }

    @Override
    public void die(Object cause) {
        ArrayList<Mob> ded = new ArrayList<>();
        for (Mob mob: Dungeon.level.mobs) if (!Dungeon.level.mobs.isEmpty()){
            if (mob instanceof SkeletonWarriorMinion && ((SkeletonWarriorMinion)mob).mompos == this.pos){
                ded.add(mob);
                CellEmitter.get(mob.pos).burst(ShadowParticle.UP, 5);
            }
        }
        if (!ded.isEmpty())for (Mob mob : ded){
            mob.die(this);
        };
        super.die(cause);
    }

    public static class SkeletonWarriorMinion extends Mob{
        int mompos;

        {
            spriteClass = SkeletonWarriorSprite.class;
            HP = HT = minionHP;
            defenseSkill = minionDefenseSkill;
            viewDistance = 7;
        }

        @Override
        protected boolean act() {
            this.beckon(mompos);
            return super.act();
        }

        @Override
        public int attackSkill(Char target) {
            return minionAttackSkill;
        }

        @Override
        public int drRoll() {
            return Random.IntRange(0, minionDR);
        }

        @Override
        public int damageRoll() {
            return Random.IntRange(minionDamageMin, minionDamageMax);
        }

        @Override
        public void die(Object cause) {
            super.die(cause);
            if(Char.findChar(mompos) instanceof TowerCSpawning ) ((TowerCSpawning)(Char.findChar(mompos))).minionCount--;
        }
        public static final String MOMPOS = "mompos";

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(MOMPOS, mompos);
        }

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            mompos = bundle.getInt(MOMPOS);
        }
    }

}