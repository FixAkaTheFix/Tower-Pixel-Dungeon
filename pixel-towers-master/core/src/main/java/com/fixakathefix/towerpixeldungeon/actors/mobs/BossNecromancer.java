/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2025 Evan Debenham
 *
 * Pixel Towers / Towers Pixel Dungeon
 * Copyright (C) 2024-2025 FixAkaTheFix (initials R. A. A.)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.fixakathefix.towerpixeldungeon.actors.mobs;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;

import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Fire;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Freezing;
import com.fixakathefix.towerpixeldungeon.actors.blobs.StenchGas;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.fixakathefix.towerpixeldungeon.items.potions.PotionOfHealing;
import com.fixakathefix.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.fixakathefix.towerpixeldungeon.levels.Arena;
import com.fixakathefix.towerpixeldungeon.mechanics.Ballistica;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.sprites.BossNecromancerSprite;
import com.fixakathefix.towerpixeldungeon.sprites.SkeletonSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGraveEliteSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.windows.WndDialogueWithPic;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossNecromancer extends Mob {

    {
        spriteClass = BossNecromancerSprite.class;

        HP = HT = 1100;
        defenseSkill = 5;

        EXP = 7;
        viewDistance = 6;

        baseSpeed= level.mode == WndModes.Modes.HARDMODE ? 1.8f : 1f;;

        loot = new PotionOfHealing();
        lootChance = 0.2f; //see lootChance()

        properties.add(Property.UNDEAD);
        properties.add(Property.BOSS);

        HUNTING = new Hunting();
    }

    private final int summonGraveCooldown = 20;
    private int summonGraveCooldownLeft = 100;

    private static final String SGCL = "sgcl";


    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(SGCL, summonGraveCooldownLeft);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        summonGraveCooldownLeft = bundle.getInt(SGCL);
    }

    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    protected boolean act() {
        summonGraveCooldownLeft--;
        if (Math.random()>0.9) {
            ((Arena)level).deploymobs(8055, Arena.Direction.RIGHT, 1);
            ((Arena)level).deploymobs(1, Arena.Direction.RIGHT, 1);
        }
        boolean summoned = false;
        boolean healed = false;
        int ran = Random.Int(6);
        int speech = Random.Int(6);
        if (HP<250){
            abSummonDesperado();
            abHeal();
            spend(1);
            for (Mob mob : Dungeon.level.mobs){
                mob.enemy = null;
            }
                    switch (speech) {
                        case 0:
                            speak("*some intense healing gibberish*", 0x00ff00);
                            break;
                        case 1:
                            speak("YOU CANNOT KILL ME I AM OMEGA", 0xdddddd);
                            break;
                        case 2:
                            speak("IT CAN'T END LIKE THIS", 0xdddddd);
                            break;
                        case 3:
                            speak("WE CAN DO THIS FOREVER", 0xdddddd);
                            break;
                        case 4:
                            speak("please, end this...", 0xaa00aa);
                            break;
                        case 5:
                            speak("*some desperate regenerative gibberish*", 0x00ff00);
                            break;

                    }
                    healed = true;

                return true;
            } else
                switch (ran){
            case 1: case 4: {
                abSummonMinion();
                switch (speech){
                    case 0: speak("*some vile gibberish*", 0x00aaaa); break;
                    case 1: speak("RISE, MINIONS", 0xffffff);break;
                    case 2: speak("FIGHT, MINIONS",0xffffff);break;
                    case 3: speak("DESTROY THEM",0xffffff);break;
                    case 4: speak("YOU WILL PAY",0xffffff);break;
                    case 5: speak("*some ancient gibberish*",0x00aaaa);break;
                }
                summoned = true;
                return true;
            }
            case 2: {
                abTpSkelly();
                switch (speech){
                    case 0: speak("*some teleporting gibberish*", 0xaa0000);break;
                    case 1: speak("GET THEM", 0xffffff);break;
                    case 2: speak("can you handle THIS???",0xffffff);break;
                    case 3: speak("you didn't expect THAT???",0xffffff);break;
                    case 4: speak("MUAHAHAHAHA",0xffffff);break;
                    case 5: speak("*some warping gibberish*",0xaa0000);break;
                }
                summoned = true;
                return true;
            }
            case 3: {
                if (HP<500) {
                    abHeal();
                    switch (speech) {
                        case 0:
                            speak("*some healing gibberish*", 0x00ff00);
                            break;
                        case 1:
                            speak("YOU CAN'T KILL ME", 0xffffff);
                            break;
                        case 2:
                            speak("HEALING ABILITY ACTIVATE!!", 0xffffff);
                            break;
                        case 3:
                            speak("HAHAHAHA", 0xffffff);
                            break;
                        case 4:
                            speak("+HP, GOT IT???", 0xffffff);
                            break;
                        case 5:
                            speak("*some regenerative gibberish*", 0x00ff00);
                            break;

                    }
                    healed = true;
                } else return super.act();
                return true;
            }
        }
        if (summonGraveCooldownLeft<=0){
            summonGraveCooldownLeft=summonGraveCooldown;
            abSummonGrave();
        }
        return super.act();
    }

    @Override
    public int attackSkill(Char target) {
        return 20;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        return (super.canAttack(enemy) || new Ballistica( pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos);
    }

    @Override
    public int damageRoll() {
        return (Random.IntRange(5,15));
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        if (HP > HT*3/4 ) for (int offset : PathFinder.NEIGHBOURS5){
            if (!Dungeon.level.solid[enemy.pos+offset]) {
                GameScene.add(Blob.seed(enemy.pos + offset, 2, Fire.class));
            }
        } else if (HP > HT/2 ) for (int offset : PathFinder.NEIGHBOURS9){
            if (!Dungeon.level.solid[enemy.pos+offset]) {
                GameScene.add(Blob.seed(enemy.pos + offset, 2, Freezing.class));
            }
        } else
            for (int offset : PathFinder.NEIGHBOURS4){
            if (!Dungeon.level.solid[enemy.pos+offset]) {
                GameScene.add(Blob.seed(enemy.pos + offset, 2, StenchGas.class));
            }
        }
        return super.attackProc(enemy, damage);
    }

    public void abTpSkelly() {

        spend(1f);
        ((BossNecromancerSprite)sprite).abTpSkelly();
        for (int c : PathFinder.NEIGHBOURS8) {
            try {
                if (Actor.findChar(pos+c)!= null && Actor.findChar(pos+c).alignment == Alignment.ENEMY) {
                    ArrayList<Integer> candidates = new ArrayList<>();
                    for (int cell : PathFinder.NEIGHBOURS8) {
                        if (Dungeon.level.passable[hero.pos + cell] && Actor.findChar(hero.pos + cell) == null) {
                            candidates.add(hero.pos+cell);
                        }
                    }
                    if (!candidates.isEmpty()) {
                        Char ch = Actor.findChar(pos + c);
                        ch.HP = ch.HT;
                        ch.pos = Random.element(candidates);
                        ch.updateSpriteState();
                        Dungeon.level.occupyCell(ch);
                        ScrollOfTeleportation.appear(ch, ch.pos);
                    }
                }
            } catch (NullPointerException ignored) {
            }

        }
        ArrayList<Integer> candidates = new ArrayList();
        for (int cell : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + cell] && Actor.findChar(pos + cell) == null) {
                candidates.add(pos+cell);
            }
        }
        if (!candidates.isEmpty()){
            Wraith skelly = new Wraith();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
    }

    public void abSummonDesperado() {
        spend(5f);
        ArrayList<Integer> candidates = new ArrayList();
        ((BossNecromancerSprite)sprite).abSummonMinion(new Callback() {
            @Override
            public void call() {onAttackComplete();
            }
        });
        for (int cell : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + cell] && Actor.findChar(pos + cell) == null) {
                candidates.add(pos+cell);
            }
        }
        if (!candidates.isEmpty()){
            SkeletonArmored skelly = new SkeletonArmored();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
        if (!candidates.isEmpty()){
            SkeletonArmored skelly = new SkeletonArmored();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
        if (!candidates.isEmpty()){
            Wraith skelly = new Wraith();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
        if (!candidates.isEmpty()){
            Necromancer skelly = new Necromancer();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
        if (!candidates.isEmpty()){
            SpectralNecromancer skelly = new SpectralNecromancer();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
    }
    public void abSummonMinion() {
        spend(1f);
        ArrayList<Integer> candidates = new ArrayList();
        ((BossNecromancerSprite)sprite).abSummonMinion(new Callback() {
            @Override
            public void call() {onAttackComplete();
            }
        });
        for (int cell : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + cell] && Actor.findChar(pos + cell) == null) {
                candidates.add(pos+cell);
            }
        }
        if (!candidates.isEmpty()){
            NecroSkeleton skelly = new NecroSkeleton();
            skelly.pos=Random.element(candidates);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
    }

    public void abSummonGrave() {
        spend(1f);
        ArrayList<Integer> candidates = new ArrayList();
        ((BossNecromancerSprite)sprite).abSummonMinion(new Callback() {
            @Override
            public void call() {onAttackComplete();
            }
        });
        for (int cell : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + cell] && Actor.findChar(pos + cell) == null) {
                candidates.add(pos+cell);
            }
        }
        if (!candidates.isEmpty()){
            NecroGrave grave = new NecroGrave();
            grave.pos=Random.element(candidates);
            Dungeon.level.occupyCell(grave);
            GameScene.add(grave);
        }
    }
    public void abHeal(){
        spend(1);
        ((BossNecromancerSprite)sprite).abHeal();
        HP+=Random.Int(50, 80);
        if (HP > HT) HP = HT;
    }

    public static class NecroSkeleton extends Skeleton {

        {
            state = WANDERING;

            spriteClass = NecroSkeletonSprite.class;

            //no loot or exp
            maxLvl = 0;
            HP = 25;
        }

        @Override
        public float spawningWeight() {
            return 0;
        }

        private void teleportSpend(){
            spend(TICK);
        }

        public static class NecroSkeletonSprite extends SkeletonSprite{

            public NecroSkeletonSprite(){
                super();
                brightness(0.75f);
            }

            @Override
            public void resetColor() {
                super.resetColor();
                brightness(0.75f);
            }
        }

    }

    @Override
    public void die(Object cause) {
        Badges.validateEnemy(this);
        {
            WndDialogueWithPic.dialogue(new BossNecromancerSprite(), "Remac",
                    new String[]{
                            Messages.get(BossNecromancer.class, "death1"),
                            Messages.get(BossNecromancer.class, "death2"),
                    },
                    new byte[]{
                            WndDialogueWithPic.IDLE
                    }, WndDialogueWithPic.WndType.FINAL, new ArrayList<>());
        }
        super.die(cause);
    }

    public static class NecroGrave extends TowerGrave1 {

        {
            spriteClass = TowerGraveEliteSprite.class;
            state = HUNTING;
            alignment=Alignment.ENEMY;

            maxLvl = 0;

            HP = 30;
            minionCooldownLeft = 10;
        }

        @Override
        public float spawningWeight() {
            return 0;
        }

        private void teleportSpend(){
            spend(TICK);
        }

        @Override
        public boolean interact(Char c) {
                return false;
        }
    }
}

