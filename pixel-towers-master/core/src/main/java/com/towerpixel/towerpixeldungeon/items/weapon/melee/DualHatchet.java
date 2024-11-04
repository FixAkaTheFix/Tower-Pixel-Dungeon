package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class DualHatchet extends MeleeWeapon {

    {
        image = ItemSpriteSheet.DUAL_HATCHET;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 0.8f;
        rarity = 2;

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
        return  Math.round(6*(damageModifier()+1) +
                2*lvl*(damageModifier()+1));
    }
    @Override
    public int max(int lvl) {
        return  Math.round(15*(damageModifier()+1) +
                5*lvl*(damageModifier()+1));
    }
    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Mace.heavyBlowAbility(hero, target, 1.50f, this);
    }
}
