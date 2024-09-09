/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.towerpixel.towerpixeldungeon.actors.mobs.npcs;

import static com.towerpixel.towerpixeldungeon.Dungeon.level;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.ShatteredPixelDungeon;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.actors.buffs.Buff;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Speck;
import com.towerpixel.towerpixeldungeon.effects.particles.ElmoParticle;
import com.towerpixel.towerpixeldungeon.items.Heap;
import com.towerpixel.towerpixeldungeon.items.Item;
import com.towerpixel.towerpixeldungeon.scenes.GameScene;
import com.towerpixel.towerpixeldungeon.sprites.ShopkeeperSprite;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class NewShopKeeper extends NPC {

    {
        spriteClass = ShopkeeperSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    public ShopDirection vertical = ShopDirection.DOWN;

    public enum ShopDirection{
        UP,
        LEFT,
        DOWN,
        RIGHT
    }

    @Override
    public boolean isImmune(Class effect) {
        return true;
    }

    @Override
    protected boolean act() {
        sprite.turnTo(pos, Dungeon.hero.pos);
        spend(TICK);
        return super.act();
    }

    @Override
    public void damage(int dmg, Object src) {

    }

    public ArrayList<Item> generateItems() {
        ArrayList<Item> itemsToSpawn = new ArrayList<>();
        return itemsToSpawn;
    }

    public void placeItems() {

        ArrayList<Item> itemsToSpawn = generateItems();

        if (vertical==ShopDirection.RIGHT) {
            int b = -Math.round(itemsToSpawn.size() * 0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop(item, pos + 1 + level.width()*b ).type = Heap.Type.FOR_SALE;//places stuff under the shopkeeper
                CellEmitter.center(pos + 1 + level.width()*b ).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        } else if (vertical==ShopDirection.DOWN) {
            int b = -Math.round(itemsToSpawn.size() * 0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop(item, pos + level.width() + b).type = Heap.Type.FOR_SALE;//places stuff under the shopkeeper
                CellEmitter.center(pos + level.width() + b).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        } else if (vertical==ShopDirection.LEFT) {
            int b = -Math.round(itemsToSpawn.size() * 0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop(item, pos - 1 + b*level.width()).type = Heap.Type.FOR_SALE;//places stuff under the shopkeeper
                CellEmitter.center(pos - 1 + b*level.width()).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        } else if (vertical==ShopDirection.UP) {
            int b = -Math.round(itemsToSpawn.size() * 0.5f) + 1;

            for (Item item : itemsToSpawn) {
                level.drop(item, pos - level.width() + b).type = Heap.Type.FOR_SALE;//places stuff under the shopkeeper
                CellEmitter.center(pos - level.width() + b).burst(Speck.factory(Speck.COIN), 3);
                b++;
            }
        }
    }

    @Override
    public boolean add(Buff buff) {
        if (super.add(buff)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        super.destroy();
        for (Heap heap : level.heaps.valueList()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                if (ShatteredPixelDungeon.scene() instanceof GameScene) {
                    CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
                }
                if (heap.size() == 1) {
                    heap.destroy();
                } else {
                    heap.items.remove(heap.size() - 1);
                    heap.type = Heap.Type.HEAP;
                }
            }
        }
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public boolean interact(Char c) {
        if (c != Dungeon.hero) {
            return true;
        }
        return true;
    }

    private static final String VERTICAL = "vertical";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(VERTICAL, vertical);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        vertical = bundle.getEnum(VERTICAL, ShopDirection.class);
    }
}