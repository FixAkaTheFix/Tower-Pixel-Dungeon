package com.towerpixel.towerpixeldungeon.levels.rooms.standard;

import com.towerpixel.towerpixeldungeon.actors.mobs.Rat;
import com.towerpixel.towerpixeldungeon.levels.Level;
import com.towerpixel.towerpixeldungeon.levels.Terrain;
import com.towerpixel.towerpixeldungeon.levels.painters.Painter;
import com.towerpixel.towerpixeldungeon.levels.traps.BurningTrap;
import com.watabou.utils.Point;

public class TrapSquareRoom extends StandardRoom {

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
        return super.canPlaceItem(p, l);
    }

    @Override
    public boolean canPlaceCharacter(Point p, Level l) {
        return false;
    }

    @Override
    public void paint(Level level) {
        Painter.fill( level, this, Terrain.WALL );
        Painter.fill( level, this, 1, Terrain.EMPTY_SP );
        Painter.fill( level, this, 2, Terrain.EMPTY);



        for (int i=top + 1; i < bottom; i++) {
            for (int j=left + 1; j < right; j++) {
                if (((i==top+2) || (i==bottom-2) || (j==left+2) || (j==right-2))&&!((i==top+1) || (i==bottom-1) || (j==left+1) || (j==right-1))) {
                    int cell = i * level.width() + j;//main coordinate code!!!
                    int t;
                    t = Terrain.TRAP;
                    level.setTrap(new BurningTrap().reveal(), cell);
                    level.map[cell] = t;
                }
            }
        }




        int minDim = Math.min(width(), height());


            Rat trappedremi = new Rat();
            do {
                trappedremi.pos = level.pointToCell(random(3));
            } while (level.map[trappedremi.pos] != Terrain.EMPTY|| level.findMob( trappedremi.pos ) != null);
            level.mobs.add( trappedremi );


        for (Door door : connected.values()) {
            door.set( Door.Type.REGULAR );
        }
    }

}
