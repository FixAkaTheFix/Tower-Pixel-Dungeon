package com.fixakathefix.towerpixeldungeon.items.herospells;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;

public abstract class HeroSpellTargeted extends HeroSpell {
    {
        usesTargeting = true;
    }

    public CellSelector.Listener cellCaster = new CellSelector.Listener(){
        @Override
        public void onSelect(Integer cell) {
            ///
        }
        @Override
        public String prompt() {
            return null;
        }
    };
    @Override
    public void cast() {
        curUser = Dungeon.hero;
        curItem = this;
        GameScene.selectCell(cellCaster);
    }

    protected void activate(int cell){
        //nothing by def
    }

}
