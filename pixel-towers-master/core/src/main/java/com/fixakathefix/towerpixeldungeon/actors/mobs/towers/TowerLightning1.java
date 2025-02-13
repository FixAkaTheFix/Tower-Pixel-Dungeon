package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Lightning;
import com.fixakathefix.towerpixeldungeon.effects.particles.SparkParticle;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.TowerLightning1Sprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class TowerLightning1 extends TowerCShooting {
    {
        HP = HT = 50;
        spriteClass = TowerLightning1Sprite.class;

        attackRange = 3;//DPT/c = 0.0125
        baseAttackDelay = 5f;

        cost = 200;
        upgrade1Cost = 400;
        damageMin = 7;
        damageMax = 18;
        upgradeLevel = 3;
    }
    public int maxChainLength = 2;

    @Override
    protected boolean canAttack( Char enemy ) {
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }


    private boolean damageIsSpreaded = false;
    private float currentMultiplier = 1f;



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
        if (Dungeon.level.water[enemy.pos]) multiplier *= 1.1f;
        if (Dungeon.level.heroFOV[this.pos] || Dungeon.level.heroFOV[enemy.pos]){
            this.sprite.parent.addToFront( new Lightning( arcs, null ) );
            Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
        }
        currentMultiplier = multiplier;
        damageIsSpreaded = true;
        for (Char char2 : affected){
            if (char2 == Dungeon.hero) Camera.main.shake( 2, 0.3f );
            char2.sprite.centerEmitter().burst( SparkParticle.FACTORY, 3 );
            char2.sprite.flash();

            if (char2.alignment == this.alignment){
                continue;
            }
            char2.damage(Math.round(damageRoll()), this);
        }


        return super.attack(enemy,dmgMulti,dmgBonus,accMulti);
    }


    @Override
    public void onAttackComplete() {
        super.onAttackComplete();
        damageIsSpreaded = false;
    }

    @Override
    public int damageRoll() {
        if (damageIsSpreaded) return (int)(super.damageRoll()*currentMultiplier);
        return super.damageRoll();
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
