package com.fixakathefix.towerpixeldungeon.sprites;

import com.fixakathefix.towerpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;

public class PylonTowerBrokenSprite extends MobSprite {

    public PylonTowerBrokenSprite() {
        super();

        texture(Assets.Sprites.PYLON);

        TextureFilm frames = new TextureFilm(texture, 10, 20);

        idle = new MovieClip.Animation(20, true);
        idle.frames(frames, 2);

        attack = idle.clone();

        run = idle.clone();

        die = idle.clone();

        play(idle);
    }
}