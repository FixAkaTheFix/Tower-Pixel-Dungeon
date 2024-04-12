package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.messages.Messages;

public class TowerCShooting extends Tower{
    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        String k;
        int attackDelayShow = Math.round(100/baseAttackDelay);
        int attackSkillShow = Math.round(attackSkill(hero)*10);
        info.append(Messages.get(this, "stats", HT , damageMin, damageMax, attackSkillShow, attackDelayShow, viewDistance));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

}
