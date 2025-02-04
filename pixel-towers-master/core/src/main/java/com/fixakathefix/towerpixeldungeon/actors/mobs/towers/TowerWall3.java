package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.walls.TowerWall3Sprite;

public class TowerWall3 extends TowerCWall{

    {
        spriteClass = TowerWall3Sprite.class;
        HP = HT = 500;
        cost = 600;

        damageMin = 0;
        damageMax = 0;
        defMin = 2;
        defMax = 4;

        upgradeLevel = 16;

        upgrade1Cost = 800;
        upgrade2Cost = 1200;
        upgCount = 2;

        state = PASSIVE;
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerWall3Sprite sprite = (TowerWall3Sprite) super.sprite();
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
