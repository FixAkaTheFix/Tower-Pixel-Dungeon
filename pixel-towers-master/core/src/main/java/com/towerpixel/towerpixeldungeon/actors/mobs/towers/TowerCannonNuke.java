package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannonNukeSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TowerCannonNuke extends TowerCShooting{

    {
        HP = HT = 100;
        spriteClass = TowerCannonNukeSprite.class;

        viewDistance = 7;//DPT =32.5*0.66 = 21.45;DPT/C=21,45:1150=0.0187
        baseAttackDelay = 8f;

        upgCount = 0;

        cost = 2800;
        upgrade1Cost = 1500;
        damageMin = 10;
        damageMax = 40;

    }
    public float damageExplosionMult = 1f;

    @Override
    public int attackSkill(Char target) {//Nope, no dodging an explosion at its epicentre((
        return 100;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {

        int cell;
        //CellEmitter.center(enemy.pos).burst(SmokeParticle.FACTORY, 30);


        for (int i : PathFinder.NEIGHBOURS25){
            cell = enemy.pos + i;
            Char ch = Actor.findChar(cell);
            if (ch!=null){
                if (ch.alignment == Alignment.ALLY){
                    ch.damage (Math.round(damageRoll()*0.05f) - enemy.drRoll(),this);//friends receive 5% damage only
                } else if (Actor.findChar(enemy.pos)==ch) {
                } else ch.damage (Math.round(damageRoll()*damageExplosionMult) - enemy.drRoll(),this);//damages foes nearby, with lowered damage


                if (ch.pos == enemy.pos + i && ch.alignment != this.alignment) {
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, 5, false, true, getClass());
                }



            }

            CellEmitter.floor(cell).burst(SacrificialParticle.FACTORY, Random.Int(1,5));


            if (Dungeon.level.flamable[cell]) {//affects terrain
                Dungeon.level.destroy(cell);
                GameScene.updateMap(cell);
            }
            if (Dungeon.level.map[cell]== Terrain.WALL||Dungeon.level.map[cell]== Terrain.EMPTY){
                if (Math.random()<0.5) {
                    Level.set(cell, Terrain.EMBERS);
                    GameScene.updateMap(cell);
                };
            }
            Heap heap = Dungeon.level.heaps.get(cell);//explodes bombs and affects heaps nearby
            if (heap != null) heap.explode();
        }
        GameScene.updateFog();
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return super.canAttack(enemy) || new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos;
    }

}