package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;

public class TowerCShooting extends TowerNotliving{

    {
        viewDistance = 40;
    }
    public Class<? extends Buff> excludeBuff = null;

    public int attackRange = 0;


    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        int attackDelayShow = Math.round(100/attackDelay());
        int ac = Math.round(attackSkill(hero)*10);
        String attackSkillShow = ac + "%";
        if (ac >= 10000) attackSkillShow = Messages.get(this.getClass(),"nevermiss");
        info.append(Messages.get(this, "stats", HT , damageMin, damageMax, attackSkillShow, attackDelayShow, attackRange));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

    @Override
    protected boolean getCloser(int target) {
        if (buff(Animated.class) !=null) {
            if (Dungeon.level.distance(pos, hero.pos)>0) return super.getCloser(hero.pos);
            if (state == HUNTING) {
                return enemySeen && getFurther( target );
            } else {
                return super.getCloser( target );
            }
        } else return true;
    }

    @Override
    protected boolean getFurther(int target) {
        if (buff(Animated.class) !=null) return super.getFurther(target); else return true;
    }
}
