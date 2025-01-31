package com.fixakathefix.towerpixeldungeon.levels.rooms.standard;

import com.fixakathefix.towerpixeldungeon.actors.mobs.Piranha;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;

public class DiamondPondPiranhaRoom extends StandardRoom {

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

        Piranha piranha = new Piranha();
        do {
            piranha.pos = level.pointToCell(random(3));
        } while (level.map[piranha.pos] != Terrain.WATER|| level.findMob( piranha.pos ) != null);
        level.mobs.add( piranha );

    }


}