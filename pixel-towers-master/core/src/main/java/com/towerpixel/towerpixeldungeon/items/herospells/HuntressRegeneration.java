package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barkskin;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class HuntressRegeneration extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_HUNTRESS_REGENERATION;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null){
                if (Char.findChar(cell)!=null){
                    Char ch = Char.findChar(cell);
                    Buff.affect(ch, Healing.class).setHeal(50,0,1);
                    CellEmitter.get(cell).burst(CPHeal.UP, 10);
                }Dungeon.hero.spendAndNext(1f);
                }
                Dungeon.gold -= castCost();
                updateQuickslot();
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
