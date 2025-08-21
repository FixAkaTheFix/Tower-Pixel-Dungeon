package com.fixakathefix.towerpixeldungeon.levels;

import static com.fixakathefix.towerpixeldungeon.Dungeon.hero;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.actors.blobs.Blob;
import com.fixakathefix.towerpixeldungeon.actors.buffs.Buff;
import com.fixakathefix.towerpixeldungeon.actors.mobs.DM300;
import com.fixakathefix.towerpixeldungeon.messages.Messages;
import com.fixakathefix.towerpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.GameMath;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class ArenaCaves extends Arena{
    @Override
    public String tileName( int tile ) {
        switch (tile) {
            case Terrain.GRASS:
                return Messages.get(CavesLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_name");
            case Terrain.WATER:
                return Messages.get(CavesLevel.class, "water_name");
            default:
                return super.tileName( tile );
        }
    }

    @Override
    public String tileDesc( int tile ) {
        switch (tile) {
            case Terrain.ENTRANCE:
                return Messages.get(CavesLevel.class, "entrance_desc");
            case Terrain.EXIT:
                return Messages.get(CavesLevel.class, "exit_desc");
            case Terrain.HIGH_GRASS:
                return Messages.get(CavesLevel.class, "high_grass_desc");
            case Terrain.WALL_DECO:
                return Messages.get(CavesLevel.class, "wall_deco_desc");
            case Terrain.BOOKSHELF:
                return Messages.get(CavesLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc( tile );
        }
    }

    @Override
    public Group addVisuals() {
        super.addVisuals();
        addCavesVisuals( this, visuals );
        return visuals;
    }

    public static void addCavesVisuals( Level level, Group group ) {
        for (int i=0; i < level.length(); i++) {
            if (level.map[i] == Terrain.WALL_DECO) {
                group.add( new Vein( i ) );
            }
        }
    }

    public static class Vein extends Group {

        private int pos;

        private float delay;

        public Vein( int pos ) {
            super();

            this.pos = pos;

            delay = Random.Float( 2 );
        }

        @Override
        public void update() {

            if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {

                super.update();

                if ((delay -= Game.elapsed) <= 0) {

                    //pickaxe can remove the ore, should remove the sparkling too.
                    if (Dungeon.level.map[pos] != Terrain.WALL_DECO){
                        kill();
                        return;
                    }

                    delay = Random.Float();

                    PointF p = DungeonTilemap.tileToWorld( pos );
                    ((Sparkle)recycle( Sparkle.class )).reset(
                            p.x + Random.Float( DungeonTilemap.SIZE ),
                            p.y + Random.Float( DungeonTilemap.SIZE ) );
                }
            }
        }
    }

    public static void dropRock( Char target ) {

        hero.interrupt();
        final int rockCenter;

        rockCenter = target.pos;


        int safeCell;
        do {
            safeCell = rockCenter + PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while ((Dungeon.level.solid[safeCell] && Random.Int(2) == 0)
                || (Blob.volumeAt(safeCell, CavesBossLevel.PylonEnergy.class) > 0 && Random.Int(2) == 0));

        ArrayList<Integer> rockCells = new ArrayList<>();

        int start = rockCenter - Dungeon.level.width() * 3 - 3;
        int pos;
        for (int y = 0; y < 7; y++) {
            pos = start + Dungeon.level.width() * y;
            for (int x = 0; x < 7; x++) {
                if (!Dungeon.level.insideMap(pos)) {
                    pos++;
                    continue;
                }
                //add rock cell to pos, if it is not solid, and isn't the safecell
                if (!Dungeon.level.solid[pos] && pos != safeCell && Random.Int(Dungeon.level.distance(rockCenter, pos)) == 0) {
                    //don't want to overly punish players with slow move or attack speed
                    rockCells.add(pos);
                }
                pos++;
            }
        }
        Buff.append(hero, DM300.FallingRockBuff.class, GameMath.gate(1, target.cooldown(), 3)).setRockPositions(rockCells);
    }

    public static final class Sparkle extends PixelParticle {

        public void reset( float x, float y ) {
            revive();

            this.x = x;
            this.y = y;

            left = lifespan = 0.5f;
        }

        @Override
        public void update() {
            super.update();

            float p = left / lifespan;
            size( (am = p < 0.5f ? p * 2 : (1 - p) * 2) * 2 );
        }
    }

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CAVES;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CAVES;
    }
}
