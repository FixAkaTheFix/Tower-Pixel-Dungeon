package com.fixakathefix.towerpixeldungeon.actors.mobs;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.Ghost;
import com.fixakathefix.towerpixeldungeon.effects.Pushing;
import com.fixakathefix.towerpixeldungeon.items.potions.exotic.PotionOfCleansing;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.CharSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ChiefRatSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ChiefRat extends Rat {

    {
        spriteClass = ChiefRatSprite.class;

        HP = HT = 60;
        defenseSkill = 5;

        EXP = 7;

        viewDistance = 10;

        state = WANDERING;

        properties.add(Property.MINIBOSS);
    }

    @Override
    public int attackSkill( Char target ) {
        return 12;
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange(0, 2);
    }

    @Override
    public int defenseProc( Char enemy, int damage ) {
        if (buff(PotionOfCleansing.Cleanse.class)!=null){
            return super.defenseProc(enemy, damage);
        }
        if (Random.Int(2) == 1 && buff(PotionOfCleansing.Cleanse.class)==null) {

            ArrayList<Integer> candidates = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                    candidates.add(pos + n);
                }
            }

            if (!candidates.isEmpty()) {
                Rat spawn = new Rat();
                spawn.alignment = this.alignment;

                spawn.pos = Random.element(candidates);
                spawn.state = spawn.HUNTING;

                GameScene.add(spawn, 0);
                Dungeon.level.occupyCell(spawn);

                if (sprite.visible) {
                    Actor.addDelayed(new Pushing(spawn, pos, spawn.pos), -1);
                }
                int i = Random.Int(4);
                switch (i) {
                    case 0: {
                        speak("GET EM'", CharSprite.WARNING);
                        break;
                    }
                    case 1: {
                        speak("RATS, RISE", CharSprite.WARNING);
                        break;
                    }
                    case 2: {
                        speak("YOU GO KILL EM'", CharSprite.WARNING);
                        break;
                    }
                    case 3: {
                        speak("FIGHT, RAT", CharSprite.WARNING);
                        break;
                    }
                    case 4: {
                        speak("FIGHT FOR YOUR LEADER", CharSprite.WARNING);
                        break;
                    }
                }

            }
        }
        if (Random.Int(3) == 1 && buff(PotionOfCleansing.Cleanse.class)==null) {
            ArrayList<Integer> candidates2 = new ArrayList<>();
            for (int n : PathFinder.NEIGHBOURS8) {
                if (Dungeon.level.passable[pos + n] && Actor.findChar(pos + n) == null) {
                    candidates2.add(pos + n);
                }
            }

            if (!candidates2.isEmpty()) {
                int posi;
                posi = Random.element(candidates2);

                this.sprite.jump(pos, posi, new Callback() {
                    @Override
                    public void call() {
                        pos = posi;
                        speak("dodged", 0xFFFF00);
                    }
                });
                Sample.INSTANCE.play(Assets.Sounds.MISS);
                return 0;
            }

        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void die( Object cause ) {
        Badges.validateEnemy(this);
        speak("i failed...", CharSprite.BLACK);
        super.die( cause );
        Ghost.Quest.process();
    }
}