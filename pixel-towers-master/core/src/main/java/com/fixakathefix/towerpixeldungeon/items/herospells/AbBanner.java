package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Banner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.IceWall;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbBanner extends HeroSpellTargeted{
    {
        image = ItemSpriteSheet.HEROSPELL_BANNER;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {
                        Banner banner = new Banner();
                        banner.HP = banner.HT = 10 + Dungeon.hero.lvl*5;
                        banner.alignment = Char.Alignment.ALLY;
                        banner.pos = cell;
                        GameScene.add(banner);
                        Sample.INSTANCE.play(Assets.Sounds.MASTERY);
                        CellEmitter.floor(cell).burst(MagicMissile.MagicParticle.ATTRACTING, 20);
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
        return 100;
    }
}
