package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barrier;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPShield;
import com.towerpixel.towerpixeldungeon.items.Generator;
import com.towerpixel.towerpixeldungeon.items.KindOfWeapon;
import com.towerpixel.towerpixeldungeon.items.weapon.Weapon;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Corrupting;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Grim;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Pure;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Vampiric;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class DuelistArcanesword extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_DUELIST_ARCANESWORD;
    }

    @Override
    public void cast() {
        super.cast();

        Weapon wep = (Weapon) Dungeon.hero.belongings.weapon;
        if(wep!=null) {
            wep.enchant(Weapon.Enchantment.random(Corrupting.class, Grim.class, Vampiric.class, Pure.class));
        } else{
           Weapon newwep = (Weapon)Generator.random(Generator.Category.WEAPON);
           newwep.identify();
           newwep.enchant(Weapon.Enchantment.random(Corrupting.class, Grim.class, Vampiric.class, Pure.class));
           Dungeon.level.drop(newwep, Dungeon.hero.pos);
        }

        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
    }

    @Override
    protected int castCost() {
        return 100+Dungeon.depth*10;
    }
}
