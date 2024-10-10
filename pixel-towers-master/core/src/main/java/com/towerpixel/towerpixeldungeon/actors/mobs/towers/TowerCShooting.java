package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.messages.Messages;

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
        int attackDelayShow = Math.round(100/baseAttackDelay);
        int ac = Math.round(attackSkill(hero)*10);
        String attackSkillShow = ac + "%";
        if (ac >= 10000) attackSkillShow = Messages.get(this.getClass(),"nevermiss");
        info.append(Messages.get(this, "stats", HT , damageMin, damageMax, attackSkillShow, attackDelayShow, attackRange));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }

}
