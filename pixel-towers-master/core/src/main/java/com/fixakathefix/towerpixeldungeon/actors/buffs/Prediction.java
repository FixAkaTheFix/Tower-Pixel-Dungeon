package com.fixakathefix.towerpixeldungeon.actors.buffs;

import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;

public class Prediction extends FlavourBuff{
    {
        type = buffType.POSITIVE;
    }

    //check amuletTower.startWave for usage

    @Override
    public int icon() {
        return BuffIndicator.PREDICTION;
    }

    @Override
    public float iconFadePercent() {
        return 1;
    }

}
