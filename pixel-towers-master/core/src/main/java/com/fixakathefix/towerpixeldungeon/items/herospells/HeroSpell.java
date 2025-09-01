package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.KindOfWeapon;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Arena25;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.HeroSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;

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

    protected int castCooldown(){//basic cost in cooldown turns
        return 10;
    }

    public void cooldown(){
        if (Dungeon.level instanceof Arena25 && ((Arena25)Dungeon.level).yog.phase == 0){
            ((Arena25)Dungeon.level).yog.startBattle();
        }
        int finalCooldown = castCooldown();
        KindOfWeapon wep = Dungeon.hero.belongings.weapon();

        if (wep != null) finalCooldown *=
                wep.spellCooldownModifier*(Math.max(0.7, (10 - Math.sqrt(level()))/10));
        Buff.affect(Dungeon.hero, AbilityCooldown.class, finalCooldown);
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
        KindOfWeapon wep = Dungeon.hero.belongings.weapon();
        int finalCooldown = castCooldown();
        if (wep != null) finalCooldown *=
                wep.spellCooldownModifier*(Math.max(0.7, (10 - Math.sqrt(level()))/10));
        return finalCooldown + "T";
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

    @Override
    public int value() {
        return 60;
    }
}
