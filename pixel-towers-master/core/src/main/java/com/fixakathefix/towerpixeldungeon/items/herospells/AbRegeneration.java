package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class AbRegeneration extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_REGENERATION;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null){
                if (Char.findChar(cell)!=null){
                    Char ch = Char.findChar(cell);
                    Buff.affect(ch, Healing.class).setHeal(20,0,1);
                    CellEmitter.get(cell).burst(CPHeal.UP, 10);
                    cooldown();
                    Dungeon.hero.spendAndNext(1f);
                }
                }
            }
            @Override
            public String prompt() {
                return Messages.get(HeroSpellTargeted.class, "cell_choose");
            }
        };
    }



    @Override
    protected int castCooldown() {
        return 50;
    }
}
