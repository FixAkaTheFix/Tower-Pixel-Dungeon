/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.AllyBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.BeeSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

//Freebees do not
// have a pot,
//but stay at their spot
//the game crash will not,
// probably
public class FreeBee extends Mob {

    {
        HP = HT = (2 + Dungeon.depth) * 2;
        defenseSkill = 8;

        spriteClass = BeeSprite.class;

        viewDistance = 4;

        EXP = 0;

        flying = true;
        state = WANDERING;

        //only applicable when the bee is charmed with elixir of honeyed healing
        intelligentAlly = true;
    }


    //-1 refers to a pot that has gone missing.
    private int guardPos;
    public boolean guardposset = false;
    //-1 for no owner
    private int potHolder;

    private static final String LEVEL	    = "level";
    private static final String GUARDPOS = "potpos";
    private static final String ALIGMNENT   = "alignment";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put(GUARDPOS, guardPos);
        bundle.put( ALIGMNENT, alignment);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        guardPos = bundle.getInt(GUARDPOS);
        if (bundle.contains(ALIGMNENT)) alignment = bundle.getEnum( ALIGMNENT, Alignment.class);
    }

    @Override
    protected boolean act() {
        if (!guardposset){ guardposset = true; guardPos=this.pos;}
        return super.act();
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 1, HT / 3);
    }

    @Override
    public int attackProc( Char enemy, int damage ) {

        damage = super.attackProc( enemy, damage );
        if (enemy instanceof Mob) {
            ((Mob)enemy).aggro( this );
        }
        return damage;
    }

    @Override
    public boolean add(Buff buff) {
        if (super.add(buff)) {
            //TODO maybe handle honeyed bees with their own ally buff?
            if (buff instanceof AllyBuff) {
                intelligentAlly = false;;
            }
            return true;
        }
        return false;
    }

    @Override
    protected Char chooseEnemy() {
        if (alignment == Alignment.ALLY || guardPos == -1){
            return super.chooseEnemy();
        } else {

            //try to find a new enemy in these circumstances
            if (enemy == null || !enemy.isAlive() || !Actor.chars().contains(enemy) || state == WANDERING
                    || Dungeon.level.distance(enemy.pos, guardPos) > 3
                    || (alignment == Alignment.ALLY && enemy.alignment == Alignment.ALLY)
                    || (buff( Amok.class ) == null && enemy.isInvulnerable(getClass()))){

                //find all mobs near the guarded spot
                HashSet<Char> enemies = new HashSet<>();
                for (Mob mob : Dungeon.level.mobs) {
                    if (!(mob == this)
                            && Dungeon.level.distance(mob.pos, guardPos) <= 3
                            && mob.alignment != Alignment.NEUTRAL
                            && !mob.isInvulnerable(getClass())
                            && !(alignment == Alignment.ALLY && mob.alignment == Alignment.ALLY)) {
                        enemies.add(mob);
                    }
                }

                if (!enemies.isEmpty()){
                    return Random.element(enemies);
                } else {
                    if (alignment != Alignment.ALLY && Dungeon.level.distance(Dungeon.hero.pos, guardPos) <= 3){
                        return Dungeon.hero;
                    } else {
                        return null;
                    }
                }

            } else {
                return enemy;
            }
        }
    }

    @Override
    protected boolean getCloser(int target) {
        if (alignment == Alignment.ALLY && enemy == null && buffs(AllyBuff.class).isEmpty()){
            target = Dungeon.hero.pos;
        } else if (enemy != null && Actor.findById(potHolder) == enemy) {
            target = enemy.pos;
        } else if (guardPos != -1 && (state == WANDERING || Dungeon.level.distance(target, guardPos) > 3))
            this.target = target = guardPos;
        return super.getCloser( target );
    }

    @Override
    public String description() {
        if (alignment == Alignment.ALLY && buffs(AllyBuff.class).isEmpty()){
            return Messages.get(this, "desc_honey");
        } else {
            return super.description();
        }
    }
}