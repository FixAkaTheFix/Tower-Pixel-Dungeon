package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPShield;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbShield extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_SHIELD;
    }

    @Override
    public void cast() {
        super.cast();
        for (int i : PathFinder.NEIGHBOURS9) {
            Char ch = Char.findChar(Dungeon.hero.pos + i);
            if (ch != null && ch.alignment== Char.Alignment.ALLY && !(ch instanceof Arena.AmuletTower)) {
                Buff.affect(ch, Barrier.class).setShield(10+Dungeon.hero.lvl*4);
                CellEmitter.floor(ch.pos).start(CPShield.TOCENTER, 0.01f, 10);
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.MASTERY);
    }

    @Override
    protected int castCooldown() {
        return 80;
    }
}
