package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
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

        critChance = 0.1f;
        critMult = 1.2f;

    }

    @Override
    public int max(int lvl) {
        return  3*(tier+1) +    //12 base
                lvl*(tier+1);
    }
    @Override
    public int min(int lvl) {
        return  2*(tier+1) +    //8 base
                lvl*(tier+1);
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