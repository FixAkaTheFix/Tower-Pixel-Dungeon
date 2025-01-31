package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Watched extends FlavourBuff{
    {
        type = buffType.NEGATIVE;
    }

    //check amuletTower.startWave for usage

    @Override
    public int icon() {
        return BuffIndicator.WATCHED;
    }

    @Override
    public float iconFadePercent() {
        return 1;
    }

}
