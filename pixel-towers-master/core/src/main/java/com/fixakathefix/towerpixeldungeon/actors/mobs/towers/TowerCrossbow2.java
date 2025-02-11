package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbow2Sprite;

public class TowerCrossbow2 extends TowerCrossbow1{
    {
        HP = HT = 75;
        spriteClass = TowerCrossbow2Sprite.class;


        baseAttackDelay = 1.5f;//dpt/c = 0.008

        upgradeLevel = 8;

        cost = 450;
        upgrade1Cost = 550;
        damageMin = 3;
        damageMax = 8;
    }
    @Override
    public int attackSkill(Char target) {
        return 13;
    }
}
