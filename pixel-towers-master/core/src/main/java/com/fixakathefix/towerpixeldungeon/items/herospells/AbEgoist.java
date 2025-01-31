package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Bless;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Foresight;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Healing;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Hex;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Levitation;
import com.fixakathefix.towerpixeldungeon.actors.buffs.MindVision;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Protected;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Stamina;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCSpawning;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCWall;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTntLog;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerTotem;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbEgoist extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_EGOIST;
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
           Buff.affect(Dungeon.hero, Random.oneOf(Bless.class, Stamina.class, Protected.class, Strength.class, Battlecry.class, Invisibility.class, MindVision.class, Levitation.class, Foresight.class ), 50);
        }
        Buff.affect(Dungeon.hero, Healing.class).setHeal(1+ mobsaffected.size()*20, 0.05f,5);
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        cooldown();
    }

    @Override
    protected int castCooldown() {
        return 150;
    }
}
