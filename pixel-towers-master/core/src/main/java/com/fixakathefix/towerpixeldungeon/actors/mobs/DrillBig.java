/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
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

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.Dungeon.updateLevelExplored;
import static com.fixakathefix.towerpixeldungeon.scenes.GameScene.updateFog;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveBuff;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.journal.Bestiary;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.DrillBigSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class DrillBig extends Mob {

    {
        spriteClass = DrillBigSprite.class;
        state = WANDERING;
        HP = HT = 8055847;
        defenseSkill = 0;
        alignment = Alignment.NEUTRAL;
        viewDistance = 10;
        properties.add(Property.IMMOVABLE);
        EXP = 2;
        maxLvl = 10;
    }
    public int counter = 0;
    public int stepcount = 0;
    public boolean active = false;

    @Override
    public boolean canInteract(Char c) {
        return false;
    }

    @Override
    public void damage(int dmg, Object src) {
    }

    @Override
    protected Char chooseEnemy() {
        return null;
    }

    @Override
    public boolean attack(Char enemy, float dmgMulti, float dmgBonus, float accMulti) {
        return false;
    }

    public void moveForward() {
        int v = pos;
        Bestiary.setSeen(getClass());

        for (int x = v % Dungeon.level.width()+5; x>v % Dungeon.level.width()-1 ;x--) for (int y = v / Dungeon.level.width()+4; y>v / Dungeon.level.width()-1 ;y--){
            if (Char.findChar(x+Dungeon.level.width()*y)!= null && Char.findChar(x+Dungeon.level.width()*y)!= this){
                Char ch = Char.findChar(x+Dungeon.level.width()*y);
                ch.pos++;
                ch.sprite.move(x+Dungeon.level.width()*y, x+Dungeon.level.width()*y+1);
                Dungeon.level.occupyCell(ch);
            }
        }

        this.sprite.move(pos,pos+1);
        pos++;
        Dungeon.level.occupyCell(this);
    }

    @Override
    public boolean isImmune(Class effect) {
        return true;
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        return 0;
    }


    @Override
    public void die(Object cause) {
        hero.die(this);
        //no death
    }

    @Override
    protected boolean act() {
        spend(1);
        if (hero.buff(WaveBuff.class)!=null) active = true;
        int[] ints = new int[]{

                pos+6,
                pos+6+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),


                pos+7,
                pos+7+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                pos+8+Dungeon.level.width(),
                pos+8+Dungeon.level.width()+Dungeon.level.width(),
                pos+8+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                pos+9+Dungeon.level.width()+Dungeon.level.width(),
        };
        for (int i : ints) {
            if (Char.findChar(i)!= null){
                Char.findChar(i).damage(20, this);
                Sample.INSTANCE.play(Assets.Sounds.HIT_STAB);
                if (Char.findChar(i)!= null) WandOfBlastWave.throwChar(Char.findChar(i), Random.oneOf(new Ballistica(i, i+level.width()*6+3, Ballistica.PROJECTILE), new Ballistica(i, i-level.width()*6+3, Ballistica.PROJECTILE)) , 6,false,true, Drill.class);
            }
        }
        int[] ints2 = new int[]{
                pos+6-Dungeon.level.width(),
                pos+6,
                pos+6+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                pos+6+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+ level.width(),

                pos+7,
                pos+7+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),
                pos+7+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                pos+8+Dungeon.level.width(),
                pos+8+Dungeon.level.width()+Dungeon.level.width(),
                pos+8+Dungeon.level.width()+Dungeon.level.width()+Dungeon.level.width(),

                pos+9+Dungeon.level.width()+Dungeon.level.width(),
        };
        for (int i : ints2) {
            if (level.map[i]!=Terrain.EMPTY&&level.map[i]!=Terrain.EMPTY_SP&&level.map[i]!=Terrain.EMBERS) {
                if (level.map[i]==Terrain.GRASS||level.map[i]==Terrain.HIGH_GRASS||level.map[i]==Terrain.FURROWED_GRASS){
                    CellEmitter.center(i).burst(BlastParticle.FACTORY, 2);
                    Sample.INSTANCE.play(Assets.Sounds.TRAMPLE);
                }
                else {
                    CellEmitter.center(i).burst(BlastParticle.FACTORY, 100);
                    Sample.INSTANCE.play(Assets.Sounds.BLAST);
                }

                Level.set(i, Terrain.EMBERS);
                level.buildFlagMaps();
                level.cleanWalls();
                GameScene.updateMap(i);
                GameScene.updateFog();
                GameScene.updateMap();
                updateLevelExplored();

            }
        }
        counter++;
        if (counter>=2 && active){
            moveForward();
            counter=0;
        }
        return true;
    }
    protected String COUNTER = "counter";
    protected String STEPCOUNT = "stepcount";
    protected String ACTIVE = "active";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(STEPCOUNT, stepcount);
        bundle.put(ACTIVE, active);
        bundle.put(COUNTER, counter);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        stepcount = bundle.getInt(STEPCOUNT);
        active = bundle.getBoolean(ACTIVE);
        counter = bundle.getInt(COUNTER);
    }
}