package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class RogueNoAmulet extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_ROGUE_NOAMULET;
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
    protected int castCost() {
        return 200;
    }
}
