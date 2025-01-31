package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.BossTrollSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class BossTroll extends Mob {

    {
        spriteClass = BossTrollSprite.class;

        HP = HT = 1200;
        defenseSkill = 6;

        viewDistance = 10;
        baseSpeed = 1.05f;

        EXP = 100;

        maxLvl = 25;

        properties.add(Property.BOSS);
    }

    protected int phase = 1;
    float attackDelayMult = 2f;
    int minDamage = 75;
    int maxDamage = 100;
    boolean enraged;

    public void setPhase(int phas) {

        //1 = normal melee
        //2 = melee rage
        if (phase == 1 ) {
            attackDelayMult = 2f;
            minDamage = 75;
            maxDamage = 100;
        }
        if (phase == 2) {
            baseSpeed = 1.5f;
            attackDelayMult = 0.9f;
            minDamage = 70;
            maxDamage = 80;
            viewDistance = 4;
            speak("ENRAGED", CharSprite.WARNING);
            Sample.INSTANCE.play(Assets.Sounds.CHALLENGE,1.5f, 0.5f);
        }
        phase = phas;
    }

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    public float attackDelay() {
        return super.attackDelay()*attackDelayMult;
    }

    @Override
    public void damage(int dmg, Object src) {
        if (!BossHealthBar.isAssigned()){
            BossHealthBar.assignBoss( this );
        }

        if ((HP*3 <= HT)){
            BossHealthBar.bleed(true);
        }
        int x = 1;
        if (HP - dmg <= 250) x = 2;
        if (phase != x) setPhase(x);
        super.damage(dmg, src);
    }


    @Override
    public int damageRoll() {
        return Random.NormalIntRange( minDamage, maxDamage);
    }

    @Override
    public int attackSkill( Char target ) {
        return 15;
    }

    @Override
    protected boolean act() {
        if (HP<HT) HP+=15;
        return super.act();
    }

    @Override
    public void die(Object cause) {
        Badges.validateEnemy(this);
        super.die(cause);
    }

    @Override
    public int drRoll() {
        return Random.NormalIntRange(5,6);//thick skin reduces damage
    }

    private static final String PHASE = "phase";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(PHASE, phase);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        phase = bundle.getInt(PHASE);
    }
}
