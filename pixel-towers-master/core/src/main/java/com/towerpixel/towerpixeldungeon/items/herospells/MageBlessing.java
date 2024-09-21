package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Bless;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.EnergyParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPLight;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;

public class MageBlessing extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_MAGE_BLESSING;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    if (Char.findChar(cell) != null) {
                        Char ch = Char.findChar(cell);
                        if (ch instanceof Hero) Buff.affect(ch, Bless.class, 200); else Buff.affect(ch, Bless.class, 2000);
                        CellEmitter.get(cell).burst(CPLight.DOWN, 10);
                        Dungeon.gold -= castCost();
                        updateQuickslot();
                        Dungeon.hero.spendAndNext(1f);
                    }
                }
            }
            @Override
            public String prompt() {
                return "Choose a cell target";
            }
        };
    }



    @Override
    protected int castCost() {
        return 100 + Dungeon.depth*5;
    }
}
