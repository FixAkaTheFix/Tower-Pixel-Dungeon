package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.MindVision;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.utils.GLog;

public class HuntressVision extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_HUNTRESS_INSIGHT;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, MindVision.class, 2);
        if(((Arena)Dungeon.level).chooseMob(((Arena) Dungeon.level).wave+1) instanceof Rat){
            GLog.i(Messages.get(StoneOfIntuition.class,"rat"));
        }
        else GLog.w(Messages.get(StoneOfIntuition.class, "mob", Messages.get(((Arena)Dungeon.level).chooseMob(((Arena) Dungeon.level).wave+1).getClass(), "name")));

    }

    @Override
    protected int castCost() {
        return 40 + Dungeon.depth*5;
    }
}
