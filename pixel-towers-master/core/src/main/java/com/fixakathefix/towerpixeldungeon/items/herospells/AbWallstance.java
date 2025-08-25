package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WallStance;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbWallstance extends HeroSpell{
    {
        image = ItemSpriteSheet.HEROSPELL_WALLSTANCE;
    }

    @Override
    public void cast() {
        super.cast();
        Buff.affect(Dungeon.hero, WallStance.class, 12);
        Sample.INSTANCE.play(Assets.Sounds.HIT_PARRY);
        Dungeon.hero.sprite.operate(Dungeon.hero.pos);
    }

    @Override
    protected int castCooldown() {
        if (Dungeon.level == null) return 100;
        return 50 + (Dungeon.level.width()+ Dungeon.level.height())/2;
    }
}
