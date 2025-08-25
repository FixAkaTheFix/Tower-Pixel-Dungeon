package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfAggression;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbTreatwounds extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_TREATWOUNDS;
    }

    @Override
    public void cast() {
        super.cast();

        Dungeon.hero.heal(
                Dungeon.hero.HT/10 + // partial 10 percent heal
                        5 +                 // flat 5 HP
                        (int)((Dungeon.hero.HT - Dungeon.hero.HP)*0.15f) // the higher is the HP lost, the better is the heal, up to 15 percent of your hp
        );

        Dungeon.hero.sprite.operate(Dungeon.hero.pos);
        Sample.INSTANCE.play(Assets.Sounds.GRASS);
    }

    @Override
    protected int castCooldown() {
        return 50;
    }
}