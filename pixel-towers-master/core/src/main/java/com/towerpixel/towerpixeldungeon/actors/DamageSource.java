package com.towerpixel.towerpixeldungeon.actors;

import com.towerpixel.towerpixeldungeon.actors.blobs.Electricity;
import com.towerpixel.towerpixeldungeon.actors.blobs.Fire;
import com.towerpixel.towerpixeldungeon.actors.blobs.ToxicGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Burning;
import com.towerpixel.towerpixeldungeon.actors.buffs.Charm;
import com.towerpixel.towerpixeldungeon.actors.buffs.Chill;
import com.towerpixel.towerpixeldungeon.actors.buffs.Corrosion;
import com.towerpixel.towerpixeldungeon.actors.buffs.Degrade;
import com.towerpixel.towerpixeldungeon.actors.buffs.Frost;
import com.towerpixel.towerpixeldungeon.actors.buffs.Hex;
import com.towerpixel.towerpixeldungeon.actors.buffs.Hunger;
import com.towerpixel.towerpixeldungeon.actors.buffs.MagicalSleep;
import com.towerpixel.towerpixeldungeon.actors.buffs.Ooze;
import com.towerpixel.towerpixeldungeon.actors.buffs.Paralysis;
import com.towerpixel.towerpixeldungeon.actors.buffs.Poison;
import com.towerpixel.towerpixeldungeon.actors.buffs.Vulnerable;
import com.towerpixel.towerpixeldungeon.actors.buffs.Weakness;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.duelist.ElementalStrike;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.mage.ElementalBlast;
import com.towerpixel.towerpixeldungeon.actors.hero.abilities.mage.WarpBeacon;
import com.towerpixel.towerpixeldungeon.actors.mobs.DM100;
import com.towerpixel.towerpixeldungeon.actors.mobs.DMWHead;
import com.towerpixel.towerpixeldungeon.actors.mobs.Eye;
import com.towerpixel.towerpixeldungeon.actors.mobs.MagicShard;
import com.towerpixel.towerpixeldungeon.actors.mobs.Shaman;
import com.towerpixel.towerpixeldungeon.actors.mobs.Warlock;
import com.towerpixel.towerpixeldungeon.actors.mobs.YogFist;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDartgunSpitter;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerDisintegration3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerLightning3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerPylon;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand1;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand2;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWand3;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWandFireball;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWandLightning;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerWandPrismatic;
import com.towerpixel.towerpixeldungeon.items.bombs.Bomb;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfSkulls;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.towerpixel.towerpixeldungeon.items.scrolls.exotic.ScrollOfDemonicSkull;
import com.towerpixel.towerpixeldungeon.items.wands.CursedWand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfCorruption;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfDisintegration;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfFireblast;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfFrost;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLightning;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfLivingEarth;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfMagicMissile;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfPrismaticLight;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfTransfusion;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfWarding;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Blazing;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Grim;
import com.towerpixel.towerpixeldungeon.items.weapon.enchantments.Shocking;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.Greatshield;
import com.towerpixel.towerpixeldungeon.items.weapon.melee.RunicBlade;
import com.towerpixel.towerpixeldungeon.levels.traps.DisintegrationTrap;
import com.towerpixel.towerpixeldungeon.levels.traps.GrimTrap;

import java.util.HashSet;

public class DamageSource {

    public static final HashSet<Class> MAGICAL = new HashSet<>();

    static {
        MAGICAL.add( MagicalSleep.class );
        MAGICAL.add( Charm.class );
        MAGICAL.add( Weakness.class );
        MAGICAL.add( Vulnerable.class );
        MAGICAL.add( Hex.class );
        MAGICAL.add( Degrade.class );

        MAGICAL.add( DisintegrationTrap.class );
        MAGICAL.add( GrimTrap.class );

        MAGICAL.add( Bomb.MagicalBomb.class );

        MAGICAL.add( ScrollOfSkulls.class );
        MAGICAL.add( ScrollOfDemonicSkull.class );
        MAGICAL.add( ScrollOfTeleportation.class );

        MAGICAL.add( Greatshield.class);

        MAGICAL.add( ElementalBlast.class );
        MAGICAL.add( CursedWand.class );
        MAGICAL.add( WandOfBlastWave.class );
        MAGICAL.add( WandOfDisintegration.class );
        MAGICAL.add( WandOfFireblast.class );
        MAGICAL.add( WandOfFrost.class );
        MAGICAL.add( WandOfLightning.class );
        MAGICAL.add( WandOfLivingEarth.class );
        MAGICAL.add( WandOfMagicMissile.class );
        MAGICAL.add( WandOfPrismaticLight.class );
        MAGICAL.add( WandOfTransfusion.class );
        MAGICAL.add( WandOfWarding.Ward.class );
        MAGICAL.add(RunicBlade.class);
        MAGICAL.add(WandOfCorruption.class);

        MAGICAL.add( ElementalStrike.class );
        MAGICAL.add( Blazing.class );
        MAGICAL.add( Shocking.class );
        MAGICAL.add( Grim.class );

        MAGICAL.add( WarpBeacon.class );

        MAGICAL.add( DM100.LightningBolt.class );
        MAGICAL.add( DMWHead.LightningBolt.class );
        MAGICAL.add( Shaman.EarthenBolt.class );
        MAGICAL.add( Warlock.DarkBolt.class );
        MAGICAL.add( Eye.DeathGaze.class );
        MAGICAL.add( YogFist.BrightFist.LightBeam.class );
        MAGICAL.add( YogFist.DarkFist.DarkBolt.class );
        MAGICAL.add(MagicShard.class);

        MAGICAL.add(TowerWand1.class);
        MAGICAL.add(TowerWand2.class);
        MAGICAL.add(TowerWand3.class);
        MAGICAL.add(TowerWandLightning.class);
        MAGICAL.add(TowerWandFireball.class);
        MAGICAL.add(TowerWandPrismatic.class);

        MAGICAL.add(TowerPylon.class);
        MAGICAL.add(TowerLightning1.class);
        MAGICAL.add(TowerLightning2.class);
        MAGICAL.add(TowerLightning3.class);
        MAGICAL.add(TowerDisintegration1.class);
        MAGICAL.add(TowerDisintegration2.class);
        MAGICAL.add(TowerDisintegration3.class);


    }

    public static final HashSet<Class> DRIGNORING = new HashSet<>();//NOT IMPLEMENTED YET
    static {
    }

    public static final HashSet<Class> PHYSICAL = new HashSet<>();//OTHER STUFF. NOT USED.
    static {
    }

    public static final HashSet<Class> SHIELDIGNORING = new HashSet<>();
    static {
        SHIELDIGNORING.add(Hunger.class);
    }

    public static final HashSet<Class> PROJECTILE = new HashSet<>();//NOT IMPLEMENTED YET
    static {
    }


    public static final HashSet<Class> ELEMENTAL = new HashSet<>();
    static {
        ELEMENTAL.add( Burning.class );
        ELEMENTAL.add( Fire.class );
        ELEMENTAL.add( Chill.class );
        ELEMENTAL.add( Frost.class );
        ELEMENTAL.add( Ooze.class );
        ELEMENTAL.add( Paralysis.class );
        ELEMENTAL.add( Poison.class );
        ELEMENTAL.add( Corrosion.class );
        ELEMENTAL.add( ToxicGas.class );
        ELEMENTAL.add( Electricity.class );
        ELEMENTAL.add( WandOfFireblast.class );
        ELEMENTAL.add( Blazing.class);
        ELEMENTAL.add( WandOfFrost.class );
        ELEMENTAL.add( Shocking.class );
        ELEMENTAL.add( DMWHead.LightningBolt.class );
        ELEMENTAL.add( DM100.LightningBolt.class );
        ELEMENTAL.add( WandOfLightning.class );
        ELEMENTAL.add(TowerPylon.class);
        ELEMENTAL.add(TowerLightning1.class);
        ELEMENTAL.add(TowerLightning2.class);
        ELEMENTAL.add(TowerLightning3.class);


    }
    public static final HashSet<Class> FIRE = new HashSet<>();
    static {
        FIRE.add( Burning.class );
        FIRE.add( WandOfFireblast.class );
        FIRE.add( Blazing.class);
        FIRE.add( Fire.class );
    }
    public static final HashSet<Class> ICE = new HashSet<>();
    static {
        ICE.add( Chill.class );
        ICE.add( Frost.class );
        ICE.add( WandOfFrost.class );
    }
    public static final HashSet<Class> LIGHTNING = new HashSet<>();
    static {
        LIGHTNING.add( Shocking.class );
        LIGHTNING.add( Electricity.class );
        LIGHTNING.add( DMWHead.LightningBolt.class );
        LIGHTNING.add( DM100.LightningBolt.class );
        LIGHTNING.add( WandOfLightning.class );
        LIGHTNING.add( TowerPylon.LightningBolt.class );
        LIGHTNING.add(TowerLightning1.class);
        LIGHTNING.add(TowerLightning2.class);
        LIGHTNING.add(TowerLightning3.class);
    }
    public static final HashSet<Class> POISON = new HashSet<>();
    static {
        POISON.add(Poison.class);
        POISON.add(ToxicGas.class);
        POISON.add(Corrosion.class);
        POISON.add(TowerDartgunSpitter.class);
    }
}
