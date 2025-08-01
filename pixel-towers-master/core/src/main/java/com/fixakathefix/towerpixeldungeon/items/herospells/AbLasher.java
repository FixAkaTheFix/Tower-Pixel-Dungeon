package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Regrowth;
import com.fixakathefix.towerpixeldungeon.actors.mobs.LicteriaLasher;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.LeafParticle;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbLasher extends HeroSpellTargeted{
    {
        image = ItemSpriteSheet.HEROSPELL_LASHER;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {
                        LicteriaLasher lashy = new LicteriaLasher();
                        lashy.alignment = Char.Alignment.ALLY;
                        lashy.pos = cell;
                        GameScene.add(lashy);
                        Sample.INSTANCE.play(Assets.Sounds.GRASS);
                        CellEmitter.floor(cell).start(LeafParticle.GENERAL, 0.05f,20);
                        CellEmitter.floor(cell).start(LeafParticle.GENERAL, 0.1f,15);
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
        return 150;
    }
}
