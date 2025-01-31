package com.fixakathefix.towerpixeldungeon.items.armor.curses;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.armor.Armor;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Unreliable extends Armor.Glyph {

    private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {

        int level = Math.max(0, armor.buffedLvl());

        // lvl 0 ~ 12,5%
        // lvl 1 ~ 11,5%

        float procChance = (15f - level)/(level+120f);//reverse level proc chance: if your upgraded armor becomes cursed, it will still be more reliable for you not to die on accident
        int d = damage;
        if ( Random.Float() < procChance ) {
            damage += armor.DRMax();// Making the possible damage MUCH higher

            if (damage > d) {
                damage = d;// controlling damage, so it wont be bigger than the damage dealt
            }
            if (damage >= defender.HP) {
                damage = defender.HP - 1;//for people not to die of the additional damage and having a chance to heal themselves if something goes wrong
            }
            defender.sprite.centerEmitter().start(Speck.factory(Speck.SMOKE), 0.1f, 2);
        }
        return damage;
    }




    @Override
    public ItemSprite.Glowing glowing() {
        return BLACK;
    }

}
