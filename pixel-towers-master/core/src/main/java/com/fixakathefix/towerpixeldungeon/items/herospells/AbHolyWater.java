package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Fire;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Blindness;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.buffs.PriorityTarget;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Terror;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Vertigo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM100;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM200;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMW;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goblin;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Guard;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Tengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.CampRat;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.mechanics.ConeAOE;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbHolyWater extends HeroSpellTargeted{
    {
        image = ItemSpriteSheet.HEROSPELL_HOLY_WATER;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    Ballistica bolt = new Ballistica(Dungeon.hero.pos, cell,Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID);
                    Dungeon.hero.busy();
                    fx(bolt);
                    cooldown();
                }
            }

            @Override
            public String prompt () {
                return Messages.get(HeroSpellTargeted.class, "cell_choose");
            }
        };
    }

    ConeAOE cone;

    public void onZap(Ballistica bolt) {

        ArrayList<Char> affectedChars = new ArrayList<>();
        ArrayList<Integer> adjacentCells = new ArrayList<>();
        for( int cell : cone.cells ){

            //ignore caster cell
            if (cell == bolt.sourcePos){
                continue;
            }

            //knock doors open
            if (Dungeon.level.map[cell] == Terrain.DOOR){
                Level.set(cell, Terrain.OPEN_DOOR);
                GameScene.updateMap(cell);
            }

            if (Dungeon.level.adjacent(bolt.sourcePos, cell)){
                adjacentCells.add(cell);
            }

            Char ch = Actor.findChar( cell );
            if (ch != null) {
                affectedChars.add(ch);
            }
        }

        //if wand was shot right at a wall
        if (cone.cells.isEmpty()){
            adjacentCells.add(bolt.sourcePos);
        }

        for ( Char ch : affectedChars ){
            if (ch.properties().contains(Char.Property.UNDEAD)||ch.properties().contains(Char.Property.DEMONIC)) {
                ch.damage(10 + Dungeon.hero.lvl/2, this);
                Buff.affect(ch, Blindness.class, 10);
                CellEmitter.floor(ch.pos).start(ShadowParticle.UP,0.1f,10);
            } else if (ch.alignment == Char.Alignment.ALLY){
                ch.heal(5 + Dungeon.hero.lvl/2);
            } else {
                ch.damage(5 + Dungeon.hero.lvl/3, this);
            }
        }
    }


    public void fx(Ballistica bolt) {
        //need to perform flame spread logic here so we can determine what cells to put flames in.

        // 3 distance
        int maxDist = 6;
        int dist = Math.min(bolt.dist, maxDist);

        cone = new ConeAOE( bolt,
                maxDist,
                50,
                Ballistica.STOP_TARGET | Ballistica.STOP_SOLID );

        //cast to cells at the tip, rather than all cells, better performance.
        for (Ballistica ray : cone.outerRays){
            ((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
                    MagicMissile.WATER_CONE,
                    curUser.sprite,
                    ray.path.get(ray.dist),
                    null
            );
        }

        Callback callback = new Callback() {
            @Override
            public void call() {
                onZap(bolt);
                Dungeon.hero.spendAndNext(1f);
            }
        };
        //final zap at half distance, for timing of the actual wand effect
        MagicMissile.boltFromChar( curUser.sprite.parent,
                MagicMissile.WATER_CONE,
                curUser.sprite,
                bolt.path.get(dist/2),
                callback );
        Sample.INSTANCE.play( Assets.Sounds.SHATTER );
        Sample.INSTANCE.play( Assets.Sounds.WATER );

    }



    @Override
    protected int castCooldown() {
        return 30;
    }
}
