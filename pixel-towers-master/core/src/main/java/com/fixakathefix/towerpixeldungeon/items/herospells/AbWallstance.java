package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WallStance;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class AbWallstance extends HeroSpell{
    {
        image = ItemSpriteSheet.HEROSPELL_WALLSTANCE;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, WallStance.class, 10);
        Dungeon.hero.sprite.operate(Dungeon.hero.pos);
    }

    @Override
    protected int castCooldown() {
        return 50 + (Dungeon.level.width()+ Dungeon.level.height())/2;
    }
}
