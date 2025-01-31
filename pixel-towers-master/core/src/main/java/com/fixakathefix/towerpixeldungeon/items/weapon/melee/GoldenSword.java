package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

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
        return  Math.round(4*damageModifier() +
                lvl*damageModifier());
    }

    @Override
    public int max(int lvl) {
        return  Math.round(7*damageModifier() +
                3*lvl*damageModifier());
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Dungeon.level.drop(new Gold(damage), attacker.pos);
        CellEmitter.center(defender.pos).burst(Speck.factory(Speck.COIN), 5);
        Sample.INSTANCE.play(Assets.Sounds.GOLD);
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