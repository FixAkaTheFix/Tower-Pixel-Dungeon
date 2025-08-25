package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.IceWallSprite;
import com.fixakathefix.towerpixeldungeon.sprites.walls.TowerWall1Sprite;
import com.watabou.utils.Bundle;

public class IceWall extends TowerCWall {
    {
        spriteClass = IceWallSprite.class;
        HP = HT = 50;
        cost = 100;
        upgrade1Cost = 150;
        damageMin = 0;
        damageMax = 1;
        defMin = 0;
        defMax = 1;
        upgradeLevel = 3;
        state = PASSIVE;
        upgCount = 0;
        sellable = false;
        properties.add(Property.ICY);
        properties.add(Property.INORGANIC);
    }

    public IceWall(){
        super();
        Buff.affect(this, Melting.class);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (distance(enemy) <= 1) Buff.prolong(enemy, Chill.class,2);
        return super.defenseProc(enemy, damage);
    }

    public static class Melting extends Buff {
        {
            announced = false;
        }

        private float buildToDamage = 0f;

        @Override
        public boolean act() {
            buildToDamage += target.HT/300f;
            int damage = (int)buildToDamage;
            buildToDamage -= damage;
            if (damage > 0)
                target.damage(damage, this);
            spend(TICK);
            return true;
        }
        private static final String BUILDTODAMAGE = "buildtodamage";

        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            bundle.put(BUILDTODAMAGE, buildToDamage);
        }

        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            buildToDamage = bundle.getFloat(BUILDTODAMAGE);
        }
    }
}
