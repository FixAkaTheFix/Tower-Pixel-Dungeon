package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.HeroSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public abstract class HeroSpell extends Item {

    {
        unique = true;
        keptThoughLostInvent = true;

        defaultAction = AC_CAST;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    protected int castCooldown(){
        return 10;//basic cost in cooldown turns
    }

    protected void cooldown(){
        Buff.affect(Dungeon.hero, AbilityCooldown.class, castCooldown());
    }

    public static final String AC_CAST		= "CAST";

    @Override
    public String info() {
        return super.info() + "\n\n" + Messages.get(this, "cost", castCooldown());
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
                    if (hero.buff(AbilityCooldown.class) == null) {
                        cast();
                    } else GLog.w(Messages.get(HeroSpell.class, "cooldown"));
                }
            });

        }
    }

    @Override
    public String status() {
        return castCooldown() + "T";
    }

    public void cast(){
        cooldown();
        updateQuickslot();
        //nothin by default
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}
