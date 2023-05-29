package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Player extends Entity {             

    public Player(Vector2 position, Texture img, Color color, float speed) {
        super(position, img, color, speed);
        this.position = new Vector2(Gdx.graphics.getWidth()/2, 
                sprite.getScaleY()*sprite.getHeight()/2);
    }
    
    
    @Override
    public void update(float deltaTime){
                       
        if(position.x -(sprite.getWidth()*sprite.getScaleX()/2)<=0){
            position.x = sprite.getWidth()*sprite.getScaleX()/2;
        }
        
        if(position.x +(sprite.getWidth()*sprite.getScaleX()/2)>= Gdx.graphics.getWidth()){
            position.x = Gdx.graphics.getWidth()-(sprite.getWidth()*sprite.getScaleX()/2);
        } 
        
        sprite.setPosition(position.x, position.y);        
                
    }
    
    public void moveLeft(float deltaTime){
        position.x-= deltaTime*speed;
    }
    
    public void moveRight(float deltaTime){
        position.x+= deltaTime*speed;
    }
    
    public Vector2 getPosition(){
        return position;
    }    
}
