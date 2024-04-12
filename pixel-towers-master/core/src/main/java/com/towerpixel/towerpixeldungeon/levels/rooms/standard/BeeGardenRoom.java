package com.towerpixel.towerpixeldungeon.levels.rooms.standard;

import com.towerpixel.towerpixeldungeon.actors.mobs.FreeBee;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class BeeGardenRoom extends StandardRoom {

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
        return super.canPlaceItem(p, l) && l.map[l.pointToCell(p)] != Terrain.WATER;
    }

    @Override
    public boolean canPlaceCharacter(Point p, Level l) {
        return super.canPlaceCharacter(p, l) && l.map[l.pointToCell(p)] != Terrain.WATER;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.GRASS );
        Painter.fillEllipse( level, this, 2, Terrain.FURROWED_GRASS );
        Painter.fillEllipse( level, this, 3, Terrain.WATER );

        int beeNum = Random.Int(1,2);

        for (int i=0; i < beeNum; i++) {// 1-2 bees
            FreeBee bee = new FreeBee();
            do {
                bee.pos = level.pointToCell(random(3));
            } while (level.map[bee.pos] != Terrain.WATER|| level.findMob( bee.pos ) != null);
            level.mobs.add( bee );
        }

        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }
    }

}
