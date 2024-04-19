package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.sprites.DMWMinionSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DMWMinion extends Mob {

    {
        spriteClass = DMWMinionSprite.class;

        HP = HT = 15;
        defenseSkill = 100;

        EXP = 1;
        maxLvl = 20;
        properties.add(Property.ELECTRIC);
        properties.add(Property.INORGANIC);
        properties.add(Property.BOSS_MINION);

        targetingPreference = TargetingPreference.NOT_HERO;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 60, 80 );
    }
    @Override
    public int attackProc( Char enemy, int damage ) {
        super.die("zap_kill");
        CellEmitter.center(this.pos).burst(BlastParticle.FACTORY, 30);
        CellEmitter.get(this.pos).burst(SmokeParticle.FACTORY, 4);
        Sample.INSTANCE.play( Assets.Sounds.BLAST);
        return damage;
    }

    @Override
    public int attackSkill( Char target ) {
        return 10;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(5, 6);
    }
}

