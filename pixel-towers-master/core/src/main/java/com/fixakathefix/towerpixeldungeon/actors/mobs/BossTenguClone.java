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
import com.fixakathefix.towerpixeldungeon.sprites.TenguSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BossTenguClone extends Mob {

    {
        spriteClass = TenguSprite.class;

        HP = HT = 1;
        defenseSkill = 40;

        viewDistance = 8;

        EXP = 0;
        maxLvl = 10;

    }

    private float alpha = 0.1f;

    private static final String ALPHA = "alfa";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(ALPHA, alpha);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        alpha = bundle.getFloat(ALPHA);
    }


    @Override
    public int damageRoll() {
        alpha+=0.2f;
        sprite.alpha(Math.min(alpha, 0.9f));//becomes more distinguishable with each successful attack
        return Random.NormalIntRange( 0, 5 );
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return super.canAttack(enemy) ||
                Math.random()>0.5 && distance(enemy) < 8;//randomly throws shurikens or attempts to engage in melee
    }

    @Override
    public int attackSkill( Char target ) {
        return 20;
    }

    @Override
    public int drRoll() {
        return 0;
    }

}
