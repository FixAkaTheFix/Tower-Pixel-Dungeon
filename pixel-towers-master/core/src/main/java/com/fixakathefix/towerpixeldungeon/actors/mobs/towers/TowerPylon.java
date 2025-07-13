package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.DamageType;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.effects.particles.SparkParticle;
import com.fixakathefix.towerpixeldungeon.levels.endlessarenas.EndlessArena5;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.PylonTowerSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class TowerPylon extends TowerCShooting implements Callback {

    {
        HP = HT = 300;
        spriteClass = PylonTowerSprite.class;

        attackRange = 8;
        upgCount = 0;

        baseAttackDelay = 1.5f;//dpt/c =
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
        if (buff(Animated.class)!=null && (Dungeon.level.distance( pos, Dungeon.hero.pos )>1 || Dungeon.level.adjacent( pos, enemy.pos ))) return false;
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos&&Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
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

    @Override
    public void die(Object cause) {
        if (Dungeon.level instanceof EndlessArena5){
            TowerPylonBroken tow = new TowerPylonBroken();
            tow.pos = this.pos;
            GameScene.add(tow);
        }
        super.die(cause);
    }
}