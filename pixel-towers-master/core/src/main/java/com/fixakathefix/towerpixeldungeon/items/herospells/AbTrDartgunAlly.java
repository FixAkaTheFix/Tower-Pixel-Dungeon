package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.effects.particles.PurpleParticle;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrDartgunAlly extends HeroSpell{

    {
        image = ItemSpriteSheet.HEROSPELL_TR_SUMMONDARTGUN;
    }

    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.RAY);
        ArrayList<Integer> veryclosecells = new ArrayList<>();
        ArrayList<Integer> abitclosecells = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS8){
            int cell = Dungeon.hero.pos + i;
            if (Dungeon.level.passable[cell] && Char.findChar(cell)==null) veryclosecells.add(cell);
        }
        for (int i : PathFinder.NEIGHBOURS25){
            int cell = Dungeon.hero.pos + i;
            if (Dungeon.level.passable[cell] && Char.findChar(cell)==null) abitclosecells.add(cell);
        }
        if (!veryclosecells.isEmpty()){
            summonCutieBeautyPieDarty(Random.element(veryclosecells));
        } else if (!abitclosecells.isEmpty()){
            summonCutieBeautyPieDarty(Random.element(abitclosecells));
        } else {
            Sample.INSTANCE.play(Assets.Sounds.DEGRADE, 1f, 0.8f);
            Dungeon.hero.speak(Messages.get(AbTrDartgunAlly.class, "no_space"), CharSprite.MYSTERIOUS);
        }

    }
    private void summonCutieBeautyPieDarty(int cell){
        TowerDartgun1 darto = new TowerDartgun1();
        Buff.affect(darto, Animated.class);
        darto.sellable = false;
        darto.pos = cell;
        GameScene.add(darto);
        Dungeon.level.occupyCell(darto);
        CellEmitter.floor(cell).burst(Speck.factory(Speck.HEART), 5);
    }

    @Override
    protected int castCooldown() {
        return Math.max(50, 200 - (int)Math.pow(Dungeon.depth, 2));
    }
}
