package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.hero.Talent;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.items.weapon.SpiritBow;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.ui.ActionIndicator;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.fixakathefix.towerpixeldungeon.ui.HeroIcon;
import com.fixakathefix.towerpixeldungeon.ui.QuickSlotButton;
import com.watabou.utils.Bundle;

public class PriorityTarget extends FlavourBuff {

    public static final float DURATION = 5f;

    {
        type = buffType.NEGATIVE;
        announced = true;
    }

    @Override
    public void detach() {
        //if our target is an enemy, reset the aggro of any allies targeting it
        if (target.isAlive()) {
            if (target.alignment == Char.Alignment.ENEMY) {
                for (Mob m : Dungeon.level.mobs) {
                    if (m.alignment == Char.Alignment.ALLY && m.isTargeting(target)) {
                        m.aggro(null);
                    }
                    if (target instanceof Mob && ((Mob) target).isTargeting(m)){
                        ((Mob) target).aggro(null);
                    }
                }
            }
        }
        super.detach();

    }

    @Override
    public int icon() {
        return BuffIndicator.MARK;
    }

    @Override
    public float iconFadePercent() {
        return Math.max(0, (DURATION - visualcooldown()) / DURATION);
    }
}
