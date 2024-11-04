package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.Gold;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class GoldenSword extends MeleeWeapon {

    {
        image = ItemSpriteSheet.GOLDEN_SWORD;
        hitSound = Assets.Sounds.HIT_SLASH;
        hitSoundPitch = 1f;
        tier = 3;
        rarity = 5;
    }

    @Override
    public int min(int lvl) {
        return  Math.round(4*(damageModifier()+1) +
                lvl*(damageModifier()+1));
    }

    @Override
    public int max(int lvl) {
        return  Math.round(7*(damageModifier()+1) +
                3*lvl*(damageModifier()+1));
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Dungeon.level.drop(new Gold(damage), attacker.pos);
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