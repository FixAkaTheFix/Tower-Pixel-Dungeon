package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.Beam;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.PurpleParticle;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerDisintegration1Sprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.PointF;

public class TowerDisintegration1 extends TowerCShooting {
    {
        HP = HT = 55;
        spriteClass = TowerDisintegration1Sprite.class;

        attackRange = 9;
        baseAttackDelay = 4f;

        cost = 300;
        upgrade1Cost = 500;
        damageMin = 5;
        damageMax = 5;
        upgradeLevel = 3;
    }

    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 5);
        return p;
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return (Dungeon.level.distance(enemy.pos, this.pos)<=attackRange);
    }


    @Override
    protected boolean doAttack(Char enemy) {
        Ballistica rayllistica = new Ballistica(pos, enemy.pos, Ballistica.WONT_STOP);
        sprite.parent.add(new Beam.DeathRay(rayPoint(), DungeonTilemap.raisedTileCenterToWorld(rayllistica.path.get(Math.min(attackRange, rayllistica.path.size()-1)))));
        for (int rayPos : rayllistica.subPath(1,attackRange)) {
            if (rayPos>=0 && rayPos<=Dungeon.level.map.length-1) CellEmitter.center(rayPos).burst(PurpleParticle.BURST, 3);
            Char x = Char.findChar(rayPos);
            if (x!=null && x!=enemy) {
                if (x.alignment == this.alignment){
                    if (x instanceof TowerNotliving || x instanceof Arena.AmuletTower || x instanceof SubAmuletTower) {
                        //all allied towers which are not living units are not damaged. NOTE: Might add something there, maybe a tiny 1 damage
                    }
                    else x.damage(damageRoll()/5, this); // all other allies are damaged by a fifth of the base damage
                } else x.damage(damageRoll(), this);
            }
        }
        return super.doAttack(enemy);
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        return super.attackProc(enemy, damage);
    }
}
