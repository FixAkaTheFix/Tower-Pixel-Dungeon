package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.levels.ArenaColiseumBalancingTable.mobSpecialty.*;

import com.fixakathefix.towerpixeldungeon.actors.mobs.Albino;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ArmoredBrute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossNecromancer;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossRatKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossTengu;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Brute;
import com.fixakathefix.towerpixeldungeon.actors.mobs.CausticSlime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.ChiefRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Crab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.FetidRat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Gnoll;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollBlind;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollThrower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTrickster;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GnollTwilight;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Goo;
import com.fixakathefix.towerpixeldungeon.actors.mobs.GreatCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.MagiCrab;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Shaman;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Slime;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Spinner;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Thief;

public enum ArenaColiseumBalancingTable {

    //approx

    RAT(10, SPAM, new Rat()),
    ALBINO(16, SPAM, new Albino()),
    GNOLLBLIND(10, SPAM, new GnollBlind()),
    GNOLL(20, SPAM, new Gnoll()),
    SNAKE(20, SPAM, new Snake()),
    THIEF(40, SPAM, new Thief()),
    BANDIT(60, SPAM, new Bandit()),
    BAT(160, SPAM, new Bat()),

    MAGICRAB(100, WARRIOR, new MagiCrab()),
    SPIDER(200, WARRIOR, new Spinner()),


    CRAB(30, TANK, new Crab()),
    SLIME(40, TANK, new Slime()),
    CAUSTICSLIME(50, TANK, new CausticSlime()),
    BRUTE(200, TANK, new Brute()),
    GOO(250, TANK, new Goo()),
    SHIELDEDBRUTE(300, TANK, new ArmoredBrute()),
    GREATCRAB(400, TANK, new GreatCrab()),

    FETIDRAT(50, SUPPORT, new FetidRat()),
    BOSSNECROMANCER(3000, SUPPORT, new BossNecromancer()),


    GNOLLTHROWER(25, RANGED, new GnollThrower()),
    GNOLLTRICKSTER(100, RANGED, new GnollTrickster()),
    SHAMANRED(120, RANGED, new Shaman.RedShaman()),
    SHAMANBLUE(130,RANGED, new Shaman.BlueShaman()),
    SHAMANPURPLE(150,RANGED, new Shaman.PurpleShaman()),
    GNOLLTWILIGHT(200, RANGED, new GnollTwilight()),


    CHIEFRAT(100, CHAMPION, new ChiefRat()),
    BOSSRATKING(800, CHAMPION, new BossRatKing()),
    BOSSOOZE(5000, CHAMPION, new BossOoze()),
    BOSSTENGU(7000, CHAMPION, new BossTengu());

    public Mob mob;
    public int powerLevel;
    public mobSpecialty specialty;

    ArenaColiseumBalancingTable(int powerLevel, mobSpecialty specialty, Mob mob){
        this.powerLevel = powerLevel; this.specialty = specialty;
    }
    enum mobSpecialty{
        SPAM, //weak at everything and cheap

        WARRIOR,//have decent attacking capabilities, being able to ignore damage, having high AT,
        TANK,//have high hp, so can just push
        RANGED,//are able to strike from afar
        AREA,//are able to make area hits and fight groups
        SUPPORT,//have cursing or supporting spells

        CHAMPION//are expensive, tough and interesting to fight, have multiple attacks and several capabilities. Are universal.
    }
}
