package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Rush;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.SparkParticle;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbSwift extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_SWIFT;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, Rush.class, 3);
        Sample.INSTANCE.play(Assets.Sounds.TRAMPLE);
        CellEmitter.get(Dungeon.hero.pos).burst(SparkParticle.FACTORY, 10);
    }

    @Override
    protected int castCooldown() {
        return 50;
    }
}
