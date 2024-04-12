package com.towerpixel.towerpixeldungeon.items.weapon.enchantments;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.items.weapon.Weapon;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;

public class Pure extends Weapon.Enchantment{
    private static ItemSprite.Glowing LIGHTCYAN = new ItemSprite.Glowing( 0x88FFFF );

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        return damage;
    }
    //check Char.hit

    @Override
    public ItemSprite.Glowing glowing() {
        return LIGHTCYAN;
    }

}
