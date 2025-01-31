package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.Quarterstaff.DefensiveStance;
import com.watabou.utils.Random;

public class Trisai extends MeleeWeapon {

    {
        image = ItemSpriteSheet.TRISAI;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;
        DLY = 0.8f;
        rarity = 2;
        critMult = 1.6f;
        critChance=0.06f;
        tier = 3;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(13*damageModifier() +    //16 base, down from 20
                4*lvl*damageModifier());   //scaling unchanged
    }
    @Override
    public int proc(Char attacker, Char defender, int damage) {
        defender.damage(defender.drRoll()/2, attacker);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public float abilityChargeUse(Hero hero) {
        return 2*super.abilityChargeUse(hero);
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        beforeAbilityUsed(hero);
        Buff.prolong(hero, DefensiveStance.class, 5f); //4 turns as using the ability is instant
        hero.sprite.operate(hero.pos);
        hero.next();
        afterAbilityUsed(hero);
    }
}
