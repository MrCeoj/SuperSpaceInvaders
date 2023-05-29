package com.uabc.spaceinvaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
 */
public class Waiting extends AbstractScreen {

    private final MainGame g;
    private boolean active;
    private Space sp;

    public Waiting(MainGame game) {
        super(game);
        this.g = game;
        sp = new Space(g);
    }

    @Override
    public void show() {
        active = false;
        sendconectado();
    }

    @Override
    public void render(float f) {
        super.render(f);
    }

    private void sendconectado() {
        if (!active) {
            this.g.cliente.enviar("entered");
        } else {
            this.g.cliente.enviar("enteres");
        }
    }

    public void seten() {
        active = true;
        sendconectado();
        cont();
    }

    public void cont() {
        g.setScreen(sp);
    }

    @Override
    public void hide() {
    }
 
    @Override
    public void dispose() {
    }

}
