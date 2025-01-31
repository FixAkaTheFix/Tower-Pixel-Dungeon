package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPLight;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;

public class AbBlessing extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_BLESSING;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    if (Char.findChar(cell) != null) {
                        Char ch = Char.findChar(cell);
                        if (ch instanceof Hero) Buff.affect(ch, Bless.class, 200); else Buff.affect(ch, Bless.class, 1000);
                        CellEmitter.get(cell).burst(CPLight.DOWN, 10);
                        cooldown();
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
        return 200;
    }
}
