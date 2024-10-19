package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;
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
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.TowerCannonNukeSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TowerCannonNuke extends TowerCShooting{

    {
        HP = HT = 100;
        spriteClass = TowerCannonNukeSprite.class;

        attackRange = 8;//DPT =32.5*0.66 = 21.45;DPT/C=21,45:1150=0.0187
        baseAttackDelay = 25f;

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

    /*@Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        int attackDelayShow = Math.round(100/baseAttackDelay);
        int ac = Math.round(attackSkill(hero)*10);
        String attackSkillShow = ac + "%";
        if (ac >= 10000) attackSkillShow = Messages.get(this.getClass(),"nevermiss");
        info.append(Messages.get(this, "stats", HT , damageMin, damageMax, attackSkillShow, attackDelayShow, attackRange));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }*/

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {

        int cell;
        //CellEmitter.center(enemy.pos).burst(SmokeParticle.FACTORY, 30);


        for (int i : PathFinder.NEIGHBOURS25) for (int i2 : PathFinder.NEIGHBOURS4){

            cell = enemy.pos + i + i2;
            if (cell > 0 && cell < level.width()*level.height()){
            Char ch = Actor.findChar(cell);
            if (ch!=null){
                if (ch.alignment == Alignment.ALLY){
                } else if (Actor.findChar(enemy.pos)==ch) {//to not double-damage the foe
                } else ch.damage (Math.round(damageRoll()*damageExplosionMult) - enemy.drRoll(),this);//damages foes nearby, with lowered damage


                if (ch.pos == cell && ch.alignment != this.alignment) {
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i + i2, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, 5, false, true, getClass());
                }
            }

            CellEmitter.floor(cell).burst(SacrificialParticle.FACTORY, Random.Int(1,3));


            if (Dungeon.level.flamable[cell]) {//affects terrain
                Dungeon.level.destroy(cell);
                GameScene.updateMap(cell);
            }
            if (Dungeon.level.map[cell]== Terrain.EMPTY){
                if (Math.random()<0.5) {
                    Level.set(cell, Terrain.EMBERS);
                    GameScene.updateMap(cell);
                };
            }
            Heap heap = Dungeon.level.heaps.get(cell);//explodes bombs and affects heaps nearby
            if (heap != null) heap.explode();
            }
        }
        GameScene.updateFog();
        return super.attack(enemy, dmgMulti, dmgBonus, accMulti);
    }

    @Override
    protected boolean canAttack( Char enemy ) {
        return (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && level.distance(enemy.pos, this.pos)<=attackRange);
    }

}