package com.fixakathefix.towerpixeldungeon.items.weapon.melee;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bleeding;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Weakness;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.ui.AttackIndicator;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Infipike extends MeleeWeapon {

    {
        image = ItemSpriteSheet.INFIPIKE;
        hitSound = Assets.Sounds.HIT_STAB;
        hitSoundPitch = 0.8f;
        rarity = 4;


        tier = 5;
        DLY = 1f;
        RCH = 100;    //Practically infinite reach
    }

    @Override
    public int max(int lvl) {
        return  Math.round(10f*damageModifier()) +    //20 base, up from 15
                4*lvl*Math.round(1f*damageModifier()); //+4 per level, up from +3
    }

    @Override
    public String targetingPrompt() {
        return Messages.get(this, "prompt");
    }

    @Override
    protected void duelistAbility(Hero hero, Integer target) {
        com.fixakathefix.towerpixeldungeon.items.weapon.melee.Infipike.phaseIncineration(hero, target, 0f, this);
    }
    public int proc(Char attacker, Char defender, int damage) {
        defender.sprite.centerEmitter().start( Speck.factory( Speck.BONE), 0.05f, 5 );
        DLY = (float)Math.sqrt(Dungeon.level.distance(attacker.pos, defender.pos));
        Buff.affect( defender, Bleeding.class ).set( Math.round(60+ level()*15) );
        return super.proc(attacker, defender, damage);
    }

    public static void phaseIncineration(Hero hero, Integer target, float dmgMulti, MeleeWeapon wep){
        if (target == null) {
            return;
        }

        Char enemy = Actor.findChar(target);
        if (enemy == null || enemy == hero || hero.isCharmedBy(enemy) || !Dungeon.level.heroFOV[target]) {
            GLog.w(Messages.get(wep, "ability_no_target"));
            return;
        }

        hero.belongings.abilityWeapon = wep;
        if (!hero.canAttack(enemy)){
            GLog.w(Messages.get(wep, "ability_bad_position"));
            hero.belongings.abilityWeapon = null;
            return;
        }
        hero.belongings.abilityWeapon = null;

        hero.sprite.attack(enemy.pos, new Callback() {
            @Override
            public void call() {
                wep.beforeAbilityUsed(hero);
                AttackIndicator.target(enemy);
                if (hero.attack(enemy, dmgMulti, 0, 1f)) {
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STAB);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STAB);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_SLASH);
                    Sample.INSTANCE.play(Assets.Sounds.HIT_STAB);
                    if (enemy.isAlive()){
                        Buff.affect( enemy, Bleeding.class ).set( Math.round(80 + 3*Dungeon.depth));
                        Buff.affect(enemy, Weakness.class, 5f);
                        Buff.affect(enemy, Blindness.class, 5f);
                    } else {
                        wep.onAbilityKill(hero);
                    }
                }
                Invisibility.dispel();
                hero.spendAndNext(hero.attackDelay());
                wep.afterAbilityUsed(hero);
            }
        });
    }

}
