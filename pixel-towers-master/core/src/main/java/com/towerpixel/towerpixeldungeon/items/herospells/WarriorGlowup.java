package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barrier;
import com.towerpixel.towerpixeldungeon.actors.buffs.Battlecry;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.PhysicalEmpower;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPRed;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPShield;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class WarriorGlowup extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_WARRIOR_GLOWUP;
    }

    @Override
    public void cast() {
        super.cast();
        for (int i : PathFinder.NEIGHBOURS25) {
            Char ch = Char.findChar(Dungeon.hero.pos + i);
            if (ch != null && ch.alignment== Char.Alignment.ALLY && ch instanceof Tower && !(ch instanceof Arena.AmuletTower)) {
                Buff.affect(ch, Battlecry.class, Math.max(10, 30*(200/((Tower) ch).cost)));
                CellEmitter.floor(ch.pos).start(CPRed.UP, 0.05f, 10);
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.CHALLENGE);
    }

    @Override
    protected int castCost() {
        return 300;
    }
}
