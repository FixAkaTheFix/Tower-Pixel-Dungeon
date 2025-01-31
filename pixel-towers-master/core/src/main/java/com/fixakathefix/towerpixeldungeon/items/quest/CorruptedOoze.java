package com.fixakathefix.towerpixeldungeon.items.quest;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vile;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;



public class CorruptedOoze extends Item {

    {
        image = ItemSpriteSheet.OOZE;
        unique = true;

    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public static final String AC_DRINK = "DRINK";

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_DRINK)) {
            eat( hero );
        }
    }


    protected void eat( Hero hero ) {

        detach( hero.belongings.backpack );
        hero.spend( 1 );
        hero.busy();
        Sample.INSTANCE.play( Assets.Sounds.DRINK);
        Buff.affect(hero, Vile.class);
        hero.sprite.operate( hero.pos );
    }
    public static class Recipe extends com.fixakathefix.towerpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Vilebloom.class, GooBlob.class, PotionOfLiquidFlame.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 10;

            output = CorruptedOoze.class;
            outQuantity = 1;
        }

    }

    @Override
    public int value() {
        return 30;
    }
}
