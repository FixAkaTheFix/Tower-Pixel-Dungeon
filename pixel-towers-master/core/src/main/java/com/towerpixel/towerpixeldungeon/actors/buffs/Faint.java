package com.towerpixel.towerpixeldungeon.actors.buffs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.ui.BuffIndicator;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class Faint extends FlavourBuff{

    {
        type = Buff.buffType.POSITIVE;
        announced = true;
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
        hero.pos = ((Arena) Dungeon.level).amuletCell;
        Sample.INSTANCE.play( Assets.Sounds.MASTERY );
        CellEmitter.center(((Arena) Dungeon.level).amuletCell).burst( Speck.factory( Speck.STAR),  10 );
        super.onRemove();
    }

    @Override
    public int icon() {
        return BuffIndicator.FAINT;
    }
}
