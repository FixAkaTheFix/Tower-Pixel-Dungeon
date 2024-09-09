package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

public abstract class TowerCTotem extends TowerNotliving{

    public int abTime = 0;
    public int abTimeMax = 10;
    protected void useAbility(int cell){
        //
    }
    protected void searchAndUse(){
        //for (int i : PathFinder.NEIGHBOURS25) if (Char.findChar(pos+i)!=null){
        //    if ((effectPref == EffectPref.ALLIES&& Char.findChar(pos+i).alignment==this.alignment)||(effectPref == EffectPref.FOES&& Char.findChar(pos+i).alignment!=this.alignment)) useAbility(pos+i);
        //}
    }

    @Override
    protected boolean getCloser(int target) {
        return true;
    }

    @Override
    protected boolean getFurther(int target) {
        return true;
    }

    @Override
    protected boolean act() {
        spend(1f);
        abTime++;
        if(abTime>=abTimeMax){
            searchAndUse();
            abTime=0;
        }
        return true;
    }
}
