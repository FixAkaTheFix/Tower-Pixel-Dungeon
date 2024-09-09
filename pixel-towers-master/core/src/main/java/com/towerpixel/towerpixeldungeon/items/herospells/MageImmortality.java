package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.GoldArmor;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class MageImmortality extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_MAGE_IMMORTALITY;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, AnkhInvulnerability.class, 10);
        CellEmitter.get(Dungeon.hero.pos).burst(SacrificialParticle.FACTORY, 3);
    }

    @Override
    protected int castCost() {
        return 100 + Dungeon.depth*20;
    }
}
