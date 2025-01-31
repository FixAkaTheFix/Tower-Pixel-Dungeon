/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
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

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.sprites.GoblinSandSprite;
import com.watabou.utils.Random;

public class GoblinSand extends Goblin {

    {
        spriteClass = GoblinSandSprite.class;

        HP = HT = 30;
        defenseSkill = 5;

        viewDistance = 20;
        baseSpeed = 1.1f;

        critMult = 5f;
        critChance = 0.2f;
        EXP = 5;
        maxLvl = 10;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 3, 4 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 30;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (Math.random()>0.3){
            Buff.affect(enemy, Blindness.class,5);
            if (enemy instanceof Tower) ((Tower) enemy).enemy = null;//the tower loses it's target.
        }
        return super.attackProc(enemy, damage);
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0,1);
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return super.canAttack(enemy) || (distance(enemy)<=2);
    }
}
