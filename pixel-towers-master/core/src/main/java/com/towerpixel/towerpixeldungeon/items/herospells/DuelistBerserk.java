package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Stamina;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vulnerable;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class DuelistBerserk extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_DUELIST_BERSERK;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, Vulnerable.class, 20);
        Buff.affect(Dungeon.hero, Strength.class, 20);

        curUser.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
    }

    @Override
    protected int castCost() {
        return 100;
    }
}
