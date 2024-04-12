package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.effects.Beam;
import com.towerpixel.towerpixeldungeon.sprites.TowerWandPrismaticSprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;

public class TowerWandPrismatic extends TowerWand3 {

    {
        HP = HT = 80;
        spriteClass = TowerWandPrismaticSprite.class;

        viewDistance = 9;//DPT = 32,5 * 1= 32,5 DPT/C = 32,5/2350 = 0,0138; 0,02346 for undead;
        upgCount = 0;

        baseAttackDelay = 0.5f;

        cost = 2000;
        damageMin = 5;
        damageMax = 11;
    }

    @Override
    public int attackSkill(Char target) {
        return 100;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {

        Beam b = new Beam.LightRay(this.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(enemy.pos));
        sprite.parent.add(b);
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }


    @Override
    public int attackProc( Char enemy, int damage ) {
        if (enemy.properties().contains(Property.DEMONIC)||enemy.properties().contains(Property.UNDEAD)){
        Buff.prolong(enemy, Weakness.class, 5);
        damage*=1.7;
        } else Buff.prolong(enemy, Blindness.class, 5);
        return damage;
    }

    @Override
    protected Char chooseEnemy() {
        return super.chooseEnemy();
    }
}