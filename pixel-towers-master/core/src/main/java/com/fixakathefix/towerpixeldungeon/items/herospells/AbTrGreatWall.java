package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCWall;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPShield;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class AbTrGreatWall extends HeroSpell{
    private static final int TURNS_ADDED_PER_WALL = 10;

    {
        image = ItemSpriteSheet.HEROSPELL_TR_WALL;
    }

    @Override
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_ADDED_PER_WALL);
    }
    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.MASTERY);
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof TowerCWall){
                int wallnear = 0;
                for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(mob.pos + i) instanceof TowerCWall) wallnear+=Char.findChar(mob.pos + i).HT/10;
                mob.sprite.emitter().start(CPShield.TOCENTER, 0.01f, 30);
                Buff.affect(mob, Barrier.class).setShield(wallnear);
            }
        }
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if (mob instanceof TowerCWall && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_WALL;
            }
        } catch (NullPointerException ignored) {
        }
        return 100 + addturns;
    }
}
