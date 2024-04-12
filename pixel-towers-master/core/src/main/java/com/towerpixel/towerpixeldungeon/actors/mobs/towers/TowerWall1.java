package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWall1Sprite;

public class TowerWall1 extends TowerCWall{

    {
        spriteClass = TowerWall1Sprite.class;
        HP = HT = 100;
        cost = 100;
        upgrade1Cost = 150;
        damageMin = 0;
        damageMax = 1;
        defMin = 0;
        defMax = 1;
        upgradeLevel = 3;
        state = PASSIVE;
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerWall1Sprite sprite = (TowerWall1Sprite) super.sprite();
        if (HP>=HT*0.6f) {
            sprite.idle();
        }
        if (HP<HT*0.6f) {
            sprite.cracked();
        }

        if (HP<HT*0.3f) {
            sprite.broken();
        }

        return sprite;
    }
}
