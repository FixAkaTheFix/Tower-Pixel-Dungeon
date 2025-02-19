package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.watabou.utils.Bundle;

public class SentientTower extends Tower {


    {
        alignment = Char.Alignment.ALLY;
        intelligentAlly = true;
        WANDERING = new SentientTower.Wandering();
        HUNTING = new SentientTower.Hunting();
        state = WANDERING;
        actPriority = MOB_PRIO + 1;

    }

    protected boolean justSpawned = true;

    protected boolean attacksAutomatically = true;

    protected int defendingPos;
    protected boolean movingToDefendPos = false;


    public void defendPos( int cell ){
        aggro(null);
        state = WANDERING;
        defendingPos = cell;
        movingToDefendPos = true;
    }

    public void clearDefensingPos(){
        defendingPos = -1;
        movingToDefendPos = false;
    }

    public void followHero(){
        aggro(null);
        state = WANDERING;
        defendingPos = -1;
        movingToDefendPos = false;
    }

    public void targetChar( Char ch ){
        aggro(ch);
        target = ch.pos;
        movingToDefendPos = false;
    }

    @Override
    public void aggro(Char ch) {
        enemy = ch;
        if (!movingToDefendPos && state != PASSIVE){
            state = HUNTING;
        }
    }

    public void directTocell( int cell ){
        if (!Dungeon.level.heroFOV[cell]
                || Actor.findChar(cell) == null
                || (Actor.findChar(cell) != Dungeon.hero && Actor.findChar(cell).alignment != Char.Alignment.ENEMY)){
            defendPos( cell );
            return;
        }

        if (Actor.findChar(cell) == Dungeon.hero){
            followHero();

        } else if (Actor.findChar(cell).alignment == Char.Alignment.ENEMY){
            targetChar(Actor.findChar(cell));

        }
    }

    private static final String DEFEND_POS = "defend_pos";
    private static final String MOVING_TO_DEFEND = "moving_to_defend";
    private static final String JUSTSPAWNED = "justspawned";

    public void justSpawnedLogic(){
        defendingPos = this.pos;

    }

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(DEFEND_POS, defendingPos);
        bundle.put(MOVING_TO_DEFEND, movingToDefendPos);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        if (bundle.contains(DEFEND_POS)) defendingPos = bundle.getInt(DEFEND_POS);
        movingToDefendPos = bundle.getBoolean(MOVING_TO_DEFEND);
    }

    private class Wandering extends Mob.Wandering {

        @Override
        public boolean act( boolean enemyInFOV, boolean justAlerted ) {
            if (justSpawned) {
                justSpawnedLogic();
                justSpawned = false;
            }
            if ( enemyInFOV
                    && attacksAutomatically
                    && !movingToDefendPos
                    && (defendingPos == -1 || !Dungeon.level.heroFOV[defendingPos] || canAttack(enemy))) {

                enemySeen = true;

                notice();
                alerted = true;
                state = HUNTING;
                target = enemy.pos;

            } else {

                enemySeen = false;
                beckon(defendingPos);

                int oldPos = pos;
                target = defendingPos != -1 ? defendingPos : Dungeon.hero.pos;
                //always move towards the hero when wandering
                if (getCloser( target )) {
                    spend( 1 / speed() );
                    if (pos == defendingPos) movingToDefendPos = false;
                    return moveSprite( oldPos, pos );
                } else {
                    spend( TICK );
                }

            }
            return true;
        }

    }

    private class Hunting extends Mob.Hunting {

        @Override
        public boolean act(boolean enemyInFOV, boolean justAlerted) {
            if (enemyInFOV && defendingPos != -1 && Dungeon.level.heroFOV[defendingPos] && !canAttack(enemy)){
                target = defendingPos;
                state = WANDERING;
                return true;
            }
            return super.act(enemyInFOV, justAlerted);
        }

    }
}
