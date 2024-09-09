package com.towerpixel.towerpixeldungeon.sprites.walls;

import com.towerpixel.towerpixeldungeon.Assets;
import com.towerpixel.towerpixeldungeon.actors.Char;
import com.towerpixel.towerpixeldungeon.sprites.MobSprite;
import com.watabou.noosa.TextureFilm;

public class TowerWallSpikeSprite extends MobSprite {

    private Animation cracked;//new anim
    private Animation broken;


    public TowerWallSpikeSprite() {
        super();

        texture(Assets.Sprites.TOWERWALLSPIKED);

        TextureFilm frames = new TextureFilm(texture, 24, 16);

        idle = new Animation(1, true);
        idle.frames(frames, 0);


        attack = idle.clone();

        run = idle.clone();

        cracked = new Animation(1,true);
        cracked.frames(frames,1);

        broken = new Animation(1,true);
        broken.frames(frames,2);

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