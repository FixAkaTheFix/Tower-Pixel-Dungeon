package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbDisengage extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_DISENGAGE;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    boolean cheatingAttempt = false;
                    for (int i : PathFinder.NEIGHBOURS25) {
                        if (Char.findChar(cell + i) != null && Char.findChar(cell + i).buff(Paralysis.class) != null && Char.findChar(cell + i).buff(Invisibility.class) != null) {
                            cheatingAttempt = true;
                        }
                    }

                    Sample.INSTANCE.play(Assets.Sounds.PUFF);
                    if (Char.findChar(cell) != null && !(Char.findChar(cell)instanceof Arena.AmuletTower) && !(Char.findChar(cell).properties().contains(Char.Property.BOSS))) {
                        Char ch = Char.findChar(cell);
                        Buff.affect(ch, Invisibility.class, cheatingAttempt ? 2 : ch.alignment == Char.Alignment.ALLY ? 10 : 5);
                        Buff.affect(ch, Paralysis.class, cheatingAttempt ? 2 : ch.alignment == Char.Alignment.ALLY ? 10 : 5);
                    }
                    cooldown();
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
