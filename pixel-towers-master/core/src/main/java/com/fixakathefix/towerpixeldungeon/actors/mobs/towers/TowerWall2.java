package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.walls.TowerWall2Sprite;

public class TowerWall2 extends TowerCWall{

    {
        spriteClass = TowerWall2Sprite.class;
        HP = HT = 250;
        cost = 250;
        upgrade1Cost = 350;
        damageMin = 0;
        damageMax = 0;
        defMin = 1;
        defMax = 2;
        upgradeLevel = 10;
        state = PASSIVE;
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerWall2Sprite sprite = (TowerWall2Sprite) super.sprite();
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
