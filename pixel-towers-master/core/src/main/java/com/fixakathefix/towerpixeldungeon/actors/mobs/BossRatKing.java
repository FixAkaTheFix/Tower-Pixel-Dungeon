package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Paralysis;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.effects.Pushing;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossRatKingSprite;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossRatKing extends Rat{
    {
        spriteClass = BossRatKingSprite.class;

        HP = HT = 200;
        defenseSkill = 1;

        viewDistance = 10;

        properties.add(Property.BOSS);

        immunities.add( Paralysis.class );
    }
    private boolean dead = false;

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    protected boolean act() {
        if (dead){
            return true;
        }
        int actChoose = Random.Int(10);
        switch (actChoose) {
            case 0: default:
                return super.act();
            case 1: {
                try {
                    ArrayList<Integer> candidates = new ArrayList<>();
                    for (int n : PathFinder.NEIGHBOURS25) {
                        if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                            candidates.add(pos + n);
                        }
                    }
                    int newpos = Random.element(candidates);
                    spend(1);
                    sprite.jump(pos, newpos, 10, 0.2f, new Callback() {
                        @Override
                        public void call() {
                            Dungeon.level.occupyCell(BossRatKing.this);
                            pos = newpos;
                            next();
                        }
                    });
                    Dungeon.level.occupyCell(this);

                } catch (Exception ignored) {
                }
            return false;
            }
            case 2: case 4: case 5:{

                ArrayList<Integer> candidates = new ArrayList<>();
                for (int n : PathFinder.NEIGHBOURS8) {
                    if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                        candidates.add(pos + n);
                    }
                }

                if (!candidates.isEmpty()) {
                    if (Dungeon.depth==2) ((Arena)Dungeon.level).deploymobs(1, Arena.Direction.RANDOM, 1);
                    spend(1);
                    Albino spawn = new Albino();


                    spawn.pos = Random.element(candidates);
                    spawn.state = spawn.HUNTING;

                    GameScene.add(spawn, 0);
                    Dungeon.level.occupyCell(spawn);

                    if (sprite.visible) {
                        Actor.addDelayed(new Pushing(spawn, pos, spawn.pos), -1);
                    }
                    int i = Random.Int(3);
                    switch (i) {
                        case 0: {
                            speak("GET THEM, RODENT", CharSprite.WARNING);
                            break;
                        }
                        case 1: {
                            speak("RODENTS, ATTACK!!!", CharSprite.WARNING);
                            break;
                        }
                        case 2: {
                            speak("RATS GET THINGS DONE", CharSprite.WARNING);
                            break;
                        }
                        case 3: {
                            speak("RATS DON'T LEAVE THEIR OWN", CharSprite.WARNING);
                            break;
                        }
                    }

                }
                return true;
            }
            case 3:{
                for (Mob mob : Dungeon.level.mobs){
                    if (mob instanceof Tower) Buff.affect(mob, Paralysis.class, 1);
                }
                int i = Random.Int(2);
                switch (i) {
                    case 0: {
                        speak("YOUR TOWERS ARE USELESS", CharSprite.WARNING);
                        break;
                    }
                    case 1: {
                        speak("NOW, ATTACK", CharSprite.WARNING);
                        break;
                    }
                    case 2: {
                        speak("SABOTAGE!!!", CharSprite.WARNING);
                        break;
                    }
                }
            }
        }
        return super.act();
    }

    @Override
    public int damageRoll() {
        return Random.NormalIntRange(10,25);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (Random.Int(2) == 1) {
            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                    candidates.add(pos + n);
                }
            }

            if (!candidates.isEmpty()) {
                int posi;
                posi = Random.element(candidates);

                this.sprite.jump(pos, posi, new Callback() {
                    @Override
                    public void call() {
                        Dungeon.level.occupyCell(BossRatKing.this);
                        pos = posi;
                        int i = Random.Int(6);
                        switch (i) {
                            case 0: {
                                speak("MISSED!", CharSprite.WARNING);
                                break;
                            }
                            case 1: {
                                speak("NOPE!", CharSprite.WARNING);
                                break;
                            }
                            case 2: {
                                speak("DODGE!", CharSprite.WARNING);
                                break;
                            }
                            case 3: {
                                speak("CRY ABOUT IT!", CharSprite.WARNING);
                                break;
                            }
                            case 4: {
                                speak("ARE YOU BLIND???", CharSprite.WARNING);
                                break;
                            }
                            case 5: {
                                speak("SKILL ISSUE!", CharSprite.WARNING);
                                break;
                            }
                        }
                    }
                });
                Sample.INSTANCE.play(Assets.Sounds.MISS);
                return 0;
            }

        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die(Object cause) {
        Badges.validateEnemy(this);
        baseSpeed = 45454545f;
        dead = true;//Stops act(): if He is not "dead", he will continue acting, summoning and attacking because of die() method being implemented in a visuals callback
        int newpos = pos +5;
        if (pos + 20< Dungeon.level.width()*Dungeon.level.height()) newpos = pos+20;
        else if (pos - 20 > 0) newpos = pos - 20;
        speak("NO. A TRUE KING NEVER DIES", CharSprite.WARNING);
        spend(1000);
        int finalNewpos = newpos;
        sprite.jump(pos, pos, 0, 1f, new Callback() {
            @Override
            public void call() {
                sprite.jump(pos, finalNewpos, 0, 4f, new Callback() {
                @Override
                public void call() {
                    Dungeon.level.occupyCell(BossRatKing.this);
                }
            });
                speak("I WILL BE BACK", CharSprite.WARNING);
                Dungeon.level.occupyCell(BossRatKing.this);
                Dungeon.level.map[finalNewpos] = Terrain.EMPTY;
                next();
                BossRatKing.super.die(cause);
            }
        });

    }
}
