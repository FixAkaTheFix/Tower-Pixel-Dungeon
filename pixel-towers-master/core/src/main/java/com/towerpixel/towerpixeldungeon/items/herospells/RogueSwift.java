package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barrier;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.buffs.Stamina;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPShield;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class RogueSwift extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_ROGUE_SWIFT;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, Stamina.class, 20);
        Sample.INSTANCE.play(Assets.Sounds.TRAMPLE);
        CellEmitter.get(Dungeon.hero.pos).burst(SparkParticle.FACTORY, 10);
    }

    @Override
    protected int castCost() {
        return 50+Dungeon.depth*5;
    }
}
