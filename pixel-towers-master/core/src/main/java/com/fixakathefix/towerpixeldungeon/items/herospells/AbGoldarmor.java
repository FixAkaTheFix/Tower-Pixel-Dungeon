package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.GoldArmor;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class AbGoldarmor extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_GOLDARMOR;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, GoldArmor.class).set(10);
    }

    @Override
    protected int castCooldown() {
        return 100;
    }
}
