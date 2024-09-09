package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barrier;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPShield;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class WarriorShield extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_WARRIOR_SHIELD;
    }

    @Override
    public void cast() {
        super.cast();
        for (int i : PathFinder.NEIGHBOURS9) {
            Char ch = Char.findChar(Dungeon.hero.pos + i);
            if (ch != null && ch.alignment== Char.Alignment.ALLY && !(ch instanceof Arena.AmuletTower)) {
                Buff.affect(ch, Barrier.class).setShield(10+Dungeon.depth*3);
                CellEmitter.floor(ch.pos).start(CPShield.TOCENTER, 0.01f, 10);
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.MASTERY);
    }

    @Override
    protected int castCost() {
        return 30+Dungeon.depth*9;
    }
}
