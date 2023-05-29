package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;



public class Alien extends Entity{
    Vector2 offset_aliens;
    int past_direction;

    public Alien(Vector2 position, Texture img, Color color, float speed) {
        super(position, img, color, speed);
        offset_aliens = Vector2.Zero;
        offset_aliens = new Vector2(0,0);                
        past_direction = 1;
    }

    public void update(float deltaTime, int direction_alien) {

        offset_aliens.x += direction_alien * deltaTime * speed;
        if (direction_alien == 1 && (past_direction != direction_alien)) {            
            offset_aliens.y -= sprite.getHeight() * sprite.getScaleY() * 0.75f;
            speed += 3;
            past_direction = direction_alien;
        }
        if (direction_alien == -1 && (past_direction != direction_alien)) {            
            offset_aliens.y -= sprite.getHeight() * sprite.getScaleY() * 0.75f;
            speed += 3;
            past_direction = direction_alien;
        }
        
        if (position.y <= 0) {
            Gdx.app.exit();
        }
        position = new Vector2(position_initial.x + offset_aliens.x, position_initial.y + offset_aliens.y);
        
        sprite.setPosition(position.x, position.y);

    }

    @Override
    public void update(float deltaTime) {
        
    }
}

