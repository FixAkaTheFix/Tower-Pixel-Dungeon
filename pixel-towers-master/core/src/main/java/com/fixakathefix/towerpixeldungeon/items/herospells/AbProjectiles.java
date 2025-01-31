package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Roots;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.items.weapon.missiles.darts.ParalyticDart;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbProjectiles extends HeroSpell {
    {
        image = ItemSpriteSheet.HEROSPELL_PROJECTILES;
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
    protected int castCooldown() {
        return 50 + Dungeon.depth*10;
    }
}
