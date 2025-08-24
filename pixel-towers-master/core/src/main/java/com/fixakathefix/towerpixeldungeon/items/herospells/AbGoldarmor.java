package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.GoldArmor;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbGoldarmor extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_GOLDARMOR;
    }

    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.GOLD, 1f, 0.5f);
        CellEmitter.get(Dungeon.hero.pos).burst(ElmoParticle.FACTORY, 10);
        Buff.affect(Dungeon.hero, GoldArmor.class).set(10);
    }

    @Override
    protected int castCooldown() {
        return 200;
    }
}
