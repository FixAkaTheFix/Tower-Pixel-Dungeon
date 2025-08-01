package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Skeleton;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbNecromancy extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_NECROMANCY;
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
                Sample.INSTANCE.play(Assets.Sounds.CURSED);
                if (ch != null) {
                    skeletonbud.die(Dungeon.hero);
                    Buff.affect(ch, Vertigo.class, 2);
                }
            }

        }

        Dungeon.hero.spendAndNext(1f);
    }

    @Override
    protected int castCooldown() {
        return Math.max(Math.max(45 - Dungeon.depth, 250 - Dungeon.hero.lvl*15), 20);
    }
}
