package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class AbImmortality extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_IMMORTALITY;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, AnkhInvulnerability.class, 10);
        CellEmitter.get(Dungeon.hero.pos).burst(SacrificialParticle.FACTORY, 3);
    }

    @Override
    protected int castCooldown() {
        return 200;
    }
}
