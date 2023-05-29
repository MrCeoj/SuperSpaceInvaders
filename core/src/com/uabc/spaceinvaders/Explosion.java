package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Explosion {

    public ParticleEffect effect;

    public Explosion(float x, float y) {

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("explosion.p"), Gdx.files.internal(""));
        startEffect(x, y);
    }

    public void draw(SpriteBatch batch, float delta) {
        effect.draw(batch, delta);
    }

    private void startEffect(float x, float y) {
        effect.setPosition(x, y);
        effect.start();
    }

    public void dispose() {
        effect.dispose();
    }
}
