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

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Blindness;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.GorematiaSpiritSprite;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class GorematiaSpirit extends Mob {
    {
        spriteClass = GorematiaSpiritSprite.class;

        HP = HT = 500;
        defenseSkill = 7;

        EXP = 10;
        maxLvl = 666;

        baseSpeed = 1f;
        flying = true;

        properties.add(Property.UNDEAD);
        properties.add(Property.BOSS);


        HUNTING = new Hunting();
    }

    @Override
    protected boolean act() {
        voidTiles();
        return super.act();
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(18, 30);
    }

    @Override
    public int attackSkill(Char target) {
        return 13;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(5, 10);
    }

    public void abTeleport(){
        sprite.emitter().start(ShadowParticle.CURSE, 0.05f, 30);
        CellEmitter.center(pos).burst(Speck.factory(Speck.RED_LIGHT), 12);
        Buff.affect(hero, Blindness.class, 5);
        hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 20);
        this.pos = Dungeon.level.randomRespawnCell(this);

    }
    public void abPush(Char user, Char target){
            if (target != null){
                Ballistica trajectory = new Ballistica(user.pos, target.pos, Ballistica.STOP_TARGET);
                trajectory = new Ballistica(trajectory.collisionPos, trajectory.path.get(trajectory.path.size()-1), Ballistica.PROJECTILE);
                throwChar(target, trajectory, 8, false, true, getClass());
            }
    }
    public void voidTiles(){
        int cell = pos + PathFinder.NEIGHBOURS8[Random.Int(8)];
        if (Dungeon.level.map[cell] != Terrain.ENTRANCE && Dungeon.level.map[cell] != Terrain.EXIT && Dungeon.level.map[cell] != Terrain.CHASM){
            Level.set( cell, Terrain.EMPTY);
            GameScene.updateMap( cell );
        }
    }

    @Override
    public int attackProc(Char enemy, int damage) {

        damage = super.attackProc(enemy, damage);
        Wraith w = new Wraith();
        int reg = Math.min(damage - 4, HT - HP);
        if (reg > 0) {
            HP += reg;
            hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 20);
            sprite.emitter().start(ShadowParticle.CURSE, 0.05f, 30);
        }
        w.pos = Dungeon.level.randomDestination(this);
        if (w.pos != -1){
            GameScene.add(w);
        }
        w.isActive();
        w.state = WANDERING;
        int ab = Random.Int(2);
        switch (ab) {
            case 0 : {abTeleport(); break;}
            case 1: {abPush(this, enemy); break;}
           case 2: {voidTiles();break;}
        }
        return damage;
    }

    @Override
    public void die(Object cause) {
        Dungeon.level.locked = false;
        super.die(cause);
    }
}



