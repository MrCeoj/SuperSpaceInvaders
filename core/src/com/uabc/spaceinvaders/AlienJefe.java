package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class AlienJefe extends Alien{ 
    protected int vida = 5;

    public AlienJefe(Vector2 position, Texture img, Color color, float speed) {
        super(position, img, color, speed);
    }
    
    protected void hitAlien(){
        vida-=1;
        if(vida==0){
            isAlive = false;
        }
    }
    
    public void update(float deltaTime, float playerposx){
        offset_aliens.y -= deltaTime*speed;
        position = new Vector2(playerposx, position_initial.y + offset_aliens.y);
        sprite.setPosition(position.x, position.y);
    }
    
}
