package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Foliage;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barkskin;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.LeafParticle;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbOakskin extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_OAKSKIN;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    if (Char.findChar(cell) != null && Char.findChar(cell)!= Dungeon.hero) {
                        Char ch = Char.findChar(cell);
                        Buff.affect(ch, Barkskin.class).set((int)(Dungeon.depth/1.5) + 6, 30);
                        Sample.INSTANCE.play(Assets.Sounds.GRASS);
                        CellEmitter.get(cell).burst(LeafParticle.GENERAL, 10);
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
    public String info() {
        return Messages.get(this, "desc", Dungeon.depth+4);
    }

    @Override
    protected int castCooldown() {
        return 50 + Dungeon.depth*5;
    }
}
