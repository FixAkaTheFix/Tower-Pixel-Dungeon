package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Cripple;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCannon1Sprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class TowerCannon1 extends TowerCShooting{

    {
        HP = HT = 40;
        spriteClass = TowerCannon1Sprite.class;

        attackRange = 7;
        baseAttackDelay = 2.5f;

        cost = 300;
        upgrade1Cost = 300;
        damageMin = 2;
        damageMax = 6;
        upgradeLevel = 3;

    }

    public float damageExplosionMult = 0.5f;

    @Override
    public int attackSkill(Char target) {//Nope, no dodging an explosion at its epicentre((
        return 100;
    }

    public void boom (int cell){
        if (Dungeon.level.heroFOV[this.pos] || Dungeon.level.heroFOV[cell]){
            Sample.INSTANCE.play(Assets.Sounds.BLAST);
        }
        for (int i : PathFinder.NEIGHBOURS9){
            int cell2 = cell+i;
            Char ch = Char.findChar(cell2);
            if (ch!=null && !level.cellAdjacentToBorderCells(cell2)){
                if (ch.alignment == this.alignment){
                    //friends receive 0 damage
                } else {
                    ch.damage (Math.round(damageRoll()*(ch.pos==cell ? 1 : damageExplosionMult)) - ch.drRoll(),this);
                };
            }
            if (level.heroFOV[cell2] && !level.cellAdjacentToBorderCells(cell2)) {
                CellEmitter.center(cell2).burst(BlastParticle.FACTORY, 30);
            }
            if (level.heroFOV[cell2] && !level.cellAdjacentToBorderCells(cell2)) {
                CellEmitter.center(cell2).start(SmokeParticle.FACTORY, 0.05f, 2);
                CellEmitter.floor(cell2).start(SmokeParticle.FACTORY, 0.12f, 3);
            }
            if (level.flamable[cell2] && !level.cellAdjacentToBorderCells(cell)) {//affects terrain
                level.destroy(cell2);
                GameScene.updateMap(cell2);
            }
        }
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        boom(enemy.pos);
        return true;
    }


    @Override
    protected boolean canAttack( Char enemy ) {
        if (buff(Animated.class)!=null && (Dungeon.level.distance( pos, Dungeon.hero.pos )>2 || Dungeon.level.adjacent( pos, enemy.pos ))) return false;
        return (new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && level.distance(enemy.pos, this.pos)<=attackRange);
    }
}
