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

package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.FlavourBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Protected;
import com.towerpixel.towerpixeldungeon.actors.buffs.Regeneration;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.buffs.Terror;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPRed;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPShield;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.GoblinShamanSprite;
import com.watabou.utils.Random;

import java.util.ArrayList;

public abstract class GoblinShaman extends Mob {

    {
        HP = HT = 70;
        defenseSkill = 5;
        viewDistance = 4;
        baseSpeed = 1.04f;
        EXP = 10;
        maxLvl = 15;
        immunities.add(Terror.class);

    }
    public Buff buff;



    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 5, 10 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 10;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 1);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return super.canAttack(enemy)
                || (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&(distance(enemy)<5));
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        ArrayList<Char> charsToEmpower = new ArrayList<>();
        for (Mob ch: Level.mobs) {
            if (distance(ch) < 5 && ch.alignment == alignment && !(this instanceof ShamanRegen && ch.HP>=ch.HT)) {
                charsToEmpower.add(ch);
            }
        }
        if (!charsToEmpower.isEmpty()) {
            if (buff!=null) {
                Char empoweredChar = charsToEmpower.get(Random.Int(charsToEmpower.size()));
                if (this instanceof ShamanRegen){
                    for (int i = 0; i < 10;i++) if (empoweredChar.HP<empoweredChar.HT) empoweredChar.HP++;
                } else if (buff instanceof FlavourBuff) {
                    FlavourBuff buff2 = (FlavourBuff) buff;
                    Buff.affect(empoweredChar, buff2.getClass(), 10);
                } else Buff.affect(empoweredChar, buff.getClass());


                if (this instanceof ShamanRegen) {
                    CellEmitter.floor(empoweredChar.pos).start(CPHeal.UP, 0.005f, 10);
                } else if (this instanceof ShamanStrength) {
                    CellEmitter.floor(empoweredChar.pos).start(CPRed.UP, 0.005f, 10);
                } else if (this instanceof ShamanShield) {
                    CellEmitter.floor(empoweredChar.pos).start(CPShield.TOCENTER, 0.005f, 15);
                }
            }
        }
        return true;
    }

    public static class ShamanStrength extends GoblinShaman {
        {
            spriteClass = GoblinShamanSprite.ShamanStrength.class;
            buff = new Strength();
        }
    }
    public static class ShamanShield extends GoblinShaman {
        {
            spriteClass = GoblinShamanSprite.ShamanShield.class;
            buff = new Protected();
        }
    }
    public static class ShamanRegen extends GoblinShaman {
        {
            spriteClass = GoblinShamanSprite.ShamanRegen.class;
            buff = new Regeneration();//does not use the buff just a mark;
        }
    }
    public static class ShamanFake extends GoblinShaman {
        {
            spriteClass = GoblinShamanSprite.ShamanFake.class;
        }
    }

    public static Class<? extends GoblinShaman> random(){
        float roll = Random.Float();
        if (roll < 0.3f){
            return GoblinShaman.ShamanRegen.class;
        } else if (roll < 0.6f){
            return GoblinShaman.ShamanStrength.class;
        } else if (roll < 0.9f) {
            return GoblinShaman.ShamanShield.class;
        }
        return GoblinShaman.ShamanFake.class;
    }

}
