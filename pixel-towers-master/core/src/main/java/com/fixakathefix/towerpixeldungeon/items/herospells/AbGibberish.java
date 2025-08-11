package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.RainbowParticle;
import com.fixakathefix.towerpixeldungeon.items.wands.Wand;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfCorrosion;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfCorruption;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfDisintegration;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfFrost;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLightning;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfPrismaticLight;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class AbGibberish extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_GIBBERISH;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null){
                    CellEmitter.get(Dungeon.hero.pos).burst(RainbowParticle.BURST, 10);
                    Wand wando = Random.oneOf(
                            new WandOfBlastWave(),
                            new WandOfCorrosion(),
                            new WandOfFrost(),
                            new WandOfDisintegration(),
                            new WandOfCorruption(),
                            new WandOfLivingEarth(),
                            new WandOfPrismaticLight(),
                            new WandOfLightning()
                    );
                    curItem = wando;
                    cooldown();
                    wando.zapper.onSelect(cell);
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
        return 15;
    }
}
