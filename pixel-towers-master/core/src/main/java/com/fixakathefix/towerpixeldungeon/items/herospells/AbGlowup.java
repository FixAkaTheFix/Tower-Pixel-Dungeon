package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPRed;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbGlowup extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_GLOWUP;
    }

    @Override
    public void cast() {
        super.cast();
        for (int i : PathFinder.NEIGHBOURS25) {
            Char ch = Char.findChar(Dungeon.hero.pos + i);
            if (ch != null &&ch != Dungeon.hero && ch.alignment== Char.Alignment.ALLY) {
                Buff.affect(ch, Battlecry.class, ch instanceof Tower ? Math.max(15, 70*(200/((Tower) ch).cost)) : 20);
                CellEmitter.floor(ch.pos).start(CPRed.UP, 0.05f, 10);
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
    }

    @Override
    protected int castCooldown() {
        return 100;
    }
}
