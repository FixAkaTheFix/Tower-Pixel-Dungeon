package com.towerpixel.towerpixeldungeon.actors.mobs;

import static com.towerpixel.towerpixeldungeon.Dungeon.hero;
import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.blobs.Blob;
import com.towerpixel.towerpixeldungeon.actors.blobs.Fire;
import com.towerpixel.towerpixeldungeon.actors.blobs.Freezing;
import com.towerpixel.towerpixeldungeon.actors.blobs.StenchGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.AnkhInvulnerability;
import com.towerpixel.towerpixeldungeon.actors.buffs.Berserk;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Healing;
import com.towerpixel.towerpixeldungeon.actors.buffs.LifeLink;
import com.towerpixel.towerpixeldungeon.actors.buffs.ShieldBuff;
import com.towerpixel.towerpixeldungeon.actors.buffs.Speed;
import com.towerpixel.towerpixeldungeon.actors.buffs.Strength;
import com.towerpixel.towerpixeldungeon.actors.hero.Hero;
import com.towerpixel.towerpixeldungeon.actors.mobs.npcs.RatKing;
import com.towerpixel.towerpixeldungeon.actors.mobs.towers.TowerGrave1;
import com.towerpixel.towerpixeldungeon.effects.Beam;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.particles.ShadowParticle;
import com.towerpixel.towerpixeldungeon.items.Amulet;
import com.towerpixel.towerpixeldungeon.items.potions.PotionOfHealing;
import com.towerpixel.towerpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.towerpixel.towerpixeldungeon.items.wands.Wand;
import com.towerpixel.towerpixeldungeon.items.wands.WandOfBlastWave;
import com.towerpixel.towerpixeldungeon.levels.Arena;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.messages.Messages;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.BossNecromancerSprite;
import com.towerpixel.towerpixeldungeon.sprites.CharSprite;
import com.towerpixel.towerpixeldungeon.sprites.GuardSprite;
import com.towerpixel.towerpixeldungeon.sprites.KingSprite;
import com.towerpixel.towerpixeldungeon.sprites.SkeletonSprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGraveEliteSprite;
import com.towerpixel.towerpixeldungeon.ui.BossHealthBar;
import com.towerpixel.towerpixeldungeon.utils.GLog;
import com.towerpixel.towerpixeldungeon.windows.WndDialogueWithPic;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class BossDwarfKing extends Mob{

    {
        spriteClass = KingSprite.class;

        HP = HT = 10000;
        defenseSkill = 0;

        EXP = 7;
        viewDistance = 100;

        baseSpeed=1f;

        loot = new PotionOfHealing();
        lootChance = 0.2f; //see lootChance()

        properties.add(Property.UNDEAD);
        properties.add(Property.BOSS);
        properties.add(Property.IMMOVABLE);

        state = PASSIVE;

        alignment = Alignment.ENEMY;


        HUNTING = new Hunting();
    }

    private boolean lastrage = false;

    private static final int DORMANT = 0;
    private static final int MELEE = 1;
    private static final int RANGED = 2;
    private static final int SUPPORT = 3;

    public int battleMode = DORMANT;

    private final int oneAbilityCooldown = 11;

    private int oneAbilityCooldownLeft = 11;

    private final int twoAbilityCooldown = 7;

    private int twoAbilityCooldownLeft = 7;

    private final int changeModeAbilityCooldown = 10;

    private int changeAbilityCooldownLeft = 10;





    @Override
    public void notice() {
        super.notice();
        if (!BossHealthBar.isAssigned()) {
            BossHealthBar.assignBoss(this);
        }
    }

    @Override
    protected boolean getFurther(int target) {
        if (battleMode==DORMANT) return true;
        return super.getFurther(target);
    }
    @Override
    protected boolean getCloser(int target) {
        if (battleMode==DORMANT) return true;
        return super.getCloser(target);
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        if (battleMode == DORMANT){
            return 0;
        }
        return super.defenseProc(enemy, damage);
    }

    @Override
    public void damage(int dmg, Object src) {
        if (battleMode == DORMANT){
            sprite.showStatus(CharSprite.MYSTERIOUS, Messages.get(DwarfKing.class, "invulnerable"));
        } else super.damage(dmg, src);
    }

    @Override
    public boolean interact(Char c) {
        if (battleMode == DORMANT){
            return true;
        }
        return super.interact(c);
    }

    @Override
    protected boolean act() {
        if (battleMode == DORMANT){
            if (sprite!=null ) sprite.turnTo(pos, ((Arena) Dungeon.level).amuletCell);
            return super.act();
        } else {
            System.out.println(battleMode);
            oneAbilityCooldownLeft--;
            twoAbilityCooldownLeft--;
            changeAbilityCooldownLeft--;
            tryUsingAbility();
            return super.act();
        }
    }

    public void awaken(){

        Game.runOnRenderThread(new Callback() {
            @Override
            public void call() {
                Music.INSTANCE.play(Assets.Music.CITY_BOSS_SPEDUP, true);
            }
        });
        sprite.remove(CharSprite.State.SHIELDED);
        battleMode = RANGED;
        state = HUNTING;
        alignment = Alignment.ENEMY;
        Buff.detach(this, AnkhInvulnerability.class);
    }

    public void tryUsingAbility(){
        if (oneAbilityCooldownLeft<=0){
            switch (battleMode){
                case MELEE:{
                    int r = Random.Int(2);
                    if (r == 0){
                        abLifeLink();
                    } else if (r == 1){
                        abSummonHorde();
                    }
                    break;
                }
                case RANGED:{
                    int r = Random.Int(3);
                    if (r == 0){
                        abImmortality();
                    } else if (r == 1){
                        abSummonMinion();
                    }
                    else if (r == 2){
                        hordeOfUndead();
                    }
                    break;
                }
                case SUPPORT:{
                    int r = Random.Int(3);
                    if (r == 0){
                        abBuffAllUndead();
                    } else if (r == 1){
                        abSummonHorde();
                    }
                    else if (r == 2){
                        hordeOfUndead();
                    }
                    break;
                }
            }
            oneAbilityCooldownLeft = oneAbilityCooldown;
        }
        if (twoAbilityCooldownLeft<=0){
            switch (battleMode){
                case MELEE:{
                    int r = Random.Int(2);
                    if (r == 0){
                        abImmortality();
                    } else if (r == 1){
                        abStomp();
                    }
                    break;
                }
                case RANGED:{
                    abSummonHorde();
                    break;
                }
                case SUPPORT:{
                    int r = Random.Int(2);
                    if (r == 0){
                        abBuffAllUndead();
                    } else if (r == 1){
                        abLifeLink();
                    }
                    break;
                }
            }
            twoAbilityCooldownLeft = twoAbilityCooldown;
        }
        if (changeAbilityCooldownLeft<=0){
            switch (battleMode){
                case MELEE:{
                    int r = Random.Int(2);
                    if (r == 0){
                        abTryBackstepRangedEngage();
                    } else if (r == 1){
                        abTrySupportEngage();
                    }
                    break;
                }
                case RANGED:{
                    abTryMeleeEngage();
                    break;
                }
                case SUPPORT:{
                    int r = Random.Int(2);
                    if (r == 0){
                        abTryMeleeEngage();
                    } else if (r == 1){
                        abTryBackstepRangedEngage();
                    }
                    break;
                }
            }
            changeAbilityCooldownLeft = changeModeAbilityCooldown;
        }
    }

    @Override
    public int attackSkill(Char target) {
        return 20;
    }

    @Override
    protected boolean canAttack(Char enemy) {
        if (battleMode == DORMANT){
            return false;
        } else return super.canAttack(enemy)
                || (battleMode == RANGED && distance(enemy)<7 && new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos)
                || (battleMode == SUPPORT && distance(enemy)<5 && new Ballistica(pos, enemy.pos, Ballistica.PROJECTILE).collisionPos == enemy.pos);
    }

    @Override
    public int damageRoll() {
        switch (battleMode){
            case MELEE: return (Random.IntRange(26,36));
            case SUPPORT: return (Random.IntRange(15,20));
            case RANGED: default: return (Random.IntRange(13,18));
        }
    }

    @Override
    public int attackProc(Char enemy, int damage) {
        return super.attackProc(enemy, damage);
    }

    ///MELEE ABILITIES
    private void abLifeLink(){
        Mob linkmob = null;

        int att = 0;

        do {
            linkmob = Random.element(Dungeon.level.mobs);
            att++;
        } while ((linkmob==null || linkmob.alignment == Alignment.ALLY || linkmob == this) && att <= 30);

        if (att == 30) return;
        if (linkmob!= null && linkmob.alignment==Alignment.ENEMY && linkmob!=this){
            Buff.append(linkmob, LifeLink.class, 100f).object = id();
            Buff.append(this, LifeLink.class, 100f).object = linkmob.id();
            yell(Messages.get(DwarfKing.class, "lifelink_" + Random.IntRange(1, 2)));
            sprite.parent.add(new Beam.HealthRay(sprite.destinationCenter(), linkmob.sprite.destinationCenter()));
        }
    }

    public void abSummonHorde() {
        spend(1f);
        sprite.attack(pos);
        ((Arena)Dungeon.level).deployMobs(3);
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        int r = Random.Int(2);
        for (Mob mob : level.mobs){
            if (mob.alignment == Alignment.ALLY) mob.enemy =null;
        }
        for (int i : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + i] && Actor.findChar(pos + i) == null) {
                Mob mm;
                if (r == 1) mm = new Monk(); else mm = new Warlock();
                CellEmitter.get(mm.pos).burst(ShadowParticle.UP, 5);
                mm.pos = pos + i;
                GameScene.add(mm);
                Dungeon.level.occupyCell(mm);
            }
        }
    }
    public void abStomp() {
        WandOfBlastWave wand = new WandOfBlastWave();
        wand.upgrade(8);
        wand.onZap(new Ballistica(((Arena)level).amuletCell, this.pos, Ballistica.PROJECTILE));
    }

    ///RANGED ABILITIES
    public void abSummonMinion() {
        for (Mob mob : level.mobs){
            if (mob.alignment == Alignment.ALLY) mob.enemy =null;
        }
        ((Arena)Dungeon.level).deployMobs(2);
        spend(1f);
        ArrayList<Integer> candidates = new ArrayList();
        sprite.attack(pos + Dungeon.level.width(), new Callback() {
            @Override
            public void call() {
                onAttackComplete();
            }
        });
        for (int cell : PathFinder.NEIGHBOURS8) {
            if (Dungeon.level.passable[pos + cell] && Actor.findChar(pos + cell) == null) {
                candidates.add(pos+cell);
            }
        }
        if (!candidates.isEmpty()){
           SkeletonArmored skelly = new SkeletonArmored();
            skelly.pos = Random.element(candidates);
            CellEmitter.get(skelly.pos).burst(ShadowParticle.UP, 5);
            Sample.INSTANCE.play(Assets.Sounds.CURSED);
            Dungeon.level.occupyCell(skelly);
            GameScene.add(skelly);
        }
    }
    public void abImmortality(){
        spend(1);
        ((Arena)Dungeon.level).deployMobs(4);
        Buff.affect(this, AnkhInvulnerability.class, 5);
    }

    ///SUPPORT ABILITIES
    public void abBuffAllUndead() {
        spend(1f);
        for (Mob mob : Dungeon.level.mobs){

            if (mob.alignment == Alignment.ENEMY && mob.properties().contains(Property.UNDEAD)){
                int r = Random.Int(5);
                switch (r) {
                    case 0: default: Buff.affect(mob, Strength.class, 7); break;
                    case 1: Buff.affect(mob, Speed.class, 7); break;
                    case 2:
                        Buff.affect(mob, Healing.class).setHeal(30,0.5f,10);
                        break;
                    case 3: Buff.affect(mob, AnkhInvulnerability.class, 3); break;
                }
            }
        }
    }
    public void hordeOfUndead() {
        spend(1f);
        Sample.INSTANCE.play(Assets.Sounds.CURSED);
        for (int i : PathFinder.FARCROSS) for (int x : PathFinder.NEIGHBOURS8){
            Skeleton skelly = new Skeleton();
            skelly.pos = this.pos + i + x;
            CellEmitter.get(skelly.pos).burst(ShadowParticle.UP, 5);

            GameScene.add(skelly);
            ScrollOfTeleportation.appear(skelly,skelly.pos);


        }
        ((Arena)Dungeon.level).deployMobs(3);

    }

    ///CHANGE MODE
    public void abTryMeleeEngage() {
       // System.out.println("MEAUUU");
        if (!lastrage && HP < 1200){
            sprite.move(pos, 60 + level.width()*35);
            pos = 60 + level.width()*35;
            ((Arena)Dungeon.level).deployMobs(15);
            lastrage = true;
        }
        spend(1f);
        if (enemy != null && enemy.isAlive() && !(enemy instanceof Hero) && !(enemy instanceof Arena.AmuletTower)) {
            sprite.move(pos, enemy.pos);
            pos = enemy.pos + 1;
        }
        battleMode = MELEE;
        next();
    }
    public void abTryBackstepRangedEngage() {
        ArrayList<Integer> candidates = new ArrayList();
        spend(1f);
        for (int x = pos % Dungeon.level.width(); x < pos % Dungeon.level.width() + 5; x++) for (int y = pos / Dungeon.level.width()-1; y < pos / Dungeon.level.width() + 2; y++){
            int cell = x + y* level.width();
            if (level.passable[cell] && Char.findChar(cell)==null){
                candidates.add(cell);
            }
        }
        if (!(candidates.isEmpty())){
            int posss = Random.element(candidates);

            sprite.move(pos, posss);
            pos = posss + 1;
        }
        battleMode = RANGED;
        next();
    }
    public void abTrySupportEngage() {
        battleMode = SUPPORT;
    }

    ///DORMANT ADAPTATIONS





    ///OTHER





    @Override
    public void die(Object cause) {
        WndDialogueWithPic.dialogue(new KingSprite(), Messages.get(this, "name"),
                new String[]{
                        Messages.get(this, "deathspeech1"),
                        Messages.get(this, "deathspeech2"),
                        Messages.get(this, "deathspeech3"),
                        Messages.get(this, "deathspeech4"),
                        Messages.get(this, "deathspeech5"),
                        Messages.get(this, "deathspeech6"),
                        Messages.get(this, "deathspeech7")
                },
                new byte[]{
                        WndDialogueWithPic.IDLE
                },
                WndDialogueWithPic.WndType.FINAL);
        super.die(cause);
    }
    private static final String BATTLEMODE = "battlemode";
    private static final String LASTRAGE = "battlemode";
    private static final String CHANGECOOLDOWN = "changecooldown";
    private static final String ONEABCOOLDOWN = "oneabcooldown";
    private static final String TWOABCOOLDOWN = "twoabcooldown";
    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(BATTLEMODE, battleMode);
        bundle.put(LASTRAGE, lastrage);
        bundle.put(CHANGECOOLDOWN, changeAbilityCooldownLeft);
        bundle.put(ONEABCOOLDOWN, oneAbilityCooldownLeft);
        bundle.put(TWOABCOOLDOWN, twoAbilityCooldownLeft);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        battleMode = bundle.getInt(BATTLEMODE);
        lastrage = bundle.getBoolean(LASTRAGE);
        changeAbilityCooldownLeft = bundle.getInt(CHANGECOOLDOWN);
        oneAbilityCooldownLeft = bundle.getInt(ONEABCOOLDOWN);
        twoAbilityCooldownLeft = bundle.getInt(TWOABCOOLDOWN);
    }
}
