package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.KeenEye;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MindVision;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;

public class AbVision extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_INSIGHT;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, MindVision.class, 21);
        Buff.affect(Dungeon.hero, KeenEye.class, 21);
    }

    @Override
    protected int castCooldown() {
        return 180;
    }
}
