package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.GoldArmor;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPRed;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;

public class WarriorGoldarmor extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_WARRIOR_GOLDARMOR;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, GoldArmor.class).set(20);
    }

    @Override
    protected int castCost() {
        return 50;
    }
}
