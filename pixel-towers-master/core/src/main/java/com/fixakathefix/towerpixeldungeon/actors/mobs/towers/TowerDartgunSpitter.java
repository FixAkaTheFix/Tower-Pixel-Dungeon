package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Poison;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Slow;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.PoisonParticle;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerDartgunSpitterSprite;
import com.watabou.utils.Random;

public class TowerDartgunSpitter extends TowerDartgun3 {
    {
        HP = HT = 200;
        spriteClass = TowerDartgunSpitterSprite.class;

        attackRange = 11;
        cost = 1610;
        upgCount = 0;
        damageMin = 2;
        damageMax = 4;
        upgradeLevel = 8;
        poisonPower = 10;
    }


    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        Ballistica poisonSplashLlistica = new Ballistica(pos, enemy.pos, Ballistica.WONT_STOP);
        for (int splashPos : poisonSplashLlistica.subPath(1,attackRange)) {

            if (splashPos - Dungeon.level.width() >=0) CellEmitter.center(splashPos - Dungeon.level.width()).burst(PoisonParticle.CELLSPLASH, 30);
            Char x = Char.findChar(splashPos);
            if (splashPos == poisonSplashLlistica.collisionPos) break;
            if (x!=null && x.alignment!=this.alignment) {
                Buff.affect(x, Poison.class).set(poisonPower);
                if (Random.Int(6)<2)Buff.affect(x, Slow.class, 2);
            }
        }
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }
}
