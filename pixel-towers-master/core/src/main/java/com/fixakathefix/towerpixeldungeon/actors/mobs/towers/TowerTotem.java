package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Barrier;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Battlecry;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Wraith;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPHeal;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPRed;
import com.fixakathefix.towerpixeldungeon.effects.particles.custom.CPShield;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.TowerTotemSprite;
import com.watabou.utils.PathFinder;

public abstract class TowerTotem extends TowerCTotem{

    {
        HT = HP = 300;
        upgCount = 0;
    }

    public static class TotemNecrotic extends TowerTotem{
        //finishes almost-dead foes and turns them into wraiths
        {
            spriteClass = TowerTotemSprite.TNecrotic.class;
            abTimeMax = 1;
        }
        protected void useAbility(int cell){
            Char ch = Char.findChar(cell);
            if (ch.HP < ch.HT/15 && !ch.properties().contains(Property.BOSS)){
                ch.die(this);
                Wraith wraith = new Wraith();
                wraith.alignment = this.alignment;
                wraith.pos = cell;
                GameScene.add(wraith);
                Dungeon.level.occupyCell(wraith);
                CellEmitter.floor(wraith.pos).start(ShadowParticle.UP, 0.01f, 30);
            }
        }
        protected void searchAndUse(){
            for (int i : PathFinder.FAR2TILES) for (int c : PathFinder.NEIGHBOURS25)if (Char.findChar(pos+i+c)!=null){
                if (Char.findChar(pos+i+c).alignment!=this.alignment) useAbility(pos+i+c);
            }
        }
    }
    public static class TotemHealing extends TowerTotem{
        //constant healing in a small range
        {
            spriteClass = TowerTotemSprite.THealing.class;
            abTimeMax = 1;
        }
        protected void useAbility(int cell){
            Char ch = Char.findChar(cell);
            if (ch.HP < ch.HT &&!(ch instanceof Arena.AmuletTower)){
                ch.HP++;
                CellEmitter.floor(ch.pos).start(CPHeal.UP, 0.005f, 10);
            }
        }
        protected void searchAndUse(){
            for (int i : PathFinder.NEIGHBOURS8) if (Char.findChar(pos+i)!=null){
                if (Char.findChar(pos+i).alignment==this.alignment) useAbility(pos+i);
            }
        }
    }
    public static class TotemShield extends TowerTotem{
        //gives shield in a large range
        {
            spriteClass = TowerTotemSprite.TShield.class;
            abTimeMax = 20;
        }
        protected void useAbility(int cell){
            Char ch = Char.findChar(cell);
            Buff.affect(ch, Barrier.class).setShield(50);
            CellEmitter.floor(ch.pos).start(CPShield.TOCENTER, 0.01f, 10);
        }
        protected void searchAndUse(){
            for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(pos+i)!=null){
                if (Char.findChar(pos+i).alignment==this.alignment && !(Char.findChar(pos+i) instanceof Arena.AmuletTower)) useAbility(pos+i);
            }
        }
    }
    public static class TotemAttack extends TowerTotem{
        //gives a bit of damage boost every 25  turns for 5 turns
        {
            spriteClass = TowerTotemSprite.TAttack.class;
            abTimeMax = 25;
        }
        protected void useAbility(int cell){
            Char ch = Char.findChar(cell);
            Buff.affect(ch, Battlecry.class, 12);
            CellEmitter.floor(ch.pos).start(CPRed.UP, 0.01f, 30);
        }
        protected void searchAndUse(){
            for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(pos+i)!=null){
                if (Char.findChar(pos+i).alignment==this.alignment) useAbility(pos+i);
            }
        }
    }


}
