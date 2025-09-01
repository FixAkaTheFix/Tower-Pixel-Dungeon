package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Chill;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Frost;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MagicalSleep;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Minion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Slow;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Ghoul;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BloodParticle;
import com.fixakathefix.towerpixeldungeon.items.stuff.BloodCrystal;
import com.fixakathefix.towerpixeldungeon.levels.Arena10;
import com.fixakathefix.towerpixeldungeon.levels.Arena16;
import com.fixakathefix.towerpixeldungeon.levels.Arena20;
import com.fixakathefix.towerpixeldungeon.levels.Arena25;
import com.fixakathefix.towerpixeldungeon.levels.Arena4;
import com.fixakathefix.towerpixeldungeon.levels.Arena5;
import com.fixakathefix.towerpixeldungeon.levels.endlessarenas.EndlessArena3;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.NightmareRiftSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class EnemyPortal extends Mob {

    {
        spriteClass = NightmareRiftSprite.class;

        properties.add(Property.MINIBOSS);
        properties.add(Property.DEMONIC);
        properties.add(Property.INORGANIC);
        properties.add(Property.IMMOVABLE);


        immunities.add(Frost.class);
        immunities.add(Paralysis.class);
        immunities.add(Chill.class);
        immunities.add(Slow.class);
        immunities.add(MagicalSleep.class);

        HP = HT = Dungeon.depth*70 + 300;
        defenseSkill = 0;

        viewDistance = 5;

        EXP = 2;
        maxLvl = 10;

        state = HUNTING;
        alignment = Alignment.ENEMY;
    }

    public int countDownToSpawn = 30;
    public int countDownToSpawnLeft = countDownToSpawn;//??
    public boolean counting = false;

    public int damageAccum = 0;


    @Override
    protected boolean act() {
        GameScene.updateFog(pos, 5);
        if (counting){
            countDownToSpawnLeft--;
            if (countDownToSpawnLeft<=0) summonMobs();
        }
        return super.act();
    }

    public void startMobSpawnTimer(int countToSpawn){
        countDownToSpawnLeft = countDownToSpawn = countToSpawn;
        counting = true;
    }

    @Override
    public void damage(int dmg, Object src) {
        damageAccum+=dmg;
        if (damageAccum > Reflection.newInstance(chooseMobClass()).HT*2) {
            damageAccum = 0;
            ArrayList<Integer> candidatesNear = new ArrayList<>();
            Sample.INSTANCE.play(Assets.Sounds.CURSED);
            for (int i : PathFinder.NEIGHBOURS25){
                int cell = pos + i;
                if (Char.findChar(cell) == null && level.passable[cell]){
                    candidatesNear.add(cell);
                }
            }
            if (!candidatesNear.isEmpty()){
                Mob mob = Reflection.newInstance(chooseMobClass());
                mob.pos = Random.element(candidatesNear);
                mob.state = mob.HUNTING;
                mob.alignment = alignment;
                Buff.affect(mob, Minion.class);
                GameScene.add(mob);
                CellEmitter.floor(mob.pos).burst(BloodParticle.BURST, 10);
            }
        }
        super.damage(dmg, src);
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }

    /*@Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        return info.toString();
    }*/
    @Override
    protected boolean canAttack(Char enemy) {return false;
    }
    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        NightmareRiftSprite sprite = (NightmareRiftSprite) super.sprite();
        if (HP<HT*0.5f) {
            sprite.broken();
        }
        return sprite;
    }
    public void summonMobs(){
        ArrayList<Integer> candidatesNear = new ArrayList<>();
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        counting = false;
        countDownToSpawnLeft = countDownToSpawn;
        for (int count = 0; count<chooseMobCount();count++){
            count++;
            for (int i : PathFinder.NEIGHBOURS25){
                int cell = pos + i;
                if (Char.findChar(cell) == null && level.passable[cell]){
                    candidatesNear.add(cell);
                }
            }
            if (!candidatesNear.isEmpty()){
                Mob mob = Reflection.newInstance(chooseMobClass());
                mob.pos = Random.element(candidatesNear);
                mob.state = mob.HUNTING;
                mob.alignment = alignment;
                Buff.affect(mob, Minion.class);
                GameScene.add(mob);
                CellEmitter.floor(mob.pos).burst(BloodParticle.BURST, 10);
            }
        }
    }
    public static void createEnemyPortal(int pos, int cooldown){
        EnemyPortal port = new EnemyPortal();
        port.countDownToSpawn = port.countDownToSpawnLeft = cooldown;
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        port.pos = pos;
        GLog.i(Messages.get(EnemyPortal.class,"appear"));
        CellEmitter.floor(pos).start(BloodParticle.FACTORY,0.2f,10);
        if (Char.findChar(pos)!=null) Char.findChar(pos).damage(666666, EnemyPortal.class);
        GameScene.add(port);
    }

    public static Class<? extends Mob> chooseMobClass(){

        //the portals will appear on levels 4, 5 (two),
        Class<? extends Mob> mob = Rat.class;

        if (level instanceof Arena4) mob = Snake.class;
        if (level instanceof Arena5) mob = CausticSlime.class;
        if (level instanceof Arena10) mob = Thief.class;
        if (level instanceof EndlessArena3) mob = Thief.class;
        if (level instanceof Arena16) mob = Ghoul.class;
        if (level instanceof Arena20) mob = Ghoul.class;
        if (level instanceof Arena25) mob = RipperDemon.class;
        return mob;
    }

    public static int chooseMobCount(){
        int count = 1;

        if (level instanceof Arena4) count = level.wave/2+1;
        if (level instanceof Arena5) count = level.wave/3-1;
        if (level instanceof Arena10) count = (int) (level.wave * 0.5f);
        if (level instanceof Arena16) count = (int) (level.wave * 0.2f) + 1;
        if (level instanceof Arena20) count = (int) (level.wave * 0.35f) + 1;
        if (level instanceof Arena25) count = (int) (level.wave * 0.2f) + 1;
        return count;
    }

    @Override
    public void die(Object cause) {
        Badges.validateEyeSlayer();
        level.drop(new BloodCrystal(), pos);
        super.die(cause);
    }

    public static final String SPAWNCOUNTDOWN = "spawncountdown";
    public static final String SPAWNCOUNTDOWNLEFT = "spawncountdownleft";
    public static final String COUNTING = "counting";
    public static final String DAMAGEACCUM = "damageaccumulated";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SPAWNCOUNTDOWN, countDownToSpawn);
        bundle.put(SPAWNCOUNTDOWNLEFT, countDownToSpawnLeft);
        bundle.put(COUNTING,counting);
        bundle.put(DAMAGEACCUM, damageAccum);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        countDownToSpawn = bundle.getInt(SPAWNCOUNTDOWN);
        countDownToSpawnLeft = bundle.getInt(SPAWNCOUNTDOWNLEFT);
        counting = bundle.getBoolean(COUNTING);
        damageAccum = bundle.getInt(DAMAGEACCUM);
    }
}
