package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Undying;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbUndying extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_UNDYING;
    }
    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, Undying.class, 60);
        curUser.sprite.centerEmitter().start(ShadowParticle.CURSE, 0.3f, 3 );
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
    }
    @Override
    protected int castCooldown() {
        return 80;
    }
}


