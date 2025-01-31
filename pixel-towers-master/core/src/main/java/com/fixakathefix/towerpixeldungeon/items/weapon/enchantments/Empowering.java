package com.fixakathefix.towerpixeldungeon.items.weapon.enchantments;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;


public class Empowering extends Weapon.Enchantment {

    private static ItemSprite.Glowing GOLDEN = new ItemSprite.Glowing( 0xFFDC18 );

    @Override
    public int proc( Weapon weapon, Char attacker, Char defender, int damage ) {//Gives blessing sometimes
        int level = Math.max( 0, weapon.buffedLvl() );
        float procChance = (level+5f)/(level+50f) * procChanceMultiplier(attacker);
        if (Random.Float() < procChance){
            Buff.affect(attacker, Bless.class, 5);
            attacker.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 3);
        }
        return damage+1;//Just a nice addition of +1 damage
    }
    //ALSO check Char.hit, empowering weapons increase overall accuracy by 5%

    @Override
    public Glowing glowing() {
        return GOLDEN;
    }

}