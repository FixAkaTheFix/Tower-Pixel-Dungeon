package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.Random;

public class TowerGuard1 extends SentientTower {

    {

        HP = HT = 75;
        spriteClass = TowerGuard1Sprite.class;

        viewDistance = 6;
        baseAttackDelay = 1f;

        cost = 150;
        damageMin = 3;//dpt/c = 0.00267
        damageMax = 5;
        upgradeLevel = 3;
        defMin = 0;
        defMax = 4;
        upgrade1Cost = 300;
        defenseSkill = 5;

        defendingPos = -1;
    }

    public int regenNum = 6;
    public int turnsUntilRegen = 0;

    public TowerGuard1(){
        super();
    }
    private static final String TURNSTOREGEN = "turns_to_regen";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(TURNSTOREGEN, turnsUntilRegen);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        turnsUntilRegen = bundle.getInt(TURNSTOREGEN);
    }

    @Override
    public void defendPos(int cell) {
        super.defendPos(cell);
        speak(Messages.get(TowerGuard1.class, "defending" + Integer.toString(Random.Int(1,3))), CharSprite.WARNING);
    }

    @Override
    public void targetChar(Char ch) {
        super.targetChar(ch);
        speak(Messages.get(TowerGuard1.class, "attacking" + Integer.toString(Random.Int(1,3))), CharSprite.WARNING);
    }

    @Override
    public void followHero() {
        super.followHero();
        speak(Messages.get(TowerGuard1.class, "follow" + Integer.toString(Random.Int(1,3))), CharSprite.WARNING);
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HP, HT , damageMin, damageMax, defMin, defMax, regenNum ));
        info.append(Messages.get(this, "descstats"));
        if (DeviceCompat.isDebug()){
            if (enemy== null) info.append("\n\nHas no enemy"); else info.append("\n\nEnemy is " + enemy.getClass().getSimpleName());
            info.append("\nState is: " + state.getClass().getSimpleName());
            info.append("\nIs moving to defendpos: " + movingToDefendPos);
            info.append("\nHas a defpos: " + (defendingPos != -1));
            info.append("\nFollowing hero: " + followingHero);
        }
        return info.toString();
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        turnsUntilRegen = 10;
        return super.defenseProc(enemy, damage);
    }

    @Override
    protected boolean act() {
        if (enemy == null){
            if (turnsUntilRegen>0) turnsUntilRegen--;
        } else turnsUntilRegen = 10;
        if (Dungeon.hero.buff(WaveCooldownBuff.class) != null && turnsUntilRegen == 0){
            if (HP + regenNum <= HT) HP+=regenNum;
            else if (HT < HP + regenNum && HT>HP) HP = HT;
        }
        return super.act();
    }

    @Override
    public int attackSkill(Char target) {
        return 11;
    }
}
