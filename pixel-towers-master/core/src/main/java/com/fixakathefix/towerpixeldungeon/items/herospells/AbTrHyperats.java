package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCrossbow1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerRatCamp;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbTrHyperats extends HeroSpell{

    {
        image = ItemSpriteSheet.HEROSPELL_TR_CHAMP;
    }

    private static final int TURNS_PER_RATCAMP = 20;

    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof TowerRatCamp){
                ((TowerRatCamp)mob).prepChamps();
            }
        }
    }
    @Override
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_PER_RATCAMP);
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if (mob instanceof TowerRatCamp && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_PER_RATCAMP + ((Arena)Dungeon.level).waveCooldownBoss/3;
            }
        } catch (NullPointerException ignored) {
        }
        return 100 + addturns;
    }
}
