package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCannonNukeSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class TowerCannonNuke extends TowerCShooting{

    {
        HP = HT = 400;
        spriteClass = TowerCannonNukeSprite.class;

        attackRange = 12;//DPT =32.5*0.66 = 21.45;DPT/C=21,45:1150=0.0187
        baseAttackDelay = 25f;

        upgCount = 0;

        cost = 2800;
        upgrade1Cost = 1500;
        damageMin = 20;
        damageMax = 35;

    }
    public float damageExplosionMult = 1f;

    @Override
    public int attackSkill(Char target) {//Nope, no dodging an explosion at its epicentre((
        return 1000000;
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
        if (buff(Animated.class)!=null && (Dungeon.level.distance( pos, Dungeon.hero.pos )>2)) return false;
        return (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && level.distance(enemy.pos, this.pos)<=attackRange);
    }

}