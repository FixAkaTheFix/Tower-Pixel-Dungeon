package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Quarterstaff.DefensiveStance;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.watabou.utils.Random;

public class Trisai extends MeleeWeapon {

    {
        image = ItemSpriteSheet.TRISAI;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;
        DLY = 0.8f;
        rarity = 2;

        tier = 3;
    }

    @Override
    public int max(int lvl) {
        return  Math.round(13*(damageModifier()+1) +    //16 base, down from 20
                4*lvl*(damageModifier()+1));   //scaling unchanged
    }
    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Char def = defender;//making a spare "Mob" from a "Char" class to read the defenseSkill successfully
        if (def.defenseSkill(attacker) > 4){ damage += Random.Int(5);
        } else { damage+=Random.Int(def.defenseSkill(attacker));}
        if (damage>(4*(Dungeon.depth/5 +1) + this.level()*(Dungeon.depth/5+1))) damage = max(); //probably FIXME - not changing enemies armor yet (i want it to deal a bit more dmg to armored foes), make it based on reducing armor for 1 momemt, by //def.defenseSkill-=5; defender = def (like mob def)
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
