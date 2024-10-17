package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.Fire;
import com.towerpixel.towerpixeldungeon.actors.blobs.Freezing;
import com.towerpixel.towerpixeldungeon.actors.buffs.Burning;
import com.towerpixel.towerpixeldungeon.actors.buffs.Frost;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.ShieldBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Slow;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.plants.Sungrass;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.PortalUnstableSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;

public class SubAmuletTower extends Mob {//this is the secondary tower to kill

    {
        spriteClass = PortalUnstableSprite.class;

        HP = HT = 10;

        viewDistance = 3;

        defenseSkill = 0;

        EXP = 0;

        state = PASSIVE;

        flying = true;

        properties.add(Property.IMMOVABLE);
        properties.add(Property.BOSS);
        properties.add(Property.INORGANIC);

        immunities.add(Healing.class);
        immunities.add(Sungrass.Health.class);
        immunities.add(ShieldBuff.class);

        immunities.add(Paralysis.class);
        immunities.add(Slow.class);
        immunities.add(Frost.class);
        immunities.add(Freezing.class);
        immunities.add(Fire.class);
        immunities.add(Burning.class);

        alignment = Alignment.ALLY;
    }

    public SubAmuletTower(){
        super();
    }

    @Override
    public void die(Object cause) {
        hero.die(this);
        GLog.cursed("Amulet was lost...");
        super.die(cause);
    };
    @Override
    protected boolean act() {

        HP = ((Arena)level).amuletTower.HP;
        if (Dungeon.depth == 18) GameScene.updateFog(pos, 8);
        else GameScene.updateFog(pos, 5);


        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {
        //can only be damaged by chars in melee (by jumping into it). Melee enemies attack it directly, while any mob marked as ranged can attack it only in close quarters. See Mob
        if (src instanceof Char && ((Char)src).alignment==Alignment.ENEMY && (distance(((Char)src))<=1 || (src instanceof Mob && !((Mob)src).ranged))) {
            Char chsrc = (Char)src;
            chsrc.damagePortal(pos);
            if (chsrc.properties().contains(Property.BOSS)){
                ((Arena)level).amuletTower.damage(100, SubAmuletTower.class);
            } else if (chsrc.properties().contains(Property.BOSS)){
                ((Arena)level).amuletTower.damage(5, SubAmuletTower.class);
            } else ((Arena)level).amuletTower.damage(1, SubAmuletTower.class);
        } else super.damage(1, SubAmuletTower.class);
        HP = ((Arena)level).amuletTower.HP;
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public void beckon(int cell) {
        //do nothing
    }

    @Override
    public boolean interact(Char c) {
        return true;
    }


    @Override
    protected boolean canAttack(Char enemy) {
        return false;
    }

}