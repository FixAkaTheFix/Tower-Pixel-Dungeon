package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Lightning;
import com.fixakathefix.towerpixeldungeon.effects.particles.SparkParticle;
import com.fixakathefix.towerpixeldungeon.sprites.TowerWandLightningSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class TowerWandLightning extends TowerWand3 {

    {
        HP = HT = 80;
        spriteClass = TowerWandLightningSprite.class;

        attackRange = 7;//DPT = 40 * 0.7= 28 DPT/C = 28/2350 = 0,012;
        upgCount = 0;

        baseAttackDelay = 1.7f;

        cost = 2000;
        upgrade1Cost = 1000;
        damageMin = 12;
        damageMax = 32;
    }

    @Override
    public int attackSkill(Char target) {
        return 100;
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
        this.sprite.parent.addToFront( new Lightning( arcs, null ) );
        Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );

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
                else if (n != null && !affected.contains( n )) {
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