package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.LicteriaLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskBloodstone;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskNecrotic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.ObeliskPermafrost;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.LeafParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShadowParticle;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.ObeliskNecroticSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class AbObelisk extends HeroSpellTargeted{
    {
        image = ItemSpriteSheet.HEROSPELL_OBELISK;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {
                        Mob obelisk = Random.oneOf(new ObeliskBloodstone(), new ObeliskNecrotic(), new ObeliskPermafrost());
                        obelisk.alignment = Char.Alignment.ALLY;
                        obelisk.pos = cell;
                        GameScene.add(obelisk);
                        Sample.INSTANCE.play(Assets.Sounds.SHATTER);
                        CellEmitter.floor(cell).burst(ShadowParticle.CURSE, 10);
                        CellEmitter.floor(cell).start(ShadowParticle.CURSE, 0.2f, 10);
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
        return 166;
    }
}
