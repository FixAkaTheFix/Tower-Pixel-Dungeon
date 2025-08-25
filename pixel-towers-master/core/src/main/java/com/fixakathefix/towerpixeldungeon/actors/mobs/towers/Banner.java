package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Inspired;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPRed;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.FlagFriendSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerTotemSprite;
import com.watabou.utils.PathFinder;

public class Banner extends TowerCTotem{
    //gives a bit of damage speed boost
    {
        HP = HT = 10;
        spriteClass = FlagFriendSprite.class;
        abTimeMax = 15;
        upgCount = 0;
        sellable = false;
    }
    protected void useAbility(int cell){
        Char ch = Char.findChar(cell);
        if (ch!=null){
            if (!(ch instanceof TowerNotliving))Buff.append(ch, Inspired.class,15);
            ch.heal(1 + Dungeon.depth/5);
        }
    }
    protected void searchAndUse(){
        WandOfBlastWave.BlastWave.blast(pos);
        for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(pos+i)!=null){
            Char ch = Char.findChar(pos + i);
            if (ch.alignment==alignment && ch != this)
                useAbility(pos+i);
        }
    }
}
