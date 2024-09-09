package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.DamageSource;
import com.towerpixel.towerpixeldungeon.actors.buffs.Burning;
import com.towerpixel.towerpixeldungeon.actors.buffs.Ooze;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vulnerable;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.FistSprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWall3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWallObsidianSprite;

public class TowerWallRunic extends TowerCWall{

    {
        spriteClass = TowerWallObsidianSprite.class;
        HP = HT = 1000;
        cost = 1400;

        immunities.add(Burning.class);
        immunities.add(Vulnerable.class);
        immunities.add(Ooze.class);

        damageMin = 0;
        damageMax = 0;
        defMin = 0;
        defMax = 0;
        upgCount = 0;

        state = PASSIVE;
    }

    @Override
    public void damage(int dmg, Object src) {
        if (DamageSource.MAGICAL.contains(src.getClass())){
            super.damage(dmg/2,src);
        } else super.damage(dmg, src);
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerWallObsidianSprite sprite = (TowerWallObsidianSprite) super.sprite();
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
