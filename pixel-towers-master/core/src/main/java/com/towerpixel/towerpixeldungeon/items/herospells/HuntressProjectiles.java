package com.towerpixel.towerpixeldungeon.items.herospells;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.MindVision;
import com.towerpixel.towerpixeldungeon.actors.buffs.Roots;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.huntress.SpectralBlades;
import com.towerpixel.towerpixeldungeon.actors.mobs.Mob;
import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.items.stones.StoneOfIntuition;
import com.towerpixel.towerpixeldungeon.items.weapon.SpiritBow;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.Dart;
import com.towerpixel.towerpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.towerpixel.towerpixeldungeon.sprites.MissileSprite;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HuntressProjectiles extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_HUNTRESS_PROJECTILES;
    }

    @Override
    public void cast() {
        super.cast();
        ArrayList<Mob> mobsToShoot = new ArrayList<>();
        for (Mob mob : Dungeon.level.mobs) {
            if (Dungeon.level.heroFOV[mob.pos] && mob.alignment == Char.Alignment.ENEMY) {
                mobsToShoot.add(mob);
            }
        }

        int mob1 = -20;
        int mob2 = -19;
        int mob3 = -28;
        ArrayList<Integer> arrayOfMobs = new ArrayList<>();
        if (mobsToShoot.size() >=1){

            mob1 = (int) (Math.random() * mobsToShoot.size());
            arrayOfMobs.add(mob1);
        }
        if (mobsToShoot.size() >=2){

            do {
                mob2 = (int) (Math.random() * mobsToShoot.size());
            } while (mob1!=mob2);
            arrayOfMobs.add(mob2);
        }
        if (mobsToShoot.size() >=3){
            do {
                mob3 = (int) (Math.random() * mobsToShoot.size());
            } while (mob3!=mob2);
            arrayOfMobs.add(mob3);
        }
        for (int i : arrayOfMobs){
            Hero user = Dungeon.hero;
            ((MissileSprite) user.sprite.parent.recycle(MissileSprite.class)).
                    reset(user.pos,
                            i,
                            new ParalyticDart(),
                            new Callback() {
                                @Override
                                public void call() {
                                    Char ic = Char.findChar(i);
                                    if (ic !=null && ic.isAlive()) {
                                        curUser = user;
                                        Buff.affect(ic, Roots.class, Random.Int(2,6));
                                        if (Dungeon.level.map[i]== Terrain.EMPTY) Painter.set(Dungeon.level, i, Terrain.HIGH_GRASS);
                                    }
                                }
                            });
        }

    }

    @Override
    protected int castCost() {
        return 50 + Dungeon.depth*10;
    }
}
