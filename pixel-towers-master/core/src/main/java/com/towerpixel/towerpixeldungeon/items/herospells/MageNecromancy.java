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

public class MageNecromancy extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_MAGE_NECROMANCY;
    }

    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        for ( int i : PathFinder.NEIGHBOURS4){
            int cell = Dungeon.hero.pos + i;
            if (Dungeon.level.passable[cell]){
                Char ch = Char.findChar(cell);
                Skeleton skeletonbud = new Skeleton();
                skeletonbud.pos = cell;
                skeletonbud.alignment = Char.Alignment.ALLY;
                GameScene.add(skeletonbud);
                Dungeon.level.occupyCell(skeletonbud);
                CellEmitter.get(cell).burst(ShadowParticle.UP, 5);
                if (ch != null) {
                    skeletonbud.die(Dungeon.hero);
                    Buff.affect(ch, Vertigo.class, 2);
                }
            }

        }

        Dungeon.gold -= castCost();
        updateQuickslot();
        Dungeon.hero.spendAndNext(1f);
    }

    @Override
    protected int castCost() {
        return 300;
    }
}
