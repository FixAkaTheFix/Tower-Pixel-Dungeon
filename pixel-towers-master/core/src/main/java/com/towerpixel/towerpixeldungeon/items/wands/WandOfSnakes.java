/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.items.wands;

import static com.towerpixel.towerpixeldungeon.actors.Actor.findChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.actors.mobs.Snake;
import com.towerpixel.towerpixeldungeon.effects.MagicMissile;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.MagesStaff;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfSnakes extends DamageWand {

    {
        image = ItemSpriteSheet.WAND_SNAKES;

        collisionProperties = Ballistica.PROJECTILE;
    }

    public int min(int lvl){
        return 1+lvl;
    }

    public int max(int lvl){
        return 2+2*lvl;
    }

    @Override
    public void onZap(Ballistica bolt) {
        if (findChar(bolt.collisionPos) == null){

        Sample.INSTANCE.play( Assets.Sounds.GRASS );
        Snake snake = new Snake();
        snake.alignment = Char.Alignment.ALLY;
        snake.defenseSkill = 21 + buffedLvl()*3;
        snake.pos = bolt.collisionPos;
        GameScene.add(snake);
        } else {Buff.affect(findChar(bolt.collisionPos), Poison.class).set(4+buffedLvl());}
    }

    @Override
    public void onHit(MagesStaff staff, Char attacker, Char defender, int damage) {
        Buff.affect(defender, Poison.class).set((3 + staff.level())*Math.round(damage*0.5));
    }

    @Override
    public void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.SHAMAN_PURPLE,
                curUser.sprite,
                bolt.collisionPos,
                callback);
        Sample.INSTANCE.play(Assets.Sounds.ZAP);
    }

    @Override
    public void staffFx(MagesStaff.StaffParticle particle) {
        particle.color( 0x12EE11 ); particle.am = 0.8f;
        particle.setLifespan(3f);
        particle.speed.polar(Random.Float(PointF.PI2), 0.3f);
        particle.setSize( 1f, 2f);
        particle.radiateXY(2.5f);
    }
}
