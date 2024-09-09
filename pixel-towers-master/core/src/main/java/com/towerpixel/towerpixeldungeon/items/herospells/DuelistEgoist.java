package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Barkskin;
import com.towerpixel.towerpixeldungeon.actors.buffs.Bless;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Foresight;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.actors.buffs.Hex;
import com.towerpixel.towerpixeldungeon.actors.buffs.Invisibility;
import com.towerpixel.towerpixeldungeon.actors.buffs.Levitation;
import com.towerpixel.towerpixeldungeon.actors.buffs.MindVision;
import com.towerpixel.towerpixeldungeon.actors.buffs.Protected;
import com.towerpixel.towerpixeldungeon.actors.buffs.Stamina;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.Tower;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCSpawning;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerCWall;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWall1;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.custom.CPRed;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class DuelistEgoist extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_DUELIST_EGOIST;
    }

    @Override
    public void cast() {
        super.cast();
        ArrayList<Mob> mobsaffected = new ArrayList<>();
        for (Mob mob : Dungeon.level.mobs) {
            if (mob instanceof Tower
                    && !(mob instanceof TowerCWall)
                    && !(mob instanceof TowerTntLog)
                    && !(mob instanceof TowerTotem)
                    && !(mob instanceof TowerCSpawning)
                    && mob.alignment == Char.Alignment.ALLY){
                mobsaffected.add(mob);
            }
        }
        for (Mob mob : mobsaffected) {
           Buff.affect(mob, Hex.class, 100);
           Buff.affect(Dungeon.hero, Random.oneOf(Bless.class, Stamina.class, Protected.class, Strength.class, Invisibility.class, MindVision.class, Levitation.class, Foresight.class ), 50);
        }
        Buff.affect(Dungeon.hero, Healing.class).setHeal(1+ mobsaffected.size()*20, 0.05f,5);
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        Dungeon.gold -= castCost();
        updateQuickslot();
    }

    @Override
    protected int castCost() {
        return 100;
    }
}
