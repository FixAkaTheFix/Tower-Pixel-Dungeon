package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;

public class RogueShadowclone extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_ROGUE_SHADOWCLONE;
    }

    @Override
    public void cast() {
        super.cast();
        ShadowClone.ShadowAlly myShadow = new ShadowClone.ShadowAlly();
        myShadow.HP = myShadow.HT = Dungeon.hero.HT;
        myShadow.pos = Dungeon.hero.pos;
        GameScene.add(myShadow);
    }

    @Override
    protected int castCost() {
        return 50 + Dungeon.hero.lvl*30;
    }
}
