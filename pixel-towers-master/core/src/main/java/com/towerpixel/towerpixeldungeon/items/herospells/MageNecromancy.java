package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vertigo;
import com.towerpixel.towerpixeldungeon.actors.mobs.Skeleton;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.scenes.CellSelector;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class MageNecromancy extends HeroSpellTargeted {
    {
        image = ItemSpriteSheet.HEROSPELL_MAGE_NECROMANCY;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell!=null) {
                    Char ch = Char.findChar(cell);
                    Skeleton skeletonbud = new Skeleton();
                    skeletonbud.pos = cell;
                    skeletonbud.alignment = Char.Alignment.ALLY;
                    GameScene.add(skeletonbud);
                    Dungeon.level.occupyCell(skeletonbud);
                    CellEmitter.get(cell).burst(ShadowParticle.UP, 5);
                    Sample.INSTANCE.play(Assets.Sounds.CURSED);
                    if (ch != null){
                        skeletonbud.die(Dungeon.hero);
                        Buff.affect(ch, Vertigo.class,2);
                    }
                    Dungeon.hero.spendAndNext(1f);
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
        return 150 + Dungeon.depth * 10;
    }
}
