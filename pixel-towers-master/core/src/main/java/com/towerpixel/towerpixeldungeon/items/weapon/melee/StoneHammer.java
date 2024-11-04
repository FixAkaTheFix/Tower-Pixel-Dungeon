package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class StoneHammer extends MeleeWeapon {

    {
        image = ItemSpriteSheet.STONE_HAMMER;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 0.6f;
        rarity = 2;
        tier = 3;
        ACC = 0.7f; //-30% to accuracy
        DLY = 1.2f; //-20% to speed
    }

    @Override
    public int min(int lvl) {
        return  Math.round(8*(damageModifier()+1) +    //15 base, down from 30
                2*lvl*(damageModifier()+1));   //4/lvl
    }
    @Override
    public int max(int lvl) {
        return  Math.round(15*(damageModifier()+1) +    //24 base, down from 30
                6*lvl*(damageModifier()+1));   //8/lvl
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Mace.heavyBlowAbility(hero, target, 1.50f, this);
    }

}
