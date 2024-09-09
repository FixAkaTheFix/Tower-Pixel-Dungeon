package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barkskin;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class HuntressOakskin extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_HUNTRESS_OAKSKIN;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    if (Char.findChar(cell) != null) {
                        Char ch = Char.findChar(cell);
                        Buff.affect(ch, Barkskin.class).set(Random.Int(3, 7), 10);
                        CellEmitter.get(cell).burst(ElmoParticle.FACTORY, 10);
                    } Dungeon.hero.spendAndNext(1f);
                    Dungeon.gold -= castCost();
                    updateQuickslot();
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
        return 50 + Dungeon.depth*5;
    }
}
