package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Animated;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Overcharge;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerDartgun1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrPlanB extends HeroSpell{

    {
        image = ItemSpriteSheet.HEROSPELL_TR_PLANB;
    }

    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        ArrayList<Mob> mobsmoconcur = new ArrayList<>();
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof TowerTntLog && mob.alignment == Char.Alignment.ALLY){
                mobsmoconcur.add(mob);
            }
        }
        for (Mob mob : mobsmoconcur){
            mob.die(this);
        }
    }

    @Override
    protected int castCooldown() {
        return 100;
    }
}
