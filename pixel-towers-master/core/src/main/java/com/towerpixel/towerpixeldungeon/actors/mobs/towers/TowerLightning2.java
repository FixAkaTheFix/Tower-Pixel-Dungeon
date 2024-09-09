package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.Actor;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.effects.CellEmitter;
import com.towerpixel.towerpixeldungeon.effects.Lightning;
import com.towerpixel.towerpixeldungeon.effects.particles.SparkParticle;
import com.towerpixel.towerpixeldungeon.mechanics.Ballistica;
import com.towerpixel.towerpixeldungeon.sprites.TowerLightning2Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerWand1Sprite;
import com.towerpixel.towerpixeldungeon.tiles.DungeonTilemap;
import com.towerpixel.towerpixeldungeon.utils.BArray;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class TowerLightning2 extends TowerLightning1 {
    {
        HP = HT = 60;
        spriteClass = TowerLightning2Sprite.class;

        viewDistance = 3;
        baseAttackDelay = 4f;

        cost = 600;
        upgrade1Cost = 700;
        damageMin = 25;
        damageMax = 52;
        upgradeLevel = 3;
        maxChainLength = 6;
    }
}
