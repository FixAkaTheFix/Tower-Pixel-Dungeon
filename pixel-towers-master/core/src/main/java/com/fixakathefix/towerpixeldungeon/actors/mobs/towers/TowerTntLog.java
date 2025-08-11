package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.BlastParticle;
import com.fixakathefix.towerpixeldungeon.items.herospells.AbTrPlanB;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.sprites.TowerTntLogSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class TowerTntLog extends TowerCWall{

    {
        spriteClass = TowerTntLogSprite.class;
        HP = HT = 175;
        cost = 400;
        upgrade1Cost = 150;
        damageMin = 10;
        damageMax = 50;
        defMin = 1;
        defMax = 2;
        upgCount = 0;
        state = PASSIVE;
    }

    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HT , damageMin, damageMax));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }

    @Override
    public void die(Object cause) {
        int cell;
        Sample.INSTANCE.play(Assets.Sounds.BLAST);
        for (int i : PathFinder.NEIGHBOURS25){
            cell = pos + i;
            Char ch = Char.findChar(cell);
            if (ch!=null){
                if (ch.alignment == Alignment.ALLY){
                    //friends receive 0 damage
                } else {
                    int dam = Math.round(damageRoll()) - enemy.drRoll();

                    ch.damage (cause instanceof AbTrPlanB ? dam*2 : dam,this);
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, cause instanceof AbTrPlanB ? 6 : 3, false, true, getClass());
                };//damages foes nearby and throws them away
            }
            if (level.heroFOV[cell]) {
                CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
            }
        }


        super.die(cause);
    }
}
