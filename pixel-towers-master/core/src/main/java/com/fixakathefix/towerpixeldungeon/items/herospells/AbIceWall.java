package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.LicteriaLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.IceWall;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.particles.LeafParticle;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbIceWall extends HeroSpellTargeted{
    {
        image = ItemSpriteSheet.HEROSPELL_ICEWALL;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {
                        IceWall wall = new IceWall();
                        wall.HP = wall.HT = 20 + (int)(Dungeon.depth*(Math.sqrt(Math.sqrt(Dungeon.depth)))*7);
                        wall.alignment = Char.Alignment.ALLY;
                        wall.pos = cell;
                        GameScene.add(wall);
                        Sample.INSTANCE.play(Assets.Sounds.SHATTER);
                        CellEmitter.floor(cell).start(MagicMissile.MagicParticle.FACTORY, 0.05f,30);
                        CellEmitter.floor(cell).start(MagicMissile.MagicParticle.FACTORY, 0.1f,10);
                        cooldown();
                    }
                }
            }

            @Override
            public String prompt () {
                return Messages.get(HeroSpellTargeted.class, "cell_choose");
            }
        };
    }



    @Override
    protected int castCooldown() {
        return 140;
    }
}
