package com.fixakathefix.towerpixeldungeon.levels;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.levels.traps.BlazingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.CorrosionTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.CursingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DisarmingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DisintegrationTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.DistortionTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FlashingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.FrostTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GatewayTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GeyserTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GrimTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.GuardianTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.PitfallTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.RockfallTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.StormTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.SummoningTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.WarpingTrap;
import com.fixakathefix.towerpixeldungeon.levels.traps.WeakeningTrap;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class ArenaHalls extends Arena{
    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_HALLS;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_HALLS;
    }

    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_name");
            case Terrain.GRASS:
                return Messages.get(HallsLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(HallsLevel.class, "high_grass_name");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return Messages.get(HallsLevel.class, "water_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return Messages.get(HallsLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(HallsLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addHallsVisuals( this, visuals );
        return visuals;
    }

    public static void addHallsVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WATER) {
                group.add( new Stream( i ) );
            }
        }
    }

    private static class Stream extends Group {

        private int pos;

        private float delay;

        public Stream( int pos ) {
            super();

            this.pos = pos;

            delay = Random.Float( 2 );
        }

        @Override
        public void update() {

            if (!Dungeon.level.water[pos]){
                killAndErase();
                return;
            }

            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if ((delay -= Game.elapsed) <= 0) {

                    delay = Random.Float( 2 );

                    PointF p = DungeonTilemap.tileToWorld( pos );
                    ((FireParticle)recycle( FireParticle.class )).reset(
                            p.x + Random.Float( DungeonTilemap.SIZE ),
                            p.y + Random.Float( DungeonTilemap.SIZE ) );
                }
            }
        }

        @Override
        public void draw() {
            Blending.setLightMode();
            super.draw();
            Blending.setNormalMode();
        }
    }

    public static class FireParticle extends PixelParticle.Shrinking {

        public FireParticle() {
            super();

            color( 0xEE7722 );
            lifespan = 1f;

            acc.set( 0, +80 );
        }

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan;

            speed.set( 0, -40 );
            size = 4;
        }

        @Override
        public void update() {
            super.update();
            float p = left / lifespan;
            am = p > 0.8f ? (1 - p) * 5 : 1;
        }
    }
}
