package com.towerpixel.towerpixeldungeon.items.armor.glyphs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.items.armor.Armor;
import com.towerpixel.towerpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Holy extends Armor.Glyph {//named purity in the game, because i dont like the word HOLINESS

    private static ItemSprite.Glowing HOLY = new ItemSprite.Glowing( 0xffee88 );

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {

        int level = Math.max(0, armor.buffedLvl());

        // lvl 0 ~ 33%
        // lvl 1 ~ 36%

        float procChance = (level+12f)/(level+36f) * procChanceMultiplier(defender);
        if ( Random.Float() < procChance ) {

            float powerMulti = Math.max(1f, procChance);

            if (defender.properties().contains(Char.Property.DEMONIC) || defender.properties().contains(Char.Property.UNDEAD)) {
                defender.HP += Math.sqrt(Math.sqrt(damage));
                if (defender.HP > defender.HT) {
                    defender.HP = defender.HT;
                }
                Buff.affect(attacker, Weakness.class, 2);

            }
        }
        if (armor.cursed) {armor.cursed = false; defender.sprite.emitter().start( ShadowParticle.UP, 0.05f, 10 );}//a minor addition for holy stuff being able to resist curses
        return damage;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return HOLY;
    }
}