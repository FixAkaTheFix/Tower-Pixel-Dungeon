package com.fixakathefix.towerpixeldungeon.actors.buffs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class Faint extends FlavourBuff{

    {
        type = Buff.buffType.POSITIVE;
        announced = true;
    }

    @Override
    public boolean attachTo( Char target ) {
        if (super.attachTo( target )) {
            target.paralysed++;
            target.invisible++;
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void detach() {
        super.detach();
        if (target.paralysed > 0)
            target.paralysed--;
        if (target.invisible > 0)
            target.invisible--;
    }



    @Override
    protected void onRemove() {
        hero.sprite.play(hero.sprite.idle, true);
        Camera.main.panFollow(hero.sprite,1f);
        hero.sprite.move(hero.pos, ((Arena) Dungeon.level).amuletCell);
        hero.paralysed = 0;
        hero.invisible = 0;
        GameScene.scene.menu.active = GameScene.scene.menu.visible = true;
        hero.sprite.play(hero.sprite.idle);
        int portalpos = ((Arena)level).amuletCell;
        for (Mob m : level.mobs){
            if (m instanceof Arena.AmuletTower) portalpos = m.pos;
        }
        if (portalpos > 1) hero.pos = portalpos;
        else hero.pos = ((Arena)level).amuletCell;
        Sample.INSTANCE.play( Assets.Sounds.MASTERY );
        CellEmitter.center(hero.pos).burst( Speck.factory( Speck.STAR),  10 );
        super.onRemove();
    }

    @Override
    public int icon() {
        return BuffIndicator.FAINT;
    }


}
