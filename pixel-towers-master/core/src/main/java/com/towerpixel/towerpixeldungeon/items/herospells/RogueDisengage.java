package com.towerpixel.towerpixeldungeon.items.herospells;

import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vertigo;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

public class RogueDisengage extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_ROGUE_DISENGAGE;

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
                    Dungeon.hero.spendAndNext(1f);
                }
                Dungeon.gold -= castCost();
                updateQuickslot();
            }
            @Override
            public String prompt() {
                return null;
            }
        };
    }



    @Override
    protected int castCost() {
        return 50 + Dungeon.depth*10;
    }
}
