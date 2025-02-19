package com.fixakathefix.towerpixeldungeon.items.quest;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Doom;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vile;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.HeroClass;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;



public class Vilebloom extends Item {

    {
        image = ItemSpriteSheet.VILEBLOOM;
        unique = true;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public static final String AC_EAT = "EAT";

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_EAT );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_EAT)) {
            eat( hero );
        }
    }


    protected void eat( Hero hero ) {

        detach( hero.belongings.backpack );
        hero.spend( 1 );
        hero.busy();
        Sample.INSTANCE.play( Assets.Sounds.EAT);
        if (hero.heroClass == HeroClass.NECROHERO){
            Buff.affect(hero, Vile.class);
        } else Buff.affect(hero, Doom.class);
        hero.sprite.operate( hero.pos );
    }

    @Override
    public int value() {
        return 10;
    }
}

