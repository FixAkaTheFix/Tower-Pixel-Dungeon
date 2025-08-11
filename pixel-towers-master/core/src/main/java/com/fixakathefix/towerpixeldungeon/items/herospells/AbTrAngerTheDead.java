package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGraveCrypt;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Fireball;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.PurpleParticle;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.fixakathefix.towerpixeldungeon.items.scrolls.exotic.ScrollOfDemonicSkull;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.function.Predicate;

public class AbTrAngerTheDead extends HeroSpell{

    {
        image = ItemSpriteSheet.HEROSPELL_TR_ANGERTHEDEAD;
    }
    private static final int TURNS_ADDED_PER_GRAVE = 30;


    @Override
    public void cast() {
        super.cast();
        ArrayList<Integer> friconcurrent = new ArrayList<>();
        Sample.INSTANCE.play(Assets.Sounds.DEATH);
        Sample.INSTANCE.play(Assets.Sounds.BONES);
        for (Mob mob : Level.mobs){
            if ((mob instanceof TowerGrave1 || mob instanceof TowerGraveCrypt) && mob.alignment == Char.Alignment.ALLY){
                ArrayList<Integer> emptycells = new ArrayList<>();
                for (int i : PathFinder.NEIGHBOURS8) {
                    int cell = i + mob.pos;
                    if (Dungeon.level.passable[cell] && Char.findChar(cell)==null){
                        emptycells.add(cell);
                    }
                }
                if (!emptycells.isEmpty()) friconcurrent.add(Random.element(emptycells));
            }
        }
        for (int cell : friconcurrent){
            ScrollOfSkulls.ExplosiveSkull skully = new ScrollOfSkulls.ExplosiveSkull();
            skully.pos = cell;
            skully.state = skully.HUNTING;
            CellEmitter.center(cell).burst(FlameParticle.FACTORY,10);
            GameScene.add(skully);
        }
    }

    @Override
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_ADDED_PER_GRAVE);
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if ((mob instanceof TowerGrave1 || mob instanceof TowerGraveCrypt) && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_GRAVE;
            }
        } catch (NullPointerException ignored) {
        }
        return 100 + addturns;
    }
}
