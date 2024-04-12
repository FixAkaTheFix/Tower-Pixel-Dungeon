package com.towerpixel.towerpixeldungeon.levels;

import static com.towerpixel.towerpixeldungeon.levels.ArenaEndlessBalancingTable.mobSpecialty.*;

public enum ArenaEndlessBalancingTable {

    RAT(10, SPAM),
    ALBINO(16, SPAM),
    GNOLLBLIND(10, SPAM),
    GNOLL(20, SPAM),

    SNAKE(20, WARRIOR),
    MAGICRAB(100, WARRIOR),
    SPIDER(200, WARRIOR),


    CRAB(30, TANK),
    SLIME(40, TANK),
    CAUSTICSLIME(50, TANK),
    BRUTE(200, TANK),
    GOO(250, TANK),
    SHIELDEDBRUTE(300, TANK),
    GREATCRAB(400, TANK),

    FETIDRAT(50, SUPPORT),
    BOSSNECROMANCER(3000, SUPPORT),


    GNOLLTHROWER(25, RANGED),
    GNOLLTRICKSTER(100, RANGED),
    SHAMANRED(120, RANGED),
    SHAMANBLUE(130,RANGED),
    SHAMANPURPLE(150,RANGED),
    GNOLLTWILIGHT(200, RANGED),


    CHIEFRAT(100, CHAMPION),
    BOSSRATKING(300, SUPPORT),

    BOSSOOZE(5000, CHAMPION);

    public int powerLevel;
    public mobSpecialty specialty;
    ArenaEndlessBalancingTable(int powerLevel, mobSpecialty specialty){
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
