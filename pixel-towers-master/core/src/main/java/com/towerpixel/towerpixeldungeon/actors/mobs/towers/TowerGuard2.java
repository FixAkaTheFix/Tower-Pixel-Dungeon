package com.towerpixel.towerpixeldungeon.actors.mobs.towers;

import com.towerpixel.towerpixeldungeon.Dungeon;
import com.towerpixel.towerpixeldungeon.actors.blobs.ToxicGas;
import com.towerpixel.towerpixeldungeon.actors.buffs.Amok;
import com.towerpixel.towerpixeldungeon.actors.buffs.Drowsy;
import com.towerpixel.towerpixeldungeon.actors.buffs.WaveBuff;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard1Sprite;
import com.towerpixel.towerpixeldungeon.sprites.TowerGuard2Sprite;

public class TowerGuard2 extends TowerGuard1{

    {
        HP = HT = 100;
        spriteClass = TowerGuard2Sprite.class;

        cost = 300;
        damageMin = 4;
        damageMax = 6;
        upgradeLevel = 9;
        defMin = 0;
        defMax = 7;
        upgrade1Cost = 400;
        regenNum = 8;
    }
}
