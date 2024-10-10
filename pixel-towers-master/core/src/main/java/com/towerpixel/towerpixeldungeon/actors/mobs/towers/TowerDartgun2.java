package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgun2Sprite;

public class TowerDartgun2 extends TowerDartgun1 {
    {
        HP = HT = 35;
        spriteClass = TowerDartgun2Sprite.class;

        attackRange = 14;
        cost = 430;
        upgrade1Cost = 480;
        damageMin = 7;
        damageMax = 13;
        upgradeLevel = 8;
        poisonPower = 5;
    }

    @Override
    public int attackSkill(Char target) {
        return 14;
    }
}
