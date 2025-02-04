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

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.DamageSource;
import com.fixakathefix.towerpixeldungeon.items.food.MysteryMeat;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.MagiCrabSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MagiCrab extends Crab {

    {
        spriteClass = MagiCrabSprite.class;

        HP = HT = 50;
        defenseSkill = 0;
        baseSpeed = 1.2f;
        EXP = 6;

        state = WANDERING;

        loot = new MysteryMeat().quantity(2);
        lootChance = 1f;

        targetingPreference = TargetingPreference.NOT_HERO;

        properties.add(Property.MINIBOSS);
    }

    @Override
    public void damage( int dmg, Object src ){
        //crab blocks all magical or elemental damage
        if (DamageSource.MAGICAL.contains(src.getClass()))
        {
            sprite.showStatus( CharSprite.MYSTERIOUS, Messages.get(this, "def_verb") );
            Sample.INSTANCE.play( Assets.Sounds.CURSED, 1, Random.Float(0.96f, 1.05f));
        } else {
            super.damage( dmg, src );
        }
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 4, 8 );
    }

    @Override
    public int drRoll() {
        return 0;
    }
}
