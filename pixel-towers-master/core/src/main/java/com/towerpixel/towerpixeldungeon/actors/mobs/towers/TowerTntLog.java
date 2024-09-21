package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;
import static com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave.throwChar;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Cripple;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.BlastParticle;
import com.towerpixel.towerpixeldungeon.effects.particles.SmokeParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerTntLogSprite;
import com.towerpixel.towerpixeldungeon.sprites.walls.TowerWall1Sprite;
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
                    ch.damage (Math.round(damageRoll()) - enemy.drRoll(),this);
                    Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
                    throwChar(ch, trajectory, 3, false, true, getClass());
                };//damages foes nearby and throws them away
            }
            if (level.heroFOV[cell]) {
                CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
            }
        }


        super.die(cause);
    }
}
