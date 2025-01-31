package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Amok;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Dread;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Sleep;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Terror;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Pushing;
import com.fixakathefix.towerpixeldungeon.effects.particles.SmokeParticle;
import com.fixakathefix.towerpixeldungeon.items.LiquidMetal;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.DMWBodySprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DMWBody extends Mob {

    {
        spriteClass = DMWBodySprite.class;

        HP = HT = 300;
        defenseSkill = 0;

        EXP = 0;

        state = PASSIVE;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.BOSS);
        properties.add(Property.INORGANIC);
        properties.add(Property.ELECTRIC);

        loot = new LiquidMetal();
        lootChance = 0.5f;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(3, 12);
    }

    @Override
    public void beckon(int cell) {
        //do nothing
    }

    @Override
    public boolean reset() {
        return true;
    }

    private float spawnCooldown = 20;

    public boolean spawnRecorded = false;

    @Override
    protected boolean act() {
        spawnCooldown--;
        if (spawnCooldown <= 0){

            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos+n] && Actor.findChar( pos+n ) == null) {
                    candidates.add( pos+n );
                }
            }

            if (!candidates.isEmpty()) {
                DMWMinion spawn = new DMWMinion();

                speak ("DEPLOYING", CharSprite.WARNING);
                spawn.alignment = this.alignment;

                spawn.pos = Random.element( candidates );
                spawn.state = spawn.HUNTING;

                GameScene.add( spawn, 1 );
                Dungeon.level.occupyCell(spawn);

                if (sprite.visible) {
                    Actor.addDelayed(new Pushing(spawn, pos, spawn.pos), -1);
                }
                spawnCooldown += 20;
            }
        }
        alerted = false;
        state = PASSIVE;
        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {
        if (dmg >= 30){
            dmg = 29 + (int)(Math.sqrt(8*(dmg - 29) + 1) - 1)/2;
            spawnCooldown -= dmg;
        }
        super.damage(dmg, src);
    }
    public static final String SPAWN_COOLDOWN = "spawn_cooldown";
    public static final String SPAWN_RECORDED = "spawn_recorded";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SPAWN_COOLDOWN, spawnCooldown);
        bundle.put(SPAWN_RECORDED, spawnRecorded);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        spawnCooldown = bundle.getFloat(SPAWN_COOLDOWN);
        spawnRecorded = bundle.getBoolean(SPAWN_RECORDED);
    }

    {
        immunities.add( Paralysis.class );
        immunities.add( Amok.class );
        immunities.add( Sleep.class );
        immunities.add( Dread.class );
        immunities.add( Terror.class );
        immunities.add( Vertigo.class );
    }
    @Override
    public void die( Object src ) {;
        CellEmitter.get(this.pos).burst(SmokeParticle.FACTORY, 4);
        Sample.INSTANCE.play( Assets.Sounds.BLAST);
        super.die( src );
    }
}
