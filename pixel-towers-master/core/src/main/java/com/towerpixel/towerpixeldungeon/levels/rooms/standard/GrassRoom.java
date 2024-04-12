package com.towerpixel.towerpixeldungeon.levels.rooms.standard;

import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;

public class GrassRoom extends StandardRoom {

    @Override
    public int minWidth() {
        return Math.max(8, super.minWidth());
    }

    @Override
    public int minHeight() {
        return Math.max(8, super.minHeight());
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{4, 2, 1};
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );

        Painter.fill( level, this, 1 , Terrain.GRASS );

        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
            if (door.x == left || door.x == right){
                Painter.drawInside(level, this, door, width()/2, Terrain.EMPTY);
            } else {
                Painter.drawInside(level, this, door, height()/2, Terrain.EMPTY);
            }
        }

        Painter.fillEllipse( level, this, 3 , Terrain.CHASM );
    }
}