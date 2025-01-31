package com.fixakathefix.towerpixeldungeon.levels.rooms.standard;

import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;

public class DiamondRoom extends StandardRoom {

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
    public void paint(Level level) {
        Painter.fillDiamond( level, this, Terrain.WALL );
        Painter.fillDiamond( level, this, 1, Terrain.EMPTY );
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
