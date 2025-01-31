package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Cripple;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Banhammer extends MeleeWeapon {

    {
        image = ItemSpriteSheet.BAN_HAMMER;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 0.8f;
        ACC = 1.5f;
        DLY = 2f;
        tier = 5;
        rarity = 4;
    }

    @Override
    public int min(int lvl) {
        return Math.round(19 * (damageModifier()) +
                6* lvl * damageModifier());
    }
    @Override
    public int max(int lvl) {
        return Math.round(25 * damageModifier()  +
                8 * lvl * damageModifier());
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        Mace.heavyBlowAbility(hero, target, 1.50f, this);
    }

    @Override
    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect(defender, Cripple.class, Random.Int(10, 20));
        Buff.affect(defender, Vertigo.class, Random.Int(10, 20));
        return super.proc(attacker, defender, damage);
    }
}