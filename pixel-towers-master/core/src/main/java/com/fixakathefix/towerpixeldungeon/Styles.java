package com.fixakathefix.towerpixeldungeon;

import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.windows.WndStyles;
import com.watabou.utils.DeviceCompat;

public class Styles {

    public enum Style{
        NORMAL,
        REDNEON,
        SEWER,
        PRISON,
        FIXED,
        MAGICLING,
        CLAIRVOYANT,
        YELLOWNEON,
        GREENNEON,
        BLUENEON,
        PURPLENEON;




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
            boolean ex = true;
            if (style == NORMAL) ex = true;
            if (style == REDNEON) ex = true;
            if (style == SEWER) ex = true;
            if (style == PRISON) ex = true;
            if (style == FIXED) ex = true;
            if (style == MAGICLING) ex = true;
            return ex;
        }

        public boolean condition(Style style){
            boolean con = false;
            if (style == NORMAL) con = true;
            if (style == REDNEON) con = true;
            if (style == YELLOWNEON) con = SPDSettings.maxlevelunlockedChalmode()>=5;
            if (style == GREENNEON) con = SPDSettings.maxlevelunlockedChalmode()>=11;
            if (style == BLUENEON) con = SPDSettings.maxlevelunlockedChalmode()>=15;
            if (style == PURPLENEON) con = SPDSettings.maxlevelunlockedChalmode()>=20;
            if (style == SEWER) con = SPDSettings.maxlevelunlockedHardmode()>=6;
            if (style == PRISON) con = SPDSettings.maxlevelunlockedHardmode()>=11;
            if (style == FIXED) con = SPDSettings.maxlevelunlocked()>=6;
            if (style == MAGICLING) con = SPDSettings.maxlevelunlocked()>=11;
            if (style == CLAIRVOYANT) con = SPDSettings.maxlevelunlocked()>=16;
            if (DeviceCompat.isDebug()) return true;
            return con;
        }
        public String index(Style style){
            if (style == NORMAL)return "normal";
            if (style == REDNEON)return "redneon";
            if (style == YELLOWNEON)return "yellowneon";
            if (style == GREENNEON)return "greenneon";
            if (style == BLUENEON)return "blueneon";
            if (style == PURPLENEON)return "purpleneon";
            if (style == SEWER)return "sewer";
            if (style == PRISON)return "prison";
            if (style == FIXED) return "fixed";
            if (style == MAGICLING) return "magicling";
            if (style == CLAIRVOYANT) return "clairvoyant";
            return "normal";
        }
    }

}
