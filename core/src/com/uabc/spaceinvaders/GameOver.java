package com.uabc.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOver extends AbstractScreen{
    SpriteBatch batch;
    Texture back = new Texture(Gdx.files.internal("GameOver.jpg"));
    private Music over;
    public GameOver(MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        over = Gdx.audio.newMusic(Gdx.files.internal("over.mp3"));
        over.play();
    }

    @Override
    public void render(float f) {
        super.render(f);
        batch.begin();
        batch.draw(back, f, f);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        back.dispose();
        over.dispose();
    }
    
}
