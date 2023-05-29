package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Menu extends AbstractScreen {

    private final MainGame g;
    private Texture back;
    private final SpriteBatch batch;
    private final Space sp;
    private final Music menuMusic;

    public Menu(MainGame game) {
        super(game);
        this.g = game;
        sp = new Space(g);

        batch = new SpriteBatch();
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    @Override
    public void show() {
        back = new Texture("background.jpg");
    }

    @Override
    public void render(float f) {
        super.render(f);
        batch.begin();
        batch.draw(back, 0, 0);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.g.setScreen(sp);
            dispose();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            dispose();
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        back.dispose();
        menuMusic.dispose();
    }

}
