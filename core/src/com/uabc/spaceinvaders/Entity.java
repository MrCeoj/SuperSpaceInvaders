package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    
    protected Vector2 position;
    protected Vector2 position_initial;
    protected Sprite sprite;
    protected Boolean isAlive;
    protected float speed;
    
    
    public Entity(Vector2 position, Texture img, Color color, float speed){
        this.position = position;
        position_initial = position;
        sprite = new Sprite(img);
        sprite.setScale(1);
        isAlive = true;
        this.speed = speed;
    }
    
    public void draw(SpriteBatch batch){        
        sprite.draw(batch);
    }
    
    public abstract void update(float deltaTime);
    
}
