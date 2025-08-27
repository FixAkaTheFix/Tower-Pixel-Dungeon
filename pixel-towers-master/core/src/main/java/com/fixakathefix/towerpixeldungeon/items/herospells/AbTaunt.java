package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAggression;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbTaunt extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_TAUNT;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, StoneOfAggression.Aggression.class, 12);
        curUser.sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
        Sample.INSTANCE.play(Assets.Sounds.READ, 1f, 0.5f);
    }

    @Override
    protected int castCooldown() {
        if (Dungeon.level==null) return 200;
        return 75 + (Dungeon.level.width()+ Dungeon.level.height())/2;
    }
}
