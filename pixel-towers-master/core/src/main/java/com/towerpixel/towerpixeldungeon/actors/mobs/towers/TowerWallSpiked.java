package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.DamageSource;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWall3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWallSpikeSprite;

public class TowerWallSpiked extends TowerCWall{

    {
        spriteClass = TowerWallSpikeSprite.class;
        HP = HT = 1000;
        cost = 1800;

        damageMin = 0;
        damageMax = 0;
        defMin = 3;
        defMax = 15;
        upgCount = 0;

        state = PASSIVE;
    }

    @Override
    public void damage(int dmg, Object src) {
        if (src instanceof Mob) if (distance((Mob)src)<=1 && !(DamageSource.MAGICAL.contains(src))) ((Mob)src).damage(dmg/5, this);
        super.damage(dmg, src);
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerWallSpikeSprite sprite = (TowerWallSpikeSprite) super.sprite();
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
