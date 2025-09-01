package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wisp;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPRed;
import com.fixakathefix.towerpixeldungeon.items.Generator;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbPray extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_PRAY;
    }

    @Override
    public void cast() {
        super.cast();
        int index = Random.NormalIntRange(1, 1000);
        GLog.i(prayResult(index));
    }

    private String prayResult(int index){
        Sample.INSTANCE.play(Assets.Sounds.READ);
        if (index < 300 ) return Messages.get(AbPray.class, "no");
        else {
            if (index < 400){
                subtleWispSummon();
            } else if (index < 500){
                subtleHeroHeal();
                subtleCoinGive();
            } else if (index < 550){
                subtleHeroBless();
            } else if (index < 600){
                subtleItemDrop();
                subtleBless();
            } else if (index < 700){
                subtleFoeIgnite();
            } else if (index < 750){
                subtleFoeKill();
            } else if (index < 800){
                subtleMassHealToHalf();
            } else if (index < 850){
                subtleDefenseskillUpgrade();
            } else if (index < 900){
                subtleGoldDrop();
                subtleHeroShield();
            } else if (index < 950){
                subtleSeveralFoeKill();
            } else if (index < 999){
                subtleSeveralFoeKill();
            } else if (index == 1000){
                apocalypse();
            }
        }
        return Messages.get(AbPray.class, "yes");
    }

    private void subtleHeroHeal(){
        Dungeon.hero.heal(Dungeon.hero.HT/3);
    }
    private void subtleDefenseskillUpgrade(){
        for (Mob mob : Level.mobs){
         if (mob.alignment== Char.Alignment.ALLY) mob.defenseSkill++;
        }
    }
    private void subtleMassHealToHalf(){
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ALLY) {
                mob.heal(mob.HT/5);
            }
        }
    }
    private void apocalypse(){
        GameScene.flash(0xffff99);
        Camera.main.shake(10f,5f);
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ENEMY) if (!mob.properties().contains(Char.Property.BOSS) && !mob.properties().contains(Char.Property.MINIBOSS)) {
                Sample.INSTANCE.play(Random.oneOf(Assets.Sounds.HIT, Assets.Sounds.HIT_CRUSH, Assets.Sounds.HIT_STRONG));
                mob.damage(mob.HT/2, Dungeon.hero);
                WandOfBlastWave.BlastWave.blast(mob.pos);

            }
        }
    }
    private void subtleGoldDrop(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 0;x < Dungeon.level.width();x++) for (int y = 0; y < Dungeon.level.height(); y++) {
            int cell = x + Dungeon.level.width()*y;
            if (Dungeon.level.passable[cell] &&
                    !Dungeon.level.heroFOV[cell]) candidates.add(cell);

        }
        if (!candidates.isEmpty()) {
            Dungeon.level.dropMany(candidates,
                    new Gold(Random.Int(10, 10 + Dungeon.depth)),
                    new Gold(Random.Int(10, 10 + Dungeon.depth)),
                    new Gold(Random.Int(10, 10 + Dungeon.depth)),
                    new Gold(Random.Int(10, 10 + Dungeon.depth)),
                    new Gold(Random.Int(10, 10 + Dungeon.depth))
            );
        }
    }
    private void subtleItemDrop(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 0;x < Dungeon.level.width();x++) for (int y = 0; y < Dungeon.level.height(); y++) {
            int cell = x + Dungeon.level.width()*y;
            if (Dungeon.level.passable[cell] &&
                    !Dungeon.level.heroFOV[cell]) candidates.add(cell);

        }
        if (!candidates.isEmpty()){
            Dungeon.level.dropMany(candidates,
                    Generator.random(),
                    Generator.random()
            );
        }
    }
    private void subtleFoeIgnite(){
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ENEMY){
                Buff.affect(mob, Burning.class).setTime(50);
                return;
            }
        }
    }
    private void subtleBless(){
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ALLY && mob.buff(Bless.class)==null){
                Buff.affect(mob, Bless.class,2000);
                return;
            }
        }
    }
    private void subtleFoeKill() {
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ENEMY)
                if (!mob.properties().contains(Char.Property.BOSS) && !mob.properties().contains(Char.Property.MINIBOSS)) {
                    Camera.main.shake(5f,0.5f);
                    mob.die(Dungeon.hero);
                    return;
            }
        }
    }

    private void subtleSeveralFoeKill() {
        int killed = 0;
        for (Mob mob : Level.mobs){
            if (mob.alignment== Char.Alignment.ENEMY)
                if (mob.properties().contains(Char.Property.BOSS) && mob.properties().contains(Char.Property.MINIBOSS)) {
                    GameScene.flash(0xffffff);
                    Sample.INSTANCE.play(Assets.Sounds.RAY);
                    mob.die(Dungeon.hero);
                    killed++;
                }
            if (killed >=5) return;
        }
    }

    private void subtleWispSummon(){
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int x = 0;x < Dungeon.level.width();x++) for (int y = 0; y < Dungeon.level.height(); y++) {
            int cell = x + Dungeon.level.width()*y;
            if (Dungeon.level.passable[cell] &&
                    !Dungeon.level.heroFOV[cell]) candidates.add(cell);

        }
        if (!candidates.isEmpty()){
            Wisp wisp = new Wisp();
            wisp.pos = Random.element(candidates);
            wisp.alignment = Char.Alignment.ALLY;
            wisp.state = wisp.HUNTING;
            GameScene.add(wisp);
        }
    }
    private void subtleCoinGive(){
        Dungeon.level.drop(new Gold(Random.Int(10, 10+Dungeon.depth)), Dungeon.hero.pos);
    }
    private void subtleHeroBless(){
        Buff.affect(Dungeon.hero, Bless.class, 200);
    }
    private void subtleHeroShield(){
        Buff.affect(Dungeon.hero, Barrier.class).setShield(Dungeon.depth * 2);
    }

    @Override
    protected int castCooldown() {
        return 40;
    }
}