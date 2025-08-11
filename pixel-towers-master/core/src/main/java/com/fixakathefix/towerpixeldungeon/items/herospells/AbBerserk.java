package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbBerserk extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_BERSERK;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, Strength.class, 15);

        curUser.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
    }

    @Override
    protected int castCooldown() {
        return 80;
    }
}
