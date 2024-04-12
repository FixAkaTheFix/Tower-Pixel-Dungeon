package com.towerpixel.towerpixeldungeon;

import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.windows.WndStyles;
import com.watabou.utils.DeviceCompat;

public class Styles {

    public enum Style{
        NORMAL,
        REDNEON,
        SEWER,
        PRISON,
        CAVERN,
        FIXED,
        MAGICLING;


        public String btName(){
            return Messages.get(WndStyles.class, this.name() + "_name");
        }
        public String desc(){
            return Messages.get(WndStyles.class, this.name() + "_desc");
        }

        @Override
        public String toString() {
            return Messages.get(Styles.class, this.name());
        }

        public boolean exists(Style style){
            boolean ex = false;
            if (style == NORMAL) ex = true;
            if (style == REDNEON) ex = true;
            if (style == SEWER) ex = true;
            if (style == PRISON) ex = true;
            if (style == CAVERN) ex = false;
            if (style == FIXED) ex = true;
            if (style == MAGICLING) ex = true;
            return ex;
        }

        public boolean condition(Style style){
            boolean con = false;
            if (style == NORMAL) con = true;
            if (style == REDNEON) con = true;
            if (style == SEWER) con = Badges.isUnlocked(Badges.Badge.OOZE_SLAIN);
            if (style == PRISON) con = Badges.isUnlocked(Badges.Badge.TENGU_SLAIN);
            if (style == CAVERN) con = false;
            if (style == FIXED) con = true;
            if (style == MAGICLING) con = true;
            if (DeviceCompat.isDebug()) return true;
            return con;
        }
        public String index(Style style){
            if (style == NORMAL)return "normal";
            if (style == REDNEON)return "redneon";
            if (style == SEWER)return "sewer";
            if (style == PRISON)return "prison";
            if (style == CAVERN)return "cavern";
            if (style == FIXED) return "fixed";
            if (style == MAGICLING) return "magicling";
            return "normal";
        }
    }

}
