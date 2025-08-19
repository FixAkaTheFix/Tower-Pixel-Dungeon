package com.fixakathefix.towerpixeldungeon.items.quest;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vile;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RipperDemon;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.GooSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;



public class CorruptedOoze extends Item {

    {
        image = ItemSpriteSheet.OOZE;
        unique = true;

    }
    @Override
    protected void onThrow(int cell) {
        if (CeremonialCandle.checkCellForSurroundingLitCandles(cell)){
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int i : PathFinder.NEIGHBOURS25){
                int cel = cell + i;
                if (Char.findChar(cel)==null && Dungeon.level.passable[cel]) candidates.add(cel);
            }
            int power = 40;
            while (power > 0 && !candidates.isEmpty()){
                power -= 40;
                Goo goo = new Goo();
                goo.pos = Random.element(candidates);
                candidates.remove((Integer)goo.pos);
                goo.alignment = Char.Alignment.ALLY;
                goo.state = goo.HUNTING;
                CellEmitter.get(goo.pos).burst(GooSprite.GooParticle.FACTORY, 7);
                GameScene.add(goo);
            }
            Sample.INSTANCE.play(Assets.Sounds.DEATH);
            CellEmitter.get(cell).start(GooSprite.GooParticle.FACTORY, 0.02f,50);
            return;
        }
        super.onThrow(cell);
    }

    @Override
    public boolean isIdentified() {
        return true;
    }

    public static final String AC_DRINK = "DRINK";

    @Override
    public ArrayList<String> actions( Hero hero ) {
        ArrayList<String> actions = super.actions( hero );
        actions.add( AC_DRINK );
        return actions;
    }

    @Override
    public void execute( final Hero hero, String action ) {
        super.execute( hero, action );
        if (action.equals( AC_DRINK)) {
            eat( hero );
        }
    }


    protected void eat( Hero hero ) {

        detach( hero.belongings.backpack );
        hero.spend( 1 );
        hero.busy();
        Sample.INSTANCE.play( Assets.Sounds.DRINK);
        Buff.affect(hero, Vile.class);
        hero.sprite.operate( hero.pos );
    }
    public static class Recipe extends com.fixakathefix.towerpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{Vilebloom.class, GooBlob.class, PotionOfLiquidFlame.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 10;

            output = CorruptedOoze.class;
            outQuantity = 1;
        }

    }

    @Override
    public int value() {
        return 30;
    }
}
