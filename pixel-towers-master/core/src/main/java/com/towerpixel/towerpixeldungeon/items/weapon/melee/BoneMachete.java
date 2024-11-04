package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Bleeding;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class BoneMachete extends MeleeWeapon {

    {
        image = ItemSpriteSheet.BONE_MACHETE;
        hitSound = Assets.Sounds.BONES;
        hitSoundPitch = 1.2f;
        tier = 3;
        rarity = 2;

        critChance = 0.2f;
        critMult = 1.3f;

    }

    @Override
    public int max(int lvl) {
        return  Math.round(15*(damageModifier()+1) +    //12 base
                5*lvl*(damageModifier()+1));

    }
    @Override
    public int min(int lvl) {
        return  Math.round(9*(damageModifier()+1) +    //8 base
                3*lvl*(damageModifier()+1));
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(attacker, Bleeding.class).set(Dungeon.depth/2);
        return super.proc(attacker, defender, damage);
    }

    @Override
    public float abilityChargeUse( Hero hero ) {
        if (hero.buff(Sword.CleaveTracker.class) != null){
            return 0;
        } else {
            return super.abilityChargeUse( hero );
        }
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Sword.cleaveAbility(hero, target, 1.27f, this);
    }
}