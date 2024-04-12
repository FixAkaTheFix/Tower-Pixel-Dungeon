package com.towerpixel.towerpixeldungeon.items.weapon.curses;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.items.weapon.Weapon;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Bundle;

public class Degrading extends Weapon.Enchantment {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );
    int brkness = 200;

    @Override
    public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        brkness--;
        if (brkness <= 160) {weapon.degrade(); brkness = 200;}
        return Math.round(damage*brkness/200);
    }

    @Override
    public boolean curse() {
        return true;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }
    private static final String BRKNESS = "brkness";

    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put(BRKNESS, brkness);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        brkness = bundle.getInt(BRKNESS);
        //pre-1.3 saves
        if (brkness <= 160){
            brkness = 200;
        }
    }


}