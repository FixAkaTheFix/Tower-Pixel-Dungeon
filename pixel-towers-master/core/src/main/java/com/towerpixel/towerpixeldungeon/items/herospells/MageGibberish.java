package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.items.wands.Wand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfCorrosion;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfCorruption;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfDisintegration;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfFrost;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLightning;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfPrismaticLight;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class MageGibberish extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_MAGE_GIBBERISH;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null){
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
                    wando.zapper.onSelect(cell);
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
        return 20;
    }
}
