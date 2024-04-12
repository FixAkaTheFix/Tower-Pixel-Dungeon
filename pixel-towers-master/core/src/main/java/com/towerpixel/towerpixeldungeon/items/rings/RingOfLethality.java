package com.towerpixel.towerpixeldungeon.items.rings;

import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.messages.Messages;

public class RingOfLethality extends Ring {
    public String statsInfo() {
        if (isIdentified()){
            return Messages.get(this, "stats", Messages.decimalFormat("#.##", 100f * (0.05f+ 0.03f*soloBuffedBonus())), Messages.decimalFormat("#.##", 100f * (0.2f+0.1*soloBuffedBonus())));
        } else {
            return Messages.get(this, "typical_stats", 5, 20);
        }
    }

    @Override
    protected Ring.RingBuff buff( ) {
        return new Lethality();
    }

    public static float critChanceAdd( Char target ){
        if (getBuffedBonus(target, RingOfLethality.Lethality.class)==0) {
            return 0; // because the ranking system is additional, the main value must be equal to 0 (without a ring equipped)
        }
        else return 0.05f + 0.03f*getBuffedBonus(target, RingOfLethality.Lethality.class);
    }
    public static float critMultAdd( Char target ){
        if (getBuffedBonus(target, RingOfLethality.Lethality.class)==0) {
            return 0; // because the ranking system is additional, the main value must be equal to 0 (without a ring equipped)
        } else return 0.2f + 0.1f*getBuffedBonus(target, RingOfLethality.Lethality.class);
    }

    public class Lethality extends Ring.RingBuff {
    }
}
