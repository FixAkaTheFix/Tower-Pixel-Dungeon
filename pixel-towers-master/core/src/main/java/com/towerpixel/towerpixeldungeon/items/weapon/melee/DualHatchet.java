package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
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
        return  3*(tier+1) +   //9 base
                lvl*(tier+1);  //+3 per level
    }
    @Override
    public int max(int lvl) {
        return  6*(tier+1) +        //18 base
                lvl*Math.round(1.6f*(tier+1));  //+4.8 per level, rounded
    }
    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Mace.heavyBlowAbility(hero, target, 1.50f, this);
    }
}
