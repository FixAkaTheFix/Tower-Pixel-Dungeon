package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.actors.mobs.*;

import java.util.ArrayList;

public class ArenaEndless extends Arena {
    public static final ArrayList<Class<? extends Mob>> spamMobs = new ArrayList<Class<? extends Mob>>(){
            {
                add(Rat.class);
            }
    };
}
