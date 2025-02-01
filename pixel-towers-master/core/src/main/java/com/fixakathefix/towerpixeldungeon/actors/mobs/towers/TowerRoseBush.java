package com.fixakathefix.towerpixeldungeon.actors.mobs.towers;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.DamageSource;
import com.fixakathefix.towerpixeldungeon.actors.buffs.AscensionChallenge;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.hero.Belongings;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.DirectableAlly;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.Speck;
import com.fixakathefix.towerpixeldungeon.effects.particles.ShaftParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.WindParticle;
import com.fixakathefix.towerpixeldungeon.items.Item;
import com.fixakathefix.towerpixeldungeon.items.armor.Armor;
import com.fixakathefix.towerpixeldungeon.items.armor.glyphs.AntiMagic;
import com.fixakathefix.towerpixeldungeon.items.armor.glyphs.Brimstone;
import com.fixakathefix.towerpixeldungeon.items.artifacts.DriedRose;
import com.fixakathefix.towerpixeldungeon.items.artifacts.RoseSeed;
import com.fixakathefix.towerpixeldungeon.items.bags.Bag;
import com.fixakathefix.towerpixeldungeon.items.weapon.Weapon;
import com.fixakathefix.towerpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.CellSelector;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.sprites.GhostSprite;
import com.fixakathefix.towerpixeldungeon.sprites.ItemSpriteSheet;
import com.fixakathefix.towerpixeldungeon.sprites.RoseBushSprite;
import com.fixakathefix.towerpixeldungeon.ui.BossHealthBar;
import com.fixakathefix.towerpixeldungeon.ui.RenderedTextBlock;
import com.fixakathefix.towerpixeldungeon.ui.Window;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.IconTitle;
import com.fixakathefix.towerpixeldungeon.windows.WndBag;
import com.fixakathefix.towerpixeldungeon.windows.WndBlacksmith;
import com.fixakathefix.towerpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class TowerRoseBush extends TowerCSpawning{

    {
        HP = HT = 70;

        spriteClass = RoseBushSprite.class;

        minionCooldown = 50;
        minionCooldownLeft = 1;

        maxMinions = 1;

        spawnsExcessMinions = false;

        sellable = false;
        cost = 200;

        upgrade1Cost = 300;

        upgradeLevel = 3;

    }

    private boolean talkedTo = false;
    private boolean firstSummon = false;

    private GhostHero ghost = null;
    private int ghostID = 0;

    private MeleeWeapon weapon = null;
    private Armor armor = null;

    public int guardingPositionForRestore = -1;

    @Override
    public boolean interact(Char c) {
        if (c == hero) {

            ArrayList<String> options = new ArrayList<>();
            options.add( Messages.get(TowerRoseBush.class, "direct"));
            options.add( Messages.get(TowerRoseBush.class, "outfit"));

            Game.runOnRenderThread(new Callback() {
                @Override
                public void call() {
                    GameScene.show(new WndOptions(
                            new GhostSprite(),
                            Messages.get(TowerRoseBush.class, "wndname"),
                            Messages.get(TowerRoseBush.class, "wnddesc"),
                            options.toArray(new String[0])
                    ){
                        @Override
                        protected void onSelect(int index) {
                            super.onSelect(index);
                            switch (index) {
                                case 0: {
                                    if (ghost == null && ghostID != 0){
                                        Actor a = Actor.findById(ghostID);
                                        if (a != null){
                                            ghost = (GhostHero)a;
                                        } else {
                                            ghostID = 0;
                                        }
                                    }
                                    if (ghost != null) GameScene.selectCell(ghostDirector);
                                    break;
                                }
                                case 1: {
                                    GameScene.show(new WndGhostHero(TowerRoseBush.this));
                                    break;
                                }
                            }
                        }
                    });
                }


            });
            return true;
        } else return true;
    }


    @Override
    public String info() {
        StringBuilder info = new StringBuilder();
        info.append(description());
        info.append(Messages.get(this, "stats", HP, HT));
        info.append(Messages.get(this, "descstats"));
        return info.toString();
    }
    @Override
    public void spawnMinion(int pos) {
        boolean theresAGhost = false;
        for (Mob mob : Level.mobs){
            if (mob instanceof GhostHero) theresAGhost = true;
        }
        if (!theresAGhost){
            GhostHero minion = new GhostHero();
            minion.pos = pos;
            minion.bush = this;
            this.ghost = minion;
            ghostID = minion.id();
            GameScene.add(minion);
            if (guardingPositionForRestore == -1) minion.defendPos(this.pos); else minion.defendPos(guardingPositionForRestore);
            Dungeon.level.occupyCell(minion);
            minion.state = minion.WANDERING;
            CellEmitter.get(pos).start( ShaftParticle.FACTORY, 0.3f, 4 );
            CellEmitter.get(pos).start( Speck.factory(Speck.LIGHT), 0.2f, 3 );
        }
    }

    public Weapon ghostWeapon(){
        return weapon;
    }

    public Armor ghostArmor(){
        return armor;
    }

    private static final String TALKEDTO =      "talkedto";
    private static final String FIRSTSUMMON =   "firstsummon";
    private static final String GHOSTID =       "ghostID";
    private static final String GUARDINGPOS =       "guardingpos";
    private static final String WEAPON =        "weapon";
    private static final String ARMOR =         "armor";

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);

        bundle.put( TALKEDTO, talkedTo );
        bundle.put( FIRSTSUMMON, firstSummon );
        bundle.put( GHOSTID, ghostID );
        bundle.put( GUARDINGPOS, guardingPositionForRestore );

        if (weapon != null) bundle.put( WEAPON, weapon );
        if (armor != null)  bundle.put( ARMOR, armor );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle(bundle);

        talkedTo = bundle.getBoolean( TALKEDTO );
        firstSummon = bundle.getBoolean( FIRSTSUMMON );
        ghostID = bundle.getInt( GHOSTID );
        guardingPositionForRestore = bundle.getInt(GUARDINGPOS);

        if (bundle.contains(WEAPON)) weapon = (MeleeWeapon)bundle.get( WEAPON );
        if (bundle.contains(ARMOR))  armor = (Armor)bundle.get( ARMOR );
    }

    public CellSelector.Listener ghostDirector = new CellSelector.Listener(){

        @Override
        public void onSelect(Integer cell) {
            if (cell == null) return;

            Sample.INSTANCE.play( Assets.Sounds.GHOST );

            ghost.directTocell(cell);

        }

        @Override
        public String prompt() {
            return  "\"" + Messages.get(DriedRose.GhostHero.class, "direct_prompt") + "\"";
        }
    };

    @Override
    public void die(Object cause) {
        Sample.INSTANCE.play(Assets.Sounds.TELEPORT);
        CellEmitter.get(pos).burst(WindParticle.FACTORY, 20);
        CellEmitter.get(pos).start(ShaftParticle.FACTORY, 0.2f, 20);
        ArrayList<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < Dungeon.level.width()*Dungeon.level.height();i++){
            if (PathFinder.buildDistanceMap(hero.pos, i, Dungeon.level.passable) && Dungeon.level.passable[i])  candidates.add(i);
        }
        if (ghost!= null && ghost.isAlive()) ghost.die(this);
        Dungeon.level.drop(new RoseSeed(), Random.element(candidates));
        super.die(cause);
    }

    public static class GhostHero extends DirectableAlly {

        {
            spriteClass = GhostSprite.class;

            flying = true;

            state = HUNTING;

            properties.add(Property.UNDEAD);

            defenseSkill = 8;

            HT = HP = 20 + Dungeon.hero.lvl*5;
        }

        private TowerRoseBush bush = null;

        public GhostHero(){
            super();
        }

        public GhostHero(TowerRoseBush bush){
            super();
            this.bush = bush;
            updateBush();
            if (bush.guardingPositionForRestore > -1) defendPos(bush.guardingPositionForRestore);

        }

        @Override
        public void defendPos(int cell) {
            yell(Messages.get(this, "directed_position_" + Random.IntRange(1, 5)));
            if (bush!=null)bush.guardingPositionForRestore = cell;
            super.defendPos(cell);
        }

        @Override
        public void followHero() {
            yell(Messages.get(this, "directed_follow_" + Random.IntRange(1, 5)));
            super.followHero();
        }

        @Override
        public void targetChar(Char ch) {
            yell(Messages.get(this, "directed_attack_" + Random.IntRange(1, 5)));
            super.targetChar(ch);
        }

        private void updateBush(){
            if (bush == null) {
                for (Mob mob : Level.mobs) if (mob instanceof TowerRoseBush)
                bush = (TowerRoseBush) mob;
            }
        }

        @Override
        protected boolean act() {
            updateBush();
            GameScene.updateFog(pos, 1);
            if (bush == null){
                damage(1, this);
            }

            if (!isAlive()) {
                return true;
            }
            return super.act();
        }

        @Override
        public int attackSkill(Char target) {

            //same accuracy as the hero.
            int acc = Dungeon.hero.lvl + 9;

            if (bush != null && bush.weapon != null){
                acc *= bush.weapon.accuracyFactor( this, target );
            }

            return acc;
        }

        @Override
        public float attackDelay() {
            float delay = super.attackDelay();
            if (bush != null && bush.weapon != null){
                delay *= bush.weapon.delayFactor(this);
            }
            return delay;
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return super.canAttack(enemy) || (bush != null && bush.weapon != null && bush.weapon.canReach(this, enemy.pos));
        }

        @Override
        public int damageRoll() {
            int dmg = 0;
            if (bush != null && bush.weapon != null){
                dmg += bush.weapon.damageRoll(this);
            } else {
                dmg += Random.NormalIntRange(0, 5);
            }

            return dmg;
        }

        @Override
        public int attackProc(Char enemy, int damage) {
            damage = super.attackProc(enemy, damage);
            if (bush != null && bush.weapon != null) {
                damage = bush.weapon.proc( this, enemy, damage );
                if (!enemy.isAlive() && enemy == Dungeon.hero){
                    Dungeon.fail(getClass());
                    GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
                }
            }
            return damage;
        }

        @Override
        public int defenseProc(Char enemy, int damage) {
            if (bush != null && bush.armor != null) {
                damage = bush.armor.proc( enemy, this, damage );
            }
            return super.defenseProc(enemy, damage);
        }

        @Override
        public void damage(int dmg, Object src) {
            //TODO improve this when I have proper damage source logic
            if (bush != null && bush.armor != null && bush.armor.hasGlyph(AntiMagic.class, this)
                    && DamageSource.MAGICAL.contains(src.getClass())){
                dmg -= AntiMagic.drRoll(this, bush.armor.buffedLvl());
            }

            super.damage( dmg, src );

            //for the rose status indicator
            Item.updateQuickslot();
        }

        @Override
        public float speed() {
            float speed = super.speed();

            if (bush != null && bush.armor != null){
                speed = bush.armor.speedFactor(this, speed);
            }

            //moves 2 tiles at a time when returning to the hero
            if (state == WANDERING
                    && defendingPos == -1
                    && Dungeon.level.distance(pos, Dungeon.hero.pos) > 1){
                speed *= 2;
            }

            return speed;
        }

        @Override
        public int defenseSkill(Char enemy) {
            int defense = super.defenseSkill(enemy);

            if (defense != 0 && bush != null && bush.armor != null ){
                defense = Math.round(bush.armor.evasionFactor( this, defense ));
            }

            return defense;
        }

        @Override
        public float stealth() {
            float stealth = super.stealth();

            if (bush != null && bush.armor != null){
                stealth = bush.armor.stealthFactor(this, stealth);
            }

            return stealth;
        }

        @Override
        public int drRoll() {
            int dr = super.drRoll();
            if (bush != null && bush.armor != null){
                dr += Random.NormalIntRange( bush.armor.DRMin(), bush.armor.DRMax());
            }
            if (bush != null && bush.weapon != null){
                dr += Random.NormalIntRange( 0, bush.weapon.defenseFactor( this ));
            }
            return dr;
        }

        //used in some glyph calculations
        public Armor armor(){
            if (bush != null){
                return bush.armor;
            } else {
                return null;
            }
        }

        @Override
        public boolean isImmune(Class effect) {
            if (effect == Burning.class
                    && bush != null
                    && bush.armor != null
                    && bush.armor.hasGlyph(Brimstone.class, this)){
                return true;
            }
            return super.isImmune(effect);
        }

        @Override
        public boolean interact(Char c) {
            updateBush();
            {
                return super.interact(c);
            }
        }

        @Override
        public void die(Object cause) {
            sayDefeated();
            super.die(cause);
        }

        @Override
        public void destroy() {
            updateBush();
            if (bush != null) {
                bush.ghost = null;
                bush.ghostID = -1;
            }
            super.destroy();
        }

        public void sayAppeared(){
            if (Dungeon.hero.buff(AscensionChallenge.class) != null){
                yell( Messages.get( this, "dialogue_ascension_" + Random.IntRange(1, 6) ));

            } else {

                int depth = (Dungeon.depth - 1) / 5;

                //only some lines are said on the first floor of a depth
                int variant = Dungeon.depth % 5 == 1 ? Random.IntRange(1, 3) : Random.IntRange(1, 6);

                switch (depth) {
                    case 0:
                        yell(Messages.get(this, "dialogue_sewers_" + variant));
                        break;
                    case 1:
                        yell(Messages.get(this, "dialogue_prison_" + variant));
                        break;
                    case 2:
                        yell(Messages.get(this, "dialogue_caves_" + variant));
                        break;
                    case 3:
                        yell(Messages.get(this, "dialogue_city_" + variant));
                        break;
                    case 4:
                    default:
                        yell(Messages.get(this, "dialogue_halls_" + variant));
                        break;
                }
            }
            if (ShatteredPixelDungeon.scene() instanceof GameScene) {
                Sample.INSTANCE.play( Assets.Sounds.GHOST );
            }
        }

        public void sayBoss(){
            int depth = (Dungeon.depth - 1) / 5;

            switch(depth){
                case 0:
                    yell( Messages.get( this, "seen_goo_" + Random.IntRange(1, 3) ));
                    break;
                case 1:
                    yell( Messages.get( this, "seen_tengu_" + Random.IntRange(1, 3) ));
                    break;
                case 2:
                    yell( Messages.get( this, "seen_dm300_" + Random.IntRange(1, 3) ));
                    break;
                case 3:
                    yell( Messages.get( this, "seen_king_" + Random.IntRange(1, 3) ));
                    break;
                case 4: default:
                    yell( Messages.get( this, "seen_yog_" + Random.IntRange(1, 3) ));
                    break;
            }
            Sample.INSTANCE.play( Assets.Sounds.GHOST );
        }

        public void sayDefeated(){
            if (BossHealthBar.isAssigned()){
                yell( Messages.get( this, "defeated_by_boss_" + Random.IntRange(1, 3) ));
            } else {
                yell( Messages.get( this, "defeated_by_enemy_" + Random.IntRange(1, 3) ));
            }
            Sample.INSTANCE.play( Assets.Sounds.GHOST );
        }

        public void sayHeroKilled(){
            yell( Messages.get( this, "player_killed_" + Random.IntRange(1, 3) ));
            GLog.newLine();
            Sample.INSTANCE.play( Assets.Sounds.GHOST );
        }

        public void sayAnhk(){
            yell( Messages.get( this, "blessed_ankh_" + Random.IntRange(1, 3) ));
            Sample.INSTANCE.play( Assets.Sounds.GHOST );
        }

    }
    public int ghostStrength(){
        return 16;
    }

    public static class WndGhostHero extends Window {

        private static final int BTN_SIZE	= 32;
        private static final float GAP		= 2;
        private static final float BTN_GAP	= 12;
        private static final int WIDTH		= 116;

        private WndBlacksmith.ItemButton btnWeapon;
        private WndBlacksmith.ItemButton btnArmor;

        WndGhostHero(final TowerRoseBush bush){

            IconTitle titlebar = new IconTitle();
            titlebar.icon( new RoseBushSprite() );
            titlebar.label( Messages.get(WndGhostHero.class, "title") );
            titlebar.setRect( 0, 0, WIDTH, 0 );
            add( titlebar );

            RenderedTextBlock message =
                    PixelScene.renderTextBlock(Messages.get(this, "desc", bush.ghostStrength()), 6);
            message.maxWidth( WIDTH );
            message.setPos(0, titlebar.bottom() + GAP);
            add( message );

            btnWeapon = new WndBlacksmith.ItemButton(){
                @Override
                protected void onClick() {
                    if (bush.weapon != null){
                        item(new WndBag.Placeholder(ItemSpriteSheet.WEAPON_HOLDER));
                        if (!bush.weapon.doPickUp(Dungeon.hero)){
                            Dungeon.level.drop( bush.weapon, Dungeon.hero.pos);
                        }
                        bush.weapon = null;
                    } else {
                        GameScene.selectItem(new WndBag.ItemSelector() {

                            @Override
                            public String textPrompt() {
                                return Messages.get(WndGhostHero.class, "weapon_prompt");
                            }

                            @Override
                            public Class<?extends Bag> preferredBag(){
                                return Belongings.Backpack.class;
                            }

                            @Override
                            public boolean itemSelectable(Item item) {
                                return item instanceof MeleeWeapon;
                            }

                            @Override
                            public void onSelect(Item item) {
                                if (!(item instanceof MeleeWeapon)) {
                                    //do nothing, should only happen when window is cancelled
                                } else if (item.unique) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_unique"));
                                    hide();
                                } else if (!item.isIdentified()) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_unidentified"));
                                    hide();
                                } else if (item.cursed) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_cursed"));
                                    hide();
                                } else if (((MeleeWeapon)item).STRReq() > bush.ghostStrength()) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_strength"));
                                    hide();
                                } else {
                                    if (item.isEquipped(Dungeon.hero)){
                                        ((MeleeWeapon) item).doUnequip(Dungeon.hero, false, false);
                                    } else {
                                        item.detach(Dungeon.hero.belongings.backpack);
                                    }
                                    bush.weapon = (MeleeWeapon) item;
                                    item(bush.weapon);
                                }

                            }
                        });
                    }
                }
            };
            btnWeapon.setRect( (WIDTH - BTN_GAP) / 2 - BTN_SIZE, message.top() + message.height() + GAP, BTN_SIZE, BTN_SIZE );
            if (bush.weapon != null) {
                btnWeapon.item(bush.weapon);
            } else {
                btnWeapon.item(new WndBag.Placeholder(ItemSpriteSheet.WEAPON_HOLDER));
            }
            add( btnWeapon );

            btnArmor = new WndBlacksmith.ItemButton(){
                @Override
                protected void onClick() {
                    if (bush.armor != null){
                        item(new WndBag.Placeholder(ItemSpriteSheet.ARMOR_HOLDER));
                        if (!bush.armor.doPickUp(Dungeon.hero)){
                            Dungeon.level.drop( bush.armor, Dungeon.hero.pos);
                        }
                        bush.armor = null;
                    } else {
                        GameScene.selectItem(new WndBag.ItemSelector() {

                            @Override
                            public String textPrompt() {
                                return Messages.get(WndGhostHero.class, "armor_prompt");
                            }

                            @Override
                            public Class<?extends Bag> preferredBag(){
                                return Belongings.Backpack.class;
                            }

                            @Override
                            public boolean itemSelectable(Item item) {
                                return item instanceof Armor;
                            }

                            @Override
                            public void onSelect(Item item) {
                                if (!(item instanceof Armor)) {
                                    //do nothing, should only happen when window is cancelled
                                } else if (item.unique || ((Armor) item).checkSeal() != null) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_unique"));
                                    hide();
                                } else if (!item.isIdentified()) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_unidentified"));
                                    hide();
                                } else if (item.cursed) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_cursed"));
                                    hide();
                                } else if (((Armor)item).STRReq() > bush.ghostStrength()) {
                                    GLog.w( Messages.get(WndGhostHero.class, "cant_strength"));
                                    hide();
                                } else {
                                    if (item.isEquipped(Dungeon.hero)){
                                        ((Armor) item).doUnequip(Dungeon.hero, false, false);
                                    } else {
                                        item.detach(Dungeon.hero.belongings.backpack);
                                    }
                                    bush.armor = (Armor) item;
                                    item(bush.armor);
                                }

                            }
                        });
                    }
                }
            };
            btnArmor.setRect( btnWeapon.right() + BTN_GAP, btnWeapon.top(), BTN_SIZE, BTN_SIZE );
            if (bush.armor != null) {
                btnArmor.item(bush.armor);
            } else {
                btnArmor.item(new WndBag.Placeholder(ItemSpriteSheet.ARMOR_HOLDER));
            }
            add( btnArmor );

            resize(WIDTH, (int)(btnArmor.bottom() + GAP));
        }

    }

}
