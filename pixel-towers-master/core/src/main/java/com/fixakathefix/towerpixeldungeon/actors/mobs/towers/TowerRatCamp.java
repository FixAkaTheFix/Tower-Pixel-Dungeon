package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.particles.FlameParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerRatCampSprite;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public class TowerRatCamp extends TowerWave {

    //works like a summoning tower yet it is not.

    {
        spriteClass = TowerRatCampSprite.class;
        properties.add(Char.Property.IMMOVABLE);

        HT = HP = 100;

        state = PASSIVE;

        minionencodingsMax = 1;//00001
        minionencodingsCurrent=1;//00001

        viewDistance = 9;
    }



    public int levelCurrent = 1;
    public int levelMax = 5;

    private boolean champs = false;

    public void prepChamps(){
        champs = true;
    }


    @Override
    public String info() {
        StringBuilder info = new StringBuilder(description());

        int knives = minionencodingsCurrent % 10;
        int leaders = (minionencodingsCurrent/10) % 10;
        int shields = (minionencodingsCurrent/100)%10;
        int archers = (minionencodingsCurrent/1000)%10;
        int mages = (minionencodingsCurrent/10000)%10;
        if (knives>0) info.append("\n\n").append(Messages.get(TowerRatCamp.class,"knifecount", knives));
        if (leaders>0) info.append("\n\n").append(Messages.get(TowerRatCamp.class,"leadercount", leaders));
        if (shields>0) info.append("\n\n").append(Messages.get(TowerRatCamp.class,"shieldcount", shields));
        if (archers>0) info.append("\n\n").append(Messages.get(TowerRatCamp.class,"archercount", archers));
        if (mages>0) info.append("\n\n").append(Messages.get(TowerRatCamp.class,"magecount", mages));
        info.append(Messages.get(this, "descstats", levelCurrent, levelMax));
        if (champs) info.append(Messages.get(this, "champ"));
        return info.toString();
    }

    public static final String TOWERLEVELMAX = "towerlevelmax";
    public static final String TOWERLEVELCURRENT = "towerlevelcurrent";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(TOWERLEVELMAX, levelMax);
        bundle.put(TOWERLEVELCURRENT, levelCurrent);

    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        levelCurrent = bundle.getInt(TOWERLEVELCURRENT);
        levelMax = bundle.getInt(TOWERLEVELMAX);

    }

    @Override
    public boolean interact(Char c) {
        if (c == hero) {

            ArrayList<String> options = new ArrayList<>();
            options.add( Messages.get(TowerRatCamp.class, "knife", new CampRatKnife().cost));
            if (Dungeon.depth>=3) options.add(Messages.get(TowerRatCamp.class, "leader", new CampRatLeader().cost));
            if (Dungeon.depth>=6) options.add(Messages.get(TowerRatCamp.class, "shield", new CampRatShield().cost));
            if (Dungeon.depth>=8) options.add(Messages.get(TowerRatCamp.class, "archer", new CampRatArcher().cost));
            if (Dungeon.depth>=11) options.add(Messages.get(TowerRatCamp.class, "mage", new CampRatMage().cost));
            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndOptions(
                            new TowerRatCampSprite(),
                            Messages.get(TowerRatCamp.class, "wndname"),
                            Messages.get(TowerRatCamp.class, "wnddesc"),
                            options.toArray(new String[0])
                    ){
                        @Override
                        protected void onSelect(int index) {
                            super.onSelect(index);
                            int gold = 0;
                            int toadd = 0;
                            Mob rat;

                            switch (index) {
                                default: {
                                    rat = new TowerCrossbow1();
                                    break;
                                }
                                case 0: {
                                    rat = new CampRatKnife();
                                    toadd = 1;
                                    break;
                                }
                                case 1: {
                                    rat = new CampRatLeader();
                                    toadd = 10;
                                    break;
                                }
                                case 2: {
                                    rat = new CampRatShield();
                                    toadd = 100;
                                    break;
                                }
                                case 3: {
                                    rat = new CampRatArcher();
                                    toadd = 1000;
                                    break;
                                }
                                case 4: {
                                    rat = new CampRatMage();
                                    toadd = 10000;
                                    break;
                                }
                            }
                            if (rat instanceof CampRat) gold = ((CampRat) rat).cost;

                            if (levelCurrent >= levelMax) {
                                onBackPressed();
                                GLog.w(Messages.get(Tower.class, "no_upgrade"));
                            } else if (gold > Dungeon.gold) {
                                onBackPressed();
                                GLog.w(Messages.get(Tower.class, "no_gold", gold));
                            } else {
                                levelCurrent++;
                                minionencodingsCurrent += toadd;
                                Dungeon.gold -= gold;
                                sprite.update();
                                sprite.linkVisuals(TowerRatCamp.this);
                                Item.updateQuickslot();
                            }
                        }
                    });
                }

            });
            return true;
        } else return true;
    }

    public void summonOneMinion(Mob mob, ArrayList<Integer> candidates){
        if (!candidates.isEmpty()){
            mob.alignment = Alignment.ALLY;
            mob.pos = Random.element(candidates);
            GameScene.add(mob);
            Dungeon.level.occupyCell(mob);
            CellEmitter.floor(mob.pos).burst(FlameParticle.FACTORY, 10);
        }
    }

    public void spawnWaveMinions() {
        int knifes = minionencodingsCurrent % 10;
        int leaders = minionencodingsCurrent/10 % 10;
        int shields = minionencodingsCurrent/100%10;
        int archers = minionencodingsCurrent/1000%10;
        int mages = minionencodingsCurrent/10000%10;

        ArrayList<Integer> cand = new ArrayList<>();
        for (int i : PathFinder.NEIGHBOURS25){
            if (Char.findChar(pos + i)==null &&
                    level.passable[pos+i]) cand.add(pos + i);
        }

        for (int i = 0; i<knifes;i++){
            CampRatKnife rat = new CampRatKnife();
            if (champs) Buff.affect(rat, ChampionEnemy.Projecting.class);
            summonOneMinion(rat, cand);
        }
        for (int i = 0; i<leaders;i++){
            CampRatLeader rat = new CampRatLeader();
            if (champs) Buff.affect(rat, ChampionEnemy.Rejuvenating.class);
            summonOneMinion(rat, cand);
        }
        for (int i = 0; i<shields;i++){
            CampRatShield rat = new CampRatShield();
            if (champs) Buff.affect(rat, ChampionEnemy.Giant.class);
            summonOneMinion(rat, cand);
        }
        for (int i = 0; i<archers;i++){
            CampRatArcher rat = new CampRatArcher();
            if (champs) Buff.affect(rat, ChampionEnemy.Blazing.class);
            summonOneMinion(rat, cand);
        }
        for (int i = 0; i<mages;i++){
            CampRatMage rat = new CampRatMage();
            if (champs) Buff.affect(rat, ChampionEnemy.Blessed.class);
            summonOneMinion(rat, cand);
        }
        champs = false;
    }

    @Override
    public CharSprite sprite() { // changes the icon in the mob info window
        TowerRatCampSprite sprite = (TowerRatCampSprite) super.sprite();
        switch (levelCurrent){
            case 1: default: sprite.play(sprite.lvl1); break;
            case 2: sprite.play(sprite.lvl2); break;
            case 3: sprite.play(sprite.lvl3); break;
            case 4: sprite.play(sprite.lvl4); break;
            case 5: sprite.play(sprite.lvl5); break;
        }

        return sprite;
    }

}