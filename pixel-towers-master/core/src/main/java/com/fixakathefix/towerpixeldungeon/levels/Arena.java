package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.branch;
import static com.fixakathefix.towerpixeldungeon.Dungeon.depth;
import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;
import static com.fixakathefix.towerpixeldungeon.Dungeon.level;
import static com.fixakathefix.towerpixeldungeon.Dungeon.win;
import static com.fixakathefix.towerpixeldungeon.items.Item.updateQuickslot;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Badges;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.GamesInProgress;
import com.fixakathefix.towerpixeldungeon.SPDSettings;
import com.fixakathefix.towerpixeldungeon.ShatteredPixelDungeon;
import com.fixakathefix.towerpixeldungeon.actors.Actor;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;

import com.fixakathefix.towerpixeldungeon.actors.buffs.Burning;
import com.fixakathefix.towerpixeldungeon.actors.buffs.ChampionEnemy;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Corrosion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Faint;
import com.fixakathefix.towerpixeldungeon.actors.buffs.GoldArmor;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Invisibility;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Levitation;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Minion;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Prediction;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Speed;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Strength;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveBuff;
import com.fixakathefix.towerpixeldungeon.actors.buffs.WaveCooldownBuff;
import com.fixakathefix.towerpixeldungeon.actors.hero.Hero;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bandit;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Bee;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossDwarfKing;
import com.fixakathefix.towerpixeldungeon.actors.mobs.BossOoze;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DMWMinion;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mimic;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Mob;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Piranha;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Rat;
import com.fixakathefix.towerpixeldungeon.actors.mobs.RotLasher;
import com.fixakathefix.towerpixeldungeon.actors.mobs.Snake;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NewShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.NormalShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.npcs.TowerShopKeeper;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.EnemyPortal;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.SubAmuletTower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.Tower;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerGuard1;
import com.fixakathefix.towerpixeldungeon.actors.mobs.towers.TowerWave;
import com.fixakathefix.towerpixeldungeon.effects.CellEmitter;
import com.fixakathefix.towerpixeldungeon.effects.MagicMissile;
import com.fixakathefix.towerpixeldungeon.effects.particles.ElmoParticle;
import com.fixakathefix.towerpixeldungeon.effects.particles.SacrificialParticle;
import com.fixakathefix.towerpixeldungeon.items.Amulet;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.features.LevelTransition;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.scenes.GameScene;
import com.fixakathefix.towerpixeldungeon.scenes.LevelSelectScene;
import com.fixakathefix.towerpixeldungeon.scenes.PixelScene;
import com.fixakathefix.towerpixeldungeon.scenes.RankingsScene;
import com.fixakathefix.towerpixeldungeon.sprites.GoblinFatSprite;
import com.fixakathefix.towerpixeldungeon.sprites.MissileSprite;
import com.fixakathefix.towerpixeldungeon.sprites.PortalSprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerCrossbow1Sprite;
import com.fixakathefix.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.fixakathefix.towerpixeldungeon.sprites.walls.TowerWall1Sprite;
import com.fixakathefix.towerpixeldungeon.ui.BuffIndicator;
import com.fixakathefix.towerpixeldungeon.ui.Compass;
import com.fixakathefix.towerpixeldungeon.utils.GLog;
import com.fixakathefix.towerpixeldungeon.windows.WndModes;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;
import com.watabou.utils.Reflection;

import java.util.ArrayList;
import java.util.HashSet;

public class Arena extends Level {

    {
        color1 = 0x801500;
        color2 = 0xa68521;

        viewDistance = 15;


    }
    public int startGold = 5;
    public int startLvl = 6;
    public int arenaDepth= 0;

    /**
     * Wave system works like that:
     * wave 1, cooldown 1 (=waveCooldownNormal), wave 2, ..., cooldown time 4 (=waveCooldownBoss), wave 5 (boss or miniboss wave) etc.
     */


    public AmuletTower amuletTower = new AmuletTower();
    protected NewShopKeeper towerShopKeeper = new TowerShopKeeper();
    protected NormalShopKeeper normalShopKeeper = new NormalShopKeeper();

    public boolean shopkeepVertical = false;

    public int WIDTH = 101;
    public int HEIGHT = 101;

    public String name = "Testers' Chamber";

    public int exitCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH+1;//center
    public int amuletCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH;//where is the amulet mob
    public int towerShopKeeperCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH - WIDTH*3;//3 cells under the amulet for now
    public int normalShopKeeperCell = Math.round(WIDTH*0.5f)+ Math.round(HEIGHT*0.5f)*WIDTH - WIDTH*3-6;//3 cells under the amulet for now

    private Rect fullArena = new Rect(2,2,WIDTH-1,HEIGHT-1);//the whole arena rect
    private Rect barrierArena = new Rect(9,9,WIDTH-8,HEIGHT-8);//used for restricting, to be placed around the playerArena, where the enemies can't spawn
    private Rect playerArena = new Rect(10,10,WIDTH-9,HEIGHT-9);//places with no barriers, where the player can walk

    public final int cornersize = 38;//width and length of a cornercube
    public final int pathWidth = 2;

    public Rect upleftcorner = new Rect(2,2,2+cornersize,2 + cornersize);//TODO change the logic from cornersize to centersize
    public Rect downleftcorner = new Rect(2,HEIGHT - 1 - cornersize,2 + cornersize,HEIGHT-1);
    public Rect uprightcorner = new Rect(WIDTH - 1 - cornersize,2,WIDTH-1,2 + cornersize);
    public Rect downrightcorner = new Rect(WIDTH - 1 - cornersize,HEIGHT - 1 - cornersize,WIDTH - 1,HEIGHT - 1);

    public Rect horizontalPath = new Rect(2, (int) (Math.round(HEIGHT*0.5) - pathWidth),WIDTH - 1, (int) (Math.round(HEIGHT*0.5) + pathWidth));

    public Rect verticalPath = new Rect((int)Math.round(WIDTH*0.5)-pathWidth,2,(int)Math.round(WIDTH*0.5)+pathWidth, 2);


    public Rect middle = new Rect(2 + cornersize, 2 + cornersize, WIDTH - 1 - cornersize, HEIGHT - 1 - cornersize);
    public Rect middle1less = new Rect(3 + cornersize, 3 + cornersize, WIDTH - 2 - cornersize, HEIGHT - 2 - cornersize);//a shape inside the middle rect

    public HashSet<Rect> deployingRects = new HashSet<>();




    /**
     * Stuff you can change probably. All turns handled by the amulet.
     */

    public int maxWaves = 15;

    public boolean waterIsToxic = false;




    @Override
    public void playLevelMusic() {
        if (locked){
            Music.INSTANCE.play(Assets.Music.CAVES_BOSS, true);
            return;
        }
        Music.INSTANCE.playTracks(
                new String[]{Assets.Music.SEWERS_BOSS},
                new float[]{1},
                false);
    }

    public boolean bossSpawned = false;



    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_HALLS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }



    @Override
    protected boolean build() {

        setSize(WIDTH,HEIGHT);

        //base room
        Painter.fill(this, fullArena, Terrain.EMPTY);
        Painter.fill(this, barrierArena, Terrain.ADDITIONALBARRIER);
        Painter.fill(this, playerArena, Terrain.EMPTY);

        Painter.fill(this, upleftcorner, Terrain.WALL);
        Painter.fill(this, uprightcorner, Terrain.WALL);
        Painter.fill(this, downleftcorner, Terrain.WALL);
        Painter.fill(this, downrightcorner, Terrain.WALL);

        Painter.fill(this, middle, Terrain.EMPTY_SP);
        Painter.fillEllipse(this, middle1less, Terrain.EMPTY);



        LevelTransition exit = new LevelTransition(this, exitCell, LevelTransition.Type.REGULAR_EXIT);


        exit.set(Math.round(WIDTH*0.5f),Math.round(HEIGHT*0.5f),Math.round(WIDTH*0.5f),Math.round(HEIGHT*0.5f));//under the amulet so the hero may pathetically leave after it is destroyed
        transitions.add(exit);
        this.map[exitCell] = Terrain.EXIT;
        this.map[amuletCell] = Terrain.PEDESTAL;

        return true;
    }

    /** Mob deploying system
     * On a wave start choose a random cell which is canDeployOnCell, choose a random of some mobs and summon them, so each run is kind of unique.
     * for
     *
     */

    public int waveLast = 100;
    public int[] wavePrepareTime = new int[waveLast+1];//+1 for last wave existing; Cooldown 0 being the pre-start cooldown

    public int waveCooldownNormal = 20;
    public int waveCooldownBoss   = 50;

    public int mobsToDeploy(int wave){//amount of mobs deployed
        return 1;
    }

    public void doStuffStartwave(int wave){}/// does something in the beginning of each wave
    public void doStuffEndwave(int wave){}/// does something in the end of each wave
    public void addDestinations(){}//for stuff placing around the map after it is generated. Allows to create both mobs, structures, and loot, yet may be laggy
    public Mob chooseMob(int wave){ //Chooses a mob to spawn on a wave;
        Mob mob = new Rat();


        if (mode == WndModes.Modes.CHALLENGE){
            ((Arena)level).affectMob(mob);
        }

        return mob;
    }

    public Actor addRespawner() {
        return null;
    }

    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT,

        TOOUP,
        TOODOWN,
        TOOLEFT,
        TOORIGHT,
        RANDOM,
        CENTRAL,
        EVENMORECENTRAL,
        NOTONEDGE,
        EXACTLYRIGHT,
        EXACTLYLEFT,
        EXACTLYUP,
        EXACTLYDOWN,
        EXACTLYUPRIGHTDOWN
    }

    public void deploymobs(int wave, Direction direction, int groupnum){

        ArrayList<Integer> candidatecells = new ArrayList<>();
        for (int x = 0; x < WIDTH;x++) for (int y = 0; y < HEIGHT; y++){
            switch (direction) {
                case RANDOM: {
                    if (((x >= WIDTH - 5) || (x <= 6) || (y >= HEIGHT - 5) || (y <= 6)) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case UP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case DOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case LEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case RIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOOUP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+5 && x > WIDTH/2-5) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOODOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+5 && x > WIDTH/2-5) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOOLEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y]) && y < HEIGHT/2+5 && y > HEIGHT/2-5 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case TOORIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])&& y < HEIGHT/2+5 && y > HEIGHT/2-5 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case NOTONEDGE: {
                    if ( ( x > 6) && (x < WIDTH - 6) && ( y > 6) && (y < HEIGHT - 6) && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case CENTRAL: {
                    if ( Math.abs(x-WIDTH/2f)<10 && Math.abs(y-HEIGHT/2f)<10 && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EVENMORECENTRAL: {
                    if ( Math.abs(x-WIDTH/2f)<5 && Math.abs(y-HEIGHT/2f)<5 && (level.passable[x + WIDTH * y])) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYUP: {
                    if ((y <= 7) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYDOWN: {
                    if ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYLEFT: {
                    if ((x <= 7)  && (level.passable[x + WIDTH * y]) && y < HEIGHT/2+2 && y > HEIGHT/2-2 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYRIGHT: {
                    if ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])&& y < HEIGHT/2+2 && y > HEIGHT/2-2 ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
                case EXACTLYUPRIGHTDOWN:{
                    if (
                            ((y >= HEIGHT - 5) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2) ||
                                    ((x >= WIDTH - 6) && (level.passable[x + WIDTH * y])&& y < HEIGHT/2+2 && y > HEIGHT/2-2 ) ||
                                    ((y <= 7) && (level.passable[x + WIDTH * y]) && x < WIDTH/2+2 && x > WIDTH/2-2)
                    ) {
                        candidatecells.add(x + WIDTH * y);
                    }
                    break;
                }
            }
        }
        bossSpawned = false;
        int grouppos = 0;
        int mobsDeployed = 0;
        int mobsToDeployFinal = mobsToDeploy(wave);
        if (Dungeon.level.mode == WndModes.Modes.HARDMODE ) {
            mobsToDeployFinal = mobsToDeployFinal + mobsToDeployFinal*2*(level.wave*level.wave/maxWaves/maxWaves);
        }
        float onexd = ((float)mobsToDeployFinal/groupnum);
        float onexdsum = onexd;
        while (mobsDeployed<mobsToDeployFinal) {
            grouppos = Random.element(candidatecells);
            GameScene.scene.status.heroCompasses.clear();
            GameScene.scene.status.heroCompasses.add(new Compass(grouppos, 0xff0000));

            while ((float)mobsDeployed<onexdsum) {
                Mob mob = chooseMob(wave);
                if (Dungeon.level.mode == WndModes.Modes.HARDMODE && mobsDeployed % 8 == 1 && !(mob instanceof BossOoze)){
                    int r = Random.Int(9);
                    switch (r){
                        case 0: Buff.affect(mob, ChampionEnemy.Blazing.class); break;
                        case 1: Buff.affect(mob, ChampionEnemy.Projecting.class); break;
                        case 2: Buff.affect(mob, ChampionEnemy.AntiMagic.class); break;
                        case 3: Buff.affect(mob, ChampionEnemy.Blessed.class); break;
                        case 4: Buff.affect(mob, ChampionEnemy.Growing.class); break;
                        case 5: Buff.affect(mob, ChampionEnemy.Giant.class); break;
                        case 6: Buff.affect(mob, ChampionEnemy.Copying.class); break;
                        case 7: Buff.affect(mob, ChampionEnemy.Rejuvenating.class); break;
                        case 8: Buff.affect(mob, ChampionEnemy.Destructive.class); break;
                    }
                }
                if (depth == 14 && mobsDeployed == 0 && mode == WndModes.Modes.CHALLENGE){
                    Bandit bandit = new Bandit();
                    Buff.affect(bandit, Invisibility.class, 1000);
                    bandit.spriteClass = Random.oneOf(TowerCrossbow1Sprite.class, TowerWall1Sprite.class, TowerGuard1Sprite.class, GoblinFatSprite.class);
                    bandit.targetingPreference = Mob.TargetingPreference.ONLY_AMULET;
                    Buff.affect(bandit, Strength.class, 100000);
                    bandit.pos = grouppos;
                    GameScene.add(bandit);
                    bandit.state = bandit.HUNTING;
                }
                mob.pos = grouppos;
                GameScene.add(mob);
                mob.state = mob.HUNTING;
                mobsDeployed++;

            }
            for (Compass comp : GameScene.scene.status.heroCompasses){
                comp.x = GameScene.scene.status.avatar.x + GameScene.scene.status.avatar.width / 2f - comp.origin.x;
                comp.y = GameScene.scene.status.avatar.y + GameScene.scene.status.avatar.height / 2f - comp.origin.y;
                PixelScene.align(comp);
            }
            onexdsum+=onexd;
        }
    }

    public void deployMobsOnPortal(int portalPos){

    }
    public void deployMobs(int wave) {
        deploymobs(wave, Direction.RANDOM,0);
    }

    @Override
    public int mobCount() {//an important thing to count mobs on level.
        return super.mobCount();
    }

    public void createMapGuard(ArrayList<Integer> candidates, Mob mob){
        mob.pos = Random.element(candidates);
        mob.state = mob.HUNTING;
        mob.mapGuard = true;
        candidates.remove((Integer) mob.pos);
        GameScene.add(mob);
    }



    @Override
    public void initNpcs() {
        hero.lvl = startLvl;
        hero.updateHT(true);
        Dungeon.gold += startGold;
        updateQuickslot();
        amuletTower.pos = amuletCell;
        amuletTower.HP = amuletTower.HT;
        if (towerShopKeeper!=null){
            towerShopKeeper.pos = towerShopKeeperCell;
        }
        if (normalShopKeeper!=null){
            normalShopKeeper.pos = normalShopKeeperCell;
        }

        level = this;
        GameScene.add(amuletTower);
        this.occupyCell(amuletTower);
        if (towerShopKeeper!=null){
            GameScene.add(towerShopKeeper);
            this.occupyCell(towerShopKeeper);
            towerShopKeeper.placeItems();
        }
        if (normalShopKeeper!=null){
            GameScene.add(normalShopKeeper);
            this.occupyCell(normalShopKeeper);
            normalShopKeeper.placeItems();
        }
    }


    public void startWave() {
        wave++;
        Buff.detach(hero, WaveCooldownBuff.class);
        int coldow = ((Arena)Dungeon.level).width() + ((Arena)Dungeon.level).height();
        if (((Arena)Dungeon.level).WIDTH < 70 || ((Arena)Dungeon.level).HEIGHT < 70) coldow += 50;
        Buff.affect(hero, WaveBuff.class,   ((Arena) level).maxWaves==((Arena) level).wave ? 10000 : coldow);
        deployMobs(wave);
        doStuffStartwave(wave);
        HashSet<Mob> mobsnoconcurrent = new HashSet<>();
        mobsnoconcurrent.addAll(mobs);

        for (Mob mob : mobsnoconcurrent){
            if (mob instanceof EnemyPortal){
                EnemyPortal portal = (EnemyPortal) mob;
                portal.startMobSpawnTimer(portal.countDownToSpawn);
            }
            if (mob instanceof TowerWave) ((TowerWave)mob).prepareMobSpawn();
        }
        /**
         * working on probability:
         * By possible mob classes in wave (failure probability):
         * 1 - 0%;
         * 2 - (1/2)^20
         * 3 - (2/3)^20 etc
         * each level has its own description generated only by the arena class
         */



        Arena arenacopy = Reflection.newInstance(this.getClass());
        StringBuilder waveDesc = new StringBuilder();
        HashSet<Class<? extends Mob>> classesinawave = new HashSet<>();

        for (int randomo = 0; randomo < 20; randomo++){
            classesinawave.add(arenacopy.chooseMob(wave).getClass());
        }
        int howmanyclasses = classesinawave.size();
        for (Class<? extends Mob> aClass : classesinawave) {

            waveDesc.append(Messages.get(aClass, "name"));
            howmanyclasses--;
            if (howmanyclasses!=0) waveDesc.append(", ");
        }
        GLog.i(Messages.get(this, "wavestart", wave, waveDesc.toString()));

        /*for (int wavenum = wave; wavenum <= Math.min(maxWaves, wave + 3);wavenum++){
            StringBuilder waveDesc = new StringBuilder(Messages.get(LevelSelectScene.class, "wave") + " " + wavenum + ": _");
            HashSet<Class<? extends Mob>> classesinawave = new HashSet<>();
            for (int randomo = 0; randomo < 20; randomo++){
                classesinawave.add(chooseMob(wavenum).getClass());
            }
            int howmanyclasses = classesinawave.size();
            for (Class<? extends Mob> aClass : classesinawave) {

                waveDesc.append(Messages.get(aClass, "name"));
                howmanyclasses--;
                if (howmanyclasses!=0) waveDesc.append(", ");
            }
            desc.append(waveDesc);
            if (wavenum!=maxWaves) desc.append("\n");}
        */




    };
    public void destroyShopItems(){
        for (Heap heap: Dungeon.level.heaps.valueList()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                if (ShatteredPixelDungeon.scene() instanceof GameScene) {
                    CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
                }
                //heap.destroy();//FIXME the sale heaps either double when they have something on them or just steal these items
                if (heap.size() == 1) {
                    heap.destroy();
                } else {
                    heap.items.remove(heap.size()-1);
                    heap.type = Heap.Type.HEAP;
                }
            }
        }
    }
    public void endWave() {
        destroyShopItems();
        if (towerShopKeeper!=null) towerShopKeeper.placeItems();
        if (normalShopKeeper!=null) normalShopKeeper.placeItems();
        doStuffEndwave(wave);

        for (Mob mob : mobs){
            if (mob instanceof TowerGuard1 && mob.alignment == Char.Alignment.ALLY){
                ((TowerGuard1)mob).turnsUntilRegen = 0;
            }
        }


        if ((wave==maxWaves) && (depth!=6) && (depth!=17) && (depth!=20) && (depth!=1)){
            int maxlevel = SPDSettings.maxlevelunlocked();
            win( Amulet.class );
            Dungeon.deleteGame( GamesInProgress.curSlot, true );
            Game.switchScene( RankingsScene.class );
            int maxnewlevel = SPDSettings.maxlevelunlocked();
            if (maxnewlevel > maxlevel) {
                if (maxnewlevel == 5 ||
                        maxnewlevel==7||
                        maxnewlevel==9||
                        maxnewlevel==10||
                        maxnewlevel==11||
                        maxnewlevel==14||
                        maxnewlevel==18){
                    SPDSettings.towerUnlockedMessage(true);
                }
            }
        }
        //attempts to save each fifth (5) wave.
        if (wave%5==0) try {
            Dungeon.saveAll();
        } catch (Exception ignored) {}
        Buff.detach(hero, WaveBuff.class);
        Buff.affect(hero, WaveCooldownBuff.class, (wave % 5 == 4 ? waveCooldownBoss : waveCooldownNormal));

        //same that for start wave for prediction buff
        if (hero.buff(Prediction.class)!=null){
            Arena arenacopy = Reflection.newInstance(this.getClass());
            if (wave<maxWaves) {
                StringBuilder waveNextDesc = new StringBuilder();
                HashSet<Class<? extends Mob>> classesinawave = new HashSet<>();
                for (int randomo = 0; randomo < 20; randomo++){
                    classesinawave.add(arenacopy.chooseMob(wave+1).getClass());
                }
                int howmanyclasses = classesinawave.size();
                for (Class<? extends Mob> aClass : classesinawave) {

                    waveNextDesc.append(Messages.get(aClass, "name"));
                    howmanyclasses--;
                    if (howmanyclasses!=0) waveNextDesc.append(", ");
                }
                if (!(this instanceof Arena18))GLog.i(Messages.get(Prediction.class, "nextwave", waveNextDesc.toString()));
                if (wave<maxWaves-1) {
                    StringBuilder waveAfterThatDesc = new StringBuilder();
                    HashSet<Class<? extends Mob>> classesinanafterwave = new HashSet<>();
                    for (int randomo = 0; randomo < 20; randomo++){
                        classesinanafterwave.add(arenacopy.chooseMob(wave+2).getClass());
                    }
                    int howmanyclasses2 = classesinanafterwave.size();
                    for (Class<? extends Mob> aClass : classesinanafterwave) {

                        waveAfterThatDesc.append(Messages.get(aClass, "name"));
                        howmanyclasses2--;
                        if (howmanyclasses2!=0) waveAfterThatDesc.append(", ");
                    }
                    if (!(this instanceof Arena18))GLog.i(Messages.get(Prediction.class, "afternextwave", waveAfterThatDesc.toString()));
                }
            }



        }

    };


    //a method used for challenges with mobs being affected
    public void affectMob(Mob mob){
        //nothing by default
    }
    @Override
    protected void createMobs() {
        initNpcs();
        addDestinations();
    }
    @Override
    protected void createItems() {
    }

    @Override
    public int randomRespawnCell( Char ch ) {
            return -1;
    }


    public static class AmuletTower extends Mob {//this is the must-kill tower and the wave handler class at the same time

        {
            spriteClass = PortalSprite.class;

            HP = HT = 10;

            viewDistance = 3;

            defenseSkill = 0;

            EXP = 0;

            state = PASSIVE;

            flying = true;

            properties.add(Property.IMMOVABLE);
            properties.add(Property.BOSS);
            properties.add(Property.INORGANIC);


            alignment = Alignment.ALLY;
        }
        public int counter = 0;

        @Override
        public void heal(int healing) {
            //no healing
        }

        @Override
        public void die(Object cause) {
            if (cause == AmuletTower.class) super.die(cause);
            else {
                Dungeon.deleteGame(GamesInProgress.curSlot, true);
                Camera.main.panFollow(sprite, 2f);
                CellEmitter.get(pos).start(SacrificialParticle.FACTORY, 0.01f, 300);
                GameScene.updateFog(pos,5);
                hero.die(AmuletTower.class);
                hero.sprite.play(hero.sprite.idle, true);
                sprite.play(((PortalSprite)sprite).collapse, true);
                GameScene.updateFog(pos,5);
                if (cause instanceof Snake) Badges.validateSnaked();

                this.sprite.jump(pos, pos, 0, 3f, new Callback() {
                    @Override
                    public void call() {
                        Game.runOnRenderThread(new Callback() {
                            @Override
                            public void call() {

                                GameScene.gameOver();
                                GameScene.updateFog(pos,5);
                                GLog.cursed("The portal was destroyed...");
                                Sample.INSTANCE.play(Assets.Sounds.DEATH);
                                Hero.reallyDie(cause);
                                (AmuletTower.this).die(AmuletTower.class);
                                GameScene.updateFog(pos,5);
                                CellEmitter.get(pos).burst(SacrificialParticle.FACTORY,  300);
                            }
                        });
                    }
                });
            }
        }

        @Override
        public boolean isImmune(Class effect) {
            if (Buff.class.isAssignableFrom(effect)) return true;
            return super.isImmune(effect);
        }

        public boolean itWasAWave = false;
        @Override
        protected boolean act() {
            boolean enemyspotted = false;


            counter++;
            if (Dungeon.depth == 18) GameScene.updateFog(pos, 8);
            else GameScene.updateFog(pos, 5);

            //acid water actions
            for (Mob mob : level.mobs.toArray( new Mob[0] )) {
                if (((Arena)level).waterIsToxic && level.map[mob.pos] == Terrain.WATER && !mob.flying && !(mob instanceof DMWMinion)){
                    if (!(level.mode == WndModes.Modes.CHALLENGE && depth==15)) mob.damage(1, Corrosion.class);
                    else if (mob.alignment == Alignment.ALLY) mob.damage(3, Corrosion.class);
                }
            }
            if (((Arena)level).waterIsToxic && level.map[hero.pos] == Terrain.WATER && hero.buff(Levitation.class)==null){
                hero.damage((level.mode == WndModes.Modes.CHALLENGE && depth==15) ? 3 : 1, Corrosion.class);
            }

            //chal depth-specific actions:
            if (branch == 0 && depth==5 && level.mode == WndModes.Modes.CHALLENGE){
                for (Mob mob : level.mobs){
                    if (mob.alignment==Alignment.ALLY && level.map[pos]==Terrain.WATER){
                        Buff.affect(mob, Burning.class).setTime(20);
                    }
                }
            }
            if (branch == 0 && depth==8 && level.mode == WndModes.Modes.CHALLENGE){
                for (Mob mob : level.mobs){
                    if (mob.alignment==Alignment.ALLY && mob.buff(GoldArmor.class)==null){
                        Buff.affect(mob, GoldArmor.class).setTime(10000);
                    }
                }
            }

            //not arena related necessary actions
            if (hero.buff(Faint.class)!=null) BuffIndicator.refreshHero();


            //every arena necessary actions
            state = PASSIVE;
            ArrayList<Integer> posesToBeckon = new ArrayList<>();

            for (Mob mob2 : mobs){
                if (mob2 instanceof AmuletTower || mob2 instanceof SubAmuletTower) posesToBeckon.add(mob2.pos);
            }


            for (Mob mob : mobs.toArray( new Mob[0] )) {

                int beckoncell = this.pos;
                for (int posvar : posesToBeckon){
                    if (Dungeon.level.distance(mob.pos, posvar) < Dungeon.level.distance(mob.pos, beckoncell)) beckoncell = posvar;
                }

                if (mob.alignment==Alignment.ENEMY &&
                        !(mob.mapGuard)&&
                        !(mob instanceof Tower) &&
                        !(mob instanceof SubAmuletTower) &&
                        !(mob instanceof Piranha) &&
                        !(mob instanceof RotLasher) &&
                        !(mob instanceof Mimic) &&
                        !(mob instanceof Bee) &&
                        !(mob instanceof Arena6.SleepyThief) &&
                        !(mob instanceof EnemyPortal) &&
                        !(mob instanceof BossDwarfKing && ((BossDwarfKing)mob).battleMode == 0))
                {
                    mob.beckon( beckoncell );
                    enemyspotted = true;
                }
            }
            //non-chal depth specific actions
            if (Dungeon.depth==11 && Math.random()*1000+level.wave>999){
                if (mobs!=null && hero.buff(WaveCooldownBuff.class)==null) Arena11.dropRock(Random.element(mobs));
            }
            if (Dungeon.depth!=18 && !(depth==20 && level.wave == 25)){
                if (hero.buff(WaveBuff.class) != null && !enemyspotted) {
                    ((Arena) level).endWave();
                    itWasAWave = false;
                }
                if (hero.buff(WaveCooldownBuff.class) == null && hero.buff(WaveBuff.class) == null) {//starts the wave at cooldowns end
                    if (!itWasAWave) {
                        ((Arena) level).startWave();
                        itWasAWave = true;
                    } else {
                        ((Arena) level).endWave();
                        itWasAWave = false;}
                }
            }

            //some arena necessary actions







            //adjusting the projectile speed for the game to run smoothly
            if (mobs!=null) {
                MissileSprite.SPEED = 640f + mobs.size()*30;
                MagicMissile.SPEED = 600 + mobs.size()*20;
            }




            alerted = false;
            return super.act();
        }

        @Override
        public void damage(int dmg, Object src) {

            if(src == SubAmuletTower.class){
                super.damage(dmg,src);
            } else if (src instanceof Char && ((Char)src).alignment==Alignment.ENEMY) {
                Char chsrc = (Char)src;
                if (chsrc.properties().contains(Property.BOSS)){
                    super.damage(100, src);
                    System.out.println("000000000000");
                } else if (chsrc.properties().contains(Property.MINIBOSS)){
                    super.damage(3, src);
                    System.out.println("3333333333333");
                } else {
                    super.damage(1, src);
                    System.out.println("1111111111");
                }
                chsrc.damagePortal(pos);
            } else {
                super.damage(1, src);
                System.out.println("010101010101");
            }
        }

        @Override
        public boolean reset() {
            return true;
        }

        @Override
        public void beckon(int cell) {
            //do nothing
        }

        @Override
        public boolean interact(Char c) {
            if (c == Dungeon.hero && Dungeon.depth!=18) {
                if (level.wave==0){
                    ((Arena) level).startWave();
                }
                return true;
            } else return true;
        }

        @Override
        protected boolean canAttack(Char enemy) {
            return false;
        }

        private String COUNTER = "counter";
        private String LASTWASCOOLDOWN= "lastwascooldown";
        @Override
        public void storeInBundle(Bundle bundle) {
            super.storeInBundle(bundle);
            bundle.put(COUNTER, counter);
            bundle.put(LASTWASCOOLDOWN, itWasAWave);
        }
        @Override
        public void restoreFromBundle(Bundle bundle) {
            super.restoreFromBundle(bundle);
            counter = bundle.getInt(COUNTER);
            itWasAWave = bundle.getBoolean(LASTWASCOOLDOWN);
        }
    }

    private String WAVE = "wave";

    private String SHOPKEEPER = "shopkeeper";
    private String TOWERSHOPKEEPER = "towershopkeeper";



    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(WAVE, wave);
        bundle.put(SHOPKEEPER,normalShopKeeper);
        bundle.put(TOWERSHOPKEEPER,towerShopKeeper);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        wave = bundle.getInt(WAVE);
        normalShopKeeper = (NormalShopKeeper) bundle.get(SHOPKEEPER);
        towerShopKeeper = (TowerShopKeeper) bundle.get(TOWERSHOPKEEPER);
    }

}
