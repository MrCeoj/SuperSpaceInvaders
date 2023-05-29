package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


public class Bullet extends Entity {

    public Bullet(Vector2 position, Texture img, Color color, float speed) {
        super(position, img, color, speed);
    }
    
    @Override
    public void update(float deltaTime){
        position.y += deltaTime*speed;
        sprite.setPosition(position.x, position.y);
    }
    
    public Boolean isInsideScreen(){
        return (position.y >= Gdx.graphics.getHeight());
    }
    
    public Boolean isoutside(){
        return(position.y < 0);
    }
}
