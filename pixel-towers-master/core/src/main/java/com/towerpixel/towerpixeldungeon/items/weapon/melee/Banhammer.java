package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Cripple;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vertigo;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Banhammer extends MeleeWeapon {

    {
        image = ItemSpriteSheet.BAN_HAMMER;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 0.8f;
        ACC = 1.5f;
        DLY = 2f;
        tier = 8;
        rarity = 4;
    }

    @Override
    public int min(int lvl) {
        return 10 * (tier + 1) +    //90 + 18/lvl
                2* lvl * (tier + 1);
    }
    @Override
    public int max(int lvl) {
        return 15 * (tier + 1) +    //135 + 36/lvl
                4 * lvl * (tier + 1);
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