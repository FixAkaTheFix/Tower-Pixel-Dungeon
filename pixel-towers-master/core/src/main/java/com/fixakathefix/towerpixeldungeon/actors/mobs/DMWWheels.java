package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.items.LiquidMetal;
import com.fixakathefix.towerpixeldungeon.sprites.DMWWheelsSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class DMWWheels extends Mob {

    {
        spriteClass = DMWWheelsSprite.class;

        HP = HT = 200;
        defenseSkill = 5;
        baseSpeed = 1.1f;

        EXP = 0;
        maxLvl = 16;

        properties.add(Property.INORGANIC);
        properties.add(Property.LARGE);
        properties.add(Property.ELECTRIC);
        properties.add(Property.BOSS);
        loot = new LiquidMetal();
        lootChance = 0.5f;
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(20, 25);
    }

    @Override
    public int attackSkill(Char target) {
        return 10;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(5, 10);
    }

    @Override
    public void die( Object src ) {;
        CellEmitter.get(this.pos).burst(BlastParticle.FACTORY, 16);
        Sample.INSTANCE.play( Assets.Sounds.BLAST);
        super.die( src );
    }

}