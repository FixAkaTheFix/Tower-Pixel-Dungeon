package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbNoAmulet extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_NOAMULET;
    }

    @Override
    public void cast() {
        super.cast();
        int foesAround = 0;

        Mob amuletto = ((Arena)(Dungeon.level)).amuletTower;
        for (int i : PathFinder.NEIGHBOURS25){
            if (Char.findChar(amuletto.pos + i)!=null && Char.findChar(amuletto.pos + i).alignment == Char.Alignment.ENEMY){
                foesAround++;
            }
        }
        Sample.INSTANCE.play(Assets.Sounds.PUFF);
        Buff.affect(amuletto, Invisibility.class, Math.max(3, 10-foesAround*2));

    }

    @Override
    protected int castCooldown() {
        return 100;
    }
}
