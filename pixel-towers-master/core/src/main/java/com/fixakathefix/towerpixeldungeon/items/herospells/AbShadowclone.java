package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbShadowclone extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_SHADOWCLONE;
    }

    @Override
    public void cast() {
        super.cast();
        ShadowClone.ShadowAlly myShadow = new ShadowClone.ShadowAlly();
        myShadow.HP = myShadow.HT = Dungeon.hero.HT;
        myShadow.pos = Dungeon.hero.pos;
        Sample.INSTANCE.play(Assets.Sounds.PUFF);
        GameScene.add(myShadow);
    }

    @Override
    protected int castCooldown() {
        return Math.max(125, 250 - Dungeon.depth*10);
    }
}
