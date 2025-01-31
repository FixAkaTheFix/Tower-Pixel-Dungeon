package com.fixakathefix.towerpixeldungeon.levels.rooms.standard;

import com.fixakathefix.towerpixeldungeon.actors.mobs.RotLasher;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;

public class LasherRoom extends StandardRoom {

    @Override
    public int minWidth() {
        return Math.max(super.minWidth(), 7);
    }

    @Override
    public int minHeight() {
        return Math.max(super.minHeight(), 7);
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{3, 1, 0};
    }

    @Override
    public boolean canPlaceItem(Point p, Level l) {
        return super.canPlaceItem(p, l) && l.map[l.pointToCell(p)] != Terrain.FURROWED_GRASS;
    }

    @Override
    public boolean canPlaceCharacter(Point p, Level l) {
        return super.canPlaceCharacter(p, l) && l.map[l.pointToCell(p)] != Terrain.FURROWED_GRASS;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY );
        Painter.fill( level, this, 2, Terrain.EMPTY_SP );
        Painter.fill( level, this, 3, Terrain.FURROWED_GRASS );

        int minDim = Math.min(width(), height());
        int numLasher = 1;

        for (int i=0; i < numLasher; i++) {
            RotLasher rl = new RotLasher();
            do {
                rl.pos = level.pointToCell(random(3));
            } while (level.map[rl.pos] != Terrain.FURROWED_GRASS|| level.findMob( rl.pos ) != null);
            level.mobs.add( rl );
        }

        for (Door door : connected.values()) {
            door.set( Door.Type.UNLOCKED );
        }
    }

}
