package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.SoulBleeding;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.sprites.ObeliskBloodstoneSprite;
import com.watabou.noosa.audio.Sample;

public class ObeliskBloodstone extends Obelisk{
    {
        HP = HT = 15;
        spriteClass = ObeliskBloodstoneSprite.class;

        attackRange = 10;
        baseAttackDelay = 2f;

        cost = 0;
        upgrade1Cost = 0;
        damageMin = 1;
        damageMax = 2;
        upgCount = 0;
        sellable = false;
        if (Dungeon.hero != null){
            HP = HT = Dungeon.depth * 6 + 15;
        }
    }

    @Override
    public int attackSkill(Char target) {
        return 1000000;
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        Buff.affect(enemy, SoulBleeding.class).prolong(Dungeon.depth/2 + 3);
        CellEmitter.get(enemy.pos).burst(ShadowParticle.CURSE, 10);
        return super.attackProc(enemy, damage);
    }
    @Override
    public void hitSound(float pitch) {
        Sample.INSTANCE.play(Assets.Sounds.CURSED, 1, pitch);
    }

    @Override
    protected boolean canAttack( Char enemy ) {//does not attack close foes in melee
        return new Ballistica( pos, enemy.pos, Ballistica.TARGETING_BOLT).collisionPos == enemy.pos && Dungeon.level.distance(enemy.pos, this.pos)<=attackRange;
    }
}
