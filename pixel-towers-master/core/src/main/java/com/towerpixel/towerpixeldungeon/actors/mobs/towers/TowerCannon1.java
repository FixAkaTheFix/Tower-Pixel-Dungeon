package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Cripple;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Lightning;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannon1Sprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerCannon1 extends TowerCShooting{

    {
        HP = HT = 30;
        spriteClass = TowerCannon1Sprite.class;

        viewDistance = 7;
        baseAttackDelay = 1.5f;

        cost = 300;
        upgrade1Cost = 300;
        damageMin = 2;
        damageMax = 7;
        upgradeLevel = 3;

    }

    public float damageExplosionMult = 0.5f;

    @Override
    public int attackSkill(Char target) {//Nope, no dodging an explosion at its epicentre((
        return 100;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        int cell;
        if (Dungeon.level.heroFOV[this.pos] || Dungeon.level.heroFOV[enemy.pos]){
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }
        for (int i : PathFinder.NEIGHBOURS8){
            cell = enemy.pos + i;
            Char ch = Char.findChar(cell);
            if (ch!=null){
                if (ch.alignment == Alignment.ALLY){
                    //friends receive 0 damage
                } else {
                    ch.damage (Math.round(damageRoll()*damageExplosionMult) - enemy.drRoll(),this);
                };//damages foes nearby, with lowered damage
                if (ch.pos == enemy.pos + i && ch.alignment != this.alignment && Math.random()>0.8) {
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, 1, false, true, getClass());
                }
                if (ch == enemy && Math.random()>0.9){
                    Buff.affect(ch, Cripple.class, 3);
                }
            }
            if (level.heroFOV[enemy.pos+i]) {
                CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);

            }
            if (level.heroFOV[enemy.pos]) {
                CellEmitter.center(enemy.pos).start(SmokeParticle.FACTORY, 0.3f, 8);
                CellEmitter.center(enemy.pos).start(SmokeParticle.FACTORY, 1f, 8);
            }
            if (level.flamable[cell]) {//affects terrain
                level.destroy(cell);
                GameScene.updateMap(cell);
            }
        }

        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }


    @Override
    protected boolean canAttack( Char enemy ) {
        return (new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos|| level.distance(enemy.pos, this.pos)<=viewDistance);
    }
}
