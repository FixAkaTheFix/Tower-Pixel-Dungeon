package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.armor.Armor;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.sprites.StatueSprite;
import com.watabou.utils.Random;

public class Mercenary extends Mob {//TODO Finish someday this as the mercs will suit this TD game

    {
        spriteClass = StatueSprite.class;
        loot = Gold.class;
        lootChance = 0.5f;
    }

    @Override
    public int attackSkill( Char target ) {
        return 10+lvl;
    }

    @Override
    public int defenseSkill( Char target ) {
        return 5+lvl;
    }


    public int damageMin = 1;
    public int damageMax = 3;
    public int lvl = 1;
    public int armorMin = 0;
    public int armorMax = 0;
    public int exp = 0;
    protected Weapon weapon;
    protected Armor armor;





    @Override
    public int damageRoll() {
        if (weapon == null) return Random.NormalIntRange( 1, 3 );
        else return Random.NormalIntRange( damageMin, damageMax );
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(armorMin, armorMax);
    }
}
