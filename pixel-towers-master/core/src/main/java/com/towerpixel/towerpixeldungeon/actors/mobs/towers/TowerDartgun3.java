package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgun3Sprite;

public class TowerDartgun3 extends TowerDartgun2 {
    {
        HP = HT = 50;
        spriteClass = TowerDartgun3Sprite.class;

        viewDistance = 15;
        cost = 910;
        upgrade1Cost = 700;
        upgrade2Cost = 700;
        damageMin = 15;
        damageMax = 25;
        upgradeLevel = 8;
        poisonPower = 7;
        upgCount = 2;
    }

    @Override
    public int attackSkill(Char target) {
        return 16;
    }
}
