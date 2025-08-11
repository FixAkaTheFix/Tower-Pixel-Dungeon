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
import com.fixakathefix.towerpixeldungeon.sprites.ItemSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.ObeliskNecroticSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AbObelisk extends HeroSpellTargeted{
    enum ObEnergy{
        BLOOD,
        COLD,
        DECAY
    }

    {
        image = ItemSpriteSheet.HEROSPELL_OBELISK;


        cellCaster = new CellSelector.Listener() {
            @Override
            public void onSelect(Integer cell) {
                if (cell != null) {
                    if (Char.findChar(cell) == null && Dungeon.level.passable[cell] && !Dungeon.level.pit[cell] && Dungeon.hero.fieldOfView[cell]) {

                        Mob obelisk = energy == ObEnergy.BLOOD ? new ObeliskBloodstone() : energy == ObEnergy.COLD ? new ObeliskPermafrost() : new ObeliskNecrotic();
                        obelisk.alignment = Char.Alignment.ALLY;
                        obelisk.pos = cell;
                        GameScene.add(obelisk);
                        Sample.INSTANCE.play(Assets.Sounds.SHATTER);
                        CellEmitter.floor(cell).burst(ShadowParticle.CURSE, 10);
                        CellEmitter.floor(cell).start(ShadowParticle.CURSE, 0.2f, 10);
                        cooldown();
                        if (energy == ObEnergy.BLOOD) energy = Random.oneOf(ObEnergy.COLD, ObEnergy.DECAY);
                        else if (energy == ObEnergy.COLD) energy = Random.oneOf(ObEnergy.BLOOD, ObEnergy.DECAY);
                        else if (energy == ObEnergy.DECAY) energy = Random.oneOf(ObEnergy.COLD, ObEnergy.BLOOD);
                        updateQuickslot();
                    }
                }
            }

            @Override
            public String prompt () {
                return Messages.get(HeroSpellTargeted.class, "cell_choose");
            }
        };
    }

    private ObEnergy energy = ObEnergy.BLOOD;

    @Override
    public ItemSprite.Glowing glowing() {
        ItemSprite.Glowing glowing;
        switch (energy) {
            default: {
                return null;
            }
            case BLOOD: {
                glowing = new ItemSprite.Glowing(0xAA0000);
            } break;
            case COLD: {
                glowing = new ItemSprite.Glowing(0xAAAAFF);
            } break;
            case DECAY:{
                glowing = new ItemSprite.Glowing(0x00FF00);
            } break;
        }
        return glowing;
    }

    @Override
    public String info() {
        String finalinfo = super.info();
        switch (energy) {
            case BLOOD: {
                finalinfo+= "\n\n" + Messages.get(AbObelisk.class, "energy_blood");
            } break;
            case COLD: {
                finalinfo+= "\n\n" + Messages.get(AbObelisk.class, "energy_cold");
            } break;
            case DECAY:{
                finalinfo+= "\n\n" + Messages.get(AbObelisk.class, "energy_decay");
            } break;
        }
        return finalinfo;
    }

    private static final String ENERGY = "energy";

    @Override
    public void storeInBundle(Bundle bundle) {
        bundle.put(ENERGY, energy);
        super.storeInBundle(bundle);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        energy = bundle.getEnum(ENERGY, ObEnergy.class);
        super.restoreFromBundle(bundle);
    }

    @Override
    protected int castCooldown() {
        return 150;
    }
}
