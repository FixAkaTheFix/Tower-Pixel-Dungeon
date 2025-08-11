package com.fixakathefix.towerpixeldungeon.items.herospells;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.badlogic.gdx.utils.Timer;
import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Overcharge;
import com.fixakathefix.towerpixeldungeon.actors.hero.abilities.rogue.ShadowClone;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannon1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonMissileLauncher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerCannonNuke;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.bombs.Bomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.Firebomb;
import com.fixakathefix.towerpixeldungeon.items.bombs.ShockBomb;
import com.fixakathefix.towerpixeldungeon.items.weapon.enchantments.Chilling;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrOvercharge extends HeroSpell{
    private static final int TURNS_ADDED_PER_WAND = 40;

    {
        image = ItemSpriteSheet.HEROSPELL_TR_MANABOOM;
    }

    @Override
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), TURNS_ADDED_PER_WAND);
    }
    @Override
    public void cast() {
        super.cast();
        Sample.INSTANCE.play(Assets.Sounds.CHARGEUP);
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof TowerWand1){
                mob.sprite.emitter().start(MagicMissile.MagicParticle.ATTRACTING, 0.02f, 30);
                Buff.affect(mob, Overcharge.class, 3);
            }
        }
    }

    @Override
    protected int castCooldown() {
        int addturns = 0;
        try {
            for (Mob mob : Level.mobs) {
                if (mob instanceof TowerWand1 && mob.alignment == Char.Alignment.ALLY)
                    addturns += TURNS_ADDED_PER_WAND;
            }
        } catch (NullPointerException ignored) {
        }
        return 100 + addturns;
    }
}
