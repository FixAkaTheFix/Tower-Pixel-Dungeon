package com.fixakathefix.towerpixeldungeon.sprites.walls;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.MobSprite;
import com.watabou.noosa.TextureFilm;

public class TowerWall3Sprite extends MobSprite {

    private Animation cracked;//new anim
    private Animation broken;


    public TowerWall3Sprite() {
        super();

        texture(Assets.Sprites.TOWERWALL);

        TextureFilm frames = new TextureFilm(texture, 16, 16);

        idle = new Animation(1, true);
        idle.frames(frames, 32);


        attack = idle.clone();

        run = idle.clone();

        cracked = new Animation(1,true);
        cracked.frames(frames,33);

        broken = new Animation(1,true);
        broken.frames(frames,34);

        die = broken.clone();

        play(idle);
    }

    public void cracked(){//methods for the new anim
        play(cracked);
    }

    public void broken(){
        play(broken);
    }

    @Override
    public void idle() {
        if (ch==null) play (idle); else linkVisuals(ch);
    }


    @Override
    public void linkVisuals(Char ch) {
        super.linkVisuals(ch);
        if (ch.HP<0.3f*ch.HT) {
            broken();
        } else if (ch.HP<0.6f*ch.HT) {
            cracked();
        } else play(idle);

    }

    @Override
    public void move(int from, int to) {
        linkVisuals(ch);
        super.move(from, to);
        linkVisuals(ch);
    }
}