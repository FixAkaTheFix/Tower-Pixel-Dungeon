package com.fixakathefix.towerpixeldungeon.levels.rooms.standard;

import com.fixakathefix.towerpixeldungeon.Dungeon;
import com.fixakathefix.towerpixeldungeon.items.Gold;
import com.fixakathefix.towerpixeldungeon.items.Heap;
import com.fixakathefix.towerpixeldungeon.levels.Level;
import com.fixakathefix.towerpixeldungeon.levels.Terrain;
import com.fixakathefix.towerpixeldungeon.levels.painters.Painter;
import com.fixakathefix.towerpixeldungeon.levels.rooms.special.MassGraveRoom;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class MassGraveLibraryRoom extends StandardRoom {

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
        Painter.fill( level, this, 1, Terrain.EMPTY );
        Painter.fill( level, this, 2, Terrain.EMPTY_SP );
        Painter.fill( level, this, 3, Terrain.BOOKSHELF );
        Painter.fill( level, this, 4, Terrain.EMPTY_DECO );

        MassGraveRoom.Bones b = new MassGraveRoom.Bones();

        b.setRect(left+4, top+3, width()-8, height()-7);
        level.customTiles.add(b);

        int pos;
        do {
            pos = level.pointToCell(random());
        } while (level.map[pos] != Terrain.EMPTY_DECO || level.heaps.get(pos) != null);
        Heap h = level.drop(new Gold(Random.Int(Dungeon.depth*5,Dungeon.depth*10)), pos);
        h.type = Heap.Type.SKELETON;


        for (Door door : connected.values()) {
            door.set( Door.Type.EMPTY );
        }
    }

}
