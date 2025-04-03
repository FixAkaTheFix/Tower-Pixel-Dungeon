package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hex;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CurvedKnife extends MeleeWeapon {
    public static int sac = SPDSettings.knifelevel();
    {
        image = ItemSpriteSheet.CURVED_KNIFE;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 1.1f;
        DLY = 0.7f;
        ACC = 1.2f;
        visiblyCursed();
        critChance = 0.05f;
        tier = 2;
        rarity = 2;
    }
    @Override
    public int min(int lvl) {
        return Math.round(5 * damageModifier() +    //3 base
                3*lvl * damageModifier() + (int)Math.sqrt(sac));   //scaling the same
    }
    @Override
    public int max(int lvl) {
        return Math.round(10 * damageModifier() +    //6 base
                4*lvl * damageModifier() + sac);   //scaling unchanged
    }
    ///they increase with each sacrificed duelist

    public int proc(Char attacker, Char defender, int damage) {
        if (level()>0) Buff.affect( defender, Hex.class,4 * level()  );
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
        SPDSettings.knifeLevel(Math.min(SPDSettings.knifelevel() + 1, 6));
        Sample.INSTANCE.play( Assets.Sounds.HIT_STAB );
        wep.afterAbilityUsed(hero);
        Badges.validateDuelistSac();
    }
}
