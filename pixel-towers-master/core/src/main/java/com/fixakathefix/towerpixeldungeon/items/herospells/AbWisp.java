package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wisp;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPRed;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbWisp extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_SUN;
    }

    @Override
    public void cast() {
        super.cast();
        Wisp wisp = new Wisp();
        wisp.alignment = Char.Alignment.ALLY;
        wisp.pos = Dungeon.hero.pos;
        wisp.state = wisp.HUNTING;
        GameScene.add(wisp);
        Sample.INSTANCE.play(Assets.Sounds.HIT_MAGIC);
    }

    @Override
    protected int castCooldown() {
        return 25;
    }
}
