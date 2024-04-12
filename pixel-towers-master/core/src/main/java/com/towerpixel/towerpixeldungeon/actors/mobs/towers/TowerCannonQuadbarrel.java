package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannonQuadbarrelSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerCannonQuadbarrel extends TowerCShooting {

    {
        HP = HT = 100;
        spriteClass = TowerCannonQuadbarrelSprite.class;

        viewDistance = 7;//DPT =32.5*0.66 = 21.45;DPT/C=21,45:1150=0.0187
        baseAttackDelay = 0.6f;

        upgCount = 0;

        cost = 2800;
        upgrade1Cost = 1500;
        damageMin = 10;
        damageMax = 15;

    }


    @Override
    public int attackSkill(Char target) {//Nope, no dodging an explosion at its epicentre((
        return 100;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        int cell;
        for (int i : PathFinder.NEIGHBOURS4) {
            cell = enemy.pos + i;
            Char ch = Actor.findChar(cell);
            if (ch != null) {
                if (ch.alignment == this.alignment) {
                } else if (Actor.findChar(enemy.pos) == ch) {
                } else ch.damage(Math.round(damageRoll()) - enemy.drRoll(), this);//damages foes nearby, does not damage the foe directly
            }
            CellEmitter.floor(cell).burst(BlastParticle.FACTORY, Random.Int(1, 3));
        }

        GameScene.updateFog();
        return super.attack(enemy, 0, 0, 0);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return (new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos||Dungeon.level.distance(enemy.pos, this.pos)<=viewDistance);
    }
}

