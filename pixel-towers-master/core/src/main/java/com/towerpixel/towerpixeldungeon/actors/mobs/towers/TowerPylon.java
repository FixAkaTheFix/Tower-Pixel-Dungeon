package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Badges;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.DamageType;
import com.towerpixel.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.PylonTowerSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class TowerPylon extends TowerCShooting implements Callback {

    {
        HP = HT = 300;
        spriteClass = PylonTowerSprite.class;

        viewDistance = 8;
        upgCount = 0;

        baseAttackDelay = 1.5f;
        damageMin = 10;
        damageMax = 20;
        properties.add(Property.IMMOVABLE);
    }

    @Override
    public int attackSkill(Char target) {
        return 100;
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos||Dungeon.level.distance(enemy.pos, this.pos)<=viewDistance;
    }
    public static class LightningBolt{}
    @Override
    protected boolean doAttack( Char enemy ) {



            spend( 1f );

            Invisibility.dispel(this);
            if (hit( this, enemy, DamageType.MAGICAL )) {
                int dmg = Random.NormalIntRange(damageMin,damageMax);
                dmg = Math.round(dmg * AscensionChallenge.statModifier(this));
                enemy.damage( dmg, new LightningBolt() );

                if (enemy.sprite.visible) {
                    enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
                    enemy.sprite.flash();
                }

                if (enemy == Dungeon.hero) {

                    Camera.main.shake( 2, 0.3f );

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
    @Override
    public void call() {
        next();
    }

}