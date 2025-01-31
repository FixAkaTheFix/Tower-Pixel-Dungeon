package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class DualHatchet extends MeleeWeapon {

    {
        image = ItemSpriteSheet.DUAL_HATCHET;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 0.8f;
        rarity = 2;

        critChance = 0.05f;
        critMult = 1.5f;
        tier = 2;
        ACC = 0.8f; //0.8x accuracy
        //see Hero.canSurpriseAttack for the "cant surprise attack" code
    }
    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }
    @Override
    public int min(int lvl) {
        return  Math.round(6*damageModifier() +
                2*lvl*damageModifier());
    }
    @Override
    public int max(int lvl) {
        return  Math.round(15*damageModifier() +
                5*lvl*damageModifier());
    }
    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Mace.heavyBlowAbility(hero, target, 1.50f, this);
    }
}
