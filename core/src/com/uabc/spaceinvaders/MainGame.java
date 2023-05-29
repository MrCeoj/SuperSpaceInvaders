package com.uabc.spaceinvaders;

/*
    Marcelo Ortiz - Giselle Solorio - Alan Banagas
*/

import com.badlogic.gdx.Game;
import com.uabc.client.Client;

public class MainGame extends Game {
    
    public Client cliente;
    private Menu menu;
    
    @Override
    public void create() {
        cliente = new Client();
        Thread clientT = new Thread(cliente);
        clientT.start();
        menu = new Menu(this);
        setScreen(menu);
    }
}
