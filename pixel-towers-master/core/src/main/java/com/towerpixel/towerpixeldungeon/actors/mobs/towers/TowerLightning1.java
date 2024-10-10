package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Lightning;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerLightning1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerWand1Sprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class TowerLightning1 extends TowerCShooting {
    {
        HP = HT = 40;
        spriteClass = TowerLightning1Sprite.class;

        attackRange = 3;//DPT =5.5*0.6 = 3.3 DPT/C = 3.3/100 = 0,033
        baseAttackDelay = 4f;

        cost = 200;
        upgrade1Cost = 400;
        damageMin = 9;
        damageMax = 22;
        upgradeLevel = 3;
    }
    public int maxChainLength = 4;

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        affected.clear();
        arcs.clear();

        int cell = enemy.pos;

        Char char1 = Actor.findChar( cell );
        if (char1 != null) {
            affected.add( char1 );
            arcs.add( new Lightning.Arc(this.sprite.center(), char1.sprite.center()));
            arc(char1);
        } else {
            arcs.add( new Lightning.Arc(this.sprite.center(), DungeonTilemap.raisedTileCenterToWorld(enemy.pos)));
            CellEmitter.center( cell ).burst( SparkParticle.FACTORY, 3 );
        }

        //Formulas from lightningwand
        float multiplier = 0.1f + (0.9f/affected.size());
        if (Dungeon.level.water[enemy.pos]) multiplier *= 1.5f;
        if (Dungeon.level.heroFOV[this.pos] || Dungeon.level.heroFOV[enemy.pos]){
            this.sprite.parent.addToFront( new Lightning( arcs, null ) );
            Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        }


        for (Char char2 : affected){
            if (char2 == Dungeon.hero) Camera.main.shake( 2, 0.3f );
            char2.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
            char2.sprite.flash();

            if (char2 != this && char2.alignment == this.alignment && char2.pos != enemy.pos){
                continue;
            }
            if ((char2 == this || char2.alignment==this.alignment) && char2.isAlive()) {
                char2.damage(Math.round(damageRoll() * multiplier * 0.2f), this);
            } else {
                char2.damage(Math.round(damageRoll() * multiplier), this);
            }
        }


        return super.attack(enemy,dmgMulti,dmgBonus,accMulti);
    }

    @Override
    protected Char chooseEnemy() {
        return super.chooseEnemy();
    }

    private ArrayList<Char> affected = new ArrayList<>();

    private ArrayList<Lightning.Arc> arcs = new ArrayList<>();

    private void arc( Char ch ) {
        int dist = (Dungeon.level.water[ch.pos] && !ch.flying) ? 2 : 1;

        ArrayList<Char> hitThisArc = new ArrayList<>();
        PathFinder.buildDistanceMap( ch.pos, BArray.not( Dungeon.level.solid, null ), dist );
        for (int i = 0; i < PathFinder.distance.length; i++) {
            if (PathFinder.distance[i] < Integer.MAX_VALUE){
                Char n = Actor.findChar( i );
                if (n == Dungeon.hero && PathFinder.distance[i] > 1)
                    continue;
                else if (n != null && !affected.contains( n ) && hitThisArc.size()<maxChainLength) {
                    hitThisArc.add(n);
                }
            }
        }

        affected.addAll(hitThisArc);
        for (Char hit : hitThisArc){
            arcs.add(new Lightning.Arc(ch.sprite.center(), hit.sprite.center()));
            if (arcs.size()<4) arc(hit);
        }
    }
}
