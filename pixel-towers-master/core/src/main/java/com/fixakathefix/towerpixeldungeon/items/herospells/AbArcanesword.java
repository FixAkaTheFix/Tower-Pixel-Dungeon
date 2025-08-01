package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Elemental;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.RainbowParticle;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Corrupting;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Grim;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Pure;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Vampiric;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbArcanesword extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_ARCANESWORD;
    }

    @Override
    public void cast() {
        super.cast();

        Weapon wep = (Weapon) Dungeon.hero.belongings.weapon;
        if(wep!=null) {
            wep.enchant(Weapon.Enchantment.random(Corrupting.class, Grim.class, Vampiric.class, Pure.class));
        } else{
           Weapon newwep = (Weapon)Generator.random(Generator.Category.WEP_T2);
           if (newwep.enchantment==null) newwep.enchant(Weapon.Enchantment.random(Corrupting.class, Grim.class, Vampiric.class, Pure.class));
           Dungeon.level.drop(newwep, Dungeon.hero.pos);
        }
        CellEmitter.get(Dungeon.hero.pos).start(RainbowParticle.BURST, 0.2f, 10);
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
    }

    @Override
    protected int castCooldown() {
        return 200;
    }
}
