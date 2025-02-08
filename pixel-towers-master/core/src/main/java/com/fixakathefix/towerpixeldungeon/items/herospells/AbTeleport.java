package com.fixakathefix.towerpixeldungeon.items.herospells;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.PriorityTarget;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class AbTeleport extends HeroSpellTargeted {

    public static final int DISTANCE_COOLDOWN = 2;

    {
        image = ItemSpriteSheet.HEROSPELL_TELEPORTATION;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {
                        Buff.affect(Dungeon.hero, AbilityCooldown.class, castCooldown() + DISTANCE_COOLDOWN*Dungeon.level.distance(Dungeon.hero.pos, cell));//cooldown depends on the distance
                        Dungeon.hero.pos = cell;
                        ScrollOfTeleportation.appear(Dungeon.hero, cell);
                        Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
                        Dungeon.level.occupyCell(hero);
                        Dungeon.observe();
                        GameScene.updateFog();
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
    public String info() {
        return desc() + "\n\n" + Messages.get(this, "cost", castCooldown(), DISTANCE_COOLDOWN);
    }



    @Override
    protected int castCooldown() {
        return 10;
    }
}
