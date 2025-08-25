package com.fixakathefix.towerpixeldungeon.sprites.walls;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.fixakathefix.towerpixeldungeon.actors.Char;
import com.fixakathefix.towerpixeldungeon.sprites.MobSprite;
import com.watabou.noosa.TextureFilm;

public class TowerWall2Sprite extends MobSprite {

    private Animation cracked;//new anim
    private Animation broken;


    public TowerWall2Sprite() {
        super();

        texture(Assets.Sprites.TOWERWALL);

        TextureFilm frames = new TextureFilm(texture, 16, 16);

        idle = new Animation(1, true);
        idle.frames(frames, 16);


        attack = idle.clone();

        run = idle.clone();

        cracked = new Animation(1,true);
        cracked.frames(frames,17);

        broken = new Animation(1,true);
        broken.frames(frames,18);

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

    //walls show no emotions
    @Override
    public void showAlert() {
    }

    @Override
    public void showLost() {
    }

    @Override
    public void showSleep() {
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