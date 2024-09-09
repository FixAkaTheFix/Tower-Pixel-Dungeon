package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.HeroSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public abstract class HeroSpell extends Item {

    {
        unique = true;
        keptThoughLostInvent = true;

        defaultAction = AC_CAST;
    }

    protected int castCost(){
        return 100;//basic cost in coins
    }

    public static final String AC_CAST		= "CAST";

    @Override
    public String info() {
        return super.info() + "\n\n" + Messages.get(this, "cost", castCost());
    }

    @Override
    public ArrayList<String> actions(Hero hero ) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add( AC_CAST );
        return actions;
    }
    @Override
    public void execute( Hero hero, String action ) {

        GameScene.cancel();
        curUser = hero;
        curItem = this;

        if (action.equals( AC_CAST )) {

            ((HeroSprite)hero.sprite).castSpell(new Callback() {
                @Override
                public void call() {
                    if (Dungeon.gold >= castCost()) {
                        cast();
                    } else GLog.w(Messages.get(HeroSpell.class, "no_coins"));
                }
            });

        }
    }

    @Override
    public String status() {
        return castCost()+"g";
    }

    public void cast(){
        Dungeon.gold -= castCost();
        updateQuickslot();
        //nothin by default
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
