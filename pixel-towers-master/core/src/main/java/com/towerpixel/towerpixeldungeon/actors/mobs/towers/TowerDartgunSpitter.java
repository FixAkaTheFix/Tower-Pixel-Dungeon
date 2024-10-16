package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.actors.buffs.Slow;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.PoisonParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgun3Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDartgunSpitterSprite;
import com.watabou.utils.Random;

public class TowerDartgunSpitter extends TowerDartgun3 {
    {
        HP = HT = 50;
        spriteClass = TowerDartgunSpitterSprite.class;

        attackRange = 11;
        cost = 1610;
        upgCount = 0;
        damageMin = 2;
        damageMax = 4;
        upgradeLevel = 8;
        poisonPower = 7;
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
                if (Random.Int(5)>2)Buff.affect(x, Slow.class, 2);
            }
        }
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }
}
