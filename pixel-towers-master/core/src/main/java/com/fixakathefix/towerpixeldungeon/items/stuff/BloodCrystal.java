package com.fixakathefix.towerpixeldungeon.items.stuff;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.SoulBleeding;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Succubus;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.quest.CeremonialCandle;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BloodCrystal extends Item  {
    {
        image = ItemSpriteSheet.BLOOD_CRYSTAL;
        cursed = true;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    protected void onThrow(int cell) {
        if (CeremonialCandle.checkCellForSurroundingLitCandles(cell)){
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS25){
                int cel = cell + i;
                if (Char.findChar(cel)==null && Dungeon.level.passable[cel]) candidates.add(cel);
            }
            int power = 40 + Dungeon.depth*10;
            while (power > 0 && !candidates.isEmpty()){
                power -= 40;
                RipperDemon demon = new RipperDemon();
                demon.pos = Random.element(candidates);
                candidates.remove((Integer)demon.pos);
                demon.alignment = Char.Alignment.ALLY;
                demon.state = demon.HUNTING;
                CellEmitter.get(demon.pos).burst(FlameParticle.FACTORY, 7);
                GameScene.add(demon);
            }
            Buff.affect(Dungeon.hero, SoulBleeding.class).prolong(Dungeon.hero.HT/3);
            Badges.validateBloodCultist(1);
            Sample.INSTANCE.play(Assets.Sounds.DEATH);
            CellEmitter.get(cell).start(FlameParticle.FACTORY, 0.02f,50);
            return;
        }
        super.onThrow(cell);
    }

    @Override
    public void doDrop(Hero hero) {
        super.doDrop(hero);
    }

    @Override
    public int value() {
        return 200;
    }
}
