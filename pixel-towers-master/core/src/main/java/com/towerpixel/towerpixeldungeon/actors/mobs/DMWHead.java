package com.towerpixel.towerpixeldungeon.actors.mobs;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.DamageType;
import com.towerpixel.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.DMWHeadSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class DMWHead extends Mob implements Callback {

    private static final float ZAPTIME	= 0.3f;

    {
        spriteClass = DMWHeadSprite.class;

        HP = HT = 300;
        defenseSkill = 8;

        baseSpeed = 1.1f;

        viewDistance = 5;

        EXP = 0;

        lootChance = 0f;

        properties.add(Property.BOSS);
        properties.add(Property.ELECTRIC);
        properties.add(Property.INORGANIC);
    }

    @Override
    protected boolean getCloser( int target ) {
        if (state == HUNTING) {
            return enemySeen && getFurther( target );
        } else {
            return super.getCloser( target );
        }
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange( 10, 12 );
    }

    @Override
    public int attackSkill( Char target ) {
        return 21;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 3);
    }


    @Override
    protected boolean canAttack( Char enemy ) {
        return !Dungeon.level.adjacent( pos, enemy.pos )
                && (super.canAttack(enemy) || new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos);
    }
    public static class LightningBolt{}

    @Override
    protected boolean doAttack( Char enemy ) {

        if (Dungeon.level.adjacent( pos, enemy.pos )
                || new Ballistica( pos, enemy.pos, Ballistica.MAGIC_BOLT).collisionPos != enemy.pos) {

            return super.doAttack( enemy );

        } else {

            spend( ZAPTIME );

            Invisibility.dispel(this);
            if (hit( this, enemy, DamageType.PHYSICAL )) {
                int dmg = Random.NormalIntRange(2, 6);
                dmg = Math.round(dmg * AscensionChallenge.statModifier(this));
                enemy.damage( dmg, new LightningBolt() );

                if (enemy.sprite.visible) {
                    enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
                    enemy.sprite.flash();
                }

                if (enemy == Dungeon.hero) {

                    Camera.main.shake( 40, 0.5f );

                    if (!enemy.isAlive()) {
                        Badges.validateDeathFromEnemyMagic();
                        Dungeon.fail( getClass() );
                        GLog.n( Messages.get(this, "zap_kill") );
                    }
                }
            } else {
                enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
            }

            if (sprite != null && (sprite.visible || enemy.sprite.visible)) {
                sprite.zap( enemy.pos );
                return false;
            } else {
                return true;
            }
        }
    }
    public void die( Object src ) {;
        CellEmitter.get(this.pos).burst(SmokeParticle.FACTORY, 4);
        Sample.INSTANCE.play( Assets.Sounds.BLAST);
        super.die( src );
    }

    @Override
    public void call() {
        next();
    }

}