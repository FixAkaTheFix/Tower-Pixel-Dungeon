package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.GoblinFatSprite;
import com.watabou.utils.Random;

public class GoblinFat extends Goblin {

        {
            spriteClass = GoblinFatSprite.class;

            HP = HT = 50;
            defenseSkill = 4;

            viewDistance = 10;
            baseSpeed = 1.05f;

            EXP = 5;
            maxLvl = 10;
        }

        @Override
        public int damageRoll() {
            return Random.NormalIntRange( 8, 12 );
        }

        @Override
        public int attackSkill( Char target ) {
            return 13;
        }

        @Override
        public int drRoll() {
            return super.drRoll() + Random.NormalIntRange(0,1);
        }

}
