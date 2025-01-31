package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class BoneMachete extends MeleeWeapon {

    {
        image = ItemSpriteSheet.BONE_MACHETE;
        hitSound = Assets.Sounds.BONES;
        hitSoundPitch = 1.2f;
        tier = 3;
        rarity = 2;

        critChance = 0.3f;
        critMult = 2f;

    }

    @Override
    public int max(int lvl) {
        return  Math.round(11*damageModifier() +    //12 base
                5*lvl*damageModifier());

    }
    @Override
    public int min(int lvl) {
        return  Math.round(3*damageModifier() +    //8 base
                3*lvl*damageModifier());
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