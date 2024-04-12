package com.towerpixel.towerpixeldungeon.levels.rooms.standard;

import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;

public class DiamondPondRoom extends StandardRoom {

    @Override
    public int minWidth() {
        return Math.max(super.minWidth(), 9);
    }

    @Override
    public int minHeight() {
        return Math.max(super.minHeight(), 9);
    }

    @Override
    public float[] sizeCatProbs() {
        return new float[]{2, 3, 0};
    }

    @Override
    public void paint(Level level) {
        Painter.fillDiamond( level, this, Terrain.WALL );
        Painter.fillDiamond( level, this, 1, Terrain.FURROWED_GRASS );
        Painter.fillEllipse( level, this, 3, Terrain.WATER );
        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
            if (door.x == left || door.x == right){
                Painter.drawInside(level, this, door, width()/2, Terrain.EMPTY);
            } else {
                Painter.drawInside(level, this, door, height()/2, Terrain.EMPTY);
            }
        }
    }

}