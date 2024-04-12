package com.towerpixel.towerpixeldungeon.items.weapon.melee;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Hex;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CurvedKnife extends MeleeWeapon {
    public static int sac = 0;
    {
        image = ItemSpriteSheet.CURVED_KNIFE;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;
        DLY = 0.7f;
        ACC = 1.2f;
        visiblyCursed();
        tier = 2;
        rarity = 3;
    }
    @Override
    public int min(int lvl) {
        return (tier + 1) +    //3 base
                lvl * (tier + 1) + (int)Math.sqrt(sac);   //scaling the same
    }
    @Override
    public int max(int lvl) {
        return 2 * (tier + 1) +    //6 base
                lvl * (tier + 1) + sac;   //scaling unchanged
    }
    ///they increase with each sacrificed duelist

    public int proc(Char attacker, Char defender, int damage) {
        Buff.affect( defender, Hex.class,2 );
        return super.proc(attacker, defender, damage);
    }

    @Override
    public int damageRoll(Char owner) {
        if (owner instanceof Hero) {
            Hero hero = (Hero) owner;
            Char enemy = hero.enemy();
            if (enemy instanceof Mob && ((Mob) enemy).surprisedBy(hero)) {
                int diff = max() - min();
                int damage = augment.damageFactor(Random.NormalIntRange(
                        min() + Math.round(diff * 0.7f),
                        max()));
                int exStr = hero.STR() - STRReq();
                if (exStr > 0) {
                    damage += Random.IntRange(0, exStr);
                }
                return damage;
            }
        }
        return super.damageRoll(owner);
    }

    @Override
    public float abilityChargeUse(Hero hero) {
        return 2 * super.abilityChargeUse(hero);
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        CurvedKnife.sacrifice(hero,  this);
    }
    public static void sacrifice(Hero hero, MeleeWeapon wep){
        wep.beforeAbilityUsed(hero);
        hero.die(1);
        sac++;//check hero class for sac
        if (sac>6) sac = 6;
        Sample.INSTANCE.play( Assets.Sounds.HIT_STAB );
        wep.afterAbilityUsed(hero);
    }
}
