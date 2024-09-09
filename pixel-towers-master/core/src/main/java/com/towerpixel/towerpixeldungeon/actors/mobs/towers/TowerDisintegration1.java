package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.effects.Beam;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.PoisonParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.PurpleParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerDisintegration1Sprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.utils.PointF;

public class TowerDisintegration1 extends TowerCShooting {
    {
        HP = HT = 50;
        spriteClass = TowerDisintegration1Sprite.class;

        viewDistance = 5;
        baseAttackDelay = 1.6f;

        cost = 300;
        upgrade1Cost = 500;
        damageMin = 3;
        damageMax = 3;
        upgradeLevel = 3;
    }

    protected PointF rayPoint(){
        PointF p = new PointF( sprite.x + sprite.width() / 2, sprite.y + 5);
        return p;
    }

    @Override
    public int attackSkill(Char target) {
        return 1000;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return (new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos||Dungeon.level.distance(enemy.pos, this.pos)<=viewDistance);
    }


    @Override
    protected boolean doAttack(Char enemy) {
        Ballistica rayllistica = new Ballistica(pos, enemy.pos, Ballistica.WONT_STOP);
        sprite.parent.add(new Beam.DeathRay(rayPoint(), DungeonTilemap.raisedTileCenterToWorld(rayllistica.path.get(Math.min(viewDistance, rayllistica.path.size()-1)))));
        for (int rayPos : rayllistica.subPath(1,viewDistance)) {
            if (rayPos>=0 && rayPos<=Dungeon.level.map.length-1) CellEmitter.center(rayPos).burst(PurpleParticle.BURST, 3);
            Char x = Char.findChar(rayPos);
            if (x!=null && x!=enemy) {
                if (x instanceof TowerDisintegration1){
                    //x.damage(ZERO, they don't damage other diswands)
                } else if (x.alignment == this.alignment){
                    x.damage(damageRoll()/2, this);
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
