package com.fixakathefix.towerpixeldungeon.items.herospells;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AbilityCooldown;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.fixakathefix.towerpixeldungeon.effects.Lightning;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AbTrLightningStrike extends HeroSpellTargeted {
    private static int MIN_COST = 10;

    private static int mindam(){
        if (hero == null) return 7;
        return hero.lvl  + 5;
    }
    private static int maxdam(){
        if (hero == null) return 15;
        return hero.lvl * 3 + 9;
    }

    {
        image = ItemSpriteSheet.HEROSPELL_TR_ORBITALLIGHTNING;

        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    ArrayList<Lightning.Arc> arcs = new ArrayList<>();
                    PointF source = DungeonTilemap.tileCenterToWorld(cell);
                    source.y-=200;
                    PointF dest = Char.findChar(cell)== null ? DungeonTilemap.raisedTileCenterToWorld(cell) : DungeonTilemap.tileCenterToWorld(cell);
                    Sample.INSTANCE.play(Assets.Sounds.LIGHTNING, 2f, 0.8f);
                    arcs.add(new Lightning.Arc(source, dest));
                    Dungeon.hero.sprite.parent.addToFront(new Lightning(arcs, null));
                    WandOfBlastWave.BlastWave.blast(cell);
                    Char ch = Char.findChar(cell);
                    if (ch!=null){
                        ch.damage(Random.Int(mindam(), maxdam()), AbTrLightningStrike.this);
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
        return desc() + "\n\n" + Messages.get(this, "damage", mindam(), maxdam()) + "\n\n" + Messages.get(this, "cost", castCooldown(), MIN_COST);
    }

    @Override
    protected int castCooldown() {
        int lightcost = 0;
        for (Mob mob : Dungeon.level.mobs){
            if (mob instanceof TowerLightning1){
                lightcost += ((Tower)mob).cost;
            }
        }
        return (int)Math.max(100 - Math.sqrt(lightcost), 10);
    }
}
